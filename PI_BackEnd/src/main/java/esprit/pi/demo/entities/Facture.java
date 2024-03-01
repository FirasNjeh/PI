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
public class Facture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int ref;
    private LocalDate date_emission;
    private LocalDate date_echeance;
    private float montant;
    @ToString.Exclude
    @ManyToOne
    private Contrat_Factoring contratFactorings;
}
