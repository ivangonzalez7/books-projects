/*source librodb-schema.sql*/

insert into users (username, userpass, name, email) values('alicia', MD5('alicia'), 'Alicia', 'alicia@acme.com');
insert into user_roles (username, rolename)values ('alicia', 'registered');

insert into users (username, userpass, name, email) values('blas', MD5('blas'), 'Blas', 'blas@acme.com');
insert into user_roles (username, rolename) values('blas', 'registered');

insert into users (username, userpass, name, email) values('admin', MD5('admin'), 'Administrator', 'admin@acme.com');
insert into user_roles (username, rolename) values('admin', 'admin');


insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo1', 'autor1', 'castellano','edicion1', '2003-05-09', '2003-06-20', 'editorial1');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo2', 'autor2', 'castellano','edicion2', '2004-06-09', '2004-07-01', 'editorial2');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo3', 'autor3', 'castellano','edicion3', '2005-07-09', '2005-08-05', 'editorial3');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo4', 'autor4', 'castellano','edicion4', '2006-08-09', '2006-09-13', 'editorial4');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo5', 'autor1', 'castellano','edicion1', '2007-09-09', '2007-10-23', 'editorial5');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo6', 'autor2', 'castellano','edicion2', '2003-10-09', '2003-10-20', 'editorial6');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo7', 'autor3', 'castellano','edicion3', '2003-11-09', '2004-12-09', 'editorial7');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo8', 'autor4', 'castellano','edicion4', '2003-01-09', '2005-02-04', 'editorial8');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo9', 'autor1', 'castellano','edicion1', '2003-02-09', '2006-03-06', 'editorial9');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo10', 'autor2', 'castellano','edicion2', '2003-03-09', '2004-04-02', 'editorial10');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo11', 'autor3', 'castellano','edicion3', '2003-04-09', '2005-05-22', 'editorial11');
insert into libros (titulo, autor, lengua, edicion, fecha_ed, fecha_imp, editorial) values('titulo12', 'autor4', 'castellano','edicion4', '2003-05-09', '2008-06-23', 'editorial12');
	
insert into resenas  (username, fecha, texto, idlibro) values ('alicia', '2009-10-09', 'libro muy recomendable',6);
insert into resenas  (username, fecha, texto, idlibro) values ('alicia', '2009-05-06', 'libro muy recomendable',3);
insert into resenas  (username, fecha, texto, idlibro) values ('blas', '2009-01-03', 'libro muy recomendable',1);
insert into resenas  (username, fecha, texto, idlibro) values ('blas', '2009-03-08', 'libro muy recomendable',2);
insert into resenas  (username, fecha, texto, idlibro) values ('alicia', '2009-08-09', 'libro muy recomendable',5);
insert into resenas  (username, fecha, texto, idlibro) values ('alicia', '2009-04-10', 'libro muy recomendable',4);
insert into resenas  (username, fecha, texto, idlibro) values ('blas', '2009-05-19', 'libro muy recomendable',2);
insert into resenas  (username, fecha, texto, idlibro) values ('blas', '2009-06-29', 'libro muy recomendable',1);
insert into resenas  (username, fecha, texto, idlibro) values ('alicia', '2009-07-31', 'libro muy recomendable',6);
insert into resenas  (username, fecha, texto, idlibro) values ('alicia', '2009-04-30', 'libro muy recomendable',8);
insert into resenas  (username, fecha, texto, idlibro) values ('blas', '2009-08-28', 'libro muy recomendable',9);
insert into resenas  (username, fecha, texto, idlibro) values ('blas', '2009-02-14', 'libro muy recomendable',10);
insert into resenas  (username, fecha, texto, idlibro) values ('alicia', '2009-05-13', 'libro muy recomendable',11);
insert into resenas  (username, fecha, texto, idlibro) values ('alicia', '2009-06-12', 'libro muy recomendable',5);
insert into resenas  (username, fecha, texto, idlibro) values ('blas', '2009-07-11', 'libro muy recomendable',4);
insert into resenas  (username, fecha, texto, idlibro) values ('blas', '2009-08-10', 'libro muy recomendable',2);


insert into autor (name) values ('autor1');
insert into autor (name) values ('autor2');
insert into autor (name) values ('autor3');
insert into autor (name) values ('autor4');
insert into autor (name) values ('autor5');
insert into autor (name) values ('autor6');
insert into autor (name) values ('autor7');
insert into autor (name) values ('autor8');
insert into autor (name) values ('autor9');
insert into autor (name) values ('autor10');
