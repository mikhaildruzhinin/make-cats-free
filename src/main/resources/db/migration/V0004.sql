alter table mcf.tasks add column executed_at timestamp not null default '1970-01-01';
alter table mcf.tasks alter column executed_at drop default;
alter table mcf.tasks add column created_at timestamp not null default '1970-01-01';
alter table mcf.tasks alter column created_at set default now();
