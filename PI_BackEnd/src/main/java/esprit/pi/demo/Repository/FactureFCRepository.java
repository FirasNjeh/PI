package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.FactureFC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureFCRepository extends JpaRepository<FactureFC, Long> {
    List<FactureFC> findAllByOrderByNumeroFactureAsc();
    List<FactureFC> findAllByOrderByNumeroFactureDesc();

    List<FactureFC> findAllByOrderByDateEmissionAsc();
    List<FactureFC> findAllByOrderByDateEmissionDesc();

    List<FactureFC> findAllByOrderByDateEcheanceAsc();
    List<FactureFC> findAllByOrderByDateEcheanceDesc();

    List<FactureFC> findAllByOrderByMontantAsc();
    List<FactureFC> findAllByOrderByMontantDesc();

    List<FactureFC> findAllByOrderByAcheteurAsc();
    List<FactureFC> findAllByOrderByAcheteurDesc();
}
