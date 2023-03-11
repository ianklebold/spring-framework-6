package jedi.followmypath.webapp.services.cars.impl;

import jedi.followmypath.webapp.entities.Customer;
import jedi.followmypath.webapp.mappers.CustomerMapper;
import jedi.followmypath.webapp.model.dto.CustomerDTO;
import jedi.followmypath.webapp.repositories.CustomerRepository;
import jedi.followmypath.webapp.services.cars.CustomerService;
import jedi.followmypath.webapp.services.cars.PageRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerServiceJPA implements CustomerService {
    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    private final PageRequestService pageRequestService;

    @Override
    public Page<CustomerDTO> getCustomers(String email, String name, String surname, Integer pageNumber, Integer pageSize) {

        Page<Customer> listCustomers;

        PageRequest pageRequest = pageRequestService.buildPageRequest(pageNumber,pageSize);

        if(email != null && name == null){
            listCustomers = getCustomersByEmail(email,pageRequest);
        } else if (email == null && name != null) {
            listCustomers = getCustomersByName(name,pageRequest);
        } else if (email != null && name != null) {
            listCustomers = getCustomersByNameAndEmail(name,email,pageRequest);
        }else {
            listCustomers = new PageImpl<>(customerRepository.findAll());
        }

        return listCustomers.map(customerMapper::customerToCustomerDto);
    }

    private Page<Customer> getCustomersByEmail(String email, Pageable pageable){
        return customerRepository.findAllByEmailIsLikeIgnoreCase("%"+email+"%",pageable);
    }

    private Page<Customer> getCustomersByName(String name, Pageable pageable){
        return customerRepository.findAllByNameIsLikeIgnoreCase("%"+name+"%",pageable);
    }

    private Page<Customer> getCustomersByNameAndEmail(String name,String email, Pageable pageable){
        return customerRepository.findAllByNameIsLikeIgnoreCaseAndEmailIsLikeIgnoreCase("%"+name+"%","%"+email+"%",pageable);
    }



}
