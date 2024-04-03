package esprit.pi.demo.entities;

import esprit.pi.demo.entities.Enumeration.PackCredit;
import esprit.pi.demo.entities.Enumeration.RelationGarant;
import esprit.pi.demo.entities.Enumeration.StatusCredit;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDate dateDeb;
    private LocalDate dateFin;
    private float paiementMensuel;
    @Enumerated(EnumType.STRING)
    private PackCredit packCredit;
    private float tauxInteret;
    private int duree;
    @Enumerated(EnumType.STRING)
    private StatusCredit statusCredit;
    @Enumerated(EnumType.STRING)
    private RelationGarant realtionGarant;
    @ToString.Exclude
    @ManyToOne
    private PackCR packCR;
    @ToString.Exclude
    @ManyToOne
    private User userCR;
    @ToString.Exclude
    @OneToOne
    private Garant garant;

}
