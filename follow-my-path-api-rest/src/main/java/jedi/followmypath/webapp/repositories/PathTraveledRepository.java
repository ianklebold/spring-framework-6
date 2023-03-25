package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.PathTraveled;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PathTraveledRepository extends JpaRepository<PathTraveled, UUID> {
}
