package esprit.pi.demo.Services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import esprit.pi.demo.Repository.CreditRepository;
import esprit.pi.demo.Repository.TransactionCRepository;
import esprit.pi.demo.Repository.UserRepository;
import esprit.pi.demo.entities.*;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


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

        float montant = credit.getMontant();
        repository.save(credit);
    //si credit approuvé
        if((u.getNbr_credit()<=2) &&(montant<MaxCredit(credit.getId())) && (montant<pa.getPackCRById(idp).getMontantMax()))
        {
        credit.setStatusCredit(StatusCredit.APPROUVE);


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
        double taux =c.getTauxInteret();
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

    @Override
    public double[] SimulateurCredit(float MontantCredit , int nbmois){
        double taux = 0.23  ;
        double[] matrice = new double[4];
//            float salaire=iUser.getUserByEmail(iMicroGrowth.getCurrentUserName()).getSalaire();

        double tauxMensuel = taux / 12;
        double mensualite = (MontantCredit * tauxMensuel) / (1 - Math.pow(1 + tauxMensuel, -nbmois));
        System.out.println("Le Montant est :"+ MontantCredit);
        matrice[0]=MontantCredit;
        System.out.println("Taux d'intérêt annuel : " + taux);
        matrice[1]=taux;
        System.out.println("Mensualité : " + mensualite);
        matrice[2]=mensualite;;
        System.out.println("Duree :"+ nbmois/12 + " années");
        matrice[3]=(nbmois/12);
        return matrice;
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


    @Override
    public float[][] calcul_tableau_credit(int id) {
        Credit c=getCreditById(id);
        float amt, interest, monthly_payment;
        float[][] matrice = new float[c.getDuree() * 12][4];

        matrice[0][0] = c.getMontant(); // montant
        matrice[0][1] = (matrice[0][0] * c.getTauxInteret())/ 12; //montant d'interet mensuel
        matrice[0][2] = matrice[0][0] / (c.getDuree() * 12); // montant a payer mensuellement
        amt = matrice[0][2];

        matrice[0][3] = amt + matrice[0][1]; // paiement mensuel total

        for (int j = 1; j < c.getDuree() * 12; j++) //boucle sur tous les mois
        {
            matrice[j][0] = matrice[j - 1][0] - amt;//montant
            matrice[j][1] = (matrice[j][0] * c.getTauxInteret()) / 12;//interet
            matrice[j][2] = matrice[0][0] / (c.getDuree() * 12);//amt
            matrice[j][3] = amt + matrice[j][1];//mensualite

            if (j == c.getDuree() * 12 - 1) {
                // Dernier paiement
                if (matrice[j][0] != 0) {
                    matrice[j][3] += matrice[j][0];
                    amt += matrice[j][0];
                    matrice[j][0] = 0;
                    matrice[j][2] = 0;
                }
            } else {
                amt = matrice[0][2];
            }
        }

        return matrice;
    }

    @Override
    public BigDecimal Currency(String convertTo, BigDecimal quantity) throws IOException {


        String urlString = "https://api.fxratesapi.com/latest?base=TND&api_key=fxr_live_afa4bfa16fe5a0285657bd81c9d3d406c8ca";
        //"https://api.fxratesapi.com/convert?from=" +convertFrom +"&to=" +convertTo+" &amount="+quantity+"api_key=fxr_live_afa4bfa16fe5a0285657bd81c9d3d406c8ca";


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String stringResponse = response.body().string();
        JSONObject jsonObject = new JSONObject(stringResponse);
        JSONObject ratesObject = jsonObject.getJSONObject("rates");
        BigDecimal rate = ratesObject.getBigDecimal(convertTo.toUpperCase());

        BigDecimal result = rate.multiply(quantity);
        System.out.println(result);
        return(result);

    }
    @Override
    public void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    @Override
    public byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig( 0xFF000002 , 0xFFFFC041 ) ;

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    //statistiques
    @Override
    public int NbrCredit() {
        return repository.countByStatusCreditEquals(StatusCredit.APPROUVE);
    }

    @Override
    public int NbrCreditPack(PackCredit pack) {
        return repository.countByPackCredit(pack);
    }

    @Override
    public int NbrCreditCloture() {
        return repository.countByStatusCreditEquals(StatusCredit.CLOTURE);
    }

    @Override
    public PackCredit mostDemandedPack() {
        List<Credit> allCredits = repository.findAll();

        Map<PackCredit, Long> packCountMap = new HashMap<>();
        for (Credit credit : allCredits) {
            PackCredit pack = credit.getPackCredit();
            packCountMap.put(pack, packCountMap.getOrDefault(pack, 0L) + 1);
        }

        // Trouver le pack avec le nombre le plus élevé
        PackCredit mostDemandedPack = null;
        long maxCount = 0;
        for (Map.Entry<PackCredit, Long> entry : packCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostDemandedPack = entry.getKey();
            }
        }

        return mostDemandedPack;
    }

    @Override
    public Float TotalLoan() {
        List<Credit> allCredits = repository.findAll();
        Float creditAmounts = 0.0f;

        for (Credit credit : allCredits) {
            creditAmounts+=credit.getMontant();
        }
        return creditAmounts;
    }

    @Override
    public double calculateDefaultRate() {
        List<Credit> allCredits = repository.findAll();
        int totalCredits = allCredits.size();
        int latePayments = 0;

        for (Credit credit : allCredits) {
            if (credit.getLateTimes() > 0) {
                latePayments++;
            }
        }

        if (totalCredits == 0) {
            return 0.0; // Pour éviter une division par zéro
        } else {
            return (double) latePayments / totalCredits * 100;
        }
    }







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



}