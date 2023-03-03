package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID>{
}
