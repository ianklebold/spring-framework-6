package jedi.springframework.dependencyinjection.controllers;

import jedi.springframework.dependencyinjection.services.GreetingService;

public class SetterInjectedController {
    private GreetingService greetingService;

    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello(){
        /*
        El primer gran problema de este es que si no lo seteaste, saltara un Null Pointer Exception

        Consiste en simplemente instanciar la clase, setearle las clases en las cuales este depende y luego
        usarlas. (Ejemplo en la clase SetterInjectedController)
         */
        return greetingService.sayGreeting();
    }

}
