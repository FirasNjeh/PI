package esprit.pi.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "packCR")
    private List<Credit> creditP;
}
