SET FOREIGN_KEY_CHECKS = 0;
CREATE DATABASE IF NOT EXISTS data;

USE data;

DROP TABLE if EXISTS users;
CREATE TABLE users (username varchar(50) not null primary key, password varchar(50) not null, enabled boolean not null);

DROP TABLE if EXISTS authorities;
CREATE TABLE authorities (username varchar(50) not null, authority varchar(50) not null, constraint fk_authorities_users foreign key(username) references users(username));


CREATE unique index ix_auth_username on authorities (username,authority);




insert into users values ('sreynolds', 'cherries51', true);	
insert into users values ('ncraigie0@ezinearticles.com', 'YNlx7SHy', true);
insert into authorities values ('sreynolds', 'ROLE_ADMIN');
insert into authorities values ('ncraigie0@ezinearticles.com', 'ROLE_USER');




DROP TABLE if EXISTS roster;

CREATE TABLE roster (employeeemail varchar(60) PRIMARY KEY, employeename varchar(60), weekno int(2), monhours varchar(10), tueshours varchar(10),
 wedhours varchar(10), thurshours varchar(10), frihours varchar(10), sathours varchar(10), sunhours varchar(10),  employeedept varchar(60));

#Week number 12 roster
insert into roster values ('ncraigie0@ezinearticles.com', 'Natty Craigie', 12, '9-5', '1-10', 'OFF', '8-5', '9-6', '1-10', '3-9', 'Clothing');
insert into roster values ('ffranciskiewicz1@cnet.com', 'Flory Franciskiewicz', 12, 'OFF', '9-5', 'OFF', '8-5', '9-6', '1-10', '3-9', 'Beauty');
insert into roster values ('ilythgoe2@marriott.com', 'Ivory Lythgoe', 12, 'OFF', '9-5', '1-10', '8-5', '9-6', '1-10', '3-9', 'Electronics');
insert into roster values ('gwimlet3@oracle.com', 'Godiva Wimlet', 12, '1-10', '9-5', '1-10', '8-5', '9-6', 'OFF', '3-9', 'Health');
insert into roster values ('anormanvill4@upenn.edu', 'Anjanette Normanvill', 12, '1-10', 'OFF', '1-10', '8-5', '9-6', '9-5', '3-9', 'Books');
insert into roster values ('bshakle5@smh.com.au', 'Bella Shakle', 12, 'OFF', '9-5', 'OFF', '8-5', '9-6', '1-10', '3-9', 'Grocery');
insert into roster values ('cstubbeley6@google.it', 'Chandler Stubbeley', 12, '1-10', '1-10', 'OFF', '8-5', '9-6', 'OFF', '3-9', 'Grocery');
insert into roster values ('btrainer7@java.com', 'Brinn Trainer', 12, '1-10', '1-10', 'OFF', '1-10', '9-6', 'OFF', '3-9', 'Home');
insert into roster values ('bskillington8@nps.gov', 'Brandice Skillington', 12, '9-5', '1-10', 'OFF', '8-5', '9-6', '1-10', '3-9', 'Grocery');
insert into roster values ('gsimla9@w3.org', 'Garry Simla', 12, '9-5', '1-10', 'OFF', '8-5', '9-6', 'OFF', '3-9', 'Jewelry');
insert into roster values ('gsperwella@yelp.com', 'Gregory Sperwell', 12, '1-10', '9-5', '1-10', '8-5', '9-6', '9-5', '3-9', 'Baby');
insert into roster values ('kmackellerb@stanford.edu', 'Keslie MacKeller', 12, 'OFF', '1-10', 'OFF', '8-5', '9-6', '1-10', '3-9', 'Grocery');

