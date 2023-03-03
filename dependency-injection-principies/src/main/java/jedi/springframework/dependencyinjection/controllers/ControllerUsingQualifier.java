package jedi.springframework.dependencyinjection.controllers;


import jedi.springframework.dependencyinjection.services.GreetingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

/*
Usamos Qualifier para indicar con que servicio de todos los que implementan una interfaz vamos a quedarnos sin importar
si tenemos o no la notacion @Primary
 */
@Controller
public class ControllerUsingQualifier {


    private final GreetingService greetingServicePropertyInjected;
    private final GreetingService greetingService;

    public ControllerUsingQualifier(@Qualifier("greetingServiceAuxImpl") GreetingService greetingService,
                                    @Qualifier("greetingServicePropertyInjected") GreetingService greetingServicePropertyInjected) {
        this.greetingService = greetingService;
        this.greetingServicePropertyInjected = greetingServicePropertyInjected;
    }

    public String sayHello(){
        return greetingService.sayGreeting();
    }
}
