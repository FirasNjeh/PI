package esprit.pi.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import esprit.pi.demo.entities.Enumeration.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
    @Temporal (TemporalType.DATE)
    private Date date_payement;//date achat
    private float montant_prime;
    private int duree_contrat;
    private float remboursement_en_cas_de_sinsitre;
    private float franchise; // montant a partir duquel l'assuré va commencer a payer



    //Agricole
    private String nom_exploitation_agricole;
    private String lieu_de_lexploitation;
    private float motant_de_couverture_souhaite;
    private float capitaleagricole_assuré;

    @Enumerated(EnumType.STRING)
    private TypeAgriculture typeagriculture;
    @Enumerated(EnumType.STRING)
    private TypeDeCouverture typedecouverture;


    //Sante
    private String Condition_medicale ;
    @Enumerated(EnumType.STRING)
    private TypeAssuranceSante typeAssuranceSante;
    @Enumerated(EnumType.STRING)
    private NiveauDeCouverture niveauDeCouverture;

    //ENTREPRENEUR
    private String nom_entreprise;
    private String adresse_entreprise;
    private String activité_entreprise;
    @Enumerated(EnumType.STRING)
    private TypeAssuranceEntrep typeAssuranceEntrep;
    @Enumerated(EnumType.STRING)
    private BienAssuré bien_assuré;
    private String historique_de_sinistralite; //Préciser les sinistres antérieurs, le cas échéant, et les réparations effectuées

    private String responsbilité_civile; //Couverture pour les dommages causés à des tiers pendant l'exercice de l'activité

    //Scolaire
    private String nom_enfant;
    private String prenom_enfant;
    private float capitalescolaire_assuré;

    private float montant_couverture;







    @JsonIgnore
    @ToString.Exclude
    @OneToMany (mappedBy = "assurance")
    private Set<Sinistre> sinistres;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    private PackAssur packAssur;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    private User userAssurance;
}
