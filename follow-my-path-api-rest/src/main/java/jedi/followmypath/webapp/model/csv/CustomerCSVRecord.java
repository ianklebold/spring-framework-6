package jedi.followmypath.webapp.model.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCSVRecord extends ModelCsvRecord {
    @CsvBindByName(column = "email")
    private String email;
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "surname")
    private String surname;
    @CsvBindByName(column = "birthDate")
    private String birthDate;
    @CsvBindByName(column = "country")
    private String country;
}
