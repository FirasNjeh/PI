package esprit.pi.demo.entities;

import esprit.pi.demo.entities.Enumeration.PackCredit;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PackCR implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private float montantMin;
    private float montantMax;
    private String image;
    @Enumerated(EnumType.STRING)
    private PackCredit packCredit;
}
