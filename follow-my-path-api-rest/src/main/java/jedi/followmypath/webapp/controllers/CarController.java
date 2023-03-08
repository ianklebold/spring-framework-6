package jedi.followmypath.webapp.controllers;

import jedi.followmypath.webapp.exceptions.NotFoundException;
import jedi.followmypath.webapp.model.dto.CarDTO;
import jedi.followmypath.webapp.services.cars.CarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class CarController {
    public static final String CAR_PATH = "/api/v1/cars";
    public static final String CAR_PATH_ID = CAR_PATH + "/{uuidCar}";
    private final CarService carService;

    @PostMapping(value = CAR_PATH)
    public ResponseEntity createCar(@Validated @RequestBody CarDTO carDTO){
        CarDTO carDTOCreated = carService.createCar(carDTO);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location",CAR_PATH+"/"+ carDTOCreated.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CAR_PATH_ID)
    public CarDTO getCarByUUID(@PathVariable UUID uuidCar) throws NotFoundException {
        return carService.getCarById(uuidCar).orElseThrow(NotFoundException::new);
    }

    @PutMapping(value = CAR_PATH_ID)
    public ResponseEntity updateCar(@PathVariable(value = "uuidCar") UUID uuidCar, @RequestBody CarDTO carDTO) throws NotFoundException {

        Optional<CarDTO> carDTOUpdated = carService.updateCar(uuidCar, carDTO);

        if(carDTOUpdated.isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = CAR_PATH)
    public List<CarDTO> getCars(){
        return carService.getCars();
    }

    @DeleteMapping(value = CAR_PATH_ID)
    public ResponseEntity deleteCar(@PathVariable(value = "uuidCar") UUID uuid) throws NotFoundException {

        Boolean isSuccessful = carService.deleteCar(uuid);

        if(Boolean.FALSE.equals(isSuccessful)){
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }





}
