package jedi.followmypath.webapp.repositories;

import jakarta.transaction.Transactional;
import jedi.followmypath.webapp.bootstrap.BootstrapData;
import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.entities.Customer;
import jedi.followmypath.webapp.entities.PathTraveled;
import jedi.followmypath.webapp.entities.PositionTraveled;
import jedi.followmypath.webapp.services.csv.CustomerCsvServiceV2Impl;
import jedi.followmypath.webapp.services.csv.car.CarCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BootstrapData.class, CarCsvServiceImpl.class, CustomerCsvServiceV2Impl.class})
class PathTraveledRepositoryTest {

    @Autowired
    PathTraveledRepository pathTraveledRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    PositionTraveledRepository positionTraveledRepository;

    Customer testCustomer;
    Car testCar;

    @BeforeEach
    void setUp(){
        testCustomer = customerRepository.findAll().get(0);
        testCar = carRepository.findAll().get(0);
    }

    @Transactional
    @Rollback
    @Test
    void test_post_paths_with_car_but_without_positions(){

        long countInDb = pathTraveledRepository.count();

        PathTraveled path= PathTraveled.builder()
                .car(testCar)
                .positions(Set.of())
                .build();

        //Save and flush me permite persistir el objeto en la BD pero a su vez las entidades con que se relacionan
        //Actualizarlas, es decir, car ahora tendra una lista de paths
        //Sin embaro saveAndFlush puede generar degradacion de performance
        //Por ello en el setters de path relacionamos ambos objetos
        PathTraveled pathSaved = pathTraveledRepository.save(path);

        assertThat(pathSaved.getCar()).isNotNull();
        assertThat(pathSaved.getCar().getPaths()).isNotNull();
        assertThat(pathSaved.getCar().getPaths()).isNotEmpty();

        assertThat(countInDb).isLessThan(pathTraveledRepository.count());
    }

    @Transactional
    @Rollback
    @Test
    void test_post_paths_with_car_and_with_positions(){

        long countPathInDb = pathTraveledRepository.count();
        long countPositionsDb = positionTraveledRepository.count();

        //Por CASCADE.PERSIST se persistiran todos las posiciones no guardadas
        Set<PositionTraveled> newPosition = Set.of(PositionTraveled.builder().positionInfo("Esta es una nueva posicion").build());

        PathTraveled path= PathTraveled.builder()
                .car(testCar)
                .positions(newPosition)
                .build();


        PathTraveled pathSaved = pathTraveledRepository.save(path);

        assertThat(pathSaved.getCar()).isNotNull();
        assertThat(pathSaved.getCar().getPaths()).isNotNull();
        assertThat(pathSaved.getCar().getPaths()).isNotEmpty();

        assertThat(pathSaved.getPositions()).isNotEmpty();
        assertThat(pathSaved.getPositions()).isNotEmpty();
        assertThat(pathSaved.getPositions().stream().toList().get(0).getPath()).isNotNull();


        assertThat(countPathInDb).isLessThan(pathTraveledRepository.count());
        assertThat(countPositionsDb).isLessThan(positionTraveledRepository.count());
    }
}