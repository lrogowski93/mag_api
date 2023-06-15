-- liquibase formatted sql
-- changeset lukasz:1
create table users (
	id int not null AUTO_INCREMENT primary key,
	mag_id int not null,
	username varchar(50) not null,
	password varchar(500) not null,
	enabled boolean not null
);