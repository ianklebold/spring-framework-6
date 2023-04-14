package jedi.followmypath.webapp.client;

import com.auditsystem.auditsystemcommons.entities.Audit;
import com.auditsystem.auditsystemcommons.entities.enums.AuditType;
import com.auditsystem.auditsystemcommons.entities.enums.Level;
import jakarta.transaction.Transactional;
import jedi.followmypath.webapp.client.audit.AuditClientImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuditClientImplTest {

    @Autowired
    AuditClientImpl auditClient;

    @Test
    void listAudits() {
        auditClient.listAudits(1,20);
    }

    @Test
    void listAuditsWithCreationDateBeforeToDateParam() {
        LocalDateTime localDateTime = LocalDateTime.of(2023,4,3,0,0,0,0);
        assertThat(auditClient.listAudits(localDateTime,1,20).getContent()).isNotEmpty();
    }

    @Test
    void emptyListAuditsWithCreationDateBeforeToDateParam() {
        LocalDateTime localDateTime = LocalDateTime.of(2023,6,3,0,0,0,0);
        assertThat(auditClient.listAudits(localDateTime,1,20).getContent()).isEmpty();
    }

    @Rollback
    @Transactional
    @Test
    void testCreateAudit(){
        Audit newAudit = Audit.builder()
                .auditType(AuditType.BASIC)
                .log("Log string")
                .description("Description test")
                .level(Level.INFO)
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .webservice("/api/v1/audit")
                .build();

        Audit auditSaved = auditClient.createAudit(newAudit);

        assertNotNull(auditSaved);
        assertThat(auditSaved.getAuditType()).isNotNull();
        assertThat(auditSaved.getAuditType()).isEqualTo(newAudit.getAuditType());

        assertThat(auditSaved.getLog()).isNotNull();
        assertThat(auditSaved.getLog()).isEqualTo(newAudit.getLog());

        assertThat(auditSaved.getDescription()).isNotNull();
        assertThat(auditSaved.getDescription()).isEqualTo(newAudit.getDescription());

        assertThat(auditSaved.getLevel()).isNotNull();
        assertThat(auditSaved.getLevel()).isEqualTo(newAudit.getLevel());

        assertThat(auditSaved.getWebservice()).isNotNull();
        assertThat(auditSaved.getWebservice()).isEqualTo(newAudit.getWebservice());
    }
}