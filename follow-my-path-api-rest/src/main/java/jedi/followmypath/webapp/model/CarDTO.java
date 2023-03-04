package jedi.followmypath.webapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @NotNull
    private String model;

    @NotNull
    private int yearCar;

    @NotBlank
    @NotNull
    private String patentCar;

    @NotBlank
    @NotNull
    private String size;

    @NotBlank
    @NotNull
    private String make;

    @NotBlank
    @NotNull
    private String fuelType;

    private LocalDateTime createCarDate;
    private LocalDateTime updateCarDate;
}
