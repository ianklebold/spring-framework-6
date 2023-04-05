package jedi.followmypath.webapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jedi.followmypath.webapp.mappers.CarMapper;
import jedi.followmypath.webapp.model.dto.CarDTO;
import jedi.followmypath.webapp.model.dto.PathTraveledDTO;
import jedi.followmypath.webapp.model.dto.PositionTraveledDTO;
import jedi.followmypath.webapp.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class PathTraveledControllerTestIT {
    @Autowired
    CarController carController;
    @Autowired
    PathTraveledController pathTraveledController;
    @Autowired
    CarRepository carRepository;

    @Autowired
    CarMapper carMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        //Inyectamos el Spring application context dentro.
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Rollback
    @Transactional
    @Test
    void test_when_we_create_a_new_path_we_get_a_created_status() throws Exception {
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

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        mockMvc.perform(post(PathTraveledController.PATH_CAR_ID,savedUUID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Rollback
    @Transactional
    @Test
    void test_when_we_create_a_new_path_with_a_unexist_car_we_get_a_not_found_status() throws Exception {
        mockMvc.perform(post(PathTraveledController.PATH_CAR_ID,UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Rollback
    @Transactional
    @Test
    void test_get_a_empty_list_of_paths_by_car() throws Exception {
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

        String[] locationCarUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedCarUUID = UUID.fromString(locationCarUUID[4]);

        mockMvc.perform(get(PathTraveledController.PATH_CAR_ID,savedCarUUID)
                        .queryParam("pageNumber","1")
                        .queryParam("pageSize","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()",is(0)));
    }

    @Rollback
    @Transactional
    @Test
    void test_get_a_list_of_paths_by_car() throws Exception {
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

        String[] locationCarUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedCarUUID = UUID.fromString(locationCarUUID[4]);

        pathTraveledController.createPathByCar(savedCarUUID);

        mockMvc.perform(get(PathTraveledController.PATH_CAR_ID,savedCarUUID)
                        .queryParam("pageNumber","1")
                        .queryParam("pageSize","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()",is(1)));
    }

    @Rollback
    @Transactional
    @Test
    void test_get_a_list_of_positions_by_car() throws Exception {
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

        String[] locationCarUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedCarUUID = UUID.fromString(locationCarUUID[4]);
        pathTraveledController.createPathByCar(savedCarUUID);


        Page<PathTraveledDTO> pathTraveledDTO = pathTraveledController.getPathsByCar(savedCarUUID,null,null);

        assertThat(pathTraveledDTO.getContent()).isNotEmpty();
        assertThat(pathTraveledDTO.getContent().get(0).getCarDTO()).isNotNull();
        CarDTO carDTO = pathTraveledDTO.getContent().get(0).getCarDTO();

        assertThat(carDTO.getPatentCar()).isEqualTo(car.getPatentCar());
        assertThat(carDTO.getSize()).isEqualTo(car.getSize());
        assertThat(carDTO.getMake()).isEqualTo(car.getMake());
        assertThat(carDTO.getModel()).isEqualTo(car.getModel());
        assertThat(carDTO.getYearCar()).isEqualTo(car.getYearCar());
        assertThat(carDTO.getFuelType()).isEqualTo(car.getFuelType());
        assertThat(carDTO.getCreateCarDate()).isEqualTo(car.getCreateCarDate());
        assertThat(carDTO.getUpdateCarDate()).isEqualTo(car.getUpdateCarDate());

        assertThat(pathTraveledDTO.getContent().get(0).getPositionsDto()).isNotNull();
        assertThat(pathTraveledDTO.getContent().get(0).getPositionsDto()).isNotEmpty();

        Optional<PositionTraveledDTO> positionTraveledDTO = pathTraveledDTO.getContent().get(0).getPositionsDto().stream().findFirst();

        assertThat(positionTraveledDTO).isPresent();
        assertThat(positionTraveledDTO.get().getId()).isNotNull();
        assertThat(positionTraveledDTO.get().getPositionInfo()).isNotNull();
        assertThat(positionTraveledDTO.get().getVersion()).isOne();
        assertThat(positionTraveledDTO.get().getCreatedDate()).isNotNull();
        assertThat(positionTraveledDTO.get().getLastModifiedDate()).isNotNull();

        assertThat(pathTraveledDTO.getContent().get(0).getId()).isNotNull();
        assertThat(pathTraveledDTO.getContent().get(0).getVersion()).isZero();
        assertThat(pathTraveledDTO.getContent().get(0).getCreatedDate()).isNotNull();
        assertThat(pathTraveledDTO.getContent().get(0).getLastModifiedDate()).isNotNull();
    }



}