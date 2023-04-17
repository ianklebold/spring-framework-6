package jedi.followmypath.webapp.services.positiontraveled.impl;

import jedi.followmypath.webapp.entities.PositionTraveled;
import jedi.followmypath.webapp.repositories.PositionTraveledRepository;
import jedi.followmypath.webapp.services.positiontraveled.PositionTraveledService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Slf4j
@AllArgsConstructor
@Service
public class PositionTraveledServiceJPA implements PositionTraveledService {

    private final PositionTraveledRepository positionTraveledRepository;

    @Override
    public PositionTraveled createPosition() {
        PositionTraveled positionTraveled = PositionTraveled.builder()
                .positionInfo("new Postion")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        positionTraveled = positionTraveledRepository.save(positionTraveled);
        return positionTraveled;
    }
}
