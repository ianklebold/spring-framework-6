package jedi.followmypath.webapp.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCSVRecord {
    @CsvBindByName(column = "row")
    private Integer row;
    @CsvBindByName(column = "id")
    private String id;
    @CsvBindByName(column = "version")
    private Integer version;
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
