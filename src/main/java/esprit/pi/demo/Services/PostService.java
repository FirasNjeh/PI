package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Comment;
import esprit.pi.demo.entities.Post;
import esprit.pi.demo.Repository.CommentRepository;
import esprit.pi.demo.Repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService implements IPostService {

    private final ThreadLocal<String> uploadDir = new ThreadLocal<>();


    public void setUploadDir(String dir) {
        uploadDir.set(dir);
    }

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;



    @Override
    public List<Post> retrieveAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post addPost(Post p) {
        return postRepository.save(p);
    }

    @Override
    public Post updatePost(Long idPost ,Post p) {
        if (postRepository.existsById(idPost)){
            p.setIdPost(idPost);
            return postRepository.save(p);
        }
        return null ;
    }

    @Override
    public Post retrievePost(long idPost) {
        return postRepository.findById(idPost).orElse(null);
    }

    @Override
    public void removePost(long idPost) {
        postRepository.deleteById(idPost);
    }




    public void addCommentToPost(Long idPost, Comment comment) {
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
    public List<Post> searchPosts(String keyword) {
        return postRepository.findByDescPostContaining(keyword);
    }



    @Override
    public void addPostImage(MultipartFile imageFile, Long postId) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {

            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir.get());
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);

            byte[] imageData = imageFile.getBytes();


            Files.write(filePath, imageData);


            Post post = postRepository.findById(postId).orElse(null);
            if (post != null) {
                post.setImageData(imageData);
                postRepository.save(post);
            }
        }
    }



}




