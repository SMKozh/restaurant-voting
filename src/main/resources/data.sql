INSERT INTO users (EMAIL, NAME, PASSWORD)
VALUES ('user@yandex.ru', 'User', '{noop}password'),
       ('admin@gmail.com', 'Admin', '{noop}admin'),
       ('guest@mail.ru', 'Guest', '{noop}guest');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (ID, NAME)
VALUES (1, 'new york pizza'),
       (2, 'clever irish pub'),
       (3, 'sushi make');

INSERT INTO menu (ID, MENU_DATE, RESTAURANT_ID)
VALUES (1, now(), 1),
       (2, '2020-02-15', 2),
       (3, now(), 2);

INSERT INTO dish (ID, NAME, PRICE, MENU_ID)
VALUES (1, 'pepsi', 100, 1),
       (2, 'pizza', 150, 1),
       (3, 'beer', 200, 2),
       (4, 'steak', 500, 2),
       (5, 'dark beer', 250, 3),
       (6, 'chips', 500, 3);

INSERT INTO vote (ID, VOTE_DATE, USER_ID, RESTAURANT_ID)
VALUES (1, '2020-01-11', 1, 2),
       (2, '2020-02-15', 1, 1),
       (3, '2020-02-16', 1, 2),
       (4, '2020-02-17', 2, 1),
       (5, '2020-02-18', 2, 1),
       (6, now(), 1, 1),
       (7, now(), 2, 2);
