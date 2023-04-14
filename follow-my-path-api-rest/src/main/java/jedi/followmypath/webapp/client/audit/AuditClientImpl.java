package jedi.followmypath.webapp.client.audit;

import com.auditsystem.auditsystemcommons.entities.Audit;
import jedi.followmypath.webapp.model.restauditdto.AuditPageConvertor;
import jedi.followmypath.webapp.model.restauditdto.CustomPageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuditClientImpl implements AuditClient {

    private final RestTemplateBuilder restTemplateBuilder;

    private final AuditPageConvertor auditPageConvertor;

    private static final String AUDIT_PATH = "/api/v1/audit";


    //Para queries con API DATA REST va el /search/nombreDelQueryMethod
    private static final String GET_AUDIT_SEARCH_CREATION_DATE_AFTER = AUDIT_PATH + "/search/findAllByCreationDateIsAfter";


    @Override
    public Page<Audit> listAudits(Integer pageNumber, Integer pageSize) {
        // Con String simplemente convierte la request en String
        // ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity(GET_AUDIT_PATH, String.class);

        //Con map, convierte cada objeto del listado en un map, donde las llaves son los atributos
        //No ponemos el PATH BASE porque esto ya nos lo da RestTemplateBuilderConfig

        //URI COMPONENT ES UN CONSTRUCTOR DE URIS, le pasas el parametro y luego si tenemos queries lo pasamos en
        // el metodo .queryParam donde va exactamente el nombre del parametro
        RestTemplate restTemplate = restTemplateBuilder.build();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(AUDIT_PATH);

        if(pageNumber != null){
            uriComponentsBuilder.queryParam("pageNumber",pageNumber);
        }

        if(pageSize != null){
            uriComponentsBuilder.queryParam("pageSize",pageSize);
        }

        ResponseEntity<CustomPageImpl> stringResponseEntity = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), CustomPageImpl.class);
        ResponseEntity responseEntity = auditPageConvertor.getPageAudit(stringResponseEntity);

        return new PageImpl<> ((List<Audit>) responseEntity.getBody());
    }

    @Override
    public Page<Audit> listAudits(LocalDateTime localDateTime,Integer pageNumber, Integer pageSize) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        UriComponentsBuilder uriComponentsBuilder;

        uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_AUDIT_SEARCH_CREATION_DATE_AFTER);
        uriComponentsBuilder.queryParam("localDateTime",localDateTime);

        if(pageNumber != null){
            uriComponentsBuilder.queryParam("pageNumber",pageNumber);
        }

        if(pageSize != null){
            uriComponentsBuilder.queryParam("pageSize",pageSize);
        }

        ResponseEntity<CustomPageImpl> stringResponseEntity = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), CustomPageImpl.class);

        ResponseEntity responseEntity = auditPageConvertor.getPageAudit(stringResponseEntity);

        return new PageImpl<> ((List<Audit>) responseEntity.getBody());
    }

    @Override
    public Audit createAudit(Audit newAudit) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //Damos la URL, El Objeto a crear y contra que clase vamos a serializar
        ResponseEntity<Audit> response = restTemplate.postForEntity(AUDIT_PATH,newAudit,Audit.class);

        return response.getBody();
    }
}
