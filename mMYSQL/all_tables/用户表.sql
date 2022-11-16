/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.17 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `usertb` (
	`id` int (11) PRIMARY KEY AUTO_INCREMENT,
	`name` varchar (33),
	`sex` char (3),
	`account` varchar (81),
	`password` int (11),
	`shijian` timestamp 
); 
