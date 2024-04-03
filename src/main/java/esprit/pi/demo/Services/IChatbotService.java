package esprit.pi.demo.Services;

import esprit.pi.demo.entities.ChatBot;

import java.util.List;

public interface IChatbotService {
    List<ChatBot> selectall ();
    List<ChatBot> addAll (List<ChatBot> list );
}
