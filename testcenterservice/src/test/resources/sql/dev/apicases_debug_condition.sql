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
-- Table structure for table `apicases_debug_condition`


DROP TABLE IF EXISTS `apicases_debug_condition`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apicases_debug_condition`
(
    `id`            bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `deployunitid`    bigint(20) unsigned  COMMENT '微服务Id',
    `deployunitname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '微服务',
    `caseid`  bigint(20) unsigned  COMMENT '用例id',
    `casename`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例名',
    `conditionname`  varchar(500) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '条件名',
    `conditionid`  bigint(20) unsigned  COMMENT '条件id',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    `creator`    varchar(10) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='用例调试全局条件表';

