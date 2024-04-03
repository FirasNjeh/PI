package esprit.pi.demo.Services;

import esprit.pi.demo.entities.FactureFC;

import java.util.List;

public interface IFactureFCService {
    public List<FactureFC> retrieveAllFactures();
    public FactureFC retrieveFacture(Long factureId);
    public FactureFC addFacture(FactureFC factureFC);
    public void removeFacture(Long factureId);
    public FactureFC modifyFacture(FactureFC factureFC);

    public List<FactureFC> retrieveAllFacturesSortedByNumeroFactureAsc();
    public List<FactureFC> retrieveAllFacturesSortedByNumeroFactureDesc();

    public List<FactureFC> retrieveAllFacturesSortedByDateEmissionAsc();
    public List<FactureFC> retrieveAllFacturesSortedByDateEmissionDesc();

    public List<FactureFC> retrieveAllFacturesSortedByDateEcheanceAsc();
    public List<FactureFC> retrieveAllFacturesSortedByDateEcheanceDesc();

    public List<FactureFC> retrieveAllFacturesSortedByMontantAsc();
    public List<FactureFC> retrieveAllFacturesSortedByMontantDesc();

    public List<FactureFC> retrieveAllFacturesSortedByAcheteurAsc();
    public List<FactureFC> retrieveAllFacturesSortedByAcheteurDesc();
}
