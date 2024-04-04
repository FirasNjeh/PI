package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.IConversationService;
import esprit.pi.demo.entities.*;
import esprit.pi.demo.entities.Enumeration.EtatMessage;
import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/user/api/conversations")  // Adjusted the base path

public class ConversationController {

    @Autowired
    private IConversationService iConversationService;

    @GetMapping("/getall")
    public List<Conversation> getAllConversations() {
        return iConversationService.getall();
    }

    @GetMapping("/getconv/{id}")
    public ResponseEntity<Conversation> getConversationById(@PathVariable("id") int id) {
        Optional<Conversation> optionalConversation = iConversationService.findById(id);
        return ResponseEntity.ok(optionalConversation.orElse(null));
    }

    @GetMapping("/getconversationbyuserid/{id}")
    public ResponseEntity<List<Conversation>> getConversationsByUserId(@PathVariable("id") int userId) {
        List<Conversation> conversations = iConversationService.getallbyuserid(userId);
        return ResponseEntity.ok(conversations);
    }

    @PostMapping("/addconversation")
    public ResponseEntity<MessageResponse> addConversation(@RequestBody ConversationMessageRequest request) {
        iConversationService.ajoutconvertation(request);
        return ResponseEntity.ok(new MessageResponse("Conversation registered successfully!"));
    }

    @PostMapping("/addmessage")
    public ResponseEntity<MessageResponse> addMessage(@RequestBody MessageRequest request) {
        iConversationService.ajoutmessage(request);
        return ResponseEntity.ok(new MessageResponse("Message registered successfully!"));
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<HttpStatus> deleteAllconv() {
        try {
            iConversationService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/unread/count")
    public long getUnreadMessagesCount(@RequestParam EtatMessage etat, @RequestParam User currentUser) {
        return iConversationService.getUnreadMessagesCount(etat, currentUser);
    }

    // Uncomment and complete the following methods if needed

    @PutMapping("/updateStatus/{id_conv}")
    public void updateStatus(@PathVariable int id_conv, @RequestBody ConversationMessageRequest c) {
        iConversationService.updateStatus(id_conv, c);
    }


    @GetMapping("/downloadFile/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
        Resource file = iConversationService.download(filename);
        Path path = file.getFile().toPath();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
