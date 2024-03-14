create table users
(
    id          varchar(255) primary key,
    first_name  varchar(255) not null,
    second_name varchar(255) null,
    email       varchar(255) not null,
    pass   varchar(255) not null

);

create table types(
    id varchar(255) primary key,
    name varchar(255) not null

);

create table shoes(
    id varchar(255) primary key,
    user_id varchar(255) not null references users (id),
    types_id varchar(255) not null references types (id),
    name varchar(255) not null,
    size int not null

);