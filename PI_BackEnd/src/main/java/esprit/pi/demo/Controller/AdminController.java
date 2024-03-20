package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.IServiceUser;
import esprit.pi.demo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private IServiceUser serviceUser;

    @GetMapping("/all")
   public List<User> read(){
        return serviceUser.lire();
    }
    @PostMapping("/ban/{userId}")
    public void banUser(@PathVariable int userId) {
        serviceUser.banUser(userId);
    }
    @PostMapping("/deban/{userId}")
    public void debanUser(@PathVariable int userId) {
        serviceUser.debanUser(userId);
    }

}
