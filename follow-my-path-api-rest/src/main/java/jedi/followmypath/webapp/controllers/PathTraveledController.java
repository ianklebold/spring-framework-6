package jedi.followmypath.webapp.controllers;

import jedi.followmypath.webapp.entities.PathTraveled;
import jedi.followmypath.webapp.exceptions.NotFoundException;
import jedi.followmypath.webapp.mappers.CarMapper;
import jedi.followmypath.webapp.model.dto.CarDTO;
import jedi.followmypath.webapp.model.dto.PathTraveledDTO;
import jedi.followmypath.webapp.services.cars.CarService;
import jedi.followmypath.webapp.services.pathtraveled.PathTraveledService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class PathTraveledController {
    public static final String PATH_URL = "/api/v1/path";
    public static final String PATH_URL_ID = PATH_URL + "/{uuidPath}";
    public static final String PATH_CAR_ID = PATH_URL+"/cars/{uuidCar}";

    private final CarService carService;

    private final PathTraveledService pathTraveledService;

    private final CarMapper carMapper;

    @PostMapping(value = PATH_CAR_ID)
    public ResponseEntity createPathByCar(@PathVariable UUID uuidCar) throws NotFoundException {
        //Obtener usuario del contexto de spring

        Optional<CarDTO> carDTO =  carService.getCarById(uuidCar);

        if(carDTO.isPresent()){
            PathTraveled pathSaved = pathTraveledService.createPathTraveled(carMapper.carDtoToCar(carDTO.get()));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location",PATH_URL+"/"+ pathSaved.getId());

            return new ResponseEntity(headers, HttpStatus.CREATED);
        }
        throw new NotFoundException();
    }

    @GetMapping(value = PATH_CAR_ID)
    public Page<PathTraveledDTO> getPathsByCar(@PathVariable UUID uuidCar,
                                               @RequestParam(required = false, name ="pageNumber") Integer pageNumber,
                                               @RequestParam(required = false, name = "pageSize") Integer pageSize) throws NotFoundException {
        //Obtener usuario del contexto de spring

        Optional<CarDTO> carDTO =  carService.getCarById(uuidCar);

        if(carDTO.isPresent()){
            return pathTraveledService.getPathsByCar(carMapper.carDtoToCar(carDTO.get()),pageNumber,pageSize);
        }
        throw new NotFoundException();
    }



}
