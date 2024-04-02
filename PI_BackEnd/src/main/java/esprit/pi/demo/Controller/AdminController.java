package esprit.pi.demo.Controller;

import esprit.pi.demo.DTO.AgeGroupStatisticsDTO;
import esprit.pi.demo.DTO.GenderStatisticsDTO;
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
    @GetMapping("/id/{id}")
    public User findUserById (@PathVariable int id){
        return serviceUser.getUserById(id);
    }
    @PostMapping("/create")
    public User create(@RequestBody User user){
        return serviceUser.creer(user);
    }
    @PutMapping("/update/{id}")
    public User update(@PathVariable int id,@RequestBody User user){
        return serviceUser.modifier(id, user);
    }
    @PostMapping("/ban/{userId}")
    public void banUser(@PathVariable int userId) {
        serviceUser.banUser(userId);
    }
    @PostMapping("/deban/{userId}")
    public void debanUser(@PathVariable int userId) {
        serviceUser.debanUser(userId);
    }
    @GetMapping("/sortbyname")
    public List<User> trierUtilisateurParNom() {
        return serviceUser.trierUtilisateurParNom();
    }
    @GetMapping("/agemoyen")
    public double calculerAgeMoyenUsers() {
        return serviceUser.calculerAgeMoyenUsers();
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        return serviceUser.supprimer(id);
    }
    @GetMapping("/sortbyage")
    public List<User> trierUtilisateurParAge() {
        return serviceUser.trierUtilisateurParAge();
    }
    @GetMapping("/nom/{nom}")
    public List<User> findByNom(@PathVariable String nom) {
        return serviceUser.findByNom(nom);
    }
    @GetMapping("/prenom/{prenom}")
    public List<User> findByPrenom(@PathVariable String prenom) {
        return serviceUser.findByPrenom(prenom);
    }
    @GetMapping("/sortbyrole")
    public List<User> trierUtilisateurParRole() {
        return serviceUser.trierUtilisateurParRole();
    }
    @GetMapping("/nom/{nom}/prenom/{prenom}")
    public List<User> findByPrenomAndNom(@PathVariable String prenom,@PathVariable String nom) {
        return serviceUser.findByPrenomAndNom(prenom,nom);
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
    @GetMapping("/statgenre")
    public GenderStatisticsDTO obtenirStatistiquesGenre() {
        return serviceUser.obtenirStatistiquesGenre();
    }
    @GetMapping("/stattrancheage")
    public AgeGroupStatisticsDTO obtenirStatistiquesTranchesAge() {
        return serviceUser.obtenirStatistiquesTranchesAge();
    }
    @GetMapping("/cin/{cin}")
    public User findByCinLike(@PathVariable int cin) {
        return serviceUser.findByCinLike(cin);
    }
    @GetMapping("/matfiscale/{matriculeFiscale}")
    public User findByMatricule_fiscale(@PathVariable int matriculeFiscale) {
        return serviceUser.findByMatricule_fiscale(matriculeFiscale);
    }

}
