package esprit.pi.demo.Controller;

import esprit.pi.demo.Services.IReactService;
import esprit.pi.demo.entities.React;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/api/react")
@CrossOrigin("*")

public class ReactController {

    IReactService reactInterface;

    @PostMapping("/addReact")
    public React addReact(@RequestBody React r) {
        return reactInterface.addReact(r);
    }


    @DeleteMapping("/removeReact/{idReact}")
    public void removeReact(@PathVariable("idReact") long idReact) {
        reactInterface.removeReact(idReact);

    }
}

