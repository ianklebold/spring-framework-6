package jedi.springframework.dependencyinjection.controllers.faux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"dev","ES"})
@SpringBootTest
class FauxControllerTest {

    @Autowired
    private FauxController fauxController;

    @Test
    void dataFromDataSource() {
        System.out.println(fauxController.dataFromDataSource());
    }
}