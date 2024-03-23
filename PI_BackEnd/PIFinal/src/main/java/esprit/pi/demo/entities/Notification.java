package esprit.pi.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Notification  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String msg;
    private String objet;
    @Enumerated(EnumType.STRING)
    private TypeNotif typeNotif;
    @Enumerated(EnumType.STRING)
    private StatusNotif statusNotif;
    @ToString.Exclude
    @ManyToOne
    private User userNotif;
}
