package jedi.springframework.dependencyinjection.controllers.faux;

import jedi.springframework.dependencyinjection.services.faux.FauxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class FauxController {

    private final FauxService fauxService;

    //Este fauxService apunta a todos los servicios cuyo alias es fauxService ¿Como lo diferencias? Con profile!
    public FauxController(@Qualifier("fauxService") FauxService fauxService) {
        this.fauxService = fauxService;
    }

    public String dataFromDataSource(){
        return fauxService.dataFromDataSource();
    }

}
