package jedi.followmypath.webapp.controllers;


import jedi.followmypath.webapp.config.security.SpringSecurityConfig;
import jedi.followmypath.webapp.model.dto.CustomerDTO;
import jedi.followmypath.webapp.services.customers.CustomerService;
import jedi.followmypath.webapp.services.customers.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(CustomerController.class)
@Import(SpringSecurityConfig.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    CustomerServiceImpl customerServiceImpl;

    @Value("${spring.security.user.name}")
    String username;

    @Value("${spring.security.user.password}")
    String password;

    @BeforeEach
    void setUp(){
        this.customerServiceImpl = new CustomerServiceImpl();
    }


    @Test
    void get_all_customers() throws Exception {

        Page<CustomerDTO> customer = this.customerServiceImpl.getCustomers(null,null,null,null, null);
        //Given
        when(customerService.getCustomers(any(),any(),any(),any(),any())).thenReturn(customer);

        //When
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .with(httpBasic(username,password))
                .accept(MediaType.APPLICATION_JSON))
        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()",is(3)));

    }
    @Test
    void get_customer_by_id() throws Exception {

        Page<CustomerDTO> customer = this.customerServiceImpl.getCustomers(null,null,null,null, null);
        //Given
        when(customerService.getCustomersById(any())).thenReturn(Optional.ofNullable(customer.getContent().get(0)));

        //When
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,customer.getContent().get(0).getId())
                        .with(httpBasic(username,password))
                        .accept(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name",is(customer.getContent().get(0).getName())))
                .andExpect(jsonPath("$.surname",is(customer.getContent().get(0).getSurname())))
                .andExpect(jsonPath("$.email",is(customer.getContent().get(0).getEmail())));
    }

    @Test
    void get_exception_when_send_wrong_id() throws Exception {
        //Given
        when(customerService.getCustomersById(any(UUID.class))).thenReturn(Optional.empty());

        //When
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,UUID.randomUUID())
                        .with(httpBasic(username,password))
                        .accept(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isNotFound());
    }

}