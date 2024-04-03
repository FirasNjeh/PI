package esprit.pi.demo.Controller;


import esprit.pi.demo.Services.BadWordsFilter;
import esprit.pi.demo.Services.IPostService;
import esprit.pi.demo.dto.PostDto;
import esprit.pi.demo.entities.Post;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/posts")
@CrossOrigin(origins ="*")
public class PostController {

    IPostService postInterface;

    BadWordsFilter badWordsFilter;

    @PostMapping("/addPost")
    public ResponseEntity<Post> addPost(@RequestBody PostDto postDto, @RequestParam("imageFile") MultipartFile imageFile) {
        try {

            String filteredDescPost = badWordsFilter.filter(postDto.getDescPost());


            Post post = new Post();
            post.setDescPost(filteredDescPost);
            post.setImageData(imageFile.getBytes()); // Stocker les données de l'image


            Post savedPost = postInterface.addPost(post);

            return ResponseEntity.ok(savedPost);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/get")
    public List<Post> retrieveAllPosts() {
        return postInterface.retrieveAllPosts();
    }



    @PutMapping("/updatePost/{idPost}")
    public Post updatePost(@PathVariable Long idPost ,@RequestBody Post p) {
        return postInterface.updatePost(idPost,p);
    }

    @GetMapping("/retrievePost/{idPost}")
    public Post retrievePost(@PathVariable("idPost") long idPost) {
        return postInterface.retrievePost(idPost);
    }

    @DeleteMapping("/removePost/{idPost}")
    public void removePost(@PathVariable("idPost") long idPost) {
        postInterface.removePost(idPost);

    }

    @GetMapping("/search")
    public List<Post> searchPosts(@RequestParam("keyword") String keyword) {
        return postInterface.searchPosts(keyword);
    }


}
