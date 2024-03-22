package esprit.pi.demo.dto;

import esprit.pi.demo.entities.NiveauDeCouverture;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SanteAssuranceDTO {
    private Date date_payement;//date achat
    private float montant_prime;
    private int duree_contrat;
    private float remboursement_en_cas_de_sinsitre;
    private float franchise; // montant a partir duquel l'assuré va commencer a payer
    private String Condition_medicale;
    private NiveauDeCouverture niveauDeCouverture;
}
