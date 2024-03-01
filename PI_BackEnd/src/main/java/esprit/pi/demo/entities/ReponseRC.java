package esprit.pi.demo.entities;

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
public class ReponseRC implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDate date_rep;
    private String Reponse;
    @ToString.Exclude
    @OneToOne(mappedBy = "reponseRC")
    private Reclamation reclamation;
}
