package esprit.pi.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import esprit.pi.demo.entities.Enumeration.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Credit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float montant;
    private float montantRestant;
    private LocalDate dateDeb;
    private Date datepp;
    private float paiementMensuel;
    private float amortissement;
    private float annuité;
    private int lateTimes;
    @Enumerated(EnumType.STRING)
    private CreditHistory creditHistory;
    @Enumerated(EnumType.STRING)
    private PackCredit packCredit;
    private float tauxInteret;
    private int duree;
    @Enumerated(EnumType.STRING)
    private StatusCredit statusCredit;
    @Enumerated(EnumType.STRING)
    private RelationGarant realtionGarant;
    @Enumerated(EnumType.STRING)
    private TypeCredit typeCredit;
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne()
    private PackCR packCR;
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private User userCR;
    @ToString.Exclude
    @JsonIgnore
    @OneToOne
    private Garant garant;
    @OneToMany(mappedBy = "creditM")
    @JsonIgnore
    private List<MonthlyPayment> monthlyPayment;

}
