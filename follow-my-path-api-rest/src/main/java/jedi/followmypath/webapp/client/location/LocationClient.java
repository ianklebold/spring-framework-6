package jedi.followmypath.webapp.client.location;

import jedi.followmypath.webapp.model.dto.LocationDTO;

import java.io.IOException;
import java.net.URISyntaxException;

public interface LocationClient {
    public LocationDTO getCarPosition() throws URISyntaxException, IOException;
}
