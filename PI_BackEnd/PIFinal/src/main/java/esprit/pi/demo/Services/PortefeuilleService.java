package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.PortefeuilleRepository;
import esprit.pi.demo.entities.Portefeuille;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service @AllArgsConstructor
public class PortefeuilleService implements iPortefeuilleService{
    private PortefeuilleRepository repository;
    @Override
    public Portefeuille getPortefeuilleById(int id) {
        return repository.findById(id).orElse(null);
    }



}
