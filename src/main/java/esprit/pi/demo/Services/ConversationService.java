package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.ConversationRepository;
import esprit.pi.demo.Repository.MessageRepository;
import esprit.pi.demo.Repository.NotificationRepository;
import esprit.pi.demo.Repository.UserRepository;
import esprit.pi.demo.entities.Notification;
import esprit.pi.demo.entities.TypeNotif;
import esprit.pi.demo.entities.*;
import esprit.pi.demo.Controller.FileStorageProperties;
import esprit.pi.demo.Controller.FileStorageException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/messages")
@Service
public class ConversationService implements IConversationService {

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationRepository notificationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final Path fileStorageLocation;

    public Resource download(String filename) {
        try {
            if (this.fileStorageLocation == null) {
                throw new FileStorageException("File storage location is not initialized.");
            }

            Path file = this.fileStorageLocation.resolve("chat/" + filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new FileStorageException("Error: " + e.getMessage());
        } catch (FileStorageException e) {
            throw e; // Re-lancer l'exception FileStorageException
        } catch (Exception e) {
            throw new FileStorageException("Could not download the file " + filename, e);
        }
    }

    @Autowired
    public ConversationService(FileStorageProperties fileStorageProperties) {
        if (fileStorageProperties == null || fileStorageProperties.getUploadDir() == null) {
            throw new IllegalArgumentException("File storage properties or upload directory is not initialized.");
        }

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public Optional<Conversation> findById(int id) {
        return conversationRepository.findById(id);
    }

    @Override
    public List<Conversation> getall() {
        return conversationRepository.findAll();
    }

    @Override
    public List<Conversation> getallbyuserid(int userid) {
        User user = userRepository.findById(userid).get();
        List<Conversation> conversations = conversationRepository.findAll();
        List<Conversation> convertationbyuser = conversations.stream()
                .filter(c -> c.getUser1().getId() == (userid) || c.getUser2().getId() == (userid))
                .collect(Collectors.toList());

        return convertationbyuser;
    }

    @Override
    public Conversation getConv(int id) {
        return conversationRepository.findById(id).get();
    }

    @Override
    public List<Conversation> findByIdUser(int id) {

        User sender = userRepository.findById(id).get();
        User receiver = userRepository.findById(id).get();

        return conversationRepository.findAll();
    }

    @Override
    public void deleteAllByEtatandUser(int idsender, EtatMessage etatMessage) {

    }

    @Override
    public void ajoutconvertation(ConversationMessageRequest n) {
        // Vérifie si une conversation existe déjà entre les deux utilisateurs
        Optional<Conversation> existingConversation = conversationRepository.findByUser1AndUser2(
                userRepository.findById(n.getSenderid()).orElse(null),
                userRepository.findById(n.getReceiverid()).orElse(null)
        );

        if (existingConversation.isPresent()) {
            // Si une conversation existe déjà, utilisez-la
            return;
        }

        // Sinon, créez une nouvelle conversation
        Conversation conversation = new Conversation();
        User sender = userRepository.findById(n.getSenderid()).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        conversation.setUser1(sender);
        User receiver = userRepository.findById(n.getReceiverid()).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
        conversation.setUser2(receiver);
        conversationRepository.save(conversation);
    }

    @Override
    public String uplodefileinmessage(int idcon, int idsender, int idrec, MultipartFile file) {
        Conversation conversation = conversationRepository.findById(idcon).get();
        User sender = userRepository.findById(idsender).get();
        User receiver = userRepository.findById(idrec).get();

        String filor = StringUtils.cleanPath(file.getOriginalFilename());

        Date date = new Date();

        Message message = new Message();
        message.setDate(date);
        message.setTypeeMessage(TypeMessage.FILE);
        message.setConversation(conversation);
        message.setEtat(EtatMessage.UNREAD);
        message.setMessage(filor);
        message.setSender(sender);
        message.setReceiver(receiver);
        Message mes = messageRepository.save(message);
        if (conversation.getMessages() != null) {
            List<Message> messss = conversation.getMessages();
            messss.add(mes);
            conversation.setMessages(messss);
        } else {
            List<Message> messss = new ArrayList<>();
            messss.add(mes);
            conversation.setMessages(messss);
        }
        conversationRepository.save(conversation);

        Notification notification = new Notification();
        notification.setStatusNotif(StatusNotif.UNREAD);
        notification.setMsg("Nouveau message reçu");
        notification.setTypeNotif(TypeNotif.CHAT); // Utiliser TypeNotif au lieu de TypeNotification
        notification.setDate(date);
        notification.setUserNotif(receiver);

        notificationRepository.save(notification);

        try {
            // Check if the file's name contains invalid characters
            if (filor.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + filor);
            }
            // Copy file to the target location (Replacing existing file with the same name)

            Path targetLocation = this.fileStorageLocation.resolve("chat/" + filor);
            //Path targetLocation = this.fileStorageLocation.getFileName(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return filor;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + filor + ". Please try again!", ex);
        }
    }

    @Override
    public void ajoutmessage(MessageRequest n) {
        Optional<Conversation> conversationOptional = conversationRepository.findById(n.getIdconversation());
        if (conversationOptional.isPresent()) {
            Conversation conversation = conversationOptional.get();
            User sender = userRepository.findById(n.getSenderid()).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
            User receiver = userRepository.findById(n.getReceiverid()).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
            Date date = new Date();

            // Création du message
            Message message = new Message();
            message.setDate(date);
            message.setTypeeMessage(TypeMessage.TXT);
            message.setConversation(conversation);
            message.setEtat(EtatMessage.UNREAD);
            message.setMessage(n.getMessage());
            message.setSender(sender);
            message.setReceiver(receiver);
            messageRepository.save(message);

            // Ajout du message à la liste des messages de la conversation
            List<Message> messages = conversation.getMessages();
            messages.add(message);
            conversation.setMessages(messages);
            conversationRepository.save(conversation);

            // Création de la notification associée au message
            Notification notification = new Notification();
            notification.setStatusNotif(StatusNotif.UNREAD);
            notification.setMsg("Nouveau message reçu");
            notification.setTypeNotif(TypeNotif.CHAT);
            notification.setUserNotif(receiver);
            notification.setDate(date);

            notificationRepository.save(notification); // Enregistrement de la notification
        } else {
            throw new IllegalArgumentException("Conversation not found");
        }
    }

    @Override
    public void deleteAll() {
        conversationRepository.deleteAll();
    }

    @Override
    public long getUnreadMessagesCount(EtatMessage etatMessage, User currentUser) {
        return messageRepository.countByEtatAndReceiver(etatMessage, currentUser);
    }

    @Override
    public void updateStatus(int id, ConversationMessageRequest c) {
        Conversation conversation = conversationRepository.findById(id).get();
        List<Message> msg = conversation.getMessages();
        int i = msg.size() - 1;
        while (i >= 0 && msg.get(i).getEtat() == EtatMessage.UNREAD) {
            Message m = messageRepository.findById((long) msg.get(i).getIdM()).get();
            m.setEtat(EtatMessage.READ);
            messageRepository.save(m);
            i--;
        }
        if (msg.size() > 0 && msg.get(0).getEtat() == EtatMessage.UNREAD) {
            Message m = messageRepository.findById((long) msg.get(0).getIdM()).get();
            m.setEtat(EtatMessage.READ);
            messageRepository.save(m);
        }
        conversation.setMessages(msg);
        conversationRepository.save(conversation);
    }
}
