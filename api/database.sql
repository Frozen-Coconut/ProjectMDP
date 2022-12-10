create table if not exists users(
	id int(11) primary key auto_increment,
	email varchar(256) not null unique,
	password varchar(256) not null,
	name varchar(256) not null,
	address varchar(256) not null,
	phone varchar(32), -- untuk rumah sakit atau puskesmas
	date_of_birth date -- untuk user biasa
);

create table if not exists blood_requests(
    id int(11) primary key auto_increment,
    blood_type varchar(256) not null,
    scheduled_date date not null,
    puskesmas_id int(11) not null references users(id)
);
