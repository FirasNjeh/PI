package esprit.pi.demo.Services;

import esprit.pi.demo.DTO.AgeGroupStatisticsDTO;
import esprit.pi.demo.DTO.ChangePasswordRequest;
import esprit.pi.demo.DTO.GenderStatisticsDTO;
import esprit.pi.demo.DTO.UpdateUserRequest;
import esprit.pi.demo.Repository.UserRepository;
import esprit.pi.demo.entities.Enumeration.Genre;
import esprit.pi.demo.entities.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServiceUser implements IServiceUser {

  private UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
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
            user1.setProfession(user.getProfession());
            user1.setRole(user.getRole());
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
        List<User> users=userRepository.findAll();
        return users.stream().sorted(Comparator.comparing(User::getNom)).
                collect(Collectors.toList());
    }

    @Override
    public List<User> trierUtilisateurParPrenom() {
        List<User> users=userRepository.findAll();
        return users.stream().sorted(Comparator.comparing(User::getPrenom)).
                collect(Collectors.toList());
    }

    @Override
    public List<User> trierUtilisateurParSalaireCroissant() {
        List<User> users =userRepository.findAll();
        return users.stream().sorted(Comparator.comparingDouble(User::getSalaire)).
                collect(Collectors.toList());
    }

    @Override
    public List<User> trierUtilisateurParSalaireDecroissant() {
        List<User> users =userRepository.findAll();
        return users.stream().sorted(Comparator.comparingDouble(User::getSalaire).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<User> trierUtilisateurParAge() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .sorted(Comparator.comparingInt(user -> calculateAge(user.getDateNaissance())))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> trierUtilisateurParRole() {
        List<User> users =userRepository.findAll();
        return users.stream().sorted(Comparator.comparing(User::getRole)).
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

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user= (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong Password");
        }
        if(!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new IllegalStateException("Password are not the same");
        }
        user.setMdp(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void banUser(int userId) {
        Optional<User> optionalUser =userRepository.findById(userId);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setBanni(true);
            userRepository.save(user);
        }
        else {
            throw new EntityNotFoundException("Utilisateur inexistant : " + userId);
        }
    }

    @Override
    public void debanUser(int userId) {

        Optional<User> optionalUser =userRepository.findById(userId);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setBanni(false);
            userRepository.save(user);
        }
        else {
            throw new EntityNotFoundException("Utilisateur inexistant " );
        }

    }

    @Override
    public User getCurrentUser(Principal connectedUser) {
        if (connectedUser instanceof UsernamePasswordAuthenticationToken) {
            Object principal = ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            if (principal instanceof UserDetails) {
                return (User) principal;
            }
        }
        return null;
    }

    @Override
    public void updateCurrentUser(Principal connectedUser, UpdateUserRequest updatedUser) {
        User currentUser=getCurrentUser(connectedUser);
        if(updatedUser.getNom() != null) currentUser.setNom(updatedUser.getNom());
        if(updatedUser.getPrenom() != null) currentUser.setPrenom(updatedUser.getPrenom());
        if(updatedUser.getEmail() != null) currentUser.setEmail(updatedUser.getEmail());
        if(updatedUser.getCin() != -1) currentUser.setCin(updatedUser.getCin());
        if(updatedUser.getDateNaissance() != null) currentUser.setDateNaissance(updatedUser.getDateNaissance());
        if(updatedUser.getNumtel() != -1) currentUser.setNumtel(updatedUser.getNumtel());
        if(updatedUser.getProfession() != null) currentUser.setProfession(updatedUser.getProfession());
        if(updatedUser.getSalaire() != -1) currentUser.setSalaire(updatedUser.getSalaire());
        if(updatedUser.getAge() != -1)
            if (updatedUser.getDateNaissance() != null) {
            currentUser.setAge(calculateAge(updatedUser.getDateNaissance()));
        }
        userRepository.save(currentUser) ;
    }



    public int calculateAge(LocalDate dateNaissance) {
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
