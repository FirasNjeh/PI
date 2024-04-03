package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Groupe;
import esprit.pi.demo.Services.IGroupeService;
import esprit.pi.demo.Repository.GroupeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class GroupeService implements IGroupeService{


    GroupeRepository groupeRepository;

    @Override
    public Groupe addGroupe(Groupe g) {
        return groupeRepository.save(g);
    }


    @Override
    public void removeGroupe(long idGroupe) {
        groupeRepository.deleteById(idGroupe);

    }
}
