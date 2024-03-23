package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.PackCRRepository;
import esprit.pi.demo.entities.PackCR;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PackCRService implements IPackCRService {

    private PackCRRepository repository;
    //private CreditService cs;
    //Agent
    @Override
    public PackCR savePackCR(PackCR packCR){
//        Credit c;
//        c=cs.getCreditById(id);
//        packCR.setCreditP((List<Credit>) c);

        return repository.save(packCR);
    }

    //agent + client
    @Override
    public List<PackCR> getPacksCR(){
        return repository.findAll();
    }
    //agent
    @Override
    public PackCR getPackCRById(int id){
        return repository.findById(id).orElse(null);
    }
    //agent
    @Override
    public String deletePackCR (int id){
        repository.deleteById(id);
        return "Pack Crédit supprimé !!" +id;
    }
    //agent
    @Override
    public PackCR updatePackCR(int id,PackCR packCR){
        PackCR existingPackCR=repository.findById(id).orElse(null);
        existingPackCR.setPackCredit(packCR.getPackCredit());
        existingPackCR.setDescription(packCR.getDescription());
        existingPackCR.setNom(packCR.getNom());
        existingPackCR.setImage(packCR.getImage());
        existingPackCR.setMontantMin(packCR.getMontantMin());
        existingPackCR.setMontantMax(packCR.getMontantMax());

        return repository.save(existingPackCR);

    }
}
