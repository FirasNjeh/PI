package esprit.pi.demo.Controller;

import esprit.pi.demo.entities.Forum;
import esprit.pi.demo.Services.IForumService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RestController
@AllArgsConstructor

@RequestMapping("/user/api/v1/forum")
public class ForumController {
    private IForumService iforumservice ;

    @GetMapping("/Forum")
    public List<Forum> getForum()
    {
        return iforumservice.getForum() ;
    }
    @GetMapping("/Forum/{id_forum}")
    public Forum getForum(@PathVariable Long id_forum )
    {
        return iforumservice.getForumById(id_forum).orElseThrow(
                ()->new EntityNotFoundException("Requested forum not found")
        );
    }
    @PostMapping("/addForum")
    public Forum addForum(@RequestBody Forum Forum)
    {
        return iforumservice.addForum(Forum) ;
    }
    @PutMapping("/Forum/{id_forum}")
    public ResponseEntity<?> UpdateForum(@RequestBody Forum Forum , @PathVariable Long id_forum) {
        if (iforumservice.existByIdForum(id_forum)) {
            esprit.pi.demo.entities.Forum forum = iforumservice.getForumById(id_forum).orElseThrow(
                    ()->new EntityNotFoundException(("Requested Forum not found"))
            );

            forum.setTitle_forum(Forum.getTitle_forum());
            forum.setDescription_forum(Forum.getDescription_forum());
            forum.setDate_forum(Forum.getDate_forum());
            forum.setNbr_like(Forum.getNbr_like());
            forum.setNbr_signal(Forum.getNbr_signal());
            forum.setCategory_forum(Forum.getCategory_forum());

            // Sauvegardez les modifications apportées à credit1, pas à Credit
            iforumservice.updateForum(forum,id_forum);

            // Retournez la réponse avec l'objet credit1 modifié
            return ResponseEntity.ok().body(forum);
        } else {
            HashMap<String , String> message= new HashMap<>();
            message.put("message", id_forum +  "Forum not found or matched") ;
            // Retournez une réponse avec un code d'erreur 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }


    @PutMapping("/Forum/like/{id_forum}")
    public ResponseEntity<?> incrementLikes(@PathVariable Long id_forum) {
        Forum updatedForum = iforumservice.incrementLikes(id_forum);
        return ResponseEntity.ok().body(updatedForum);
    }
    @DeleteMapping("/Forum/{id_forum}")
    @ResponseBody
    public void removeForum(@PathVariable("id_forum") long id_forum) {
        iforumservice.DeleteForum(id_forum);
    }


    @PutMapping("/Forum/nbrlike")
    public List<Forum> getForumBynbr_like()
    {
        return iforumservice.getForumBynbr_like() ;
    }


}
