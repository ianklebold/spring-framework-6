package jedi.followmypath.webapp.controllers;

import jedi.followmypath.webapp.exceptions.NotFoundException;
import jedi.followmypath.webapp.model.dto.CustomerDTO;
import jedi.followmypath.webapp.services.cars.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;
    public static final String CUSTOMER_PATH = "/api/v1/customers";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{uuidCustomer}";

    @GetMapping(value = CUSTOMER_PATH)
    public Page<CustomerDTO> getCustomers(@RequestParam(name = "email", required = false) String email,
                                          @RequestParam(name = "name", required = false) String name,
                                          @RequestParam(name = "surname", required = false) String surname,
                                          @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                          @RequestParam(name = "pageSize",required = false) Integer pageSize){

        return customerService.getCustomers(email,name,surname,pageNumber,pageSize);
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable(value = "uuidCustomer") String uuidCustomer) throws NotFoundException {
        return customerService.getCustomersById(UUID.fromString(uuidCustomer)).orElseThrow(NotFoundException::new);
    }

    @PostMapping(value = CUSTOMER_PATH)
    public ResponseEntity createCustomer(@Validated @RequestBody CustomerDTO customerToCreate){
        CustomerDTO customerCreated = customerService.createCustomer(customerToCreate);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",CUSTOMER_PATH+"/"+ customerCreated.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomer(@Validated @RequestBody CustomerDTO customerDTO,
                                         @PathVariable(value = "uuidCustomer") String uuidCustomer) throws NotFoundException {
        customerService.updateCustomer(customerDTO,UUID.fromString(uuidCustomer)).orElseThrow(NotFoundException::new);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",CUSTOMER_PATH+"/"+ uuidCustomer);
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }


}
