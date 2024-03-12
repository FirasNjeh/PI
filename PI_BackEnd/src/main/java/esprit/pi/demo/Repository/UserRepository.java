package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Integer> {
List<User> findByNomLike(String nom);
List<User> findByPrenomLike(String prenom);
List<User> findByPrenomLikeAndNomLike(String prenom,String nom);
User findByCin(int cin);
User findByMatriculeFiscale(int matriculeFiscale);
@Query("select avg (u.age) from User u")
    double calculerAgeMoyen();
Optional<User> findByEmail (String Email);


}
