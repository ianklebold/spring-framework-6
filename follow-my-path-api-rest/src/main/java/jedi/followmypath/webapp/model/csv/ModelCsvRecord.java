package jedi.followmypath.webapp.model.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ModelCsvRecord {
    @CsvBindByName(column = "row")
    private Integer row;
    @CsvBindByName(column = "id")
    private String id;
    @CsvBindByName(column = "version")
    private Integer version;
}
