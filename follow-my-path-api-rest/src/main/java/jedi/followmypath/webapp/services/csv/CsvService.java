package jedi.followmypath.webapp.services.csv;

import jedi.followmypath.webapp.model.csv.ModelCsvRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface CsvService<M extends ModelCsvRecord> {
    List<M> convertCSV(File csvFile,Class<M> type) throws FileNotFoundException;
}
