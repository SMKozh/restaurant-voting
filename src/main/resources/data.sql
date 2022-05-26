INSERT INTO users (EMAIL, NAME, PASSWORD)
VALUES ('user@yandex.ru', 'User', '{noop}password'),
       ('admin@gmail.com', 'Admin', '{noop}admin'),
       ('guest@mail.ru', 'Guest', '{noop}guest');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO restaurant (ID, NAME)
VALUES (1, 'new york pizza'),
       (2, 'clever irish pub'),
       (3, 'sushi make');

INSERT INTO menu (ID, MENU_DATE, RESTAURANT_ID)
VALUES (1, now(), 1),
       (2, '2020-02-15', 2),
       (3, now(), 2);

INSERT INTO dish (ID, NAME, PRICE, MENU_ID)
VALUES (1, 'pepsi', 100.0, 1),
       (2, 'pizza', 150.0, 1),
       (3, 'beer', 200.0, 2),
       (4, 'steak', 500.0, 2),
       (5, 'dark beer', 250.0, 3),
       (6, 'chips', 500.0, 3);

INSERT INTO vote (ID, VOTE_DATE, USER_ID, RESTAURANT_ID)
VALUES (1, '2020-02-15', 1, 2),
       (2, '2020-02-15', 2, 2),
       (3, now(), 1, 1),
       (4, now(), 2, 2);
