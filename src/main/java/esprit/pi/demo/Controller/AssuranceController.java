package esprit.pi.demo.Controller;


import esprit.pi.demo.Services.IAssurance;
import esprit.pi.demo.entities.Assurance;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import esprit.pi.demo.dto.AgricoleAssuranceDTO;
import esprit.pi.demo.dto.EntrepreneurAssuranceDTO;
import esprit.pi.demo.dto.SanteAssuranceDTO;
import esprit.pi.demo.dto.ScolaireAssuranceDTO;
import java.util.List;
import java.util.Set;


@RestController
@AllArgsConstructor
@RequestMapping("/Assurance")

public class AssuranceController {

    private IAssurance service;
//    @PostMapping("/addAssurance")
//    public Assurance addAssurance(@RequestBody Assurance assurance)
//    {
//    return service.saveAssurance(assurance);
//    }
    @Operation(description = "Afficher toutes les assurances")
    @GetMapping("/Assurances")
    public List<Assurance> findAllAssurances(){
        return service.getAssurance();}
    @GetMapping("/Assurance/{id}")
    public Assurance findAssuranceById(@PathVariable int id) {
        return service.getAssuranceById(id);

    }
    // @PutMapping("/update")
    //    public Assurance updateAssurance(@RequestBody int id ,Assurance assurance){
    //        return service.updateAssurance(id,assurance);
    // }
    @DeleteMapping("/delete/{id}")
    public String deleteAssurance(@PathVariable int id) {
        return service.deleteAssurance(id);
    }

    @GetMapping("/getByPackAssur/{idpack}")
    public ResponseEntity<Set<Assurance>> getListAssurancesByPackAssur(@PathVariable int idpack) {
        Set<Assurance> assurances = service.getListAssurancesByPackAssur(idpack);
        return ResponseEntity.ok(assurances);
    }
    @GetMapping("/getByUser/{iduser}")
    public ResponseEntity<Set<Assurance>> getListAssurancesByUser(@PathVariable int iduser) {
        Set<Assurance> assurances = service.getListAssurancesByUser(iduser);
        return ResponseEntity.ok(assurances);
    }

//    @PostMapping("/createWithPackAssur/{packId}")
//    public ResponseEntity<Assurance> createAssuranceWithPackAssur( @PathVariable int packId, @RequestBody Assurance assurance) {
//        Assurance createdAssurance = service.createAssuranceWithPackAssur(packId, assurance);
//        return ResponseEntity.ok(createdAssurance);
//    }

    @PostMapping("/create-scolaire-assurance/{packId}")
    public Assurance createScolaireAssurance(@PathVariable int packId, @RequestBody ScolaireAssuranceDTO scolaireAssuranceDTO) {
        return service.createScolaireAssurance(packId, scolaireAssuranceDTO);
    }

    @PostMapping("/create-entrepreneur-assurance/{packId}")
    public Assurance createEntrepreneurAssurance(@PathVariable int packId, @RequestBody EntrepreneurAssuranceDTO entrepreneurAssuranceDTO) {
        return service.createEntrepreneurAssurance(packId, entrepreneurAssuranceDTO);
    }

    @PostMapping("/create-sante-assurance/{packId}")
    public Assurance createSanteAssurance(@PathVariable int packId, @RequestBody SanteAssuranceDTO santeAssuranceDTO) {
        return service.createSanteAssurance(packId, santeAssuranceDTO);
    }

    @PostMapping("/create-agricole-assurance/{packId}")
    public Assurance createAgricoleAssurance(@PathVariable int packId, @RequestBody AgricoleAssuranceDTO agricoleAssuranceDTO) {
        return service.createAgricoleAssurance(packId, agricoleAssuranceDTO);
    }

    @PostMapping("/createWithPackAndUser")
    public ResponseEntity<Assurance> createAssuranceWithPackAndUser(
            @RequestParam("userId") int userId,
            @RequestParam("packId") int packId,
            @RequestBody Assurance assurance) {

        Assurance createdAssurance = service.createAssuranceWithPackAssurandUser(userId, packId, assurance);
        return new ResponseEntity<>(createdAssurance, HttpStatus.CREATED);
    }
}
