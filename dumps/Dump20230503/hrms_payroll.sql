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
-- Table structure for table `payroll`
--

DROP TABLE IF EXISTS `payroll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payroll` (
  `pay_id` int NOT NULL AUTO_INCREMENT,
  `emp_id` int DEFAULT NULL,
  `month` date DEFAULT NULL,
  `hours` int DEFAULT NULL,
  `gross_pay` double DEFAULT NULL,
  `deductions` double DEFAULT NULL,
  `net_pay` double DEFAULT NULL,
  `pay_date` date DEFAULT NULL,
  `overtime_hour` int DEFAULT NULL,
  `leave_hour` int DEFAULT NULL,
  `late_count` int DEFAULT NULL,
  `basic` double DEFAULT NULL,
  `house_rent` double DEFAULT NULL,
  `medical` double DEFAULT NULL,
  `conveyance` double DEFAULT NULL,
  `provident_fund` double DEFAULT NULL,
  `insurance` double DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `others` double DEFAULT NULL,
  `overtime` double DEFAULT NULL,
  `late` double DEFAULT NULL,
  `leave1` double DEFAULT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payroll`
--

LOCK TABLES `payroll` WRITE;
/*!40000 ALTER TABLE `payroll` DISABLE KEYS */;
INSERT INTO `payroll` VALUES (9,1,'2023-01-01',183,15300,1630,13670,'2023-01-31',0,0,1,9000,4500,900,900,1080,100,450,0,0,0,0),(10,2,'2023-01-01',186,8581.5,1150,7431.5,'2023-01-31',2,0,0,5000,2500,500,500,600,200,250,100,81.5,0,0),(11,4,'2023-01-01',184,10200,1560,8640,'2023-01-31',0,0,0,6000,3000,600,600,720,500,300,40,0,0,0),(12,5,'2023-01-01',184,10200,1320,8880,'2023-01-31',0,0,0,6000,3000,600,600,720,200,300,100,0,0,0),(13,6,'2023-01-01',184,10200,1220,8980,'2023-01-31',0,0,0,6000,3000,600,600,720,200,300,0,0,0,0),(14,7,'2023-01-01',184,9520,1252,8268,'2023-01-31',0,0,0,5600,2800,560,560,672,200,280,100,0,0,0),(15,8,'2023-01-01',184,10200,1320,8880,'2023-01-31',0,0,0,6000,3000,600,600,720,200,300,100,0,0,0),(16,9,'2023-01-01',184,12750,1275,11475,'2023-01-31',0,0,0,7500,3750,750,750,900,0,375,0,0,0,0),(17,10,'2023-01-01',184,12920,1792,11128,'2023-01-31',0,0,0,7600,3800,760,760,912,500,380,0,0,0,0),(18,11,'2023-01-01',184,9520,952,8568,'2023-01-31',0,0,0,5600,2800,560,560,672,0,280,0,0,0,0),(19,12,'2023-01-01',184,11390,1139,10251,'2023-01-31',0,0,0,6700,3350,670,670,804,0,335,0,0,0,0),(20,13,'2023-01-01',184,13600,3360,10240,'2023-01-31',0,0,0,8000,4000,800,800,960,2000,400,0,0,0,0),(21,14,'2023-01-01',184,13005,1300.5,11704.5,'2023-01-31',0,0,0,7650,3825,765,765,918,0,382.5,0,0,0,0),(22,15,'2023-01-01',184,9520,952,8568,'2023-01-31',0,0,0,5600,2800,560,560,672,0,280,0,0,0,0),(23,1,'2023-02-01',152,15300,1630,13670,'2023-02-28',0,0,0,9000,4500,900,900,1080,100,450,0,0,0,0),(24,2,'2023-02-01',152,8500,1150,7350,'2023-02-28',0,0,0,5000,2500,500,500,600,200,250,100,0,0,0),(25,4,'2023-02-01',152,10200,1560,8640,'2023-02-28',0,0,0,6000,3000,600,600,720,500,300,40,0,0,0),(26,5,'2023-02-01',152,10200,1951.6,8248.4,'2023-02-28',0,16,0,6000,3000,600,600,720,200,300,100,0,0,631.6),(27,6,'2023-02-01',152,10200,1220,8980,'2023-02-28',0,0,0,6000,3000,600,600,720,200,300,0,0,0,0),(28,7,'2023-02-01',152,9520,1252,8268,'2023-02-28',0,0,0,5600,2800,560,560,672,200,280,100,0,0,0),(29,8,'2023-02-01',152,10200,1320,8880,'2023-02-28',0,0,0,6000,3000,600,600,720,200,300,100,0,0,0),(30,9,'2023-02-01',152,12750,1275,11475,'2023-02-28',0,0,0,7500,3750,750,750,900,0,375,0,0,0,0),(31,10,'2023-02-01',152,12920,1792,11128,'2023-02-28',0,0,0,7600,3800,760,760,912,500,380,0,0,0,0),(32,11,'2023-02-01',152,9520,952,8568,'2023-02-28',0,0,0,5600,2800,560,560,672,0,280,0,0,0,0),(33,12,'2023-02-01',152,11390,1139,10251,'2023-02-28',0,0,0,6700,3350,670,670,804,0,335,0,0,0,0),(34,13,'2023-02-01',152,13600,3360,10240,'2023-02-28',0,0,0,8000,4000,800,800,960,2000,400,0,0,0,0),(35,14,'2023-02-01',152,13005,1300.5,11704.5,'2023-02-28',0,0,0,7650,3825,765,765,918,0,382.5,0,0,0,0),(36,15,'2023-02-01',152,9520,952,8568,'2023-02-28',0,0,0,5600,2800,560,560,672,0,280,0,0,0,0),(37,1,'2023-03-01',159,15300,1630,13670,'2023-03-31',0,0,1,9000,4500,900,900,1080,100,450,0,0,0,0),(38,2,'2023-03-01',161,8546.9,1150,7396.9,'2023-03-31',1,0,0,5000,2500,500,500,600,200,250,100,46.9,0,0),(39,4,'2023-03-01',157,10200,1860,8340,'2023-03-31',0,0,3,6000,3000,600,600,720,500,300,40,0,300,0),(40,5,'2023-03-01',160,10200,1320,8880,'2023-03-31',0,0,0,6000,3000,600,600,720,200,300,100,0,0,0),(41,6,'2023-03-01',160,10200,1220,8980,'2023-03-31',0,0,0,6000,3000,600,600,720,200,300,0,0,0,0),(42,7,'2023-03-01',160,9520,1252,8268,'2023-03-31',0,0,0,5600,2800,560,560,672,200,280,100,0,0,0),(43,8,'2023-03-01',160,10200,1320,8880,'2023-03-31',0,0,0,6000,3000,600,600,720,200,300,100,0,0,0),(44,9,'2023-03-01',161,12820.3,1275,11545.3,'2023-03-31',1,0,0,7500,3750,750,750,900,0,375,0,70.3,0,0),(45,10,'2023-03-01',160,12920,1792,11128,'2023-03-31',0,0,0,7600,3800,760,760,912,500,380,0,0,0,0),(46,11,'2023-03-01',160,9520,952,8568,'2023-03-31',0,0,0,5600,2800,560,560,672,0,280,0,0,0,0),(47,12,'2023-03-01',160,11390,1139,10251,'2023-03-31',0,0,0,6700,3350,670,670,804,0,335,0,0,0,0),(48,13,'2023-03-01',160,13600,4160,9440,'2023-03-31',0,16,0,8000,4000,800,800,960,2000,400,0,0,0,800),(49,14,'2023-03-01',160,13005,1300.5,11704.5,'2023-03-31',0,0,0,7650,3825,765,765,918,0,382.5,0,0,0,0),(50,15,'2023-03-01',160,9520,952,8568,'2023-03-31',0,0,0,5600,2800,560,560,672,0,280,0,0,0,0),(55,5,'2023-04-01',112,10200,1320,8880,'2023-05-02',0,0,0,6000,3000,600,600,720,200,300,100,0,0,0),(56,6,'2023-04-01',112,10200,1220,8980,'2023-05-02',0,0,0,6000,3000,600,600,720,200,300,0,0,0,0),(57,7,'2023-04-01',112,9520,1252,8268,'2023-05-02',0,0,0,5600,2800,560,560,672,200,280,100,0,0,0),(58,8,'2023-04-01',112,10200,1320,8880,'2023-05-02',0,0,0,6000,3000,600,600,720,200,300,100,0,0,0),(59,9,'2023-04-01',112,12750,1275,11475,'2023-05-02',0,0,0,7500,3750,750,750,900,0,375,0,0,0,0),(60,10,'2023-04-01',112,12920,1792,11128,'2023-05-02',0,0,0,7600,3800,760,760,912,500,380,0,0,0,0),(61,11,'2023-04-01',112,9520,952,8568,'2023-05-02',0,0,0,5600,2800,560,560,672,0,280,0,0,0,0),(62,12,'2023-04-01',112,11390,1139,10251,'2023-05-02',0,0,0,6700,3350,670,670,804,0,335,0,0,0,0),(63,13,'2023-04-01',112,13600,3360,10240,'2023-05-02',0,0,0,8000,4000,800,800,960,2000,400,0,0,0,0),(64,14,'2023-04-01',112,13005,1300.5,11704.5,'2023-05-02',0,0,0,7650,3825,765,765,918,0,382.5,0,0,0,0),(65,15,'2023-04-01',112,9520,952,8568,'2023-05-02',0,0,0,5600,2800,560,560,672,0,280,0,0,0,0);
/*!40000 ALTER TABLE `payroll` ENABLE KEYS */;
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