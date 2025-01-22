CREATE TABLE IF NOT EXISTS orders (
    id SERIAL NOT NULL PRIMARY KEY,
    item_number INT NOT NULL,
    amount INT NOT NULL,
    sum_cost INT NOT NULL,
    date_and_time TIMESTAMP NOT NULL
);

INSERT INTO orders
VALUES (default, 4, 15, 319, '2025-01-21 14:22:07'),
       (default, 2, 7, 54, '2025-01-19 21:30:44')