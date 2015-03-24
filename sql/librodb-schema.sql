drop database if exists librodb;
create database librodb;

use librodb;

create table users (
	username	varchar(20) not null primary key,  
	userpass	char(32) not null,
	name		varchar(70) not null,
	email		varchar(255) not null
);

create table user_roles (
	username			varchar(20) not null,  
	rolename 			varchar(20) not null,
	foreign key(username) references users(username) on delete cascade,
	primary key (username, rolename)
);


create table libros (
	id int auto_increment primary key, 
	titulo varchar(100) not null,
	autor varchar(20) not null,
	lengua varchar(20) not null,
	edicion varchar(20) not null,
	fecha_ed date,
	fecha_imp date,
	editorial varchar(20) not null	
);



create table autor (
    idautor int not null auto_increment primary key,
    name  varchar (30) not null
    
    
);


create table resenas (
	idres int not null auto_increment primary key,    
	username varchar(20) not null,
	fecha date,
	texto varchar(500),
	idlibro int not null,
	foreign key(username) references users(username),
	foreign key(idlibro) references libros(id)
	
);