package esprit.pi.demo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PostDto {
    long idPost;

    private String descPost;


}
