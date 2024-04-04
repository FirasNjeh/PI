package esprit.pi.demo.Repository;
import esprit.pi.demo.entities.PackAssur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PackAssuranceRepository extends JpaRepository<PackAssur,Integer> {

}
