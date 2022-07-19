CREATE DATABASE IF NOT EXISTS aware;

USE aware; #Created Schema


CREATE TABLE users (username varchar(50) not null primary key, password varchar(50) not null, enabled boolean not null);
insert into users values ('user', 'pass', true);
insert into users values ('admin', 'pass', true);	

CREATE TABLE authorities (username varchar(50) not null, authority varchar(50) not null, constraint fk_authorities_users foreign key(username) references users(username));
insert into authorities values ('user', 'ROLE_USER');
insert into authorities values ('admin', 'ROLE_ADMIN');

CREATE unique index ix_auth_username on authorities (username,authority);


