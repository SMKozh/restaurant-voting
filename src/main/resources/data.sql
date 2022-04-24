INSERT INTO users (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', 'password'),
       ('admin@javaops.ru', 'Admin_First', 'Admin_Last', 'admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('ROLE_USER', 1),
       ('ROLE_ADMIN', 2),
       ('ROLE_USER', 2);

INSERT INTO restaurant (ID, NAME)
VALUES (1, 'kfc'),
       (2, 'gusi');

INSERT INTO menu (ID, DATE, RESTAURANT_ID)
VALUES (1, '2020-01-11', 1),
       (2, '2020-02-15', 2);

INSERT INTO dish (ID, NAME, PRICE, MENU_ID)
VALUES (1, 'carbonara', 100, 1),
       (2, 'pizza', 50, 1);

INSERT INTO vote (ID, DATE_TIME, USER_ID, RESTAURANT_ID)
VALUES (1, '2020-01-11 10:00:00', 1, 2),
       (2, '2020-02-15 15:00:00', 2, 1);
