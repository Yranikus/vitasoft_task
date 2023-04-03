INSERT INTO roles (role) VALUES ('пользователь'),('администратор'),('оператор');
INSERT INTO users (name, password) VALUES ('алексей', '1234'),('федор', '1234'),('ксения', '1234'),('алекса', '1234');
insert into users_roles (users_id, roles_id) values (1,1),(2,1),(3,2),(4,3);