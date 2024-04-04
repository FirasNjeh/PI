package esprit.pi.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import esprit.pi.demo.entities.Acheteur;

import java.util.List;

@Repository
public interface AcheteurRepository extends JpaRepository<Acheteur, Long> {
    List<Acheteur> findAllByOrderByMatriculeFiscaleAsc();
    List<Acheteur> findAllByOrderByMatriculeFiscaleDesc();

    List<Acheteur> findAllByOrderByRaisonSocialeAsc();
    List<Acheteur> findAllByOrderByRaisonSocialeDesc();

    List<Acheteur> findAllByOrderByDateCreationAsc();
    List<Acheteur> findAllByOrderByDateCreationDesc();
}
