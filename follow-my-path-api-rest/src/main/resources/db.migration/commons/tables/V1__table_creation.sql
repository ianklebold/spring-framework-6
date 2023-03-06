drop table if exists car;

drop table if exists customer;

create table car (
                     id varchar(36) not null,
                     create_car_date datetime(6),
                     fuel_type varchar(255),
                     make varchar(255),
                     model varchar(50) not null,
                     patent_car varchar(255),
                     size varchar(255),
                     update_car_date datetime(6),
                     version integer,
                     year_car integer not null,
                     primary key (id)
) engine=InnoDB;

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