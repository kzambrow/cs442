CREATE TABLE Airport(
	airport_code CHAR(3) NOT NULL, 
	name VARCHAR(50) NOT NULL, 
	city VARCHAR(50) NOT NULL, 
	state CHAR(2) NOT NULL, 
	PRIMARY KEY (airport_code));
CREATE TABLE Flight(
	airline_code VARCHAR(5) NOT NULL, 
	flight_number INTEGER NOT NULL, 
	weekdays VARCHAR(50) NOT NULL, 
	PRIMARY KEY (airline_code, flight_number));
CREATE TABLE FlightLeg(
	airline_code VARCHAR(5) NOT NULL,
	flight_number INTEGER NOT NULL,
	leg_number INTEGER NOT NULL,
	dep_airport_code CHAR(3) NOT NULL,
	sched_dep_time TIME with time zone NOT NULL,
	arr_airport_code CHAR(3) NOT NULL,
	sched_arr_time TIME with time zone NOT NULL,
	PRIMARY KEY (airline_code, flight_number, leg_number),
	FOREIGN KEY (dep_airport_code) REFERENCES Airport (airport_code),
	FOREIGN KEY (arr_airport_code) REFERENCES Airport (airport_code));
INSERT INTO Airport(airport_code,name,city,state) VALUES('EWR', 'Newark International Airport', 'Newark', 'NJ'); 
INSERT INTO Airport(airport_code,name,city,state) VALUES('JFK', 'J F Kennedy Airport', 'New York', 'NY'); 
INSERT INTO Airport(airport_code,name,city,state) VALUES('ORD', 'Chicago O"Hare International Airport', 'Chicago', 'IL'); 
INSERT INTO Airport(airport_code,name,city,state) VALUES('MDW', 'Midway airport', 'Chicago', 'IL'); 
INSERT INTO Airport(airport_code,name,city,state) VALUES('LAX', 'Los Angeles International Airport', 'Los Angeles', 'CA'); 
INSERT INTO Airport(airport_code,name,city,state) VALUES('PHX', 'Phoenix International Airport', 'Phoenix', 'AZ'); 
INSERT INTO Flight(airline_code,flight_number,weekdays) VALUES('UA', 743, 'Su,M,T,W,R,F,Sa');
INSERT INTO Flight(airline_code,flight_number,weekdays) VALUES('SWA', 206, 'M,T,W,R,F,Sa');
INSERT INTO Flight(airline_code,flight_number,weekdays) VALUES('AA', 341, 'Su,M,T,W,R,F,Sa');
INSERT INTO Flight(airline_code,flight_number,weekdays) VALUES('SWA', 3301, 'M,T,W,R,F,Sa');
INSERT INTO FlightLeg(airline_code, flight_number, leg_number, dep_airport_code, sched_dep_time, arr_airport_code, sched_arr_time) VALUES('UA', 743, 1, 'EWR', '6:38 pm EST', 'ORD', '7:57 pm CST');
INSERT INTO FlightLeg(airline_code, flight_number, leg_number, dep_airport_code, sched_dep_time, arr_airport_code, sched_arr_time) VALUES('SWA', 206, 1, 'EWR', '6:00 am EST', 'MDW', '7:15 am CST');
INSERT INTO FlightLeg(airline_code, flight_number, leg_number, dep_airport_code, sched_dep_time, arr_airport_code, sched_arr_time) VALUES('AA', 341, 1, 'JFK', '2:27 pm EST', 'LAX', '5:33 pm PST');
INSERT INTO FlightLeg(airline_code, flight_number, leg_number, dep_airport_code, sched_dep_time, arr_airport_code, sched_arr_time) VALUES('SWA', 3301, 1, 'EWR', '6:05 am EST', 'MDW', '8:05 am CST');
INSERT INTO FlightLeg(airline_code, flight_number, leg_number, dep_airport_code, sched_dep_time, arr_airport_code, sched_arr_time) VALUES('SWA', 3301, 2, 'MDW', '9:20 am CST', 'PHX', '11:15 am PST');