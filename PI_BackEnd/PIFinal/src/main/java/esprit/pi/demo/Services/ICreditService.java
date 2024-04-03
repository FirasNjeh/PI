package esprit.pi.demo.Services;

import com.google.zxing.WriterException;
import esprit.pi.demo.entities.Credit;
import esprit.pi.demo.entities.PackCredit;
import esprit.pi.demo.entities.StatusCredit;

import java.io.IOException;
import java.math.BigDecimal;
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

    double[] SimulateurCredit(float MontantCredit, int nbmois);

    float[][] calcul_tableau_credit(int id);

    BigDecimal Currency(String convertTo, BigDecimal quantity) throws IOException;

    void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException;

    byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException;

    //statistiques
    int NbrCredit();

    int NbrCreditPack(PackCredit pack);

    int NbrCreditCloture();

    PackCredit mostDemandedPack();

    PackCredit LeastDemandedPack();

    Float TotalLoan();

    double calculateDefaultRate();


//    double CalculActifCredit();
//
//    double calcul_Rentabilite_parCreditNonActualise(Credit c);
//
//    double calcul_Rentabilite_parCredit(Credit c);
// List<float> calculamortisement(int id);


   }
