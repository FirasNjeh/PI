package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AssuranceRepository extends JpaRepository<Assurance,Integer>
{
    Set<Assurance> findByPackAssur_Id(int idpack);
    Set<Assurance> findByUserAssurance_Id(int iduserAssurance);
}
