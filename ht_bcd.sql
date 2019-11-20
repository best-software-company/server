-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: HomeTasks
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Aprova`
--

DROP TABLE IF EXISTS `Aprova`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Aprova` (
  `idUsuario` varchar(45) NOT NULL,
  `idRegra` int(11) NOT NULL,
  `estado` bit(1) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idRegra`),
  KEY `fk_Usuario_has_Regra_Regra2_idx` (`idRegra`),
  KEY `fk_Usuario_has_Regra_Usuario2_idx` (`idUsuario`),
  CONSTRAINT `fk_Usuario_has_Regra_Regra2` FOREIGN KEY (`idRegra`) REFERENCES `Regra` (`idRegra`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Regra_Usuario2` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Aprova`
--

LOCK TABLES `Aprova` WRITE;
/*!40000 ALTER TABLE `Aprova` DISABLE KEYS */;
INSERT INTO `Aprova` VALUES ('Roque007',1,_binary '');
/*!40000 ALTER TABLE `Aprova` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Casa`
--

DROP TABLE IF EXISTS `Casa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Casa` (
  `idCasa` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `aluguel` decimal(6,2) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  `foto` blob,
  PRIMARY KEY (`idCasa`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Casa`
--

LOCK TABLES `Casa` WRITE;
/*!40000 ALTER TABLE `Casa` DISABLE KEYS */;
INSERT INTO `Casa` VALUES (1,'Casa dos Roque','Casa bonita',500.00,'rua jacinto pinto',NULL);
/*!40000 ALTER TABLE `Casa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Comentario`
--

DROP TABLE IF EXISTS `Comentario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Comentario` (
  `idComentario` int(11) NOT NULL AUTO_INCREMENT,
  `texto` varchar(255) NOT NULL,
  `midia` blob,
  `data` datetime NOT NULL,
  `idTarefa` int(11) NOT NULL,
  `idUsuario` varchar(45) NOT NULL,
  PRIMARY KEY (`idComentario`,`idTarefa`,`idUsuario`),
  KEY `fk_Comentario_Tarefa1_idx` (`idTarefa`),
  KEY `fk_Comentario_Usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_Comentario_Tarefa1` FOREIGN KEY (`idTarefa`) REFERENCES `Tarefa` (`idTarefa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comentario_Usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comentario`
--

LOCK TABLES `Comentario` WRITE;
/*!40000 ALTER TABLE `Comentario` DISABLE KEYS */;
INSERT INTO `Comentario` VALUES (1,'lala',NULL,'2019-11-20 00:00:00',1,'Roque007');
/*!40000 ALTER TABLE `Comentario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Convite`
--

DROP TABLE IF EXISTS `Convite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Convite` (
  `idConvidado` varchar(45) NOT NULL,
  `idConvidante` varchar(45) NOT NULL,
  `sentido` bit(1) NOT NULL,
  `estado` bit(1) NOT NULL,
  `idCasa` int(11) NOT NULL,
  PRIMARY KEY (`idConvidado`,`idConvidante`,`idCasa`),
  KEY `fk_Usuario_has_Usuario_Usuario5_idx` (`idConvidante`),
  KEY `fk_Usuario_has_Usuario_Usuario4_idx` (`idConvidado`),
  KEY `fk_Usuario_has_Usuario_Casa1_idx` (`idCasa`),
  CONSTRAINT `fk_Usuario_has_Usuario_Casa1` FOREIGN KEY (`idCasa`) REFERENCES `Casa` (`idCasa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Usuario_Usuario4` FOREIGN KEY (`idConvidado`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Usuario_Usuario5` FOREIGN KEY (`idConvidante`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Convite`
--

LOCK TABLES `Convite` WRITE;
/*!40000 ALTER TABLE `Convite` DISABLE KEYS */;
/*!40000 ALTER TABLE `Convite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Notificacao`
--

DROP TABLE IF EXISTS `Notificacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Notificacao` (
  `idNotificacao` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` varchar(45) NOT NULL,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`idNotificacao`,`idUsuario`),
  KEY `fk_Notificacao_Usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_Notificacao_Usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notificacao`
--

LOCK TABLES `Notificacao` WRITE;
/*!40000 ALTER TABLE `Notificacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `Notificacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pagamento`
--

DROP TABLE IF EXISTS `Pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pagamento` (
  `idPagamento` int(11) NOT NULL AUTO_INCREMENT,
  `idCredor` varchar(45) NOT NULL,
  `idDevedor` varchar(45) NOT NULL,
  `valor` int(11) NOT NULL,
  `data` varchar(45) NOT NULL,
  `juros` varchar(45) NOT NULL,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`idPagamento`,`idCredor`,`idDevedor`),
  KEY `fk_Usuario_has_Usuario_Usuario2_idx` (`idDevedor`),
  KEY `fk_Usuario_has_Usuario_Usuario1_idx` (`idCredor`),
  CONSTRAINT `fk_Usuario_has_Usuario_Usuario1` FOREIGN KEY (`idCredor`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Usuario_Usuario2` FOREIGN KEY (`idDevedor`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pagamento`
--

LOCK TABLES `Pagamento` WRITE;
/*!40000 ALTER TABLE `Pagamento` DISABLE KEYS */;
INSERT INTO `Pagamento` VALUES (3,'Roque007','Roque1',500,'2019-10-02','0','aluguel outubro');
/*!40000 ALTER TABLE `Pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Regra`
--

DROP TABLE IF EXISTS `Regra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Regra` (
  `idRegra` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  `estado` bit(1) NOT NULL,
  `idUsuario` varchar(45) NOT NULL,
  `idCasa` int(11) NOT NULL,
  `data` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `valor` varchar(45) NOT NULL,
  PRIMARY KEY (`idRegra`,`idCasa`),
  KEY `fk_Regra_Usuario1_idx` (`idUsuario`),
  KEY `fk_Regra_Casa1_idx` (`idCasa`),
  CONSTRAINT `fk_Regra_Casa1` FOREIGN KEY (`idCasa`) REFERENCES `Casa` (`idCasa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Regra_Usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Regra`
--

LOCK TABLES `Regra` WRITE;
/*!40000 ALTER TABLE `Regra` DISABLE KEYS */;
INSERT INTO `Regra` VALUES (1,'Todos tem que saber cantar',_binary '\0','Roque007',1,'2019-11-20','Regra1','0');
/*!40000 ALTER TABLE `Regra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rotina`
--

DROP TABLE IF EXISTS `Rotina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Rotina` (
  `validade` date NOT NULL,
  `alternar` bit(1) NOT NULL,
  `idRotina` int(11) NOT NULL,
  PRIMARY KEY (`idRotina`),
  CONSTRAINT `fk_Rotina_Tarefa1` FOREIGN KEY (`idRotina`) REFERENCES `Tarefa` (`idTarefa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rotina`
--

LOCK TABLES `Rotina` WRITE;
/*!40000 ALTER TABLE `Rotina` DISABLE KEYS */;
/*!40000 ALTER TABLE `Rotina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tarefa`
--

DROP TABLE IF EXISTS `Tarefa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tarefa` (
  `idTarefa` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `data` varchar(45) NOT NULL,
  `valor` decimal(6,2) NOT NULL,
  `idRelator` varchar(45) NOT NULL,
  `idResponsavel` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  PRIMARY KEY (`idTarefa`),
  KEY `fk_Tarefa_Usuario1_idx` (`idRelator`),
  KEY `fk_Tarefa_Usuario2_idx` (`idResponsavel`),
  CONSTRAINT `fk_Tarefa_Usuario1` FOREIGN KEY (`idRelator`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tarefa_Usuario2` FOREIGN KEY (`idResponsavel`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tarefa`
--

LOCK TABLES `Tarefa` WRITE;
/*!40000 ALTER TABLE `Tarefa` DISABLE KEYS */;
INSERT INTO `Tarefa` VALUES (1,'Lavar louca','Lavar toda louca','2019-10-10',10.00,'Roque007','Roque007','aberta');
/*!40000 ALTER TABLE `Tarefa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Usuario` (
  `idUsuario` varchar(45) NOT NULL,
  `data` date NOT NULL,
  `genero` varchar(45) NOT NULL,
  `pontos` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `telefone` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `idCasa` int(11) DEFAULT NULL,
  `perfil` varchar(45) NOT NULL,
  `foto` blob,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_Usuario_Casa1_idx` (`idCasa`),
  CONSTRAINT `fk_Usuario_Casa1` FOREIGN KEY (`idCasa`) REFERENCES `Casa` (`idCasa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
INSERT INTO `Usuario` VALUES ('Roque007','1997-02-08','masculino',0,'Guilherme Lopes Roque','48991100802','12345','roque@email.com',1,'semteto',NULL),('Roque1','1997-02-08','masculino',0,'Guilherme Lopes Roque','48991100802','12345','roque@email.com',NULL,'semteto',NULL);
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UsuarioRotina`
--

DROP TABLE IF EXISTS `UsuarioRotina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UsuarioRotina` (
  `idUsuario` varchar(45) NOT NULL,
  `idRotina` int(11) NOT NULL,
  `data` date NOT NULL,
  `estado` bit(1) NOT NULL,
  PRIMARY KEY (`idUsuario`,`idRotina`),
  KEY `fk_Usuario_has_Rotina_Rotina1_idx` (`idRotina`),
  KEY `fk_Usuario_has_Rotina_Usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_Usuario_has_Rotina_Rotina1` FOREIGN KEY (`idRotina`) REFERENCES `Rotina` (`idRotina`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Rotina_Usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UsuarioRotina`
--

LOCK TABLES `UsuarioRotina` WRITE;
/*!40000 ALTER TABLE `UsuarioRotina` DISABLE KEYS */;
/*!40000 ALTER TABLE `UsuarioRotina` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-20  9:59:05
