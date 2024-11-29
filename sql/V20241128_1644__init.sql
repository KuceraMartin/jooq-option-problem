CREATE SCHEMA test;

SET search_path TO test;

CREATE TABLE employee (
  id SERIAL NOT NULL,
  name TEXT NOT NULL,
  salary INT NOT NULL,
  age INT NULL,
  CONSTRAINT employee_pkey PRIMARY KEY (id)
);

INSERT INTO employee (name, salary, age) VALUES
  ('Alan Turing', 11111, 41),
  ('Ada Lovelace', 12345, null);
