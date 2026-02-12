drop database if exists libreria;

create database libreria character set latin1 collate latin1_spanish_Ci;

use libreria;

-- tabla vendedor
create table administrador(
	id int unsigned  primary key  not null auto_increment,
    nombre varchar(40) not null,
    contraseña varchar(30) not null
);

-- tabla comprador
create table comprador(
	id int unsigned primary key not null auto_increment,
    nombre varchar(40) not null,
    contraseña varchar(30) not null
);

-- tabla libro
create table libro(
	id int unsigned primary key not null auto_increment,
    titulo varchar(40) not null,
    descripcion varchar(100) not null,
    precio int not null,
    id_vendedor int unsigned not null,
    
    constraint fk_libro_vendedor
        foreign key (id_vendedor)
        references vendedor(id)
        on delete cascade
        on update cascade
      
);
