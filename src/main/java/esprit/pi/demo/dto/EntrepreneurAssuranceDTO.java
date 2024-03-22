package esprit.pi.demo.dto;

import esprit.pi.demo.entities.BienAssuré;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntrepreneurAssuranceDTO {
    private Date date_payement;//date achat
    private float montant_prime;
    private int duree_contrat;
    private float remboursement_en_cas_de_sinsitre;
    private float franchise; // montant a partir duquel l'assuré va commencer a payer
    private String nom_entreprise;
    private String adresse_entreprise;
    private String activité_entreprise;
    private BienAssuré bien_assuré;
    private String historique_de_sinistralite;
    private String responsbilité_civile;
}
