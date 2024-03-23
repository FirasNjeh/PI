package esprit.pi.demo.Services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import esprit.pi.demo.Repository.CreditRepository;
import esprit.pi.demo.Repository.TransactionCRepository;
import esprit.pi.demo.Repository.UserRepository;
import esprit.pi.demo.entities.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;


@AllArgsConstructor
@Service
public class CreditService implements ICreditService {

    private CreditRepository repository;
    private TransactionCRepository repot;
    UserRepository repo;
    private GarantService ga;
    private PackCRService pa;
    private ServiceUser se;
    private TransactionCService trans;
    private PortefeuilleService port;
    //private MonthlyPaymentService mps;

    //client
    @Override
    public Credit saveCredit(int idp, int idg, int idu, Credit credit) {
        PackCR p;
        Garant g;
        User u;
        TransactionCredit transactionC = new TransactionCredit();

        //MonthlyPayment mp;
        p = pa.getPackCRById(idp);
        g = ga.getById(idg);
        u = se.getUserById(idu);

        credit.setPackCR(p);
        credit.setGarant(g);
        credit.setUserCR(u);
        credit.setPackCredit(p.getPackCredit());
        credit.setStatusCredit(StatusCredit.EN_ATTENTE);

    //si credit approuvé
        if(u.getNbr_credit()<=2)
        {
        credit.setStatusCredit(StatusCredit.APPROUVE);
        float montant = credit.getMontant();

        //date d'ajout du credit
        credit.setDateDeb(LocalDate.now());

        //montant restant a payer
        credit.setMontantRestant(credit.getMontant());

        //date du prochain paiement
        Calendar calendar = Calendar.getInstance();
        LocalDate localDate =credit.getDateDeb();
        Date date = Date.valueOf(localDate);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        credit.setDatepp(calendar.getTime());

        //ajouter transaction
        transactionC.setDate(LocalDate.now());
        transactionC.setTypeTransaction(TypeTransaction.VERSEMENT);
        transactionC.setMontant(montant);
        int cut=credit.getUserCR().getPortefeuilleUser().getId(); // el id mtaa el portefeuille
        transactionC.setRib_destination(port.getPortefeuilleById(cut).getRib());
        trans.saveTransactionC(transactionC,cut);

        repository.save(credit); // enregistrer

        //incrementer nombre des credits de l'utilisateur
        u.setNbr_credit(u.getNbr_credit()+1);

        credit.setCreditHistory(CreditHistory.NONE_PAYED_YET);
        credit.setAnnuité(calculRemboursementAnnuite(credit.getId()));
        credit.setPaiementMensuel(credit.getAnnuité()/12);
     //   credit.setTauxInteret(calculateInterestRate(credit));

        repository.save(credit);

        //envoyer le mail que le credit est approuvé
        String tomail="nerminenafti@gmail.com";
        String subject="Credit approuvé";
        String body ="Votre credit a ete approuvé nshlh mabrouk ";
        try { sendEmail(tomail,subject,body);} catch (MessagingException e ) {e.printStackTrace();}}

    //si credit non approuvé
        else
        {  credit.setStatusCredit(StatusCredit.REFUSE);
            repository.save(credit);

            // envoyer mail li el credit rejeté
            String tomail="nerminenafti@gmail.com";
            String subject="Credit rejeté";
            String body ="Votre credit a ete rejeté ";
            try { sendEmail(tomail,subject,body);} catch (MessagingException e ) {e.printStackTrace();}}


return credit;
    }


    //agent + client
    @Override
    public List<Credit> getCredits() {
        return repository.findAll();
    }

    //agent
    @Override
    public Credit getCreditById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Credit> getCreditsByStatus(StatusCredit statusCredit) {
        return repository.findByStatusCredit(statusCredit);
    }
@Override
    public List<Credit> getCreditsByMontant(float montant) {
        return repository.findByMontant(montant);
    }
    @Override
    public List<Credit> getCreditByMontantMore(float montant) {return  repository.getCreditByMontantGreaterThan(montant);}
@Override
    public List<Credit> getCreditByMontantLess(float montant) {return  repository.getCreditByMontantLessThan(montant);}
@Override
    public List<Credit> getCreditsByDuree(int duree) {
        return repository.findByDuree(duree);
    }

