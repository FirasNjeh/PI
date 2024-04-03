package esprit.pi.demo.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import esprit.pi.demo.entities.Contrat;
import esprit.pi.demo.Repository.ContratRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ContratService implements IContratService{
    ContratRepository contratRepository;
    @Override
    public List<Contrat> retrieveAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat retrieveContrat(Long contratId) {
        return contratRepository.findById(contratId).get();
    }

    @Override
    public Contrat addContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public void removeContrat(Long contratId) {
        contratRepository.deleteById(contratId);
    }

    @Override
    public Contrat modifyContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public List<Contrat> retrieveAllContratsSortedByReferenceContratAsc() {
        return contratRepository.findAllByOrderByReferenceContratAsc();    }

    @Override
    public List<Contrat> retrieveAllContratsSortedByReferenceContratDesc() {
        return contratRepository.findAllByOrderByReferenceContratDesc();
    }

    @Override
    public List<Contrat> retrieveAllContratsSortedByMontantTotalFacturesAsc() {
        return contratRepository.findAllByOrderByMontantTotalFacturesAsc();
    }

    @Override
    public List<Contrat> retrieveAllContratsSortedByMontantTotalFacturesDesc() {
        return contratRepository.findAllByOrderByMontantTotalFacturesDesc();
    }

    @Override
    public List<Contrat> retrieveAllContratsSortedByAcheteurAsc() {
        return contratRepository.findAllByOrderByAcheteurAsc();
    }

    @Override
    public List<Contrat> retrieveAllContratsSortedByAcheteurDesc() {
        return contratRepository.findAllByOrderByAcheteurDesc();
    }

    @Override
    public int noteImpayes(double impayes) {
        if(impayes != 0)
            return 1;
        return 0;
    }

    @Override
    public int noteEndettement(double endettement, double capital) {
        if ((endettement / capital) > 0.5)
            return 1;
        return 0;
    }

    @Override
    public int noteECLTSCA(double encoursClients, double ca) {
        if((encoursClients / ca) > 0.9)
            return 1;
        return 0;
    }

    @Override
    public int noteEFRSCA(double encoursFRS, double ca) {
        if((encoursFRS / ca) > 0.9)
            return 1;
        return 0;
    }

    @Override
    public int noteFR(double fr) {
        if(fr < 0)
            return 1;
        return 0;
    }

    @Override
    public int noteBFR(double bfr) {
        if(bfr < 0)
            return 1;
        return 0;
    }

    @Override
    public int noteTauxAvance(double tauxAvance) {
        if(tauxAvance >= 0.7)
            return 1;
        return 0;
    }

    @Override
    public int taux(int note, int risque) {
        return note * risque;
    }

    @Override
    public double scoring(int taux) {
        if(taux == 5)
            return 1;
        else if (taux == 4) {
            return 0.8;
        } else if (taux == 3) {
            return 0.6;
        } else if (taux == 2) {
            return 0.4;
        } else if (taux == 1) {
            return 0.2;
        }
        return 0;
    }

    @Override
    public double attribution(double scoring) {
        if(scoring == 1)
            return 0.1;
        else if (scoring == 0.8) {
            return 0.01;
        } else if (scoring == 0.6) {
            return 0.0075;
        } else if (scoring == 0.4) {
            return 0.005;
        } else if (scoring == 0.2) {
            return 0.0025;
        }
        return 0;
    }
}
