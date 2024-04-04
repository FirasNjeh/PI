package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Comment;
import esprit.pi.demo.entities.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    List<Post> retrieveAllPosts();
    Post addPost (Post p);
    Post updatePost(Long idPost ,Post p);
    Post retrievePost (long idPost);
    void  removePost (long idPost);

    void addCommentToPost(Long idPost, Comment comment);

    List<Post> searchPosts(String keyword);

    void addPostImage(MultipartFile imageFile, Long postId) throws IOException;


}