    //agent
    @Override
    public String deleteCredit(int id) {
        repository.deleteById(id);
        return "Crédit cloturé !!" + id;
    }

    //agent
    @Override
    public Credit updateCredit(int id, Credit credit) {
        Credit existingCredit = repository.findById(id).orElse(null);
        existingCredit.setMontant(credit.getMontant());
        existingCredit.setStatusCredit(credit.getStatusCredit());
        existingCredit.setDuree(credit.getDuree());
        existingCredit.setDateDeb(credit.getDateDeb());
        existingCredit.setPaiementMensuel(credit.getPaiementMensuel());
        existingCredit.setTauxInteret(credit.getTauxInteret());
        return repository.save(existingCredit);

    }

    @Override
    public List<Credit> getCreditsFiltrage(String field, String value) {
        List<Credit> credits = null;
        switch (field) {
            case "montant":
                float montant = Float.parseFloat(value);
                credits = repository.findByMontant(montant);
                break;
            case "duree":
                int duree = Integer.parseInt(value);
                credits = repository.findByDuree(duree);
                break;
            case "statusCredit":
                StatusCredit statusCredit = StatusCredit.valueOf(value.toUpperCase());
                credits = repository.findByStatusCredit(statusCredit);
                break;
            default:
                throw new IllegalArgumentException("Le champ de filtrage n'est pas valide");
        }
        if (credits.isEmpty()) {
            throw new IllegalArgumentException("Il n'y a pas de crédits dans la base de données");
        }
        return credits;
    }
@Override
    public List<Credit> getCreditsTrie(String field, String value) {
        List<Credit> credits = null;
        switch (field) {
            case "montant":
                if(value=="asc"){
                credits = getCreditsTriéMontantA();}
                else credits = getCreditsTriéMontantD();
                break;
            case "montantRestant":
                if(value=="asc"){
                    credits = getCreditsTriéMontantRA();}
                else credits = getCreditsTriéMontantRD();
                break;
            case "dateDeb":
                if(value=="asc"){
                    credits = getCreditsTriéDateDA();}
                else credits = getCreditsTriéDateDD();
                break;
            default:
                throw new IllegalArgumentException("Le champ de tri n'est pas valide");
        }
        if (credits.isEmpty()) {
            throw new IllegalArgumentException("Il n'y a pas de crédits dans la base de données");
        }
        return credits;
    }
    @Override
    public List<Credit> getCreditsTriéMontantA() {
        return repository.findAll(Sort.by(Sort.Direction.ASC,"Montant"));
    }
@Override
    public List<Credit>getCreditsTriéMontantD() {
        return repository.findAll(Sort.by(Sort.Direction.DESC,"montant"));
    }
@Override
    public List<Credit> getCreditsTriéMontantRA() {
        return repository.findAll(Sort.by(Sort.Direction.ASC,"montantRestant"));
    }
@Override
    public List<Credit>getCreditsTriéMontantRD() {
        return repository.findAll(Sort.by(Sort.Direction.DESC,"montantRestant"));
    }
@Override
    public List<Credit> getCreditsTriéDateDA() {
        return repository.findAll(Sort.by(Sort.Direction.ASC,"dateDeb"));
    }
@Override
    public List<Credit>getCreditsTriéDateDD() {
        return repository.findAll(Sort.by(Sort.Direction.DESC,"dateDeb"));
    }



    @Override
    public float calculRemboursementAnnuite(int id) {
        double A;
        Credit c = repository.findById(id).orElse(null);
        A = (c.getMontant() * (c.getTauxInteret() / 100)) / (1 - Math.pow(1 + (c.getTauxInteret() / 100), (-c.getDuree())));
        c.setAnnuité((float) A);
        return (float)A;

    }

