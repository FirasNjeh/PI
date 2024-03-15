package esprit.pi.demo.Controller;
import esprit.pi.demo.DTO.AgeGroupStatisticsDTO;
import esprit.pi.demo.DTO.ChangePasswordRequest;
import esprit.pi.demo.DTO.GenderStatisticsDTO;
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

    @PostMapping("/create")
    public User create(@RequestBody User user){
        return serviceUser.creer(user);
    }
    @GetMapping("/all")
    public List<User> read(){return serviceUser.lire();}
    @GetMapping("/{id}")
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
    @GetMapping("/sortbyname")
    public List<User> trierUtilisateurParNom() {
        return serviceUser.trierUtilisateurParNom();
    }
    @GetMapping("/sortbysurname")
    public List<User> trierUtilisateurParPrenom() {
        return serviceUser.trierUtilisateurParPrenom();
    }
    @GetMapping("/sortbysalairecroissant")
    public List<User> trierUtilisateurParSalaireCroissant() {
        return serviceUser.trierUtilisateurParSalaireCroissant();
    }
    @GetMapping("/sortbysalairedecroissant")
    public List<User> trierUtilisateurParSalaireDecroissant() {
        return serviceUser.trierUtilisateurParSalaireDecroissant();
    }
    @GetMapping("/sortbyage")
    public List<User> trierUtilisateurParAge() {
        return serviceUser.trierUtilisateurParAge();
    }
    @GetMapping("/{nom}")
    public List<User> findByNom(@PathVariable String nom) {
        return serviceUser.findByNom(nom);
    }
    @GetMapping("/{prenom}")
    public List<User> findByPrenom(@PathVariable String prenom) {
        return serviceUser.findByPrenom(prenom);
    }
    @GetMapping("/sortbyrole")
    public List<User> trierUtilisateurParRole() {
        return serviceUser.trierUtilisateurParRole();
    }
    @GetMapping("/{nom}/{prenom}")
    public List<User> findByPrenomAndNom(@PathVariable String prenom,@PathVariable String nom) {
        return serviceUser.findByPrenomAndNom(prenom,nom);
    }
    @GetMapping("/{cin}")

    public User findByCinLike(@PathVariable int cin) {
        return serviceUser.findByCinLike(cin);
    }
    @GetMapping("/{matriculeFiscale}")
    public User findByMatricule_fiscale(@PathVariable int matriculeFiscale) {
        return serviceUser.findByMatricule_fiscale(matriculeFiscale);
    }
    @GetMapping("/agemoyen")
    public double calculerAgeMoyenUsers() {
        return serviceUser.calculerAgeMoyenUsers();
    }
    @GetMapping("/statgenre")
    public GenderStatisticsDTO obtenirStatistiquesGenre() {
        return serviceUser.obtenirStatistiquesGenre();
    }
    @GetMapping("/stattrancheage")
    public AgeGroupStatisticsDTO obtenirStatistiquesTranchesAge() {
        return serviceUser.obtenirStatistiquesTranchesAge();
    }
    @PatchMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser){
        serviceUser.changePassword(request,connectedUser);
        return ResponseEntity.ok().build();
    }
}
