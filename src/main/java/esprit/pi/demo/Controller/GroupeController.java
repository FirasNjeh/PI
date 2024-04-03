package esprit.pi.demo.Controller;

import esprit.pi.demo.entities.Groupe;
import esprit.pi.demo.Services.IGroupeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/groupes")
@CrossOrigin("*")
public class GroupeController {
    IGroupeService groupeInterface;

    @PostMapping("/addGroupe")
    public Groupe addGroupe(@RequestBody Groupe g) {
        return groupeInterface.addGroupe(g);
    }


    @DeleteMapping("/removeGroupe/{idGroupe}")
    public void removeGroupe(@PathVariable("idGroupe") long idGroupe) {
        groupeInterface.removeGroupe(idGroupe);

    }
}
