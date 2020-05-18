create table users(
    id varchar(40) not null primary key,
    first_name varchar(100),
    last_name varchar(100),
    age integer
);

insert into users(id, first_name, last_Name, age) values ('05a35dfc-6772-11ea-bc55-0242ac130003', 'Jan', 'Kowalski', 45);
insert into users(id, first_name, last_Name, age) values ('1775faf8-6772-11ea-bc55-0242ac130003', 'Jan', 'Nowak', 33);
insert into users(id, first_name, last_Name, age) values ('1e6d2c8c-6772-11ea-bc55-0242ac130003', 'Jerzy', 'Malinowski', 50);
insert into users(id, first_name, last_Name, age) values ('29095efe-6772-11ea-bc55-0242ac130003', 'Maciej', 'Kwiatkowski', 11);