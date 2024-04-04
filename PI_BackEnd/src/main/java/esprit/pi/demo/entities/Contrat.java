package esprit.pi.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String referenceContrat;
    private double tauxAvance;
    private double montantTotalFactures;
    private double tauxInteret;
    private double impayesAdherent;
    private double impayesAcheteurs;
    private double endettementAdherent;
    private double endettementAcheteur;
    private double caAdherent;
    private double caAcheteur;
    private double encoursCLTSAdherent;
    private double encoursFRSAcheteur;
    private double frAdherent;
    private double frAcheteur;
    private double bfrAdherent;
    private double bfrAcheteur;
    private double capitalAdherent;
    private double capitalAcheteur;
    @ManyToOne
    private Acheteur acheteur;
    @OneToMany
    private Set<FactureFC> factureFCS;
}
