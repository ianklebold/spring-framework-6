package jedi.followmypath.webapp.services.csv.car;

import jedi.followmypath.webapp.model.csv.CarCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface CarCsvService {
    List<CarCSVRecord> convertCSV(File csvFile) throws FileNotFoundException;
}
