package jedi.followmypath.webapp.services.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import jedi.followmypath.webapp.model.csv.ModelCsvRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public abstract class AbstractCsvService<M extends ModelCsvRecord>  implements CsvService<M> {

    @Override
    public List<M> convertCSV(File csvFile,Class<M> type) throws FileNotFoundException{
        return new CsvToBeanBuilder<M>(new FileReader(csvFile))
                .withType(type)
                .build()
                .parse();
    }


}
