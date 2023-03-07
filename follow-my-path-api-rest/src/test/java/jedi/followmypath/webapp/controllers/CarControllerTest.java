package jedi.followmypath.webapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jedi.followmypath.webapp.model.CarDTO;
import jedi.followmypath.webapp.services.cars.CarService;
import jedi.followmypath.webapp.services.cars.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CarService carService;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

    @Captor
    ArgumentCaptor<CarDTO> carArgumentCaptor = ArgumentCaptor.forClass(CarDTO.class);

    CarServiceImpl carServiceImpl;

    @BeforeEach
    void setUp(){
        this.carServiceImpl = new CarServiceImpl();
    }



    @Nested
    @DisplayName("GET ENDPOINTS")
    class test_method_get_for_cars{

        @Test
        void get_all_cars() throws Exception {

            List<CarDTO> carDTOList = carServiceImpl.getCars(null);

            given(carService.getCars(null)).willReturn(carDTOList);

             mockMvc.perform(get(CarController.CAR_PATH)
                             .accept(MediaType.APPLICATION_JSON))
                     .andExpect(status().isOk())
                     .andExpect(jsonPath("$.length()",is(3)));
        }

        @Test
        void get_car_by_uuid() throws Exception {
            CarDTO cars = carServiceImpl.getCars(null).get(0);

            given(carService.getCarById(any(UUID.class))).willReturn(Optional.of(cars));

            mockMvc.perform(get(CarController.CAR_PATH_ID,cars.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id",is(cars.getId().toString())))
                    .andExpect(jsonPath("$.model",is(cars.getModel())))
                    .andExpect(jsonPath("$.patentCar",is(cars.getPatentCar())))
                    .andExpect(jsonPath("$.size",is(cars.getSize())))
                    .andExpect(jsonPath("$.make",is(cars.getMake())))
                    .andExpect(jsonPath("$.fuelType",is(cars.getFuelType())));
        }

        @Test
        void get_car_by_uuid_not_found_exception() throws Exception {
            //Cuando no encontremos el car devolver empty
            given(carService.getCarById(any(UUID.class))).willReturn(Optional.empty());

            //Al recibir empty deberiamos devolver not Found
            mockMvc.perform(get(CarController.CAR_PATH_ID,UUID.randomUUID()))
                    .andExpect(status().isNotFound());
        }

    }

    @Nested
    @DisplayName("TESTING POST ENDPOINTS")
    class testing_method_post_for_cars{
        @Test
        void post_new_cart() throws Exception {
            CarDTO carDTO = carServiceImpl.getCars(null).get(0);
            carDTO.setId(null);

            given(carService.createCar(any(CarDTO.class))).willReturn(carServiceImpl.getCars(null).get(1));

            mockMvc.perform(post(CarController.CAR_PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(carDTO))
            ).andExpect(status().isCreated())
                    .andExpect(header().exists("Location"));

        }

        @Test
        void when_create_an_car_with_model_null_return_bad_request() throws Exception {
            CarDTO carDTO = CarDTO.builder().build();

            given(carService.createCar(any(CarDTO.class))).willReturn(carServiceImpl.getCars(null).get(0));

            MvcResult mvcResult = mockMvc.perform(post(CarController.CAR_PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(carDTO)))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            System.out.println(mvcResult.getResponse().getContentAsString());

        }

    }

    @Nested
    @DisplayName("TESTING DELETE ENDPOINTS")
    class testing_method_delete_for_cars{
        @Test
        void delete_car_by_uuid() throws Exception {
            CarDTO carDTO = carServiceImpl.getCars(null).get(0);

            given(carService.deleteCar(any())).willReturn(true);

            mockMvc.perform(delete(CarController.CAR_PATH_ID, carDTO.getId())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());

            verify(carService).deleteCar(uuidArgumentCaptor.capture());

            assertThat(carDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    @DisplayName("TESTING UPDATE ENDPOINTS")
    class testing_method_update_for_cars{
        @Test
        void update_car_by_uuid() throws Exception {
            CarDTO carDTO = carServiceImpl.getCars(null).get(0);
            carDTO.setUpdateCarDate(LocalDateTime.now());

            given(carService.updateCar(any(),any())).willReturn(Optional.of(carDTO));

            mockMvc.perform(put(CarController.CAR_PATH_ID, carDTO.getId())
                    .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(carDTO)))
                    .andExpect(status().isNoContent());

            verify(carService).updateCar(uuidArgumentCaptor.capture(),carArgumentCaptor.capture());

            assertThat(carDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        }
    }


}