CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS students (
    id SERIAL NOT NULL,
    full_name VARCHAR NOT NULL,
    age INTEGER NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    roles VARCHAR ARRAY,
    grades_list INTEGER ARRAY,
    country VARCHAR NOT NULL,

    PRIMARY KEY (id, country),
    UNIQUE (email, country)
) PARTITION BY LIST(country);

CREATE TABLE students_rus PARTITION OF students
FOR VALUES IN ('Russian Federation');

CREATE TABLE students_uk PARTITION OF students
FOR VALUES IN ('Ukraine');

CREATE TABLE students_bel PARTITION OF students
FOR VALUES IN ('Belarus');

CREATE TABLE students_other PARTITION OF students
DEFAULT;