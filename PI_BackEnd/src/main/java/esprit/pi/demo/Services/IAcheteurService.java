package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Acheteur;

import java.util.List;

public interface IAcheteurService {
    public List<Acheteur> retrieveAllAcheteurs();
    public Acheteur retrieveAcheteur(Long acheteurId);
    public Acheteur addAcheteur(Acheteur acheteur);
    public void removeAcheteur(Long acheteurId);
    public Acheteur modifyAcheteur(Acheteur acheteur);

    public List<Acheteur> retrieveAllAcheteursSortedByMatriculeFiscaleAsc();
    public List<Acheteur> retrieveAllAcheteursSortedByMatriculeFiscaleDesc();

    public List<Acheteur> retrieveAllAcheteursSortedByRaisonSocialeAsc();
    public List<Acheteur> retrieveAllAcheteursSortedByRaisonSocialeDesc();

    public List<Acheteur> retrieveAllAcheteursSortedByDateCreationAsc();
    public List<Acheteur> retrieveAllAcheteursSortedByDateCreationDesc();
}
