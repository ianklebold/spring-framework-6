package jedi.followmypath.webapp.bootstrap;

import jedi.followmypath.webapp.model.csv.CarCSVRecord;
import jedi.followmypath.webapp.model.csv.CustomerCSVRecord;
import jedi.followmypath.webapp.repositories.CarRepository;
import jedi.followmypath.webapp.repositories.CustomerRepository;
import jedi.followmypath.webapp.services.csv.CsvService;
import jedi.followmypath.webapp.services.csv.customer.CustomerCsvService;
import jedi.followmypath.webapp.services.csv.customer.CustomerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Import porque, al tener solamente DATAJPATEST nos limitamos a Repositories, a servicios de datos.
 * Como necesitamos un servicio usamos Import pero a su vez hacemos un Autowired y se lo mandamos a BootstrapData
 * el cual lo requiere
 */
@DataJpaTest
@Import(CustomerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CsvService<CustomerCSVRecord> customerCSVRecordCsvService;
    @Autowired
    CsvService<CarCSVRecord> carCSVRecordCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp(){
        //Como lo necesitamos para cada uno de los test, no lo inyectamos con autowired sino manualmente
        bootstrapData = new BootstrapData(carRepository,customerRepository,customerCSVRecordCsvService,carCSVRecordCsvService);
    }

    @Test
    void load_test_data() throws Exception {
        bootstrapData.run(null);

        assertThat(carRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(103);
    }

}