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
public class Salon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @Enumerated(EnumType.STRING)
    private StatusSalon status_salon;
    private String theme;
    private LocalDate date_creation;
    private int nbr_place;
    @ToString.Exclude
    @OneToMany(mappedBy = "salonQ")
    private List<Question> questions;
    @ToString.Exclude
    @ManyToMany
    private  List<User> usersSalon;

}
