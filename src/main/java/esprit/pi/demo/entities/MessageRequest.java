package esprit.pi.demo.entities;


import lombok.*;



    @Getter
    @Setter
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public class MessageRequest {

        private String message ;

        private int senderid;
        private int receiverid;
        private int idconversation;


    }

