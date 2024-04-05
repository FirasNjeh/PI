package esprit.pi.demo.pi_back.Controller;


import esprit.pi.demo.pi_back.Entities.ReponseReclamation;
import esprit.pi.demo.pi_back.Services.ReponseReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ReponseReclamationController {
    @Autowired
    private ReponseReclamationService reponseRCService;
    @PostMapping("/addReponseReclamation")
    public ReponseReclamation addReponseReclamation(@RequestBody ReponseReclamation reponseRC){
        return reponseRCService.saveReponseReclamation(reponseRC);
    }

    @PostMapping("/addReponseReclamations")
    public List<ReponseReclamation> addReponseReclamations(@RequestBody List<ReponseReclamation> reponsesRC){
        return reponseRCService.saveReponseReclamations(reponsesRC);
    }
    @GetMapping("/ReponseReclamations")
    public List<ReponseReclamation> findAllReponseReclamations(){
        return reponseRCService.getReponseReclamations();
    }
    @GetMapping("/ReponseReclamation/{id}")
    public ReponseReclamation findReponseReclamationById(@PathVariable int id){
        return reponseRCService.getReponseReclamationById(id);
    }

    @PutMapping("/updateReponseReclamation")
    public ReponseReclamation updateReponseReclamation(@RequestBody ReponseReclamation reponseRC){
        return reponseRCService.updateReponseReclamation(reponseRC);
    }
    @DeleteMapping("/deleteReponseReclamation/{id}")
    public String deleteReponseReclamation(@PathVariable int id){
        return reponseRCService.deleteReponseReclamation(id);
    }
}
