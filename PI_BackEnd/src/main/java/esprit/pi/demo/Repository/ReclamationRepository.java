package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Enumeration.PriorityLevel;
import esprit.pi.demo.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation,Integer> {
    Reclamation findByPriorityLevel(PriorityLevel priorityLevel);
}
