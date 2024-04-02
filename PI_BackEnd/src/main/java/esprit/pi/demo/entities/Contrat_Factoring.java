package esprit.pi.demo.entities;

import esprit.pi.demo.entities.Enumeration.DelaiPaiement;
import esprit.pi.demo.entities.Enumeration.Historique;
import esprit.pi.demo.entities.Enumeration.Solvabilite;
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
public class Contrat_Factoring implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int ref;
    private LocalDate date;
    private String adherant;
    private float taux_avance;
    private float plafond;
    @Enumerated(EnumType.STRING)
    private Solvabilite solvabilite;
    @Enumerated(EnumType.STRING)
    private DelaiPaiement delaiPaiement;
    @Enumerated(EnumType.STRING)
    private Historique historique;
    private float taux_comm;
    @ToString.Exclude
    @OneToMany
    private List<AcheteurFactoring> acheteurFactorings;
    @ToString.Exclude
    @ManyToOne
    private User user;

}
