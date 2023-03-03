package jedi.followmypath.webapp.mappers;

import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.model.CarDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {
    Car carDtoToCar(CarDTO dto);

    CarDTO carToCarDto(Car car);
}
