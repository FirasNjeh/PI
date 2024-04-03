package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatbotRepository extends JpaRepository <ChatBot,Integer> {

}