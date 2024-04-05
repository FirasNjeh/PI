package esprit.pi.demo.pi_back.Controller;

import esprit.pi.demo.pi_back.Entities.PriorityLevel;
import esprit.pi.demo.pi_back.Entities.Reclamation;
import esprit.pi.demo.pi_back.Entities.StatusRC;
import esprit.pi.demo.pi_back.Services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;
    @PostMapping("/addReclamation/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("object") String object,
                                              @RequestParam("description") String description,
                                              @RequestParam("statusRC") StatusRC statusRC,
                                              @RequestParam("dateRC") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateRC,
                                              @RequestParam("priorityLevel") PriorityLevel priorityLevel,
                                              @RequestParam("typeRC") String typeRC,
                                              @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            reclamationService.saveReclamationWithImage(object,description,statusRC,dateRC,priorityLevel,typeRC,file);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

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
