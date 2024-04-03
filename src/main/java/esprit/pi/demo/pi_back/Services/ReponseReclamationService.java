package esprit.pi.demo.pi_back.Services;


import esprit.pi.demo.pi_back.Entities.ReponseReclamation;
import esprit.pi.demo.pi_back.Repository.ReponseReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class ReponseReclamationService {
    @Autowired
    private ReponseReclamationRepository reponseRCRepository;

    public ReponseReclamation saveReponseReclamation(ReponseReclamation reponseRC){
        return reponseRCRepository.save(reponseRC);
    }
    public List<ReponseReclamation> saveReponseReclamations(List<ReponseReclamation> reponsesRC){
        return reponseRCRepository.saveAll(reponsesRC);
    }
    public List<ReponseReclamation> getReponseReclamations(){
        return reponseRCRepository.findAll();
    }
    public ReponseReclamation getReponseReclamationById(int id){
        return reponseRCRepository.findById(id).orElse(null);
    }

    public String deleteReponseReclamation(int id){
        reponseRCRepository.deleteById(id);
        return "réponse de reclamation supprimée : "+id;
    }
    public ReponseReclamation updateReponseReclamation(ReponseReclamation reponseRC){
        ReponseReclamation newReponseReclamation=reponseRCRepository.findById(reponseRC.getId()).orElse(null);
        newReponseReclamation.setDescription(reponseRC.getDescription());
        newReponseReclamation.setReclamation(reponseRC.getReclamation());
        newReponseReclamation.setReponse(reponseRC.getReponse());
        newReponseReclamation.setDateReponse(reponseRC.getDateReponse());
        return reponseRCRepository.save(newReponseReclamation);
    }
}
