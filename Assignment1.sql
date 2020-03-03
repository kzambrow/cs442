create table room(
	room_number integer NOT NULL,
	usage varchar(50) NOT NULL,
	PRIMARY KEY(room_number)
);

create table patient(
	patient_ID integer NOT NULL,
	name varchar(50) NOT NULL,
	PRIMARY KEY(patient_ID)
);

create table assigned(
	room_number integer NOT NULL,
	patient_ID integer NOT NULL,
	PRIMARY KEY (patient_ID, room_number),
	FOREIGN KEY (room_number) REFERENCES room,
	FOREIGN KEY (patient_ID) REFERENCES patient
);

create table doctor(
	doctor_ID integer NOT NULL,
	specialty varchar(50) NOT NULL,
	phone_number char(10) NOT NULL,
	PRIMARY KEY (doctor_ID)
);

create table treats(
	doctor_ID integer NOT NULL,
	patient_ID integer NOT NULL,
	PRIMARY KEY (doctor_ID, patient_ID),
	FOREIGN KEY (doctor_ID) REFERENCES doctor,
	FOREIGN KEY (patient_ID) REFERENCES patient
);

create table emergency_contact_relationship(
	patient_ID integer NOT NULL,
	contact_name varchar(50),
	relationship varchar(50),
	contact_phone_number char(10),
	PRIMARY KEY (patient_ID, contact_phone_number),
	FOREIGN KEY (patient_ID) REFERENCES patient
);

insert into room(room_number, usage) values
	(101,'post-surgery recovery'),
	(102,'post-surgery recovery'),
	(104,'post-surgery recovery'),
	(103,'pre-surgery prep'),
	(105,'pre-surgery prep'),
	(107,'pre-surgery prep'),
	(106,'diagnostic tests'),
	(108,'diagnostic tests');

insert into patient(patient_ID,name) values
	(200, 'John Mack'),
	(301, 'Ram Gopal'),
	(250, 'Ted Zack'),
	(134, 'Emily Blunt'),
	(412, 'Priya Ram');

insert into assigned(room_number, patient_ID) values
	(102, 200),
	(102, 301),
	(103, 250),
	(108, 134),
	(108, 412);

insert into doctor(doctor_ID, specialty, phone_number) values
	(11, 'Neuro surgeon', 9991112222),
	(20, 'Cardiothoracic surgeon', 9995552222),
	(25, 'Orthopedic surgeon', 9993331111);

insert into treats(doctor_ID, patient_ID) values
	(11, 250),
	(11, 301),
	(20, 134),
	(20, 412),
	(25, 301),
	(25, 200);

insert into emergency_contact_relationship(patient_ID, contact_name, relationship, contact_phone_number) values
	(200, 'Elizabeth', 'wife', 9992224444),
	(200, 'Eddie Mack', 'son', 9990001111),
	(301, 'Latha', 'wife', 9998887777),
	(250, 'Mary Zack', 'daughter', 9995554444),
	(134, 'Joe Blunt', 'father', 9997771111),
	(412, 'Suresh Ram', 'husband', 9991110000);