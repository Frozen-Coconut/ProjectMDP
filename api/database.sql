/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 10.3.37-MariaDB-cll-lve : Database - mikotomy_database
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mikotomy_database` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;

USE `mikotomy_database`;

/*Table structure for table `blood_requests` */

DROP TABLE IF EXISTS `blood_requests`;

CREATE TABLE `blood_requests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blood_type` varchar(256) NOT NULL,
  `scheduled_date` date NOT NULL,
  `puskesmas_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

/*Data for the table `blood_requests` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `name` varchar(256) NOT NULL,
  `address` varchar(256) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`email`,`password`,`name`,`address`,`phone`,`date_of_birth`,`latitude`,`longitude`) values 
(1,'puskesmas1@example.com','puskesmas1','Puskesmas 1','Jalan Dharmahusada Indah Timur 35-37, Surabaya, Indonesia','0123456789',NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
