package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Groupe;

public interface IGroupeService {
    Groupe addGroupe (Groupe g);

    void  removeGroupe (long idGroupe);

}
