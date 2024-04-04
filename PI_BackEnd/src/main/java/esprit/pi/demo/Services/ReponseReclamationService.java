package esprit.pi.demo.Services;


import esprit.pi.demo.Repository.ReponseReclamationRepository;
import esprit.pi.demo.entities.Enumeration.StatusRC;
import esprit.pi.demo.entities.Reclamation;
import esprit.pi.demo.entities.ReponseReclamation;
import esprit.pi.demo.Repository.ReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@AllArgsConstructor
@Service
public class ReponseReclamationService {
    @Autowired
    private ReponseReclamationRepository reponseRCRepository;
    private void sendEmail(String to, String subject, String body) throws MessagingException {

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
    private ReclamationRepository recRepository;

    public ReponseReclamation saveReponseReclamation(ReponseReclamation reponseRC){
        Reclamation reclamation = recRepository.findById(reponseRC.getReclamation().getId()).orElse(null);
        if (reclamation != null) {
            reponseRC.setReclamation(reclamation);
            reponseRC.getReclamation().setStatusRC(StatusRC.resolved);
            reponseRCRepository.save(reponseRC);
        } else {
            return null;}
        String to = "soumaya.nabli7@gmail.com";
        String subject = " Claim Processed - Check Response on Website";
        String body = "We're pleased to inform you that your claim has been processed. Please log in to our website to view the response.";

        try {
            sendEmail(to, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return reponseRC;
    }
    public List<ReponseReclamation> saveReponseReclamations(List<ReponseReclamation> reponsesRC){
        return reponseRCRepository.saveAll(reponsesRC);
    }
    public List<ReponseReclamation> getReponseReclamations(){
        return reponseRCRepository.findAll();
    }
    public ReponseReclamation getReponseReclamationById(int id){
        return reponseRCRepository.findById(id).orElse(null);
    }

    public String deleteReponseReclamation(int id){
        reponseRCRepository.deleteById(id);
        return "réponse de reclamation supprimée : "+id;
    }
    public ReponseReclamation updateReponseReclamation(ReponseReclamation reponseRC){
        ReponseReclamation newReponseReclamation=reponseRCRepository.findById(reponseRC.getId()).orElse(null);
        newReponseReclamation.setDescription(reponseRC.getDescription());
        newReponseReclamation.setReclamation(reponseRC.getReclamation());
        newReponseReclamation.setReponse(reponseRC.getReponse());
        newReponseReclamation.setDateReponse(reponseRC.getDateReponse());
        return reponseRCRepository.save(newReponseReclamation);
    }
}
