package esprit.pi.demo.Services;

import esprit.pi.demo.entities.PackAssur;


import java.util.List;
import java.util.Map;

public interface IPackAssurService {
    PackAssur savePackAssur(PackAssur Packassur);

    List<PackAssur> getPacksAssur();

    PackAssur getPackAssurById(int id);

    String deletePackAssur(int id);

    PackAssur updatePackAssur(int id,PackAssur packAssur);

    Map<String, Integer> getPackAssurAssuranceCounts();

    String findPackAssurNomWithMostAssurances();

    String findPackAssurNomWithLeastAssurances();
}
