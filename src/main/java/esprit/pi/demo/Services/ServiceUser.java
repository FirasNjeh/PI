package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.UserRepository;
import esprit.pi.demo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class ServiceUser implements IServiceUser {

  private UserRepository userRepository;
    @Override
    public User creer(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> lire() {
        return userRepository.findAll();
    }
    @Override
    public User getUserById (int id){
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public User modifier(int id, User user) {
        return userRepository.findById(id).map(user1 -> {
            user1.setNom(user.getNom());
            user1.setPrenom(user.getPrenom());
            user1.setCin(user.getCin());
            user1.setAdresse(user.getAdresse());
            user1.setDateNaissance(user.getDateNaissance());
            user1.setNumtel(user.getNumtel());
            user1.setEmail(user.getEmail());
            user1.setMdp(user.getMdp());
            user1.setProfession(user.getProfession());
            user1.setRole(user.getRole());
            user1.setNbr_credit(user.getNbr_credit());
            user1.setImage(user.getImage());
            user1.setSalaire(user.getSalaire());
            user.setMatricule_fiscale(user.getMatricule_fiscale());
            return userRepository.save(user1);

        }).orElse(null);
    }

    @Override
    public String supprimer(int id) {
        userRepository.deleteById(id);
        return "Utilisateur supprimer" ;
    }



}
