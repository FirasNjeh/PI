package esprit.pi.demo.entities;

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
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private String theme;
    private String auteur;
    private int rating;
    @Enumerated(EnumType.STRING)
    private PrioriteQ prioriteQ;
    @Enumerated(EnumType.STRING)
    private StatusQ statusQ;
    @ToString.Exclude
    @OneToMany(mappedBy = "question")
    private List<ReponseQ> reponseQS;
    @ToString.Exclude
    @ManyToOne
    private Salon salonQ;

}
