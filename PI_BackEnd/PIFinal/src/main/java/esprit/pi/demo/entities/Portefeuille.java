package esprit.pi.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToMany(mappedBy = "portefeuilleTransaction")
    private List<TransactionCredit> transactions;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "portefeuilleCreditCard")
    private List <CreditCard> creditCards;
    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "portefeuilleUser")
    private User user;

}
