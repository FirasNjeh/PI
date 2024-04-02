package esprit.pi.demo.entities;

import esprit.pi.demo.entities.Enumeration.TypeTransaction;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class TransactionCredit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate date;
    private Long rib_source;
    private Long rib_destination;
    private float montant;
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;
    @ToString.Exclude
    @ManyToOne
    private Portefeuille portefeuilleTransaction;
}
