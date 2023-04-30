package jedi.followmypath.webapp.client.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;


@SpringBootTest
class LocationClientImplTest {

    @Autowired
    LocationClientImpl locationClient;

    @Test
    void getCarPosition() throws URISyntaxException, IOException {
        locationClient.getCarPosition();
    }
}