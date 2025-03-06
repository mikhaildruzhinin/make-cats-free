create table mcf.users (
    id bigserial not null primary key,
    username text unique not null,
    password text not null,
    role text not null,
    created_at timestamp not null default now()
);
