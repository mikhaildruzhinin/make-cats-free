alter table mcf.managers add foreign key (id) references mcf.users(id);
alter table mcf.workers add foreign key (id) references mcf.users(id);
alter table mcf.clients add foreign key (id) references mcf.users(id);
