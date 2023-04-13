package jedi.followmypath.webapp.client;

import com.auditsystem.auditsystemcommons.entities.Audit;
import jedi.followmypath.webapp.model.restauditdto.AuditPageConvertor;
import jedi.followmypath.webapp.model.restauditdto.CustomPageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@RequiredArgsConstructor
@Service
public class AuditClientImpl implements AuditClient {

    private final RestTemplateBuilder restTemplateBuilder;

    private final AuditPageConvertor auditPageConvertor;

    private static final String BASE_URL = "http://localhost:8080";
    private static final String GET_AUDIT_PATH = BASE_URL + "/api/v1/audit";

    @Override
    public Page<Audit> listAudits() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        // Con String simplemente convierte la request en String
        // ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity(GET_AUDIT_PATH, String.class);

        //Con map, convierte cada objeto del listado en un map, donde las llaves son los atributos
        ResponseEntity<CustomPageImpl> stringResponseEntity = restTemplate.getForEntity(GET_AUDIT_PATH, CustomPageImpl.class);

        ResponseEntity responseEntity = auditPageConvertor.getPageAudit(stringResponseEntity);

        System.out.println(responseEntity);

        return null;
    }
}
