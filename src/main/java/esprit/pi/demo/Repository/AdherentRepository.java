package esprit.pi.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import esprit.pi.demo.entities.Adherent;

import java.util.List;

@Repository
public interface AdherentRepository extends JpaRepository<Adherent, Long> {
    List<Adherent> findAllByOrderByIdentifiantUniqueRNEAsc();
    List<Adherent> findAllByOrderByIdentifiantUniqueRNEDesc();

    List<Adherent> findAllByOrderByMatriculeFiscaleAsc();
    List<Adherent> findAllByOrderByMatriculeFiscaleDesc();

    List<Adherent> findAllByOrderByRaisonSocialeAsc();
    List<Adherent> findAllByOrderByRaisonSocialeDesc();

    List<Adherent> findAllByOrderByDateCreationAsc();
    List<Adherent> findAllByOrderByDateCreationDesc();
}
