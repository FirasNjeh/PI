package esprit.pi.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private int cin;
    private LocalDate dateNaissance;
    private int age;
    private int numtel;
    private String email;
    private String adresse;
    private String mdp;
    private String profession;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private Role role;
    private int nbr_credit;
    private String image;
    private float salaire;
    private int matriculeFiscale;
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
    @OneToMany(mappedBy = "userToken")
    private List<Token> tokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getPassword() {
        return this.mdp;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
