package jedi.followmypath.webapp.repositories;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jedi.followmypath.webapp.bootstrap.BootstrapData;
import jedi.followmypath.webapp.entities.Customer;
import jedi.followmypath.webapp.services.csv.CustomerCsvServiceV2Impl;
import jedi.followmypath.webapp.services.csv.car.CarCsvServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BootstrapData.class, CarCsvServiceImpl.class, CustomerCsvServiceV2Impl.class})
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Nested
    @DisplayName("GET METHOD TEST")
    class test_get_customers{
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

        @Test
        void test_get_customer_by_id(){
            Page<Customer> customerPage = customerRepository.findAll(this.pageable);

            UUID uuidCustomer = customerPage.getContent().stream()
                    .map(Customer::getId)
                    .toList()
                    .get(0);

            Optional<Customer> customer = customerRepository.findById(uuidCustomer);

            assertThat(customer).isPresent();
            assertThat(customer.get().getId()).isEqualTo(uuidCustomer);
        }

        @Test
        void test_get_empty_when_send_wrong_id(){
            Optional<Customer> customer = customerRepository.findById(UUID.randomUUID());

            assertThat(customer).isEmpty();
        }
    }

    @Nested
    @DisplayName("POST METHOD TEST")
    class test_post_customers{
        Customer customerCorrect;
        Customer customerInCorrect;
        @BeforeEach
        void setUp(){
            this.customerCorrect = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Test name customer")
                    .surname("Test surname customer")
                    .country("España")
                    .email("test@gmail.com")
                    .birthDate(LocalDateTime.of(1999,8,10,0,0,0))
                    .createCustomerDate(LocalDateTime.now())
                    .updateCustomerDate(LocalDateTime.now())
                    .version(1)
                    .build();

            this.customerInCorrect = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Test")
                    .surname("Test surname customer")
                    .country("España")
                    .email("test342342332423432423com343242342342342342342342342342332423432423com343242342342342342342342342342332423432423343242342342342342342342342342332423432423com343242342342342342342342342342332423432423com343242342342342342342342@gmail.com")
                    .birthDate(LocalDateTime.of(1999,8,10,0,0,0))
                    .createCustomerDate(LocalDateTime.now())
                    .updateCustomerDate(LocalDateTime.now())
                    .version(1)
                    .build();

        }

        @Rollback //Estas dos anotaciones son necesrias, para que se restablezca la BD para los demas tests
        @Transactional
        @Test
        void test_create_customer_with_correct_data(){

            long countCustomerInDb = customerRepository.count();

            Customer customerSaved = customerRepository.save(this.customerCorrect);

            assertThat(customerSaved).isNotNull();
            assertThat(customerSaved.getEmail()).isNotNull();
            assertThat(customerSaved.getEmail()).isNotBlank();

            assertThat(customerSaved.getName()).isEqualTo("Test name customer");
            assertThat(customerSaved.getSurname()).isEqualTo("Test surname customer");
            assertThat(customerSaved.getCreateCustomerDate()).isNotNull();
            assertThat(customerSaved.getUpdateCustomerDate()).isNotNull();
            assertThat(customerSaved.getBirthDate()).isEqualTo(LocalDateTime.of(1999,8,10,0,0,0));


            assertThat(countCustomerInDb).isLessThan(customerRepository.count());
        }

        @Rollback //Estas dos anotaciones son necesrias, para que se restablezca la BD para los demas tests
        @Transactional
        @Test
        void test_not_create_customer_with_in_correct_data(){

            long countCustomerInDb = customerRepository.count();

            Assertions.assertThrows(ConstraintViolationException.class,() -> customerRepository.save(customerInCorrect));


            assertThat(countCustomerInDb).isEqualTo(customerRepository.count());
        }

    }


}