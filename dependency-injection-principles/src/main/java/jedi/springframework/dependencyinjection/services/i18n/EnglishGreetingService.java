package jedi.springframework.dependencyinjection.services.i18n;

import jedi.springframework.dependencyinjection.services.GreetingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/*
Un componente puede tener varios perfiles. El perfil default es un perfil especial el cual dice que si no hay ninguna
especificacion explicita de que servicio o perfil se va a usar por default se tome este.
 */
@Profile({"EN","default"}) //Lo identificarlo con un English!
@Service("i18NService") //El mismo nombre tiene el SpanishGreetingService pero se diferencian por profile
public class EnglishGreetingService implements GreetingI18NService {
    @Override
    public String sayGreeting() {
        return "Hello World - EN";
    }
}
