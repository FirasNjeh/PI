package esprit.pi.demo.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import esprit.pi.demo.entities.Acheteur;
import esprit.pi.demo.Services.IAcheteurService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/acheteur")
public class AcheteurController {
    IAcheteurService acheteurService;

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs
    @GetMapping("/retrieve-all-acheteurs")
    public List<Acheteur> getAcheteurs(){
        List<Acheteur> listAcheteurs = acheteurService.retrieveAllAcheteurs();
        return listAcheteurs;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-acheteur/8
    @GetMapping("/retrieve-acheteur/{acheteur-id}")
    public Acheteur retrieveAcheteur(@PathVariable("acheteur-id") Long acheteurId){
        Acheteur acheteur = acheteurService.retrieveAcheteur(acheteurId);
        return acheteur;
    }

    // http://localhost:8085/factoringpi/acheteur/add-acheteur
    @PostMapping("/add-acheteur")
    public Acheteur addAcheteur(@RequestBody Acheteur a){
        Acheteur acheteur = acheteurService.addAcheteur(a);
        return acheteur;
    }

    // http://localhost:8085/factoringpi/acheteur/remove-acheteur/{acheteur-id}
    @DeleteMapping("/remove-acheteur/{acheteur-id}")
    public void removeAcheteur(@PathVariable("acheteur-id") Long acheteurId){
        acheteurService.removeAcheteur(acheteurId);
    }

    // http://localhost:8085/factoringpi/acheteur/modify-acheteur
    @PostMapping("/modify-acheteur")
    public Acheteur modifyAcheteur(@RequestBody Acheteur a){
        Acheteur acheteur = acheteurService.modifyAcheteur(a);
        return acheteur;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-matriculeFiscaleAsc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-matriculeFiscaleAsc")
    public List<Acheteur> getAcheteursSortedByMatriculeFiscaleAsc(){
        List<Acheteur> listAcheteurs = acheteurService.retrieveAllAcheteursSortedByMatriculeFiscaleAsc();
        return listAcheteurs;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-matriculeFiscaleDesc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-matriculeFiscaleDesc")
    public List<Acheteur> getAcheteursSortedByMatriculeFiscaleDesc(){
        List<Acheteur> listAcheteurs = acheteurService.retrieveAllAcheteursSortedByMatriculeFiscaleDesc();
        return listAcheteurs;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-raisonSocialeAsc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-raisonSocialeAsc")
    public List<Acheteur> getAcheteursSortedByRaisonSocialeAsc(){
        List<Acheteur> listAcheteurs = acheteurService.retrieveAllAcheteursSortedByRaisonSocialeAsc();
        return listAcheteurs;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-raisonSocialeDesc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-raisonSocialeDesc")
    public List<Acheteur> getAcheteursSortedByRaisonSocialeDesc(){
        List<Acheteur> listAcheteurs = acheteurService.retrieveAllAcheteursSortedByRaisonSocialeDesc();
        return listAcheteurs;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-dateCreationAsc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-dateCreationAsc")
    public List<Acheteur> getAcheteursSortedByDateCreationAsc(){
        List<Acheteur> listAcheteurs = acheteurService.retrieveAllAcheteursSortedByDateCreationAsc();
        return listAcheteurs;
    }

    // http://localhost:8085/factoringpi/acheteur/retrieve-all-acheteurs-sorted-by-dateCreationDesc
    @GetMapping("/retrieve-all-acheteurs-sorted-by-dateCreationDesc")
    public List<Acheteur> getAcheteursSortedByDateCreationDesc(){
        List<Acheteur> listAcheteurs = acheteurService.retrieveAllAcheteursSortedByDateCreationDesc();
        return listAcheteurs;
    }
}
