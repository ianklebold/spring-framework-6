package jedi.followmypath.webapp.model.restauditdto.impl;

import com.auditsystem.auditsystemcommons.entities.Audit;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jedi.followmypath.webapp.model.restauditdto.AuditPageConvertor;
import jedi.followmypath.webapp.model.restauditdto.CustomPageImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class AuditPageConvertorImpl implements AuditPageConvertor {

    @Override
    public ResponseEntity getPageAudit(ResponseEntity<CustomPageImpl> stringResponseEntity) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List contentString = stringResponseEntity.getBody().getContent();

        List content = contentString.stream().map(audit -> mapper.convertValue(audit, Audit.class)).toList();

        return new ResponseEntity(content,stringResponseEntity.getHeaders(),stringResponseEntity.getStatusCode());
    }
}
