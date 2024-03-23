package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.IMonthlyPaymentService;
import esprit.pi.demo.entities.MonthlyPayment;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/MonthlyPayment")

public class MonthlyPaymentController {
    private IMonthlyPaymentService service;

    @PostMapping("/ajouter/{id}/{montant}")
    public MonthlyPayment ajouterPaiement(@PathVariable int id, @RequestBody MonthlyPayment mp,@PathVariable float montant) {
        return service.add(id, mp,montant);
    }

    @PutMapping("/modifier/{id}")
    public MonthlyPayment ModifierPaiement(@PathVariable int id, @RequestBody MonthlyPayment mp) {
        return service.edit(id, mp);
    }

    @GetMapping("/all")
    public List<MonthlyPayment> selectAll() {
        return service.selectAll();
    }

    @GetMapping("/{id}")
    public MonthlyPayment selectById(@PathVariable int id) {
        return service.selectById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        service.deleteById(id);
    }

//    @GetMapping
//    public List<MonthlyPayment> addAll(@RequestBody List<MonthlyPayment> list) {
//        return service.addAll(list);
//    }

    @GetMapping("/lateDays/{idc}/{id}")
    public int calculateLateDays(@PathVariable int idc,@PathVariable int id) {
        return service.calculateLateDays(idc,id);
    }

    @GetMapping("/MonthlyPayment/creditNum/{id}")
    public List<MonthlyPayment> getCreditMonthlyPayment(@PathVariable int id){return service.getCreditMonthlyPayment(id);}


}
