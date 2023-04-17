package jedi.followmypath.webapp.services.cars;

import com.fasterxml.jackson.core.JsonProcessingException;
import jedi.followmypath.webapp.model.dto.CarDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;


public interface CarService {
    CarDTO createCar(CarDTO carDTO) throws JsonProcessingException;

    Optional<CarDTO> updateCar(UUID uuid, CarDTO carDTO) throws JsonProcessingException;

    Optional<CarDTO> getCarById(UUID id);

    Page<CarDTO> getCars(String model, String make, Integer yearCar, Integer pageNumber, Integer pageSize);

    Boolean deleteCar(UUID uuid);
}
