package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Page<Customer> findAllByNameIsLikeIgnoreCase(String name, Pageable pageable);
    Page<Customer> findAllByEmailIsLikeIgnoreCase(String email, Pageable pageable);
    Page<Customer> findAllByNameIsLikeIgnoreCaseAndEmailIsLikeIgnoreCase(String name, String email, Pageable pageable);

}
