package esprit.pi.demo.pi_back.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="ReponseRC")
public class ReponseReclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition="TEXT")
    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateReponse;
    private String reponse;
    ///@ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rec")
    private Reclamation reclamation;

    public ReponseReclamation(String description, Date dateReponse, String reponse, Reclamation reclamation) {
        this.description = description;
        this.dateReponse = dateReponse;
        this.reponse = reponse;
        this.reclamation = reclamation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(Date dateReponse) {
        this.dateReponse = dateReponse;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }
}
