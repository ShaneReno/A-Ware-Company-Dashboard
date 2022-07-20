CREATE DATABASE IF NOT EXISTS aware;

USE aware; #Created Schema


DROP TABLE if EXISTS managers;

CREATE TABLE managers (managerEmail varchar(60) PRIMARY KEY, roles varchar(12), managerPass varchar(12) );
insert into employees values ('sreynolds@admin.com', 'pass', 'ROLE_USER', 'user');

DROP TABLE if EXISTS employees;

CREATE TABLE employees (employeeEmail varchar(60) PRIMARY KEY, employeePass varchar(12), employeeName varchar(60), employeeDept varchar(60));

insert into employees values ('ncraigie0@ezinearticles.com', 'YNlx7SHy', 'Natty Craigie', 'Clothing');
insert into employees values ('ffranciskiewicz1@cnet.com', 'yzzeoFKb3ys', 'Flory Franciskiewicz', 'Beauty');
insert into employees values ('ilythgoe2@marriott.com', '3vl3EyOS', 'Ivory Lythgoe', 'Electronics');
insert into employees values ('gwimlet3@oracle.com', 'wF63Ix0', 'Godiva Wimlet', 'Health');
insert into employees values ('anormanvill4@upenn.edu', 'bqdHdhUoS9vN', 'Anjanette Normanvill', 'Books');
insert into employees values ('bshakle5@smh.com.au', 'SYNzFw2iEMFJ', 'Bella Shakle', 'Grocery');
insert into employees values ('cstubbeley6@google.it', 'UpVXOY', 'Chandler Stubbeley', 'Grocery');
insert into employees values ('btrainer7@java.com', 'Ir1RW4pa', 'Brinn Trainer', 'Home');
insert into employees values ('bskillington8@nps.gov', '95JI8zHW', 'Brandice Skillington', 'Grocery');
insert into employees values ('gsimla9@w3.org', '70L8h6Q3p', 'Garry Simla', 'Jewelry');
insert into employees values ('gsperwella@yelp.com', 'P17mVsLp1', 'Gregory Sperwell', 'Baby');
insert into employees values ('kmackellerb@stanford.edu', 'ogKv066mO', 'Keslie MacKeller', 'Grocery');



DROP TABLE if EXISTS roster;

CREATE TABLE roster (employee_email varchar(60) PRIMARY KEY, employee_name varchar(60), week_no int(2), mon_hours varchar(10), tues_hours varchar(10),
 wed_hours varchar(10), thurs_hours varchar(10), fri_hours varchar(10), sat_hours varchar(10), sun_hours varchar(10),  employee_dept varchar(60));

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



