package jedi.followmypath.webapp.controllers;

import jedi.followmypath.webapp.exceptions.NotFoundException;
import jedi.followmypath.webapp.model.Car;
import jedi.followmypath.webapp.services.cars.CarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class CarController {
    public static final String CAR_PATH = "/api/v1/cars";
    public static final String CAR_PATH_ID = CAR_PATH + "/{uuidCar}";
    private final CarService carService;

    @PostMapping(value = CAR_PATH)
    public ResponseEntity createCar(@RequestBody Car car){
        Car carCreated = carService.createCar(car);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location",CAR_PATH+"/"+carCreated.getUuid());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CAR_PATH_ID)
    public Car getCarByUUID(@PathVariable UUID uuidCar) throws Exception {
        return carService.getCarByUUID(uuidCar).orElseThrow(NotFoundException::new);
    }

    @PutMapping(value = CAR_PATH_ID)
    public ResponseEntity updateCar(@PathVariable(value = "uuidCar") UUID uuidCar, @RequestBody Car car){
        carService.updateCar(uuidCar,car);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = CAR_PATH)
    public List<Car> getCars(){
        return carService.getCars();
    }

    @DeleteMapping(value = CAR_PATH_ID)
    public ResponseEntity deleteCar(@PathVariable(value = "uuidCar") UUID uuid){
        carService.deleteCar(uuid);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }





}
