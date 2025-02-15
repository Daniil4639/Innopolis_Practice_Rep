CREATE TABLE IF NOT EXISTS grades (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    start_date DATE NOT NULL,
    is_active BOOLEAN NOT NULL
);

INSERT INTO grades VALUES (default, 'Frontend - developer', '2025-02-20', true),
                          (default, 'Fullstack - developer', '2025-03-01', true),
                          (default, 'Python - developer', '2025-03-15', true),
                          (default, 'Java - developer', '2025-04-25', true),
                          (default, '1C - developer', '2025-04-10', true),
                          (default, 'Go - developer', '2024-09-03', false);