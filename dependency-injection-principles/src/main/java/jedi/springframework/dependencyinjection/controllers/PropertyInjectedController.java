package jedi.springframework.dependencyinjection.controllers;

import jedi.springframework.dependencyinjection.services.GreetingService;

public class PropertyInjectedController {

    GreetingService greetingService;

    public String sayHello(){
        /*
        Esta forma de hacerlo es a partir de, instanciar el controlador y a la propiedad de ese controlador
        Asignarle el servicio implementado y asi utilizarlo (Ver ejemplo en la clase PropertyInjectedControllerTest)
        Esta la peor forma de hacer la inyeccion de dependencias.
         */
        return greetingService.sayGreeting();
    }
}
