package esprit.pi.demo.entities;

import esprit.pi.demo.entities.Enumeration.PackAssurance;
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
public class Assurance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate date_payement;
    private float montant_prime;
    @Enumerated(EnumType.STRING)
    private PackAssurance packAssurance;
    private float franchise;
    private float remboursement;
    private LocalDate date_remboursement;
    @ToString.Exclude
    @ManyToMany (mappedBy = "assuranceSinistre")
    private List <Sinistre> sinistres;
    @ToString.Exclude
    @ManyToOne
    private PackAssur packAssur;
    @ToString.Exclude
    @ManyToOne
    private User userAssurance;
}
