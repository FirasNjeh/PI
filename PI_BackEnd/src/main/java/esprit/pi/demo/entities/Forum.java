package esprit.pi.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_forum ;
    private String title_forum ;
    private String description_forum ;
    private LocalDate date_forum  ;
    private long nbr_like ;
    private long nbr_signal ;
    private String category_forum ;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "forum")
    private Set<Comment> comments;

}
