drop table if exists position_traveled;
drop table if exists path_traveled;


create table path_traveled (
                     id varchar(36) not null,
                     id_car varchar(36) not null,
                     create_path_traveled datetime(6),
                     update_path_traveled datetime(6),
                     version integer,
                     primary key (id),
                     constraint foreign key(id_car) references car(id)
) engine=InnoDB;


create table position_traveled (
                               id varchar(36) not null,
                               id_path varchar(36) not null,
                               created_position_traveled datetime(6),
                               position_info varchar(255),
                               update_position_traveled datetime(6),
                               version integer,
                               primary key (id),
                               constraint foreign key(id_path) references path_traveled(id)
) engine=InnoDB;