package jedi.followmypath.webapp.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationDTO {
    private String ip;
    private String countryCode;
    private String countryName;
    private String regionName;
    private String cityName;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String timeZone;
    private String asn;
    private String as;
    private Boolean isProxy;
}
