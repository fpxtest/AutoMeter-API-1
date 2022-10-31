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
-- Table structure for table `apicases_condition`


DROP TABLE IF EXISTS `apicases_condition`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apicases_condition`
(
    `id`            bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `execplanid`    bigint(20) unsigned  COMMENT '执行计划Id',
    `execplanname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '执行计划名',
    `target`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '条件目标',
    `caseid`  bigint(20) unsigned  COMMENT '用例id',
    `envassemid`  bigint(20) unsigned  COMMENT '环境组件id',
    `casedeployunitname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例所属微服务',
    `caseapiname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例所属api',
    `casename`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例名',
    `basetype`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '前置，后置',
    `conditionbasetype`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '数据库，接口',
    `conditiontype`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '数据库：mysql，oracle，sqlserver，接口：http,https,dubbo',
    `deployunitname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '包含调用接口的微服务',
    `apiname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '调用接口的api',
    `conditionvalue`  varchar(500) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '条件值，如果是数据库为sql，如果是接口为用例名',
    `conditioncaseid`  bigint(20) unsigned  COMMENT '条件值id，如果是数据库为空，如果是接口为用例id',
    `connectstr`      varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '连接字',
    `memo`          varchar(200) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '备注',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    `creator`    varchar(10) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='api用例条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_condition`
--

LOCK TABLES `apicases_condition` WRITE;
/*!40000 ALTER TABLE `apicases_condition`
    DISABLE KEYS */;
INSERT INTO `apicases_condition`
VALUES (1, 1,4,'accountservice','getlogin','测试用例名','前置','数据库','mysql','accountservice','gettest', 'inser test values(1,1)','username=root','测试下条件',
        '2019-07-01 00:00:00', '2019-07-01 00:00:00','admin');
/*!40000 ALTER TABLE `apicases_condition`
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
