package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Portefeuille;

import java.util.List;

public interface iPortefeuilleService {
    Portefeuille getPortefeuilleById(int id);

    Portefeuille updatePortefeuille(int id, Portefeuille p);


    List<Portefeuille> selectAll();

    Portefeuille save(int id,Portefeuille p);
}
