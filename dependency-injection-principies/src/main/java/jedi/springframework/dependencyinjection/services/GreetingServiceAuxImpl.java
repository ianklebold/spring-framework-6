package jedi.springframework.dependencyinjection.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceAuxImpl implements GreetingService{
    @Override
    public String sayGreeting() {
        return "Hello Everyone From Auxiliar Service!!!";
    }
}
