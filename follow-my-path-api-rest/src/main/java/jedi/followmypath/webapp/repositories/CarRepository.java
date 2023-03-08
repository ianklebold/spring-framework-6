package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID>{

    List<Car> findAllByModelIsLikeIgnoreCase(String model);
    List<Car> findAllByMakeIsLikeIgnoreCase(String make);

    List<Car> findAllByMakeIsLikeIgnoreCaseAndModelIsLikeIgnoreCase(String make,String model);
}
