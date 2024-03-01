package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.NotificationRepository;
import esprit.pi.demo.entities.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class NotificationService  implements INotificationService{
    private NotificationRepository notificationRepository;
    @Override
    public Notification creer(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> lire() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(int id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public Notification modifier(int id, Notification notification) {
        return notificationRepository.findById(id).map(nf -> {
            nf.setMsg(notification.getMsg());
            nf.setObjet(notification.getObjet());
            nf.setTypeNotif(notification.getTypeNotif());
            nf.setStatusNotif(notification.getStatusNotif());

            return notificationRepository.save(nf);

        }).orElse(null);
    }

    @Override
    public String supprimer(int id) {
        notificationRepository.deleteById(id);
        return "notification supprimer";
    }
}
