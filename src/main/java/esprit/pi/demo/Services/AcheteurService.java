package esprit.pi.demo.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import esprit.pi.demo.entities.Acheteur;
import esprit.pi.demo.Repository.AcheteurRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AcheteurService implements IAcheteurService{
    AcheteurRepository acheteurRepository;
    @Override
    public List<Acheteur> retrieveAllAcheteurs() {
        return acheteurRepository.findAll();
    }

    @Override
    public Acheteur retrieveAcheteur(Long acheteurId) {
        return acheteurRepository.findById(acheteurId).get();
    }

    @Override
    public Acheteur addAcheteur(Acheteur acheteur) {
        return acheteurRepository.save(acheteur);
    }

    @Override
    public void removeAcheteur(Long acheteurId) {
        acheteurRepository.deleteById(acheteurId);
    }

    @Override
    public Acheteur modifyAcheteur(Acheteur acheteur) {
        return acheteurRepository.save(acheteur);
    }

    @Override
    public List<Acheteur> retrieveAllAcheteursSortedByMatriculeFiscaleAsc() {
        return acheteurRepository.findAllByOrderByMatriculeFiscaleAsc();
    }

    @Override
    public List<Acheteur> retrieveAllAcheteursSortedByMatriculeFiscaleDesc() {
        return acheteurRepository.findAllByOrderByMatriculeFiscaleDesc();
    }

    @Override
    public List<Acheteur> retrieveAllAcheteursSortedByRaisonSocialeAsc() {
        return acheteurRepository.findAllByOrderByRaisonSocialeAsc();
    }

    @Override
    public List<Acheteur> retrieveAllAcheteursSortedByRaisonSocialeDesc() {
        return acheteurRepository.findAllByOrderByRaisonSocialeDesc();
    }

    @Override
    public List<Acheteur> retrieveAllAcheteursSortedByDateCreationAsc() {
        return acheteurRepository.findAllByOrderByDateCreationAsc();
    }

    @Override
    public List<Acheteur> retrieveAllAcheteursSortedByDateCreationDesc() {
        return acheteurRepository.findAllByOrderByDateCreationDesc();
    }
}
