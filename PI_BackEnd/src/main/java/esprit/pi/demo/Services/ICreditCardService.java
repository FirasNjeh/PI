package esprit.pi.demo.Services;

import esprit.pi.demo.entities.CreditCard;

import java.util.List;

public interface ICreditCardService {
    CreditCard creer(CreditCard creditCard);
    List<CreditCard> lire();
    CreditCard getCreditCardById (int id);
    CreditCard modifier(int id, CreditCard creditCard);
    String supprimer(int id);
}
