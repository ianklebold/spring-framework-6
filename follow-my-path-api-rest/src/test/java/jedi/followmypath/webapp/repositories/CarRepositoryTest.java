package jedi.followmypath.webapp.repositories;

import jakarta.transaction.Transactional;
import jedi.followmypath.webapp.bootstrap.BootstrapData;
import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.entities.Customer;
import jedi.followmypath.webapp.services.csv.CustomerCsvServiceV2Impl;
import jedi.followmypath.webapp.services.csv.car.CarCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
            Page<Car> list = carRepository.findAllByModelIsLikeIgnoreCase("Sandero%", null);

            assertThat(list.getContent().size()).isEqualTo(1);
        }

        @Test
        void test_get_car_list_by_make(){
            // % WildCards --> Tdo lo que viene despues, puede ser cualquier cosa
            Page<Car> list = carRepository.findAllByMakeIsLikeIgnoreCase("honda%", null);

            assertThat(list.getContent().size()).isGreaterThan(0);
        }

        @Test
        void test_get_car_list_by_make_and_model(){
            // % WildCards --> Tdo lo que viene despues, puede ser cualquier cosa
            Page<Car> list = carRepository.findAllByMakeIsLikeIgnoreCaseAndModelIsLikeIgnoreCase("CR-Z", "honda%", null);

            assertThat(list.getContent().size()).isGreaterThan(0);
        }

    }

    @Nested
    @DisplayName("GET CAR TESTS")
    class get_car_test{
        Customer customerCorrect;
        Set<Car> testCars = new HashSet<>();
        @BeforeEach
        void setUp(){
            this.customerCorrect = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Test name customer")
                    .surname("Test surname customer")
                    .country("España")
                    .email("test@gmail.com")
                    .birthDate(LocalDateTime.of(1999,8,10,0,0,0))
                    .createCustomerDate(LocalDateTime.now())
                    .updateCustomerDate(LocalDateTime.now())
                    .version(1)
                    .build();

            Car car1 = Car.builder()
                    .model("Model Test")
                    .patentCar("ABCDE123")
                    .yearCar(1999)
                    .fuelType("Gasoil")
                    .make("Make test")
                    .size("Mid")
                    .build();

            Car car2 = Car.builder()
                    .model("Model Test")
                    .patentCar("ABCDE123")
                    .yearCar(1999)
                    .fuelType("Gasoil")
                    .make("Make test")
                    .size("Mid")
                    .build();

            this.testCars.add(car1);
            this.testCars.add(car2);
        }

        @Rollback //Estas dos anotaciones son necesrias, para que se restablezca la BD para los demas tests
        @Transactional
        @Test
        void test_get_cars_by_customer(){
            Customer customerSaved = customerRepository.save(this.customerCorrect);
            List<Car> cars =  carRepository.saveAll(this.testCars);

            customerSaved.setCars(cars);
            customerSaved = customerRepository.save(customerSaved);

            List<Car> carsFounded = carRepository.findAllByCustomer(customerSaved);

            assertThat(carsFounded).isNotNull();
            assertThat(carsFounded).isNotEmpty();
            assertThat(carsFounded).contains(cars.get(0),cars.get(1));
        }

        @Rollback
        @Transactional
        @Test
        void test_get_empty_when_customer_not_have_cars(){
            Customer customerSaved = customerRepository.save(this.customerCorrect);

            customerSaved = customerRepository.save(customerSaved);

            List<Car> carsFounded = carRepository.findAllByCustomer(customerSaved);

            assertThat(carsFounded).isNotNull();
            assertThat(carsFounded).isEmpty();
        }

    }


}