drop table if exists car;

drop table if exists customer;

create table customer (
                          id varchar(36) not null,
                          birth_date datetime(6),
                          country varchar(255),
                          create_customer_date datetime(6),
                          name varchar(255),
                          surname varchar(255),
                          update_customer_date datetime(6),
                          version integer,
                          primary key (id)

) engine=InnoDB;
#Por convencion las foreign key son entidad_id por ejemplo customer_id
create table car (
                     id varchar(36) not null,
                     customer_id varchar(36),
                     create_car_date datetime(6),
                     fuel_type varchar(255),
                     make varchar(255),
                     model varchar(50) not null,
                     patent_car varchar(255),
                     size varchar(255),
                     update_car_date datetime(6),
                     version integer,
                     year_car integer not null,
                     primary key (id),
                     constraint foreign key(customer_id) references customer(id)
) engine=InnoDB;
