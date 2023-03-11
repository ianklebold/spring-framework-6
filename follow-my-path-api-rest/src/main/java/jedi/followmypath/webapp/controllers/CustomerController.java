package jedi.followmypath.webapp.controllers;

import jedi.followmypath.webapp.model.dto.CustomerDTO;
import jedi.followmypath.webapp.services.cars.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        return customerService.getCars(email,name,surname,pageNumber,pageSize);
    }

}
