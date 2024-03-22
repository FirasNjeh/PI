package esprit.pi.demo.Services;
import esprit.pi.demo.dto.AgricoleAssuranceDTO;
import esprit.pi.demo.dto.EntrepreneurAssuranceDTO;
import esprit.pi.demo.dto.SanteAssuranceDTO;
import esprit.pi.demo.dto.ScolaireAssuranceDTO;
import esprit.pi.demo.entities.Assurance;

import java.util.List;
import java.util.Set;

public interface IAssurance {


    Assurance saveAssurance(Assurance assurance);
    List<Assurance> getAssurance();
    Assurance getAssuranceById(int id);
    String deleteAssurance(int id);

    // @Override
 //role : adminn+agent
 //    public Assurance updateAssurance(int id , Assurance assurance ){
 //        Assurance existingAssurance=repository.findById(assurance.getId()).orElse(null);
 //        existingAssurance.setDate_payement(assurance.getDate_payement());
 //        existingAssurance.setDate_remboursement(assurance.getDate_remboursement());
 //        existingAssurance.setMontant_prime(assurance.getMontant_prime());
 //        existingAssurance.setRemboursement(assurance.getRemboursement());
 //        return repository.save(existingAssurance);
 //
 //
 //    }
    Set<Assurance> getListAssurancesByPackAssur(int idpack);

    Set<Assurance> getListAssurancesByUser(int iduser);

    Assurance createAssuranceWithPackAssur(int packId, Assurance assurance);

    Assurance createScolaireAssurance(int packId, ScolaireAssuranceDTO scolaireAssuranceDTO);

    Assurance createEntrepreneurAssurance(int packId, EntrepreneurAssuranceDTO entrepreneurAssuranceDTO);

    Assurance createSanteAssurance(int packId, SanteAssuranceDTO santeAssuranceDTO);

    Assurance createAgricoleAssurance(int packId, AgricoleAssuranceDTO agricoleAssuranceDTO);

    Assurance createAssuranceWithPackAssurandUser(int iduser, int packId, Assurance assurance);

    // Assurance updateAssurance(int id , Assurance assurance);


}
