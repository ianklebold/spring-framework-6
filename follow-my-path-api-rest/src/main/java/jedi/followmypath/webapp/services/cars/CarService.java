package jedi.followmypath.webapp.services.cars;

import jedi.followmypath.webapp.model.CarDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CarService {
    CarDTO createCar(CarDTO carDTO);

    Optional<CarDTO> updateCar(UUID uuid, CarDTO carDTO);

    Optional<CarDTO> getCarById(UUID id);

    List<CarDTO> getCars(String model);

    Boolean deleteCar(UUID uuid);
}
