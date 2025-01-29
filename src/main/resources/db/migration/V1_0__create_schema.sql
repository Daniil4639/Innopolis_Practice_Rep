CREATE TABLE IF NOT EXISTS students (
    id SERIAL NOT NULL PRIMARY KEY,
    full_name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    grades_list INTEGER ARRAY
);

CREATE TABLE IF NOT EXISTS grades (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL
);

INSERT INTO grades VALUES (default, 'Frontend - developer'),
                          (default, 'Fullstack - developer'),
                          (default, 'Python - developer'),
                          (default, 'Java - developer'),
                          (default, '1C - developer');

INSERT INTO students VALUES (default, 'Овчинникова Вероника Максимовна', 'kufibuj-efe70@yandex.ru', ARRAY[2]),
                            (default, 'Наумова Анна Артёмовна', 'vewad_ewicu45@hotmail.com', ARRAY[1, 4]),
                            (default, 'Львов Константин Павлович', 'lotizaj-ige60@yandex.ru', ARRAY[5]),
                            (default, 'Козлов Александр Савельевич', 'yomano_wavo78@gmail.com', ARRAY[1, 3]);