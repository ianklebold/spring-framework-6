package jedi.apirest.auditsystem.repositories;

import jedi.apirest.auditsystem.entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit,Long>{


}
