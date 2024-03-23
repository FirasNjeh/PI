package esprit.pi.demo.Services;

import esprit.pi.demo.entities.User;

import java.util.List;

public interface IServiceUser {
    User creer(User user);
    List<User> lire();
    User getUserById (int id);
    User modifier(int id, User user);
    String supprimer(int id);
}
