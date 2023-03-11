package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID>{

    Page<Car> findAllByModelIsLikeIgnoreCase(String model, Pageable pageable);
    Page<Car> findAllByMakeIsLikeIgnoreCase(String make, Pageable pageable);

    Page<Car> findAllByMakeIsLikeIgnoreCaseAndModelIsLikeIgnoreCase(String make,String model, Pageable pageable);
}
