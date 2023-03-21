package jedi.followmypath.webapp.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
//@Data <-- No es recomendable usarlo
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private UUID id;
    private Integer version;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String surname;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Past
    private LocalDateTime birthDate;

    private String country;

    private LocalDateTime createCustomerDate;
    private LocalDateTime updateCustomerDate;
}
