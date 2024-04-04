package esprit.pi.demo.Controller;


import esprit.pi.demo.Services.BadWordsFilter;
import esprit.pi.demo.Services.IPostService;
import esprit.pi.demo.Services.IServiceUser;
import esprit.pi.demo.entities.Post;
import esprit.pi.demo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/api/posts")
@CrossOrigin(origins ="*")
public class PostController {

    IPostService postInterface;
    BadWordsFilter badWordsFilter;
    IServiceUser userService;

    @PostMapping("/addPost")
    public ResponseEntity<Post> addPost(@RequestParam("descPost") String descPost,
                                        @RequestParam(value = "imageData", required = false) MultipartFile imageData,
                                        @RequestParam("user_id") int userId) {
        try {
            String filteredDescPost = badWordsFilter.filter(descPost);


            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Post post = new Post();
            post.setDescPost(filteredDescPost);
            post.setDateCreation(LocalDate.now());


            if (imageData != null && !imageData.isEmpty()) {
                post.setImageData(imageData.getBytes());
            }

            post.setUser(user);

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
