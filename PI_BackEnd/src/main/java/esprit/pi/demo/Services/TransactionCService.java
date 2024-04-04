package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.TransactionCRepository;
import esprit.pi.demo.Repository.UserRepository;
import esprit.pi.demo.entities.Portefeuille;
import esprit.pi.demo.entities.TransactionCredit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TransactionCService implements ITransactionCService {
    private TransactionCRepository repository;
    private UserRepository repository1;

   private PortefeuilleService po;

    @Override
    public TransactionCredit saveTransactionC(TransactionCredit ts,int id) {
        Portefeuille p;
        p=po.getPortefeuilleById(id);
        ts.setPortefeuilleTransaction(p);

        int code_tr = 0;

       // for ( p : ps.selectAll()) {

            if (p.getRib() == ts.getRib_source()) {

                //String mail=repository1.retrieveEmailByAccounNum(account.getAccountNum());

                //code_tr = sendAttachmentEmail(mail);

                float sold = p.getMontant();
                p.setMontant(sold - ts.getMontant());
               // iPortefeuilleService.edit(p);
                repository.save(ts);

            } else if (p.getRib() == ts.getRib_destination()) {
                float a = p.getMontant();
                p.setMontant(a + ts.getMontant());
                repository.save(ts);

            }
        //}
        return repository.save(ts);
    }

    @Override
    public List<TransactionCredit> getTransactionsC() {
        return repository.findAll();
    }

    @Override
    public TransactionCredit getTransactionCById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public String deleteTransactionC(int id) {
        repository.deleteById(id);
        return "Transaction suprimée !!" +id;
    }

    @Override
    public TransactionCredit updateTransactionC(int id, TransactionCredit transactionCredit) {
        TransactionCredit existingTransactionC=repository.findById(id).orElse(null);
        existingTransactionC.setMontant(transactionCredit.getMontant());

        return repository.save(existingTransactionC);
    }
}
