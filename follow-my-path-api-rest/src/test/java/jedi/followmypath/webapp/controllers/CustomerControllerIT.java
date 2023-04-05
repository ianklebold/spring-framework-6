package jedi.followmypath.webapp.controllers;

import jedi.followmypath.webapp.services.customers.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerController customerController;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        //Inyectamos el Spring application context dentro.
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Nested
    @DisplayName("GET TESTS")
    class test_get_method_for_customers{
        @Autowired
        WebApplicationContext wac;

        MockMvc mockMvc;

        @BeforeEach
        void setUp(){
            //Inyectamos el Spring application context dentro.
            mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }
        @Test
        void test_get_all_customers() throws Exception {
            mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.size()",is(103)));

        }

        @Test
        void test_get_all_customers_by_email() throws Exception {
            mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                            .queryParam("email","in.nec@google.ca")
                            .queryParam("pageNumber","1")
                            .queryParam("pageSize","25")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.size()",is(1)));
        }
    }

    @Nested
    @DisplayName("POST TESTS")
    class test_post_method_for_customers{
        @Autowired
        WebApplicationContext wac;

        MockMvc mockMvc;

        @BeforeEach
        void setUp(){
            //Inyectamos el Spring application context dentro.
            mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }
    }


}