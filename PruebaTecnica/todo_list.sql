-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: todo_list
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `tarea`
--

DROP TABLE IF EXISTS `tarea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarea` (
  `id_tarea` bigint NOT NULL AUTO_INCREMENT COMMENT 'Id tarea',
  `nombre` varchar(100) NOT NULL COMMENT 'Nombre de la tarea',
  `descripcion` varchar(5000) DEFAULT NULL COMMENT 'Descripción de la tarea',
  `estado` tinyint NOT NULL COMMENT 'Estado de la tarea',
  `fecha_creacion` datetime NOT NULL COMMENT 'Fecha creación registro',
  `fecha_modificacion` datetime DEFAULT NULL COMMENT 'Fecha modificacón',
  PRIMARY KEY (`id_tarea`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarea`
--

LOCK TABLES `tarea` WRITE;
/*!40000 ALTER TABLE `tarea` DISABLE KEYS */;
INSERT INTO `tarea` VALUES (4,'Tarea test','Tarea descripcion de prueba',1,'2022-01-14 10:18:33','2022-01-14 10:18:33'),(6,'sadfa dfdasf','If you\'re noticing choppy animations, and the component that\'s being collapsed has non-zero margin or padding, try wrapping the contents of your <Collapse> inside a node with no margin or padding, like the <div> in the example below. This will allow the height to be computed properly, so the animation can proceed smoothly.',3,'2022-02-16 15:16:07','2022-02-16 15:39:28'),(7,'nueva tarea','If you\'re noticing choppy animations, and the component that\'s being collapsed has non-zero margin or padding, try wrapping the contents of your <Collapse> inside a node with no margin or padding, like the <div> in the example below. This will allow the height to be computed properly, so the animation can proceed smoothly.',2,'2022-02-16 15:16:13','2022-02-16 15:49:39'),(8,'Otra tarea','If you\'re noticing choppy animations, and the component that\'s being collapsed has non-zero margin or padding, try wrapping the contents of your <Collapse> inside a node with e computed properly, so the animation can proceed smoothly.',2,'2022-02-16 15:16:23','2022-02-16 15:16:42'),(9,'Otra tarea','If you\'re noticing choppy animations, and the component that\'s being collapsed has non-zero margin or padding, try ',1,'2022-02-16 15:16:33','2022-02-16 15:39:25');
/*!40000 ALTER TABLE `tarea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT COMMENT 'Identificador usuario',
  `primer_nombre` varchar(500) NOT NULL COMMENT 'Nombre usuario',
  `fecha_creacion` datetime NOT NULL COMMENT 'Fecha registro usuario',
  `correo` varchar(100) DEFAULT NULL COMMENT 'Correo usuario',
  `password` text NOT NULL COMMENT 'Contraseña',
  `user_name` varchar(100) DEFAULT NULL COMMENT 'User name para iniciar sesión',
  `segundo_nombre` varchar(500) DEFAULT NULL COMMENT 'Segundo nombre',
  `primer_apellido` varchar(500) NOT NULL COMMENT 'Primer apellido',
  `segundo_apellido` varchar(500) DEFAULT NULL COMMENT 'Segundo apellido',
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'John','2022-01-14 05:35:45','john.pineros@artemisa.tech','$2a$10$tPlkYZU.FapZ/Fh8vGE4aONshnUmzkBoP87tQXEpCJoceyoG0RVBS','johnpiud','Fredy','Piñeros','Hernández'),(2,'John','2022-01-14 05:38:13','john.pineros@artemisa.tech','$2a$10$1YX1QCIC0z7yO0GqQjWgmOL875DpxdSHLo3Mv0EeKxuTtjLMaDigO','john','Fredy','Piñeros','Hernández'),(3,'Test','2022-01-14 09:30:05','test@test.com','$2a$10$GYy75kYdVfp/aGKCgqLPoeGdE56T/5UZysRpLhJ.lCcg/2OHF0bHO','test',NULL,'Testing',NULL),(4,'','2022-02-16 15:15:19','santiago@gmail.com','$2a$10$V5YAUsOP57WODVHnmeGAsunx/RwrBonsmjlntk8.OgC0CWZobH4jq','santiago82','Santiago','Benavides','');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_tarea`
--

DROP TABLE IF EXISTS `usuario_tarea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_tarea` (
  `id_usuario_tarea` bigint NOT NULL AUTO_INCREMENT COMMENT 'Identificador usuario tarea',
  `id_usuario` bigint NOT NULL COMMENT 'Identificador usuario',
  `id_tarea` bigint NOT NULL COMMENT 'Identificador tarea',
  PRIMARY KEY (`id_usuario_tarea`),
  KEY `usuario_tarea_FK` (`id_usuario`),
  KEY `usuario_tarea_FK_1` (`id_tarea`),
  CONSTRAINT `usuario_tarea_FK` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `usuario_tarea_FK_1` FOREIGN KEY (`id_tarea`) REFERENCES `tarea` (`id_tarea`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_tarea`
--

LOCK TABLES `usuario_tarea` WRITE;
/*!40000 ALTER TABLE `usuario_tarea` DISABLE KEYS */;
INSERT INTO `usuario_tarea` VALUES (4,1,4),(6,4,6),(7,4,7),(8,4,8),(9,4,9);
/*!40000 ALTER TABLE `usuario_tarea` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-16 16:09:17
