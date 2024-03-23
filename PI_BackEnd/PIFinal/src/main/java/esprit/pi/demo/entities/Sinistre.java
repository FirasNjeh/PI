package esprit.pi.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Sinistre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateSinistre;
    private String image;
    private float estimation_expert;
    private String lieu;
    private String description;
    @ToString.Exclude
    @ManyToMany
    private List <Assurance> assuranceSinistre;
}
