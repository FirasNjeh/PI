package esprit.pi.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
@ToString

public class MonthlyPayment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private Date SupposedDate;
    private LocalDate paymentDate;
    private int lateDays;
    private float montant;
    @Enumerated(EnumType.STRING)
    private StatusMonthlyPayment status;
    @ManyToOne
    @JsonIgnore
    Credit creditM;
}
