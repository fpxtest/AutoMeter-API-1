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
-- Table structure for table `dispatch`
--

DROP TABLE IF EXISTS `dispatch`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dispatch`
(
    `id`            bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '环境Id',
    `execplanid`    bigint(20) unsigned NOT NULL COMMENT '执行计划Id',
    `execplanname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '执行计划名',
    `batchid`    bigint(20) unsigned NOT NULL COMMENT '批次Id',
    `batchname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '批次名',
    `slaverid`    bigint(20) unsigned NOT NULL COMMENT '执行机Id',
    `slavername`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '执行机名',
    `testcaseid`    bigint(20) unsigned NOT NULL COMMENT '用例Id',
    `testcasename`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例名',
    `casejmxname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'jmeter-class',
    `deployunitname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'jmeter-class',
    `expect`  varchar(500) CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'jmeter-class',
    `status`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '待分配，已分配，已结束',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dispatch`
--

LOCK TABLES `dispatch` WRITE;
/*!40000 ALTER TABLE `dispatch`
    DISABLE KEYS */;
INSERT INTO `dispatch`
VALUES (1, '功能测试环境一', '测试部门功能测试环境第一套',
        '2019-07-01 00:00:00', '2019-07-01 00:00:00');
INSERT INTO `dispatch`
VALUES (2, '功能测试环境二', '测试部门功能测试环境第二套',
        '2019-07-01 00:00:00', '2019-07-01 00:00:00');
INSERT INTO `dispatch`
VALUES (3, '预功能测试环境', '开发提交自动化测试验收环境',
        '2019-07-01 00:00:00', '2019-07-01 00:00:00');
/*!40000 ALTER TABLE `dispatch`
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
