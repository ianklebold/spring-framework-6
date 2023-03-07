package jedi.followmypath.webapp.services.cars.impl;

import jedi.followmypath.webapp.model.CarDTO;
import jedi.followmypath.webapp.services.cars.CarService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    Map<UUID, CarDTO> carsMap;

    public CarServiceImpl() {
        carsMap = new HashMap<>();

        CarDTO carDTOCreated = CarDTO.builder()
                .id(UUID.randomUUID())
                .patentCar("1ASZW231")
                .size("MID")
                .make("Renault")
                .model("Sandero Stepway 1.6")
                .yearCar(2013)
                .fuelType("Gas oil")
                .createCarDate(LocalDateTime.now())
                .updateCarDate(LocalDateTime.now())
                .build();

        CarDTO carDTOCreated2 = CarDTO.builder()
                .id(UUID.randomUUID())
                .patentCar("12CXW242")
                .size("MID")
                .make("TOYOTA")
                .model("RAV4")
                .yearCar(2022)
                .fuelType("Gas oil")
                .createCarDate(LocalDateTime.now())
                .updateCarDate(LocalDateTime.now())
                .build();

        CarDTO carDTOCreated3 = CarDTO.builder()
                .id(UUID.randomUUID())
                .patentCar("123KPO213")
                .size("SMALL")
                .make("Peugeot")
                .model("208 EV")
                .yearCar(2023)
                .fuelType("ELECTRIC")
                .createCarDate(LocalDateTime.now())
                .updateCarDate(LocalDateTime.now())
                .build();

        carsMap.put(carDTOCreated.getId(), carDTOCreated);
        carsMap.put(carDTOCreated2.getId(), carDTOCreated2);
        carsMap.put(carDTOCreated3.getId(), carDTOCreated3);
    }

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        CarDTO carDTOCreated = CarDTO.builder()
                .id(UUID.randomUUID())
                .patentCar(carDTO.getPatentCar())
                .size(carDTO.getSize())
                .make(carDTO.getMake())
                .yearCar(carDTO.getYearCar())
                .fuelType(carDTO.getFuelType())
                .createCarDate(LocalDateTime.now())
                .updateCarDate(LocalDateTime.now())
                .build();

        carsMap.put(carDTOCreated.getId(), carDTOCreated);
        return carDTO;
    }

    @Override
    public Optional<CarDTO> updateCar(UUID uuid, CarDTO carDTO) {
        Optional<CarDTO> findCar = getCarById(uuid);
        if(findCar.isPresent()){
            CarDTO carDTOUpdated = findCar.get();

            carDTOUpdated.setYearCar(carDTO.getYearCar());
            carDTOUpdated.setPatentCar(carDTO.getPatentCar());
            carDTOUpdated.setMake(carDTO.getMake());
            carDTOUpdated.setSize(carDTO.getSize());
            carDTOUpdated.setModel(carDTO.getModel());
            carDTOUpdated.setFuelType(carDTO.getFuelType());
            carDTOUpdated.setUpdateCarDate(LocalDateTime.now());

            carsMap.put(uuid, carDTOUpdated);
            return Optional.of(carDTOUpdated);
        }

        return Optional.empty();
    }

    @Override
    public Optional<CarDTO> getCarById(UUID id) {
        return Optional.of(carsMap.get(id));
    }

    @Override
    public List<CarDTO> getCars(String model) {
        return carsMap.values()
                .stream()
                .toList();
    }

    @Override
    public Boolean deleteCar(UUID uuid) {
        Optional<CarDTO> findCar = getCarById(uuid);
        if(findCar.isPresent()){
            carsMap.remove(findCar.get().getId());
            return true;
        }
        return false;
    }


}
