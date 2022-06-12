
INSERT INTO foods (id, name) VALUES (1, 'Bread & Egg'), (2, 'Porridge'), (3, 'Tom brown');

INSERT INTO restaurants(id, name, zip_code, city, state) VALUES (1, 'Restaurant1', '1234', 'City1', 'state1'),
                               (2, 'Restaurant2', '1534', 'City2', 'state2');

INSERT INTO users (id, display_name, password, city, state, zip_code)
VALUES (2, 'User1', 'password1', 'City1', 'State1', '1234'),
       (3, 'User2', 'password2', 'City2', 'State2', '358');

INSERT INTO reviews (restaurant_id, user_id, commentary, status)
VALUES (1, 2, 'Nice food', 1),
       (2, 2, 'Awesome food', 0),
       (1, 3, 'delicious dishes', 1),
       (2, 3, 'dope', 0);

INSERT INTO food_ratings (id, rating, food_id, restaurant_id, user_id)
VALUES (1, 3, 1, 1, 2),
       (2, 4, 2, 1, 2),
       (3, 1, 3, 1, 2),
       (4, 2, 1, 2, 2),
       (5, 5, 2, 2, 2),
       (6, 3, 3, 2, 2),
       (7, 2, 1, 1, 3),
       (8, 3, 2, 1, 3),
       (9, 1, 3, 1, 3),
       (10,2, 1, 2, 3),
       (11,5, 2, 2, 3),
       (12,4, 3, 2, 3);
