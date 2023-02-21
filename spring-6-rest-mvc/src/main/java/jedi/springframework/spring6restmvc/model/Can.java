package jedi.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data //Equivalente a @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
public class Can {
    private UUID id;
    private String name;
    private String ownerName;
    private LocalDateTime birthDate;
    private String pedigree;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
