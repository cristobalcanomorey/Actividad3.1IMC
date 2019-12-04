CREATE DATABASE IF NOT EXISTS ActividadIMC;

use ActividadIMC;

CREATE USER IF NOT EXISTS 'tofol'@'localhost' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON ActividadIMC.* TO 'tofol'@'localhost' WITH GRANT OPTION;

CREATE TABLE IF NOT EXISTS usuario (
	correo VARCHAR(100) NOT NULL PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	foto VARCHAR(255),
    validado BOOLEAN NOT NULL,
    fechaRegistro DATE NOT NULL
)  ENGINE=INNODB charset=utf8;

/*Foreign keys no funcionan*/
CREATE TABLE IF NOT EXISTS validacion (
	codigo VARCHAR(100) NOT NULL PRIMARY KEY,
    idUsuario VARCHAR(100) NOT NULL,
    foreign key (idUsuario)
   	 references usuario (correo)
    	on delete cascade
    	on update cascade
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS calculo (
	id INT(11) AUTO_INCREMENT PRIMARY KEY,
    estatura float NOT NULL,
    peso float NOT NULL,
    idUsuario VARCHAR(100) NOT NULL,
    foreign key (idUsuario)
   	 references usuario (correo)
    	on delete cascade
    	on update cascade
)  ENGINE=INNODB;