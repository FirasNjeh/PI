package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.BadWordsFilter;
import esprit.pi.demo.Services.ICommentService;
import esprit.pi.demo.Services.IPostService;
import esprit.pi.demo.dto.CommentDto;
import esprit.pi.demo.entities.Comment;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/comments")
@CrossOrigin(origins = "*")

public class CommentController {

    ICommentService commentInterface;
    IPostService postInterface;
    private final BadWordsFilter badWordsFilter;

    @GetMapping("/getComments")
    public List<Comment> retrieveAllComments() {
        return commentInterface.retrieveAllComments();
    }

    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {

        String filteredContent = badWordsFilter.filter(comment.getDescCmnt());
        comment.setDescCmnt(filteredContent);

        Comment addedComment = commentInterface.addComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
    }

    @PutMapping("/updateComment/{idCmnt}")

    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {

        String filteredContent = badWordsFilter.filter(comment.getDescCmnt());
        comment.setDescCmnt(filteredContent);


        Comment updatedComment = commentInterface.updateComment(id, comment);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/retrieveComment/{idCmnt}")
    public Comment retrieveComment(@PathVariable("idCmnt") long idCmnt) {
        return commentInterface.retrieveComment(idCmnt);
    }

    @DeleteMapping("/removeComment/{idCmnt}")
    public void removeComment(@PathVariable("idCmnt") long idCmnt) {
        commentInterface.removeComment(idCmnt);

    }

    @GetMapping("/post/{idPost}")
    public List<Comment> getCommentsForPost(@PathVariable Long idPost) {
        return commentInterface.getCommentsForPost(idPost);
    }
    @PostMapping("/addComment/{idPost}")

    public ResponseEntity<String> addCommentToPost(@PathVariable Long id, @RequestBody Comment comment) {
        try {

            String filteredContent = badWordsFilter.filter(comment.getDescCmnt());
            comment.setDescCmnt(filteredContent);

            commentInterface.addCommentToPosts(id, comment);
            return ResponseEntity.ok("Comment added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding comment to post: " + e.getMessage());
        }
    }

    @PostMapping("/createComment")
    public ResponseEntity<?> commentPost(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentInterface.createComment(commentDto));
    }

}
