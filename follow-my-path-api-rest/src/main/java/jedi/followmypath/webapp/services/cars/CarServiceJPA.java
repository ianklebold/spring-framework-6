package jedi.followmypath.webapp.services.cars;

import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.mappers.CarMapper;
import jedi.followmypath.webapp.model.dto.CarDTO;
import jedi.followmypath.webapp.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CarServiceJPA implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    @Override
    public CarDTO createCar(CarDTO carDTO) {
        return carMapper.carToCarDto(
                carRepository.save(carMapper.carDtoToCar(carDTO))
        );
    }

    @Override
    public Optional<CarDTO> updateCar(UUID uuid, CarDTO carDTO) {
        Optional<CarDTO> car = getCarById(uuid);

        if(car.isPresent()){

            Car carUpdated = carMapper.carDtoToCar(car.get());

            carUpdated.setModel(carDTO.getModel());
            carUpdated.setMake(carDTO.getMake());
            carUpdated.setPatentCar(carDTO.getPatentCar());
            carUpdated.setSize(carDTO.getSize());
            carUpdated.setYearCar(carDTO.getYearCar());
            carUpdated.setFuelType(carDTO.getFuelType());
            carUpdated.setUpdateCarDate(LocalDateTime.now());

            carRepository.save(carUpdated);

            return Optional.of(carMapper.carToCarDto(carUpdated));
        }

        return Optional.empty();
    }

    @Override
    public Optional<CarDTO> getCarById(UUID id) {
        return Optional.ofNullable(carMapper.carToCarDto(carRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public List<CarDTO> getCars(String model) {
        List<Car> carList;
        if(StringUtils.hasText(model)){
            carList = listCarByModel(model);
        }else {
            carList = carRepository.findAll();
        }

        return carList
                .stream()
                .map(carMapper::carToCarDto)
                .collect(Collectors.toList());
    }

    public List<Car> listCarByModel(String model){
        // % WildCards --> Tdo lo que viene despues, puede ser cualquier cosa
        return carRepository.findAllByModelIsLikeIgnoreCase("%"+ model +"%");
    }

    @Override
    public Boolean deleteCar(UUID uuid) {
        if (carRepository.existsById(uuid)){
            carRepository.deleteById(uuid);
            return true;
        }
        return false;
    }
}
