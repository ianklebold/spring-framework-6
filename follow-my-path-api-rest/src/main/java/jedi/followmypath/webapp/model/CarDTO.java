package jedi.followmypath.webapp.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
//@Data <-- No es recomendable usarlo
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDTO {

    private UUID id;
    private Integer version;
    private String model;
    private int yearCar;
    private String patentCar;
    private String size;
    private String make;
    private String fuelType;

    private LocalDateTime createCarDate;
    private LocalDateTime updateCarDate;
}
