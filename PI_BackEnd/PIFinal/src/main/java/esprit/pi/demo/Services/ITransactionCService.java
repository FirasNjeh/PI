package esprit.pi.demo.Services;


import esprit.pi.demo.entities.TransactionCredit;

import java.util.List;

public interface ITransactionCService {
    TransactionCredit saveTransactionC(TransactionCredit transactionCredit,int id);
    List<TransactionCredit> getTransactionsC();
    TransactionCredit getTransactionCById(int id);
    String deleteTransactionC (int id);
    TransactionCredit updateTransactionC(int id, TransactionCredit transactionCredit);
}
