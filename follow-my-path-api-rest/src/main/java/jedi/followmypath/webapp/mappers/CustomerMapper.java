package jedi.followmypath.webapp.mappers;

import jedi.followmypath.webapp.entities.Customer;
import jedi.followmypath.webapp.model.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);
    CustomerDTO customerToCustomerDto(Customer customer);
}
