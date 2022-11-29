create table users(
	id int(11) primary key auto_increment,
	email varchar(256) not null unique,
	password varchar(256) not null,
	name varchar(256) not null,
	address varchar(256) not null,
	phone varchar(32), -- untuk rumah sakit atau puskesmas
	date_of_birth date -- untuk user biasa
);
