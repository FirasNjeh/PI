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


public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private int cin;
    private LocalDate dateNaissance;
    private int numtel;
    private String email;
    private String adresse;
    private String mdp;
    private String profession;
    @Enumerated(EnumType.STRING)
    private Role role;
    private int nbr_credit;
    private String image;
    private float salaire;
    private int AncienneteEmploi;
    private int matricule_fiscale;
    private boolean etat;
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<Contrat_Factoring> contratFactorings;
    @ToString.Exclude
    @OneToOne
    private Portefeuille portefeuilleUser;
    @ToString.Exclude
    @ManyToMany(mappedBy = "userNews")
    private List<NewsBaha> newsBaha;
    @ToString.Exclude
    @OneToMany(mappedBy = "userAssurance")
    private List <Assurance> assurances;
    @ToString.Exclude
    @OneToMany (mappedBy = "userCR")
    private List<Credit> credits;
    @ToString.Exclude
    @OneToMany (mappedBy = "userNotif")
    private List<Notification> notifications;
    @ToString.Exclude
   @OneToMany(mappedBy = "userReclamation")
    private List<Reclamation> reclamations;
    @ToString.Exclude
   @ManyToMany(mappedBy = "usersSalon")
    private List <Salon> salons;


}
