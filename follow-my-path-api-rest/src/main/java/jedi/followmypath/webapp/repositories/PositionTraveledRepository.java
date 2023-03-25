package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.PositionTraveled;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PositionTraveledRepository extends JpaRepository<PositionTraveled, UUID> {
}
