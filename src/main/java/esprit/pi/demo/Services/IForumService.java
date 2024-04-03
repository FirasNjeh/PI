package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Forum;

import java.util.List;
import java.util.Optional;

public interface IForumService {
    List<Forum> getForum();
    public Optional<Forum> getForumById(long id_forum);
    public boolean existByIdForum(Long id_forum);


    Forum addForum(Forum forum);
    Forum updateForum(Forum forum,Long id_forum);

    void DeleteForum(Long id_forum);

    List<Forum> getForumBynbr_like();
    Forum incrementLikes(Long id_forum);

}
