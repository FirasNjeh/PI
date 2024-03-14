package esprit.pi.demo.DTO;

import esprit.pi.demo.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private Role role;

}
