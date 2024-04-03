package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Forum;
import esprit.pi.demo.Repository.ForumRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ForumService implements IForumService {
        private ForumRepository forumrepository ;

        public List<Forum> getForum()
        {
            return forumrepository.findAll() ;
        }

        public Optional<Forum> getForumById(long id_forum)
        {
            return forumrepository.findById(id_forum) ;
        }

        public Forum addForum(Forum forum)
        {
            return forumrepository.save(forum) ;
        }


        public Forum updateForum(Forum forum, Long id_forum) {return forumrepository.save(forum);
        }

        public boolean existByIdForum(Long id_forum)
        {
            return forumrepository.existsById(id_forum);
        }

        public void DeleteForum(Long id_forum) {
            forumrepository.deleteById(id_forum);
        }

    public List<Forum> getForumBynbr_like() {return forumrepository.getAllBynbr_like();}
    public Forum incrementLikes(Long id_forum) {
        Optional<Forum> optionalForum = forumrepository.findById(id_forum);
        if (optionalForum.isPresent()) {
            Forum forum = optionalForum.get();
            forum.setNbr_like(forum.getNbr_like() + 1);
            return forumrepository.save(forum);
        } else {
            throw new EntityNotFoundException("Forum not found with id: " + id_forum);
        }
    }

}
