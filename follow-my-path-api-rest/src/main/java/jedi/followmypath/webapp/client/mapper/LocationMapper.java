package jedi.followmypath.webapp.client.mapper;

import jedi.followmypath.webapp.model.dto.LocationDTO;

import java.util.Map;

public interface LocationMapper {
    LocationDTO mapToLocationDTO(Map locationMap);
}
