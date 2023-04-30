package jedi.followmypath.webapp.services.positiontraveled.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import jedi.followmypath.webapp.client.location.LocationClient;
import jedi.followmypath.webapp.entities.PositionTraveled;
import jedi.followmypath.webapp.model.dto.LocationDTO;
import jedi.followmypath.webapp.repositories.PositionTraveledRepository;
import jedi.followmypath.webapp.services.positiontraveled.PositionTraveledService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Slf4j
@AllArgsConstructor
@Service
public class PositionTraveledServiceJPA implements PositionTraveledService {

    private final PositionTraveledRepository positionTraveledRepository;

    private final LocationClient locationClient;

    @Override
    public PositionTraveled createPosition() throws URISyntaxException, IOException {

        JsonMapper jsonMapper = new JsonMapper();

        LocationDTO locationDTO = locationClient.getCarPosition();

        if(locationDTO != null){
            String positionInfo = jsonMapper.writeValueAsString(locationDTO);

            PositionTraveled positionTraveled = PositionTraveled.builder()
                    .positionInfo(positionInfo)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .build();

            positionTraveled = positionTraveledRepository.save(positionTraveled);
            return positionTraveled;
        }else {
            return null;
        }
    }

}
