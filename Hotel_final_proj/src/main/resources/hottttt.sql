use hotel;

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password` varchar(35) NOT NULL,
  `status` enum('ADMIN','USER') NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `accounts_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `phone` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `room` (
  `idRoom` int(11) NOT NULL AUTO_INCREMENT,
  `numberR` int(10) NOT NULL,
  `roomCap` tinyint(4) NOT NULL,
  `price` int(11) NOT NULL,
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`idRoom`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

CREATE TABLE `minibar` (
  `idMinibar` int(11) NOT NULL AUTO_INCREMENT,
  `stock` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idMinibar`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `booking` (
  `idbook` int(11) NOT NULL AUTO_INCREMENT,
  `idRoom` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idMinibar` int(11) NOT NULL,
  `dateIn` date NOT NULL,
  `dateOut` date NOT NULL,
  PRIMARY KEY (`idbook`),
  KEY `idRoom` (`idRoom`),
  KEY `idMinibar` (`idMinibar`),
  KEY `idUser` (`idUser`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`idRoom`) REFERENCES `room` (`idRoom`),
  CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`idMinibar`) REFERENCES `minibar` (`idMinibar`),
  CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`idUser`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8accounts;

select * from users;

INSERT INTO users (name, surname, phone)
VALUES("Paul", "McCartney", 380989956);

INSERT INTO accounts (id, login, password, status)
VALUES(4, "ppolozhe", "11111", 'ADMIN');
select *from accounts;
