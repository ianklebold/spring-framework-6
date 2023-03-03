package jedi.springframework.dependencyinjection.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/*
Usamos Primary para indicar cual de todos los servicios que implementan una interfaz va a ser utilizado
Si no indicamos esto, Spring nos devuelve un error diciendo que no sabe cual intanciar.

Podemos usar Primary para dejar por default el servicio a elegir o usar la anotacion @Qualifier
 */
@Primary
@Service
public class GreetingServicePrimary implements GreetingService{

    @Override
    public String sayGreeting() {
        return "Hello from the primary bean";
    }
}
