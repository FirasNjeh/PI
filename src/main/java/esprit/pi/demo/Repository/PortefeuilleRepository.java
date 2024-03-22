package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.PackAssur;
import esprit.pi.demo.entities.Portefeuille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortefeuilleRepository extends JpaRepository<Portefeuille,Integer> {
}
