package jedi.springframework.dependencyinjection.controllers;

import jedi.springframework.dependencyinjection.services.GreetingService;
import jedi.springframework.dependencyinjection.services.GreetingServiceImpl;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {

    private final GreetingService greetingService;

    public MyController() {

        /*
            Ahora mismo el controlador es quien realiza la implementacion, instancia la clase, es quien lo maneja para luego usarlo
            Esta es la peor forma de instanciar un objeto, porque el controlador depende de esta clase service
            Depende de el y que este no crezca, no cambie, esta atado a sus cambios.
        */
        this.greetingService = new GreetingServiceImpl();
    }


    public String sayHello(){
        System.out.println("Im in the controller");

        return greetingService.sayGreeting();
    }
}