    @Override
    public double MaxCredit(int id){
        Credit c=getCreditById(id);
        User user=c.getUserCR();
        int nbmois=c.getDuree()*12;
        double taux=0.23;
        double tauxMensuel = taux / 12;
        double MaxCredit=((user.getSalaire()*0.43)*(1 - Math.pow(1 + tauxMensuel, -nbmois))) / tauxMensuel; //0.43 ratio d'endettement entre 33% et 43%
        return MaxCredit;
    }

    @Override
    public int scoreCredit(int id) {
        int score = 0;

        Credit credit = repository.findById(id).get();
        //User user=repo.findByUserCRId(credit.getUserCR()); //recuperation de l'utilisateur
        User user=credit.getUserCR();

        float revenuMensuel = user.getSalaire();
        int ancienneteEmploi = user.getAncienneteEmploi();
        float montantPret = credit.getMontant();
        int dureePret = credit.getDuree();
        //  float ratioEndettement = credit.getTauxInteret();

//

        // calcul de l'age
        int age = Period.between(user.getDateNaissance(), LocalDate.now()).getYears();

        //historique de paiement
        int hist;
        if(credit.getCreditHistory().ordinal()==0 ||credit.getCreditHistory().ordinal()==1 ||credit.getCreditHistory().ordinal()==2)
        {hist=0;} else {hist=1;}


        //Critère 1: Revenu mensuel
        if (revenuMensuel >= 1500 && revenuMensuel <= 3000) {
            score += 20;
        } else if (revenuMensuel > 3000) {
            score += 40;
        }

        //Critère 2: Ancienneté de l'emploi
        if (ancienneteEmploi >= 2 && ancienneteEmploi <= 5) {
            score += 10;
        } else if (ancienneteEmploi > 5) {
            score += 20;
        }

        //Critère 3: Montant du prêt && dureePret <= 24
        if (montantPret >= 3000 && montantPret <= 10000 ) {
            score += 10;
        } else if (montantPret > 10000 ) {
            score += 20;
        }

        //Critère 4: Duree du prêt
        if (dureePret >= 2 && dureePret <= 4 ) {
            score += 10;
        } else if (dureePret > 4 ) {
            score += 20;
        }

        //Critère 5: Age
        if (age >= 25 && age <= 40) {
            score += 10;
        } else if (age > 40 && age <= 50) {
            score += 5;
        }

        //Critère 6: Historique de crédit
        if (hist==0) {
            score += 10;
        }

        return score;
    }

    @Override
    public float calculateInterestRate(int id) {
        Credit c=getCreditById(id);

        float baseInterestRate = 0.2f; // taux d'intérêt de base pour la microfinance
        float interestRate = baseInterestRate;
        User user = repo.findById(c.getUserCR().getId()).orElse(null);

        // Vérification de l'âge du client
        int age = Period.between(user.getDateNaissance(), LocalDate.now()).getYears();


        double ageFactor = 1.0;
        if (age < 25) {
            ageFactor = 1.1; // augmentation de 10% du taux d'intérêt pour les jeunes emprunteurs
        } else if (age >= 65) {
            ageFactor = 0.9; // diminution de 10% du taux d'intérêt pour les emprunteurs âgés
        }
        interestRate *= ageFactor;

        // Vérification du niveau de revenu du client
        double income = user.getSalaire();
        double incomeFactor = 1.0;
        if (income < 1000) {
            incomeFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les emprunteurs à faible revenu
        } else if (income >= 1000) {
            incomeFactor = 0.8; // diminution de 20% du taux d'intérêt pour les emprunteurs à revenu élevé
        }
        interestRate *= incomeFactor;

        // Vérification de l'historique de crédit du client

        double creditHistoryFactor = 1.0;
        if (user.isEtat()==false) {
            creditHistoryFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les emprunteurs ayant déjà fait défaut
        }
        interestRate *= creditHistoryFactor;

        // Vérification de la durée du prêt
        int loanTerm =c.getDuree();
        double loanTermFactor = 1.0;
        if (loanTerm < 2) {
            loanTermFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les prêts de courte durée
        } else if (loanTerm > 2) {
            loanTermFactor = 0.9; // diminution de 10% du taux d'intérêt pour les prêts de longue durée
        }
        interestRate *= loanTermFactor;

        // Vérification du montant du prêt demandé par le client
        double loanAmount = c.getMontant();
        double loanAmountFactor = 1.0;
        if (loanAmount > 5000) {
            loanAmountFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les montants de prêt élevés
        }
        interestRate *= loanAmountFactor;

        return interestRate;

    }
    public void sendEmail(String tomail, String Subject, String body) throws MessagingException {

        String from ="techwork414@gmail.com";
        String password="pacrvzlvscatwwkb";

        Properties props=new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });


        Message message=new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(tomail));
        message.setSubject(Subject);
        message.setContent(body,"text/html");

        Transport.send(message);
    }

