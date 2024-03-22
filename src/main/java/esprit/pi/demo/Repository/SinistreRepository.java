package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Sinistre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SinistreRepository extends JpaRepository<Sinistre,Integer> {

    Set<Sinistre> findByAssurance_Id(int idassur);
}
