package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
