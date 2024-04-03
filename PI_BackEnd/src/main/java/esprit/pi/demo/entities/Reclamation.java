package esprit.pi.demo.entities;

import esprit.pi.demo.entities.Enumeration.PriorityLevel;
import esprit.pi.demo.entities.Enumeration.StatusRC;
import esprit.pi.demo.entities.Enumeration.TypeRC;
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
public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String objet;
    private String description;
    @Enumerated(EnumType.STRING)
    private StatusRC statusRC;
    private LocalDate dateRC;
    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel;
    @Enumerated(EnumType.STRING)
    private TypeRC typeRC;
    @ToString.Exclude
    @ManyToOne
    private User userReclamation;
    @ToString.Exclude
    @OneToOne
    private ReponseRC reponseRC;
}
