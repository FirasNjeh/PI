package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.TransactionAssurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionAssuranceRepository extends JpaRepository<TransactionAssurance,Integer> {
}
