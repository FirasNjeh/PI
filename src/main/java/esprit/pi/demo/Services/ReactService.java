package esprit.pi.demo.Services;


import esprit.pi.demo.Services.IReactService;
import esprit.pi.demo.Repository.ReactRepository;
import esprit.pi.demo.entities.React;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReactService implements IReactService {

    ReactRepository reactRepository;
    @Override
    public React addReact(React r) {
        return reactRepository.save(r);
    }

    @Override
    public void removeReact(long idReact) {
        reactRepository.deleteById(idReact);

    }
}
