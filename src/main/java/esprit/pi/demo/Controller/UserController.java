package esprit.pi.demo.Controller;
import esprit.pi.demo.Services.IServiceUser;
import esprit.pi.demo.Services.ServiceUser;
import esprit.pi.demo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private IServiceUser serviceUser;

    @PostMapping("/create")
    public User create(@RequestBody User user){
        return serviceUser.creer(user);
    }
    @GetMapping("/read")
    public List<User> read(){return serviceUser.lire();}
    @GetMapping("/lire/{id}")
    public User findUserById (@PathVariable int id){
        return serviceUser.getUserById(id);
    }
    @PutMapping("/update/{id}")
    public User update(@PathVariable int id,@RequestBody User user){
        return serviceUser.modifier(id,user);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        return serviceUser.supprimer(id);
    }
}
