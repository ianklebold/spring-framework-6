package jedi.springframework.dependencyinjection.services.i18n;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("ES") //Lo identificarlo con un Spanish!
@Service("i18NService") //El mismo nombre tiene el EnglishGreetingService pero se diferencian por profile
public class SpanishGreetingService implements GreetingI18NService {
    @Override
    public String sayGreeting() {
        return "Hola Mundo - ES";
    }
}
