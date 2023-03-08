package jedi.followmypath.webapp.model.csv;


import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarCSVRecord extends ModelCsvRecord {
    @CsvBindByName(column = "model")
    private String model;
    @CsvBindByName(column = "yearCar")
    private int yearCar;
    @CsvBindByName(column = "patentCar")
    private String patentCar;
    @CsvBindByName(column = "size")
    private String size;
    @CsvBindByName(column = "make")
    private String make;
    @CsvBindByName(column = "fuelType")
    private String fuelType;
}
