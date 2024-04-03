package esprit.pi.demo.Services;

import esprit.pi.demo.entities.Contrat;

import java.util.List;

public interface IContratService {
    public List<Contrat> retrieveAllContrats();
    public Contrat retrieveContrat(Long contratId);
    public Contrat addContrat(Contrat contrat);
    public void removeContrat(Long contratId);
    public Contrat modifyContrat(Contrat contrat);

    public List<Contrat> retrieveAllContratsSortedByReferenceContratAsc();
    public List<Contrat> retrieveAllContratsSortedByReferenceContratDesc();

    public List<Contrat> retrieveAllContratsSortedByMontantTotalFacturesAsc();
    public List<Contrat> retrieveAllContratsSortedByMontantTotalFacturesDesc();

    public List<Contrat> retrieveAllContratsSortedByAcheteurAsc();
    public List<Contrat> retrieveAllContratsSortedByAcheteurDesc();

    public int noteImpayes(double impayes);
    public int noteEndettement(double endettement, double capital);
    public int noteECLTSCA(double encoursClients, double ca);
    public int noteEFRSCA(double encoursFRS, double ca);
    public int noteFR(double fr);
    public int noteBFR(double bfr);
    public int noteTauxAvance(double tauxAvance);
    public int taux(int note, int risque);
    public double scoring(int taux);
    public double attribution(double scoring);
}
