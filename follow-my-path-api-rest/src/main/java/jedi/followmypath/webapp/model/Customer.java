package jedi.followmypath.webapp.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Customer {
    private UUID uuid;
    private String name;
    private String surname;
    private LocalDateTime birthDate;
    private String country;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
