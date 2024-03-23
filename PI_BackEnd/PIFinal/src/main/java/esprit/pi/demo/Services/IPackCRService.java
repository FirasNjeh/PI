package esprit.pi.demo.Services;

import esprit.pi.demo.entities.PackCR;

import java.util.List;

public interface IPackCRService {
    PackCR savePackCR(PackCR packCR);
    List<PackCR> getPacksCR();
    PackCR getPackCRById(int id);
    String deletePackCR (int id);
    PackCR updatePackCR(int id,PackCR packCR);

    }
