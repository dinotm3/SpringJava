delete from authorities;
delete from users;
delete from transaction;

insert into users (username, password, enabled) values
    ('admin', '$2a$10$nDVTobJ/x7slKTOwCcfGC.ePRjRr2yfVhFJ8im6I/V4Z9zP2c0/lK', 1),
    ('user', '$2a$10$nDVTobJ/x7slKTOwCcfGC.ePRjRr2yfVhFJ8im6I/V4Z9zP2c0/lK', 1);

insert into authorities (username, authority)
values
    ('admin', 'ROLE_ADMIN'),
    ('admin', 'ROLE_USER'),
    ('user', 'ROLE_USER');

insert into transaction (id, name, date, type, country, amount, exchange_rate, amountHRK) values
        (1, 'placa',  current_date, 'INCOME', 'BOSNIA', 100.0, 7.50, 750);

insert into transaction (id, name, date, type, country, amount, exchange_rate, amountHRK) values
        (2, 'cigarete',  current_date, 'EXPENSE', 'BOSNIA', 10, 7.50, 75);
