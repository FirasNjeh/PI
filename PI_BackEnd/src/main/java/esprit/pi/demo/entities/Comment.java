package esprit.pi.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

    @Entity
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor

    public class Comment implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long idCmnt;
        private String descCmnt;
        private Date dateCmnt;

        @JsonIgnore
        @ManyToOne
        private User user;
        @JsonIgnore
        @ManyToOne
        private Post post;
        @ManyToOne
        private Forum forum;
    }