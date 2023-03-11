package jedi.followmypath.webapp.repositories;

import jedi.followmypath.webapp.bootstrap.BootstrapData;
import jedi.followmypath.webapp.entities.Customer;
import jedi.followmypath.webapp.services.csv.CustomerCsvServiceV2Impl;
import jedi.followmypath.webapp.services.csv.car.CarCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BootstrapData.class, CarCsvServiceImpl.class, CustomerCsvServiceV2Impl.class})
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Pageable pageable;

    @BeforeEach
    void setUp(){
        this.pageable = PageRequest.of(0,25);
    }

    @Test
    void test_find_all_customers() {

        Page<Customer> customerPage = customerRepository.findAll(this.pageable);

        assertThat(customerPage.getContent()).isNotEmpty();
        assertThat(customerPage.getContent().size()).isGreaterThan(0);
        assertThat(customerPage.getContent().size()).isEqualTo(25);

    }

    @Test
    void test_find_all_customers_by_name_() {

        Page<Customer> customerPage = customerRepository.findAllByNameIsLikeIgnoreCase("fiona",this.pageable);

        assertThat(customerPage.getContent().size()).isGreaterThan(0);

    }

    @Test
    void find_all_by_email_() {

        Page<Customer> customerPage = customerRepository.findAllByEmailIsLikeIgnoreCase("nulla.facilisi@hotmail.ca",this.pageable);

        assertThat(customerPage.getContent().size()).isGreaterThan(0);
    }

    @Test
    void find_all_by_name_is_like_ignore_case_and_email_is_like_ignore_case() {

        Page<Customer> customerPage = customerRepository
                .findAllByNameIsLikeIgnoreCaseAndEmailIsLikeIgnoreCase("fiona","ET@GOOGLE.EDU",this.pageable);

        assertThat(customerPage.getContent().size()).isEqualTo(1);
    }

    @Test
    void empty_when_name_and_email_not_exist() {

        Page<Customer> customerPage = customerRepository
                .findAllByNameIsLikeIgnoreCaseAndEmailIsLikeIgnoreCase("No existo","METNIRA@GOOGLE.EDU",this.pageable);

        assertThat(customerPage.getContent()).isEmpty();
    }

    @Test
    void empty_when_name_not_exist_but_email_exist() {

        Page<Customer> customerPage = customerRepository
                .findAllByNameIsLikeIgnoreCaseAndEmailIsLikeIgnoreCase("No existo","ET@GOOGLE.EDU",this.pageable);

        assertThat(customerPage.getContent()).isEmpty();
    }

    @Test
    void empty_when_email_not_exist_but_name_exist() {

        Page<Customer> customerPage = customerRepository
                .findAllByNameIsLikeIgnoreCaseAndEmailIsLikeIgnoreCase("fiona","METNIRA@GOOGLE.EDU",this.pageable);

        assertThat(customerPage.getContent()).isEmpty();
    }


}