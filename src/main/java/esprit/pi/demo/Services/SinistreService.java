package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.AssuranceRepository;
import esprit.pi.demo.Repository.PortefeuilleRepository;
import esprit.pi.demo.Repository.SinistreRepository;

import esprit.pi.demo.Repository.TransactionAssuranceRepository;
import esprit.pi.demo.entities.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Set;


@AllArgsConstructor
@Service
public class SinistreService implements ISinistreAssurance {
    private SinistreRepository repository;
    private AssuranceRepository assurancerepository;

    private PortefeuilleRepository portefeuilleepository;
    private TransactionAssuranceRepository transactionAssurancerepository;

    @Override

    //client
    public Sinistre saveSinistre(Sinistre sinistre)
    {
         repository.save(sinistre);
        String to = "maryembouchahoua@gmail.com"; // replace with the actual recipient email
        String subject = "New Sinistre Added";
        String body = "A new Sinistre has been added with details:";

        try {
            sendEmail(to, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace(); // handle the exception according to your application's needs
        }

        return sinistre;
    }



    @Override
    //Client+Agent
    public List<Sinistre> getSinsitre()
    {
        return repository.findAll();
    }

    @Override
    //Agent
    public Sinistre getSinistreById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sinistre with id " + id + " not found"));
    }
        @Override
        //aAdmin
        public  String deleteSinistre(int id){
            repository.deleteById(id);
            return "Sinitre cloturé !!"+id;
        }

    @Override
//role : Admin
    public Sinistre updateSinistre(int id , Sinistre sinistre ){
        Sinistre existingSinistre=repository.findById(sinistre.getId()).orElse(null);
        existingSinistre.setEstimation_expert(sinistre.getEstimation_expert());

        return repository.save(existingSinistre);


    }

    private void sendEmail(String to, String subject, String body) throws MessagingException {
        // Send an email
        // ...
        String from = "techwork414@gmail.com";
        String password = "pacrvzlvscatwwkb";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        //   message.setText(body);
        message.setContent(body, "text/html");

        Transport.send(message);
    }
    @Override
    public Set<Sinistre> getListSinistesByAssurance(int idassur) {
        return repository.findByAssurance_Id(idassur);
    }
    @Override
    public Sinistre createSinistreWithAssurance(int idassur, Sinistre sinistre) {
        Assurance assurance = assurancerepository.findById(idassur)
                .orElseThrow(() -> new RuntimeException("Assurance with id " + idassur + " not found"));
        sinistre.setDateSinistre(new Date(System.currentTimeMillis()));
        sinistre.setEtatSinistre(EtatSinistre.EN_ATTENTE);
        sinistre.setAssurance(assurance);
        return repository.save(sinistre);
    }
    @Override
    public Sinistre acceptSinistre(int idsin) {
        Sinistre sinistre = getSinistreById(idsin);
        sinistre.setEtatSinistre(EtatSinistre.ACCEPTE);

        float MontantPaye = sinistre.getRemboursement();

        TransactionAssurance transactionAssurance = new TransactionAssurance();
        transactionAssurance.setDate(LocalDate.now());
        transactionAssurance.setTypeTransaction(TypeTransaction.VERSEMENT);
        transactionAssurance.setTypeTransaction1(TypeTransaction1.Remboursement);
        transactionAssurance.setMontant(MontantPaye);

        User user = sinistre.getAssurance().getUserAssurance();
        Portefeuille userPortefeuille = user.getPortefeuilleUser();
        if (userPortefeuille != null) {
            userPortefeuille.setMontant(userPortefeuille.getMontant() + MontantPaye);
            portefeuilleepository.save(userPortefeuille);

            transactionAssurance.setPortefeuilleTransactionA(userPortefeuille);
            transactionAssurancerepository.save(transactionAssurance);
        } else {
            throw new RuntimeException("Portefeuille not found for user " + user);
        }

        return repository.save(sinistre);
    }
    @Override
    public Sinistre refuseSinistre(int idsin) {
        Sinistre sinistre = getSinistreById(idsin);
        sinistre.setEtatSinistre(EtatSinistre.REFUSE);
        return repository.save(sinistre);
    }

}
