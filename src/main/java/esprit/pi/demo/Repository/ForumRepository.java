package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    @Query(value = "select * from Forum order by nbr_like desc",nativeQuery = true)
    public List<Forum> getAllBynbr_like() ;
}