//    @Override
//    public File genererCreditPDF(int nbmois) throws IOException, DocumentException {
//        //User user = iUser.getUserByEmail(iMicroGrowth.getCurrentUserName());
//        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("credit.pdf"));
//
//        // Ajouter le header
//        HeaderFooter header = new HeaderFooter(new Phrase("MicroGrowth"), false);
//        header.setAlignment(Element.ALIGN_CENTER);
//        document.setHeader(header);
//
//        // Ajouter le footer
//        Font FooterFont = new Font(Font.HELVETICA, 7, Font.NORMAL);
//
//        Paragraph footerText = new Paragraph("Le résultat de cette simulation est non contractuel et revêt un caractère strictement informatif.\n" ,FooterFont);
//
//        footerText.setSpacingBefore(10f); // ajoute un espace de 10 points avant le texte du footer
//
//        HeaderFooter footer = new HeaderFooter(footerText, new Phrase(" - Page ",FooterFont));
//        footer.setAlignment(Element.ALIGN_LEFT);
//        document.setFooter(footer);
//
//
//        // Ouvrir le document
//        document.open();
//
//        // Ajouter le logo
//        Image logo = Image.getInstance("logo.png");
//        logo.scaleAbsolute(100f, 100f);
//        logo.setAlignment(Element.ALIGN_CENTER);
//        document.add(logo);
//
//        // Ajouter les informations du contrat
//        double max = MaxCredit(37);
//        Font boldFont = new Font(Font.HELVETICA, 16, Font.BOLD);
//        Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
//        Paragraph montantMax = new Paragraph("Montant maximum : " + max + " dinars", boldFont);
//        montantMax.setSpacingAfter(20f);
//        document.add(montantMax);
//
////        Paragraph mensualite = new Paragraph("Mensualité : " + user.getSalaire()*0.43 + " dinars", normalFont);
////        mensualite.setSpacingAfter(20f);
////        document.add(mensualite);
//
//        Paragraph duree = new Paragraph("Durée : " + nbmois + " mois", normalFont);
//        duree.setSpacingAfter(20f);
//        document.add(duree);
//
//        Paragraph taux = new Paragraph("Taux d'intérêt : 0.23%", normalFont);
//        taux.setSpacingAfter(20f);
//        document.add(taux);
//
//
//        // Fermer le document
//        document.close();
//        return null;
//    }

    @Override
    public void export(HttpServletResponse response, int id) throws IOException, DocumentException {
        Credit credit= repository.findById(id).orElse(null);
         Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph= new Paragraph("Tableau d''Amortissement "+String.valueOf(id),fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(3);
        PdfPCell cell= new PdfPCell();
        cell.setPhrase(new Phrase("Montant restant",fontTitle));

//        table.addCell(cell);
//        cell.setPhrase(new Phrase("Amortissement",fontTitle));

        table.addCell(cell);
        cell.setPhrase(new Phrase("Interet",fontTitle));

        table.addCell(cell);
        cell.setPhrase(new Phrase("Annuité",fontTitle));


            table.addCell(String.valueOf(credit.getMontant()));
            table.addCell(String.valueOf(credit.getTauxInteret()));
            table.addCell(String.valueOf(credit.getMonthlyPayment()));

document.add(table);

document.close();
    }



//
//
//    @Override
//    public double CalculActifCredit() {
//
//        //recuperation de la date du jour
//        java.util.Date currentDate = new java.util.Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(currentDate);
//
//        //recuperation de l'anne
//        int year = calendar.get(Calendar.YEAR);
//
//        double actif = 0;
//
//        //recuperation des credits prises cette annee
//        List<Credit> creditdeAnnee = repository.creditParAnnee(year);
//
//        for (Credit c : creditdeAnnee) {
//            double test = 0;
//            System.out.println("idUser=" + c.getUserCR().getId());
//            System.out.println("annee=" + year);
//
//            List<TransactionCredit> transaction = repot.findTransactionsByUserAndYear(c.getUserCR().getId(),year);
//            System.out.println(transaction);
//            if (!transaction.isEmpty()) {
//                test = repot.sumDepositsForUserAndYear(c.getUserCR().getId(), year);
//            }
//
//            actif += (c.getAnnuité() * c.getDuree() ) - test; // a revoir la formule
//
//        }
//        System.out.println("actif:"+actif);
//        return actif;
//    }
//
//    @Override
//    public double calcul_Rentabilite_parCreditNonActualise(Credit c) {
//
//        // calcul du taux d'interet par mois
//        //1+ pour le montant initial
//        //on ajout taux de croissance / rendement a ce moment
//        //on soustarit 1 pour obtenir le montant de croissance
//        double tauxparmois = Math.pow(1 + c.getTauxInteret(), 0.08333333) - 1;
//        //valeur futur du credit
//        double S = Math.pow(1 + tauxparmois, c.getDuree() * 12);
//        double Sa = (S - 1) / tauxparmois;
//
//        //calcul du montant total a rembourser
//        double produit = c.getPaiementMensuel() * Sa;
//
//        //rentabilite du credit non actualisé ( benefice net)
//        return produit - c.getMontant();
//    }
//
//    @Override
//    public double calcul_Rentabilite_parCredit(Credit c) {
//
//        double Taux_Actualisation = 0.1;
//        double Taux_Actualisationparmois = Math.pow(1 + Taux_Actualisation, 0.08333333) - 1;
//        double tauxparmois = Math.pow(1 + c.getTauxInteret(), 0.08333333) - 1;
//        double S = Math.pow(1 + tauxparmois, c.getDuree() * 12);
//        double tauxdActualisationConverti = Math.pow(1 + Taux_Actualisationparmois, c.getDuree() * 12);
//        double Sa = (S - 1) / tauxparmois;
//        double cap = 1 / tauxdActualisationConverti;
//
//        double produit = c.getPaiementMensuel() * Sa * cap;
//
//        return produit - c.getMontant();
//
//    }
//


//
//    @Override
//    public String SimulateurCredit(Credit credit,String S){
//        repository.save(credit);
//
//        float MontantCredit=credit.getMontant();
//        double taux =credit.getTauxInteret()   ;
//        //int nbmois=c.getDuree()*12;
//        double[] matrice = new double[4];
//        //double tauxMensuel = taux / 12;
//        double Annuite=calculRemboursementAnnuite(credit.getId());
//        //double mensualite = (MontantCredit * tauxMensuel) / (1 - Math.pow(1 + tauxMensuel, -nbmois));
//
//        S="Le Montant est :"+ MontantCredit+"Taux d'intérêt annuel : " + taux+ "Coût total  : " + (Annuite - MontantCredit);
////       // matrice[0]=MontantCredit;
//        //System.out.println("Taux d'intérêt annuel : " + taux);
////       // matrice[1]=taux;
////        System.out.println("Annuité : " + Annuite);
//       // matrice[2]=Annuite;
//   //     System.out.println("Coût total  : " + (Annuite - MontantCredit));
////       // matrice[3]=(Annuite - MontantCredit);
//        return S;
//    }
//

//
//    @Override
//    public File genererCreditPDF(int id) throws IOException, DocumentException {
//        Credit c=getCreditById(id);
//        User user=c.getUserCR();
//        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("credit.pdf"));
//
//        // Ajouter le header
//        HeaderFooter header = new HeaderFooter(new Phrase("FundHub"), false);
//        header.setAlignment(Element.ALIGN_CENTER);
//        document.setHeader(header);
//
//        // Ajouter le footer
//        Font FooterFont = new Font(Font.HELVETICA, 7, Font.NORMAL);
//
//        Paragraph footerText = new Paragraph("Le résultat de cette simulation revêt un caractère strictement informatif.\n"
//                ,FooterFont);
//
//        footerText.setSpacingBefore(10f); // ajoute un espace de 10 points avant le texte du footer
//
//        HeaderFooter footer = new HeaderFooter(footerText, new Phrase(" - Page ",FooterFont));
//        footer.setAlignment(Element.ALIGN_LEFT);
//        document.setFooter(footer);
//
//
//        // Ouvrir le document
//        document.open();
//
//        // Ajouter le logo
//        Image logo = Image.getInstance("logo_MicroGrowth.png");
//        logo.scaleAbsolute(100f, 100f);
//        logo.setAlignment(Element.ALIGN_CENTER);
//        document.add(logo);
//
//         //Ajouter les informations du contrat
//        double max = MaxCredit(id);
//        Font boldFont = new Font(Font.HELVETICA, 16, Font.BOLD);
//        Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
//        Paragraph montantMax = new Paragraph("Montant maximum : " + max + " dinars", boldFont);
//        montantMax.setSpacingAfter(20f);
//        document.add(montantMax);
//
//        Paragraph mensualite = new Paragraph("Mensualité : " + user.getSalaire()*0.43 + " dinars");
//        mensualite.setSpacingAfter(20f);
//        document.add(mensualite);
//
//        Paragraph duree = new Paragraph("Durée : " + c.getDuree()*12 + " mois");
//        duree.setSpacingAfter(20f);
//        document.add(duree);
//
//        Paragraph taux = new Paragraph("Taux d'intérêt : 0.23%");
//        taux.setSpacingAfter(20f);
//        document.add(taux);
//
//
//        // Fermer le document
//        document.close();
//        return null;
//    }
//
//    @Override
//    public float[][] calcul_tableau_credit(int id) {
//        Credit c=getCreditById(id);
//        float amt, interest, monthly_payment;
//        float[][] matrice = new float[c.getDuree() * 12][4];
//
//        matrice[0][0] = c.getMontant();
//        matrice[0][1] = (matrice[0][0] * c.getTauxInteret())/ 12;
//        matrice[0][2] = matrice[0][0] / (c.getDuree() * 12);
//        amt = matrice[0][2];
//
//        matrice[0][3] = amt + matrice[0][1];
//
//        for (int j = 1; j < c.getDuree() * 12; j++) {
//            matrice[j][0] = matrice[j - 1][0] - amt;//montant
//            matrice[j][1] = (matrice[j][0] * c.getTauxInteret()) / 12;//interet
//            matrice[j][2] = matrice[0][0] / (c.getDuree() * 12);//amt
//            matrice[j][3] = amt + matrice[j][1];//mensualite
//
//            if (j == c.getDuree() * 12 - 1) {
//                // Dernier paiement
//                if (matrice[j][0] != 0) {
//                    matrice[j][3] += matrice[j][0];
//                    amt += matrice[j][0];
//                    matrice[j][0] = 0;
//                    matrice[j][2] = 0;
//                }
//            } else {
//                amt = matrice[0][2];
//            }
//        }
//
//        return matrice;
//    }
//
//
//
}