package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Sinistre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

@Repository
public interface SinistreRepository extends JpaRepository<Sinistre,Integer> {

    Set<Sinistre> findByAssurance_Id(int idassur);

    @Query("SELECT COUNT(s) FROM Sinistre s JOIN s.assurance a WHERE a.userAssurance.id = :userId")
    Long countSinistresByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(s) FROM Sinistre s JOIN s.assurance a JOIN a.packAssur p WHERE p.id = :packAssurId")
    Long countSinistresByPackAssurId(@Param("packAssurId") Integer packAssurId);

    @Query("SELECT COUNT(s) FROM Sinistre s JOIN s.assurance a WHERE a.userAssurance.id = :userId AND YEAR(s.dateSinistre) >= YEAR(CURRENT_DATE) - :n")
    Long countSinistresByUserLastNYear(@Param("userId") Integer userId, @Param("n") Integer n);
}
