package jedi.springframework.spring6restmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CanControllerTest {

    @Autowired
    CanController canController;

    @Test
    void getCanById() {
        System.out.println(canController.getCanById(UUID.randomUUID()));
    }
}