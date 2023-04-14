package com.auditsystem.auditsystemcommons.entities;

import com.auditsystem.auditsystemcommons.entities.enums.AuditType;
import com.auditsystem.auditsystemcommons.entities.enums.Level;
import jakarta.persistence.*;
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

    @Column(nullable = false, updatable = false,length = 200000)
    private String log;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime modificationDate;
}
