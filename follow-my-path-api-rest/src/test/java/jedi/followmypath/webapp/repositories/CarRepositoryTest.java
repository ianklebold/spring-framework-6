package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.bootstrap.BootstrapData;
import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.model.csv.CarCSVRecord;
import jedi.followmypath.webapp.model.csv.CustomerCSVRecord;
import jedi.followmypath.webapp.services.csv.CsvService;
import jedi.followmypath.webapp.services.csv.CustomerCsvServiceV2Impl;
import jedi.followmypath.webapp.services.csv.car.CarCsvServiceImpl;
import jedi.followmypath.webapp.services.csv.customer.CustomerCsvServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
Como nuestro Test Slice es DATAJPATEST nuestro conexto solo tiene Repositories, por lo que debemos importar
El bootstrapData para que cuando ejecutemos nuestros test inicie el bootstrap y cargue la BD
Con import incluis en el contexto a las clases que indiques
 */
@DataJpaTest
@Import({BootstrapData.class, CarCsvServiceImpl.class, CustomerCsvServiceV2Impl.class})
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Nested
    @DisplayName("Testing Car Repository")
    class post_car_test{

        @Test
        void test_name_car_is_too_long(){
            Car carSaved = carRepository.save(
                    Car.builder()
                            .patentCar("123TEST321123TEST321123TEST321123TEST321123TEST321123TEST321123TEST321123TEST321")
                            .yearCar(1999)
                            .patentCar("TEST")
                            .size("TEST")
                            .make("TEST")
                            .fuelType("TEST")
                            .model("TOYOTA")
                            .build()
            );

            carRepository.flush();

            assertThat(carSaved).isNotNull();
            assertThat(carSaved.getId()).isNotNull();

        }

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

        @Test
        void test_get_car_list_by_model(){
            // % WildCards --> Tdo lo que viene despues, puede ser cualquier cosa
            List<Car> list = carRepository.findAllByModelIsLikeIgnoreCase("Sandero%");

            assertThat(list.size()).isEqualTo(1);
        }

        @Test
        void test_get_car_list_by_make(){
            // % WildCards --> Tdo lo que viene despues, puede ser cualquier cosa
            List<Car> list = carRepository.findAllByMakeIsLikeIgnoreCase("honda%");

            assertThat(list.size()).isGreaterThan(0);
        }

        @Test
        void test_get_car_list_by_make_and_model(){
            // % WildCards --> Tdo lo que viene despues, puede ser cualquier cosa
            List<Car> list = carRepository.findAllByMakeIsLikeIgnoreCaseAndModelIsLikeIgnoreCase("honda%","CR-Z");

            assertThat(list.size()).isGreaterThan(0);
        }


    }


}