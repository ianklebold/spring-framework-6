package jedi.followmypath.webapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PathTraveled {

    //Cuando usamos Builder estamos invocando este metodo, por lo que no podemos usar ALLARGSCONSTRUCTOR
    //Porque necesitamos que utilizar el .setCar custom que hemos creado
    public PathTraveled(UUID id, int version, Timestamp createdDate, Timestamp lastModifiedDate, Car car, Set<PositionTraveled> positions) {
        this.id = id;
        this.version = version;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.setCar(car);
        this.setPositions(positions);
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID id;

    @Version
    private int version;

    //Timestamp es especifico de hibernate el cual se usa cuando la fecha es creada por el motor de BD
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @ManyToOne
    private Car car;

    public void setCar(Car car) {
        //Esto automaticamente pisa al set de Lombok
        this.car = car;
        car.getPaths().add(this);
    }

    //Persist dice, si alguna posicion que seteamos no esta en bd
    //Adelante y guardalo tambien
    @Builder.Default
    @OneToMany(mappedBy = "path", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<PositionTraveled> positions = new HashSet<>();

    public void setPositions(Set<PositionTraveled> positions) {
        if(this.positions == null || this.positions.isEmpty()){
            this.positions = positions;
        }else {
            this.positions.addAll(positions);
        }
        positions.forEach(position -> position.setPath(this));
    }
}
