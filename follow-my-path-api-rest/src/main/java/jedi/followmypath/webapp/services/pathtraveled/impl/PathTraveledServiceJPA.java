package jedi.followmypath.webapp.services.pathtraveled.impl;

import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.entities.PathTraveled;
import jedi.followmypath.webapp.entities.PositionTraveled;
import jedi.followmypath.webapp.mappers.PathMapper;
import jedi.followmypath.webapp.model.dto.PathTraveledDTO;
import jedi.followmypath.webapp.repositories.PathTraveledPageRepository;
import jedi.followmypath.webapp.repositories.PathTraveledRepository;
import jedi.followmypath.webapp.services.pagesrequest.PageRequestService;
import jedi.followmypath.webapp.services.pathtraveled.PathTraveledService;
import jedi.followmypath.webapp.services.positiontraveled.PositionTraveledService;
import jedi.followmypath.webapp.services.utils.UtilService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class PathTraveledServiceJPA implements PathTraveledService{

    private final PathTraveledRepository pathTraveledRepository;

    private final PathTraveledPageRepository pathTraveledPageRepository;

    private final PositionTraveledService positionTraveledService;

    private final UtilService utilService;

    private final PageRequestService pageRequestService;

    private final PathMapper pathMapper;

    private static final String PROPERTY_SORT = "createdDate";


    @Override
    public PathTraveled createPathTraveled(Car car) throws URISyntaxException, IOException {
        Optional<PathTraveled> optionalPathTraveled = pathTraveledRepository.findAllByCar(car)
                .stream()
                .filter(path -> utilService.isToday(path.getCreatedDate().toLocalDateTime()))
                .findFirst();

        if(optionalPathTraveled.isPresent()){
            log.info("Exist path for "+car.getId() + "Creating new position");
            PathTraveled pathTraveled = optionalPathTraveled.get();
            PositionTraveled positionTraveled = positionTraveledService.createPosition();
            pathTraveled.setPositions(Set.of(positionTraveled));
            pathTraveled.setLastModifiedDate(Timestamp.valueOf(LocalDateTime.now()));
            return pathTraveledRepository.save(pathTraveled);

        }else {
            log.info("Not exist path for "+car.getId() + "Creating new path with position");
            Set<PositionTraveled> positionTraveled = Set.of(positionTraveledService.createPosition());

            PathTraveled pathTraveled = PathTraveled.builder()
                    .car(car)
                    .positions(positionTraveled)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            return pathTraveledRepository.save(pathTraveled);
        }

    }

    @Override
    public Page<PathTraveledDTO> getPathsByCar(Car car, Integer pageNumber, Integer pageSize) {
        Pageable pageable = pageRequestService.buildPageRequest(pageNumber,pageSize,PROPERTY_SORT);


        return pathTraveledPageRepository.findAllByCar(car, pageable)
                .map(pathMapper::pathTraveledToPathTraveledDto);
    }
}
