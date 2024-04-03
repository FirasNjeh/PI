package esprit.pi.demo.pi_back.Services;

import esprit.pi.demo.pi_back.Entities.PriorityLevel;
import esprit.pi.demo.pi_back.Entities.Reclamation;
import esprit.pi.demo.pi_back.Repository.ReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class ReclamationService {
    @Autowired
    private ReclamationRepository recRepository;

    public Reclamation saveReclamation(Reclamation reclamation){
       return recRepository.save(reclamation);
    }
    public List<Reclamation> saveReclamations(List<Reclamation> reclamations){
        return recRepository.saveAll(reclamations);
    }
    public List<Reclamation> getReclamations(){
        return recRepository.findAll();
    }
    public Reclamation getReclamationById(int id){
        return recRepository.findById(id).orElse(null);
    }
    public Reclamation getReclamationByPriorityLevel(PriorityLevel priorityLevel){
        return recRepository.findByPriorityLevel(priorityLevel);
    }

    public String deleteReclamation(int id){
        recRepository.deleteById(id);
        return "reclamation supprimée : "+id;
    }
    public Reclamation updateReclamation(Reclamation reclamation){
        Reclamation newReclamation=recRepository.findById(reclamation.getId()).orElse(null);
        newReclamation.setDescription(reclamation.getDescription());
        newReclamation.setObject(reclamation.getObject());
        newReclamation.setDateRC(reclamation.getDateRC());
        newReclamation.setReponseRC(reclamation.getReponseRC());
        newReclamation.setTypeRC(reclamation.getTypeRC());
        newReclamation.setStatusRC(reclamation.getStatusRC());
        newReclamation.setPriorityLevel(reclamation.getPriorityLevel());
        return recRepository.save(newReclamation);
    }


}
