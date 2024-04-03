package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Comment;
import esprit.pi.demo.entities.Post;
import esprit.pi.demo.Services.ICommentService;
import esprit.pi.demo.Repository.CommentRepository;
import esprit.pi.demo.Repository.PostRepository;
import esprit.pi.demo.dto.CommentDto;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService implements ICommentService {

    CommentRepository commentRepository;
    PostRepository postRepository;

    @Override
    public List<Comment> retrieveAllComments() {
        return (List<Comment>) commentRepository.findAll();
    }

    @Override
    public Comment addComment(Comment c) {
        return commentRepository.save(c);
    }

    @Override
    public Comment updateComment(Long idCmnt, Comment c) {
        if (commentRepository.existsById(idCmnt)) {
            c.setIdCmnt(idCmnt);
            return commentRepository.save(c);
        }
        return null;
    }

    @Override
    public Comment retrieveComment(long idCmnt) {
        return commentRepository.findById(idCmnt).orElse(null);
    }

    @Override
    public void removeComment(long idCmnt) {
        commentRepository.deleteById(idCmnt);

    }

    @Override
    public List<Comment> getCommentsForPost(Long idPost) {
        return commentRepository.findByPostIdPost(idPost);
    }


    @Override
    public void addCommentToPosts(Long idPost, Comment comment) {
        Post post = postRepository.findById(idPost).orElse(null);
        if (post != null) {
            comment.setPost(post);
            commentRepository.save(comment);
            // Mise à jour de la liste des commentaires dans l'entité Post
            post.getComments().add(comment);
            postRepository.save(post);
        }
    }


    @Override
    public ResponseEntity<?> createComment(CommentDto commentDto) {
        try {
          /*  User user = userRepository.findById(commentDto.getIdUser())
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
*/
            Post post = postRepository.findById(commentDto.getIdPost())
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

            Comment comment=new Comment();
           // comment.setUsers(user);
            comment.setPost(post);

            comment.setDescCmnt(commentDto.getDescCmnt());
            comment.setDateCmnt(commentDto.getDateCmnt());
            commentRepository.save(comment);


            return ResponseEntity.ok("comment has been created");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }



}




