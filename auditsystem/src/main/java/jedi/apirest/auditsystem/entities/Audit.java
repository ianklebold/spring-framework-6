package jedi.apirest.auditsystem.entities;

import jakarta.persistence.*;
import jedi.apirest.auditsystem.entities.enums.AuditType;
import jedi.apirest.auditsystem.entities.enums.Level;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false, updatable = false)
    private Level level;

    @Column(nullable = false, updatable = false)
    private AuditType auditType;

    @Column(nullable = false, updatable = false)
    private String webservice;

    @Column(nullable = false, updatable = false, length = 200000)
    private String log;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime modificationDate;
}