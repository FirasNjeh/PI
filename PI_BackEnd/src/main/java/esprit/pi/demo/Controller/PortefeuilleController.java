package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.IPortefeuilleService;
import esprit.pi.demo.entities.Portefeuille;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/portefeuille")
@AllArgsConstructor
public class PortefeuilleController {
    private IPortefeuilleService iPortefeuilleService;
    @PostMapping("/create")
    public Portefeuille create(@RequestBody Portefeuille portefeuille){
        return iPortefeuilleService.creer(portefeuille);
    }
    @GetMapping("/all")
    public List<Portefeuille> read(){
        return iPortefeuilleService.lire();
    }
    @GetMapping("/{id}")
    public Portefeuille findById(@PathVariable int id) {
        return iPortefeuilleService.getPortefeuilleById(id);
    }
    @PutMapping("/update/{id}")
    public Portefeuille update(@PathVariable int id,@RequestBody Portefeuille portefeuille){
        return iPortefeuilleService.modifier(id, portefeuille);
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id){

        return iPortefeuilleService.supprimer(id);
    }

}
