package jedi.followmypath.webapp.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuditClientImplTest {

    @Autowired
    AuditClientImpl auditClient;

    @Test
    void listAudits() {
        auditClient.listAudits();
    }
}