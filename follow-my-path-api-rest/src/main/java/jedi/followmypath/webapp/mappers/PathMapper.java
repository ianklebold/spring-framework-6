package jedi.followmypath.webapp.mappers;

import jedi.followmypath.webapp.entities.PathTraveled;
import jedi.followmypath.webapp.model.dto.PathTraveledDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {PositionMapper.class, CarMapper.class})
public interface PathMapper {

    @Mapping(source = "positions", target = "positionsDto")
    @Mapping(source = "car", target = "carDTO")
    PathTraveledDTO pathTraveledToPathTraveledDto(PathTraveled pathTraveled);
}
