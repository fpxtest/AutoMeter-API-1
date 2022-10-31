-- MySQL dump 10.13  Distrib 8.0.25, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: testcenter
-- ------------------------------------------------------
-- Server version	5.7.28

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
-- Table structure for table `account`
--
create database if NOT EXISTS testcenter default character set utf8mb4 collate utf8mb4_bin;

use testcenter;

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `register_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `login_time` datetime DEFAULT NULL COMMENT '上一次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_account_name` (`name`),
  UNIQUE KEY `ix_account_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'admin@qq.com','admin','$2a$10$wg0f10n.30UbU.9hPpucbef/ya62LdTKs72xJfjxvTFsL0Xaewbra','2019-07-01 00:00:00','2022-10-30 15:06:28');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_role`
--

DROP TABLE IF EXISTS `account_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_role` (
  `account_id` bigint(20) unsigned NOT NULL COMMENT '用户Id',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色Id',
  PRIMARY KEY (`account_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `account_role_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `account_role_fk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_role`
--

LOCK TABLES `account_role` WRITE;
/*!40000 ALTER TABLE `account_role` DISABLE KEYS */;
INSERT INTO `account_role` VALUES (1,1);
/*!40000 ALTER TABLE `account_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api`
--

DROP TABLE IF EXISTS `api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `api` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT 'DeployUnitId',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微服务名',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '接口名',
  `visittype` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '访问方式，字典表获取',
  `apistyle` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'restful,普通方式',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'url访问路径',
  `requestcontenttype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '请求数据格式，form表单，json',
  `responecontenttype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '响应数据格式，form表单，json',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `requesttype` varchar(20) DEFAULT 'Body传值' COMMENT '请求传值方式',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微服务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api`
--

LOCK TABLES `api` WRITE;
/*!40000 ALTER TABLE `api` DISABLE KEYS */;
/*!40000 ALTER TABLE `api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_casedata`
--

DROP TABLE IF EXISTS `api_casedata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `api_casedata` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `apiparam` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api参数',
  `apiparamvalue` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例参数值',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api属性类型，header，body',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `paramstype` varchar(20) DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_casedata`
--

LOCK TABLES `api_casedata` WRITE;
/*!40000 ALTER TABLE `api_casedata` DISABLE KEYS */;
/*!40000 ALTER TABLE `api_casedata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_params`
--

DROP TABLE IF EXISTS `api_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `api_params` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiId',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api名',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '微服务Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微服务名',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api属性类型，header，body',
  `keyname` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'key名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `keydefaultvalue` text COMMENT 'Key默认值',
  `keytype` varchar(20) DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api参数表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_params`
--

LOCK TABLES `api_params` WRITE;
/*!40000 ALTER TABLE `api_params` DISABLE KEYS */;
/*!40000 ALTER TABLE `api_params` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apicases`
--

DROP TABLE IF EXISTS `apicases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'API',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '微服务id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微服务',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `casejmxname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例jmx名，和jmx文件名对齐',
  `casetype` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '类型，功能，性能',
  `threadnum` bigint(20) unsigned NOT NULL COMMENT '线程数',
  `loops` bigint(20) unsigned NOT NULL COMMENT '循环数',
  `casecontent` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例内容，以英文逗号分开，提供jar获取自定义期望结果A：1的值，入参为冒号左边的内容',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '期望值',
  `middleparam` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '中间变量',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `level` bigint(20) unsigned NOT NULL COMMENT '优先级',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases`
--

LOCK TABLES `apicases` WRITE;
/*!40000 ALTER TABLE `apicases` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apicases_assert`
--

DROP TABLE IF EXISTS `apicases_assert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_assert` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '断言Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `asserttype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言类型',
  `assertsubtype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言子类型',
  `assertvalues` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `expression` varchar(20) DEFAULT NULL COMMENT '断言表达式',
  `assertcondition` varchar(20) DEFAULT NULL COMMENT '断言条件',
  `assertvaluetype` varchar(20) DEFAULT NULL COMMENT '断言值类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='断言表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_assert`
--

LOCK TABLES `apicases_assert` WRITE;
/*!40000 ALTER TABLE `apicases_assert` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases_assert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apicases_condition`
--

DROP TABLE IF EXISTS `apicases_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_condition` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `execplanid` bigint(20) unsigned DEFAULT NULL COMMENT '执行计划Id',
  `execplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行计划名',
  `target` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件目标',
  `caseid` bigint(20) unsigned DEFAULT NULL COMMENT '用例id',
  `envassemid` bigint(20) unsigned DEFAULT NULL COMMENT '环境组件id',
  `casedeployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例所属微服务',
  `caseapiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例所属api',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `basetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '前置，后置',
  `conditionbasetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据库，接口',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据库：mysql，oracle，sqlserver，接口：http,https,dubbo',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '包含调用接口的微服务',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '调用接口的api',
  `conditionvalue` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件值，如果是数据库为sql，如果是接口为用例名',
  `conditioncaseid` bigint(20) unsigned DEFAULT NULL COMMENT '条件值id，如果是数据库为空，如果是接口为用例id',
  `connectstr` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '连接字',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_condition`
--

LOCK TABLES `apicases_condition` WRITE;
/*!40000 ALTER TABLE `apicases_condition` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases_condition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apicases_condition_report`
--

DROP TABLE IF EXISTS `apicases_condition_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_condition_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchid` bigint(20) unsigned NOT NULL COMMENT '批次id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '前置，后置',
  `casetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '功能，性能',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `errorinfo` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '错误信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例前后置条件运行报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_condition_report`
--

LOCK TABLES `apicases_condition_report` WRITE;
/*!40000 ALTER TABLE `apicases_condition_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases_condition_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apicases_debug_condition`
--

DROP TABLE IF EXISTS `apicases_debug_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_debug_condition` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitid` bigint(20) unsigned DEFAULT NULL COMMENT '微服务Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微服务',
  `caseid` bigint(20) unsigned DEFAULT NULL COMMENT '用例id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `conditionname` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件名',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '条件id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COMMENT='用例调试全局条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_debug_condition`
--

--
-- Table structure for table `apicases_performanceapdex`
--

DROP TABLE IF EXISTS `apicases_performanceapdex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_performanceapdex` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `apdex` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'apdex',
  `toleration` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'toleration',
  `frustration` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'frustration',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例性能apdex表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_performanceapdex`
--

LOCK TABLES `apicases_performanceapdex` WRITE;
/*!40000 ALTER TABLE `apicases_performanceapdex` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases_performanceapdex` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apicases_performancestatistics`
--

DROP TABLE IF EXISTS `apicases_performancestatistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_performancestatistics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `runtime` double(11,2) NOT NULL COMMENT '运行时长,秒',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `samples` bigint(20) unsigned NOT NULL COMMENT '样本',
  `errorcount` bigint(20) unsigned NOT NULL COMMENT '错误次数',
  `errorrate` double(11,2) NOT NULL COMMENT '错误率',
  `average` double(11,2) NOT NULL COMMENT '平均数',
  `min` double(11,2) NOT NULL COMMENT '最小值',
  `max` double(11,2) NOT NULL COMMENT '最大值',
  `median` double(11,2) NOT NULL COMMENT '中间值',
  `nzpct` double(11,2) NOT NULL COMMENT '90pct',
  `nfpct` double(11,2) NOT NULL COMMENT '95pct',
  `nnpct` double(11,2) NOT NULL COMMENT '99pct',
  `tps` double(11,2) NOT NULL COMMENT 'tps',
  `receivekbsec` double(11,2) NOT NULL COMMENT '每秒接受Kb数',
  `sendkbsec` double(11,2) NOT NULL COMMENT '每秒发送Kb数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例性能统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_performancestatistics`
--

LOCK TABLES `apicases_performancestatistics` WRITE;
/*!40000 ALTER TABLE `apicases_performancestatistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases_performancestatistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apicases_report`
--

DROP TABLE IF EXISTS `apicases_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `respone` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '返回结果',
  `assertvalue` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '断言详细经过',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `expect` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '期望值',
  `errorinfo` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '错误信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `requestheader` text COMMENT '请求头数据',
  `requestdatas` text COMMENT '请求数据',
  `url` varchar(200) DEFAULT NULL COMMENT '地址',
  `requestmethod` varchar(20) DEFAULT NULL COMMENT '请求方式',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_report`
--

LOCK TABLES `apicases_report` WRITE;
/*!40000 ALTER TABLE `apicases_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apicases_report_performance`
--

DROP TABLE IF EXISTS `apicases_report_performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_report_performance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `respone` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '返回结果',
  `assertvalue` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '断言详细经过',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `expect` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '期望值',
  `errorinfo` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '错误信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `requestheader` text COMMENT '请求头数据',
  `requestdatas` text COMMENT '请求数据',
  `url` varchar(200) DEFAULT NULL COMMENT '地址',
  `requestmethod` varchar(20) DEFAULT NULL COMMENT '请求方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_report_performance`
--

LOCK TABLES `apicases_report_performance` WRITE;
/*!40000 ALTER TABLE `apicases_report_performance` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases_report_performance` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `apicases_reportstatics`
--

DROP TABLE IF EXISTS `apicases_reportstatics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_reportstatics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发单单元id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `totalcases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalpasscases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalfailcases` bigint(20) NOT NULL COMMENT '用例总数',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api计划批量用例统计报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_reportstatics`
--

LOCK TABLES `apicases_reportstatics` WRITE;
/*!40000 ALTER TABLE `apicases_reportstatics` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases_reportstatics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apicases_variables`
--

DROP TABLE IF EXISTS `apicases_variables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apicases_variables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `variablesid` bigint(20) unsigned NOT NULL COMMENT '变量Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `deployunitname` varchar(64) DEFAULT NULL COMMENT '微服务',
  `apiname` varchar(64) DEFAULT NULL COMMENT 'api',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT 'deployunitid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_variables`
--

LOCK TABLES `apicases_variables` WRITE;
/*!40000 ALTER TABLE `apicases_variables` DISABLE KEYS */;
/*!40000 ALTER TABLE `apicases_variables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condition_api`
--

DROP TABLE IF EXISTS `condition_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `condition_api` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '条件id',
  `deployunitid` bigint(20) unsigned DEFAULT NULL COMMENT '微服务id',
  `caseid` bigint(20) unsigned DEFAULT NULL COMMENT '接口caseid',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `apiid` int(11) NOT NULL COMMENT 'apiid',
  `conditionname` varchar(64) DEFAULT NULL COMMENT '条件名',
  `deployunitname` varchar(64) DEFAULT NULL COMMENT '微服务名',
  `apiname` varchar(64) DEFAULT NULL COMMENT 'api名',
  `casename` varchar(64) DEFAULT NULL COMMENT '接口名',
  `subconditionname` varchar(64) DEFAULT NULL COMMENT '子条件名',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='接口条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condition_api`
--

LOCK TABLES `condition_api` WRITE;
/*!40000 ALTER TABLE `condition_api` DISABLE KEYS */;
/*!40000 ALTER TABLE `condition_api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condition_db`
--

DROP TABLE IF EXISTS `condition_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `condition_db` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '条件id',
  `enviromentid` bigint(20) unsigned DEFAULT NULL COMMENT '环境id',
  `dbtype` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据库类型',
  `dbcontent` text COMMENT 'db执行内容',
  `connectstr` varchar(200) DEFAULT NULL COMMENT 'db连接字',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `subconditionname` varchar(64) DEFAULT NULL COMMENT '子条件名',
  `assembleid` bigint(20) unsigned DEFAULT NULL COMMENT '组件id',
  `assemblename` varchar(64) DEFAULT NULL COMMENT '组件名',
  `conditionname` varchar(64) DEFAULT NULL COMMENT '条件名',
  `enviromentname` varchar(64) DEFAULT NULL COMMENT '环境名',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='db条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condition_db`
--

LOCK TABLES `condition_db` WRITE;
/*!40000 ALTER TABLE `condition_db` DISABLE KEYS */;
/*!40000 ALTER TABLE `condition_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condition_delay`
--

DROP TABLE IF EXISTS `condition_delay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `condition_delay` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '父条件id',
  `conditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '父条件名',
  `subconditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '子条件名',
  `delaytime` bigint(20) unsigned DEFAULT NULL COMMENT '延时时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='延时子条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condition_delay`
--

LOCK TABLES `condition_delay` WRITE;
/*!40000 ALTER TABLE `condition_delay` DISABLE KEYS */;
/*!40000 ALTER TABLE `condition_delay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condition_order`
--

DROP TABLE IF EXISTS `condition_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `condition_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '父条件id',
  `subconditionid` bigint(20) unsigned DEFAULT NULL COMMENT '子条件id',
  `subconditiontype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '子条件类型',
  `subconditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '子条件名',
  `conditionorder` bigint(20) unsigned DEFAULT NULL COMMENT '条件顺序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `conditionname` varchar(64) DEFAULT NULL COMMENT '父条件名',
  `orderstatus` varchar(20) DEFAULT NULL COMMENT '顺序状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='条件顺序表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condition_order`
--

LOCK TABLES `condition_order` WRITE;
/*!40000 ALTER TABLE `condition_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `condition_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condition_script`
--

DROP TABLE IF EXISTS `condition_script`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `condition_script` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) unsigned DEFAULT NULL COMMENT '条件id，只取类型为用例',
  `script` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '脚本',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `conditionname` varchar(64) DEFAULT NULL COMMENT '条件名',
  `subconditionname` varchar(64) DEFAULT NULL COMMENT '子条件名',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='脚本条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condition_script`
--

LOCK TABLES `condition_script` WRITE;
/*!40000 ALTER TABLE `condition_script` DISABLE KEYS */;
/*!40000 ALTER TABLE `condition_script` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dbcondition_variables`
--

DROP TABLE IF EXISTS `dbcondition_variables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dbcondition_variables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `dbconditionid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `dbconditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `variablesid` bigint(20) unsigned NOT NULL COMMENT '变量Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `fieldname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '字段名',
  `roworder` bigint(20) unsigned NOT NULL COMMENT '行序号',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据库条件变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dbcondition_variables`
--

LOCK TABLES `dbcondition_variables` WRITE;
/*!40000 ALTER TABLE `dbcondition_variables` DISABLE KEYS */;
/*!40000 ALTER TABLE `dbcondition_variables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dbvariables`
--

DROP TABLE IF EXISTS `dbvariables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dbvariables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `dbvariablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `variablesdes` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量描述',
  `valuetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量值类型',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据库变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dbvariables`
--

LOCK TABLES `dbvariables` WRITE;
/*!40000 ALTER TABLE `dbvariables` DISABLE KEYS */;
/*!40000 ALTER TABLE `dbvariables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deployunit`
--

DROP TABLE IF EXISTS `deployunit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deployunit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微服务名',
  `protocal` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '协议',
  `port` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '访问端口',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `baseurl` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '基本路径',
  `projectid` bigint(20) unsigned DEFAULT NULL COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微服务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deployunit`
--

LOCK TABLES `deployunit` WRITE;
/*!40000 ALTER TABLE `deployunit` DISABLE KEYS */;
/*!40000 ALTER TABLE `deployunit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dictionary`
--

DROP TABLE IF EXISTS `dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dictionary` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `dicname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '字典类名',
  `diccode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典编码',
  `dicitemname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项名',
  `dicitmevalue` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COMMENT='字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dictionary`
--

LOCK TABLES `dictionary` WRITE;
/*!40000 ALTER TABLE `dictionary` DISABLE KEYS */;
INSERT INTO `dictionary` VALUES (1,'测试环境','testenviroment1','功能测试环境1','te112','2019-07-01 00:00:00','2020-04-21 17:04:47'),(2,'测试环境','testenviroment','功能测试环境2','te2','2019-07-01 00:00:00','2021-10-10 10:51:08'),(5,'http请求方式','httpmethod','查询','get','2020-04-17 17:57:30','2021-10-10 10:55:55'),(6,'http请求方式','httpmethod','提交数据','post','2020-04-17 17:59:02','2020-11-15 15:11:35'),(9,'测试环境','测试环境','测试环境','测试环境','2020-04-20 15:42:10','2021-09-24 16:09:53'),(17,'访问方式','httpvisittype','http访问方式','GET','2020-05-18 21:05:24','2021-09-24 16:09:55'),(18,'访问方式','httpvisittype','http访问方式','POST','2020-05-18 21:05:55','2021-09-24 16:09:57'),(19,'服务器作用','machineuse','部署','部署微服务','2020-09-09 12:26:21','2020-09-09 12:09:53'),(20,'服务器作用','machineuse','执行测试','功能测试用例执行机','2020-09-09 12:27:40','2020-09-09 12:09:26'),(21,'服务器作用','machineuse','执行测试','性能测试执行机','2020-09-09 12:29:25','2021-09-24 16:09:01'),(27,'数据库类型','DBType','用例前后置数据库类型','Mysql','2020-11-02 08:29:29','2021-10-10 10:56:03'),(28,'数据库类型','DBType','用例前后置数据库类型','Oracle','2020-11-02 08:30:29','2021-09-24 16:09:01'),(29,'数据库类型','DBType','用例前后置数据库类型','Sqlserver','2020-11-02 08:31:05','2021-09-24 16:09:03'),(34,'环境部署内容','machinedeploy','数据库','mysql','2020-11-06 15:43:18','2021-09-24 16:09:06'),(35,'环境部署内容','machinedeploy','数据库','oracle','2020-11-06 15:43:36','2021-09-24 16:09:08'),(39,'api请求格式','requestcontentype','请求数据格式','Form表单','2020-11-10 08:43:55','2021-09-24 16:09:15'),(40,'api请求格式','requestcontentype','请求数据格式','JSON','2020-11-10 08:44:19','2021-09-24 16:09:17'),(41,'api响应格式','responecontenttype','响应数据格式','json','2020-11-10 08:50:28','2021-09-24 16:09:19'),(42,'api响应格式','responecontenttype','响应数据格式','html','2020-11-10 08:55:14','2021-09-24 16:09:33'),(43,'http请求方式','httpmethod','更新','put','2020-11-15 15:41:55','2021-09-24 16:09:35'),(44,'http请求方式','httpmethod','删除','delete','2020-11-15 15:42:14','2021-09-24 16:09:37'),(45,'访问方式','httpvisittype','更新','PUT','2020-11-15 15:47:03','2021-09-24 16:09:39'),(46,'访问方式','httpvisittype','删除','DELETE','2020-11-15 15:47:20','2021-09-24 16:09:41'),(47,'功能执行机最大并发数','FunctionJmeterProcess','功能执行机并发Jmeter进程','2','2020-11-28 17:02:39','2021-04-02 12:04:57'),(52,'性能执行机Jmeter并发数','PerformanceJmeterProcess','性能测试Jmeter并行数','1','2021-04-07 09:08:10','2021-05-26 23:05:05'),(53,'执行计划业务类型','planbusinesstype','执行计划业务类型','常规测试','2021-04-20 17:24:17','2021-09-24 16:09:08'),(54,'执行计划业务类型','planbusinesstype','执行计划业务类型','CI自动化测试','2021-04-20 17:24:48','2021-09-24 16:09:10'),(55,'环境部署内容','machinedeploy','数据库','pgsql','2020-11-06 15:43:36','2021-09-24 16:09:08'),(56,'钉钉通知','DingDing','DingDingToken','111','2022-03-04 15:17:07','2022-06-07 19:33:41'),(57,'邮件通知','Mail','MailInfo','qq.smtp.qq.com,465,from@qq.com,mail,pass','2022-03-04 16:16:04','2022-03-04 16:16:04');
/*!40000 ALTER TABLE `dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dispatch`
--

DROP TABLE IF EXISTS `dispatch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dispatch` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `execplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划Id',
  `execplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行计划名',
  `batchid` bigint(20) unsigned NOT NULL COMMENT '批次Id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次名',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机Id',
  `slavername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行机名',
  `testcaseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `testcasename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `casejmxname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'jmeter-class',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微服务',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'jmeter-class',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待分配，已分配，已结束，调度异常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `plantype` varchar(20) DEFAULT NULL COMMENT '计划类型',
  `threadnum` bigint(20) DEFAULT NULL COMMENT '线程数',
  `loops` bigint(20) DEFAULT NULL COMMENT '循环数',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dispatch`
--

LOCK TABLES `dispatch` WRITE;
/*!40000 ALTER TABLE `dispatch` DISABLE KEYS */;
/*!40000 ALTER TABLE `dispatch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enviroment`
--

DROP TABLE IF EXISTS `enviroment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enviroment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境名',
  `envtype` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境类型',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='环境表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enviroment`
--

LOCK TABLES `enviroment` WRITE;
/*!40000 ALTER TABLE `enviroment` DISABLE KEYS */;
/*!40000 ALTER TABLE `enviroment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enviroment_assemble`
--

DROP TABLE IF EXISTS `enviroment_assemble`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enviroment_assemble` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `assemblename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境组件名',
  `assembletype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'mysql，oracle，redis',
  `connectstr` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '连接字',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='环境组件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enviroment_assemble`
--

LOCK TABLES `enviroment_assemble` WRITE;
/*!40000 ALTER TABLE `enviroment_assemble` DISABLE KEYS */;
/*!40000 ALTER TABLE `enviroment_assemble` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `envmachine`
--

DROP TABLE IF EXISTS `envmachine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `envmachine` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `envid` bigint(20) unsigned NOT NULL COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境名',
  `machineid` bigint(20) unsigned NOT NULL COMMENT '服务器Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '机器名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='环境服务器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `envmachine`
--

LOCK TABLES `envmachine` WRITE;
/*!40000 ALTER TABLE `envmachine` DISABLE KEYS */;
/*!40000 ALTER TABLE `envmachine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `executeplan`
--

DROP TABLE IF EXISTS `executeplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `executeplan` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '执行计划Id',
  `envid` bigint(20) unsigned NOT NULL COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境名',
  `executeplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行计划名',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态，new，waiting，running，pause，finish',
  `usetype` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行类型，function，performance，来区分分配什么slaver',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `businesstype` varchar(80) DEFAULT NULL COMMENT '业务类型，常规测试，CI自动化测试',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `runmode` varchar(20) DEFAULT NULL COMMENT '运行模式，单机运行，多机并发',
  `dingdingtoken` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '通知钉钉token',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='执行计划表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executeplan`
--

LOCK TABLES `executeplan` WRITE;
/*!40000 ALTER TABLE `executeplan` DISABLE KEYS */;
/*!40000 ALTER TABLE `executeplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `executeplan_params`
--

DROP TABLE IF EXISTS `executeplan_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `executeplan_params` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `executeplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `paramstype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '参数类型',
  `keyname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Key',
  `keyvalue` text,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试集合全局参数表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executeplan_params`
--

LOCK TABLES `executeplan_params` WRITE;
/*!40000 ALTER TABLE `executeplan_params` DISABLE KEYS */;
/*!40000 ALTER TABLE `executeplan_params` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `executeplan_testcase`
--

DROP TABLE IF EXISTS `executeplan_testcase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `executeplan_testcase` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `executeplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '微服务id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '微服务',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'API名',
  `testcaseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用例名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '操作人',
  `caseorder` bigint(20) unsigned DEFAULT NULL COMMENT '用例顺序',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='执行计划用例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executeplan_testcase`
--

LOCK TABLES `executeplan_testcase` WRITE;
/*!40000 ALTER TABLE `executeplan_testcase` DISABLE KEYS */;
/*!40000 ALTER TABLE `executeplan_testcase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `executeplanbatch`
--

DROP TABLE IF EXISTS `executeplanbatch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `executeplanbatch` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '执行计划Id',
  `executeplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '批次名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `status` varchar(10) DEFAULT NULL COMMENT '状态，new，waiting，running，pause，finish',
  `source` varchar(10) DEFAULT NULL COMMENT '来源，平台，ci,其他',
  `executeplanname` varchar(100) DEFAULT NULL COMMENT '计划名',
  `exectype` varchar(20) DEFAULT NULL COMMENT '执行类型，立即，某天定时，每天定时',
  `execdate` varchar(20) DEFAULT NULL COMMENT '执行时间',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='执行计划表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executeplanbatch`
--

LOCK TABLES `executeplanbatch` WRITE;
/*!40000 ALTER TABLE `executeplanbatch` DISABLE KEYS */;
/*!40000 ALTER TABLE `executeplanbatch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `globalheader`
--

DROP TABLE IF EXISTS `globalheader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `globalheader` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `globalheadername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '全局header名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全局Header';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `globalheader`
--

LOCK TABLES `globalheader` WRITE;
/*!40000 ALTER TABLE `globalheader` DISABLE KEYS */;
/*!40000 ALTER TABLE `globalheader` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `globalheader_params`
--

DROP TABLE IF EXISTS `globalheader_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `globalheader_params` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `globalheaderid` bigint(20) unsigned NOT NULL COMMENT 'globalheaderId',
  `keyname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'key名',
  `keyvalue` text,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全局Header参数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `globalheader_params`
--

LOCK TABLES `globalheader_params` WRITE;
/*!40000 ALTER TABLE `globalheader_params` DISABLE KEYS */;
/*!40000 ALTER TABLE `globalheader_params` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `globalheaderuse`
--

DROP TABLE IF EXISTS `globalheaderuse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `globalheaderuse` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `globalheaderid` bigint(20) unsigned NOT NULL COMMENT 'globalheaderId',
  `executeplanid` bigint(20) unsigned NOT NULL COMMENT '集合Id',
  `globalheadername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '全局header名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='集合全局Header';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `globalheaderuse`
--

LOCK TABLES `globalheaderuse` WRITE;
/*!40000 ALTER TABLE `globalheaderuse` DISABLE KEYS */;
/*!40000 ALTER TABLE `globalheaderuse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `globalvariables`
--

DROP TABLE IF EXISTS `globalvariables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `globalvariables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `keyname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'key名',
  `keyvalue` text,
  `memo` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全局变量';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `globalvariables`
--

LOCK TABLES `globalvariables` WRITE;
/*!40000 ALTER TABLE `globalvariables` DISABLE KEYS */;
/*!40000 ALTER TABLE `globalvariables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `macdepunit`
--

DROP TABLE IF EXISTS `macdepunit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `macdepunit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `depunitid` bigint(20) unsigned DEFAULT NULL COMMENT '微服务Id',
  `assembleid` bigint(20) unsigned DEFAULT NULL COMMENT '组件Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微服务名',
  `assembletype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '组件类型',
  `machineid` bigint(20) unsigned NOT NULL COMMENT '服务器Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '机器名',
  `envid` bigint(20) unsigned NOT NULL COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境名',
  `visittype` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '访问方式，ip,域名',
  `domain` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '服务域名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器微服务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `macdepunit`
--

LOCK TABLES `macdepunit` WRITE;
/*!40000 ALTER TABLE `macdepunit` DISABLE KEYS */;
/*!40000 ALTER TABLE `macdepunit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine`
--

DROP TABLE IF EXISTS `machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `machine` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '机器名',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'ip',
  `cpu` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'cpu',
  `disk` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'disk',
  `mem` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '内存',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine`
--

LOCK TABLES `machine` WRITE;
/*!40000 ALTER TABLE `machine` DISABLE KEYS */;
/*!40000 ALTER TABLE `machine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mockapi`
--

DROP TABLE IF EXISTS `mockapi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mockapi` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `modelid` bigint(20) unsigned NOT NULL COMMENT '模块Id',
  `modelname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模块名',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '接口',
  `apiurl` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'url',
  `apitype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '接口类型',
  `requesttype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '请求类型',
  `timeout` bigint(20) unsigned NOT NULL COMMENT '超时',
  `memo` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='mock接口表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mockapi`
--

LOCK TABLES `mockapi` WRITE;
/*!40000 ALTER TABLE `mockapi` DISABLE KEYS */;
/*!40000 ALTER TABLE `mockapi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mockapirespone`
--

DROP TABLE IF EXISTS `mockapirespone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mockapirespone` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT '接口Id',
  `responecode` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '接口',
  `responecontent` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '响应内容',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='mock接口响应表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mockapirespone`
--

LOCK TABLES `mockapirespone` WRITE;
/*!40000 ALTER TABLE `mockapirespone` DISABLE KEYS */;
/*!40000 ALTER TABLE `mockapirespone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mockmodel`
--

DROP TABLE IF EXISTS `mockmodel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mockmodel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `modelcode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模块编码',
  `modelname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模块名',
  `memo` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='mock模块表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mockmodel`
--

LOCK TABLES `mockmodel` WRITE;
/*!40000 ALTER TABLE `mockmodel` DISABLE KEYS */;
/*!40000 ALTER TABLE `mockmodel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `performancereportfilelog`
--

DROP TABLE IF EXISTS `performancereportfilelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `performancereportfilelog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `execplanid` bigint(20) unsigned NOT NULL COMMENT 'execplanid',
  `batchid` bigint(20) unsigned NOT NULL COMMENT 'batchid',
  `caseid` bigint(20) unsigned NOT NULL COMMENT 'caseid',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT 'slaverid',
  `filename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件名，planid+batchid+slaverid',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='性能报告文件记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performancereportfilelog`
--

LOCK TABLES `performancereportfilelog` WRITE;
/*!40000 ALTER TABLE `performancereportfilelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `performancereportfilelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `performancereportsource`
--

DROP TABLE IF EXISTS `performancereportsource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `performancereportsource` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `planid` bigint(20) unsigned NOT NULL COMMENT '执行计划Id',
  `batchid` bigint(20) unsigned NOT NULL COMMENT '批次Id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `testclass` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '解析文件匹配sample',
  `runtime` double(11,2) NOT NULL COMMENT '运行时长',
  `source` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '解析文件目录',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待解析，已解析',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `totalcasenums` bigint(20) unsigned NOT NULL COMMENT '用例数',
  `totalcasepassnums` bigint(20) unsigned NOT NULL COMMENT '用例成功数',
  `totalcasefailnums` bigint(20) unsigned NOT NULL COMMENT '用例失败数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='性能报告路径表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performancereportsource`
--

LOCK TABLES `performancereportsource` WRITE;
/*!40000 ALTER TABLE `performancereportsource` DISABLE KEYS */;
/*!40000 ALTER TABLE `performancereportsource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限Id',
  `resource` varchar(256) NOT NULL COMMENT '权限对应的资源',
  `code` varchar(256) NOT NULL COMMENT '权限的代码/通配符,对应代码中@hasAuthority(xx)',
  `handle` varchar(256) NOT NULL COMMENT '对应的资源操作',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=251 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'账号','account:list','列表'),(2,'账号','account:detail','详情'),(3,'账号','account:add','添加'),(4,'账号','account:update','更新'),(5,'账号','account:delete','删除'),(6,'账号','account:search','搜索'),(7,'角色','role:list','列表'),(8,'角色','role:detail','详情'),(9,'角色','role:add','添加'),(10,'角色','role:update','更新'),(11,'角色','role:delete','删除'),(12,'角色','role:search','搜索'),(13,'测试表','table:list','列表'),(14,'字典','dictionary:list','列表'),(15,'字典','dictionary:add','添加'),(16,'字典','dictionary:search','搜索'),(17,'字典','dictionary:update','修改'),(18,'字典','dictionary:delete','删除'),(19,'微服务','depunit:list','列表'),(20,'微服务','depunit:detail','详情'),(21,'微服务','depunit:add','添加'),(22,'微服务','depunit:update','更新'),(23,'微服务','depunit:delete','删除'),(24,'微服务','depunit:search','搜索'),(25,'测试环境','enviroment:list','列表'),(26,'测试环境','enviroment:detail','详情'),(27,'测试环境','enviroment:add','添加'),(28,'测试环境','enviroment:update','更新'),(29,'测试环境','enviroment:delete','删除'),(30,'测试环境','enviroment:search','搜索'),(31,'服务器','machine:list','列表'),(32,'服务器','machine:detail','详情'),(33,'服务器','machine:add','添加'),(34,'服务器','machine:update','更新'),(35,'服务器','machine:delete','删除'),(36,'服务器','machine:search','搜索'),(37,'测试人员','tester:list','列表'),(38,'测试人员','tester:detail','详情'),(39,'测试人员','tester:add','添加'),(40,'测试人员','tester:update','更新'),(41,'测试人员','tester:delete','删除'),(42,'测试人员','tester:search','搜索'),(43,'api','api:list','列表'),(44,'api','api:detail','详情'),(45,'api','api:add','添加'),(46,'api','api:update','更新'),(47,'api','api:delete','删除'),(48,'api','api:search','搜索'),(49,'api参数','apiparams:list','列表'),(50,'api参数','apiparams:detail','详情'),(51,'api参数','apiparams:add','添加'),(52,'api参数','apiparams:update','更新'),(53,'api参数','apiparams:delete','删除'),(54,'api参数','apiparams:search','搜索'),(55,'环境服务器','envmachine:list','列表'),(56,'环境服务器','envmachine:detail','详情'),(57,'环境服务器','envmachine:add','添加'),(58,'环境服务器','envmachine:update','更新'),(59,'环境服务器','envmachine:delete','删除'),(60,'环境服务器','envmachine:search','搜索'),(61,'服务器微服务','macdepunit:list','列表'),(62,'服务器微服务','macdepunit:detail','详情'),(63,'服务器微服务','macdepunit:add','添加'),(64,'服务器微服务','macdepunit:update','更新'),(65,'服务器微服务','macdepunit:delete','删除'),(66,'服务器微服务','macdepunit:search','搜索'),(67,'API用例库','apicases:list','列表'),(68,'API用例库','apicases:detail','详情'),(69,'API用例库','apicases:add','增加'),(70,'API用例库','apicases:update','更新'),(71,'API用例库','apicases:delete','删除'),(72,'API用例库','apicases:search','查询'),(73,'执行机','slaver:list','列表'),(74,'执行机','slaver:detail','详情'),(75,'执行机','slaver:add','增加'),(76,'执行机','slaver:update','更新'),(77,'执行机','slaver:delete','删除'),(78,'执行机','slaver:search','查询'),(79,'执行计划','executeplan:list','列表'),(80,'执行计划','executeplan:detail','详情'),(81,'执行计划','executeplan:add','增加'),(82,'执行计划','executeplan:update','更新'),(83,'执行计划','executeplan:delete','删除'),(84,'执行计划','executeplan:search','查询'),(85,'api报告','apireport:list','列表'),(86,'api报告','apireport:detail','详情'),(87,'api报告','apireport:add','增加'),(88,'api报告','apireport:update','更新'),(89,'api报告','apireport:delete','删除'),(90,'api报告','apireport:search','查询'),(91,'API用例库','apicases:params','参数'),(92,'用例前后条件','apicases_condition:list','列表'),(93,'用例前后条件','apicases_condition:detail','详情'),(94,'用例前后条件','apicases_condition:add','增加'),(95,'用例前后条件','apicases_condition:update','更新'),(96,'用例前后条件','apicases_condition:delete','删除'),(97,'用例前后条件','apicases_condition:search','查询'),(98,'环境组件','enviroment_assemble:list','列表'),(99,'环境组件','enviroment_assemble:detail','详情'),(100,'环境组件','enviroment_assemble:add','增加'),(101,'环境组件','enviroment_assemble:update','更新'),(102,'环境组件','enviroment_assemble:delete','删除'),(103,'环境组件','enviroment_assemble:search','查询'),(104,'调度','dispatch:list','列表'),(105,'调度','dispatch:detail','详情'),(106,'调度','dispatch:add','增加'),(107,'调度','dispatch:update','更新'),(108,'调度','dispatch:delete','删除'),(109,'调度','dispatch:search','查询'),(110,'性能报告','apiperformancereport:list','列表'),(111,'性能报告','apiperformancereport:detail','列表'),(112,'性能报告','apiperformancereport:add','列表'),(113,'性能报告','apiperformancereport:update','列表'),(114,'性能报告','apiperformancereport:delete','列表'),(115,'性能报告','apiperformancereport:search','列表'),(116,'性能报告','apiperformancestatistics:list','列表'),(117,'性能报告','apiperformancestatistics:detail','详情'),(118,'性能报告','apiperformancestatistics:add','增加'),(119,'性能报告','apiperformancestatistics:update','更新'),(120,'性能报告','apiperformancestatistics:delete','删除'),(121,'性能报告','apiperformancestatistics:search','查询'),(122,'功能报告统计','apireportstatics:list','列表'),(123,'功能报告统计','apireportstatics:search','查询'),(124,'执行计划批次','executeplanbatch:list','列表'),(125,'执行计划批次','executeplanbatch:detail','详情'),(126,'执行计划批次','executeplanbatch:add','增加'),(127,'执行计划批次','executeplanbatch:update','更新'),(128,'执行计划批次','executeplanbatch:delete','删除'),(129,'执行计划批次','executeplanbatch:search','查询'),(130,'条件管理','condition:list','列表'),(131,'条件管理','condition:search','查询'),(132,'条件管理','condition:add','增加'),(133,'条件管理','condition:detail','详情'),(134,'条件管理','condition:update','更新'),(135,'条件管理','condition:delete','删除'),(136,'api条件','apicondition:list','列表'),(137,'api条件','apicondition:search','查询'),(138,'api条件','apicondition:add','增加'),(139,'api条件','apicondition:detail','详情'),(140,'api条件','apicondition:update','更新'),(141,'api条件','apicondition:delete','删除'),(142,'变量管理','testvariables:list','列表'),(143,'变量管理','testvariables:search','查询'),(144,'变量管理','testvariables:add','增加'),(145,'变量管理','testvariables:detail','详情'),(146,'变量管理','testvariables:update','更新'),(147,'变量管理','testvariables:delete','删除'),(148,'用例变量','ApicasesVariables:list','列表'),(149,'用例变量','ApicasesVariables:search','查询'),(150,'用例变量','ApicasesVariables:add','增加'),(151,'用例变量','ApicasesVariables:detail','详情'),(152,'用例变量','ApicasesVariables:update','更新'),(153,'用例变量','ApicasesVariables:delete','删除'),(154,'变量值','testvariablesvalue:list','列表'),(155,'变量值','testvariablesvalue:search','查询'),(156,'变量值','testvariablesvalue:add','增加'),(157,'变量值','testvariablesvalue:detail','详情'),(158,'变量值','testvariablesvalue:update','更新'),(159,'变量值','testvariablesvalue:delete','删除'),(160,'条件报告','testconditionreport:list','列表'),(161,'条件报告','testconditionreport:search','查询'),(162,'条件报告','testconditionreport:add','增加'),(163,'条件报告','testconditionreport:detail','详情'),(164,'条件报告','testconditionreport:update','更新'),(165,'条件报告','testconditionreport:delete','删除'),(166,'脚本条件','scriptcondition:list','列表'),(167,'脚本条件','scriptcondition:detail','详情'),(168,'脚本条件','scriptcondition:add','添加'),(169,'脚本条件','scriptcondition:update','更新'),(170,'脚本条件','scriptcondition:delete','删除'),(171,'脚本条件','scriptcondition:search','搜索'),(172,'数据库条件','dbcondition:list','列表'),(173,'数据库条件','dbcondition:detail','详情'),(174,'数据库条件','dbcondition:add','添加'),(175,'数据库条件','dbcondition:update','更新'),(176,'数据库条件','dbcondition:delete','删除'),(177,'数据库条件','dbcondition:search','搜索'),(178,'条件顺序','conditionorder:list','列表'),(179,'条件顺序','conditionorder:detail','详情'),(180,'条件顺序','conditionorder:add','添加'),(181,'条件顺序','conditionorder:moveup','上移'),(182,'条件顺序','conditionorder:movedown','下移'),(183,'条件顺序','conditionorder:search','搜索'),(185,'随机变量','variables:list','列表'),(186,'随机变量','variables:detail','详情'),(187,'随机变量','variables:update','修改'),(188,'随机变量','variables:delete','删除'),(189,'随机变量','variables:add','添加'),(190,'随机变量','variables:search','搜索'),(191,'数据库变量','dbvariables:search','搜索'),(192,'数据库变量','dbvariables:add','添加'),(193,'数据库变量','dbvariables:delete','删除'),(194,'数据库变量','dbvariables:update','更新'),(195,'数据库变量','dbvariables:detail','修改'),(196,'数据库变量','dbvariables:list','查询'),(197,'项目迭代','project:search','搜索'),(198,'项目迭代','project:add','添加'),(199,'项目迭代','project:delete','删除'),(200,'项目迭代','project:update','更新'),(201,'项目迭代','project:detail','修改'),(202,'项目迭代','project:list','查询'),(203,'延时子条件','delaycondition:search','搜索'),(204,'延时子条件','delaycondition:add','添加'),(205,'延时子条件','delaycondition:delete','删除'),(206,'延时子条件','delaycondition:update','更新'),(207,'延时子条件','delaycondition:detail','修改'),(208,'延时子条件','delaycondition:list','查询'),(209,'脚本变量','scriptvariables:search','搜索'),(210,'脚本变量','scriptvariables:add','添加'),(211,'脚本变量','scriptvariables:delete','删除'),(212,'脚本变量','scriptvariables:update','更新'),(213,'脚本变量','scriptvariables:detail','修改'),(214,'脚本变量','scriptvariables:list','查询'),(215,'流程用例','processtestcase:search','搜索'),(216,'流程用例','processtestcase:add','添加'),(217,'流程用例','processtestcase:delete','删除'),(218,'流程用例','processtestcase:update','更新'),(219,'流程用例','processtestcase:detail','修改'),(220,'流程用例','processtestcase:list','查询'),(221,'全局Header','globalheader:search','搜索'),(222,'全局Header','globalheader:add','添加'),(223,'全局Header','globalheader:delete','删除'),(224,'全局Header','globalheader:update','更新'),(225,'全局Header','globalheader:detail','修改'),(226,'全局Header','globalheader:list','查询'),(227,'全局变量','globalvariables:search','搜索'),(228,'全局变量','globalvariables:add','添加'),(229,'全局变量','globalvariables:delete','删除'),(230,'全局变量','globalvariables:update','更新'),(231,'全局变量','globalvariables:detail','修改'),(232,'全局变量','globalvariables:list','查询'),(233,'mock模块','mockmodel:search','搜索'),(234,'mock模块','mockmodel:add','添加'),(235,'mock模块','mockmodel:delete','删除'),(236,'mock模块','mockmodel:update','更新'),(237,'mock模块','mockmodel:detail','修改'),(238,'mock模块','mockmodel:list','查询'),(239,'mock接口','mockapi:search','搜索'),(240,'mock接口','mockapi:add','添加'),(241,'mock接口','mockapi:delete','删除'),(242,'mock接口','mockapi:update','更新'),(243,'mock接口','mockapi:detail','修改'),(244,'mock接口','mockapi:list','查询'),(245,'mock接口响应','mockapirespone:search','搜索'),(246,'mock接口响应','mockapirespone:add','添加'),(247,'mock接口响应','mockapirespone:delete','删除'),(248,'mock接口响应','mockapirespone:update','更新'),(249,'mock接口响应','mockapirespone:detail','修改'),(250,'mock接口响应','mockapirespone:list','查询');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planbantchexeclog`
--

DROP TABLE IF EXISTS `planbantchexeclog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planbantchexeclog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `batchid` bigint(20) unsigned NOT NULL COMMENT '批次Id',
  `exec_time` varchar(20) DEFAULT NULL COMMENT '执行时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `memo` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划执行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planbantchexeclog`
--

LOCK TABLES `planbantchexeclog` WRITE;
/*!40000 ALTER TABLE `planbantchexeclog` DISABLE KEYS */;
/*!40000 ALTER TABLE `planbantchexeclog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_testcase`
--

DROP TABLE IF EXISTS `process_testcase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `process_testcase` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `executeplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '微服务id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '微服务',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'API名',
  `testcaseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用例名',
  `ordernum` bigint(20) unsigned NOT NULL COMMENT '顺序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程用例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_testcase`
--

LOCK TABLES `process_testcase` WRITE;
/*!40000 ALTER TABLE `process_testcase` DISABLE KEYS */;
/*!40000 ALTER TABLE `process_testcase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '项目Id',
  `projectname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目名',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '项目状态',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '项目描述',
  `creator` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='项目迭代表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'演示项目','开始','2022-04-27 00:00:00','2022-04-27 12:56:46','这是一个演示项目','admin','2022-04-27 12:56:50','2022-10-29 22:19:56');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'超级管理员','2019-07-01 00:00:00','2019-07-01 00:00:00'),(2,'普通用户','2019-07-01 00:00:00','2022-07-28 16:38:09'),(3,'测试','2019-07-01 00:00:00','2019-07-01 00:00:00');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色Id',
  `permission_id` bigint(20) unsigned NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permission_fk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (2,1),(3,1),(2,2),(2,3),(2,4),(2,5),(3,5),(2,6),(2,25),(2,26),(2,27),(2,28),(2,29),(2,30),(2,31),(2,32),(2,33),(2,34),(2,35),(2,36);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routeperformancereport`
--

DROP TABLE IF EXISTS `routeperformancereport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `routeperformancereport` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '路由Id',
  `executeplanid` bigint(20) unsigned DEFAULT NULL COMMENT '集合id',
  `tablename` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '表名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='性能结果路由表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routeperformancereport`
--

LOCK TABLES `routeperformancereport` WRITE;
/*!40000 ALTER TABLE `routeperformancereport` DISABLE KEYS */;
/*!40000 ALTER TABLE `routeperformancereport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scriptvariables`
--

DROP TABLE IF EXISTS `scriptvariables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scriptvariables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `scriptvariablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `variablesdes` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量描述',
  `valuetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量值类型',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='脚本变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scriptvariables`
--

LOCK TABLES `scriptvariables` WRITE;
/*!40000 ALTER TABLE `scriptvariables` DISABLE KEYS */;
/*!40000 ALTER TABLE `scriptvariables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slaver`
--

DROP TABLE IF EXISTS `slaver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slaver` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '执行机Id',
  `slavername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '执行机器名',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'ip',
  `port` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '端口',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态，idle，running',
  `stype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '执行机类型，功能机，性能机',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `macaddress` varchar(100) DEFAULT NULL COMMENT 'mac地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='服务器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slaver`
--

--
-- Table structure for table `statics_deployunitandcases`
--

DROP TABLE IF EXISTS `statics_deployunitandcases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statics_deployunitandcases` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '计划名',
  `passrate` double(11,2) NOT NULL COMMENT '成功率',
  `totalcases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalpasscases` bigint(20) NOT NULL COMMENT '用例成功总数',
  `totalfailcases` bigint(20) NOT NULL COMMENT '用例失败总数',
  `runtime` bigint(50) NOT NULL COMMENT '运行时长',
  `statics_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '统计日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api微服务用例统计报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statics_deployunitandcases`
--

LOCK TABLES `statics_deployunitandcases` WRITE;
/*!40000 ALTER TABLE `statics_deployunitandcases` DISABLE KEYS */;
/*!40000 ALTER TABLE `statics_deployunitandcases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statics_planandcases`
--

DROP TABLE IF EXISTS `statics_planandcases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statics_planandcases` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `testplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '计划名',
  `passrate` double(11,2) NOT NULL COMMENT '成功率',
  `totalcases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalpasscases` bigint(20) NOT NULL COMMENT '用例成功总数',
  `totalfailcases` bigint(20) NOT NULL COMMENT '用例失败总数',
  `runtime` bigint(50) NOT NULL COMMENT '运行时长',
  `statics_date` date NOT NULL COMMENT '统计日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api计划用例统计报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statics_planandcases`
--

LOCK TABLES `statics_planandcases` WRITE;
/*!40000 ALTER TABLE `statics_planandcases` DISABLE KEYS */;
/*!40000 ALTER TABLE `statics_planandcases` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `testcondition`
--

DROP TABLE IF EXISTS `testcondition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testcondition` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件名',
  `objectid` bigint(20) unsigned DEFAULT NULL COMMENT '目标Id，计划，用例的id',
  `objectname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '目标名',
  `objecttype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '目标类型',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件类型，前置，后置，其他',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微服务名',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api名',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT 'deployunitid',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testcondition`
--

LOCK TABLES `testcondition` WRITE;
/*!40000 ALTER TABLE `testcondition` DISABLE KEYS */;
/*!40000 ALTER TABLE `testcondition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testcondition_report`
--

DROP TABLE IF EXISTS `testcondition_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testcondition_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) DEFAULT NULL COMMENT '条件id',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '前置，后置',
  `subconditionid` bigint(20) DEFAULT NULL COMMENT '子条件id，接口，db，nosql条件id',
  `conditionresult` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '接口返回，数据库返回结果等等',
  `conditionstatus` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '条件完成状态，成功，失败',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `batchname` varchar(64) DEFAULT NULL COMMENT '批次',
  `planname` varchar(64) DEFAULT NULL COMMENT '计划名',
  `testplanid` bigint(20) DEFAULT NULL COMMENT '计划id',
  `subconditiontype` varchar(20) DEFAULT NULL COMMENT '子条件类型，接口，数据库，脚本其他',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `subconditionname` varchar(20) DEFAULT NULL COMMENT '子条件名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='条件报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testcondition_report`
--

LOCK TABLES `testcondition_report` WRITE;
/*!40000 ALTER TABLE `testcondition_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `testcondition_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tester`
--

DROP TABLE IF EXISTS `tester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tester` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '测试人员姓名',
  `testertitle` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '测试人员职务',
  `testerorg` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所属组织',
  `testermemo` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='测试人员表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `testvariables`
--

DROP TABLE IF EXISTS `testvariables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testvariables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testvariablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `testvariablestype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量类型，用例变量，全局变量',
  `variablesexpress` varchar(210) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量表达',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `variablesdes` varchar(100) DEFAULT NULL COMMENT '变量描述',
  `valuetype` varchar(20) DEFAULT 'String' COMMENT 'String，Number，Array,Bool,其他',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testvariables`
--

LOCK TABLES `testvariables` WRITE;
/*!40000 ALTER TABLE `testvariables` DISABLE KEYS */;
/*!40000 ALTER TABLE `testvariables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testvariables_value`
--

DROP TABLE IF EXISTS `testvariables_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testvariables_value` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `planid` bigint(20) unsigned NOT NULL COMMENT '计划Id',
  `planname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '计划名',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `variablesid` bigint(20) unsigned NOT NULL COMMENT '变量Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `variablesvalue` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量值',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `batchname` varchar(64) DEFAULT NULL COMMENT '批次',
  `variablestype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='变量值表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testvariables_value`
--

LOCK TABLES `testvariables_value` WRITE;
/*!40000 ALTER TABLE `testvariables_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `testvariables_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variables`
--

DROP TABLE IF EXISTS `variables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variables` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量名',
  `variablestype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量类型',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '变量描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `variablecondition` varchar(64) DEFAULT NULL COMMENT '变量条件',
  `projectid` bigint(20) unsigned DEFAULT '1' COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variables`
--

LOCK TABLES `variables` WRITE;
/*!40000 ALTER TABLE `variables` DISABLE KEYS */;
/*!40000 ALTER TABLE `variables` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-30 15:30:49
