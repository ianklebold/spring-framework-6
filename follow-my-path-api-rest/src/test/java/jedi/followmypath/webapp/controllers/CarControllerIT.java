package jedi.followmypath.webapp.controllers;

import com.sun.source.tree.AssertTree;
import jakarta.transaction.Transactional;
import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.exceptions.NotFoundException;
import jedi.followmypath.webapp.mappers.CarMapper;
import jedi.followmypath.webapp.model.CarDTO;
import jedi.followmypath.webapp.repositories.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//Test de integracion, necesitamos SpringBootTest, es decir tomar del contexto a todos los beans
@SpringBootTest
class CarControllerIT {

    @Autowired
    CarController carController;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarMapper carMapper;

    @Test
    void test_delete_fail_id_not_found() throws NotFoundException {

        assertThrows(NotFoundException.class, () ->{
            carController.deleteCar(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void delete_by_id() throws NotFoundException {
        Car car = carRepository.findAll().get(0);

        ResponseEntity responseEntity = carController.deleteCar(car.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(carRepository.findById(car.getId())).isEmpty();
    }

    @Test
    void test_update_fail_id_not_found(){

        assertThrows(NotFoundException.class, () ->{
            carController.updateCar(UUID.randomUUID(),CarDTO.builder().build());
        });
    }

    @Rollback //Estas dos anotaciones son necesrias, para que se restablezca la BD para los demas tests
    @Transactional
    @Test
    void test_update_car() throws Exception {
        Car car = carRepository.findAll().get(0);
        CarDTO carDTO = carMapper.carToCarDto(car);
        carDTO.setId(null);
        carDTO.setVersion(null);

        final String modelName = "UPDATED";
        final Integer version = car.getVersion();
        final LocalDateTime updateDate = car.getUpdateCarDate();

        carDTO.setModel(modelName);

        ResponseEntity responseEntity = carController.updateCar(car.getId(),carDTO);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Car carUpdated = carMapper.carDtoToCar(carController.getCarByUUID(car.getId()));
        assertThat(carUpdated.getModel()).isEqualTo(modelName);
        assertThat(carUpdated.getUpdateCarDate()).isNotEqualTo(updateDate);
    }



    @Rollback
    @Transactional
    @Test
    void test_create_new_car(){

        CarDTO car = CarDTO.builder()
                .patentCar("1ASZW231")
                .size("MID")
                .make("Renault")
                .model("Sandero Stepway 1.6")
                .yearCar(2013)
                .fuelType("Gas oil")
                .createCarDate(LocalDateTime.now())
                .updateCarDate(LocalDateTime.now())
                .build();

        ResponseEntity responseEntity = carController.createCar(car);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Car carEntity = carRepository.findById(savedUUID).get();
        assertThat(carEntity).isNotNull();

    }

    @Test
    void test_exception_when_id_car_not_found(){
        assertThrows(NotFoundException.class,() -> {
            CarDTO carDTO = carController.getCarByUUID(UUID.randomUUID());
        });
    }

    @Test
    void test_get_car_by_id() throws Exception {
        Car car = carRepository.findAll().get(0);

        CarDTO carDTO = carController.getCarByUUID(car.getId());

        assertThat(carDTO).isNotNull();
    }

    @Test
    void test_list_cars_is_not_empty(){
        List<CarDTO> cars = carController.getCars();

        assertThat(cars.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void test_list_cars_is_empty(){
        carRepository.deleteAll();
        List<CarDTO> cars = carController.getCars();

        assertThat(cars.size()).isEqualTo(0);
    }
}