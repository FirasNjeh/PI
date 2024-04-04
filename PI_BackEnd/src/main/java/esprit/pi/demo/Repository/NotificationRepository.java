package esprit.pi.demo.Repository;

import esprit.pi.demo.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification , Integer> {


}
