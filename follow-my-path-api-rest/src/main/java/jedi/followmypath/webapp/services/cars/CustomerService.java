package jedi.followmypath.webapp.services.cars;

import jedi.followmypath.webapp.model.dto.CustomerDTO;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Page<CustomerDTO> getCustomers(String email, String name, String surname, Integer pageNumber, Integer pageSize);
}
