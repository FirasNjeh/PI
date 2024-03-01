package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.INotificationService;
import esprit.pi.demo.entities.Notification;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {
    private INotificationService iNotificationService;

    @PostMapping("/create")
    public Notification create(@RequestBody Notification notification){
        return iNotificationService.creer(notification);
    }
    @GetMapping("/all")
    public List<Notification> read(){
        return iNotificationService.lire();
    }
    @GetMapping("/{id}")
    public Notification findById(@PathVariable int id) {
        return iNotificationService.getNotificationById(id);
    }
    @PutMapping("/update/{id}")
    public Notification update(@PathVariable int id,@RequestBody Notification notification){
        return iNotificationService.modifier(id, notification);
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {

        return iNotificationService.supprimer(id);
    }
}
