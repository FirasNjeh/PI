package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.MonthlyPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyPaymentRepository extends JpaRepository<MonthlyPayment,Integer> {
    @Query("SELECT m FROM MonthlyPayment m WHERE m.creditM.id= :id_credit ")
    List<MonthlyPayment> getCredit_DuesHistory(@Param("id_credit") int id);
}
