package jedi.followmypath.webapp.services.pathtraveled;

import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.entities.PathTraveled;
import jedi.followmypath.webapp.model.dto.PathTraveledDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.net.URISyntaxException;

public interface PathTraveledService {

    PathTraveled createPathTraveled(Car car) throws URISyntaxException, IOException;

    Page<PathTraveledDTO> getPathsByCar(Car car, Integer pageNumber, Integer pageSize);

}
