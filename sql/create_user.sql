drop user 'raul'@'localhost';
create user 'raul'@'localhost' identified by 'raul';
grant all privileges on beeterdb.* to 'beeter'@'localhost';
flush privileges;