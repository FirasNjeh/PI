package esprit.pi.demo.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import esprit.pi.demo.entities.Adherent;
import esprit.pi.demo.Repository.AdherentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AdherentService implements IAdherentService{
    AdherentRepository adherentRepository;
    @Override
    public List<Adherent> retrieveAllAdherents() {
        return adherentRepository.findAll();
    }

    @Override
    public Adherent retrieveAdherent(Long adherentId) {
        return adherentRepository.findById(adherentId).get();
    }

    @Override
    public Adherent addAdherent(Adherent adherent) {
        return adherentRepository.save(adherent);
    }

    @Override
    public void removeAdherent(Long adherentId) {
        adherentRepository.deleteById(adherentId);
    }

    @Override
    public Adherent modifyAdherent(Adherent adherent) {
        return adherentRepository.save(adherent);
    }

    @Override
    public List<Adherent> retrieveAllAdherentsSortedByIdentifiantUniqueRNEAsc() {
        return adherentRepository.findAllByOrderByIdentifiantUniqueRNEAsc();
    }

    @Override
    public List<Adherent> retrieveAllAdherentsSortedByIdentifiantUniqueRNEDesc() {
        return adherentRepository.findAllByOrderByIdentifiantUniqueRNEDesc();
    }

    @Override
    public List<Adherent> retrieveAllAdherentsSortedByMatriculeFiscaleAsc() {
        return adherentRepository.findAllByOrderByMatriculeFiscaleAsc();
    }

    @Override
    public List<Adherent> retrieveAllAdherentsSortedByMatriculeFiscaleDesc() {
        return adherentRepository.findAllByOrderByMatriculeFiscaleDesc();
    }

    @Override
    public List<Adherent> retrieveAllAdherentsSortedByRaisonSocialeAsc() {
        return adherentRepository.findAllByOrderByRaisonSocialeAsc();
    }

    @Override
    public List<Adherent> retrieveAllAdherentsSortedByRaisonSocialeDesc() {
        return adherentRepository.findAllByOrderByRaisonSocialeDesc();
    }

    @Override
    public List<Adherent> retrieveAllAdherentsSortedByDateCreationAsc() {
        return adherentRepository.findAllByOrderByDateCreationAsc();
    }

    @Override
    public List<Adherent> retrieveAllAdherentsSortedByDateCreationDesc() {
        return adherentRepository.findAllByOrderByDateCreationDesc();
    }
}
