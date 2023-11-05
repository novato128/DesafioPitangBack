insert into users (first_Name, last_Name, email, birthday, login, password, phone) values('Rodrigo', 'Batista', 'athaide.rodrigo@gmail.com', '2023-10-19', 'admin', '$2a$10$31CxKY4P35VhNLmJN47SLuU92grJa3NcyYXgcjRKTj1tny/p4K6qq', '81998877665'); 
insert into users (first_Name, last_Name, email, birthday, login, password, phone) values('Rodolfo', 'Batista', 'athaide.rodolfo@gmail.com', '2023-10-19', 'sa', '$2a$10$31CxKY4P35VhNLmJN47SLuU92grJa3NcyYXgcjRKTj1tny/p4K6qq', '81998877665');
insert into car (color, license_plate, model, car_year) values ('marrom', 'pax', 'duster', 2);
insert into car (color, license_plate, model, car_year) values ('branco', 'dax', 'bravo', 1);
insert into car (color, license_plate, model, car_year) values ('preto', 'tux', 'punto', 4);
insert into car (color, license_plate, model, car_year) values ('verde', 'bux', 'celta', 5);
insert into users_cars (user_user_id, cars_car_id) values (1,1);
insert into users_cars (user_user_id, cars_car_id) values (1,2);
insert into users_cars (user_user_id, cars_car_id) values (2,3);
insert into users_cars (user_user_id, cars_car_id) values (2,4);

insert into car_count (car_id, count, user_login) values (1, 4, 'admin');
insert into car_count (car_id, count, user_login) values (2,5, 'admin');
insert into car_count (car_id, count, user_login) values (3,3, 'sa');
insert into car_count (car_id, count, user_login) values (4,1, 'sa');