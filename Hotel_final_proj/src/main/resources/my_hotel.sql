use hotel;
DROP TABLE booking;
DROP TABLE users;
DROP TABLE accounts;
DROP TABLE room;
DROP TABLE bill;

CREATE TABLE IF NOT EXISTS `hotel`.`accounts` (
  `id` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `login` VARCHAR(45)  UNIQUE NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `status` ENUM('admin', 'user') NOT NULL,
  PRIMARY KEY (`id`))
-- UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  -- UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `hotel`.`users` (
  `id` INT UNIQUE NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
 -- UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `LAST_FIRST` (`surname` ASC, `name` ASC) VISIBLE,
  CONSTRAINT `fk_users_accounts`
    FOREIGN KEY (`id`)
    REFERENCES `hotel`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `hotel`.`room` (
`idRoom` int(11) NOT NULL AUTO_INCREMENT,
  `numberR` int unsigned NOT NULL,
  `roomCap` tinyint(4) NOT NULL,
   `price` int unsigned NOT NULL,
   `type` varchar(15) NOT NULL,
  PRIMARY KEY (`idRoom`))
 -- UNIQUE INDEX `id_UNIQUE` (`idRoom` ASC) VISIBLE)
ENGINE = InnoDB;

-- CREATE TABLE `room` (
--   `idRoom` int(11) NOT NULL AUTO_INCREMENT,
--   `numberR` int(10) NOT NULL,
--   `roomCap` tinyint(4) NOT NULL,
--   `price` int(11) NOT NULL,
--   `type` varchar(15) NOT NULL,
--   PRIMARY KEY (`idRoom`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

 CREATE TABLE `bill` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `price` INT,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `hotel`.`booking` (

	`idbook` INT NOT NULL AUTO_INCREMENT,
   `idRoom` INT NOT NULL,
   `idUser` INT NOT NULL,
   `idBill` INT NULL,
   `dateIn` DATE NOT NULL,
  `dateOut` DATE NOT NULL,
 `status` ENUM('processed', 'confirmed', 'rejected') NOT NULL,
 `rooms_type` ENUM('standart', 'economy', 'lux') NOT NULL,
  PRIMARY KEY (`idbook`, `idUser`),
 -- INDEX `fk_bookings_appartments1_idx` (`idRoom` ASC) VISIBLE,
 -- INDEX `fk_bookings_bills1_idx` (`bills_id` ASC) VISIBLE,
-- UNIQUE INDEX `id_UNIQUE` (`idbook` ASC) VISIBLE,
  INDEX `STATUS` (`status` ASC) VISIBLE,
  CONSTRAINT `fk_bookings_users1`
    FOREIGN KEY (`idUser`)
    REFERENCES `hotel`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bookings_appartments1`
    FOREIGN KEY (`idRoom`)
    REFERENCES `hotel`.`room` (`idRoom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
   CONSTRAINT `fk_bookings_bills1`
     FOREIGN KEY (`idBill`)
     REFERENCES `hotel`.`bill` (`id`)
   ON DELETE NO ACTION
     ON UPDATE NO ACTION
)
ENGINE = InnoDB;

-- CREATE TABLE IF NOT EXISTS `hotel`.`bookings` (
--   `id` INT NOT NULL AUTO_INCREMENT,
--   `date_from` DATE NOT NULL,
--   `date_to` DATE NOT NULL,
--   `status` ENUM('processed', 'confirmed', 'rejected') NOT NULL,
--   `persons` VARCHAR(45) NOT NULL,
--   `apartments_type` ENUM('suite', 'standart') NOT NULL,
--   `users_id` INT NOT NULL,
--   `apartments_id` INT NULL,
--   `bills_id` INT NULL,
--   PRIMARY KEY (`id`, `users_id`),
--   INDEX `fk_bookings_appartments1_idx` (`apartments_id` ASC) VISIBLE,
--   INDEX `fk_bookings_bills1_idx` (`bills_id` ASC) VISIBLE,
--   UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
--   INDEX `STATUS` (`status` ASC) VISIBLE,
--   CONSTRAINT `fk_bookings_users1`
--     FOREIGN KEY (`users_id`)
--     REFERENCES `hotel`.`users` (`id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION,
--   CONSTRAINT `fk_bookings_appartments1`
--     FOREIGN KEY (`apartments_id`)
--     REFERENCES `hotel`.`apartments` (`id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION,
--   CONSTRAINT `fk_bookings_bills1`
--     FOREIGN KEY (`bills_id`)
--     REFERENCES `hotel`.`bills` (`id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB

-- CREATE TABLE `accounts` (
--   `id` int(11) NOT NULL AUTO_INCREMENT,
--   `login` varchar(50) NOT NULL,
--   `password` varchar(35) NOT NULL,
--   `status` enum('ADMIN','USER') NOT NULL,
--   PRIMARY KEY (`id`),
--   CONSTRAINT `accounts_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- CREATE TABLE `users` (
--   `id` int(11) NOT NULL AUTO_INCREMENT,
--   `name` varchar(45) NOT NULL,
--   `surname` varchar(45) NOT NULL,
--   `phone` int(20) NOT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- CREATE TABLE `room` (
--   `idRoom` int(11) NOT NULL AUTO_INCREMENT,
--   `numberR` int(10) NOT NULL,
--   `roomCap` tinyint(4) NOT NULL,
--   `price` int(11) NOT NULL,
--   `type` varchar(15) NOT NULL,
--   PRIMARY KEY (`idRoom`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- CREATE TABLE `minibar` (
--   `idMinibar` int(11) NOT NULL AUTO_INCREMENT,
--   `stock` varchar(200) DEFAULT NULL,
--   PRIMARY KEY (`idMinibar`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- CREATE TABLE `booking` (
--   `idbook` int(11) NOT NULL AUTO_INCREMENT,
--   `idRoom` int(11) NOT NULL,
--   `idUser` int(11) NOT NULL,
--   `idMinibar` int(11) NOT NULL,
--   `dateIn` date NOT NULL,
--   `dateOut` date NOT NULL,
--   PRIMARY KEY (`idbook`),
--   KEY `idRoom` (`idRoom`),
--   KEY `idMinibar` (`idMinibar`),
--   KEY `idUser` (`idUser`),
--   CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`idRoom`) REFERENCES `room` (`idRoom`),
--   CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`idMinibar`) REFERENCES `minibar` (`idMinibar`),
--   CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`idUser`) REFERENCES `users` (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8accounts;

select * from room;

select * from users;
INSERT INTO accounts (login, password, status) VALUES
("ppolozhe@ukr.net", "11111", 'ADMIN'),
("anerus@ukr.net", "22222", 'ADMIN'),
("dstepane@gmail.com", "33333", 'ADMIN'),
("asvirido@gmail.com", "44444", 'ADMIN');

INSERT INTO users (id, name, surname, phone) VALUES
(1, "Antonio", "Sviridov", "+380909832671"),
(2, "Paul", "Polozhevets", "+380989889673"),
(3, "Dmutro", "Stepanets", "+380967547890"),
(4, "Anatolii", "Nerus", "+380986564527");

INSERT INTO room (numberR, roomCap, price, type) VALUES
(20, 2, 800, "economy"),
(21, 2, 750, "economy"),
(22, 1, 700, "economy"),
(23, 2, 800, "economy"),
(30, 2, 1100, "standart"),
(31, 1, 1050, "standart"),
(32, 2, 1150, "standart"),
(33, 2, 1100, "standart"),
(34, 1, 1000, "standart"),
(35, 1, 1080, "standart"),
(40, 1, 2200, "lux"),
(41, 2, 2300, "lux"),
(42, 2, 2350, "lux");

select *from booking;
select * from accounts;
select * from users;
select * from room;
