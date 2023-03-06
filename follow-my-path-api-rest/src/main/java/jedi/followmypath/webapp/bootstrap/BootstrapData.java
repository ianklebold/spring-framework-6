package jedi.followmypath.webapp.bootstrap;

import jedi.followmypath.webapp.entities.Car;
import jedi.followmypath.webapp.entities.Customer;
import jedi.followmypath.webapp.repositories.CarRepository;
import jedi.followmypath.webapp.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        //Estos datos se cargan en la BD H2 y en tiempo de ejecucion (Por Scope en POM XML)
        loadCartData();
        loadCustomerData();

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
                    .birthDate(LocalDateTime.now())
                    .surname("Fernandez")
                    .country("Argentina")
                    .createCustomerDate(LocalDateTime.now())
                    .updateCustomerDate(LocalDateTime.now())
                    .build();

            Customer customerCreated2 = Customer.builder()
                    .name("Xerdan")
                    .birthDate(LocalDateTime.now())
                    .surname("Shaqiri")
                    .country("Suiza")
                    .createCustomerDate(LocalDateTime.now())
                    .updateCustomerDate(LocalDateTime.now())
                    .build();

            Customer customerCreated3 = Customer.builder()
                    .name("Eduart")
                    .birthDate(LocalDateTime.now())
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
