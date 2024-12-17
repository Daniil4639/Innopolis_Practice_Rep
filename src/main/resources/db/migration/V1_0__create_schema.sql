CREATE TABLE IF NOT EXISTS drivers (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    age INT NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS departments (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    address VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS stations (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    district VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS paths (
    id INT NOT NULL PRIMARY KEY,
    begin_station INT,
    end_station INT,
    duration INT NOT NULL,
    FOREIGN KEY (begin_station) REFERENCES stations (id) ON DELETE SET NULL,
    FOREIGN KEY (end_station) REFERENCES stations (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS buses (
    id INT NOT NULL PRIMARY KEY,
    number VARCHAR(5) NOT NULL,
    driver_id INT,
    path_id INT,
    department_id INT,
    seats_number INT NOT NULL,
    type VARCHAR NOT NULL,
    FOREIGN KEY (driver_id) REFERENCES drivers (id) ON DELETE SET NULL,
    FOREIGN KEY (path_id) REFERENCES paths (id) ON DELETE SET NULL,
    FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS path_station (
    path_id INT NOT NULL,
    station_id INT NOT NULL,
    time_spent_from_start INT NOT NULL,
    PRIMARY KEY (path_id, station_id),
    FOREIGN KEY (path_id) REFERENCES paths (id),
    FOREIGN KEY (station_id) REFERENCES stations (id)
);