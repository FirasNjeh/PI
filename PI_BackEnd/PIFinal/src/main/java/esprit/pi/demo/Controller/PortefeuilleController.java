package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.iPortefeuilleService;
import esprit.pi.demo.entities.Portefeuille;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Portefeuille")
public class PortefeuilleController {
    private iPortefeuilleService service;

@GetMapping("get/{id}")
    public Portefeuille getPortefeuilleById(@PathVariable int id) {
        return service.getPortefeuilleById(id);
    }
@PutMapping("modify/{id}")
    public Portefeuille updatePortefeuille(@PathVariable int id,@RequestBody Portefeuille p) {
        return service.updatePortefeuille(id, p);
    }
@GetMapping("/all")
    public List<Portefeuille> selectAll() {
        return service.selectAll();
    }
@PostMapping("/ajout/{id}")
    public Portefeuille save(@PathVariable int id,@RequestBody Portefeuille p) {
        return service.save(id,p);
    }
}
