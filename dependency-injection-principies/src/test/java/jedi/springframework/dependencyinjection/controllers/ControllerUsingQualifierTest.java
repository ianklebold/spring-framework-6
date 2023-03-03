package jedi.springframework.dependencyinjection.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ControllerUsingQualifierTest {

    @Autowired
    private ControllerUsingQualifier controllerUsingQualifier;

    @Test
    void sayHello() {
        System.out.println(controllerUsingQualifier.sayHello());
    }
}