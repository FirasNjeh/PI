package esprit.pi.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int rib_source;
    private int rib_destination;
    private float montant;
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private Portefeuille portefeuilleTransaction;
}
