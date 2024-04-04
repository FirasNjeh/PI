package esprit.pi.demo.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {
    private String descPost;
    private byte[] imageData;
    private int userId;
}


