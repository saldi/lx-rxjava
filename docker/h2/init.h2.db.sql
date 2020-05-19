create table users(
    id identity not null primary key,
    first_name varchar(100),
    last_name varchar(100),
    age integer,
    division_number varchar(10)
);

insert into users(id, first_name, last_Name, age, division_number) values (null, 'Jan', 'Kowalski', 45, 'P0500');
insert into users(id, first_name, last_Name, age, division_number) values (null, 'Jan', 'Nowak', 33, 'P0300');
insert into users(id, first_name, last_Name, age, division_number) values (null, 'Jerzy', 'Malinowski', 50, 'P0500');
insert into users(id, first_name, last_Name, age, division_number) values (null, 'Maciej', 'Kwiatkowski', 11, 'P0300');


create table locations(
                      id identity not null primary key,
                      location_number varchar(100),
                      location_name varchar(100)
);

create index location_number on locations(location_number);
insert into locations(id, location_number, location_name) values(null, 'P0500', 'Warsaw');
insert into locations(id, location_number, location_name) values(null, 'P0300', 'Cracow');