package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Integer> {

}
