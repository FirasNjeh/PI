package esprit.pi.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import esprit.pi.demo.entities.Contrat;

import java.util.List;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    List<Contrat> findAllByOrderByReferenceContratAsc();
    List<Contrat> findAllByOrderByReferenceContratDesc();

    List<Contrat> findAllByOrderByMontantTotalFacturesAsc();
    List<Contrat> findAllByOrderByMontantTotalFacturesDesc();

    List<Contrat> findAllByOrderByAcheteurAsc();
    List<Contrat> findAllByOrderByAcheteurDesc();
}
