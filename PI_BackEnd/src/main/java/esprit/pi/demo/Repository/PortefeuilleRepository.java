package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Portefeuille;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PortefeuilleRepository  extends JpaRepository<Portefeuille,Integer> {
}
