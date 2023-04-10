

create table if not exists transaction
(
    id identity,
    name varchar(100) NOT NULL unique,
    amount double NOT NULL,
    date timestamp NOT NULL,
    country varchar(25) NOT NULL,
    type varchar (25) NOT NULL,
    exchange_rate double NOT NULL,
    amountHRK double NOT NULL
);

create table if not exists users (
    id identity,
    username varchar(100) not null,
    password varchar(250) not null,
    enabled boolean not null
    );

create table if not exists authorities (
    username varchar(100) not null,
    authority varchar(100) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
    );


