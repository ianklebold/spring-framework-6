package jedi.followmypath.webapp.controllers;


import jedi.followmypath.webapp.model.dto.CustomerDTO;
import jedi.followmypath.webapp.services.cars.CustomerService;
import jedi.followmypath.webapp.services.cars.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    CustomerServiceImpl customerServiceImpl;

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
                .accept(MediaType.APPLICATION_JSON))
        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()",is(3)));

    }
}