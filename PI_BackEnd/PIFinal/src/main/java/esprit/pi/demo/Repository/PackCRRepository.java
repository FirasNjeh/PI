package esprit.pi.demo.Repository;
import esprit.pi.demo.entities.Credit;
import esprit.pi.demo.entities.PackCR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackCRRepository extends JpaRepository<PackCR,Integer> {
}
