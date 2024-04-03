package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Notification;

import java.util.List;

public interface INotificationService {
    Notification creer(Notification notification);
    List<Notification> lire();
    Notification getNotificationById (int id);
    Notification modifier(int id, Notification notification);
    String supprimer(int id);
}
