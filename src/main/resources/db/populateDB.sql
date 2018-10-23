DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_user_seq RESTART WITH 100000;
ALTER SEQUENCE global_meal_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2015-06-01 14:00:00', 'Админ ланч', 510, 100001),
  ('2015-06-01 21:00:00', 'Админ ужин', 1510, 100001),
  ('2015-06-01 14:00:00', 'Юзер ланч', 520, 100000),
  ('2015-06-01 21:00:00', 'Юзер ужин', 1400, 100000);