package jedi.followmypath.webapp.services.csv;

import jedi.followmypath.webapp.model.csv.CarCSVRecord;
import jedi.followmypath.webapp.model.csv.CustomerCSVRecord;
import jedi.followmypath.webapp.services.csv.car.CarCsvServiceImpl;
import jedi.followmypath.webapp.services.csv.customer.CustomerCsvService;
import jedi.followmypath.webapp.services.csv.customer.CustomerCsvServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CarCsvServiceImplTest {

    CustomerCsvService customerCsvService= new CustomerCsvServiceImpl();

    @Autowired
    CsvService<CustomerCSVRecord> customerCsvServiceV2;

    @Autowired
    CsvService<CarCSVRecord> carCsvServiceV2;

    @Test
    void convert_customer_data_csv_to_objects() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/customer-data.csv");

        //List<CustomerCSVRecord> records = customerCsvService.convertCSV(file);
        List<CustomerCSVRecord> records1 = customerCsvServiceV2.convertCSV(file,CustomerCSVRecord.class);

        //assertThat(records.size()).isGreaterThan(0);
        assertThat(records1.size()).isEqualTo(100);

    }
    @Test
    void convert_car_data_csv_to_objects() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/car-data.csv");

        List<CarCSVRecord> records = carCsvServiceV2.convertCSV(file,CarCSVRecord.class);

        assertThat(records.size()).isEqualTo(100);
    }
}