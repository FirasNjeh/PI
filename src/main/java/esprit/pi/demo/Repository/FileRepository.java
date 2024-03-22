package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.File;
import esprit.pi.demo.entities.PackAssur;
import esprit.pi.demo.entities.Sinistre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileRepository extends JpaRepository<File, String> {
    File findByName(String name);
    List<File> findBySinistre(Sinistre sinistre);
    List<File> findByPackAssur(PackAssur packAssur);

}