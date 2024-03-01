package esprit.pi.demo.Services;

import esprit.pi.demo.Repository.CreditCardRepository;
import esprit.pi.demo.entities.CreditCard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CreditCardService implements ICreditCardService{
    private CreditCardRepository creditCardRepository;
    @Override
    public CreditCard creer(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    @Override
    public List<CreditCard> lire() {
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard getCreditCardById(int id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    @Override
    public CreditCard modifier(int id, CreditCard creditCard) {
        return creditCardRepository.findById(id).map(cr -> {
            cr.setDate_valide(creditCard.getDate_valide());
            cr.setCvv(creditCard.getCvv());
            cr.setNum_card(creditCard.getNum_card());
            return creditCardRepository.save(cr);

        }).orElse(null);
    }

    @Override
    public String supprimer(int id) {
        creditCardRepository.deleteById(id);
        return "Carte de credit supprimer";
    }
}
