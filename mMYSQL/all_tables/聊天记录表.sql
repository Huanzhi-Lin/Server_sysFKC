/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.17 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `chattb` (
	`id` int (16) PRIMARY KEY AUTO_INCREMENT,
	`sendername` char (33),
	`message` char (81),
	`shijian01` timestamp 
); 
