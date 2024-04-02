package esprit.pi.demo.DTO;

import esprit.pi.demo.entities.Enumeration.Genre;
import esprit.pi.demo.entities.Enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private int cin;
    private LocalDate dateNaissance;
    private Role role;
    private int numtel;
    private String adresse;
    private String profession;
    private Genre genre;
    private float salaire;
    private int age;
    private boolean banni;

}
