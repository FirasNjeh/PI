package esprit.pi.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String nom;
    private String prenom;
    private String email;
    private int cin;
    private LocalDate dateNaissance;
    private int numtel;
    private String adresse;
    private String profession;
    private float salaire;
    private int age;

}
