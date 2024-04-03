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
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class AssuranceService implements IAssurance {

    private PackAssurService packAssurService;
    private AssuranceRepository repository;
    private PackAssuranceRepository packrepository;
    private SinistreRepository sinistrerepository;
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
    public Long countAssurancesByUser(int userId) {
        return repository.countAssurancesByUserId(userId);
    }
    @Override
    public Long countAssurancesByUserLastNYear(Integer userId, Integer n) {
        return repository.countAssurancesByUserLastNYear(userId, n);
    }
    @Override
    public Long countSinistresByUser(int userId) {
        return sinistrerepository.countSinistresByUserId(userId);
    }
    @Override
    public Long countSinistresByPackAssurId(int idpack) {
        return sinistrerepository.countSinistresByPackAssurId(idpack);
    }
    @Override
    public Long countSinistresByUserLastNYear(int userId, int n) {
        return sinistrerepository.countSinistresByUserLastNYear(userId, n);
    }
    public Assurance createAssuranceWithPackAssur(int packId, Assurance assurance) {
        PackAssur packAssur = packAssurService.getPackAssurById(packId);

        assurance.setPackAssur(packAssur);
        return repository.save(assurance);
    }
    @Override
    public Assurance createScolaireAssurance(int iduser, int packId, ScolaireAssuranceDTO scolaireAssuranceDTO) {

        Assurance assurance = scolaireAssuranceDTO.toEntity();

        return createAssuranceWithPackAssurandUser(iduser, packId, assurance);
    }

    @Override
    public Assurance createEntrepreneurAssurance(int iduser, int packId, EntrepreneurAssuranceDTO entrepreneurAssuranceDTO) {


        Assurance assurance = entrepreneurAssuranceDTO.toEntity();

        return createAssuranceWithPackAssurandUser(iduser, packId, assurance);
    }


    @Override
    public Assurance createSanteAssurance(int iduser, int packId, SanteAssuranceDTO santeAssuranceDTO) {


        Assurance assurance = santeAssuranceDTO.toEntity();

        return createAssuranceWithPackAssurandUser(iduser, packId, assurance);
    }

    @Override
    public Assurance createAgricoleAssurance(int iduser, int packId, AgricoleAssuranceDTO agricoleAssuranceDTO) {


        Assurance assurance = agricoleAssuranceDTO.toEntity();

        return createAssuranceWithPackAssurandUser(iduser, packId, assurance);
    }

    public Assurance createAssuranceWithPackAssurandUser(int iduser, int packId, Assurance assurance) {
        User user = userrepository.findById(iduser)
                .orElseThrow(() -> new RuntimeException("User with id " + iduser + " not found"));

        PackAssur packAssur = packAssurService.getPackAssurById(packId);
        assurance.setDate_payement(new Date(System.currentTimeMillis()));
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
                throw new RuntimeException("You dont have enough money for this porfeuille" + userPortefeuille.getId());

            }
        } else {
            throw new RuntimeException("Portefeuille not found for user with id " + iduser);
        }

        return repository.save(assurance);
    }
    @Override
    public float CalculScolairePrime(float capitalescolaire_assuré) {
//        float capitalescolaire_assuré = scolaireAssuranceDTO.getCapitalescolaire_assuré();

        if (capitalescolaire_assuré < 5000) {
            return capitalescolaire_assuré * 0.05f;
        } else {
            return capitalescolaire_assuré * 0.06f;
        }
    }
    @Override
    public float CalculENTREPRENEURPrime(TypeAssuranceEntrep typeAssuranceEntrep, BienAssuré bienAssuré, int idpack) {
//        TypeAssuranceEntrep typeAssuranceEntrep = entrepreneurAssuranceDTO.getTypeAssuranceEntrep();
//        BienAssuré bienAssuré = entrepreneurAssuranceDTO.getBien_assuré();
          PackAssur packAssur = packrepository.findById(idpack).orElseThrow(() -> new RuntimeException("Packassur with id " + idpack + " not found"));
        float primeMin = packAssur.getPrimeMin();
        float primeMax = packAssur.getPrimeMax();
        float calculatedPrime;

        switch (typeAssuranceEntrep) {
            case grande:
                calculatedPrime = primeMin + (primeMax - primeMin) * 0.75f;
                break;
            case moyenne:
                calculatedPrime = primeMin + (primeMax - primeMin) * 0.5f;
                break;
            case petite:
                calculatedPrime = primeMin + (primeMax - primeMin) * 0.25f;
                break;
            default:
                throw new IllegalArgumentException("Invalid TypeAssuranceEntrep: " + typeAssuranceEntrep);
        }

        switch (bienAssuré) {
            case bâtiments:
                return calculatedPrime;
            case équipements:
                return calculatedPrime * 0.8f;
            case stocks:
                return calculatedPrime * 0.6f;
            default:
                throw new IllegalArgumentException("Invalid BienAssuré: " + bienAssuré);
        }
    }

    @Override
    public float CalculSANTEPrime(TypeAssuranceSante typeAssuranceSante, int age, Gender gender) {
//        TypeAssuranceSante typeAssuranceSante = santeAssuranceDTO.getTypeAssuranceSante();

        float prime;

        if (age < 15) {
            if (gender == Gender.masculin) {
                prime = 78.85721354f;
            } else if (gender == Gender.feminin) {
                prime = 79.3018486f;
            } else {
                throw new IllegalArgumentException("Invalid gender: " + gender);
            }
        } else if (age <= 25) {
            if (gender == Gender.masculin) {
                prime = 154.4176191f;
            } else if (gender == Gender.feminin) {
                prime = 193.9017444f;
            } else {
                throw new IllegalArgumentException("Invalid gender: " + gender);
            }
        } else if (age <= 35) {
            if (typeAssuranceSante == TypeAssuranceSante.adherant) {
                if (gender == Gender.masculin) {
                    prime = 96.9185838f;
                } else if (gender == Gender.feminin) {
                    prime = 218.2174113f;
                } else {
                    throw new IllegalArgumentException("Invalid gender: " + gender);
                }
            } else if (typeAssuranceSante == TypeAssuranceSante.conjoint) {
                if (gender == Gender.masculin) {
                    prime = 73.33307383f;
                } else if (gender == Gender.feminin) {
                    prime = 232.8340649f;
                } else {
                    throw new IllegalArgumentException("Invalid gender: " + gender);
                }
            } else {
                throw new IllegalArgumentException("Invalid TypeAssuranceSante: " + typeAssuranceSante);
            }
        } else if (age <= 45) {
            if (typeAssuranceSante == TypeAssuranceSante.adherant) {
                if (gender == Gender.masculin) {
                    prime = 134.8396597f;
                } else if (gender == Gender.feminin) {
                    prime = 246.7805794f;
                } else {
                    throw new IllegalArgumentException("Invalid gender: " + gender);
                }
            } else if (typeAssuranceSante == TypeAssuranceSante.conjoint) {
                if (gender == Gender.masculin) {
                    prime = 83.410807f;
                } else if (gender == Gender.feminin) {
                    prime = 174.4987473f;
                } else {
                    throw new IllegalArgumentException("Invalid gender: " + gender);
                }
            } else {
                throw new IllegalArgumentException("Invalid TypeAssuranceSante: " + typeAssuranceSante);
            }
        } else if (age <= 60) {
            if (typeAssuranceSante == TypeAssuranceSante.adherant) {
                if (gender == Gender.masculin) {
                    prime = 242.7962149f;
                } else if (gender == Gender.feminin) {
                    prime = 429.750242f;
                } else {
                    throw new IllegalArgumentException("Invalid gender: " + gender);
                }
            } else if (typeAssuranceSante == TypeAssuranceSante.conjoint) {
                if (gender == Gender.masculin) {
                    prime = 142.2749311f;
                } else if (gender == Gender.feminin) {
                    prime = 210.4960138f;
                } else {
                    throw new IllegalArgumentException("Invalid gender: " + gender);
                }
            } else {
                throw new IllegalArgumentException("Invalid TypeAssuranceSante: " + typeAssuranceSante);
            }
        } else if (age > 60) {
            if (typeAssuranceSante == TypeAssuranceSante.adherant) {
                if (gender == Gender.masculin) {
                    prime = 544.1042289f;
                } else if (gender == Gender.feminin) {
                    prime = 802.5895599f;
                } else {
                    throw new IllegalArgumentException("Invalid gender: " + gender);
                }
            } else if (typeAssuranceSante == TypeAssuranceSante.conjoint) {
                if (gender == Gender.masculin) {
                    prime = 375.6021824f;
                } else if (gender == Gender.feminin) {
                    prime = 524.080458f;
                } else {
                    throw new IllegalArgumentException("Invalid gender: " + gender);
                }
            } else {
                throw new IllegalArgumentException("Invalid TypeAssuranceSante: " + typeAssuranceSante);
            }
        } else {
            throw new IllegalArgumentException("Invalid age: " + age);
        }

        // prime pure * coefficient de securité
        return prime * 1.05f;
    }
    @Override
    public float CalculAgriculturePrime(float capitalAgricole_assuré, TypeAgriculture typeAgriculture) {
//        float capitalAgricole_assuré = agricoleAssuranceDTO.getCapitaleagricole_assuré();
//        TypeAgriculture typeAgriculture = agricoleAssuranceDTO.getTypeagriculture();

        float prime;
        if (capitalAgricole_assuré < 5000) {
            prime = capitalAgricole_assuré * 0.05f;
        } else {
            prime = capitalAgricole_assuré * 0.06f;
        }

        if (typeAgriculture == TypeAgriculture.cultures) {
            prime *= 1.01f;
        } else if (typeAgriculture == TypeAgriculture.élevage) {
            prime *= 1.015f;
        }

        return prime;
    }


}
