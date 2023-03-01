package jedi.followmypath.webapp.services.cars.impl;

import jedi.followmypath.webapp.model.Car;
import jedi.followmypath.webapp.services.cars.CarService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    Map<UUID,Car> carsMap;

    public CarServiceImpl() {
        carsMap = new HashMap<>();

        Car carCreated = Car.builder()
                .uuid(UUID.randomUUID())
                .patentCar("1ASZW231")
                .size("MID")
                .make("Renault")
                .model("Sandero Stepway 1.6")
                .year(2013)
                .fuelType("Gas oil")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Car carCreated2 = Car.builder()
                .uuid(UUID.randomUUID())
                .patentCar("12CXW242")
                .size("MID")
                .make("TOYOTA")
                .model("RAV4")
                .year(2022)
                .fuelType("Gas oil")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Car carCreated3 = Car.builder()
                .uuid(UUID.randomUUID())
                .patentCar("123KPO213")
                .size("SMALL")
                .make("Peugeot")
                .model("208 EV")
                .year(2023)
                .fuelType("ELECTRIC")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        carsMap.put(carCreated.getUuid(),carCreated);
        carsMap.put(carCreated2.getUuid(),carCreated2);
        carsMap.put(carCreated3.getUuid(),carCreated3);
    }

    @Override
    public Car createCar(Car car) {
        Car carCreated = Car.builder()
                .uuid(UUID.randomUUID())
                .patentCar(car.getPatentCar())
                .size(car.getSize())
                .make(car.getMake())
                .year(car.getYear())
                .fuelType(car.getFuelType())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        carsMap.put(carCreated.getUuid(),carCreated);
        return car;
    }

    @Override
    public void updateCar(UUID uuid, Car car) {
        Optional<Car> findCar = getCarByUUID(uuid);
        if(findCar.isPresent()){
            Car carUpdated = findCar.get();

            carUpdated.setYear(car.getYear());
            carUpdated.setPatentCar(car.getPatentCar());
            carUpdated.setMake(car.getMake());
            carUpdated.setSize(car.getSize());
            carUpdated.setModel(car.getModel());
            carUpdated.setFuelType(car.getFuelType());
            carUpdated.setUpdateDate(LocalDateTime.now());

            carsMap.put(uuid,carUpdated);
        }
    }

    @Override
    public Optional<Car> getCarByUUID(UUID uuid) {
        return Optional.of(carsMap.get(uuid));
    }

    @Override
    public List<Car> getCars() {
        return carsMap.values()
                .stream()
                .toList();
    }

    @Override
    public void deleteCar(UUID uuid) {
        Optional<Car> findCar = getCarByUUID(uuid);
        if(findCar.isPresent()){
            carsMap.remove(findCar.get().getUuid());
        }
    }


}
