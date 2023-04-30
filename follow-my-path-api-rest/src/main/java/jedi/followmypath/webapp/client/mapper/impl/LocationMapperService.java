package jedi.followmypath.webapp.client.mapper.impl;

import jedi.followmypath.webapp.client.mapper.LocationMapper;
import jedi.followmypath.webapp.model.dto.LocationDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LocationMapperService implements LocationMapper {

    @Override
    public LocationDTO mapToLocationDTO(Map locationMap) {
        return (locationMap != null)? getLocationDto(locationMap) : null;
    }

    private LocationDTO getLocationDto(Map locationMap){
        return LocationDTO.builder()
                .ip((String) locationMap.get("ip"))
                .countryCode((String) locationMap.get("country_code"))
                .countryName((String) locationMap.get("country_name"))
                .regionName((String) locationMap.get("region_name"))
                .cityName((String) locationMap.get("city_name"))
                .latitude((Double) locationMap.get("latitude"))
                .longitude((Double) locationMap.get("longitude"))
                .zipCode((String) locationMap.get("zip_code"))
                .timeZone((String) locationMap.get("time_zone"))
                .asn((String) locationMap.get("asn"))
                .as((String) locationMap.get("as"))
                .isProxy((Boolean) locationMap.get("is_proxy"))
                .build();
    }

}
