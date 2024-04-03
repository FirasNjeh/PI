package esprit.pi.demo.dto;

import esprit.pi.demo.entities.Assurance;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ScolaireAssuranceDTO extends AssuranceDTO {
    private String nom_enfant;
    private String prenom_enfant;
    private float capitalescolaire_assuré;
    private float montant_couverture;

    public Assurance toEntity() {
        Assurance assurance = new Assurance();
        assurance.setDate_payement(this.getDate_payement());
        assurance.setMontant_prime(this.getMontant_prime());
        assurance.setDuree_contrat(this.getDuree_contrat());
        assurance.setRemboursement_en_cas_de_sinsitre(this.getRemboursement_en_cas_de_sinsitre());
        assurance.setFranchise(this.getFranchise());
        assurance.setNom_enfant(this.getNom_enfant());
        assurance.setPrenom_enfant(this.getPrenom_enfant());
        assurance.setCapitalescolaire_assuré(this.getCapitalescolaire_assuré());
        assurance.setMontant_couverture(this.getMontant_couverture());
        return assurance;
    }
}
