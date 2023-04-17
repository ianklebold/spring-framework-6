package jedi.followmypath.webapp.client.audit;

import com.auditsystem.auditsystemcommons.entities.Audit;
import com.auditsystem.auditsystemcommons.entities.enums.AuditType;
import com.auditsystem.auditsystemcommons.entities.enums.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jedi.followmypath.webapp.config.RestTemplateBuilderConfig;
import jedi.followmypath.webapp.model.restauditdto.AuditPageConvertor;
import jedi.followmypath.webapp.model.restauditdto.CustomPageImpl;
import jedi.followmypath.webapp.model.restauditdto.impl.AuditPageConvertorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
@Import(RestTemplateBuilderConfig.class)
class AuditClientMockTest {

    static final String URL = "http://localhost:8080";

    AuditClientImpl auditClient;

    MockRestServiceServer server;

    AuditPageConvertorImpl auditPageConvertor;
    @Autowired
    RestTemplateBuilder restTemplateBuilderConfigured;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    RestTemplateBuilder mockRestTemplateBuider = new RestTemplateBuilder(new MockServerRestTemplateCustomizer());


    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = restTemplateBuilderConfigured.build();
        server = MockRestServiceServer.bindTo(restTemplate).build();
        auditPageConvertor = new AuditPageConvertorImpl();

        when(mockRestTemplateBuider.build()).thenReturn(restTemplate);
        auditClient = new AuditClientImpl(mockRestTemplateBuider,auditPageConvertor);
    }

    @Test
    void listAudits() throws JsonProcessingException {
        var payload = objectMapper.writeValueAsString(getPage());

        server.expect(method(HttpMethod.GET))
                .andExpect(requestTo(URL+ AuditClientImpl.AUDIT_PATH))
                .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

        Page<Audit> audits = auditClient.listAudits(null,null);
        assertThat(audits.getContent().size()).isGreaterThan(0);
    }

    Audit getAudit(){
        return Audit.builder()
                .auditType(AuditType.BASIC)
                .log("Log string")
                .description("Description test")
                .level(Level.INFO)
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .webservice("/api/v1/audit")
                .build();
    }
    private Page<Audit> getPage() {
        return new PageImpl<>(Arrays.asList(getAudit()));

    }

    @Test
    void testListAudits() {
    }

    @Test
    void createAudit() {
    }
}