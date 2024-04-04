
package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Conversation;
import esprit.pi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation,Integer> {
    Optional<Conversation> findByUser1AndUser2(User user1, User user2);
}

