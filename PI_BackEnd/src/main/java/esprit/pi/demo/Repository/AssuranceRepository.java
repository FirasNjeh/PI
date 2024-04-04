package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AssuranceRepository extends JpaRepository<Assurance,Integer>
{
    Set<Assurance> findByPackAssur_Id(int idpack);
    Set<Assurance> findByUserAssurance_Id(int iduserAssurance);
    @Query("SELECT COUNT(a) FROM Assurance a WHERE a.userAssurance.id = :userId")
    Long countAssurancesByUserId(@Param("userId") Integer userId);
    @Query("SELECT COUNT(a) FROM Assurance a WHERE a.userAssurance.id = :userId AND YEAR(a.date_payement) >= YEAR(CURRENT_DATE) - :n")
    Long countAssurancesByUserLastNYear(@Param("userId") Integer userId, @Param("n") Integer n);
}
