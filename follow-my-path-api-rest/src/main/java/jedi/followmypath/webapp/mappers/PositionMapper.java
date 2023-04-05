package jedi.followmypath.webapp.mappers;

import jedi.followmypath.webapp.entities.PositionTraveled;
import jedi.followmypath.webapp.model.dto.PositionTraveledDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PositionMapper {
    PositionTraveledDTO positionTraveledToPositionTraveledDTO(PositionTraveled positionTraveled);
}
