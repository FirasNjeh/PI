package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Garant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GarantRepository extends JpaRepository<Garant,Integer> {
    List<Garant> findByName(String name);
}
