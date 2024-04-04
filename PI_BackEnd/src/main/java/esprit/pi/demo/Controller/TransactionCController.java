package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.ITransactionCService;
import esprit.pi.demo.entities.TransactionCredit;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/TransactionCrédit")
public class TransactionCController {
    private ITransactionCService service;

    @PostMapping("/add/{id}")
    public TransactionCredit addCredit(@PathVariable int id,@RequestBody TransactionCredit transactionCredit){
        return service.saveTransactionC(transactionCredit,id);
    }

    @GetMapping("/all")
    public List<TransactionCredit> findAllTransactionsC(){
        return service.getTransactionsC();
    }

    @GetMapping("/{id}")
    public TransactionCredit findTransactionCById(@PathVariable int id){
        return service.getTransactionCById(id);
    }

    @PutMapping("/update/{id}")
    public TransactionCredit updateTransactionC(@PathVariable int id,@RequestBody TransactionCredit transactionCredit) {
        return service.updateTransactionC(id,transactionCredit);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTransactionC(@PathVariable int id){
        return service.deleteTransactionC(id);
    }

}
