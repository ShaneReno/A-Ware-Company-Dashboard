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
insert into users values ('ffranciskiewicz1@cnet.com', 'yzzeoFKb3ys', true);
insert into users values ('ilythgoe2@marriott.com', '3vl3EyOS', true);
insert into users values ('gwimlet3@oracle.com', 'wF63Ix0', true);
insert into users values ('anormanvill4@upenn.edu', 'bqdHdhUoS9vN', true);
insert into users values ('bshakle5@smh.com.au', 'SYNzFw2iEMFJ', true);
insert into users values ('cstubbeley6@google.it', 'UpVXOY', true);
insert into users values ('btrainer7@java.com', 'Ir1RW4pa', true);
insert into users values ('bskillington8@nps.gov', '95JI8zHW', true);
insert into users values ('gsimla9@w3.org', '70L8h6Q3p', true);
insert into users values ('gsperwella@yelp.com', 'P17mVsLp1', true);
insert into users values ('kmackellerb@stanford.edu', 'ogKv066mO', true);


insert into authorities values ('sreynolds', 'ROLE_ADMIN');
insert into authorities values ('ncraigie0@ezinearticles.com', 'ROLE_USER');
insert into authorities values ('ffranciskiewicz1@cnet.com', 'ROLE_USER');
insert into authorities values ('ilythgoe2@marriott.com', 'ROLE_USER');
insert into authorities values ('gwimlet3@oracle.com', 'ROLE_USER');
insert into authorities values ('anormanvill4@upenn.edu', 'ROLE_USER');
insert into authorities values ('bshakle5@smh.com.au', 'ROLE_USER');
insert into authorities values ('cstubbeley6@google.it', 'ROLE_USER');
insert into authorities values ('btrainer7@java.com', 'ROLE_USER');
insert into authorities values ('bskillington8@nps.gov', 'ROLE_USER');
insert into authorities values ('gsimla9@w3.org', 'ROLE_USER');
insert into authorities values ('gsperwella@yelp.com', 'ROLE_USER');
insert into authorities values ('kmackellerb@stanford.edu', 'ROLE_USER');



DROP TABLE if EXISTS roster;

CREATE TABLE roster (employeeid int PRIMARY KEY, employeeemail varchar(60), employeename varchar(60), weekno int(2), monhours varchar(10), tueshours varchar(10),
 wedhours varchar(10), thurshours varchar(10), frihours varchar(10), sathours varchar(10), sunhours varchar(10),  employeedept varchar(60), employeehours int);

#Week number 12 roster
insert into roster values (0, 'wandapatel@yahoo.ie', 'Wanda Patel', 12, 'OFF', 'OFF', 'OFF', '8-5', '9-6', '2-7', '3-9', 'Clothing', 29);
insert into roster values (1, 'ncraigie0@ezinearticles.com', 'Nina Craigie', 12, '9-5', 'OFF', '1-10', '8-5', '9-6', '1-10', '3-9', 'Clothing', 50);
insert into roster values (2, 'ffranciskiewicz1@cnet.com', 'Flory Franciskiewicz', 12, 'OFF', '9-5', 'OFF', '8-5', '9-6', '1-10', '3-9', 'Beauty', 41);
insert into roster values (3, 'ilythgoe2@marriott.com', 'Ivory Lythgoe', 12, 'OFF', '9-5', '1-10', '8-5', '9-6', '1-10', '3-9', 'Electronics', 50);
insert into roster values (4, 'gwimlet3@oracle.com', 'Godiva Wimlet', 12, '1-10', '9-5', '1-10', '8-5', '9-6', 'OFF', '3-9', 'Health', 50);
insert into roster values (5, 'anormanvill4@upenn.edu', 'Anjanette Normanvill', 12, '1-10', 'OFF', '1-10', '8-5', '9-6', '9-5', '3-9', 'Books', 50);
insert into roster values (6, 'bshakle5@smh.com.au', 'Bella Shakle', 12, 'OFF', '9-5', 'OFF', '8-5', '9-6', '1-10', '3-9', 'Grocery', 41);
insert into roster values (7, 'cstubbeley6@google.it', 'Chandler Stubbeley', 12, '1-10', '1-10', 'OFF', '8-5', '9-6', 'OFF', '3-9', 'Grocery', 42);
insert into roster values (8, 'btrainer7@java.com', 'Brinn Trainer', 12, '1-10', '1-10', 'OFF', '1-10', '9-6', 'OFF', '3-9', 'Home', 42);
insert into roster values (9, 'bskillington8@nps.gov', 'Brandice Skillington', 12, '9-5', '1-10', 'OFF', '8-5', '9-6', '1-10', '3-9', 'Grocery', 50);
insert into roster values (10, 'gsimla9@w3.org', 'Garry Simla', 12, '9-5', '1-10', 'OFF', '8-5', '9-6', 'OFF', '3-9', 'Jewelry', 41);
insert into roster values (11, 'gsperwella@yelp.com', 'Gregory Sperwell', 12, '1-10', '9-5', '1-10', '8-5', '9-6', '9-5', '3-9', 'Baby', 58);
insert into roster values (12, 'kmackellerb@stanford.edu', 'Keslie MacKeller', 12, 'OFF', '1-10', 'OFF', '8-5', '9-6', '1-10', '3-9', 'Grocery', 42);


DROP TABLE if EXISTS shiftswaps;
CREATE TABLE shiftswaps (requestid int PRIMARY KEY, employeeemail varchar(60), recipientemail varchar(60), swapday varchar(20), forday varchar(20), accepted boolean);
insert into shiftswaps values (1, 'kmackellerb@stanford.edu', 'ncraigie0@ezinearticles.com', 'Tuesday', 'Wednesday', false);



