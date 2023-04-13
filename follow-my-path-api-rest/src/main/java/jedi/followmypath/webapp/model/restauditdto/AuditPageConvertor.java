package jedi.followmypath.webapp.model.restauditdto;

import org.springframework.http.ResponseEntity;

public interface AuditPageConvertor {
    ResponseEntity getPageAudit(ResponseEntity<CustomPageImpl> stringResponseEntity);
}
