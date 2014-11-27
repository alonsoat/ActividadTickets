SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

CREATE DATABASE IF NOT EXISTS `tickets` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `tickets`;

DELIMITER $$
DROP PROCEDURE IF EXISTS `export_dynamic`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `export_dynamic`(IN `file_name` CHAR(64))
BEGIN 
set @myvar = concat('SELECT * INTO OUTFILE ',"'",file_name,"'",' FROM missatges') ; 
PREPARE stmt1 FROM @myvar; 
EXECUTE stmt1; 
Deallocate prepare stmt1; 
END$$

DELIMITER ;

DROP TABLE IF EXISTS `missatges`;
CREATE TABLE IF NOT EXISTS `missatges` (
`id` int(11) NOT NULL,
  `titol` varchar(200) COLLATE utf8_bin NOT NULL,
  `text` text COLLATE utf8_bin NOT NULL,
  `imatge` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `data_crea` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_usuari` int(11) NOT NULL,
  `id_ticket` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=6 ;

INSERT INTO `missatges` (`id`, `titol`, `text`, `imatge`, `data_crea`, `id_usuari`, `id_ticket`) VALUES
(2, 'Problemes impressora', 'La impressora no funciona des de fa uns dies...', NULL, '2014-11-12 15:20:35', 2, 2),
(3, 'Ubicació impressora', 'Podria indicar les següents dades:\r\n- Marca\r\n- Model\r\n- Planta\r\n- Despatx\r\n\r\nGràcies', NULL, '2014-11-12 15:22:37', 1, 2),
(4, 'Espai al disc', 'No tinc espai al disc. Necessite més espai per als meus dissenys!!!', NULL, '2014-11-12 15:25:16', 3, 3),
(5, 'Espai actual: 200GB lliures', 'Ja s''ha solventat el problema.\r\n\r\nAra té 200GB lliures.\r\n\r\nLes pel·lícules i sèries que tenia a la carpeta "privat" han sigut eliminades.\r\n\r\nTanque ticket.', NULL, '2014-11-12 15:26:41', 1, 3);

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE IF NOT EXISTS `tickets` (
`id` int(11) NOT NULL,
  `estat` enum('Obert','Tancat') COLLATE utf8_bin NOT NULL,
  `data_obri` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `data_tanca` datetime DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=4 ;

INSERT INTO `tickets` (`id`, `estat`, `data_obri`, `data_tanca`) VALUES
(2, 'Obert', '2014-11-12 15:10:37', NULL),
(3, 'Tancat', '2014-11-12 15:23:30', '2014-11-12 16:28:00');

DROP TABLE IF EXISTS `usuaris`;
CREATE TABLE IF NOT EXISTS `usuaris` (
`id` int(11) NOT NULL,
  `nom` varchar(100) COLLATE utf8_bin NOT NULL,
  `mail` varchar(100) COLLATE utf8_bin NOT NULL,
  `pass` varchar(200) COLLATE utf8_bin NOT NULL,
  `departament` enum('Administració','Informàtica','Disseny','Màrketing') COLLATE utf8_bin NOT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=6 ;

INSERT INTO `usuaris` (`id`, `nom`, `mail`, `pass`, `departament`, `admin`) VALUES
(1, 'Admin', 'admin@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Informàtica', 1),
(2, 'Administracion01', 'administracion01@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Administració', 0),
(3, 'Disseny01', 'disseny01@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Disseny', 0),
(4, 'Marketing01', 'marketing01@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Màrketing', 0),
(5, 'Informatica01', 'informatica01@gmail.com', '1234', 'Informàtica', 0);


ALTER TABLE `missatges`
 ADD PRIMARY KEY (`id`), ADD KEY `id_ticket` (`id_ticket`), ADD KEY `id_usuari` (`id_usuari`);

ALTER TABLE `tickets`
 ADD PRIMARY KEY (`id`);

ALTER TABLE `usuaris`
 ADD PRIMARY KEY (`id`);


ALTER TABLE `missatges`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
ALTER TABLE `tickets`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
ALTER TABLE `usuaris`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;

ALTER TABLE `missatges`
ADD CONSTRAINT `fk_missatges_ticket` FOREIGN KEY (`id_ticket`) REFERENCES `tickets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_missatges_usuari` FOREIGN KEY (`id_usuari`) REFERENCES `usuaris` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
