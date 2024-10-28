-- Создание таблицы card_members
create table if not exists card_members
(
    id         uuid not null primary key,
    company    varchar(255),
    email      varchar(255),
    firstname  varchar(255),
    lastname   varchar(255),
    middlename varchar(255),
    phone      varchar(255),
    position   varchar(255)
);

-- Создание таблицы roles
create table if not exists roles
(
    id   uuid not null primary key,
    role varchar(255)
);

-- Создание таблицы template_types
create table if not exists template_types
(
    id   uuid not null primary key,
    name varchar(255)
);

-- Создание таблицы cards с внешними ключами
create table if not exists cards
(
    id               uuid not null primary key,
    member_id        uuid
        constraint uk9hchnoo6uykcp9g7awi2nlx7u unique
        constraint fkalylqe3uiqojv6h5x6pwrbb6p references card_members,
    template_type_id uuid
        constraint fk88x91hpijgg522vourfuykjcu references template_types
);

-- Создание таблицы members_roles с внешними ключами
create table if not exists members_roles
(
    role_id   uuid
        constraint fkf9ds956i3re2d8tfbdqbik9d2 references roles on delete cascade,
    memebr_id uuid not null primary key
        constraint fkm5m8kaagellai5ljlulsj01ya references card_members
);

-- Создание таблицы roles_template_types с внешними ключами
create table if not exists roles_template_types
(
    role_id          uuid
        constraint fkst66u1vsf1jp14djffu6igt4g references roles,
    template_type_id uuid not null primary key
        constraint fk5b17tpuvbpiddkhl2tdr2swxf references template_types
);
