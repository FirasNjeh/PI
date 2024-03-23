package esprit.pi.demo.Controller;

import esprit.pi.demo.Repository.CreditRepository;
import esprit.pi.demo.Services.ICreditService;
import esprit.pi.demo.entities.Credit;
import esprit.pi.demo.entities.StatusCredit;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Credit")

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

//    @GetMapping("/pdf/{id}")
//    public void export(HttpServletResponse response,@PathVariable int id) throws IOException, DocumentException {
//    }

    @GetMapping("filtrage/{field}/{value}")
    public List<Credit> getCreditsFiltrage(@PathVariable String field,@PathVariable String value) {
        return service.getCreditsFiltrage(field, value);
    }
    @GetMapping("tri/{field}/{value}")
    public List<Credit> getCreditsTrie(@PathVariable String field,@PathVariable String value) {
        return service.getCreditsTrie(field, value);
    }
//    @PostMapping("/simulateur")
//    public String SimulateurCredit( Credit credit,@RequestBody String S){
//        return service.SimulateurCredit(credit,S);
//    }


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

    @GetMapping("/pdf/generateAmortissement/{id}")
    public void generatePdf(HttpServletResponse response, @PathVariable int id) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey ="Content-Disposition";
        String headerValue ="attachment; filename=Tableau_Credit_N_"+id+".pdf";
        response.setHeader(headerKey, headerValue);
        ICreditService.export(response,id);
    }
        CreditRepository repo;
    
//    @GetMapping("/admin/export-to-pdf-credits")
//    public void generatePdfFile(HttpServletResponse response) throws DocumentException, IOException
//    {
//        response.setContentType("application/pdf");
//        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
//        String currentDateTime = dateFormat.format(new Date());
//        String headerkey = "Content-Disposition";
//        String headervalue = "attachment; filename=credit" + currentDateTime + ".pdf";
//        response.setHeader(headerkey, headervalue);
//        List < Credit > listofCredits = repo.findAll();
//        PdfGeneratorCredit generator = new PdfGeneratorCredit();
//
//        generator.generate(listofCredits, (javax.servlet.http.HttpServletResponse) response);
//    }


}

