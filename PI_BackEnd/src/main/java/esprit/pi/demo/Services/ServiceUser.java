package esprit.pi.demo.Services;

import esprit.pi.demo.DTO.AgeGroupStatisticsDTO;
import esprit.pi.demo.DTO.GenderStatisticsDTO;
import esprit.pi.demo.Repository.UserRepository;
import esprit.pi.demo.entities.Genre;
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
//            user1.setUsername(user.getUsername());
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
            user.setMatriculeFiscale(user.getMatriculeFiscale());
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
    public List<User> trierUtilisateurParRole() {
        List<User> utilisateurs=userRepository.findAll();
        return utilisateurs.stream().sorted(Comparator.comparing(User::getRole)).
                collect(Collectors.toList());
    }

    @Override
    public List<User> findByNom(String nom) {
        return userRepository.findByNomLike(nom);
    }

    @Override
    public List<User> findByPrenom(String prenom) {
        return userRepository.findByPrenomLike(prenom);
    }

    @Override
    public List<User> findByPrenomAndNom(String prenom,String nom) {
        return userRepository.findByPrenomLikeAndNomLike(prenom,nom);
    }

    @Override
    public User findByCinLike(int cin) {
        return userRepository.findByCin(cin);
    }

    @Override
    public User findByMatricule_fiscale(int matriculeFiscale) {
        return userRepository.findByMatriculeFiscale(matriculeFiscale);
    }

    @Override
    public double calculerAgeMoyenUsers() {
        return userRepository.calculerAgeMoyen();
    }

    @Override
    public GenderStatisticsDTO obtenirStatistiquesGenre() {
        List<User> users = userRepository.findAll();
        long totalUsers = users.size();
        long hommes = users.stream()
                .filter(user -> user.getGenre() == Genre.HOMME)
                .count();
        double pourcentageHommes = (hommes * 100.0) / totalUsers;
        double pourcentageFemmes = 100.0 - pourcentageHommes;

        return new GenderStatisticsDTO(pourcentageHommes, pourcentageFemmes);
    }

    @Override
    public AgeGroupStatisticsDTO obtenirStatistiquesTranchesAge() {
        List<User> users = userRepository.findAll();
        long totalUsers = users.size();

        long jeunesAdultes = users.stream()
                .filter(user -> user.getAge() >= 18 && user.getAge() <= 35)
                .count();
        long adultes = users.stream()
                .filter(user -> user.getAge() > 35 && user.getAge() <= 65)
                .count();
        long ainesTroisiemeAge = users.stream()
                .filter(user -> user.getAge() > 65 && user.getAge() <= 80)
                .count();
        long ainesQuatriemeAge = users.stream()
                .filter(user -> user.getAge() > 80)
                .count();
        double pourcentageJeunesAdultes = (jeunesAdultes * 100.0) / totalUsers;
        double pourcentageAdultes = (adultes * 100.0) / totalUsers;
        double pourcentageAinesTroisiemeAge = (ainesTroisiemeAge * 100.0) / totalUsers;
        double pourcentageAinesQuatriemeAge = (ainesQuatriemeAge * 100.0) / totalUsers;

        return new AgeGroupStatisticsDTO(pourcentageJeunesAdultes, pourcentageAdultes, pourcentageAinesTroisiemeAge, pourcentageAinesQuatriemeAge);
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
