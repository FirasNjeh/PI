package esprit.pi.demo.Controller;

import esprit.pi.demo.entities.FactureFC;
import esprit.pi.demo.entities.StatutFC;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import esprit.pi.demo.Services.EmailService;
import esprit.pi.demo.Services.IFactureFCService;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/facture")
public class FactureFCController {
    IFactureFCService factureService;
    EmailService emailService;

    // http://localhost:8085/factoringpi/facture/retrieve-all-factures
    @GetMapping("/retrieve-all-factures")
    public List<FactureFC> getFactures(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFactures();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/facture/retrieve-facture/8
    @GetMapping("/retrieve-facture/{facture-id}")
    public FactureFC retrieveFacture(@PathVariable("facture-id") Long factureId){
        FactureFC factureFC = factureService.retrieveFacture(factureId);
        return factureFC;
    }

    // http://localhost:8085/factoringpi/facture/add-facture
    @PostMapping("/add-facture")
    public FactureFC addFacture(@RequestBody FactureFC f){
        f.setStatut(StatutFC.EN_ATTENTE_DE_PAIEMENT);
        FactureFC factureFC = factureService.addFacture(f);
        return factureFC;
    }

    // http://localhost:8085/factoringpi/facture/remove-facture/{facture-id}
    @DeleteMapping("/remove-facture/{facture-id}")
    public void removeFacture(@PathVariable("facture-id") Long factureId){
        factureService.removeFacture(factureId);
    }

    // http://localhost:8085/factoringpi/facture/modify-facture
    @PostMapping("/modify-facture")
    public FactureFC modifyFacture(@RequestBody FactureFC f){
        FactureFC factureFC = factureService.modifyFacture(f);
        if(factureFC.getStatut()== StatutFC.PAYE){
            String message = "La factureFC "+ factureFC.getNumeroFacture()+" a été payée";
            emailService.sendEmail("youssef.rouissi@esprit.tn","FactureFC payée", message);
        }
        if(factureFC.getStatut()== StatutFC.PAYE_EN_RETARD) {
            String message = "La factureFC " + factureFC.getNumeroFacture() + " a été payée en retard";
            emailService.sendEmail("youssef.rouissi@esprit.tn", "FactureFC payée", message);
        }
        return factureFC;
    }

    // http://localhost:8085/factoringpi/facture/verifier-date-echeance
    @GetMapping("/verifier-date-echeance")
    public void verifierDateEcheance(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFactures();
            for(FactureFC f : listFactureFCS){
                if(f.getStatut()== StatutFC.EN_ATTENTE_DE_PAIEMENT && f.getDateEcheance().isBefore(LocalDate.now())){
                    String message = "La facture "+f.getNumeroFacture()+" doit être payée le "+f.getDateEcheance();
                    emailService.sendEmail("youssef.rouissi@esprit.tn","FactureFC payée", message);
                }
            }
    }

    // http://localhost:8085/factoringpi/facture/retrieve-all-factures-sorted-by-numeroFactureAsc
    @GetMapping("/retrieve-all-factures-sorted-by-numeroFactureAsc")
    public List<FactureFC> getFacturesSortedByNumeroFactureAsc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByNumeroFactureAsc();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/facture/retrieve-all-factures-sorted-by-numeroFactureDesc
    @GetMapping("/retrieve-all-factures-sorted-by-numeroFactureDesc")
    public List<FactureFC> getFacturesSortedByNumeroFactureDesc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByNumeroFactureDesc();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-dateEmissionAsc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-dateEmissionAsc")
    public List<FactureFC> getAcheteursSortedByDateEmissionAsc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByDateEmissionAsc();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-dateEmissionDesc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-dateEmissionDesc")
    public List<FactureFC> getAcheteursSortedByDateEmissionDesc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByDateEmissionDesc();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-dateEcheanceAsc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-dateEcheanceAsc")
    public List<FactureFC> getAcheteursSortedByDateEcheanceAsc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByDateEcheanceAsc();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-dateEcheanceDesc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-dateEcheanceDesc")
    public List<FactureFC> getAcheteursSortedByDateEcheanceDesc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByDateEcheanceDesc();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-montantAsc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-montantAsc")
    public List<FactureFC> getAcheteursSortedByMontantAsc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByMontantAsc();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-montantDesc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-montantDesc")
    public List<FactureFC> getAcheteursSortedByMontantDesc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByMontantDesc();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-acheteurAsc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-acheteurAsc")
    public List<FactureFC> getAcheteursSortedByAcheteurAsc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByAcheteurAsc();
        return listFactureFCS;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-acheteurDesc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-acheteurDesc")
    public List<FactureFC> getAcheteursSortedByAcheteurDesc(){
        List<FactureFC> listFactureFCS = factureService.retrieveAllFacturesSortedByAcheteurDesc();
        return listFactureFCS;
    }
}
