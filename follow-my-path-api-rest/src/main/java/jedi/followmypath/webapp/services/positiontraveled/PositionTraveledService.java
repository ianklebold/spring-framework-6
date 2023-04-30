package jedi.followmypath.webapp.services.positiontraveled;

import jedi.followmypath.webapp.entities.PositionTraveled;

import java.io.IOException;
import java.net.URISyntaxException;

public interface PositionTraveledService {
    PositionTraveled createPosition() throws URISyntaxException, IOException;
}
