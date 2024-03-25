package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.IGarantService;
import esprit.pi.demo.entities.Garant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Garant")
public class GarantController  {
    private IGarantService service;

    @PostMapping("/add")
    public Garant addGarant(@RequestBody Garant garant){
        return service.saveGarant(garant);
    }

    @GetMapping("/all")
    public List<Garant> findAllGarants(){
        return service.getGarants();
    }

    @GetMapping("/id/{id}")
    public Garant findById(@PathVariable int id){
        return service.getById(id);
    }

    @GetMapping("/name/{name}")
    public List<Garant> findGarantByLastName(@PathVariable String name){
        return service.getGarantByLastName(name);
    }

    @PutMapping("/update/{id}")
    public Garant updateGarant(@PathVariable int id,@RequestBody Garant garant) {
        return service.updateGarant(id,garant);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGarant(@PathVariable int id){
        return service.deleteGarant(id);
    }
}
