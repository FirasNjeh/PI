package esprit.pi.demo.dto;

import esprit.pi.demo.entities.Assurance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AssuranceDTO {
    private Date date_payement;
    private float montant_prime;
    private int duree_contrat;
    private float remboursement_en_cas_de_sinsitre;
    private float franchise; // montant a partir duquel l'assuré va commencer a payer

}
