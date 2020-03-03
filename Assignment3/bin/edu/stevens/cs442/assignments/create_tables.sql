CREATE TABLE Employee(
   employee_id INTEGER PRIMARY KEY,
   first_name VARCHAR(100) NOT NULL,
   last_name VARCHAR(100) NOT NULL,
   employment_date DATE NOT NULL,
   dept_id INTEGER,
   annual_salary DECIMAL(10,2)
   );
  
CREATE TABLE Department(
   dept_id INTEGER PRIMARY KEY,
   dept_name VARCHAR(50) NOT NULL,
   manager_id INTEGER
   );
   
ALTER TABLE Employee ADD FOREIGN KEY (dept_id) REFERENCES Department(dept_id);
ALTER TABLE Department ADD FOREIGN KEY (manager_id) REFERENCES Employee(employee_id);

TRUNCATE Employee cascade;
TRUNCATE Department cascade;


DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
   

   