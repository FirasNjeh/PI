package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Enumeration.EtatMessage;
import esprit.pi.demo.entities.MessageChat;
import esprit.pi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageChat, Long> {
    long countByEtatAndReceiver(EtatMessage etat, User receiver);
}

