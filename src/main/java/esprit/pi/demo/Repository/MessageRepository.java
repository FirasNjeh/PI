package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.EtatMessage;
import esprit.pi.demo.entities.Message;
import esprit.pi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    long countByEtatAndReceiver(EtatMessage etat, User receiver);
}

