package esprit.pi.demo.entities;

import esprit.pi.demo.entities.Enumeration.PriorityLevel;
import esprit.pi.demo.entities.Enumeration.StatusRC;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name="Reclamation")
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String object;
    private String description;
    @Enumerated(EnumType.STRING)
    private StatusRC statusRC;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date dateRC;
    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel;
    private String typeRC;
    @ToString.Exclude
    @ManyToOne
    private User userReclamation;
    @Lob
    private byte[] image;

    /*@ToString.Exclude
            @ManyToOne
            private User userReclamation;*/
   // @ToString.Exclude
    //@OneToOne(mappedBy = "reclamation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private ReponseReclamation reponseRC;
    @OneToOne(mappedBy = "reclamation")
    @JoinColumn(name = "rep")
    private ReponseReclamation reponseRC;

    public Reclamation(String object, String description, StatusRC statusRC, Date dateRC, PriorityLevel priorityLevel, String typeRC, ReponseReclamation reponseRC) {
        this.object = object;
        this.description = description;
        this.statusRC = statusRC;
        this.dateRC = dateRC;
        this.priorityLevel = priorityLevel;
        this.typeRC = typeRC;
        this.reponseRC = reponseRC;
    }

    public Reclamation(String object, String description, StatusRC statusRC, Date dateRC, PriorityLevel priorityLevel, String typeRC, byte[] image) {
        this.object = object;
        this.description = description;
        this.statusRC = statusRC;
        this.dateRC = dateRC;
        this.priorityLevel = priorityLevel;
        this.typeRC = typeRC;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusRC getStatusRC() {
        return statusRC;
    }

    public void setStatusRC(StatusRC statusRC) {
        this.statusRC = statusRC;
    }

    public Date getDateRC() {
        return dateRC;
    }

    public void setDateRC(Date dateRC) {
        this.dateRC = dateRC;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getTypeRC() {
        return typeRC;
    }

    public void setTypeRC(String typeRC) {
        this.typeRC = typeRC;
    }

    public ReponseReclamation getReponseRC() {
        return reponseRC;
    }

    public void setReponseRC(ReponseReclamation reponseRC) {
        this.reponseRC = reponseRC;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
