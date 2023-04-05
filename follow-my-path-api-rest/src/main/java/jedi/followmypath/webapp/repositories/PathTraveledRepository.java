package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.entities.PathTraveled;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PathTraveledRepository extends JpaRepository<PathTraveled, UUID> {
    List<PathTraveled> findAllByCar(Car car);
}
