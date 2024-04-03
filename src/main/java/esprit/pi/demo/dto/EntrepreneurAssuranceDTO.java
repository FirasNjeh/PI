package esprit.pi.demo.dto;

import esprit.pi.demo.entities.Assurance;
import esprit.pi.demo.entities.BienAssuré;
import esprit.pi.demo.entities.TypeAssuranceEntrep;
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
public class EntrepreneurAssuranceDTO extends  AssuranceDTO{
    private String nom_entreprise;
    private String adresse_entreprise;
    private String activité_entreprise;
    @Enumerated(EnumType.STRING)
    private TypeAssuranceEntrep typeAssuranceEntrep;
    @Enumerated(EnumType.STRING)
    private BienAssuré bien_assuré;
    private String historique_de_sinistralite; //Préciser les sinistres antérieurs, le cas échéant, et les réparations effectuées

    private String responsbilité_civile; //Couverture pour les dommages causés à des tiers pendant l'exercice de l'activité

    public Assurance toEntity() {
        Assurance assurance = new Assurance();
        assurance.setDate_payement(this.getDate_payement());
        assurance.setMontant_prime(this.getMontant_prime());
        assurance.setDuree_contrat(this.getDuree_contrat());
        assurance.setRemboursement_en_cas_de_sinsitre(this.getRemboursement_en_cas_de_sinsitre());
        assurance.setFranchise(this.getFranchise());
        assurance.setNom_entreprise(this.getNom_entreprise());
        assurance.setAdresse_entreprise(this.getAdresse_entreprise());
        assurance.setActivité_entreprise(this.getActivité_entreprise());
        assurance.setTypeAssuranceEntrep(this.getTypeAssuranceEntrep());
        assurance.setBien_assuré(this.getBien_assuré());
        assurance.setHistorique_de_sinistralite(this.getHistorique_de_sinistralite());
        assurance.setResponsbilité_civile(this.getResponsbilité_civile());
        return assurance;
    }
}
