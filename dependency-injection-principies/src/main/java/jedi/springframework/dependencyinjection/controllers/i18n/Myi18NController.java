package jedi.springframework.dependencyinjection.controllers.i18n;

import jedi.springframework.dependencyinjection.services.i18n.GreetingI18NService;
import org.springframework.stereotype.Controller;

@Controller
public class Myi18NController {

    private final GreetingI18NService greetingService;

    public Myi18NController(GreetingI18NService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello(){
        return greetingService.sayGreeting();
    }

}
