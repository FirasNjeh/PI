package esprit.pi.demo.dto;

import esprit.pi.demo.entities.TypeAgriculture;
import esprit.pi.demo.entities.TypeDeCouverture;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AgricoleAssuranceDTO {
    private Date date_payement;//date achat
    private float montant_prime;
    private int duree_contrat;
    private float remboursement_en_cas_de_sinsitre;
    private float franchise; // montant a partir duquel l'assuré va commencer a rembourser
    private String nom_exploitation_agricole;
    private String lieu_de_lexploitation;
    private float surface_totale_exploitation;
    private TypeAgriculture typeagriculture;
    private float motant_de_couverture_souhaite;
    private TypeDeCouverture typedecouverture;
}
