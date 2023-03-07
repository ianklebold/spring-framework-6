package jedi.followmypath.webapp.services.csv;

import jedi.followmypath.webapp.model.CustomerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CarCsvServiceImplTest {

    CustomerCsvService customerCsvService= new CustomerCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/customer-data.csv");

        List<CustomerCSVRecord> records = customerCsvService.convertCSV(file);

        assertThat(records.size()).isGreaterThan(0);
    }
}