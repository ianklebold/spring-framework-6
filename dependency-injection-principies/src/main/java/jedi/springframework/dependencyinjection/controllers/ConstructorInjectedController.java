package jedi.springframework.dependencyinjection.controllers;

import jedi.springframework.dependencyinjection.services.GreetingService;


public class ConstructorInjectedController {
    private final GreetingService greetingService;

    public ConstructorInjectedController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello(){
        /*
        Esta forma es la mas utilizada porque corregimos ese posible error de null pointer exception
        Cuando deseamos intanciar el controlador nos pide por el constructor que le pasemos todas aquellos objetos
        que este utiliza. (Ejemplo en la clase ConstructorInjectedControllerTest). Es el mas recomendado de usar.
         */
        return greetingService.sayGreeting();
    }
}
