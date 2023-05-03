-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: hrms
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `emp_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `department` int DEFAULT NULL,
  `designation` int DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `blood_group` varchar(3) DEFAULT NULL,
  `nid` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `do_join` date DEFAULT NULL,
  `do_leave` date DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  `sal_basic` double DEFAULT NULL,
  `sal_insurance` double DEFAULT NULL,
  `sal_others` double DEFAULT NULL,
  `sal_type` varchar(10) DEFAULT NULL,
  `add_per_address` varchar(100) DEFAULT NULL,
  `add_per_city` varchar(45) DEFAULT NULL,
  `add_per_country` varchar(45) DEFAULT NULL,
  `add_pre_address` varchar(100) DEFAULT NULL,
  `add_pre_city` varchar(45) DEFAULT NULL,
  `add_pre_country` varchar(45) DEFAULT NULL,
  `bank_holder` varchar(45) DEFAULT NULL,
  `bank_name` varchar(45) DEFAULT NULL,
  `bank_branch` varchar(45) DEFAULT NULL,
  `bank_number` varchar(45) DEFAULT NULL,
  `bank_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Hassan','Mahfuj',5,4,'Male','O+','12345678','humahfuj@gmail.com','01755177177','1997-05-01','2010-04-06',NULL,'hr','123','HR Manager',9000,100,0,'Hourly','Hossainpur, Kishoregonj','Dhaka','Bangladesh','Hossainpur, Kishoregonj','Dhaka','Bangladesh','Hassan Mahfuj','Asia Bank','Kishoregonj','11223344555','Current'),(2,'Selim','Rayhan',7,10,'Male','A+','23425324','selimrayhan43@gmail.com','01876565434','1995-04-09','2019-04-17',NULL,'emp','123','Employee',5000,200,100,'Monthly','sjkfka','','',NULL,NULL,NULL,'Selim Rayhan','GHI Bank','Westside Branch','9876543210','Savings'),(4,'Rubel','Hossain',11,11,'Male','A+','456754747','rubel@gmail.com','01987657654','2004-04-08','2019-04-17',NULL,'rubel','123','Employee',6000,500,40,'Monthly',NULL,NULL,NULL,NULL,NULL,NULL,'Rubel Hossain','LMN Bank','Eastside Branch','0123456789','Checking'),(5,'Israt','Jahan',8,9,'Female','A+','123123','israt@gmail.com','01877665567','1995-04-01','2021-04-16',NULL,'israt','123','Employee',6000,200,100,'Monthly',NULL,NULL,NULL,NULL,NULL,NULL,'Israt Jahan','STU Bank','Uptown Branch','4567890123','Checking'),(6,'Sirajum','Munira',6,5,'Female','A+','1321412342','munira@gmail.com','01892363733','1997-04-17','2021-04-08',NULL,'munira','123','Employee',6000,200,0,'Monthly',NULL,NULL,NULL,NULL,NULL,NULL,'Sirajum Munira','OPQ Bank','Downtown Branch','7890123456','Savings'),(7,'Mohammad','Arifuzzaman',9,6,'Male','A+','78578586896','arif@gmail.com','01987546667','1997-04-18','2020-04-20',NULL,'arif','123','Employee',5600,200,100,'Monthly',NULL,NULL,NULL,NULL,NULL,NULL,'Mohammad Arifuzzaman','XYZ Bank','Main Street Branch','2468135790','Checking'),(8,'Mehenaz','Mimona',8,7,'Female','A+','798098098098','mehnaz@gmail.com','01876464456','1997-04-17','2021-04-19',NULL,'mehenaz','123','Employee',6000,200,100,'Monthly','Mirpur','Dhaka','Bangladesh','Mirpur','Dhaka','Bangladesh','Mehenaz Mimona','ABC Bank','Northside Branch','1357924680','Savings'),(9,'Rejwan','Sharif',6,5,'Male','A+','8686896896','rejwan@gmail.com','01477878787','1995-04-18','2019-04-22',NULL,'rejwan','123','Employee',7500,0,0,'Monthly','Mohakhali','Dhaka','Bangladesh','Mohakhali','Dhaka','Bangladesh','Rejwan Sharif','DEF Bank','Westside Branch','2468013579','Checking'),(10,'Sharif','Hossain',6,6,'Male','A+','87678688979','sharif@gmail.com','01987876765','1996-04-23','2022-04-13',NULL,'sharif','123','Employee',7600,500,0,'Monthly',NULL,NULL,NULL,NULL,NULL,NULL,'Sharif Hossain','GHI Bank','Eastside Branch','1237894560','Savings'),(11,'Mahmudul Hassan','Sazzad',7,10,'Male','A+','384758437593','sazzad@gmail.com','01763565533','1995-04-12','2021-04-13',NULL,'sazzad','123','Employee',5600,0,0,'Monthly',NULL,NULL,NULL,NULL,NULL,NULL,'Sazzad Hassan','ASD Bank','Downtown Branch','2468103579','Checking'),(12,'Rajib','Khan',9,8,'Male','A+','472352957048','rajib@gmail.com','01676207673','1994-08-05',NULL,NULL,'rajib','123','Employee',6700,0,0,'Monthly','harbaid,gazipur','gazipur','Bangladesh','harbaid,gazipur','gazipur','Bangladesh','Rajib Khan','Trust Bank','Tongi','257478722780','Savings'),(13,'Mohinur','Rahman',7,10,'Male','B+','1234567890','mohinur.dev@gmail.com','01733878578','1993-09-17','2023-04-01',NULL,'mohinur','136','Employee',8000,2000,0,'Monthly',NULL,NULL,NULL,'Mohammadpur, Dhaka','Dhaka','Bangladesh','Mohinur Rahman','City Bank Ltd.','Kushtia','127235327221272077',''),(14,'Shahidul','Islam',6,9,'Male','AB-','7678686889','shahidul@gmail.com','01455665778','1996-04-23','2021-04-18',NULL,'reyad','123','Employee',7650,0,0,'Monthly',NULL,NULL,NULL,NULL,NULL,NULL,'Shahidul Islam','Brac Bank','Southside Branch','1357902468','Checking'),(15,'Samsuddoha','Robin',7,10,'Male','O+','7678686889789','robin@gmail.com','01354675443','1994-04-14','2020-04-14',NULL,'robin','123','Employee',5600,0,0,'Monthly',NULL,NULL,NULL,NULL,NULL,NULL,'Samsuddoha Robin','PQR Bank','Westside Branch','4681357920','Money Market');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-03  9:56:49
