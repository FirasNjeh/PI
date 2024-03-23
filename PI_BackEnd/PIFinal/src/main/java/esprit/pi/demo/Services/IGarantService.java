package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Garant;

import java.util.List;

public interface IGarantService {
    Garant saveGarant(Garant garant);
    List<Garant> getGarants();
    Garant getById(int id);
    Garant getGarantByLastName(String Nom);
    String deleteGarant (int id);
    Garant updateGarant(int id,Garant garant);
}
