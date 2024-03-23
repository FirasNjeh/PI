package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Garant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GarantRepository extends JpaRepository<Garant,Integer> {
    Garant findByName(String name);
}
