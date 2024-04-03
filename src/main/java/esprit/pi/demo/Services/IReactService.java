package esprit.pi.demo.Services;

import esprit.pi.demo.entities.React;

public interface IReactService {
    React addReact (React r);

    void  removeReact (long idReact);

}
