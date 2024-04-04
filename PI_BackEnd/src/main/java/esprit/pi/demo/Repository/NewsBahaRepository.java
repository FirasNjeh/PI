package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.NewsBaha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsBahaRepository extends JpaRepository<NewsBaha, Integer> {
    List<NewsBaha> findByTitreContainingIgnoreCase(String keyword);
}
