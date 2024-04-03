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
public class Adherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String identifiantUniqueRNE;
    private long matriculeFiscale;
    private String raisonSociale;
    private LocalDate dateCreation;
    private long numTel;
    private String email;
    private String adresse;
    private String nomRepresentantLegal;
    private String prenomRepresentantLegal;
    private long cinRepresentantLegal;
    @Enumerated(EnumType.STRING)
    private FormeJuridique formeJuridique;
}
