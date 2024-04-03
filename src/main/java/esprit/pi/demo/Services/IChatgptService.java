package esprit.pi.demo.Services;

import esprit.pi.demo.openai.OutputDto;


import java.io.IOException;

public interface IChatgptService {

    String sendChatgptRequest(String body) throws IOException, InterruptedException;


    OutputDto sendPrompt(String prompt) throws Exception;
}
