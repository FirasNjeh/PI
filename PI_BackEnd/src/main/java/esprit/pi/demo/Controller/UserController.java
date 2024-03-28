package esprit.pi.demo.Controller;
import esprit.pi.demo.DTO.AgeGroupStatisticsDTO;
import esprit.pi.demo.DTO.ChangePasswordRequest;
import esprit.pi.demo.DTO.GenderStatisticsDTO;
import esprit.pi.demo.DTO.UpdateUserRequest;
import esprit.pi.demo.Services.IServiceUser;
import esprit.pi.demo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private IServiceUser serviceUser;

    @GetMapping("/id/{id}")
    public User findUserById (@PathVariable int id){
        return serviceUser.getUserById(id);
    }

    @PatchMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser){
        serviceUser.changePassword(request,connectedUser);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateCurrent(Principal connectedUser, @RequestBody UpdateUserRequest updatedUser) {
            serviceUser.updateCurrentUser(connectedUser,updatedUser);
        return ResponseEntity.ok().build() ;
    }
}
