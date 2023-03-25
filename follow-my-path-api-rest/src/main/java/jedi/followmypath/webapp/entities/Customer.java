package jedi.followmypath.webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    public Customer(UUID id, Integer version, String email, String name, String surname, LocalDateTime birthDate, String country, List<Car> cars, LocalDateTime createCustomerDate, LocalDateTime updateCustomerDate) {
        this.id = id;
        this.version = version;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.country = country;
        this.setCars(cars);
        this.createCustomerDate = createCustomerDate;
        this.updateCustomerDate = updateCustomerDate;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR) //Aqui le decimos a Hibernate que el UUID lo mandemos como CHAR y no como Binary
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @NotBlank
    @Size(max = 70)
    @Column(length = 70, unique = true)
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 40)
    @Column(length = 40)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 40)
    @Column(length = 40)
    private String surname;

    @Past
    @NotNull
    private LocalDateTime birthDate;

    @NotNull
    @NotBlank
    @Size(max = 40)
    @Column(length = 40)
    private String country;


    @Builder.Default
    @OneToMany(mappedBy = "customer",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Car> cars = new ArrayList<>();

    public void setCars(List<Car> cars) {
        if(this.cars == null || this.cars.isEmpty()){
            this.cars = cars;
        }else {
            this.cars.addAll(cars);
        }
        this.cars.forEach(car -> car.setCustomer(this));
    }

    @CreationTimestamp
    private LocalDateTime createCustomerDate;
    @UpdateTimestamp
    private LocalDateTime updateCustomerDate;



}
