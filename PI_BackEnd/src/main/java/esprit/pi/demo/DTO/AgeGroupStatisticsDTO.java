package esprit.pi.demo.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AgeGroupStatisticsDTO {

    private double pourcentageJeunesAdultes;
    private double pourcentageAdultes;
    private double pourcentageAinesTroisiemeAge;
    private double pourcentageAinesQuatriemeAge;

}
