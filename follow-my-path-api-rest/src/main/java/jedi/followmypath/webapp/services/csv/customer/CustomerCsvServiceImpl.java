package jedi.followmypath.webapp.services.csv.customer;

import com.opencsv.bean.CsvToBeanBuilder;
import jedi.followmypath.webapp.model.csv.CustomerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
@Service
public class CustomerCsvServiceImpl implements CustomerCsvService {
    @Override
    public List<CustomerCSVRecord> convertCSV(File csvFile) throws FileNotFoundException {
        List<CustomerCSVRecord> customerCSVRecords = new CsvToBeanBuilder<CustomerCSVRecord>(new FileReader(csvFile))
                .withType(CustomerCSVRecord.class)
                .build()
                .parse();
        return customerCSVRecords;
    }
}
