package esprit.pi.demo.pi_back.Services;

import esprit.pi.demo.pi_back.Entities.PriorityLevel;
import esprit.pi.demo.pi_back.Entities.Reclamation;
import esprit.pi.demo.pi_back.Entities.StatusRC;
import esprit.pi.demo.pi_back.Repository.ReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@AllArgsConstructor
@Service
public class ReclamationService {
    @Autowired
    private ReclamationRepository recRepository;

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

    public void saveReclamationWithImage(String object, String description, StatusRC statusRC, Date dateRC, PriorityLevel priorityLevel, String typeRC, MultipartFile file) throws IOException {
        Reclamation reclamation = new Reclamation();
        reclamation.setObject(object);
        reclamation.setDescription(description);
        reclamation.setStatusRC(statusRC);
        reclamation.setDateRC(dateRC);
        reclamation.setPriorityLevel(priorityLevel);
        reclamation.setTypeRC(typeRC);
        reclamation.setImage(file.getBytes());
        recRepository.save(reclamation);
    }

    public Reclamation saveReclamation(Reclamation reclamation){
        recRepository.save(reclamation);
        //sending email
        /*String to = "soumaya.nabli7@gmail.com";
        String subject = "Successful Addition of Your Claim";
        String body = "We're pleased to inform you that your claim has been successfully added to our system. We've received your request and are committed to processing it promptly.";

        try {
            sendEmail(to, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
        return reclamation;
    }
    public List<Reclamation> saveReclamations(List<Reclamation> reclamations){
        return recRepository.saveAll(reclamations);
    }
    public List<Reclamation> getReclamations(){
        return recRepository.findAll();
    }
    public Reclamation getReclamationById(int id){
        return recRepository.findById(id).orElse(null);
    }
    public Reclamation getReclamationByPriorityLevel(PriorityLevel priorityLevel){
        return recRepository.findByPriorityLevel(priorityLevel);
    }

    public String deleteReclamation(int id){
        recRepository.deleteById(id);
        return "reclamation supprimée : "+id;
    }
    public Reclamation updateReclamation(Reclamation reclamation){
        Reclamation newReclamation=recRepository.findById(reclamation.getId()).orElse(null);
        newReclamation.setDescription(reclamation.getDescription());
        newReclamation.setObject(reclamation.getObject());
        newReclamation.setDateRC(reclamation.getDateRC());
        newReclamation.setReponseRC(reclamation.getReponseRC());
        newReclamation.setTypeRC(reclamation.getTypeRC());
        newReclamation.setStatusRC(reclamation.getStatusRC());
        newReclamation.setPriorityLevel(reclamation.getPriorityLevel());
        return recRepository.save(newReclamation);
    }


    }
