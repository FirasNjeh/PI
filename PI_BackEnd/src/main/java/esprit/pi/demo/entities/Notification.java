package esprit.pi.demo.entities;

import esprit.pi.demo.entities.Enumeration.StatusNotif;
import esprit.pi.demo.entities.Enumeration.TypeNotif;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
    private Date date;
    @Enumerated(EnumType.STRING)
    private TypeNotif typeNotif;
    @Enumerated(EnumType.STRING)
    private StatusNotif statusNotif;
    @ToString.Exclude
    @ManyToOne
    private User userNotif;
}
