package jedi.followmypath.webapp.services.customers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jedi.followmypath.webapp.model.dto.CustomerDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Page<CustomerDTO> getCustomers(String email, String name, String surname, Integer pageNumber, Integer pageSize);
    Optional<CustomerDTO> getCustomersById(UUID uuidCustomer);

    CustomerDTO createCustomer(CustomerDTO customerDTO) throws JsonProcessingException;
    Optional<CustomerDTO> updateCustomer(CustomerDTO customerDTO, UUID uuid) throws JsonProcessingException;
}
