package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;

    @Nested
    @DisplayName("Testing Car Repository")
    class post_car_test{

        @Test
        void create_car_entity(){
            Car carSaved = carRepository.save(
                    Car.builder()
                            .patentCar("123TEST321")
                            .model("TOYOTA")
                            .build()
            );

            assertThat(carSaved).isNotNull();
            assertThat(carSaved.getId()).isNotNull();
            assertThat(carSaved.getPatentCar()).isEqualTo("123TEST321");
            assertThat(carSaved.getModel()).isEqualTo("TOYOTA");

        }
    }


}