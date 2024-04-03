package esprit.pi.demo.pi_back.Repository;

import esprit.pi.demo.pi_back.Entities.PriorityLevel;
import esprit.pi.demo.pi_back.Entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation,Integer> {
    Reclamation findByPriorityLevel(PriorityLevel priorityLevel);
}
