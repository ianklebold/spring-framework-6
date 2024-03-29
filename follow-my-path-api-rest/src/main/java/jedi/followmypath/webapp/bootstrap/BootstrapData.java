package jedi.followmypath.webapp.bootstrap;

import jakarta.transaction.Transactional;
import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.entities.Customer;
import jedi.followmypath.webapp.model.csv.CarCSVRecord;
import jedi.followmypath.webapp.model.csv.CustomerCSVRecord;
import jedi.followmypath.webapp.repositories.CarRepository;
import jedi.followmypath.webapp.repositories.CustomerRepository;
import jedi.followmypath.webapp.services.csv.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final CsvService<CustomerCSVRecord> customerCsvService;
    private final CsvService<CarCSVRecord> carCsvService;

    @Override
    public void run(String... args) throws Exception {
        //Estos datos se cargan en la BD H2 y en tiempo de ejecucion (Por Scope en POM XML)
        loadCartData();
        loadCustomerData();
        loadCustomerDataFromCSVFile();
        loadCarDataFromCSVFile();
    }

    @Transactional
    private void loadCarDataFromCSVFile() throws FileNotFoundException {
        if (carRepository.count() < 100) {
            File file = ResourceUtils.getFile("classpath:csvdata/car-data.csv");
            List<CarCSVRecord> recordList = carCsvService.convertCSV(file,CarCSVRecord.class);
            if (!recordList.isEmpty()) {
                recordList.forEach(carCSVRecord ->
                        carRepository.save(
                                Car.builder()
                                        .id(UUID.fromString(carCSVRecord.getId()))
                                        .version(carCSVRecord.getVersion())
                                        .model(carCSVRecord.getModel())
                                        .yearCar(carCSVRecord.getYearCar())
                                        .patentCar(carCSVRecord.getPatentCar())
                                        .size(carCSVRecord.getSize())
                                        .make(carCSVRecord.getMake())
                                        .fuelType(carCSVRecord.getMake())
                                        .build()
                        )
                );
            }
        }
    }

    @Transactional //Si tdo lo de aqui no se carga correctamente hace un Rollback
    private void loadCustomerDataFromCSVFile() throws FileNotFoundException {
        System.out.println("CARGANDO CUTOMER");
        if (customerRepository.count() < 100){
            File file = ResourceUtils.getFile("classpath:csvdata/customer-data.csv");
            List<CustomerCSVRecord> recordList = customerCsvService.convertCSV(file,CustomerCSVRecord.class);
            if(!recordList.isEmpty()){
                recordList.forEach(customerCSVRecord -> {

                    String[] date = customerCSVRecord.getBirthDate().split("-");

                    customerRepository.save(Customer.builder()
                            .id(UUID.fromString(customerCSVRecord.getId()))
                            .email(customerCSVRecord.getEmail())
                            .country(customerCSVRecord.getCountry())
                            .name(customerCSVRecord.getName())
                            .surname(customerCSVRecord.getSurname())
                            .version(customerCSVRecord.getVersion())
                            .birthDate(LocalDateTime.of(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]),0,0,0,0))
                            .build());
                });
            }
        }
    }

    private void loadCartData(){

        if (carRepository.count() == 0){
            Car carCreated = Car.builder()
                    .patentCar("1ASZW231")
                    .size("MID")
                    .make("Renault")
                    .model("Sandero Stepway 1.6")
                    .yearCar(2013)
                    .fuelType("Gas oil")
                    .createCarDate(LocalDateTime.now())
                    .updateCarDate(LocalDateTime.now())
                    .build();

            Car carCreated2 = Car.builder()
                    .patentCar("12CXW242")
                    .size("MID")
                    .make("TOYOTA")
                    .model("RAV4")
                    .yearCar(2022)
                    .fuelType("Gas oil")
                    .createCarDate(LocalDateTime.now())
                    .updateCarDate(LocalDateTime.now())
                    .build();

            Car carCreated3 = Car.builder()
                    .patentCar("123KPO213")
                    .size("SMALL")
                    .make("Peugeot")
                    .model("208 EV")
                    .yearCar(2023)
                    .fuelType("ELECTRIC")
                    .createCarDate(LocalDateTime.now())
                    .updateCarDate(LocalDateTime.now())
                    .build();

            carRepository.save(carCreated);
            carRepository.save(carCreated2);
            carRepository.save(carCreated3);
        }
    }

    private void loadCustomerData(){

        if (customerRepository.count() == 0){
            Customer customerCreated = Customer.builder()
                    .name("Ian")
                    .email("IANDEV2022@gmail.com")
                    .birthDate(LocalDateTime.of(1999,1,20,0,0))
                    .surname("Fernandez")
                    .country("Argentina")
                    .createCustomerDate(LocalDateTime.now())
                    .updateCustomerDate(LocalDateTime.now())
                    .build();

            Customer customerCreated2 = Customer.builder()
                    .name("Xerdan")
                    .email("Xerdan@gmail.com")
                    .birthDate(LocalDateTime.of(1990,1,20,0,0))
                    .surname("Shaqiri")
                    .country("Suiza")
                    .createCustomerDate(LocalDateTime.now())
                    .updateCustomerDate(LocalDateTime.now())
                    .build();

            Customer customerCreated3 = Customer.builder()
                    .name("Eduart")
                    .email("EDU@gmail.com")
                    .birthDate(LocalDateTime.of(1982,1,20,0,0))
                    .surname("Hudson")
                    .country("Estados Unidos")
                    .createCustomerDate(LocalDateTime.now())
                    .updateCustomerDate(LocalDateTime.now())
                    .build();

            customerRepository.save(customerCreated);
            customerRepository.save(customerCreated2);
            customerRepository.save(customerCreated3);
        }
    }

}
