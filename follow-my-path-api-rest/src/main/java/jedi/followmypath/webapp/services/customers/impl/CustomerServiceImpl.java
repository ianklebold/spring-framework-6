package jedi.followmypath.webapp.services.customers.impl;

import jedi.followmypath.webapp.model.dto.CustomerDTO;
import jedi.followmypath.webapp.services.customers.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    Map<UUID,CustomerDTO> customersMap;

    public CustomerServiceImpl(){
        customersMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(0)
                .name("Eddy")
                .surname("Vedder")
                .country("USA")
                .email("eddy-vedder@gmail.com")
                .birthDate(LocalDateTime.now())
                .createCustomerDate(LocalDateTime.now())
                .updateCustomerDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(0)
                .name("Adrian")
                .surname("Belew")
                .country("USA")
                .email("andy-belew@gmail.com")
                .birthDate(LocalDateTime.now())
                .createCustomerDate(LocalDateTime.now())
                .updateCustomerDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(0)
                .name("Gustavo")
                .surname("Cerati")
                .country("Argentina")
                .email("gus-cerati@gmail.com")
                .birthDate(LocalDateTime.now())
                .createCustomerDate(LocalDateTime.now())
                .updateCustomerDate(LocalDateTime.now())
                .build();


        customersMap.put(customer1.getId(),customer1);
        customersMap.put(customer2.getId(),customer2);
        customersMap.put(customer3.getId(),customer3);
    }


    @Override
    public Page<CustomerDTO> getCustomers(String email, String name, String surname, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(customersMap.values().stream().toList());
    }
    @Override
    public Optional<CustomerDTO> getCustomersById(UUID uuidCustomer) {
        return Optional.of(customersMap.get(uuidCustomer));
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customersMap.put(customerDTO.getId(),customerDTO);
        return customerDTO;
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(CustomerDTO customerDTO, UUID uuid) {
         Optional<CustomerDTO> customerFound = Optional.ofNullable(customersMap.get(uuid));
         if (customerFound.isPresent()){
             customersMap.replace(customerDTO.getId(),customerDTO);
             return Optional.of(customerDTO);
         }
         return Optional.empty();
    }


}
