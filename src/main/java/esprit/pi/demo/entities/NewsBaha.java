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
public class NewsBaha implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String Image;
    private String description;
    @Enumerated(EnumType.STRING)
    private CategorieNews categorie;
    private LocalDate dateCreation;
    private int nbr_like;
    private int nbr_dislike;
    @ToString.Exclude
    @ManyToMany
    private List <User> userNews;
}
