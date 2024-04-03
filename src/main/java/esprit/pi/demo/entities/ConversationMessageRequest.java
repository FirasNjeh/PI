package esprit.pi.demo.entities;


import lombok.*;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationMessageRequest {

    private int senderid;
    private int receiverid;

}

