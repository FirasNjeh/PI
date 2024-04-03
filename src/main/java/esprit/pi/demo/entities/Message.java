package esprit.pi.demo.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Message implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idM;
    @Enumerated(EnumType.STRING)
    private EtatMessage etat;
    @Enumerated(EnumType.STRING)
    private TypeMessage typeeMessage;
    private String message ;
    private Date date;

    @OneToOne
    private User sender;
    @OneToOne
    private User receiver;


    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;




}