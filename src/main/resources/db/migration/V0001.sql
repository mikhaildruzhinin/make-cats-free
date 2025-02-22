create schema mcf;

create table mcf.managers (
    id bigserial not null primary key,
    name text not null
);

create table mcf.clients (
    id bigserial not null primary key,
    name text not null,
    manager_id bigint not null
);

create table mcf.workers (
    id bigserial not null primary key,
    name text not null,
    manager_id bigint not null references mcf.managers(id)
);

create table mcf.tasks (
    id bigserial not null primary key,
    title text not null,
    description text not null,
    price int,
    client_id bigint not null references mcf.clients(id),
    worker_id bigint references mcf.workers(id)
);
