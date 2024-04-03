package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeRepository extends JpaRepository<Groupe,Long> {
}
