package esprit.pi.demo.Services;

import esprit.pi.demo.entities.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IConversationService {

    Optional<Conversation> findById(int id);
    List<Conversation> getall();

    List<Conversation> getallbyuserid(int userid);


    void updateStatus(int id, ConversationMessageRequest c);
    void deleteAllByEtatandUser(int idsender, EtatMessage etatMessage);

    void ajoutconvertation(ConversationMessageRequest n);
    void ajoutmessage(MessageRequest n);

    String uplodefileinmessage(int idcon,int idsender, int idrec,  MultipartFile file);

    Resource download(String filename) throws IOException;

    List<Conversation> findByIdUser(int id);

    void deleteAll();

    public Conversation getConv(int id);
    long getUnreadMessagesCount(EtatMessage etatMessage, User currentUser);

}
