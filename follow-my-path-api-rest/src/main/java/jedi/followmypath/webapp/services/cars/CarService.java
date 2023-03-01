package jedi.followmypath.webapp.services.cars;

import jedi.followmypath.webapp.model.Car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CarService {
    Car createCar(Car car);

    void updateCar(UUID uuid, Car car);

    Optional<Car> getCarByUUID(UUID uuid);

    List<Car> getCars();

    void deleteCar(UUID uuid);
}
