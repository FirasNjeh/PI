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
public class CreditCard  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_number;
    private LocalDate date_valide;
    private int cvv;
    private int num_card;
    @ToString.Exclude
    @ManyToOne
    private Portefeuille portefeuilleCreditCard;
}
