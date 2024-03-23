package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.TransactionCredit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCRepository extends JpaRepository<TransactionCredit,Integer> {

//    @Query("SELECT t FROM TransactionCredit t " +
//            "WHERE t.portefeuilleTransaction.user.id = :userId " +
//            "AND YEAR(t.date) = :year")
//    List<TransactionCredit> findTransactionsByUserAndYear(int userId, int year);

//    @Query("SELECT SUM(tc.montant) FROM TransactionCredit tc " +
//            "JOIN tc.portefeuilleTransaction p " +
//            "JOIN p.user u " +
//            "WHERE YEAR(tc.date) = :annee AND u.id = :userId AND tc.typeTransaction = 'DEPOT'")
//    Float sumDepositsForUserAndYear(@Param("annee") int annee, @Param("userId") int userId);

}
