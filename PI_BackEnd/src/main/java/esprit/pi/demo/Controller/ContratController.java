package esprit.pi.demo.Controller;

import esprit.pi.demo.entities.FactureFC;
import esprit.pi.demo.entities.Enumeration.Risque;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import esprit.pi.demo.entities.Contrat;
import esprit.pi.demo.Services.IContratService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/contrat")
public class ContratController {
    IContratService contratService;

    // http://localhost:8085/factoringpi/contrat/retrieve-all-contrats
    @GetMapping("/retrieve-all-contrats")
    public List<Contrat> getContrats(){
        List<Contrat> listContrats = contratService.retrieveAllContrats();
        return listContrats;
    }

    // http://localhost:8085/factoringpi/contrat/retrieve-contrat/8
    @GetMapping("/retrieve-contrat/{contrat-id}")
    public Contrat retrieveContrat(@PathVariable("contrat-id") Long contratId){
        Contrat contrat = contratService.retrieveContrat(contratId);
        return contrat;
    }

    // http://localhost:8085/factoringpi/contrat/add-contrat
    @PostMapping("/add-contrat")
    public Contrat addContrat(@RequestBody Contrat c){
        float totalFactures=0;
        for (FactureFC f : c.getFactureFCS()){
            totalFactures += f.getMontant();
        }
        c.setMontantTotalFactures(totalFactures);
        double tauxI=0;
        tauxI = 0.15
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteImpayes(c.getImpayesAdherent()), Risque.CATASTROPHIQUE.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteEndettement(c.getEndettementAdherent(),c.getCapitalAdherent()), Risque.MODERE.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteECLTSCA(c.getEncoursCLTSAdherent(),c.getCaAdherent()), Risque.MAJEUR.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteTauxAvance(c.getTauxAvance()), Risque.MODERE.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteFR(c.getFrAdherent()), Risque.CATASTROPHIQUE.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteBFR(c.getBfrAdherent()), Risque.MODERE.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteImpayes(c.getImpayesAcheteurs()), Risque.CATASTROPHIQUE.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteEndettement(c.getEndettementAcheteur(),c.getCapitalAcheteur()), Risque.MAJEUR.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteEFRSCA(c.getEncoursFRSAcheteur(),c.getCaAcheteur()), Risque.MAJEUR.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteFR(c.getFrAcheteur()), Risque.CATASTROPHIQUE.ordinal())))
                + contratService.attribution(contratService.scoring(contratService.taux(contratService.noteBFR(c.getBfrAcheteur()), Risque.MODERE.ordinal())));
        c.setTauxInteret(tauxI);
        Contrat contrat = new Contrat();
        if(tauxI <= 0.21)
            contrat = contratService.addContrat(c);
        return contrat;
    }

    // http://localhost:8085/factoringpi/contrat/remove-contrat/{contrat-id}
    @DeleteMapping("/remove-contrat/{contrat-id}")
    public void removeContrat(@PathVariable("contrat-id") Long contratId){
        contratService.removeContrat(contratId);
    }

    // http://localhost:8085/factoringpi/contrat/modify-contrat
    @PostMapping("/modify-contrat")
    public Contrat modifyContrat(@RequestBody Contrat c){
        Contrat contrat = contratService.modifyContrat(c);
        return contrat;
    }

    // http://localhost:8085/factoringpi/contrat/retrieve-all-contrats-sorted-by-referenceContratAsc
    @GetMapping("/retrieve-all-contrats-sorted-by-referenceContratAsc")
    public List<Contrat> getContratsSortedByReferenceContratAsc(){
        List<Contrat> listContrats = contratService.retrieveAllContratsSortedByReferenceContratAsc();
        return listContrats;
    }

    // http://localhost:8085/factoringpi/contrat/retrieve-all-contrats-sorted-by-referenceContratDesc
    @GetMapping("/retrieve-all-contrats-sorted-by-referenceContratDesc")
    public List<Contrat> getContratsSortedByReferenceContratDesc(){
        List<Contrat> listContrats = contratService.retrieveAllContratsSortedByReferenceContratDesc();
        return listContrats;
    }

    // http://localhost:8085/factoringpi/contrat/retrieve-all-contrats-sorted-by-montantTotalFacturesAsc
    @GetMapping("/retrieve-all-contrats-sorted-by-montantTotalFacturesAsc")
    public List<Contrat> getContratsSortedByMontantTotalFacturesAsc(){
        List<Contrat> listContrats = contratService.retrieveAllContratsSortedByMontantTotalFacturesAsc();
        return listContrats;
    }

    // http://localhost:8085/factoringpi/contrat/retrieve-all-contrats-sorted-by-montantTotalFacturesDesc
    @GetMapping("/retrieve-all-contrats-sorted-by-montantTotalFacturesDesc")
    public List<Contrat> getContratsSortedByMontantTotalFacturesDesc(){
        List<Contrat> listContrats = contratService.retrieveAllContratsSortedByMontantTotalFacturesDesc();
        return listContrats;
    }

    // http://localhost:8085/factoringpi/contrat/retrieve-all-contrats-sorted-by-acheteurAsc
    @GetMapping("/retrieve-all-contrats-sorted-by-acheteurAsc")
    public List<Contrat> getContratsSortedByAcheteurAsc(){
        List<Contrat> listContrats = contratService.retrieveAllContratsSortedByAcheteurAsc();
        return listContrats;
    }

    // http://localhost:8085/factoringpi/contrat/retrieve-all-contrats-sorted-by-acheteurDesc
    @GetMapping("/retrieve-all-contrats-sorted-by-acheteurDesc")
    public List<Contrat> getContratsSortedByAcheteurDesc(){
        List<Contrat> listContrats = contratService.retrieveAllContratsSortedByAcheteurDesc();
        return listContrats;
    }
}
