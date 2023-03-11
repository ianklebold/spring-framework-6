package jedi.followmypath.webapp.services.cars;

import jedi.followmypath.webapp.model.dto.CarDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;


public interface CarService {
    CarDTO createCar(CarDTO carDTO);

    Optional<CarDTO> updateCar(UUID uuid, CarDTO carDTO);

    Optional<CarDTO> getCarById(UUID id);

    Page<CarDTO> getCars(String model, String make, Integer yearCar, Integer pageNumber, Integer pageSize);

    Boolean deleteCar(UUID uuid);
}
