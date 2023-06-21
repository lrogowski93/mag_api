--mariadb
create table users (
	id int not null AUTO_INCREMENT primary key,
	mag_id int not null,
	username varchar(50) not null,
	password varchar(500) not null,
	enabled boolean not null
);

--oracle
create table users (
	id NUMBER(18) GENERATED ALWAYS AS IDENTITY,
	mag_id NUMBER(18) NOT NULL,
	username varchar(50) NOT NULL,
	password varchar(500) NOT NULL,
	enabled NUMBER(1) NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);