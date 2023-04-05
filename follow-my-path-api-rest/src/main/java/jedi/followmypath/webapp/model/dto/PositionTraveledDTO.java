package jedi.followmypath.webapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PositionTraveledDTO {
    private UUID id;
    private int version;
    String positionInfo;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;

}
