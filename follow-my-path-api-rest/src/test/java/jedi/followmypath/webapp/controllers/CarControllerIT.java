package jedi.followmypath.webapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.core.Is.is;

import jakarta.transaction.Transactional;
import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.exceptions.NotFoundException;
import jedi.followmypath.webapp.mappers.CarMapper;
import jedi.followmypath.webapp.model.dto.CarDTO;
import jedi.followmypath.webapp.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Test de integracion, necesitamos SpringBootTest, es decir tomar del contexto a todos los beans
@SpringBootTest
class CarControllerIT {

    @Autowired
    CarController carController;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarMapper carMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Value("${spring.security.user.name}")
    String username;

    @Value("${spring.security.user.password}")
    String password;

    @BeforeEach
    void setUp(){
        //Inyectamos el Spring application context dentro.
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @Test
    void test_list_cars_by_name() throws Exception {
        mockMvc.perform(get(CarController.CAR_PATH)
                        .with(httpBasic(username,password))
                .queryParam("model","Sandero%"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",is(1)));
    }

    @Test
    void test_list_cars_by_make() throws Exception {
        mockMvc.perform(get(CarController.CAR_PATH)
                        .with(httpBasic(username,password))
                        .queryParam("make","z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",is(10)));
    }

    @Test
    void test_list_cars_by_model_make_yearcar() throws Exception {
        mockMvc.perform(get(CarController.CAR_PATH)
                        .with(httpBasic(username,password))
                        .queryParam("model","900")
                        .queryParam("make","Saab")
                        .queryParam("yearCar","1988"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()",is(1)));
    }

    @Test
    void test_list_10_cars() throws Exception {
        mockMvc.perform(get(CarController.CAR_PATH)
                        .with(httpBasic(username,password))
                        .queryParam("pageNumber","1")
                        .queryParam("pageSize","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()",is(10)));
    }

    @Test
    void test_list_10_cars_from_3_different_pages() throws Exception {

        Page<CarDTO> carsPage1 = carController.getCars(null,null,null,1,10);

        Page<CarDTO> carsPage2 = carController.getCars(null,null,null,2,10);

        assertThat(carsPage1.getContent()).isNotEmpty();
        assertThat(carsPage2.getContent()).isNotEmpty();

        assertThat(carsPage1.getContent()).hasSameSizeAs(carsPage2.getContent());

        assertThat(carsPage1.getContent().get(0)).isNotEqualTo(carsPage2.getContent().get(0));
        assertThat(carsPage1.getContent().get(1)).isNotEqualTo(carsPage2.getContent().get(1));
        assertThat(carsPage1.getContent().get(2)).isNotEqualTo(carsPage2.getContent().get(2));
        assertThat(carsPage1.getContent().get(3)).isNotEqualTo(carsPage2.getContent().get(3));
        assertThat(carsPage1.getContent().get(4)).isNotEqualTo(carsPage2.getContent().get(4));
        assertThat(carsPage1.getContent().get(5)).isNotEqualTo(carsPage2.getContent().get(5));
        assertThat(carsPage1.getContent().get(6)).isNotEqualTo(carsPage2.getContent().get(6));
        assertThat(carsPage1.getContent().get(7)).isNotEqualTo(carsPage2.getContent().get(7));
        assertThat(carsPage1.getContent().get(8)).isNotEqualTo(carsPage2.getContent().get(8));
        assertThat(carsPage1.getContent().get(9)).isNotEqualTo(carsPage2.getContent().get(9));
    }

    @Test
    void test_list_cars_by_model_make_yearcar_and_page() throws Exception {
        mockMvc.perform(get(CarController.CAR_PATH)
                        .with(httpBasic(username,password))
                        .queryParam("model","900")
                        .queryParam("make","Saab")
                        .queryParam("yearCar","1988")
                        .queryParam("pageNumber","1")
                        .queryParam("pageSize","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()",is(1)));
    }

    @Rollback
    @Transactional
    @Test
    void test_patch_failed_car_bad_model() throws Exception {
        Car car = carRepository.findAll().get(0);
        Map<String,String> carMap = new HashMap<>();
        carMap.put("model","TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123TEST FAILED! 123");
        carMap.put("patentCar","TEST");
        carMap.put("size","TEST");
        carMap.put("yearCar","2019");
        carMap.put("fuelType","TEST");

        mockMvc.perform(put(CarController.CAR_PATH_ID,car.getId())
                        .with(httpBasic(username,password))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carMap)))
                .andExpect(status().isBadRequest());
    }


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
    void test_create_new_car() throws JsonProcessingException {

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
        Page<CarDTO> cars = carController.getCars(null,null,null, 1, 25000);

        assertThat(cars.getContent().size()).isEqualTo(103);
    }

    @Test
    void test_pagesize_is_100_and_i_wait_100_cars(){
        Page<CarDTO> cars = carController.getCars(null,null,null, 1, 100);

        assertThat(cars.getContent().size()).isEqualTo(100);
    }

    @Rollback
    @Transactional
    @Test
    void test_list_cars_is_empty(){
        carRepository.deleteAll();
        Page<CarDTO> cars = carController.getCars(null,null,null, 1, 25);

        assertThat(cars.getContent().size()).isEqualTo(0);
    }


}