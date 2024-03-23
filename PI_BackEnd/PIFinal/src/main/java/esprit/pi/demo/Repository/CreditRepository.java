package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Credit;
import esprit.pi.demo.entities.StatusCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Integer>
{
    List<Credit> findByStatusCredit(StatusCredit statusCredit);
    List<Credit> findByMontant(float montant);
    List<Credit> findByDuree(int duree);

    List<Credit> getCreditByMontantLessThan(float m);
    List<Credit> getCreditByMontantGreaterThan(float m);

//    @Query(value = "SELECT * FROM credit c WHERE YEAR(c.date_deb) =?1",nativeQuery = true)
//    List<Credit> creditParAnnee(int annnee);
//

}
