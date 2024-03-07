package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User,Integer> {
List<User> findByNomLike(String nom);
List<User> findByPrenomLike(String prenom);

}
