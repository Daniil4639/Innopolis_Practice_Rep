/* ------------------------ Таблицы водителей ----------------------- */

CREATE TABLE IF NOT EXISTS drivers (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    age INT NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL
);

COMMENT ON TABLE drivers IS 'Таблица с данными о водителях';
COMMENT ON COLUMN drivers.id IS 'Идентификатор водителя';
COMMENT ON COLUMN drivers.name IS 'Имя водителя';
COMMENT ON COLUMN drivers.age IS 'Возраст водителя';
COMMENT ON COLUMN drivers.phone IS 'Телефон водителя';


/* ------------------------ Таблицы отделений ----------------------- */

CREATE TABLE IF NOT EXISTS departments (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    address VARCHAR(40) NOT NULL
);

COMMENT ON TABLE departments IS 'Таблица с данными об автобусных отделениях';
COMMENT ON COLUMN departments.id IS 'Идентификатор отделения';
COMMENT ON COLUMN departments.name IS 'Название отделения';
COMMENT ON COLUMN departments.address IS 'Адрес отделения';


/* ------------------------ Таблицы остановок ----------------------- */

CREATE TABLE IF NOT EXISTS stations (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    district VARCHAR(30) NOT NULL
);

COMMENT ON TABLE stations IS 'Таблица с данными об остановках города';
COMMENT ON COLUMN stations.id IS 'Идентификатор остановки';
COMMENT ON COLUMN stations.name IS 'Название остановки';
COMMENT ON COLUMN stations.district IS 'Район, в котором расположена остановка';


/* ------------------------ Таблицы маршрутов ----------------------- */

CREATE TABLE IF NOT EXISTS paths (
    id INT NOT NULL PRIMARY KEY,
    begin_station INT,
    end_station INT,
    duration INT NOT NULL,
    FOREIGN KEY (begin_station) REFERENCES stations (id) ON DELETE SET NULL,
    FOREIGN KEY (end_station) REFERENCES stations (id) ON DELETE SET NULL
);

COMMENT ON TABLE paths IS 'Таблица с данными о маршрутах следования автобусов';
COMMENT ON COLUMN paths.id IS 'Идентификатор маршрута';
COMMENT ON COLUMN paths.begin_station IS 'Стартовая точка маршрута';
COMMENT ON COLUMN paths.end_station IS 'Конечная точка маршрута';
COMMENT ON COLUMN paths.duration IS 'Продолжительность маршрута';


/* ------------------------ Таблицы автобусов ----------------------- */

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

COMMENT ON TABLE buses IS 'Таблица с данными об автобусах';
COMMENT ON COLUMN buses.id IS 'Идентификатор автобуса';
COMMENT ON COLUMN buses.number IS 'Номер автобуса';
COMMENT ON COLUMN buses.driver_id IS 'Идентификатор водителя автобуса';
COMMENT ON COLUMN buses.path_id IS 'Идентификатор маршрута автобуса';
COMMENT ON COLUMN buses.department_id IS 'Идентификатор отделения автобуса';
COMMENT ON COLUMN buses.seats_number IS 'Количество посадочных мест в автобусе';
COMMENT ON COLUMN buses.type IS 'Тип питания автобуса';


/* ------------------------ Таблицы соответствия маршрутов и остановок ----------------------- */

CREATE TABLE IF NOT EXISTS path_station (
    path_id INT NOT NULL,
    station_id INT NOT NULL,
    time_spent_from_start INT NOT NULL,
    PRIMARY KEY (path_id, station_id),
    FOREIGN KEY (path_id) REFERENCES paths (id),
    FOREIGN KEY (station_id) REFERENCES stations (id)
);

COMMENT ON TABLE path_station IS 'Таблица с данными о соответствии маршрута и остановки';
COMMENT ON COLUMN path_station.path_id IS 'Идентификатор маршрута';
COMMENT ON COLUMN path_station.station_id IS 'Идентификатор остановки';
COMMENT ON COLUMN path_station.time_spent_from_start IS 'Время, необходимое, чтобы доехать от стартовой точки маршрута до данной остановки';