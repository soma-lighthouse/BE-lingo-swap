INSERT INTO country (code)
VALUES ('kr'),
       ('us'),
       ('jp');
INSERT INTO language (code, name)
VALUES ('ko', '한국어'),
       ('en', 'English');
INSERT INTO category (name)
VALUES ('Food'),
       ('Game');
INSERT INTO interests (category_id, name)
VALUES (1, 'Japanese'),
       (1, 'Chinese'),
       (1, 'Korean'),
       (2, 'RPG'),
       (2, 'FPS'),
       (2, 'Sports Game');
