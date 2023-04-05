package jedi.followmypath.webapp.services.customers.impl;

import jedi.followmypath.webapp.entities.Customer;
import jedi.followmypath.webapp.mappers.CustomerMapper;
import jedi.followmypath.webapp.model.dto.CustomerDTO;
import jedi.followmypath.webapp.repositories.CustomerRepository;
import jedi.followmypath.webapp.services.customers.CustomerService;
import jedi.followmypath.webapp.services.pagesrequest.PageRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Primary
@Service
public class CustomerServiceJPA implements CustomerService {
    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    private final PageRequestService pageRequestService;

    private static final String PROPERTY_SORT = "email";


    @Override
    public Page<CustomerDTO> getCustomers(String email, String name, String surname, Integer pageNumber, Integer pageSize) {

        Page<Customer> listCustomers;

        PageRequest pageRequest = pageRequestService.buildPageRequest(pageNumber,pageSize,PROPERTY_SORT);

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

    @Override
    public Optional<CustomerDTO> getCustomersById(UUID uuidCustomer) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(uuidCustomer).orElse(null)));
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO));
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(CustomerDTO customerDTO, UUID uuid) {

        Optional<Customer> customer = customerRepository.findById(uuid);

        if(customer.isPresent()){
            Customer customerUpdated = customer.get();

            customerUpdated.setName(customerDTO.getName());
            customerUpdated.setEmail(customerDTO.getEmail());
            customerUpdated.setCountry(customerDTO.getCountry());
            customerUpdated.setBirthDate(customerDTO.getBirthDate());
            customerUpdated.setSurname(customerDTO.getSurname());
            customerUpdated.setUpdateCustomerDate(LocalDateTime.now());

            customerRepository.save(customerUpdated);
            return Optional.of(customerMapper.customerToCustomerDto(customerUpdated));
        }

        return Optional.empty();
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
