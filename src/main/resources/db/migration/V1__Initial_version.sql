create sequence sq_user start 1;
create sequence sq_company start 1;

create table empresa (
  company_id smallint, -- nextval('sq_company')
  empresa varchar(255)
);
insert into empresa (company_id, empresa) values (nextval('sq_company'), 'Empresa Um');

create table grupo (
  group_id smallint,
  grupo varchar(255)
);
insert into grupo (group_id, grupo) values (1, 'Grupo Um'), (2, 'Grupo Dois');

create table usuario (
  user_id smallint, -- nextval('sq_user')
  nome varchar(255),
  email varchar(255),
  login varchar(255),
  senha varchar(255),
  company_id smallint,
  lojas varchar(255),
  ativo smallint
);
insert into usuario (user_id, nome, email, login, senha, company_id, lojas, ativo)
values (nextval('sq_user'), 'Usuario Um', 'um@um.com', 'um', '1', 1, 'L', 1);

create table user_group (
  user_id smallint,
  group_id smallint
);
insert into user_group (user_id, group_id) values (1, 1), (1, 2);