package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard,Integer> {
}
