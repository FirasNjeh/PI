package esprit.pi.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Portefeuille implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float montant;
    private LocalDate date_creation;
    private int rib;
    @Enumerated(EnumType.STRING)
    private StatusPF statuspf;
    @ToString.Exclude
    @OneToMany(mappedBy = "portefeuilleTransaction")
    private List<TransactionCredit> transactions;
    @ToString.Exclude
    @OneToMany(mappedBy = "portefeuilleCreditCard")
    private List <CreditCard> creditCards;
    @ToString.Exclude
    @OneToOne(mappedBy = "portefeuilleUser")
    private User user;
@OneToMany(mappedBy = "portefeuilleTransactionA")
    private List<TransactionAssurance> TransactionA;
}
