package jedi.followmypath.webapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PathTraveledDTO {
    private UUID id;
    private int version;
    private CarDTO carDTO;
    private Set<PositionTraveledDTO> positionsDto;

    private Timestamp createdDate;
    private Timestamp lastModifiedDate;

}
