package esprit.pi.demo.Services;

import  esprit.pi.demo.entities.Comment;
import  esprit.pi.demo.dto.CommentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICommentService {
    List<Comment> retrieveAllComments();
    Comment addComment (Comment c);
    Comment updateComment(Long idCmnt ,Comment c);
    Comment retrieveComment (long idCmnt);
    void  removeComment (long idCmnt);
    void addCommentToPosts (Long idPost, Comment comment);
    List<Comment> getCommentsForPost(Long idPost);

     ResponseEntity<?> createComment(CommentDto commentDto);


    }
