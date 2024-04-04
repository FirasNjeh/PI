package esprit.pi.demo.DTO;

import esprit.pi.demo.entities.Assurance;
import esprit.pi.demo.entities.Enumeration.TypeAgriculture;
import esprit.pi.demo.entities.Enumeration.TypeDeCouverture;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AgricoleAssuranceDTO extends  AssuranceDTO{
    private String nom_exploitation_agricole;
    private String lieu_de_lexploitation;
    private float motant_de_couverture_souhaite;
    private float capitaleagricole_assuré;

    @Enumerated(EnumType.STRING)
    private TypeAgriculture typeagriculture;
    @Enumerated(EnumType.STRING)
    private TypeDeCouverture typedecouverture;

    public Assurance toEntity() {
        Assurance assurance = new Assurance();
        assurance.setDate_payement(this.getDate_payement());
        assurance.setMontant_prime(this.getMontant_prime());
        assurance.setDuree_contrat(this.getDuree_contrat());
        assurance.setRemboursement_en_cas_de_sinsitre(this.getRemboursement_en_cas_de_sinsitre());
        assurance.setFranchise(this.getFranchise());
        assurance.setNom_exploitation_agricole(this.getNom_exploitation_agricole());
        assurance.setLieu_de_lexploitation(this.getLieu_de_lexploitation());
        assurance.setMotant_de_couverture_souhaite(this.getMotant_de_couverture_souhaite());
        assurance.setCapitaleagricole_assuré(this.getCapitaleagricole_assuré());
        assurance.setTypeagriculture(this.getTypeagriculture());
        assurance.setTypedecouverture(this.getTypedecouverture());
        return assurance;
    }
}
