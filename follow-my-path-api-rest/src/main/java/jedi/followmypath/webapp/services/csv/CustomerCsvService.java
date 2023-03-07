package jedi.followmypath.webapp.services.csv;

import jedi.followmypath.webapp.model.CustomerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface CustomerCsvService {
    List<CustomerCSVRecord> convertCSV(File csvFile) throws FileNotFoundException;
}
