package esprit.pi.demo.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import esprit.pi.demo.entities.Adherent;
import esprit.pi.demo.Services.IAdherentService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/adherent")
public class AdherentController {
    IAdherentService adherentService;

    // http://localhost:8085/factoringpi/adherent/retrieve-all-adherents
    @GetMapping("/retrieve-all-adherents")
    public List<Adherent> getAdherents(){
        List<Adherent> listAdherents = adherentService.retrieveAllAdherents();
        return listAdherents;
    }

    // http://localhost:8085/factoringpi/adherent/retrieve-adherent/{adherent-id}
    @GetMapping("/retrieve-adherent/{adherent-id}")
    public Adherent retrieveAdherent(@PathVariable("adherent-id") Long adherentId){
        Adherent adherent = adherentService.retrieveAdherent(adherentId);
        return adherent;
    }

    // http://localhost:8085/factoringpi/adherent/add-adherent
    @PostMapping("/add-adherent")
    public Adherent addAdherent(@RequestBody Adherent a){
        Adherent adherent = adherentService.addAdherent(a);
        return adherent;
    }

    // http://localhost:8085/factoringpi/adherent/remove-adherent/{adherent-id}
    @DeleteMapping("/remove-adherent/{adherent-id}")
    public void removeAdherent(@PathVariable("adherent-id") Long adherentId){
        adherentService.removeAdherent(adherentId);
    }

    // http://localhost:8085/factoringpi/adherent/modify-adherent
    @PostMapping("/modify-adherent")
    public Adherent modifyAdherent(@RequestBody Adherent a){
        Adherent adherent = adherentService.modifyAdherent(a);
        return adherent;
    }

    // http://localhost:8085/factoringpi/adherent/retrieve-all-adherents-sorted-by-identifiantUniqueRNEAsc
    @GetMapping("/retrieve-all-adherents-sorted-by-identifiantUniqueRNEAsc")
    public List<Adherent> getAdherentsSortedByIdentifiantUniqueRNEAsc(){
        List<Adherent> listAdherents = adherentService.retrieveAllAdherentsSortedByIdentifiantUniqueRNEAsc();
        return listAdherents;
    }

    // http://localhost:8085/factoringpi/adherent/retrieve-all-adherents-sorted-by-identifiantUniqueRNEDesc
    @GetMapping("/retrieve-all-adherents-sorted-by-identifiantUniqueRNEDesc")
    public List<Adherent> getAdherentsSortedByIdentifiantUniqueRNEDesc(){
        List<Adherent> listAdherents = adherentService.retrieveAllAdherentsSortedByIdentifiantUniqueRNEDesc();
        return listAdherents;
    }

    // http://localhost:8085/factoringpi/adherent/retrieve-all-adherents-sorted-by-matriculeFiscaleAsc
    @GetMapping("/retrieve-all-adherents-sorted-by-matriculeFiscaleAsc")
    public List<Adherent> getAdherentsSortedByMatriculeFiscaleAsc(){
        List<Adherent> listAdherents = adherentService.retrieveAllAdherentsSortedByMatriculeFiscaleAsc();
        return listAdherents;
    }

    // http://localhost:8085/factoringpi/adherent/retrieve-all-adherents-sorted-by-matriculeFiscaleDesc
    @GetMapping("/retrieve-all-adherents-sorted-by-matriculeFiscaleDesc")
    public List<Adherent> getAdherentsSortedByMatriculeFiscaleDesc(){
        List<Adherent> listAdherents = adherentService.retrieveAllAdherentsSortedByMatriculeFiscaleDesc();
        return listAdherents;
    }

    // http://localhost:8085/factoringpi/adherent/retrieve-all-adherents-sorted-by-raisonSocialeAsc
    @GetMapping("/retrieve-all-adherents-sorted-by-raisonSocialeAsc")
    public List<Adherent> getAdherentsSortedByRaisonSocialeAsc(){
        List<Adherent> listAdherents = adherentService.retrieveAllAdherentsSortedByRaisonSocialeAsc();
        return listAdherents;
    }

    // http://localhost:8085/factoringpi/adherent/retrieve-all-adherents-sorted-by-raisonSocialeDesc
    @GetMapping("/retrieve-all-adherents-sorted-by-raisonSocialeDesc")
    public List<Adherent> getAdherentsSortedByRaisonSocialeDesc(){
        List<Adherent> listAdherents = adherentService.retrieveAllAdherentsSortedByRaisonSocialeDesc();
        return listAdherents;
    }

    // http://localhost:8085/factoringpi/adherent/retrieve-all-adherents-sorted-by-dateCreationAsc
    @GetMapping("/retrieve-all-adherents-sorted-by-dateCreationAsc")
    public List<Adherent> getAdherentsSortedByDateCreationAsc(){
        List<Adherent> listAdherents = adherentService.retrieveAllAdherentsSortedByDateCreationAsc();
        return listAdherents;
    }

    // http://localhost:8085/factoringpi/adherent/retrieve-all-adherents-sorted-by-dateCreationDesc
    @GetMapping("/retrieve-all-adherents-sorted-by-dateCreationDesc")
    public List<Adherent> getAdherentsSortedByDateCreationDesc(){
        List<Adherent> listAdherents = adherentService.retrieveAllAdherentsSortedByDateCreationDesc();
        return listAdherents;
    }
}
