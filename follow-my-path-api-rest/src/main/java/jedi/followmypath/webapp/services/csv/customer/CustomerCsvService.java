package jedi.followmypath.webapp.services.csv.customer;

import jedi.followmypath.webapp.model.csv.CustomerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface CustomerCsvService {
    List<CustomerCSVRecord> convertCSV(File csvFile) throws FileNotFoundException;
}
