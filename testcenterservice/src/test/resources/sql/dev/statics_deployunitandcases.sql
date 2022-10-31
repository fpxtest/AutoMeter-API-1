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
-- Table structure for table `statics_deployunitandcases`


DROP TABLE IF EXISTS `statics_deployunitandcases`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statics_deployunitandcases`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `deployunitid`  bigint(20) unsigned NOT NULL COMMENT '执行计划id',
    `deployunitname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '计划名',
    `passrate`  double(11,2) NOT NULL COMMENT '成功率',
    `totalcases`      bigint(20) NOT NULL COMMENT '用例总数',
    `totalpasscases`      bigint(20) NOT NULL COMMENT '用例成功总数',
    `totalfailcases`      bigint(20) NOT NULL COMMENT '用例失败总数',
    `runtime`      bigint(50) NOT NULL COMMENT '运行时长',
    `statics_date`   datetime DEFAULT NOW() COMMENT '统计日期',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='api微服务用例统计报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statics_deployunitandcases`
--

LOCK TABLES `statics_deployunitandcases` WRITE;
/*!40000 ALTER TABLE `statics_deployunitandcases`
    DISABLE KEYS */;
INSERT INTO `statics_deployunitandcases`
VALUES (1, 1,1,'batch2020-10-21-tag-100',100,70,30,300,'2019-07-01 00:00:00', '2019-07-01 00:00:00','admin');
/*!40000 ALTER TABLE `statics_deployunitandcases`
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
