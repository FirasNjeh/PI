package esprit.pi.demo.Controller;


import esprit.pi.demo.Services.IAssurance;
import esprit.pi.demo.entities.*;
import esprit.pi.demo.entities.Enumeration.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import esprit.pi.demo.DTO.AgricoleAssuranceDTO;
import esprit.pi.demo.DTO.EntrepreneurAssuranceDTO;
import esprit.pi.demo.DTO.SanteAssuranceDTO;
import esprit.pi.demo.DTO.ScolaireAssuranceDTO;
import java.util.List;
import java.util.Set;


@RestController
@AllArgsConstructor
@RequestMapping("/user/Assurance")

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

    @PostMapping("/create-scolaire-assurance")
    public Assurance createScolaireAssurance(  @RequestParam("userId") int userId,
                                               @RequestParam("packId") int packId,
                                               @RequestBody ScolaireAssuranceDTO scolaireAssuranceDTO) {
        return service.createScolaireAssurance(userId,packId, scolaireAssuranceDTO);
    }

    @PostMapping("/create-entrepreneur-assurance/{userId}/{packId}")
    public Assurance createEntrepreneurAssurance(  @PathVariable("userId") int userId,
                                                   @PathVariable("packId") int packId,
                                                   @RequestBody EntrepreneurAssuranceDTO entrepreneurAssuranceDTO) {
        return service.createEntrepreneurAssurance(userId,packId, entrepreneurAssuranceDTO);
    }

    @PostMapping("/create-sante-assurance")
    public Assurance createSanteAssurance(  @RequestParam("userId") int userId,
                                            @RequestParam("packId") int packId,
                                            @RequestBody SanteAssuranceDTO santeAssuranceDTO) {
        return service.createSanteAssurance(userId,packId, santeAssuranceDTO);
    }

    @PostMapping("/create-agricole-assurance")
    public Assurance createAgricoleAssurance(  @RequestParam("userId") int userId,
                                               @RequestParam("packId") int packId,
                                               @RequestBody AgricoleAssuranceDTO agricoleAssuranceDTO) {
        return service.createAgricoleAssurance(userId,packId, agricoleAssuranceDTO);
    }

    @GetMapping("/countAssurancesByUserLastNYear/{userId}/{n}")
    public Long countAssurancesByUserLastNYear(@PathVariable Integer userId, @PathVariable Integer n) {
        return service.countAssurancesByUserLastNYear(userId, n);
    }

    @GetMapping("/countSinistresByUserLastNYear/{userId}/{n}")
    public Long countSinistresByUserLastNYear(@PathVariable int userId, @PathVariable int n) {
        return service.countSinistresByUserLastNYear(userId, n);
    }

    @GetMapping("/calculScolairePrime")
    public float calculScolairePrime(@RequestParam("capitalescolaire_assure") float capitalescolaire_assure) {
        return service.CalculScolairePrime(capitalescolaire_assure);
    }

    @GetMapping("/calculENTREPRENEURPrime/{typeAssuranceEntrep}/{bienAssure}/{idpack}")
    public float calculENTREPRENEURPrime(
            @PathVariable("typeAssuranceEntrep") TypeAssuranceEntrep typeAssuranceEntrep,
            @PathVariable("bienAssure") BienAssuré bienAssuré,
            @PathVariable("idpack") int idpack) {
        return service.CalculENTREPRENEURPrime(typeAssuranceEntrep, bienAssuré, idpack);
    }

    @GetMapping("/calculSANTEPrime")
    public float calculSANTEPrime(
            @RequestParam("typeAssuranceSante") TypeAssuranceSante typeAssuranceSante,
            @RequestParam("age") int age,
            @RequestParam("gender") Gender gender) {
        return service.CalculSANTEPrime(typeAssuranceSante, age, gender);
    }

    @GetMapping("/calculAgriculturePrime/{capitalAgricole_assure}/{typeAgriculture}")
    public float calculAgriculturePrime(
            @PathVariable("capitalAgricole_assure") float capitalAgricole_assure,
            @PathVariable("typeAgriculture") TypeAgriculture typeAgriculture) {
        return service.CalculAgriculturePrime(capitalAgricole_assure, typeAgriculture);
    }

}
