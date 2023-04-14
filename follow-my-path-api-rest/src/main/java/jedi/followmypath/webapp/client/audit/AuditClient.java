package jedi.followmypath.webapp.client.audit;

import com.auditsystem.auditsystemcommons.entities.Audit;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface AuditClient {

    Page<Audit> listAudits(Integer pageNumber, Integer pageSize);
    Page<Audit> listAudits(LocalDateTime localDateTime,Integer pageNumber, Integer pageSize);

    Audit createAudit(Audit newAudit);
}
