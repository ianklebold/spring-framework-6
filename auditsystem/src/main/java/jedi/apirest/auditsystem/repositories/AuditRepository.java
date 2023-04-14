package jedi.apirest.auditsystem.repositories;

import jedi.apirest.auditsystem.entities.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;

@RepositoryRestResource(path = "audit", collectionResourceRel = "audit")
public interface AuditRepository extends JpaRepository<Audit,Long>{

    Page<Audit> findAllByCreationDateIsAfter(LocalDateTime localDateTime, Pageable pageable);


}
