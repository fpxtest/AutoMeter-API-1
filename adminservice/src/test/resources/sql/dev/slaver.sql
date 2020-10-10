-- MySQL dump 10.16  Distrib 10.1.26-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: admin_dev
-- ------------------------------------------------------
-- Server version	10.1.26-MariaDB-0+deb9u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `slaver`


DROP TABLE IF EXISTS `slaver`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slaver`
(
    `id`            bigint(20) unsigned                             NOT NULL AUTO_INCREMENT COMMENT '执行机Id',
    `slavername`         varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '执行机器名',
    `ip`          varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'ip',
    `port`          varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '端口',
    `status`          varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态，idle，running',
    `stype`          varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '执行机类型，功能机，性能机',
    `memo`      varchar(512) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '备注',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='服务器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slaver`
--

LOCK TABLES `slaver` WRITE;
/*!40000 ALTER TABLE `slaver`
    DISABLE KEYS */;
INSERT INTO `slaver`
VALUES (1, '执行机1', '192.168.1.1','8080' ,'空闲','功能机','memo',
        '2019-07-01 00:00:00', '2019-07-01 00:00:00');
/*!40000 ALTER TABLE `slaver`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2018-02-16 19:24:53
