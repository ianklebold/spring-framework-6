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
public class CustomerDTO {
    private UUID id;
    private Integer version;
    private String name;
    private String surname;
    private LocalDateTime birthDate;
    private String country;

    private LocalDateTime createCustomerDate;
    private LocalDateTime updateCustomerDate;
}
