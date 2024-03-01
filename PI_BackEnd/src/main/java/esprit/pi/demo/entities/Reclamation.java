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
