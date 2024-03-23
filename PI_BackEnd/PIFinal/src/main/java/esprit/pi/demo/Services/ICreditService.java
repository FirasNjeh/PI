package esprit.pi.demo.Services;

import com.lowagie.text.DocumentException;
import esprit.pi.demo.entities.Credit;
import esprit.pi.demo.entities.StatusCredit;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ICreditService {
    Credit saveCredit(int idp,int idg, int idu,Credit credit);

    //Credit acceptCredit(int idc);

    List<Credit> getCredits();
    Credit getCreditById(int id);
    String deleteCredit (int id);
    Credit updateCredit(int id,Credit credit);
    List<Credit> getCreditsFiltrage(String field, String value);
    List<Credit> getCreditsByStatus(StatusCredit statusCredit);

    List<Credit> getCreditsByMontant(float montant);
    List<Credit> getCreditByMontantLess(float montant);
    List<Credit> getCreditByMontantMore(float montant);
    List<Credit> getCreditsByDuree(int duree);

    List<Credit> getCreditsTrie(String field, String value);

    List<Credit> getCreditsTriéMontantA();

    List<Credit>getCreditsTriéMontantD();

    List<Credit> getCreditsTriéMontantRA();

    List<Credit>getCreditsTriéMontantRD();

    List<Credit> getCreditsTriéDateDA();

    List<Credit>getCreditsTriéDateDD();

    float calculRemboursementAnnuite(int id);

    double MaxCredit(int id);

    int scoreCredit(int id);

    float calculateInterestRate(int id);

   // File genererCreditPDF(int nbmois) throws IOException, DocumentException;

    void export(HttpServletResponse response, int id) throws IOException, DocumentException;


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


    //void export(HttpServletResponse response, int id) throws IOException, DocumentException;

    //private UserRepository repo;
    //    @Override
    //    public int scoreCredit(int id) {
    //        int score = 0;
    //
    //        Credit credit = repository.findById(id).get();
    //        User user=repo.findByUserCR(credit.getUserCR()); //recuperation de l'utilisateur
    //
    //        float revenuMensuel = user.getSalaire();
    //        int ancienneteEmploi = user.getAncienneteEmploi();
    //        float montantPret = credit.getMontant();
    //        int dureePret = credit.getDuree();
    //        float ratioEndettement = credit.getTauxInteret();
    //
    //        //recuperation de cette année
    //        java.util.Date date_now = new java.util.Date();
    //        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    //        String yearString = yearFormat.format(date_now);
    //        int year1 = Integer.parseInt(yearString);
    //
    //        //recuperation de l'année de naissance
    //        SimpleDateFormat yearFormat2 = new SimpleDateFormat("yyyy");
    //        String yearString2 = yearFormat.format(user.getDateNaissance());
    //        int year2 = Integer.parseInt(yearString2);
    //
    //        // calcul de l'age
    //        int age = year1 - year2;
    //
    //        //historique de paiement
    //        int hist;
    //        if(credit.getCreditHistory().ordinal()==0 ||credit.getCreditHistory().ordinal()==1 ||credit.getCreditHistory().ordinal()==2)
    //        {hist=0;} else {hist=1;}
    //
    //
    //        //Critère 1: Revenu mensuel
    //        if (revenuMensuel >= 3000 && revenuMensuel <= 5000) {
    //            score += 20;
    //        } else if (revenuMensuel > 5000) {
    //            score += 40;
    //        }
    //
    //        //Critère 2: Ancienneté de l'emploi
    //        if (ancienneteEmploi >= 2 && ancienneteEmploi <= 5) {
    //            score += 10;
    //        } else if (ancienneteEmploi > 5) {
    //            score += 20;
    //        }
    //
    //        //Critère 3: Montant et durée du prêt
    //        if (montantPret >= 5000 && montantPret <= 10000 && dureePret <= 24) {
    //            score += 10;
    //        } else if (montantPret > 10000 && dureePret > 24) {
    //            score += 20;
    //        }
    //
    //        //Critère 4: Ratio d'endettement
    //        if (ratioEndettement < 0.3) {
    //            score += 20;
    //        } else if (ratioEndettement >= 0.3 && ratioEndettement <= 0.4) {
    //            score += 10;
    //        }
    //
    //        //Critère 6: Age
    //        if (age >= 25 && age <= 40) {
    //            score += 10;
    //        } else if (age > 40 && age <= 50) {
    //            score += 5;
    //        }
    //
    //        //Critère 8: Historique de crédit
    //        if (hist==0) {
    //            score += 10;
    //        }
    //
    //        return score;
    //    }
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
    //    @Override
    //    public float calculateInterestRate(Credit c) {
    //
    //        float baseInterestRate = 0.2f; // taux d'intérêt de base pour la microfinance
    //        float interestRate = baseInterestRate;
    //        User user = repo.findById(c.getUserCR().getId()).orElse(null);
    //        java.util.Date date_now = new java.util.Date();
    //        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    //        String yearString = yearFormat.format(date_now);
    //        int year1 = Integer.parseInt(yearString);
    //
    //        SimpleDateFormat yearFormat2 = new SimpleDateFormat("yyyy");
    //        String yearString2 = yearFormat.format(user.getDateNaissance());
    //        int year2 = Integer.parseInt(yearString2);
    //        // Vérification de l'âge du client
    //        int age = year1 - year2;
    //
    //        double ageFactor = 1.0;
    //        if (age < 25) {
    //            ageFactor = 1.1; // augmentation de 10% du taux d'intérêt pour les jeunes emprunteurs
    //        } else if (age >= 65) {
    //            ageFactor = 0.9; // diminution de 10% du taux d'intérêt pour les emprunteurs âgés
    //        }
    //        interestRate *= ageFactor;
    //
    //        // Vérification du niveau de revenu du client
    //        double income = user.getSalaire();
    //        double incomeFactor = 1.0;
    //        if (income < 1000) {
    //            incomeFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les emprunteurs à faible revenu
    //        } else if (income >= 1000) {
    //            incomeFactor = 0.8; // diminution de 20% du taux d'intérêt pour les emprunteurs à revenu élevé
    //        }
    //        interestRate *= incomeFactor;
    //
    //        // Vérification de l'historique de crédit du client
    //
    //        double creditHistoryFactor = 1.0;
    //        if (user.isEtat()==false) {
    //            creditHistoryFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les emprunteurs ayant déjà fait défaut
    //        }
    //        interestRate *= creditHistoryFactor;
    //
    //        // Vérification de la durée du prêt
    //        int loanTerm =c.getDuree();
    //        double loanTermFactor = 1.0;
    //        if (loanTerm < 2) {
    //            loanTermFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les prêts de courte durée
    //        } else if (loanTerm > 2) {
    //            loanTermFactor = 0.9; // diminution de 10% du taux d'intérêt pour les prêts de longue durée
    //        }
    //        interestRate *= loanTermFactor;
    //
    //        // Vérification du montant du prêt demandé par le client
    //        double loanAmount = c.getMontant();
    //        double loanAmountFactor = 1.0;
    //        if (loanAmount > 5000) {
    //            loanAmountFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les montants de prêt élevés
    //        }
    //        interestRate *= loanAmountFactor;
    //
    //        return interestRate;
    //
    //    }
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



    //private UserRepository repo;
    //    @Override
    //    public int scoreCredit(int id) {
    //        int score = 0;
    //
    //        Credit credit = repository.findById(id).get();
    //        User user=repo.findByUserCR(credit.getUserCR()); //recuperation de l'utilisateur
    //
    //        float revenuMensuel = user.getSalaire();
    //        int ancienneteEmploi = user.getAncienneteEmploi();
    //        float montantPret = credit.getMontant();
    //        int dureePret = credit.getDuree();
    //        float ratioEndettement = credit.getTauxInteret();
    //
    //        //recuperation de cette année
    //        java.util.Date date_now = new java.util.Date();
    //        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    //        String yearString = yearFormat.format(date_now);
    //        int year1 = Integer.parseInt(yearString);
    //
    //        //recuperation de l'année de naissance
    //        SimpleDateFormat yearFormat2 = new SimpleDateFormat("yyyy");
    //        String yearString2 = yearFormat.format(user.getDateNaissance());
    //        int year2 = Integer.parseInt(yearString2);
    //
    //        // calcul de l'age
    //        int age = year1 - year2;
    //
    //        //historique de paiement
    //        int hist;
    //        if(credit.getCreditHistory().ordinal()==0 ||credit.getCreditHistory().ordinal()==1 ||credit.getCreditHistory().ordinal()==2)
    //        {hist=0;} else {hist=1;}
    //
    //
    //        //Critère 1: Revenu mensuel
    //        if (revenuMensuel >= 3000 && revenuMensuel <= 5000) {
    //            score += 20;
    //        } else if (revenuMensuel > 5000) {
    //            score += 40;
    //        }
    //
    //        //Critère 2: Ancienneté de l'emploi
    //        if (ancienneteEmploi >= 2 && ancienneteEmploi <= 5) {
    //            score += 10;
    //        } else if (ancienneteEmploi > 5) {
    //            score += 20;
    //        }
    //
    //        //Critère 3: Montant et durée du prêt
    //        if (montantPret >= 5000 && montantPret <= 10000 && dureePret <= 24) {
    //            score += 10;
    //        } else if (montantPret > 10000 && dureePret > 24) {
    //            score += 20;
    //        }
    //
    //        //Critère 4: Ratio d'endettement
    //        if (ratioEndettement < 0.3) {
    //            score += 20;
    //        } else if (ratioEndettement >= 0.3 && ratioEndettement <= 0.4) {
    //            score += 10;
    //        }
    //
    //        //Critère 6: Age
    //        if (age >= 25 && age <= 40) {
    //            score += 10;
    //        } else if (age > 40 && age <= 50) {
    //            score += 5;
    //        }
    //
    //        //Critère 8: Historique de crédit
    //        if (hist==0) {
    //            score += 10;
    //        }
    //
    //        return score;
    //    }
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
    //    @Override
    //    public float calculateInterestRate(Credit c) {
    //
    //        float baseInterestRate = 0.2f; // taux d'intérêt de base pour la microfinance
    //        float interestRate = baseInterestRate;
    //        User user = repo.findById(c.getUserCR().getId()).orElse(null);
    //        java.util.Date date_now = new java.util.Date();
    //        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    //        String yearString = yearFormat.format(date_now);
    //        int year1 = Integer.parseInt(yearString);
    //
    //        SimpleDateFormat yearFormat2 = new SimpleDateFormat("yyyy");
    //        String yearString2 = yearFormat.format(user.getDateNaissance());
    //        int year2 = Integer.parseInt(yearString2);
    //        // Vérification de l'âge du client
    //        int age = year1 - year2;
    //
    //        double ageFactor = 1.0;
    //        if (age < 25) {
    //            ageFactor = 1.1; // augmentation de 10% du taux d'intérêt pour les jeunes emprunteurs
    //        } else if (age >= 65) {
    //            ageFactor = 0.9; // diminution de 10% du taux d'intérêt pour les emprunteurs âgés
    //        }
    //        interestRate *= ageFactor;
    //
    //        // Vérification du niveau de revenu du client
    //        double income = user.getSalaire();
    //        double incomeFactor = 1.0;
    //        if (income < 1000) {
    //            incomeFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les emprunteurs à faible revenu
    //        } else if (income >= 1000) {
    //            incomeFactor = 0.8; // diminution de 20% du taux d'intérêt pour les emprunteurs à revenu élevé
    //        }
    //        interestRate *= incomeFactor;
    //
    //        // Vérification de l'historique de crédit du client
    //
    //        double creditHistoryFactor = 1.0;
    //        if (user.isEtat()==false) {
    //            creditHistoryFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les emprunteurs ayant déjà fait défaut
    //        }
    //        interestRate *= creditHistoryFactor;
    //
    //        // Vérification de la durée du prêt
    //        int loanTerm =c.getDuree();
    //        double loanTermFactor = 1.0;
    //        if (loanTerm < 2) {
    //            loanTermFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les prêts de courte durée
    //        } else if (loanTerm > 2) {
    //            loanTermFactor = 0.9; // diminution de 10% du taux d'intérêt pour les prêts de longue durée
    //        }
    //        interestRate *= loanTermFactor;
    //
    //        // Vérification du montant du prêt demandé par le client
    //        double loanAmount = c.getMontant();
    //        double loanAmountFactor = 1.0;
    //        if (loanAmount > 5000) {
    //            loanAmountFactor = 1.2; // augmentation de 20% du taux d'intérêt pour les montants de prêt élevés
    //        }
    //        interestRate *= loanAmountFactor;
    //
    //        return interestRate;
    //
    //    }
    //
    //String SimulateurCredit(Credit credit,String S);

//    int scoreCredit(int id);
//
//    double CalculActifCredit();
//
//    double calcul_Rentabilite_parCreditNonActualise(Credit c);
//
//    double calcul_Rentabilite_parCredit(Credit c);
//
//    float calculateInterestRate(Credit c);

//    double[] SimulateurCredit(int id);
//
//    double MaxCredit(int id);
//
//    File genererCreditPDF(int id) throws IOException, DocumentException;
//
//    float[][] calcul_tableau_credit(int id);

    // List<float> calculamortisement(int id);
  // void export(HttpServletResponse response, int id) throws IOException, DocumentException;


   }
