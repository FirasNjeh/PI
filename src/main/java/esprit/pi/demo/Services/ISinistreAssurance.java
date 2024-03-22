package esprit.pi.demo.Services;


import esprit.pi.demo.entities.Sinistre;

import java.util.List;
import java.util.Set;

public interface ISinistreAssurance {
    Sinistre saveSinistre(Sinistre sinistre);

    List<Sinistre> getSinsitre();

    Sinistre getSinistreById(int id);

    String deleteSinistre(int id);

    Sinistre updateSinistre(int id,Sinistre sinistre);

    Set<Sinistre> getListSinistesByAssurance(int idassur);

    Sinistre createSinistreWithAssurance(int idassur, Sinistre sinistre);

    Sinistre acceptSinistre(int idsin);

    Sinistre refuseSinistre(int idsin);
}
