CREATE DATABASE  IF NOT EXISTS `booklib` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `booklib`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: booklib
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(10) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (2,'nugi','nugi@gmail.com','jMp5jJDyOC5L+QqOSx0iAQ=='),(7,'admin','admin@gmail.com','QLtp36FYi99qs/o9FH72oQ==');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `id` varchar(8) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `contact` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES ('A001','Martin Wickramasinghe','0719876543'),('A002','Gunadasa Amarasekara','0774567890'),('A003','K. Jayathilaka','0723456789'),('A004','T.B. Ilangaratne','0712345678'),('A005','Ediriweera Sarachchandra','0756789012'),('A006','Chandraratna Bandara','0765432109'),('A007','W.A. Silva','0789012345'),('A008','Simon Nawagattegama','0743210987'),('A009','Ajith Thilakasena','0732109876'),('A010','Siri Gunasinghe','0798765432'),('A011','Saman Wickramaarachchi','0709876543'),('A012','Daya Dissanayake','0787654321'),('A013','Rohana Wijeweera','0712340987'),('A014','E.K. Jayawardena','0775432167'),('A015','Vijitha Weerasinghe','0793216547'),('EA001','George Orwell','+442071234567'),('EA002','J.R.R. Tolkien','+442072345678'),('EA003','Jane Austen','+442073456789'),('EA004','Charles Dickens','+442074567890'),('EA005','William Shakespeare','+442075678901'),('EA006','Agatha Christie','+442076789012'),('EA007','Virginia Woolf','+442077890123'),('EA008','Mary Shelley','+442078901234'),('EA009','Ernest Hemingway','+13055550123'),('EA010','F. Scott Fitzgerald','+12125550124'),('EA011','George R.R. Martin','+15055550125'),('EA012','J.K. Rowling','+442079012345'),('EA013','Stephen King','+12075550126'),('EA014','Neil Gaiman','+442070123456'),('EA015','Dan Brown','+16035550127');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` varchar(8) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `isbn` varchar(13) DEFAULT NULL,
  `availability_status` tinyint DEFAULT NULL,
  `publisher_id` varchar(4) DEFAULT NULL,
  `category_id` varchar(6) NOT NULL,
  `author_id` varchar(8) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn_UNIQUE` (`isbn`),
  KEY `book_ibfk_1` (`publisher_id`),
  KEY `fk_book_category1_idx` (`category_id`),
  KEY `fk_book_author1_idx` (`author_id`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`),
  CONSTRAINT `fk_book_author1` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
  CONSTRAINT `fk_book_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('24000001','Gamperaliya','9789553118424',1,'P001','FIC-01','A001'),('24000002','Viragaya','9789553118431',0,'P002','FIC-01','A001'),('24000003','Madol Duwa','9789553118448',1,'P003','ADV-07','A001'),('24000004','Yuganthaya','9789553118455',0,'P004','FIC-01','A001'),('24000005','Ape Gama','9789553118462',0,'P005','FIC-01','A001'),('24000006','Karumakkarayo','9789553118479',0,'P001','DRM-12','A002'),('24000007','Yali Upannemi','9789553118486',0,'P002','FIC-01','A002'),('24000008','Gandhabba Apadanaya','9789553118493',0,'P003','FIC-01','A002'),('24000009','Malagiya Aththo','9789553118509',1,'P004','FIC-01','A002'),('24000010','Depa Nokiya Heta','9789553118516',0,'P005','FIC-01','A003'),('24000011','Malavunge Avurudu Da','9789553118523',1,'P006','FIC-01','A004'),('24000012','Amba Yaluwo','9789553118530',1,'P007','FIC-01','A004'),('24000013','Sasara Gira','9789553118547',1,'P008','FIC-01','A004'),('24000014','Malagiya Miniyo','9789553118554',1,'P009','FIC-01','A005'),('24000015','Pemato Jayati Soko','9789553118561',1,'P010','DRM-12','A005'),('24000016','Ratnapala','9789553118578',1,'P011','FIC-01','A007'),('24000017','Hingana Kolla','9789553118585',1,'P012','FIC-01','A007'),('24000018','Lakshmi Gehenu','9789553118592',1,'P013','FIC-01','A007'),('24000019','Vijayaba Kollaya','9789553118608',1,'P014','HIS-03','A007'),('24000020','Kelani Palama','9789553118615',1,'P015','FIC-01','A007'),('24000021','Manosandha','9789553118622',1,'P016','FIC-01','A005'),('24000022','Malwarige Awurudu Da','9789553118639',1,'P017','FIC-01','A004'),('24000023','Ran Salu','9789553118646',1,'P018','FIC-01','A004'),('24000024','Sudu Saha Kalu','9789553118653',1,'P019','FIC-01','A001'),('24000025','Kaliyugaya','9789553118660',1,'P020','FIC-01','A002'),('24000026','Pitagamkarayo','9789553118677',1,'P001','FIC-01','A001'),('24000027','Ahasin Polowata','9789553118684',1,'P002','FIC-01','A008'),('24000028','Suba Saha Yasa','9789553118691',1,'P003','FIC-01','A008'),('24000029','Palagiya Kurullo','9789553118707',1,'P004','FIC-01','A009'),('24000030','Hevanella','9789553118714',1,'P005','FIC-01','A009'),('24000031','Mandarama','9789553118721',1,'P006','POE-10','A010'),('24000032','Abhinikmana','9789553118738',1,'P007','POE-10','A010'),('24000033','Ratu Kekulu','9789553118745',1,'P008','FIC-01','A011'),('24000034','Wasana','9789553118752',1,'P009','FIC-01','A012'),('24000035','Anantha','9789553118769',1,'P010','FIC-01','A012'),('24000036','Kalu Kumaraya','9789553118776',1,'P011','FIC-01','A007'),('24000037','Radala Piliruwa','9789553118783',1,'P012','FIC-01','A007'),('24000038','Daetha Kandulu','9789553118790',1,'P013','FIC-01','A004'),('24000039','Mahagedara','9789553118806',1,'P014','FIC-01','A004'),('24000040','Wahalak Nathi Geyak','9789553118813',1,'P015','FIC-01','A015'),('24000041','Paradeesaya','9789553118820',1,'P016','FIC-01','A002'),('24000042','Asathya Kathawa','9789553118837',1,'P017','FIC-01','A002'),('24000043','Minisa Saha Kaputa','9789553118844',1,'P018','FIC-01','A008'),('24000044','Bhava Geetha','9789553118851',1,'P019','POE-10','A010'),('24000045','Bhava Taranaya','9789553118868',1,'P020','FIC-01','A005'),('24000046','Sath Samudura','9789553118875',1,'P001','ADV-07','A007'),('24000047','Veera Puran Appu','9789553118882',1,'P002','HIS-03','A014'),('24000048','Magul Kema','9789553118899',1,'P003','FIC-01','A004'),('24000049','Weda Hamine','9789553118905',1,'P004','FIC-01','A007'),('24000050','Mayura Sandeshaya','9789553118912',1,'P005','POE-10','A005'),('24000051','Devana Lova','9789553118929',1,'P006','FIC-01','A013'),('24000052','Dunhinda Sannapuwa','9789553118936',1,'P007','HIS-03','A011'),('24000053','Golu Hadawatha','9789553118943',1,'P008','ROM-06','A005'),('24000054','Diya Kumari','9789553118950',1,'P009','FIC-01','A007'),('24000055','Mathaka Walawwa','9789553118967',1,'P010','FIC-01','A007'),('24000056','Sudu Sevanali','9789553118974',1,'P011','FIC-01','A009'),('24000057','Bawa Tharanaya','9789553118981',1,'P012','DRM-12','A005'),('24000058','Kele Handa','9789553118998',1,'P013','FIC-01','A001'),('24000059','Mahaweli Gammanaya','9789553119001',1,'P014','FIC-01','A002'),('24000060','Sansaranyaye Dharuwo','9789553119018',1,'P015','FIC-01','A014'),('24000061','Theravili','9789553119025',1,'P016','FIC-01','A008'),('24000062','Pavana Yamak','9789553119032',1,'P017','POE-10','A010'),('24000063','Dedunna','9789553119049',1,'P018','FIC-01','A012'),('24000064','Amaraneeya Dulada','9789553119056',1,'P019','ROM-06','A004'),('24000065','Wes Muhunu','9789553119063',1,'P020','DRM-12','A005'),('24000066','Maha Viyavula','9789553119070',1,'P001','FIC-01','A002'),('24000067','Kali Yugaya','9789553119087',1,'P002','FIC-01','A001'),('24000068','Randiya Gini','9789553119094',1,'P003','FIC-01','A007'),('24000069','Samudra Devi','9789553119100',1,'P004','FIC-01','A007'),('24000070','Giri Shikharaye','9789553119117',1,'P005','ADV-07','A015'),('24000071','Bambaru Ewith','9789553119124',1,'P006','FIC-01','A003'),('24000072','Lasanda','9789553119131',1,'P007','ROM-06','A004'),('24000073','Sudu Hansayo','9789553119148',1,'P008','POE-10','A010'),('24000074','Mage Katha','9789553119155',1,'P009','BIO-04','A013'),('24000075','Magul Gedara','9789553119162',1,'P010','DRM-12','A004'),('24000076','Kadulla','9789553119179',1,'P011','FIC-01','A009'),('24000077','Diyamanthi','9789553119186',1,'P012','FIC-01','A007'),('24000078','Rekawa','9789553119193',1,'P013','FIC-01','A005'),('24000079','Sanda Diya Mankada','9789553119209',1,'P014','FIC-01','A001'),('24000080','Wasana Wahi','9789553119216',1,'P015','FIC-01','A012'),('24000081','Aparadaya saha Danduwama','9789553119223',1,'P016','FIC-01','A002'),('24000082','Magul Kaema','9789553119230',1,'P017','FIC-01','A004'),('24000083','Mihikatha','9789553119247',1,'P018','FIC-01','A008'),('24000084','Sarama','9789553119254',1,'P019','FIC-01','A005'),('24000085','Peraliya','9789553119261',1,'P020','FIC-01','A001'),('24000086','Mahasara','9789553119278',1,'P001','FIC-01','A001'),('24000087','Ginidel','9789553119285',1,'P002','FIC-01','A007'),('24000088','Sada Mehe','9789553119292',1,'P003','FIC-01','A004'),('24000089','Janamadyama Viplavaya','9789553119308',1,'P004','EDU-15','A013'),('24000090','Daskam','9789553119315',1,'P005','POE-10','A010'),('24000091','Panamaliye','9789553119322',1,'P006','FIC-01','A008'),('24000092','Manuthapaya','9789553119339',1,'P007','FIC-01','A002'),('24000093','Sulanga Wage Avidin','9789553119346',1,'P008','FIC-01','A015'),('24000094','Magul Gedara','9789553119353',1,'P009','DRM-12','A005'),('24000095','Mangul Birinda','9789553119360',1,'P010','FIC-01','A007'),('24000096','Rathu Rosa Mal','9789553119377',1,'P011','ROM-06','A004'),('24000097','Mawatha','9789553119384',1,'P012','FIC-01','A009'),('24000098','Sahurda Sitha','9789553119391',1,'P013','FIC-01','A001'),('24000099','Diya Suwanda','9789553119407',1,'P014','FIC-01','A012'),('24000100','Maha Gama','9789553119414',1,'P015','FIC-01','A001'),('24000101','Api Okkoma Eka Hamarai','9789553119421',1,'P016','FIC-01','A013'),('24000102','Paduma','9789553119438',1,'P017','FIC-01','A007'),('24000103','Handak Nethi Re','9789553119445',1,'P018','POE-10','A010'),('24000104','Sathyaya Soya','9789553119452',1,'P019','PHI-08','A002'),('24000105','Mahacharya','9789553119469',1,'P020','BIO-04','A014'),('24000106','Senehasa Nihanda','9789553119476',1,'P001','ROM-06','A004'),('24000107','Adaraneeya Kathawak','9789553119483',1,'P002','ROM-06','A005'),('24000108','Kawluwa','9789553119490',1,'P003','FIC-01','A008'),('24000109','Pawkarayo','9789553119506',1,'P004','FIC-01','A002'),('24000110','Maha Wasanthaya','9789553119513',1,'P005','FIC-01','A015'),('24000111','Prabuddha','9789553119520',1,'P006','PHI-08','A010'),('24000112','Abhirahas Charikawa','9789553119537',1,'P007','ADV-07','A007'),('24000113','Nimnayaka Husmaru','9789553119544',1,'P008','POE-10','A010'),('24000114','Wasanthaye Mal','9789553119551',1,'P009','FIC-01','A009'),('24000115','Sihina Lowak','9789553119568',1,'P010','FIC-01','A012'),('24000116','Maha Ravana','9789553119575',1,'P011','HIS-03','A014'),('24000117','Samudra Devi','9789553119582',1,'P012','FIC-01','A007'),('24000118','Dharmasoka','9789553119599',1,'P013','HIS-03','A001'),('24000119','Sanda Kinduri','9789553119605',1,'P014','FIC-01','A004'),('24000120','Malsara','9789553119612',1,'P015','FIC-01','A015'),('24001001','1984','9780451524935',1,'P001','FIC-01','EA001'),('24001002','Animal Farm','9780452284241',1,'P002','FIC-01','EA001'),('24001003','The Hobbit','9780261103283',1,'P003','FAN-13','EA002'),('24001004','The Lord of the Rings','9780618640157',1,'P004','FAN-13','EA002'),('24001005','Pride and Prejudice','9780141439518',1,'P005','ROM-06','EA003'),('24001006','Emma','9780141439587',1,'P006','ROM-06','EA003'),('24001007','Great Expectations','9780141439563',1,'P007','FIC-01','EA004'),('24001008','Oliver Twist','9780141439747',1,'P008','FIC-01','EA004'),('24001009','Hamlet','9780743477123',1,'P009','DRM-12','EA005'),('24001010','Macbeth','9780743477109',1,'P010','DRM-12','EA005'),('24001011','Murder on the Orient Express','9780062693662',1,'P011','MYS-05','EA006'),('24001012','And Then There Were None','9780062073488',1,'P012','MYS-05','EA006'),('24001013','Mrs Dalloway','9780156628709',1,'P013','FIC-01','EA007'),('24001014','To the Lighthouse','9780156907392',1,'P014','FIC-01','EA007'),('24001015','Frankenstein','9780141439471',1,'P015','HOR-09','EA008'),('24001016','The Old Man and the Sea','9780684801223',1,'P016','FIC-01','EA009'),('24001017','The Great Gatsby','9780743273565',1,'P017','FIC-01','EA010'),('24001018','A Game of Thrones','9780553593716',1,'P018','FAN-13','EA011'),('24001019','Harry Potter and the Philosophers Stone','9780747532699',1,'P019','FAN-13','EA012'),('24001020','The Shining','9780307743657',1,'P020','HOR-09','EA013'),('24001021','American Gods','9780380789030',1,'P001','FAN-13','EA014'),('24001022','The Da Vinci Code','9780307474278',1,'P002','THR-11','EA015'),('24001023','A Tale of Two Cities','9780141439600',1,'P003','FIC-01','EA004'),('24001024','The Silmarillion','9780261102736',1,'P004','FAN-13','EA002'),('24001025','Sense and Sensibility','9780141439662',1,'P005','ROM-06','EA003');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_borrow`
--

DROP TABLE IF EXISTS `book_borrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_borrow` (
  `id` varchar(12) NOT NULL,
  `book_id` varchar(8) DEFAULT NULL,
  `member_id` varchar(6) DEFAULT NULL,
  `borrowed_date` date DEFAULT NULL,
  `isReturned` tinyint(1) DEFAULT NULL,
  `returnDate` date DEFAULT NULL,
  `returnedDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `book_borrow_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `book_borrow_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_borrow`
--

LOCK TABLES `book_borrow` WRITE;
/*!40000 ALTER TABLE `book_borrow` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_borrow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` varchar(6) NOT NULL,
  `name` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('ADV-07','Adventure'),('ART-14','Art'),('BIO-04','Biography'),('BUS-17','Business'),('DRM-12','Drama'),('EDU-15','Education'),('FAN-13','Fantasy'),('FIC-01','Fiction'),('HEA-20','Health & Wellness'),('HIS-03','History'),('HOR-09','Horror'),('MYS-05','Mystery'),('PHI-08','Philosophy'),('POE-10','Poetry'),('REL-16','Religion'),('ROM-06','Romance'),('SCI-02','Science Fiction'),('SPO-18','Sports'),('TEC-19','Technology'),('THR-11','Thriller');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fine`
--

DROP TABLE IF EXISTS `fine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` decimal(10,2) DEFAULT NULL,
  `date_count` smallint DEFAULT NULL,
  `book_borrow_id` varchar(12) NOT NULL,
  `admin_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`),
  KEY `fk_fine_book_borrow1_idx` (`book_borrow_id`),
  CONSTRAINT `fine_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
  CONSTRAINT `fk_fine_book_borrow1` FOREIGN KEY (`book_borrow_id`) REFERENCES `book_borrow` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fine`
--

LOCK TABLES `fine` WRITE;
/*!40000 ALTER TABLE `fine` DISABLE KEYS */;
/*!40000 ALTER TABLE `fine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` varchar(6) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `address` text,
  `email` varchar(50) DEFAULT NULL,
  `contact` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher` (
  `id` varchar(4) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `location` text,
  `contact` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` VALUES ('P001','Sarasavi Publishers','Colombo','0112345678'),('P002','M. D. Gunasena','Colombo','0118765432'),('P003','Sooriya Publishers','Kandy','0817654321'),('P004','Godage Publishers','Colombo','0117654321'),('P005','Dayawansa Jayakody','Nugegoda','0115566778'),('P006','Vijitha Yapa Publications','Colombo','0116789012'),('P007','Fast Publishers','Negombo','0312345678'),('P008','Neptune Publications','Galle','0912345678'),('P009','Samanthi Publishers','Kurunegala','0378765432'),('P010','Samudra Publishers','Matara','0419876543'),('P011','Wisdom Publishers','Colombo','0113456789'),('P012','Golden Pages','Jaffna','0211234567'),('P013','Rathna Publishers','Colombo','0112233445'),('P014','Everest Publications','Kandy','0812345678'),('P015','Sunshine Books','Galle','0918765432'),('P016','Lake House Publishing','Colombo','0119988776'),('P017','Modern Publishers','Kurunegala','0371234567'),('P018','Rainbow Publications','Matara','0417654321'),('P019','New Horizon Publishers','Negombo','0319876543'),('P020','Pearl Publications','Jaffna','0219876543');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-07 22:02:54
