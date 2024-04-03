package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Adherent;

import java.util.List;

public interface IAdherentService {
    public List<Adherent> retrieveAllAdherents();
    public Adherent retrieveAdherent(Long adherentId);
    public Adherent addAdherent(Adherent adherent);
    public void removeAdherent(Long adherentId);
    public Adherent modifyAdherent(Adherent adherent);

    public List<Adherent> retrieveAllAdherentsSortedByIdentifiantUniqueRNEAsc();
    public List<Adherent> retrieveAllAdherentsSortedByIdentifiantUniqueRNEDesc();

    public List<Adherent> retrieveAllAdherentsSortedByMatriculeFiscaleAsc();
    public List<Adherent> retrieveAllAdherentsSortedByMatriculeFiscaleDesc();

    public List<Adherent> retrieveAllAdherentsSortedByRaisonSocialeAsc();
    public List<Adherent> retrieveAllAdherentsSortedByRaisonSocialeDesc();

    public List<Adherent> retrieveAllAdherentsSortedByDateCreationAsc();
    public List<Adherent> retrieveAllAdherentsSortedByDateCreationDesc();
}
