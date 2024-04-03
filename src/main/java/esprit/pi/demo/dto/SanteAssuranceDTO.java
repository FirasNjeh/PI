package esprit.pi.demo.dto;

import esprit.pi.demo.entities.Assurance;
import esprit.pi.demo.entities.NiveauDeCouverture;
import esprit.pi.demo.entities.TypeAssuranceSante;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SanteAssuranceDTO extends AssuranceDTO {
    private Date date_payement;//date achat
    private float montant_prime;
    private int duree_contrat;
    private float remboursement_en_cas_de_sinsitre;
    private float franchise; // montant a partir duquel l'assuré va commencer a payer
    private String Condition_medicale ;
    @Enumerated(EnumType.STRING)
    private TypeAssuranceSante typeAssuranceSante;
    @Enumerated(EnumType.STRING)
    private NiveauDeCouverture niveauDeCouverture;

    public Assurance toEntity() {
        Assurance assurance = new Assurance();
        assurance.setDate_payement(this.getDate_payement());
        assurance.setMontant_prime(this.getMontant_prime());
        assurance.setDuree_contrat(this.getDuree_contrat());
        assurance.setRemboursement_en_cas_de_sinsitre(this.getRemboursement_en_cas_de_sinsitre());
        assurance.setFranchise(this.getFranchise());
        assurance.setTypeAssuranceSante(this.getTypeAssuranceSante());
        assurance.setNiveauDeCouverture(this.getNiveauDeCouverture());
        assurance.setCondition_medicale(this.getCondition_medicale());
        return assurance;
    }
}
