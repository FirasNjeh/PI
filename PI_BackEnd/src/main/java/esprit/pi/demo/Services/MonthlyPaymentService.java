package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.CreditRepository;
import esprit.pi.demo.Repository.MonthlyPaymentRepository;
import esprit.pi.demo.entities.*;
import esprit.pi.demo.entities.Enumeration.CreditHistory;
import esprit.pi.demo.entities.Enumeration.StatusCredit;
import esprit.pi.demo.entities.Enumeration.StatusMonthlyPayment;
import esprit.pi.demo.entities.Enumeration.TypeTransaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;


@AllArgsConstructor
@Service
public class MonthlyPaymentService implements IMonthlyPaymentService {
    private MonthlyPaymentRepository repository;
    private CreditService cr;
    private PortefeuilleService port;
    private TransactionCService trans;

    CreditRepository repository1;

    @Override
    public MonthlyPayment add(int idc,MonthlyPayment mp, float montant) {
        TransactionCredit transactionC = new TransactionCredit();
        Credit c;
        c=cr.getCreditById(idc);
        int number=0;

        int cut=c.getUserCR().getPortefeuilleUser().getId(); // id el portefeuille


        if ((montant<port.getPortefeuilleById(cut).getMontant())&& (c.getMontantRestant()!=0))
        {
            mp.setCreditM(c);
            mp.setMontant(montant);
            mp.setSupposedDate(c.getDatepp());
            mp.setPaymentDate(LocalDate.now());
            repository.save(mp);

            //implementation de la date du prochain paiement
           Calendar calendar = Calendar.getInstance();
           calendar.setTime(c.getDatepp());
           calendar.add(Calendar.MONTH, 1); // ajout d'un mois
           c.setDatepp(calendar.getTime());


            mp.setLateDays(calculateLateDays(idc, mp.getId()));
            mp.setStatus(StatusMonthlyPayment.RESOLVED);


            if(mp.getLateDays()!=0) {c.setLateTimes(c.getLateTimes()+1);} // nbre de fois payé en retard
            repository.save(mp);
            repository1.save(c);

            //verification de l'historique de paiement
           if (c.getLateTimes()==0){c.setCreditHistory(CreditHistory.ALL_PAID_IN_TIME);}
           else if (c.getLateTimes()<2){c.setCreditHistory(CreditHistory.RESPONSIBLY_PAID);}
           else if (c.getLateTimes()<4){c.setCreditHistory(CreditHistory.DELAY);}
           else if (c.getLateTimes()>=4){c.setCreditHistory(CreditHistory.CRITICAL);}

            repository1.save(c);

           //montant restant à payer
            c.setMontantRestant(c.getMontant()- mp.getMontant());
            if(c.getMontantRestant()==0)
            {c.setStatusCredit(StatusCredit.CLOTURE);
                c.getUserCR().setNbr_credit(c.getUserCR().getNbr_credit()-1);

                String tomail="nerminenafti@gmail.com";
                String subject="Credit Cloturé";
                String body ="Votre credit a ete cloturé! Vous avez payé tout votre crédit nshlh mabrouk ";
                try { cr.sendEmail(tomail,subject,body);} catch (MessagingException e ) {e.printStackTrace();}}

            //transaction
            transactionC.setRib_source(port.getPortefeuilleById(cut).getRib());
            transactionC.setDate(LocalDate.now());
            transactionC.setMontant(montant);
            transactionC.setTypeTransaction(TypeTransaction.VERSEMENT);
            trans.saveTransactionC(transactionC,cut);
        }


        // si montant dans portefeuille insuffisant
        else{
            mp.setStatus(StatusMonthlyPayment.FAILED);
            if(montant>port.getPortefeuilleById(cut).getMontant())
            {System.out.println("votre solde est insuffisant");}
        else { System.out.println("vous avez déja payé tout votre credit");}}


        return repository.save(mp);
    }

    @Override
    public MonthlyPayment edit(int id, MonthlyPayment mp) {
        MonthlyPayment existing=repository.findById(id).orElse(null);
        existing.setPaymentDate(mp.getPaymentDate());
        return repository.save(existing);
    }

    @Override
    public List<MonthlyPayment> selectAll() {
        return repository.findAll();
    }

    @Override
    public MonthlyPayment selectById(int id) {
        return repository.findById(id).orElse(null);
    }


    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<MonthlyPayment> addAll(List<MonthlyPayment> list) {
        return repository.saveAll(list);
    }

    @Override
    public int calculateLateDays(int idc,int id) {
        Credit credit = cr.getCreditById(idc);
        int numberOfLateDays=0 ;
        MonthlyPayment mp=selectById(id);

        LocalDate localDateSupposed = mp.getSupposedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();// convert de date a local date
        LocalDate paymentDate = mp.getPaymentDate();
        if(localDateSupposed.compareTo(paymentDate)<0)
           { numberOfLateDays=(int)Math.max(0, ChronoUnit.DAYS.between(localDateSupposed,paymentDate));
          credit.setCreditHistory(CreditHistory.DELAY);}

        return numberOfLateDays;
    }

    @Override
    public List<MonthlyPayment> getCreditMonthlyPayment(int id) {
        return repository.getCredit_DuesHistory(id);
    }
}
