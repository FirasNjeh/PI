package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.GarantRepository;
import esprit.pi.demo.entities.Garant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
@AllArgsConstructor
@Service
public class GarantService implements IGarantService{
    private GarantRepository repository;
    @Override
    public Garant saveGarant(Garant garant) {
        return repository.save(garant);
    }

    @Override
    public List<Garant> getGarants() {
        return repository.findAll();
    }

    @Override
    public Garant getById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Garant> getGarantByLastName(String name) {
        return repository.findByName(name);
    }

    @Override
    public String deleteGarant(int id) {
        repository.deleteById(id);
        return "Garant supprimé avec succès !!" +id;
    }

    @Override
    public Garant updateGarant(int id, Garant garant) {
        Garant existingGarant=repository.findById(id).orElse(null);
        existingGarant.setName(garant.getName());
        existingGarant.setPrenomGarant(garant.getPrenomGarant());
        existingGarant.setSalaire_garant(garant.getSalaire_garant());
        return repository.save(existingGarant);
    }
}
