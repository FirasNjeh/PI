package esprit.pi.demo.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class File {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;
    @JsonIgnore
    @ManyToOne
    Sinistre sinistre;
    @JsonIgnore
    @ManyToOne
    PackAssur packAssur;

    public File(String name, String type, byte[] data, Sinistre sinistre) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.sinistre = sinistre;
    }

    public File(String name, String type, byte[] data, PackAssur packAssur) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.packAssur = packAssur;
    }
}
