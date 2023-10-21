create table t_customer
(
    id                  bigserial    not null,
    birth_year          integer      not null,
    creation_date       timestamp(6) not null,
    full_name                varchar(255) not null,
    phone_number        varchar(255) not null,
    second_phone_number varchar(255) not null,
    primary key (id),
    unique (phone_number),
    unique (second_phone_number)
)
