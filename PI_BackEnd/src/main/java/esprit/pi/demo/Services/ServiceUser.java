package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.UserRepository;
import esprit.pi.demo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServiceUser implements IServiceUser {

  private UserRepository userRepository;
    @Override
    public User creer(User user) {
        user.setAge(calculateAge(user.getDateNaissance()));
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

    @Override
    public List<User> trierUtilisateurParNom() {
        List<User> utilisateurs=userRepository.findAll();
        return utilisateurs.stream().sorted(Comparator.comparing(User::getNom)).
                collect(Collectors.toList());
    }

    @Override
    public List<User> trierUtilisateurParPrenom() {
        List<User> utilisateurs=userRepository.findAll();
        return utilisateurs.stream().sorted(Comparator.comparing(User::getPrenom)).
                collect(Collectors.toList());
    }

    @Override
    public List<User> trierUtilisateurParSalaireCroissant() {
        List<User> utilisateurs=userRepository.findAll();
        return utilisateurs.stream().sorted(Comparator.comparingDouble(User::getSalaire)).
                collect(Collectors.toList());
    }

    @Override
    public List<User> trierUtilisateurParSalaireDecroissant() {
        List<User> utilisateurs=userRepository.findAll();
        return utilisateurs.stream().sorted(Comparator.comparingDouble(User::getSalaire).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<User> trierUtilisateurParAge() {
        List<User> utilisateurs = userRepository.findAll();
        return utilisateurs.stream()
                .sorted(Comparator.comparingInt(user -> calculateAge(user.getDateNaissance())))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByNom(String nom) {
        return userRepository.findByNomLike(nom);
    }

    @Override
    public List<User> findByPrenom(String prenom) {
        return userRepository.findByPrenomLike(prenom);
    }

    private int calculateAge(LocalDate dateNaissance) {
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.getYear() - dateNaissance.getYear();
        if (dateNaissance.getMonthValue() > currentDate.getMonthValue() ||
                (dateNaissance.getMonthValue() == currentDate.getMonthValue() &&
                        dateNaissance.getDayOfMonth() > currentDate.getDayOfMonth())) {
            age--;
        }
        return age;
    }


}
