package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.PackAssuranceRepository;
import esprit.pi.demo.entities.PackAssur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class PackAssurService implements IPackAssurService {


        private PackAssuranceRepository repository;
        //Agent
        @Override
        public PackAssur savePackAssur(PackAssur packAssur){
            return repository.save(packAssur);
        }

        //agent + client
        @Override
        public List<PackAssur> getPacksAssur(){
            return repository.findAll();
        }
        //agent
        @Override
        public PackAssur getPackAssurById(int id){
            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("PackAssur with id " + id + " not found"));
        }
        //agent
        @Override
        public String deletePackAssur (int id){
            repository.deleteById(id);
            return "Pack Assurance supprimé !!" +id;
        }
        //agent
        @Override
        public PackAssur updatePackAssur(int id,PackAssur packAssur){
            PackAssur existingPackAssur=getPackAssurById(id);


            existingPackAssur.setPackAssurance(packAssur.getPackAssurance());
            existingPackAssur.setNom(packAssur.getNom());
            existingPackAssur.setDescription(packAssur.getDescription());
            existingPackAssur.setPrime(packAssur.getPrime());

            return repository.save(existingPackAssur);

        }
    @Override
    public Map<String, Integer> getPackAssurAssuranceCounts() {
        List<PackAssur> packAssurs = repository.findAll();

        return packAssurs.stream()
                .collect(Collectors.toMap(
                        PackAssur::getNom,
                        packAssur -> packAssur.getAssurances().size(),
                        (count1, count2) -> count1,
                        HashMap::new
                ));
    }

    public String findPackAssurNomWithMostAssurances() {
        Map<String, Integer> packAssurAssuranceCounts = getPackAssurAssuranceCounts();

        Map.Entry<String, Integer> entryWithMaxAssurances = packAssurAssuranceCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        return entryWithMaxAssurances != null ? entryWithMaxAssurances.getKey() : null;
    }

    public String findPackAssurNomWithLeastAssurances() {
        Map<String, Integer> packAssurAssuranceCounts = getPackAssurAssuranceCounts();

        Map.Entry<String, Integer> entryWithMinAssurances = packAssurAssuranceCounts.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        return entryWithMinAssurances != null ? entryWithMinAssurances.getKey() : null;
    }

}
