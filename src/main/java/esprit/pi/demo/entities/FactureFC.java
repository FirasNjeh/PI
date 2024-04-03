package esprit.pi.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FactureFC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String numeroFacture;
    private LocalDate dateEmission;
    private LocalDate dateEcheance;
    private float montant;
    @Enumerated(EnumType.STRING)
    private StatutFC statut;
    @ManyToOne
    private Acheteur acheteur;
}
