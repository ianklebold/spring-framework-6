package jedi.followmypath.webapp.client;

import com.auditsystem.auditsystemcommons.entities.Audit;
import jedi.followmypath.webapp.model.restauditdto.CustomPageImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface AuditClient {
    Page<Audit> listAudits();
}
