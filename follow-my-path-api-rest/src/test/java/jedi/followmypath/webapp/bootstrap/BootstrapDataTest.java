package jedi.followmypath.webapp.bootstrap;

import jedi.followmypath.webapp.repositories.CarRepository;
import jedi.followmypath.webapp.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class BootstrapDataTest {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp(){
        //Como lo necesitamos para cada uno de los test, no lo inyectamos con autowired sino manualmente
        bootstrapData = new BootstrapData(carRepository,customerRepository);
    }

    @Test
    void load_test_data() throws Exception {
        bootstrapData.run(null);

        assertThat(carRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }

}