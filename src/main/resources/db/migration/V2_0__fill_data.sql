INSERT INTO departments
VALUES (default, 'Департамент №1', 'Москва, ул. Садовая, 1'),
       (default, 'Департамент №2', 'Москва, Красногвардейский проезд, 21');

INSERT INTO drivers
VALUES (default, 'Никитников Ринат', 41, '+79018234033'),
       (default, 'Хусид Глеб', 36, '+79586913075'),
       (default, 'Зарубин Тимофей', 53, '+79533082991'),
       (default, 'Гилев Юрий', 29, '+79910171483');

INSERT INTO stations
VALUES (default, 'Серебряный бор', 'Строгино'),
       (default, 'Проспект Буденного', 'Соколиная гора'),
       (default, '2-я Черногрязская улица', 'Пресненский'),
       (default, 'Скарятинский переулок', 'Патриаршие пруды'),
       (default, 'Метро Бульвар Рокоссовского', 'Богородское'),
       (default, 'Рижский вокзал', 'Марьина роща'),
       (default, 'Мосгорсуд', 'Преображенское'),
       (default, 'Метро Сокольники', 'Сокольники'),
       (default, '138-й квартал Выхина', 'Выхино-Жулебино'),
       (default, 'Серпуховский Вал', 'Донской'),
       (default, 'Префектура ЮАО', 'Южнопортовый'),
       (default, 'Метро Кузьминки', 'Кузьминки'),
       (default, 'Прибрежный проезд', 'Левобережный'),
       (default, 'Карамышевская набережная', 'Хорошево-Мневники'),
       (default, 'Улица Мнёвники, 4', 'Хорошево-Мневники'),
       (default, 'Гидропроект', 'Сокол');

INSERT INTO paths
VALUES (default, 1, 2, 5400000),
       (default, 5, 6, 2400000),
       (default, 9, 10, 3600000),
       (default, 13, 14, 4860000);

INSERT INTO buses
VALUES (default, 'м3', 1, 1, 2, 36, 'бензин'),
       (default, '265', 2, 2, 1, 28, 'электрический'),
       (default, 'с799', 3, 3, 1, 30, 'бензин'),
       (default, '403', 4, 4, 2, 31, 'электрический');

INSERT INTO path_station
VALUES (1, 1, 0),
       (1, 2, 5400000),
       (1, 3, 10000),
       (1, 4, 840000),
       (2, 5, 0),
       (2, 6, 2400000),
       (2, 7, 53200),
       (2, 8, 120500),
       (3, 9, 0),
       (3, 10, 3600000),
       (3, 11, 910000),
       (3, 12, 30000),
       (4, 13, 0),
       (4, 14, 4860000),
       (4, 15, 927800),
       (4, 16, 17400);