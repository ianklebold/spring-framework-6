package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.entities.PathTraveled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PathTraveledPageRepository extends JpaRepository<PathTraveled, UUID> {
    Page<PathTraveled> findAllByCar(Car car, Pageable pageable);
}
