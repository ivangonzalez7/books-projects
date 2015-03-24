drop user 'libros'@'localhost';
create user 'libros'@'localhost' identified by 'libros';
grant all privileges on librodb.* to 'libros'@'localhost';
flush privileges;