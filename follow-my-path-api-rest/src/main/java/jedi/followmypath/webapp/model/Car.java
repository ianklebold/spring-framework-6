package jedi.followmypath.webapp.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Car {

    private UUID uuid;
    private String model;
    private int year;
    private String patentCar;
    private String size;
    private String make;
    private String fuelType;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
