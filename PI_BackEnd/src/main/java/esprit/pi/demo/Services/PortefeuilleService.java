package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.PortefeuilleRepository;
import esprit.pi.demo.entities.Portefeuille;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor

public class PortefeuilleService  implements  IPortefeuilleService{

    private PortefeuilleRepository portefeuilleRepository;

    @Override
    public Portefeuille creer(Portefeuille portefeuille) {
        return portefeuilleRepository.save(portefeuille);
    }

    @Override
    public List<Portefeuille> lire() {
        return portefeuilleRepository.findAll();
    }

    @Override
    public Portefeuille getPortefeuilleById(int id) {
        return portefeuilleRepository.findById(id).orElse(null);
    }

    @Override
    public Portefeuille modifier(int id, Portefeuille portefeuille) {
        return portefeuilleRepository.findById(id).map(pf -> {
            pf.setMontant(portefeuille.getMontant());
            pf.setDate_creation(portefeuille.getDate_creation());
            pf.setRib(portefeuille.getRib());
            pf.setStatuspf(portefeuille.getStatuspf());

            return portefeuilleRepository.save(pf);

        }).orElse(null);
    }

    @Override
    public String supprimer(int id) {
         portefeuilleRepository.deleteById(id);
         return "Portefeuille supprimé";
    }
}
