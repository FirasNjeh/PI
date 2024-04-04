package esprit.pi.demo.Controller;

import ch.qos.logback.core.model.Model;
import com.google.zxing.WriterException;
import esprit.pi.demo.Services.ICreditService;
import esprit.pi.demo.entities.Credit;
import esprit.pi.demo.entities.Enumeration.PackCredit;
import esprit.pi.demo.entities.Enumeration.StatusCredit;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/Credit")

public class CreditController {
    private ICreditService service;

    @PostMapping("/add/{idp}/{idg}/{idu}")
    public Credit addCredit(@PathVariable int idp, @PathVariable int idg, @PathVariable int idu, @RequestBody Credit credit){
        return service.saveCredit(idp, idg, idu,credit);
    }

    @GetMapping("/all")
    public List<Credit> findAllCredits(){
        return service.getCredits();
    }

    @GetMapping("/{id}")
    public Credit findCreditById(@PathVariable int id){
        return service.getCreditById(id);
    }

    @PutMapping("/update/{id}")
    public Credit updateCredit(@PathVariable int id,@RequestBody Credit credit) {
        return service.updateCredit(id,credit);
    }

    @GetMapping("/get/statut/{statusCredit}")
    public List<Credit> getCreditsByStatus(@PathVariable StatusCredit statusCredit) {
        return service.getCreditsByStatus(statusCredit);
    }

    @GetMapping("/get/montant/{montant}")
    public List<Credit> getCreditsByMontant(@PathVariable float montant) {
        return service.getCreditsByMontant(montant);
    }

    @GetMapping("/get/duree/{duree}")
    public List<Credit> getCreditsByDuree(@PathVariable int duree) {
        return service.getCreditsByDuree(duree);
    }
@GetMapping("/Montant /less/{montant}")
    public List<Credit> getCreditByMontantLess(@PathVariable float montant) {
        return service.getCreditByMontantLess(montant);
    }
@GetMapping("/Montant/Greater/{montant}")
    public List<Credit> getCreditByMontantMore(@PathVariable float montant) {
        return service.getCreditByMontantMore(montant);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCredit(@PathVariable int id){
        return service.deleteCredit(id);
    }

    @GetMapping("/RemboursementAnnuite/{id}")
    public float calculRemboursementAnnuite(@PathVariable int id) {
        return service.calculRemboursementAnnuite(id);
    }

    @GetMapping("filtrage/{field}/{value}")
    public List<Credit> getCreditsFiltrage(@PathVariable String field,@PathVariable String value) {
        return service.getCreditsFiltrage(field, value);
    }
    @GetMapping("tri/{field}/{value}")
    public List<Credit> getCreditsTrie(@PathVariable String field,@PathVariable String value) {
        return service.getCreditsTrie(field, value);
    }

    @GetMapping("/currency/{convertTo}/{quantity}")
    public BigDecimal Convert(@PathVariable String convertTo, @PathVariable BigDecimal quantity) throws IOException {
        return service.Currency(convertTo,quantity);
    }

    @GetMapping("/MaxCredit/{id}")
    public double MaxCredit(@PathVariable int id) {
        return service.MaxCredit(id);
    }

    @GetMapping("/Scoring/{id}")
    public int scoreCredit(@PathVariable int id) {
        return service.scoreCredit(id);
    }
    @GetMapping("/interestRate/{id}")
    public float calculateInterestRate(@PathVariable int id) {
        return service.calculateInterestRate(id);
    }

    @GetMapping("simulateur/{MontantCredit}/{nbmois}")
    public double[] SimulateurCredit(@PathVariable float MontantCredit, @PathVariable int nbmois) {
        return service.SimulateurCredit(MontantCredit, nbmois);
    }

    @GetMapping("/tableau_credit/{id}")
    public float[][] calcul_tableau_credit(@PathVariable int id) {
        return service.calcul_tableau_credit(id);
    }


    @GetMapping("/QRCode/{id}")
    public String getQRCode(Model model,@PathVariable int id){
        String credit="http://localhost:8081/MonthlyPayment/MonthlyPayment/creditNum/"+id;
        String QR_CODE_IMAGE_PATH = "./Assets/images/QR/Credit_"+id+".png";


        byte[] image = new byte[0];
        try {
            // Generate and Return Qr Code in Byte Array
            image = service.getQRCodeImage(credit,250,250);

            // Generate and Save Qr Code Image in static/image folder
            service.generateQRCodeImage(credit,250,250,QR_CODE_IMAGE_PATH);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);

        return "qrcode généré avec succès";
    }

    //Statistiques


    @GetMapping("/Stat/NbrCredit")
    public int NbrCredit() {
        return service.NbrCredit();
    }

    @GetMapping("/Stat/NbrCredit/Pack")
    public int NbrCreditPack(@PathVariable PackCredit pack) {
        return service.NbrCreditPack(pack);
    }

    @GetMapping("/Stat/NbrCredit/Cloture")
    public int NbrCreditCloture() {
        return service.NbrCreditCloture();
    }

    @GetMapping("/Stat/MostDemandedPack")
    public PackCredit mostDemandedPack() {
        return service.mostDemandedPack();
    }

    @GetMapping("/Stat/LeastDemandedPack")

    public PackCredit LeastDemandedPack() {
        return service.LeastDemandedPack();
    }

    @GetMapping("/Stat/TotalLoan")
    public Float TotalLoan() {
        return service.TotalLoan();
    }

    @GetMapping("/Stat/NbrRetard")
    public double calculateDefaultRate() {
        return service.calculateDefaultRate();
    }
}

