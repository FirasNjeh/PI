package esprit.pi.demo.pi_back.Controller;

import esprit.pi.demo.pi_back.Entities.PriorityLevel;
import esprit.pi.demo.pi_back.Entities.Reclamation;
import esprit.pi.demo.pi_back.Services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;
    @PostMapping("/addReclamation")
    public Reclamation addReclamation(@RequestBody Reclamation reclamation){
        return reclamationService.saveReclamation(reclamation);
    }
    @PostMapping("/addReclamations")
    public List<Reclamation> addReclamations(@RequestBody List<Reclamation> reclamations){
        return reclamationService.saveReclamations(reclamations);
    }
    @GetMapping("/Reclamations")
    public List<Reclamation> findAllReclamations(){
        return reclamationService.getReclamations();
    }
    @GetMapping("/ReclamationById/{id}")
    public Reclamation findReclamationById(@PathVariable int id){
        return reclamationService.getReclamationById(id);
    }
    @GetMapping("/ReclamationByPriority/{priorityLevel}")
    public Reclamation findReclamationByPriorityLevel(@PathVariable PriorityLevel priorityLevel){
        return reclamationService.getReclamationByPriorityLevel(priorityLevel);
    }
    @PutMapping("/updateReclamation")
    public Reclamation updateReclamation(@RequestBody Reclamation reclamation){
        return reclamationService.updateReclamation(reclamation);
    }
    @DeleteMapping("/deleteReclamation/{id}")
    public String deleteReclamation(@PathVariable int id){
        return reclamationService.deleteReclamation(id);
    }



}
