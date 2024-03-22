package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.*;
import esprit.pi.demo.dto.AgricoleAssuranceDTO;
import esprit.pi.demo.dto.EntrepreneurAssuranceDTO;
import esprit.pi.demo.dto.SanteAssuranceDTO;
import esprit.pi.demo.dto.ScolaireAssuranceDTO;
import esprit.pi.demo.entities.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class AssuranceService implements IAssurance {

    private PackAssurService packAssurService;
    private AssuranceRepository repository;
    private PackAssuranceRepository packrepository;

    private UserRepository userrepository;
    private PortefeuilleRepository portefeuilleepository;
    private TransactionAssuranceRepository transactionAssurancerepository;
    //client
    @Override
    public Assurance saveAssurance(Assurance assurance)
    {
        return repository.save(assurance);
    }
    @Override
    //Agent +client
    public List<Assurance> getAssurance(){
        return repository.findAll();
    }
    @Override
    //Agent
    public Assurance getAssuranceById(int id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assurance with id " + id + " not found"));
    }
    @Override
    //Agent+admin
    public  String deleteAssurance(int id){
        repository.deleteById(id);
        return "Assurance résiliée !!"+id;
    }
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
    @Override
    public Set<Assurance> getListAssurancesByPackAssur(int idpack) {
        return repository.findByPackAssur_Id(idpack);
    }

    @Override
    public Set<Assurance> getListAssurancesByUser(int iduser) {return repository.findByUserAssurance_Id(iduser);}
    @Override
    public Assurance createAssuranceWithPackAssur(int packId, Assurance assurance) {
        PackAssur packAssur = packAssurService.getPackAssurById(packId);

        assurance.setPackAssur(packAssur);
        return repository.save(assurance);
    }
    @Override
    public Assurance createScolaireAssurance(int packId, ScolaireAssuranceDTO scolaireAssuranceDTO) {
        PackAssur packAssur = packAssurService.getPackAssurById(packId);

        Assurance assurance = new Assurance();
        assurance.setPackAssur(packAssur);
        setScolaireAssuranceProperties(assurance, scolaireAssuranceDTO);

        return repository.save(assurance);
    }

    private void setScolaireAssuranceProperties(Assurance assurance, ScolaireAssuranceDTO scolaireAssuranceDTO) {
        assurance.setDate_payement(scolaireAssuranceDTO.getDate_payement());
        assurance.setMontant_prime(scolaireAssuranceDTO.getMontant_prime());
        assurance.setDuree_contrat(scolaireAssuranceDTO.getDuree_contrat());
        assurance.setRemboursement_en_cas_de_sinsitre(scolaireAssuranceDTO.getRemboursement_en_cas_de_sinsitre());
        assurance.setFranchise(scolaireAssuranceDTO.getFranchise());

        assurance.setNom_enfant(scolaireAssuranceDTO.getNom_enfant());
        assurance.setPrenom_enfant(scolaireAssuranceDTO.getPrenom_enfant());
        assurance.setMontant_couverture(scolaireAssuranceDTO.getMontant_couverture());
    }
    @Override
    public Assurance createEntrepreneurAssurance(int packId, EntrepreneurAssuranceDTO entrepreneurAssuranceDTO) {
        PackAssur packAssur = packAssurService.getPackAssurById(packId);


        Assurance assurance = new Assurance();
        assurance.setPackAssur(packAssur);
        setEntrepreneurAssuranceProperties(assurance, entrepreneurAssuranceDTO);

        return repository.save(assurance);
    }

    private void setEntrepreneurAssuranceProperties(Assurance assurance, EntrepreneurAssuranceDTO entrepreneurAssuranceDTO) {
        assurance.setDate_payement(entrepreneurAssuranceDTO.getDate_payement());
        assurance.setMontant_prime(entrepreneurAssuranceDTO.getMontant_prime());
        assurance.setDuree_contrat(entrepreneurAssuranceDTO.getDuree_contrat());
        assurance.setRemboursement_en_cas_de_sinsitre(entrepreneurAssuranceDTO.getRemboursement_en_cas_de_sinsitre());
        assurance.setFranchise(entrepreneurAssuranceDTO.getFranchise());

        assurance.setNom_entreprise(entrepreneurAssuranceDTO.getNom_entreprise());
        assurance.setAdresse_entreprise(entrepreneurAssuranceDTO.getAdresse_entreprise());
        assurance.setActivité_entreprise(entrepreneurAssuranceDTO.getActivité_entreprise());
        assurance.setBien_assuré(entrepreneurAssuranceDTO.getBien_assuré());
        assurance.setHistorique_de_sinistralite(entrepreneurAssuranceDTO.getHistorique_de_sinistralite());
        assurance.setResponsbilité_civile(entrepreneurAssuranceDTO.getResponsbilité_civile());
    }

    @Override
    public Assurance createSanteAssurance(int packId, SanteAssuranceDTO santeAssuranceDTO) {
        PackAssur packAssur = packAssurService.getPackAssurById(packId);


        Assurance assurance = new Assurance();
        assurance.setPackAssur(packAssur);
        setSanteAssuranceProperties(assurance, santeAssuranceDTO);

        return repository.save(assurance);
    }

    private void setSanteAssuranceProperties(Assurance assurance, SanteAssuranceDTO santeAssuranceDTO) {
        assurance.setDate_payement(santeAssuranceDTO.getDate_payement());
        assurance.setMontant_prime(santeAssuranceDTO.getMontant_prime());
        assurance.setDuree_contrat(santeAssuranceDTO.getDuree_contrat());
        assurance.setRemboursement_en_cas_de_sinsitre(santeAssuranceDTO.getRemboursement_en_cas_de_sinsitre());
        assurance.setFranchise(santeAssuranceDTO.getFranchise());

        assurance.setCondition_medicale(santeAssuranceDTO.getCondition_medicale());
        assurance.setNiveauDeCouverture(santeAssuranceDTO.getNiveauDeCouverture());
    }
    @Override
    public Assurance createAgricoleAssurance(int packId, AgricoleAssuranceDTO agricoleAssuranceDTO) {
        PackAssur packAssur = packAssurService.getPackAssurById(packId);


        Assurance assurance = new Assurance();
        assurance.setPackAssur(packAssur);
        setAgricoleAssuranceProperties(assurance, agricoleAssuranceDTO);

        return repository.save(assurance);
    }

    private void setAgricoleAssuranceProperties(Assurance assurance, AgricoleAssuranceDTO agricoleAssuranceDTO) {
        assurance.setDate_payement(agricoleAssuranceDTO.getDate_payement());
        assurance.setMontant_prime(agricoleAssuranceDTO.getMontant_prime());
        assurance.setDuree_contrat(agricoleAssuranceDTO.getDuree_contrat());
        assurance.setRemboursement_en_cas_de_sinsitre(agricoleAssuranceDTO.getRemboursement_en_cas_de_sinsitre());
        assurance.setFranchise(agricoleAssuranceDTO.getFranchise());

        assurance.setNom_exploitation_agricole(agricoleAssuranceDTO.getNom_exploitation_agricole());
        assurance.setLieu_de_lexploitation(agricoleAssuranceDTO.getLieu_de_lexploitation());
        assurance.setSurface_totale_exploitation(agricoleAssuranceDTO.getSurface_totale_exploitation());
        assurance.setTypeagriculture(agricoleAssuranceDTO.getTypeagriculture());
        assurance.setMotant_de_couverture_souhaite(agricoleAssuranceDTO.getMotant_de_couverture_souhaite());
        assurance.setTypedecouverture(agricoleAssuranceDTO.getTypedecouverture());
    }

    @Override
    public Assurance createAssuranceWithPackAssurandUser(int iduser, int packId, Assurance assurance) {
        User user = userrepository.findById(iduser)
                .orElseThrow(() -> new RuntimeException("User with id " + iduser + " not found"));

        PackAssur packAssur = packAssurService.getPackAssurById(packId);

        assurance.setUserAssurance(user);
        assurance.setPackAssur(packAssur);


        float MontantPaye = assurance.getMontant_prime();

        TransactionAssurance transactionAssurance = new TransactionAssurance();
        transactionAssurance.setDate(LocalDate.now());
        transactionAssurance.setTypeTransaction(TypeTransaction.VERSEMENT);
        transactionAssurance.setTypeTransaction1(TypeTransaction1.Prime);
        transactionAssurance.setMontant(MontantPaye);


        Portefeuille userPortefeuille = user.getPortefeuilleUser();
        if (userPortefeuille != null) {
            if ( userPortefeuille.getMontant() >= MontantPaye) {
                userPortefeuille.setMontant(userPortefeuille.getMontant() - MontantPaye);
                portefeuilleepository.save(userPortefeuille);
                transactionAssurance.setPortefeuilleTransactionA(userPortefeuille);
                transactionAssurancerepository.save(transactionAssurance);
            }
            else
            {
                throw new RuntimeException("You dont have enough money for this" + userPortefeuille.getId());

            }
        } else {
            throw new RuntimeException("Portefeuille not found for user with id " + iduser);
        }

        return repository.save(assurance);
    }

}
