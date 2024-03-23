package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.PortefeuilleRepository;
import esprit.pi.demo.entities.Portefeuille;
import esprit.pi.demo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service @AllArgsConstructor
public class PortefeuilleService implements iPortefeuilleService{
    private PortefeuilleRepository repository;
    @Override
    public Portefeuille getPortefeuilleById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Portefeuille updatePortefeuille(int id, Portefeuille p) {
        Portefeuille existing = repository.findById(id).orElse(null);
        existing.setMontant(p.getMontant());

        return repository.save(existing);

    }
    @Override
    public List<Portefeuille> selectAll() {
        return repository.findAll();
    }

    private ServiceUser se;

    @Override
    public Portefeuille save(int idu,Portefeuille p){
        User u;
        u = se.getUserById(idu);


        return repository.save(p);}


}
