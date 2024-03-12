package esprit.pi.demo.Services;

import esprit.pi.demo.DTO.AgeGroupStatisticsDTO;
import esprit.pi.demo.entities.User;
import esprit.pi.demo.DTO.GenderStatisticsDTO;
import java.util.List;

public interface IServiceUser {
    User creer(User user);
    List<User> lire();
    User getUserById (int id);
    User modifier(int id, User user);
    String supprimer(int id);
    List <User> trierUtilisateurParNom();
    List <User> trierUtilisateurParPrenom();
    List <User> trierUtilisateurParSalaireCroissant();
    List <User> trierUtilisateurParSalaireDecroissant();
    List <User> trierUtilisateurParAge();
    List <User> trierUtilisateurParRole();

    List<User> findByNom(String nom);
    List<User> findByPrenom(String prenom);
    List<User> findByPrenomAndNom(String prenom,String nom);
    User findByCinLike(int cin);
    User findByMatricule_fiscale(int matriculeFiscale);
    double calculerAgeMoyenUsers();
     GenderStatisticsDTO obtenirStatistiquesGenre();
    AgeGroupStatisticsDTO obtenirStatistiquesTranchesAge();






}
