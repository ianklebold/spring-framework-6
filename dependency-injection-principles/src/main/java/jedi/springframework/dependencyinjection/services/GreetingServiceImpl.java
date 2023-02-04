package jedi.springframework.dependencyinjection.services;


public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Hello Everyone From base service!!!";
    }
}
