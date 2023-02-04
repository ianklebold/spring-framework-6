package jedi.springframework.dependencyinjection.controllers.i18n;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/*
Anotacion solo para testing, con el especificamos que perfil queremos usar. Si no lo activas tira error por ambiguedad
 */
@ActiveProfiles({"dev","ES"})
@SpringBootTest
class Myi18NControllerTest {

    @Autowired
    Myi18NController myi18NController;

    @Test
    void sayHello() {
        System.out.println(myi18NController.sayHello());
    }
}