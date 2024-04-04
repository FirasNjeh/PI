package esprit.pi.demo.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;
}
