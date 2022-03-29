-- MySQL dump 10.13  Distrib 8.0.25, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: testcenter
-- ------------------------------------------------------
-- Server version 5.7.28

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
INSERT INTO `account` VALUES (1,'admin@qq.com','admin','$2a$10$wg0f10n.30UbU.9hPpucbef/ya62LdTKs72xJfjxvTFsL0Xaewbra','2019-07-01 00:00:00','2022-03-28 16:10:35'),(2,'editor@qq.com','editor','$2a$10$/m4SgZ.ZFVZ7rcbQpJW2tezmuhf/UzQtpAtXb0WZiAE3TeHxq2DYu','2019-07-02 00:00:00','2019-07-02 00:00:00'),(3,'test@qq.com','test','$2a$10$NGJEkH3bl7rwgk0ZYChT4.tWTm28jOY9GaeMfj2kYZ2qFB4Ed9sW2','2019-07-03 00:00:00','2021-04-23 15:14:05'),(16,'admin@qqq.com','aaa','$2a$10$VCKJ6MZWZHXt2RVvT2rRIeY94pbHjWLa7S/2oGm.B2Ztg.yrciHza','2022-03-03 15:13:59',NULL);
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
INSERT INTO `account_role` VALUES (1,1),(2,2),(16,2);
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
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='发布单元表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api`
--

LOCK TABLES `api` WRITE;
/*!40000 ALTER TABLE `api` DISABLE KEYS */;
INSERT INTO `api` VALUES (1,1,'测试百度','百度首页','GET','传统方式','','Form表单','','百度首页API','2022-03-28 12:42:14','2022-03-28 12:42:14','admin',''),(3,2,'testcenterservice','登陆','POST','Restful','/account/token','JSON','','登陆接口','2022-03-28 12:50:00','2022-03-28 12:50:00','admin',''),(4,2,'testcenterservice','新增环境','POST','Restful','/enviroment','JSON','','新增环境API','2022-03-28 12:58:16','2022-03-28 12:59:25','admin','');
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
  `apiparam` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api参数',
  `apiparamvalue` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例参数值',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api属性类型，header，body',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `paramstype` varchar(20) DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='api用例数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_casedata`
--

LOCK TABLES `api_casedata` WRITE;
/*!40000 ALTER TABLE `api_casedata` DISABLE KEYS */;
INSERT INTO `api_casedata` VALUES (1,2,'正常登陆','Body','{\n  \"name\": \"admin\",\n  \"password\": \"admin123\"\n}','Body','','2022-03-28 12:51:15','2022-03-28 12:51:15','JSON'),(3,3,'正常新增环境','Authorization','<token>','Header','','2022-03-28 13:03:49','2022-03-28 13:03:49',''),(4,3,'正常新增环境','Body','{\n  \"id\": \"\",\n  \"enviromentname\": \"[enviromentname]\",\n  \"envtype\": \"功能\",\n  \"memo\": \"<<usernamefromdb>>\",\n  \"creator\": \"admin\"\n}','Body','','2022-03-28 13:03:49','2022-03-28 13:03:49','JSON'),(5,4,'新增环境性能测试','Authorization','<token>','Header','','2022-03-28 15:07:19','2022-03-28 15:07:19',''),(6,4,'新增环境性能测试','Body','{\n  \"id\": \"\",\n  \"enviromentname\": \"[enviromentname]\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}','Body','','2022-03-28 15:07:19','2022-03-28 15:07:19','JSON');
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
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发布单元Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api属性类型，header，body',
  `keyname` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'key名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `keydefaultvalue` text COMMENT 'Key默认值',
  `keytype` varchar(20) DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='api参数表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_params`
--

LOCK TABLES `api_params` WRITE;
/*!40000 ALTER TABLE `api_params` DISABLE KEYS */;
INSERT INTO `api_params` VALUES (1,4,'新增环境',2,'testcenterservice','Body','{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}','2022-03-28 12:03:58','2022-03-28 13:00:49',NULL,'NoForm','JSON'),(2,4,'新增环境',2,'testcenterservice','Header','Authorization','2022-03-28 13:03:49','2022-03-28 13:03:49','admin','<token>','');
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
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发布单元id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='api用例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases`
--

LOCK TABLES `apicases` WRITE;
/*!40000 ALTER TABLE `apicases` DISABLE KEYS */;
INSERT INTO `apicases` VALUES (1,1,'百度首页',1,'测试百度','访问百度首页功能','httpapi','功能',1,1,'百度首页功能用例测试','','','百度首页功能用例测试例子','2022-03-28 12:43:12','2022-03-28 12:52:04','admin',1),(2,3,'登陆',2,'testcenterservice','正常登陆','httpapi','功能',1,1,'正常登陆返回数据','','','正常登陆返回数据','2022-03-28 12:50:42','2022-03-28 12:50:42','admin',1),(3,4,'新增环境',2,'testcenterservice','正常新增环境','httpapi','功能',1,1,'新增环境成功','','','新增环境成功用例','2022-03-28 13:00:06','2022-03-28 13:00:06','admin',1),(4,4,'新增环境',2,'testcenterservice','新增环境性能测试','httpapi','性能',2,2,'新增环境成功','','','新增环境成功性能用例','2022-03-28 15:07:19','2022-03-28 15:07:38','admin',1);
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
  `casedeployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例所属发布单元',
  `caseapiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例所属api',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `basetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '前置，后置',
  `conditionbasetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据库，接口',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '数据库：mysql，oracle，sqlserver，接口：http,https,dubbo',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '包含调用接口的发布单元',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='api用例性能统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_performancestatistics`
--

LOCK TABLES `apicases_performancestatistics` WRITE;
/*!40000 ALTER TABLE `apicases_performancestatistics` DISABLE KEYS */;
INSERT INTO `apicases_performancestatistics` VALUES (1,4,3,'性能测试0001',0.55,1,4,0,0.00,98.25,15.00,344.00,17.00,344.00,344.00,344.00,7.40,0.00,0.00,'2022-03-28 15:11:20','2022-03-28 15:11:20',NULL);
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='api用例报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_report`
--

LOCK TABLES `apicases_report` WRITE;
/*!40000 ALTER TABLE `apicases_report` DISABLE KEYS */;
INSERT INTO `apicases_report` VALUES (1,1,1,'百度测试000001',1,'成功','<!DOCTYPE html>\r\n<!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\"></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=http://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \" name=\"tj_login\" class=\"lb\">登录</a>);</script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>\r\n','',198,'','','2022-03-28 12:56:21','2022-03-28 12:56:21','admin','Content-Type ：application/x-www-form-urlencoded','','http://www.baidu.com/','GET'),(2,3,2,'测试中心回归测试00001',1,'成功','{\"code\":4002,\"message\":\"认证异常\"}\n','',147,'','','2022-03-28 13:06:21','2022-03-28 13:06:21','admin','Authorization ：Content-Type ：application/json;charset=utf-8','{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}','http://127.0.0.1:8080/enviroment','POST'),(3,3,2,'测试中心回归00002',1,'成功','{\"code\":4002,\"message\":\"认证异常\"}\n','',133,'','','2022-03-28 13:15:45','2022-03-28 13:15:45','admin','Authorization ：<token>Content-Type ：application/json;charset=utf-8','{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}','http://127.0.0.1:8080/enviroment','POST'),(4,3,2,'000003',1,'成功','{\"code\":4002,\"message\":\"认证异常\"}\n','',140,'','','2022-03-28 14:49:06','2022-03-28 14:49:06','admin','Authorization ：Content-Type ：application/json;charset=utf-8','{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}','http://127.0.0.1:8080/enviroment','POST'),(5,3,2,'000004',1,'成功','{\"code\":4002,\"message\":\"认证异常\"}\n','',128,'','','2022-03-28 14:51:12','2022-03-28 14:51:12','admin','Authorization ：<token>Content-Type ：application/json;charset=utf-8','{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}','http://127.0.0.1:8080/enviroment','POST'),(6,3,2,'00005',1,'成功','{\n \"code\":200\n}','',148,'','','2022-03-28 15:01:06','2022-03-28 15:01:06','admin','Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4s_7UzXwJ0y2GwbpY81fAjGtZhhprZmpkZmjg-1jXxxDpbmhoNpJzuvZ8B4aV2g8nvw-n80mNs11ONye1h0uUrzarlTf-tXJWDZPxZzWON9IdzWSC6Q6CslNnMFkfOrY3yulLZ7Bvo4oEpfW6LyHVoDXCi0xIkglEAuNJUuhmITD-S2FpCUFxCSFxQQFxWX44VaUEECWGNGWBpkBQm3Jq9kUXCugGdafXZbTBrksFZXaDryZVETapKjG_KcMmrJYo1KvSKyHxPXAHCWdGPFQFlg0ioUEYlIWBBgMY92Z1s1q0cWZbXcgsMWDYziw3ELNcRkxYLzHrVvRBhZhmte_UZMNu1Wreqcvmncps2q3N5qV6faTiHMXhNScmjkmcDRiJzWrXLv54fXvF1RX3trm04saK65qN1Y3G1RSw2mRw63jw5vUw4_pu6fn71rrYaBzqPXKNu6yt1IZbqQo3Osm423E4queC6ww72ieCWwg72meBWwQz275YuEi0JkiWAEAJ4SgoGCWFWv1_Q8q9h9wiKbo4EFc0JRYnh_pNBX3BSBQ5MGsoSxrjUeVw1BhtlsO6dTb4MDKJ8ZWTAMxJCKYRwhyBuVPemzl-h4ZXfAHmo6ZBGTVVykB0KqNSblb6YpfciWSpUhxqQA6BSRceOYS5N3oPZpsU_CZxTaDKAhKzAMUMYjFLwoZ8dYQpsQO_SsJvCwz8agkfq8Rj4L9qTCK-chKYOQnJNMKYUzD5Ezbfr_l41ZdrPlvzzTa3vjUaitMaQ4tMoSRTKAkMJYmhbsXbnxYYh-uUldYpKK0wJa0xwmtnt1D_z9x6GCztxGjthGtNJLc2lZjUgW8SLglJCkigAABrygWFcUHl3-MQX_6MY-99z0dmf5QK9EYPYrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff399uv7-cf35_frl85-v3-KfrOZ-O50fP799-ezpiye3jz7-AwAA__8.qhRtIUk8aVvo_8kmViqPp32PoCKHX02WtdOykt0B0s-_36hTW-3e9NC-dRQ8XQ9ussLGOKjpYRPTrB17ieiCxwContent-Type ：application/json;charset=utf-8','{\n  \"id\": \"\",\n  \"enviromentname\": \"sss\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}','http://127.0.0.1:8080/enviroment','POST'),(7,1,1,'百度测试0001',1,'成功','<!DOCTYPE html>\r\n<!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> <body link=#0000cc> <div id=wrapper> <div id=head> <div class=head_wrapper> <div class=s_form> <div class=s_form_wrapper> <div id=lg> <img hidefocus=true src=//www.baidu.com/img/bd_logo1.png width=270 height=129> </div> <form id=form name=f action=//www.baidu.com/s class=fm> <input type=hidden name=bdorz_come value=1> <input type=hidden name=ie value=utf-8> <input type=hidden name=f value=8> <input type=hidden name=rsv_bp value=1> <input type=hidden name=rsv_idx value=1> <input type=hidden name=tn value=baidu><span class=\"bg s_ipt_wr\"><input id=kw name=wd class=s_ipt value maxlength=255 autocomplete=off autofocus></span><span class=\"bg s_btn_wr\"><input type=submit id=su value=百度一下 class=\"bg s_btn\"></span> </form> </div> </div> <div id=u1> <a href=http://news.baidu.com name=tj_trnews class=mnav>新闻</a> <a href=http://www.hao123.com name=tj_trhao123 class=mnav>hao123</a> <a href=http://map.baidu.com name=tj_trmap class=mnav>地图</a> <a href=http://v.baidu.com name=tj_trvideo class=mnav>视频</a> <a href=http://tieba.baidu.com name=tj_trtieba class=mnav>贴吧</a> <noscript> <a href=http://www.baidu.com/bdorz/login.gif?login&amp;tpl=mn&amp;u=http%3A%2F%2Fwww.baidu.com%2f%3fbdorz_come%3d1 name=tj_login class=lb>登录</a> </noscript> <script>document.write(<a href=\"http://www.baidu.com/bdorz/login.gif?login&tpl=mn&u=+ encodeURIComponent(window.location.href+ (window.location.search === \"\" ? \"?\" : \"&\")+ \"bdorz_come=1\")+ \" name=\"tj_login\" class=\"lb\">登录</a>);</script> <a href=//www.baidu.com/more/ name=tj_briicon class=bri style=\"display: block;\">更多产品</a> </div> </div> </div> <div id=ftCon> <div id=ftConw> <p id=lh> <a href=http://home.baidu.com>关于百度</a> <a href=http://ir.baidu.com>About Baidu</a> </p> <p id=cp>&copy;2017&nbsp;Baidu&nbsp;<a href=http://www.baidu.com/duty/>使用百度前必读</a>&nbsp; <a href=http://jianyi.baidu.com/ class=cp-feedback>意见反馈</a>&nbsp;京ICP证030173号&nbsp; <img src=//www.baidu.com/img/gs.gif> </p> </div> </div> </div> </body> </html>\r\n','',177,'','','2022-03-28 15:25:36','2022-03-28 15:25:36','admin','Content-Type ：application/x-www-form-urlencoded','','http://www.baidu.com/','GET'),(8,3,2,'00008',1,'成功','{\n  \"code\":200\n}','',202,'','','2022-03-28 16:09:36','2022-03-28 16:09:36','admin','Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrdhCajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetTrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCrvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gb0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZpO13unz-8OD_cPzufP_wFAAD__w.SoqYO24kUPJixCZpzcc82QgINB87iiYupneu1k153_3lhEk1FopYOE9gHICQlWjDByPRM77-t3HSf8MwX0NGRwContent-Type ：application/json;charset=utf-8','{\n  \"id\": \"\",\n  \"enviromentname\": \"XRlfZ\",\n  \"envtype\": \"功能\",\n  \"memo\": \"<< usernamefromdb >>\",\n  \"creator\": \"admin\"\n}','http://127.0.0.1:8080/enviroment','POST'),(9,3,2,'00009',1,'成功','{\n \"code\":200\n}','',199,'','','2022-03-28 16:11:00','2022-03-28 16:11:00','admin','Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrZhGajEuQrtxuozHqH8t2N4sQIcSQkRKRERJwHzQcg8ZVZZddJsLvlfZ9z24v1r4_hW04XU5qnO1yujupLV4P-dJZrYIJr5S3aphMuKhxvJPuaCYTTXcQlZ06g8mG2LGDUV5fO4PNjeogKK3XbYmpBq0RXmRCkkwgEhhPkkKdhcDjXwpLSwg6lhByLCjgWJYfTlUpAUSJIU1ZoCkQlFNezaHoQgHdoB71uow22nWpoMxu8NWkKsImVSXmN2XYhNUShXpVeiUkvgfuIOHMiIeqwLJBJDQIg7Iw0GAA41-vflaLNt641cfMEgOG7cxyAzHLZcSE9RKzbsUQVTzSrA6dmmzYrVrNO3XZvFOZTbu12bxUr49UnKM4vObExDGJswEjsVnt2sUfr2-vuLri3jaXVtxYcV2zsfrR-JoCVpsMbh0P3rzuZlzfLj1_c62Ljcah3iPXuMvaSm24lapwo5OMux2HvXouuM6wvX0iuIWwvX0WuEUw47bFwkWiNUGyBABKCEdBwSgp1Op_G1L-HeQWSdHFgbiiKbE4OTQ4FfUVI1HkwKyhLGmMR5XDUWO0WXbr19ngw8gkxldOAjAnIZhGCHME5lGFYObjOzS84gswHzUNyqipUgaiUxmVcrPSV7vkTiRLleJQA3IITLrwyCHMk9FbNG5S8JvENYEqC0jMAhQziMUsCRvy1RGmxA78Kgm_LTDwqyV8rHIcA_9VYxLxlZPAzElIphHGnILJn7D5fs3Hq75c89mab-b8-sZoKE5rDC0yhZJMoSQwlCSG-hVvf1pgHK5TVlqnoLTClLTGiKC9dbH-n7n1MFjaidHaCdeaSG5tKjGpHd8kXBKSFJBAAQDWlAsK46LKv8fxePkzjr33PR-Z_VEq0Bv9F4PVeiPqeYxw27hkFcq2UWVQ2TYqFle_erXDwsXLV5sZJV6_2uyBdzVtRtLB7lWgiewBE1kG_F4jmGCt-izEOYgzaPYv9i72_efnx9uv77cf326fP_3-8vX4k9U8udPl_vn5xcP5_vzs4cNfAAAA__8.DsWj1Fgs8rnX4nt5kVF4f0RVMd0D_EaSQ8E-I9D9N10VItYvGOhG1vxt18-R2U8PN-WW2P_cJDgTTtFgB-zQBQContent-Type ：application/json;charset=utf-8','{\n  \"id\": \"\",\n  \"enviromentname\": \"8RRAl\",\n  \"envtype\": \"功能\",\n  \"memo\": \"admin\",\n  \"creator\": \"admin\"\n}','http://127.0.0.1:8080/enviroment','POST');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='api用例报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_report_performance`
--

LOCK TABLES `apicases_report_performance` WRITE;
/*!40000 ALTER TABLE `apicases_report_performance` DISABLE KEYS */;
INSERT INTO `apicases_report_performance` VALUES (1,4,3,'性能测试0001',1,'成功','{\n  \"code\":200\n}','',339,'','','2022-03-28 15:11:12','2022-03-28 15:11:12','admin','Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1ilAEIhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5PXzy_u3328u72ycd_AAAA__8.s4BkPolAQeVOBTUoEYaiahg-bUZhfKM-Ecs8oBjU_01WX5MQeRPANIvPQUKttmTzysSNJ_xrFihfIDDWsHdE6wContent-Type ：application/json;charset=utf-8','{  \"id\": \"\",  \"enviromentname\": \"QFOgB\",  \"envtype\": \"功能\",  \"memo\": \"\",  \"creator\": \"admin\"}','http://127.0.0.1:8080/enviroment','POST'),(2,4,3,'性能测试0001',1,'成功','{\n \"code\":200\n}','',11,'','','2022-03-28 15:11:12','2022-03-28 15:11:12','admin','Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1ilAEIhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5PXzy_u3328u72ycd_AAAA__8.s4BkPolAQeVOBTUoEYaiahg-bUZhfKM-Ecs8oBjU_01WX5MQeRPANIvPQUKttmTzysSNJ_xrFihfIDDWsHdE6wContent-Type ：application/json;charset=utf-8','{  \"id\": \"\",  \"enviromentname\": \"fsTJv\",  \"envtype\": \"功能\",  \"memo\": \"\",  \"creator\": \"admin\"}','http://127.0.0.1:8080/enviroment','POST'),(3,4,3,'性能测试0001',1,'成功','{\n  \"code\":200\n}','',14,'','','2022-03-28 15:11:12','2022-03-28 15:11:12','admin','Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1ilAEIhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5PXzy_u3328u72ycd_AAAA__8.s4BkPolAQeVOBTUoEYaiahg-bUZhfKM-Ecs8oBjU_01WX5MQeRPANIvPQUKttmTzysSNJ_xrFihfIDDWsHdE6wContent-Type ：application/json;charset=utf-8','{  \"id\": \"\",  \"enviromentname\": \"5kVNj\",  \"envtype\": \"功能\",  \"memo\": \"\",  \"creator\": \"admin\"}','http://127.0.0.1:8080/enviroment','POST'),(4,4,3,'性能测试0001',1,'成功','{\n  \"code\":200\n}','',10,'','','2022-03-28 15:11:12','2022-03-28 15:11:12','admin','Authorization ：Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1ilAEIhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5PXzy_u3328u72ycd_AAAA__8.s4BkPolAQeVOBTUoEYaiahg-bUZhfKM-Ecs8oBjU_01WX5MQeRPANIvPQUKttmTzysSNJ_xrFihfIDDWsHdE6wContent-Type ：application/json;charset=utf-8','{  \"id\": \"\",  \"enviromentname\": \"348WA\",  \"envtype\": \"功能\",  \"memo\": \"\",  \"creator\": \"admin\"}','http://127.0.0.1:8080/enviroment','POST');
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='api计划批量用例统计报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_reportstatics`
--

LOCK TABLES `apicases_reportstatics` WRITE;
/*!40000 ALTER TABLE `apicases_reportstatics` DISABLE KEYS */;
INSERT INTO `apicases_reportstatics` VALUES (1,1,1,'百度测试000001',1,1,1,0,198,'2022-03-28 12:56:21','2022-03-28 12:56:21','admin'),(2,2,2,'测试中心回归测试00001',1,1,1,0,147,'2022-03-28 13:06:21','2022-03-28 13:06:21','admin'),(3,2,2,'测试中心回归00002',1,1,1,0,133,'2022-03-28 13:15:45','2022-03-28 13:15:45','admin'),(4,2,2,'000003',1,1,1,0,140,'2022-03-28 14:49:06','2022-03-28 14:49:06','admin'),(5,2,2,'000004',1,1,1,0,128,'2022-03-28 14:51:12','2022-03-28 14:51:12','admin'),(6,2,2,'00005',1,1,1,0,148,'2022-03-28 15:01:06','2022-03-28 15:01:06','admin'),(7,1,1,'百度测试0001',1,1,1,0,177,'2022-03-28 15:25:36','2022-03-28 15:25:36','admin'),(8,2,2,'00008',1,1,1,0,202,'2022-03-28 16:09:36','2022-03-28 16:09:36','admin'),(9,2,2,'00009',1,1,1,0,199,'2022-03-28 16:11:00','2022-03-28 16:11:00','admin');
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
  `deployunitname` varchar(64) DEFAULT NULL COMMENT '发布单元',
  `apiname` varchar(64) DEFAULT NULL COMMENT 'api',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='api用例变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apicases_variables`
--

LOCK TABLES `apicases_variables` WRITE;
/*!40000 ALTER TABLE `apicases_variables` DISABLE KEYS */;
INSERT INTO `apicases_variables` VALUES (1,2,'正常登陆',1,'token','绑定正常登陆获取token','2022-03-28 13:03:45','2022-03-28 13:03:45','admin','testcenterservice','登陆');
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
  `deployunitid` bigint(20) unsigned DEFAULT NULL COMMENT '发布单元id',
  `caseid` bigint(20) unsigned DEFAULT NULL COMMENT '接口caseid',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `apiid` int(11) NOT NULL COMMENT 'apiid',
  `conditionname` varchar(64) DEFAULT NULL COMMENT '条件名',
  `deployunitname` varchar(64) DEFAULT NULL COMMENT '发布单元名',
  `apiname` varchar(64) DEFAULT NULL COMMENT 'api名',
  `casename` varchar(64) DEFAULT NULL COMMENT '接口名',
  `subconditionname` varchar(20) DEFAULT NULL COMMENT '子条件名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='接口条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condition_api`
--

LOCK TABLES `condition_api` WRITE;
/*!40000 ALTER TABLE `condition_api` DISABLE KEYS */;
INSERT INTO `condition_api` VALUES (1,1,2,2,'','2022-03-28 13:05:12','2022-03-28 13:05:12','admin',3,'测试中心回归测试前置父条件','testcenterservice','登陆','正常登陆','前置登陆'),(2,2,2,2,'','2022-03-28 15:03:15','2022-03-28 15:03:15','admin',3,'新增环境用例前置父条件','testcenterservice','登陆','正常登陆','新增测试环境前置登陆'),(3,3,2,2,'','2022-03-28 15:09:57','2022-03-28 15:09:57','admin',3,'测试中心性能测试前置父条件','testcenterservice','登陆','正常登陆','测试中心性能测试前置登陆');
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
  `subconditionname` varchar(20) DEFAULT NULL COMMENT '子条件名',
  `assembleid` bigint(20) unsigned DEFAULT NULL COMMENT '组件id',
  `assemblename` varchar(20) DEFAULT NULL COMMENT '组件名',
  `conditionname` varchar(20) DEFAULT NULL COMMENT '条件名',
  `enviromentname` varchar(20) DEFAULT NULL COMMENT '环境名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='db条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condition_db`
--

LOCK TABLES `condition_db` WRITE;
/*!40000 ALTER TABLE `condition_db` DISABLE KEYS */;
INSERT INTO `condition_db` VALUES (1,1,1,'Select','select name,password from account','','','2022-03-28 15:30:40','2022-03-28 15:30:40','admin','获取用户表用户名',1,'mysql组件','测试中心回归测试前置父条件','功能测试环境');
/*!40000 ALTER TABLE `condition_db` ENABLE KEYS */;
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
  `subconditionname` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '子条件名',
  `conditionorder` bigint(20) unsigned DEFAULT NULL COMMENT '条件顺序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `conditionname` varchar(20) DEFAULT NULL COMMENT '父条件名',
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
  `conditionname` varchar(50) DEFAULT NULL COMMENT '条件名',
  `subconditionname` varchar(20) DEFAULT NULL COMMENT '子条件名',
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='数据库条件变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dbcondition_variables`
--

LOCK TABLES `dbcondition_variables` WRITE;
/*!40000 ALTER TABLE `dbcondition_variables` DISABLE KEYS */;
INSERT INTO `dbcondition_variables` VALUES (1,1,'获取用户表用户名',1,'usernamefromdb','name',1,'获取用户表第一行的name字段值','2022-03-28 15:31:18','2022-03-28 15:31:18');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='数据库变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dbvariables`
--

LOCK TABLES `dbvariables` WRITE;
/*!40000 ALTER TABLE `dbvariables` DISABLE KEYS */;
INSERT INTO `dbvariables` VALUES (1,'usernamefromdb','数据库获取用户数据','String','admin','数据库获取用户表的数据','2022-03-28 15:27:48','2022-03-28 15:27:48');
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
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
  `protocal` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '协议',
  `port` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '访问端口',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='发布单元表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deployunit`
--

LOCK TABLES `deployunit` WRITE;
/*!40000 ALTER TABLE `deployunit` DISABLE KEYS */;
INSERT INTO `deployunit` VALUES (1,'测试百度','http','80','测试百度例子发布单元','2022-03-28 12:38:25','2022-03-28 12:38:40','admin'),(2,'testcenterservice','http','8080','AutoMeter测试中心服务','2022-03-28 12:40:23','2022-03-28 12:44:01','admin');
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
  `dicitmevalue` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项值',
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
INSERT INTO `dictionary` VALUES (1,'测试环境','testenviroment1','功能测试环境1','te112','2019-07-01 00:00:00','2020-04-21 17:04:47'),(2,'测试环境','testenviroment','功能测试环境2','te2','2019-07-01 00:00:00','2021-10-10 10:51:08'),(5,'http请求方式','httpmethod','查询','get','2020-04-17 17:57:30','2021-10-10 10:55:55'),(6,'http请求方式','httpmethod','提交数据','post','2020-04-17 17:59:02','2020-11-15 15:11:35'),(9,'测试环境','测试环境','测试环境','测试环境','2020-04-20 15:42:10','2021-09-24 16:09:53'),(17,'访问方式','httpvisittype','http访问方式','GET','2020-05-18 21:05:24','2021-09-24 16:09:55'),(18,'访问方式','httpvisittype','http访问方式','POST','2020-05-18 21:05:55','2021-09-24 16:09:57'),(19,'服务器作用','machineuse','部署','部署发布单元','2020-09-09 12:26:21','2020-09-09 12:09:53'),(20,'服务器作用','machineuse','执行测试','功能测试用例执行机','2020-09-09 12:27:40','2020-09-09 12:09:26'),(21,'服务器作用','machineuse','执行测试','性能测试执行机','2020-09-09 12:29:25','2021-09-24 16:09:01'),(27,'数据库类型','DBType','用例前后置数据库类型','Mysql','2020-11-02 08:29:29','2021-10-10 10:56:03'),(28,'数据库类型','DBType','用例前后置数据库类型','Oracle','2020-11-02 08:30:29','2021-09-24 16:09:01'),(29,'数据库类型','DBType','用例前后置数据库类型','Sqlserver','2020-11-02 08:31:05','2021-09-24 16:09:03'),(34,'环境部署内容','machinedeploy','数据库','mysql','2020-11-06 15:43:18','2021-09-24 16:09:06'),(35,'环境部署内容','machinedeploy','数据库','oracle','2020-11-06 15:43:36','2021-09-24 16:09:08'),(39,'api请求格式','requestcontentype','请求数据格式','Form表单','2020-11-10 08:43:55','2021-09-24 16:09:15'),(40,'api请求格式','requestcontentype','请求数据格式','JSON','2020-11-10 08:44:19','2021-09-24 16:09:17'),(41,'api响应格式','responecontenttype','响应数据格式','json','2020-11-10 08:50:28','2021-09-24 16:09:19'),(42,'api响应格式','responecontenttype','响应数据格式','html','2020-11-10 08:55:14','2021-09-24 16:09:33'),(43,'http请求方式','httpmethod','更新','put','2020-11-15 15:41:55','2021-09-24 16:09:35'),(44,'http请求方式','httpmethod','删除','delete','2020-11-15 15:42:14','2021-09-24 16:09:37'),(45,'访问方式','httpvisittype','更新','PUT','2020-11-15 15:47:03','2021-09-24 16:09:39'),(46,'访问方式','httpvisittype','删除','DELETE','2020-11-15 15:47:20','2021-09-24 16:09:41'),(47,'功能执行机最大并发数','FunctionJmeterProcess','功能执行机并发Jmeter进程','2','2020-11-28 17:02:39','2021-04-02 12:04:57'),(52,'性能执行机Jmeter并发数','PerformanceJmeterProcess','性能测试Jmeter并行数','1','2021-04-07 09:08:10','2021-05-26 23:05:05'),(53,'执行计划业务类型','planbusinesstype','执行计划业务类型','常规测试','2021-04-20 17:24:17','2021-09-24 16:09:08'),(54,'执行计划业务类型','planbusinesstype','执行计划业务类型','CI自动化测试','2021-04-20 17:24:48','2021-09-24 16:09:10'),(55,'环境部署内容','machinedeploy','数据库','pgsql','2020-11-06 15:43:36','2021-09-24 16:09:08'),(56,'钉钉通知','DingDing','DingDingToken','http://testtoken配置钉钉机器人token','2022-03-04 15:17:07','2022-03-04 16:05:57'),(57,'邮件通知','Mail','MailInfo','qq.smtp.qq.com,465,from@qq.com,mail,pass','2022-03-04 16:16:04','2022-03-04 16:16:04');
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
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'jmeter-class',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待分配，已分配，已结束，调度异常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `plantype` varchar(20) DEFAULT NULL COMMENT '计划类型',
  `threadnum` bigint(20) DEFAULT NULL COMMENT '线程数',
  `loops` bigint(20) DEFAULT NULL COMMENT '循环数',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dispatch`
--

LOCK TABLES `dispatch` WRITE;
/*!40000 ALTER TABLE `dispatch` DISABLE KEYS */;
INSERT INTO `dispatch` VALUES (1,1,'百度功能测试集合例子',1,'百度测试000001',1,'执行机192.168.3.95',1,'访问百度首页功能','httpapi','测试百度','','已完成','2022-03-28 12:56:04','2022-03-28 12:56:21','功能',1,1,NULL),(2,2,'测试中心回归测试集合',2,'测试中心回归测试00001',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','已完成','2022-03-28 13:05:52','2022-03-28 13:06:21','功能',1,1,NULL),(3,2,'测试中心回归测试集合',3,'测试中心回归00002',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','已完成','2022-03-28 13:14:49','2022-03-28 13:15:45','功能',1,1,NULL),(4,2,'测试中心回归测试集合',4,'000003',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','已完成','2022-03-28 14:48:35','2022-03-28 14:49:06','功能',1,1,NULL),(5,2,'测试中心回归测试集合',5,'000004',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','已完成','2022-03-28 14:50:49','2022-03-28 14:51:12','功能',1,1,NULL),(6,2,'测试中心回归测试集合',6,'00005',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','已完成','2022-03-28 15:00:38','2022-03-28 15:01:06','功能',1,1,NULL),(7,3,'测试中心性能测试集合',7,'性能测试0001',1,'执行机192.168.3.95',4,'新增环境性能测试','httpapi','testcenterservice','','已完成','2022-03-28 15:10:48','2022-03-28 15:11:12','性能',2,2,NULL),(8,1,'百度功能测试集合例子',8,'百度测试0001',1,'执行机192.168.3.95',1,'访问百度首页功能','httpapi','测试百度','','已完成','2022-03-28 15:25:14','2022-03-28 15:25:36','功能',1,1,NULL),(9,2,'测试中心回归测试集合',9,'测试数据库变量',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','条件失败','2022-03-28 15:32:28','2022-03-28 15:32:35','功能',1,1,NULL),(10,2,'测试中心回归测试集合',10,'000006',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','条件失败','2022-03-28 16:00:22','2022-03-28 16:00:30','功能',1,1,NULL),(11,2,'测试中心回归测试集合',11,'00007',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','条件失败','2022-03-28 16:05:45','2022-03-28 16:05:55','功能',1,1,NULL),(12,2,'测试中心回归测试集合',12,'00008',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','已完成','2022-03-28 16:09:11','2022-03-28 16:09:36','功能',1,1,NULL),(13,2,'测试中心回归测试集合',13,'00009',1,'执行机192.168.3.95',3,'正常新增环境','httpapi','testcenterservice','','已完成','2022-03-28 16:10:34','2022-03-28 16:11:00','功能',1,1,NULL);
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='环境表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enviroment`
--

LOCK TABLES `enviroment` WRITE;
/*!40000 ALTER TABLE `enviroment` DISABLE KEYS */;
INSERT INTO `enviroment` VALUES (1,'功能测试环境','功能','功能测试环境','2022-03-28 12:37:35','2022-03-28 12:37:35','admin'),(6,'性能测试环境','性能','性能测试环境','2022-03-28 15:08:26','2022-03-28 15:08:26','admin');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='环境组件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enviroment_assemble`
--

LOCK TABLES `enviroment_assemble` WRITE;
/*!40000 ALTER TABLE `enviroment_assemble` DISABLE KEYS */;
INSERT INTO `enviroment_assemble` VALUES (1,'mysql组件','mysql','test,test,3306,testcenter','mysql中间件访问连接字','2022-03-28 15:29:03','2022-03-28 15:29:03','admin');
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT='环境服务器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `envmachine`
--

LOCK TABLES `envmachine` WRITE;
/*!40000 ALTER TABLE `envmachine` DISABLE KEYS */;
INSERT INTO `envmachine` VALUES (20,19,'性能测试环境',7,'测试服务器','2020-12-07 16:37:11','2021-10-10 10:48:34','admin'),(21,5,'功能测试环境',11,'4','2021-04-23 19:38:48','2021-04-23 19:38:48',NULL),(22,19,'性能测试环境',12,'6','2021-04-26 19:24:51','2021-04-26 19:24:51','admin'),(23,5,'功能测试环境',7,'测试服务器','2021-05-13 09:53:38','2021-10-10 10:10:00','admin'),(24,5,'功能测试环境',25,'1111','2021-09-24 15:38:23','2021-09-24 15:38:23','admin'),(25,19,'性能测试环境',28,'2','2021-10-10 10:13:10','2021-10-10 10:13:10','admin'),(26,5,'功能测试环境',32,'服务器2','2021-12-16 23:12:29','2021-12-16 23:12:29','admin');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='执行计划表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executeplan`
--

LOCK TABLES `executeplan` WRITE;
/*!40000 ALTER TABLE `executeplan` DISABLE KEYS */;
INSERT INTO `executeplan` VALUES (1,1,'功能测试环境','百度功能测试集合例子','new','功能','百度功能测试用例集合例子','2022-03-28 12:52:45','2022-03-28 12:52:45','常规测试','admin','单机运行'),(2,1,'功能测试环境','测试中心回归测试集合','new','功能','测试中心回归测试集合','2022-03-28 13:01:52','2022-03-28 13:01:52','常规测试','admin','单机运行'),(3,6,'性能测试环境','测试中心性能测试集合','new','性能','测试中心性能测试集合','2022-03-28 15:08:49','2022-03-28 15:08:49','常规测试','admin','多机并行');
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
  `keyvalue` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '值',
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
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发布单元id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '发布单元',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'API名',
  `testcaseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用例名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='执行计划用例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executeplan_testcase`
--

LOCK TABLES `executeplan_testcase` WRITE;
/*!40000 ALTER TABLE `executeplan_testcase` DISABLE KEYS */;
INSERT INTO `executeplan_testcase` VALUES (1,1,1,1,'测试百度','百度首页',1,'访问百度首页功能','2022-03-28 12:52:55','2022-03-28 12:52:55','admin'),(2,2,4,2,'testcenterservice','新增环境',3,'正常新增环境','2022-03-28 13:02:06','2022-03-28 13:02:06','admin'),(3,3,4,2,'testcenterservice','新增环境',4,'新增环境性能测试','2022-03-28 15:09:00','2022-03-28 15:09:00','admin');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='执行计划表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executeplanbatch`
--

LOCK TABLES `executeplanbatch` WRITE;
/*!40000 ALTER TABLE `executeplanbatch` DISABLE KEYS */;
INSERT INTO `executeplanbatch` VALUES (1,1,'百度测试000001','2022-03-28 12:56:04','2022-03-28 12:56:04','admin','已完成','平台','百度功能测试集合例子','立即执行',':00'),(2,2,'测试中心回归测试00001','2022-03-28 13:05:52','2022-03-28 13:05:52','admin','已完成','平台','测试中心回归测试集合','立即执行',':00'),(3,2,'测试中心回归00002','2022-03-28 13:14:49','2022-03-28 13:14:49','admin','已完成','平台','测试中心回归测试集合','立即执行',':00'),(4,2,'000003','2022-03-28 14:48:34','2022-03-28 14:48:34','admin','已完成','平台','测试中心回归测试集合','立即执行',':00'),(5,2,'000004','2022-03-28 14:50:49','2022-03-28 14:50:49','admin','已完成','平台','测试中心回归测试集合','立即执行',':00'),(6,2,'00005','2022-03-28 15:00:38','2022-03-28 15:00:38','admin','已完成','平台','测试中心回归测试集合','立即执行',':00'),(7,3,'性能测试0001','2022-03-28 15:10:48','2022-03-28 15:10:48','admin','待执行','平台','测试中心性能测试集合','立即执行',':00'),(8,1,'百度测试0001','2022-03-28 15:25:14','2022-03-28 15:25:14','admin','已完成','平台','百度功能测试集合例子','立即执行',':00'),(9,2,'测试数据库变量','2022-03-28 15:32:28','2022-03-28 15:32:35','admin','条件失败','平台','测试中心回归测试集合','立即执行',':00'),(10,2,'000006','2022-03-28 16:00:22','2022-03-28 16:00:30','admin','条件失败','平台','测试中心回归测试集合','立即执行',':00'),(11,2,'00007','2022-03-28 16:05:45','2022-03-28 16:05:55','admin','条件失败','平台','测试中心回归测试集合','立即执行',':00'),(12,2,'00008','2022-03-28 16:09:11','2022-03-28 16:09:11','admin','已完成','平台','测试中心回归测试集合','立即执行',':00'),(13,2,'00009','2022-03-28 16:10:34','2022-03-28 16:10:34','admin','已完成','平台','测试中心回归测试集合','立即执行',':00');
/*!40000 ALTER TABLE `executeplanbatch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `macdepunit`
--

DROP TABLE IF EXISTS `macdepunit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `macdepunit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `depunitid` bigint(20) unsigned DEFAULT NULL COMMENT '发布单元Id',
  `assembleid` bigint(20) unsigned DEFAULT NULL COMMENT '组件Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='服务器发布单元表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `macdepunit`
--

LOCK TABLES `macdepunit` WRITE;
/*!40000 ALTER TABLE `macdepunit` DISABLE KEYS */;
INSERT INTO `macdepunit` VALUES (1,1,NULL,'测试百度','发布单元',1,'测试服务器',1,'功能测试环境','域名','www.baidu.com','2022-03-28 12:39:02','2022-03-28 12:41:25','admin'),(2,2,NULL,'testcenterservice','发布单元',1,'测试服务器',1,'功能测试环境','ip','','2022-03-28 12:40:40','2022-03-28 12:41:21','admin'),(3,2,NULL,'testcenterservice','发布单元',1,'测试服务器',6,'性能测试环境','ip','/','2022-03-28 15:10:33','2022-03-28 15:10:33','admin'),(4,NULL,1,'mysql组件','组件',1,'测试服务器',1,'功能测试环境','ip','/','2022-03-28 16:08:56','2022-03-28 16:08:56','admin');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='服务器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine`
--

LOCK TABLES `machine` WRITE;
/*!40000 ALTER TABLE `machine` DISABLE KEYS */;
INSERT INTO `machine` VALUES (1,'测试服务器','127.0.0.1','2','256','16','2022-03-28 12:37:21','2022-03-28 12:41:09','admin');
/*!40000 ALTER TABLE `machine` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='性能报告路径表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performancereportsource`
--

LOCK TABLES `performancereportsource` WRITE;
/*!40000 ALTER TABLE `performancereportsource` DISABLE KEYS */;
INSERT INTO `performancereportsource` VALUES (1,3,7,'性能测试0001',1,4,'HttpApiPerformance',0.55,'/Users/season/Dev/AutoMetee/testplantform/slaverservice/performancereport/3-4-性能测试0001','已解析','2022-03-28 15:11:12','2022-03-28 15:11:12');
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
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'账号','account:list','列表'),(2,'账号','account:detail','详情'),(3,'账号','account:add','添加'),(4,'账号','account:update','更新'),(5,'账号','account:delete','删除'),(6,'账号','account:search','搜索'),(7,'角色','role:list','列表'),(8,'角色','role:detail','详情'),(9,'角色','role:add','添加'),(10,'角色','role:update','更新'),(11,'角色','role:delete','删除'),(12,'角色','role:search','搜索'),(13,'测试表','table:list','列表'),(14,'字典','dictionary:list','列表'),(15,'字典','dictionary:add','添加'),(16,'字典','dictionary:search','搜索'),(17,'字典','dictionary:update','修改'),(18,'字典','dictionary:delete','删除'),(19,'发布单元','depunit:list','列表'),(20,'发布单元','depunit:detail','详情'),(21,'发布单元','depunit:add','添加'),(22,'发布单元','depunit:update','更新'),(23,'发布单元','depunit:delete','删除'),(24,'发布单元','depunit:search','搜索'),(25,'测试环境','enviroment:list','列表'),(26,'测试环境','enviroment:detail','详情'),(27,'测试环境','enviroment:add','添加'),(28,'测试环境','enviroment:update','更新'),(29,'测试环境','enviroment:delete','删除'),(30,'测试环境','enviroment:search','搜索'),(31,'服务器','machine:list','列表'),(32,'服务器','machine:detail','详情'),(33,'服务器','machine:add','添加'),(34,'服务器','machine:update','更新'),(35,'服务器','machine:delete','删除'),(36,'服务器','machine:search','搜索'),(37,'测试人员','tester:list','列表'),(38,'测试人员','tester:detail','详情'),(39,'测试人员','tester:add','添加'),(40,'测试人员','tester:update','更新'),(41,'测试人员','tester:delete','删除'),(42,'测试人员','tester:search','搜索'),(43,'api','api:list','列表'),(44,'api','api:detail','详情'),(45,'api','api:add','添加'),(46,'api','api:update','更新'),(47,'api','api:delete','删除'),(48,'api','api:search','搜索'),(49,'api参数','apiparams:list','列表'),(50,'api参数','apiparams:detail','详情'),(51,'api参数','apiparams:add','添加'),(52,'api参数','apiparams:update','更新'),(53,'api参数','apiparams:delete','删除'),(54,'api参数','apiparams:search','搜索'),(55,'环境服务器','envmachine:list','列表'),(56,'环境服务器','envmachine:detail','详情'),(57,'环境服务器','envmachine:add','添加'),(58,'环境服务器','envmachine:update','更新'),(59,'环境服务器','envmachine:delete','删除'),(60,'环境服务器','envmachine:search','搜索'),(61,'服务器发布单元','macdepunit:list','列表'),(62,'服务器发布单元','macdepunit:detail','详情'),(63,'服务器发布单元','macdepunit:add','添加'),(64,'服务器发布单元','macdepunit:update','更新'),(65,'服务器发布单元','macdepunit:delete','删除'),(66,'服务器发布单元','macdepunit:search','搜索'),(67,'API用例库','apicases:list','列表'),(68,'API用例库','apicases:detail','详情'),(69,'API用例库','apicases:add','增加'),(70,'API用例库','apicases:update','更新'),(71,'API用例库','apicases:delete','删除'),(72,'API用例库','apicases:search','查询'),(73,'执行机','slaver:list','列表'),(74,'执行机','slaver:detail','详情'),(75,'执行机','slaver:add','增加'),(76,'执行机','slaver:update','更新'),(77,'执行机','slaver:delete','删除'),(78,'执行机','slaver:search','查询'),(79,'执行计划','executeplan:list','列表'),(80,'执行计划','executeplan:detail','详情'),(81,'执行计划','executeplan:add','增加'),(82,'执行计划','executeplan:update','更新'),(83,'执行计划','executeplan:delete','删除'),(84,'执行计划','executeplan:search','查询'),(85,'api报告','apireport:list','列表'),(86,'api报告','apireport:detail','详情'),(87,'api报告','apireport:add','增加'),(88,'api报告','apireport:update','更新'),(89,'api报告','apireport:delete','删除'),(90,'api报告','apireport:search','查询'),(91,'API用例库','apicases:params','参数'),(92,'用例前后条件','apicases_condition:list','列表'),(93,'用例前后条件','apicases_condition:detail','详情'),(94,'用例前后条件','apicases_condition:add','增加'),(95,'用例前后条件','apicases_condition:update','更新'),(96,'用例前后条件','apicases_condition:delete','删除'),(97,'用例前后条件','apicases_condition:search','查询'),(98,'环境组件','enviroment_assemble:list','列表'),(99,'环境组件','enviroment_assemble:detail','详情'),(100,'环境组件','enviroment_assemble:add','增加'),(101,'环境组件','enviroment_assemble:update','更新'),(102,'环境组件','enviroment_assemble:delete','删除'),(103,'环境组件','enviroment_assemble:search','查询'),(104,'调度','dispatch:list','列表'),(105,'调度','dispatch:detail','详情'),(106,'调度','dispatch:add','增加'),(107,'调度','dispatch:update','更新'),(108,'调度','dispatch:delete','删除'),(109,'调度','dispatch:search','查询'),(110,'性能报告','apiperformancereport:list','列表'),(111,'性能报告','apiperformancereport:detail','列表'),(112,'性能报告','apiperformancereport:add','列表'),(113,'性能报告','apiperformancereport:update','列表'),(114,'性能报告','apiperformancereport:delete','列表'),(115,'性能报告','apiperformancereport:search','列表'),(116,'性能报告','apiperformancestatistics:list','列表'),(117,'性能报告','apiperformancestatistics:detail','详情'),(118,'性能报告','apiperformancestatistics:add','增加'),(119,'性能报告','apiperformancestatistics:update','更新'),(120,'性能报告','apiperformancestatistics:delete','删除'),(121,'性能报告','apiperformancestatistics:search','查询'),(122,'功能报告统计','apireportstatics:list','列表'),(123,'功能报告统计','apireportstatics:search','查询'),(124,'执行计划批次','executeplanbatch:list','列表'),(125,'执行计划批次','executeplanbatch:detail','详情'),(126,'执行计划批次','executeplanbatch:add','增加'),(127,'执行计划批次','executeplanbatch:update','更新'),(128,'执行计划批次','executeplanbatch:delete','删除'),(129,'执行计划批次','executeplanbatch:search','查询'),(130,'条件管理','condition:list','列表'),(131,'条件管理','condition:search','查询'),(132,'条件管理','condition:add','增加'),(133,'条件管理','condition:detail','详情'),(134,'条件管理','condition:update','更新'),(135,'条件管理','condition:delete','删除'),(136,'api条件','apicondition:list','列表'),(137,'api条件','apicondition:search','查询'),(138,'api条件','apicondition:add','增加'),(139,'api条件','apicondition:detail','详情'),(140,'api条件','apicondition:update','更新'),(141,'api条件','apicondition:delete','删除'),(142,'变量管理','testvariables:list','列表'),(143,'变量管理','testvariables:search','查询'),(144,'变量管理','testvariables:add','增加'),(145,'变量管理','testvariables:detail','详情'),(146,'变量管理','testvariables:update','更新'),(147,'变量管理','testvariables:delete','删除'),(148,'用例变量','ApicasesVariables:list','列表'),(149,'用例变量','ApicasesVariables:search','查询'),(150,'用例变量','ApicasesVariables:add','增加'),(151,'用例变量','ApicasesVariables:detail','详情'),(152,'用例变量','ApicasesVariables:update','更新'),(153,'用例变量','ApicasesVariables:delete','删除'),(154,'变量值','testvariablesvalue:list','列表'),(155,'变量值','testvariablesvalue:search','查询'),(156,'变量值','testvariablesvalue:add','增加'),(157,'变量值','testvariablesvalue:detail','详情'),(158,'变量值','testvariablesvalue:update','更新'),(159,'变量值','testvariablesvalue:delete','删除'),(160,'条件报告','testconditionreport:list','列表'),(161,'条件报告','testconditionreport:search','查询'),(162,'条件报告','testconditionreport:add','增加'),(163,'条件报告','testconditionreport:detail','详情'),(164,'条件报告','testconditionreport:update','更新'),(165,'条件报告','testconditionreport:delete','删除'),(166,'脚本条件','scriptcondition:list','列表'),(167,'脚本条件','scriptcondition:detail','详情'),(168,'脚本条件','scriptcondition:add','添加'),(169,'脚本条件','scriptcondition:update','更新'),(170,'脚本条件','scriptcondition:delete','删除'),(171,'脚本条件','scriptcondition:search','搜索'),(172,'数据库条件','dbcondition:list','列表'),(173,'数据库条件','dbcondition:detail','详情'),(174,'数据库条件','dbcondition:add','添加'),(175,'数据库条件','dbcondition:update','更新'),(176,'数据库条件','dbcondition:delete','删除'),(177,'数据库条件','dbcondition:search','搜索'),(178,'条件顺序','conditionorder:list','列表'),(179,'条件顺序','conditionorder:detail','详情'),(180,'条件顺序','conditionorder:add','添加'),(181,'条件顺序','conditionorder:moveup','上移'),(182,'条件顺序','conditionorder:movedown','下移'),(183,'条件顺序','conditionorder:search','搜索'),(185,'随机变量','variables:list','列表'),(186,'随机变量','variables:detail','详情'),(187,'随机变量','variables:update','修改'),(188,'随机变量','variables:delete','删除'),(189,'随机变量','variables:add','添加'),(190,'随机变量','variables:search','搜索'),(191,'数据库变量','dbvariables:search','搜索'),(192,'数据库变量','dbvariables:add','添加'),(193,'数据库变量','dbvariables:delete','删除'),(194,'数据库变量','dbvariables:update','更新'),(195,'数据库变量','dbvariables:detail','修改'),(196,'数据库变量','dbvariables:list','查询'),(197,'项目迭代','project:search','搜索'),(198,'项目迭代','project:add','添加'),(199,'项目迭代','project:delete','删除'),(200,'项目迭代','project:update','更新'),(201,'项目迭代','project:detail','修改'),(202,'项目迭代','project:list','查询');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目迭代表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
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
INSERT INTO `role` VALUES (1,'超级管理员','2019-07-01 00:00:00','2019-07-01 00:00:00'),(2,'普通用户','2019-07-01 00:00:00','2019-07-01 00:00:00'),(3,'测试','2019-07-01 00:00:00','2019-07-01 00:00:00');
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
INSERT INTO `role_permission` VALUES (3,1),(3,5);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api发布单元用例统计报告表';
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
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `register_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `login_time` datetime DEFAULT NULL COMMENT '上一次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_account_name` (`name`),
  UNIQUE KEY `ix_account_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='测试表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'admin11111@qq.com','admin','$2a$10$wg0f10n.30UbU.9hPpucbef/ya62LdTKs72xJfjxvTFsL0Xaewbra','2019-07-01 00:00:00','2019-07-01 00:00:00'),(2,'editor@qq.com','editor','$2a$10$/m4SgZ.ZFVZ7rcbQpJW2tezmuhf/UzQtpAtXb0WZiAE3TeHxq2DYu','2019-07-02 00:00:00','2019-07-02 00:00:00'),(3,'test@qq.com','test','$2a$10$.0gBYBHAtdkxUUQNg3kII.fqGOngF4BLe8JavthZFalt2QIXHlrhm','2019-07-03 00:00:00','2019-07-03 00:00:00');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='条件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testcondition`
--

LOCK TABLES `testcondition` WRITE;
/*!40000 ALTER TABLE `testcondition` DISABLE KEYS */;
INSERT INTO `testcondition` VALUES (1,'测试中心回归测试前置父条件',2,'测试中心回归测试集合','测试集合','前置条件','测试中心回归测试集合前置操作','2022-03-28 13:04:44','2022-03-28 13:04:44','admin'),(2,'新增环境用例前置父条件',3,'正常新增环境','测试用例','前置条件','新增环境测试用例前置父条件','2022-03-28 15:02:43','2022-03-28 15:02:43','admin'),(3,'测试中心性能测试前置父条件',3,'测试中心性能测试集合','测试集合','前置条件','测试中心性能测试前置父条件','2022-03-28 15:09:30','2022-03-28 15:09:30','admin');
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='条件报告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testcondition_report`
--

LOCK TABLES `testcondition_report` WRITE;
/*!40000 ALTER TABLE `testcondition_report` DISABLE KEYS */;
INSERT INTO `testcondition_report` VALUES (1,1,'前置条件',1,'{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8QagZRCajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetDrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCjvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gr0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZxO12ePn_24nz_5P58_vAXAAD__w.QkA36JqO3bRFeOrcs1cyNuX0-K23A1rv_8ACU6-IkOssyW21Pvh_AaLJqvL0UV0zUdH58HsSB2iqGWmgtcV8rg\"\n}','成功',169,'2022-03-28 13:05:55','2022-03-28 13:05:55',NULL,'测试中心回归测试00001','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(2,1,'前置条件',1,'{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4X-7NfAnTLYbBuljzV8CMa1mGGmtmamRmaOD7WNfHEOluaGg2knO69nwHhpXaDye_D6fzSY2zXU43J7WHS5SvNquVN_61clYNk_FnNY430h3NZILpDoKyU2cwWR86tjfK6UtnsG-jigSl9bovIdWgNcKLTEiSCUQC40lS6GYhMP5LYWkJQXEJIXFBAXFZfjhVpQQQJYY0ZYGmQFCbcmr2RRcK6AZ1p9dltMGuSwVldoOvJlURNqkqMb8pwyaslijUq9IrIfE9cAcJZ0Y8VAWWDSKhQRiUhYEGAxj3ZnWzWrRxZltdyCwxYNjOLDcQs1xGTFgvMetW9EGFmGa179Rkw27Vat6py-adymzarc3mpXp9pOIcxeE1JyaOSZwNGInNatcu_nh9e8XVFfe2ubTixorrmo3VjcbVFLDaZHDrePDm9TDj-m7p-fvWuthoHOo9co27rK3UhlupCjc6ybjbcTiq54LrDDvaJ4JbCDvaZ4FbBDPbvli4SLQmSJYAQAnhKCgYJYVa_X9Dyr2H3CIpujgQVzQlFieH-k0FfcFIFDkwayhLGuNR5XDUGG2Ww7p1NvgwMonxlZMAzEkIphHCHIG5U96bOX6Hhld8AeajpkEZNVXKQHQqo1JuVvpil9yJZKlSHGpADoFJFx45hLk3eg9mmxT8JnFNoMoCErMAxQxiMUvChnx1hCmxA79Kwm8LDPxqCR-rxGPgv2pMIr5yEpg5Cck0wphTMPkTNt-v-XjVl2s-W_PNNre-NRqK0xpDi0yhJFMoCQwliaFuxdufFhiH65SV1ikorTAlrTHCa2e3UP_P3HoYLO3EaO2Ea00ktzaVmNSBbxIuCUkKSKAAAGvKBYVxQeXf4xBf_oxj733PR2Z_lAr0Rg9isFpvRD3jCLeNS1ahbBtVBpVto2Jx9atXOyxcvHy1mVHi9avNHvhQ024kHexeBZrIHjCRZcDvNYIJ1qrPQpyDOINm_2LvYt9_f326_v5x_fn9-uXzn6_f4p-s5n47nR-_ePby-dNHt09uP_4DAAD__w.VSj-WqkyqiXc8ki_6yCN-K9vVcKGFgV3zSrQG0LXY3tBGNuF106opN-NGCPuqe9EnKnuiOhNu1KPirShUi21kQ\"\n}','成功',199,'2022-03-28 13:15:23','2022-03-28 13:15:23',NULL,'测试中心回归00002','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(3,1,'前置条件',1,'{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4X-7NfAnTLYbBuljzV8CMa1mGGmtmamRmaOD7WNfHEOluaGg2knO69nwHhpXaDye_D6fzSY2zXU43J7WHS5SvNquVN_61clYNk_FnNY430h3NZILpDoKyU2cwWR86tjfK6UtnsG-jigSl9bovIdWgNcKLTEiSCUQC40lS6GYhMP5LYWkJQXEJIXFBAXFZfjhVpQQQJYY0ZYGmQFCbcmr2RRcK6AZ1p9dltMGuSwVldoOvJlURNqkqMb8pwyaslijUq9IrIfE9cAcJZ0Y8VAWWDSKhQRiUhYEGAxj3ZnWzWrRxZltdyCwxYNjOLDcQs1xGTFgvMetW9EGFmGa179Rkw27Vat6py-adymzarc3mpXp9pOIcxeE1JyaOSZwNGInNatcu_nh9e8XVFfe2ubTixorrmo3VjcbVFLDaZHDrePDm9TDj-m7p-fvWuthoHOo9co27rK3UhlupCjc6ybjbcTiq54LrDDvaJ4JbCDvaZ4FbBDPbvli4SLQmSJYAQAnhKCgYJYVa_X9Dyr2H3CIpujgQVzQlFieH-k0FfcFIFDkwayhLGuNR5XDUGG2Ww7p1NvgwMonxlZMAzEkIphHCHIG5U96bOX6Hhld8AeajpkEZNVXKQHQqo1JuVvpil9yJZKlSHGpADoFJFx45hLk3eg9mmxT8JnFNoMoCErMAxQxiMUvChnx1hCmxA79Kwm8LDPxqCR-rxGPgv2pMIr5yEpg5Cck0wphTMPkTNt-v-XjVl2s-W_PNNre-NRqK0xpDi0yhJFMoCQwliaFuxdufFhiH65SV1ikorTAlrTHCa2e3UP_P3HoYLO3EaO2Ea00ktzaVmNSBbxIuCUkKSKAAAGvKBYVxQeXf4xBf_oxj733PR2Z_lAr0Rg9isFpvRD3jCLeNS1ahbBtVBpVto2Jx9atXOyxcvHy1mVHi9avNHvhQ024kHexeBZrIHjCRZcDvNYIJ1qrPQpyDOINm_2LvYt9_f326_v5x_fn9-uXzn6_f4p-s5n47nR-_ePby-dNHt09uP_4DAAD__w.VSj-WqkyqiXc8ki_6yCN-K9vVcKGFgV3zSrQG0LXY3tBGNuF106opN-NGCPuqe9EnKnuiOhNu1KPirShUi21kQ\"\n}','成功',27360,'2022-03-28 13:15:29','2022-03-28 13:15:29',NULL,'测试中心回归00002','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(4,1,'前置条件',1,'{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1in4S4Rux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5P7168vH1-d_vsycd_AAAA__8.Vq4Uj94ugR9_TQJjUSHnPnns31thnqr4UbRJ7X5yGUEjAwEWTKhhdrXFs9iOGh0J7tVML4DgWWtUHzkNVZPYzg\"\n}','成功',163,'2022-03-28 14:48:40','2022-03-28 14:48:40',NULL,'000003','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(5,1,'前置条件',1,'{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrYASTcQnSldttNEb9J9vdLEKEEENGSkRGSMB90HAMjKvKLrtMhN8r7fue3V6sfX_y-3C6nNQ42-V0d1J7uEb5crNaeeNfKWfVMBl_UeN4J93RTCaY7iAoO3UGk_WhY3ujnL52Bvs2qkhQWq_7ElINWiO8yIQkmUAkMJ4khW4WAuO_FJaWEBSXEBIXFBCX5YdTVUoAUWJIUxZoCgS1KadmX3ShgG5QD3pdRhvsulRQZjf4alIVYZOqEvObMmzCaolCvSq9EhLfA3eQcGbEQ1Vg2SASGoRBWRhoMIBxr1c3q0UbZ7bVhcwSA4btzHIDMctlxIT1ErNuRR9UiGlW-05NNuxWreadumzeqcym3dpsXqrXRyrOURxec2LimMTZgJHYrHbt4o_Xt1dcXXFvm0srbqy4rtlY3WhcTQGrTQa3jgdvXg8zrm-Xnr9vrYuNxqHeI9e4y9pKbbiVqnCjk4y7HYejei64zrCjfSK4hbCjfRa4RTCz7YuFi0RrgmQJAJQQjoKCUVKo1f82pNw7yC2SoosDcUVTYnFyqN9U0FeMRJEDs4aypDEeVQ5HjdFmOaxbZ4MPI5MYXzkJwJyEYBohzBGYB-W9meN3aHjFF2A-ahqUUVOlDESnMirlZqWvdsmdSJYqxaEG5BCYdOGRQ5hHo_dgtknBbxLXBKosIDELUMwgFrMkbMhXR5gSO_CrJPy2wMCvlvCxSjwG_qvGJOIrJ4GZk5BMI4w5BZM_YfP9mo9XfbnmszXfbHPrG6OhOK0xtMgUSjKFksBQkhjqVrz9aYFxuE5ZaZ2C0gpT0hojvHZ2C_X_zK2HwdJOjNZOuNZEcmtTiUkd-CbhkpCkgAQKALCmXFAYF1T-PQ7x5c849t73fGT2R6lAb_RfDFbrjahnHOG2cckqlG2jyqCybVQsrn71aoeFi5evNjNKvH612QMfatqNpIPdq0AT2QMmsgz4vUYwwVr1WYhzEGfQ7F_sXez7z8-Pt1_fbz--3T5_-v3la_yT1Txup8v9-enzZ0_O5xf3H_4CAAD__w.oZqFSeVk2JHR0YJY7L3lGkN_4JBBN-91uBiOBJqqfapcc072dOJaP8mVlkTwAOpbtyoLhxYrtNpRRZSxglFIPQ\"\n}','成功',109458,'2022-03-28 14:50:50','2022-03-28 14:50:50',NULL,'000004','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(6,1,'前置条件',1,'{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4s_7UzXwJ0y2GwbpY81fAjGtZhhprZmpkZmjg-1jXxxDpbmhoNpJzuvZ8B4aV2g8nvw-n80mNs11ONye1h0uUrzarlTf-tXJWDZPxZzWON9IdzWSC6Q6CslNnMFkfOrY3yulLZ7Bvo4oEpfW6LyHVoDXCi0xIkglEAuNJUuhmITD-S2FpCUFxCSFxQQFxWX44VaUEECWGNGWBpkBQm3Jq9kUXCugGdafXZbTBrksFZXaDryZVETapKjG_KcMmrJYo1KvSKyHxPXAHCWdGPFQFlg0ioUEYlIWBBgMY92Z1s1q0cWZbXcgsMWDYziw3ELNcRkxYLzHrVvRBhZhmte_UZMNu1Wreqcvmncps2q3N5qV6faTiHMXhNScmjkmcDRiJzWrXLv54fXvF1RX3trm04saK65qN1Y3G1RSw2mRw63jw5vUw4_pu6fn71rrYaBzqPXKNu6yt1IZbqQo3Osm423E4queC6ww72ieCWwg72meBWwQz275YuEi0JkiWAEAJ4SgoGCWFWv1_Q8q9h9wiKbo4EFc0JRYnh_pNBX3BSBQ5MGsoSxrjUeVw1BhtlsO6dTb4MDKJ8ZWTAMxJCKYRwhyBuVPemzl-h4ZXfAHmo6ZBGTVVykB0KqNSblb6YpfciWSpUhxqQA6BSRceOYS5N3oPZpsU_CZxTaDKAhKzAMUMYjFLwoZ8dYQpsQO_SsJvCwz8agkfq8Rj4L9qTCK-chKYOQnJNMKYUzD5Ezbfr_l41ZdrPlvzzTa3vjUaitMaQ4tMoSRTKAkMJYmhbsXbnxYYh-uUldYpKK0wJa0xwmtnt1D_z9x6GCztxGjthGtNJLc2lZjUgW8SLglJCkigAABrygWFcUHl3-MQX_6MY-99z0dmf5QK9EYPYrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff399uv7-cf35_frl85-v3-KfrOZ-O50fP799-ezpiye3jz7-AwAA__8.qhRtIUk8aVvo_8kmViqPp32PoCKHX02WtdOykt0B0s-_36hTW-3e9NC-dRQ8XQ9ussLGOKjpYRPTrB17ieiCxw\"\n}','成功',161,'2022-03-28 15:00:40','2022-03-28 15:00:40',NULL,'00005','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(7,3,'前置条件',3,'{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1ilAEIhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5PXzy_u3328u72ycd_AAAA__8.s4BkPolAQeVOBTUoEYaiahg-bUZhfKM-Ecs8oBjU_01WX5MQeRPANIvPQUKttmTzysSNJ_xrFihfIDDWsHdE6w\"\n}','成功',114,'2022-03-28 15:10:50','2022-03-28 15:10:50',NULL,'性能测试0001','测试中心性能测试集合',3,'接口','已完成','测试中心性能测试前置登陆'),(8,1,'前置条件',1,'未找到环境组件部署，请检查是否存在或已被删除','失败',0,'2022-03-28 15:32:30','2022-03-28 15:32:30',NULL,'测试数据库变量','测试中心回归测试集合',2,'数据库','已完成','获取用户表用户名'),(9,1,'前置条件',1,'{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1imIgAhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc63d89ePH_68vbuycd_AAAA__8.Gfu75QgV7jKFAEQCzToRmw0BCAOiumz6YzXXfNx59IYN_Qo77MNqTwT-0MjhJubjIJ78gf7AutGksc8DbFJ-iQ\"\n}','成功',206,'2022-03-28 15:32:40','2022-03-28 15:32:40',NULL,'测试数据库变量','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(10,1,'前置条件',1,'未找到环境部署组件mysql组件，请检查是否部署存在或已被删除','失败',0,'2022-03-28 16:00:25','2022-03-28 16:00:25',NULL,'000006','测试中心回归测试集合',2,'数据库','已完成','获取用户表用户名'),(11,1,'前置条件',1,'{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8QYgBrSajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetDrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCjvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gr0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZxO12evjjfPz8_uX92_vAXAAD__w.c1HyDniStoxGCHIleoaeNbVGKYdZ3uSComHWPuN_wnVMJppaLmtxhQThQK9V4R0_a8UdNxh0WZ1MOLtcXoop-w\"\n}','成功',181,'2022-03-28 16:00:34','2022-03-28 16:00:34',NULL,'000006','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(12,1,'前置条件',1,'未找到环境部署组件：mysql组件，请检查是否部署存在或已被删除','失败',0,'2022-03-28 16:05:50','2022-03-28 16:05:50',NULL,'00007','测试中心回归测试集合',2,'数据库','已完成','获取用户表用户名'),(13,1,'前置条件',1,'{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYr7SA0GZcgXbndRmPUf7Ldza4QIcSQkRKRERJwHzQcA-OqsssuE-H3Svu-Z7cXa9-f_D6cLic1znY53Z3UHq5RvtqsVt7418pZNUzGX9Q43kl3NJMJpjsIyk6dwWR96NjeKKevncG-jSoSlNbrvoRUg9YILzIhSSYQCYwnSaGbhcD4L4WlJQTFJYTEBQXEZfnhVJUSQJQY0pQFmgJBbcqp2RddKKAb1KNel9EGuy4VlNkNvppURdikqsT8pgybsFqiUK9Kr4TE98AdJJwZ8VAVWDaIhAZhUBYGGgxg3JvVzWrRxpltdSGzxIBhO7PcQMxyGTFhvcSsW9EHFWKa1b5Tkw27Vat5py6bdyqzabc2m5fq9ZGKcxSH15yYOCZxNmAkNqtdu_jj9e0VV1fc2-bSihsrrms2VjcaV1PAapPBrePBm9fDjOu7pefvW-tio3Go98g17rK2UhtupSrc6CTjbsfhqJ4LrjPsaJ8IbiHsaJ8FbhHMbPti4SLRmiBZAgAlhKOgYJQUavW_DSn3DLlFUnRxIK5oSixODvWbCvqKkShyYNZQljTGo8rhqDHaLId162zwYWQS4ysnAZiTEEwjhDkC86i8N3P8Dg2v-ALMR02DMmqqlIHoVEal3Kz01S65E8lSpTjUgBwCky48cgjzZPQezDYp-E3imkCVBSRmAYoZxGKWhA356ghTYgd-lYTfFhj41RI-VonHwH_VmER85SQwcxKSaYQxp2DyJ2y-X_Pxqi_XfLbmm21ufWs0FKc1hhaZQkmmUBIYShJD3Yq3Py0wDtcpK61TUFphSlpjhNfObqH-n7n1MFjaidHaCdeaSG5tKjGpA98kXBKSFJBAAQDWlAsK44LKv8chvvwZx977no_M_igV6I3-i8FqvRH1jCPcNi5ZhbJtVBlUto2KxdWvXu2wcPHy1WZGidevNnvgQ027kXSwexVoInvARJYBv9cIJlirPgtxDuIMmv2LvYt9__n58fbr--3Ht9vnT7-_fI1_spqn7XS5f_Hw8vxwf38-f_gLAAD__w.pXlxNBB208eNSd1gIyWe8y1Eh2u_Wt6HubPwXyMC1S8rhaLEonoBC6uzf4zfm9aEudKgh-1sVq1ma5BaPRhtjw\"\n}','成功',181,'2022-03-28 16:05:55','2022-03-28 16:05:55',NULL,'00007','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(14,1,'前置条件',1,'成功获取 数据库变量名：usernamefromdb 值:admin','成功',62,'2022-03-28 16:09:15','2022-03-28 16:09:15',NULL,'00008','测试中心回归测试集合',2,'数据库','已完成','获取用户表用户名'),(15,1,'前置条件',1,'{\n  \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrdhCajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetTrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCrvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gb0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZpO13unz-8OD_cPzufP_wFAAD__w.SoqYO24kUPJixCZpzcc82QgINB87iiYupneu1k153_3lhEk1FopYOE9gHICQlWjDByPRM77-t3HSf8MwX0NGRw\"\n}','成功',89,'2022-03-28 16:09:15','2022-03-28 16:09:15',NULL,'00008','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(16,2,'前置条件',2,'{\n \"code\":500,\n \"message\":\"/account/token => Required request body is missing: public com.zoctan.api.core.response.Result com.zoctan.api.controller.AccountController.login(com.zoctan.api.entity.Account)\"\n}','成功',31,'2022-03-28 16:09:36','2022-03-28 16:09:36','admin','00008','正常新增环境',2,'接口','已完成','新增测试环境前置登陆'),(17,1,'前置条件',1,'成功获取 数据库变量名：usernamefromdb 值:admin','成功',20,'2022-03-28 16:10:35','2022-03-28 16:10:35',NULL,'00009','测试中心回归测试集合',2,'数据库','已完成','获取用户表用户名'),(18,1,'前置条件',1,'{\n \"code\":200,\n \"data\":\"Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrZhGajEuQrtxuozHqH8t2N4sQIcSQkRKRERJwHzQcg8ZVZZddJsLvlfZ9z24v1r4_hW04XU5qnO1yujupLV4P-dJZrYIJr5S3aphMuKhxvJPuaCYTTXcQlZ06g8mG2LGDUV5fO4PNjeogKK3XbYmpBq0RXmRCkkwgEhhPkkKdhcDjXwpLSwg6lhByLCjgWJYfTlUpAUSJIU1ZoCkQlFNezaHoQgHdoB71uow22nWpoMxu8NWkKsImVSXmN2XYhNUShXpVeiUkvgfuIOHMiIeqwLJBJDQIg7Iw0GAA41-vflaLNt641cfMEgOG7cxyAzHLZcSE9RKzbsUQVTzSrA6dmmzYrVrNO3XZvFOZTbu12bxUr49UnKM4vObExDGJswEjsVnt2sUfr2-vuLri3jaXVtxYcV2zsfrR-JoCVpsMbh0P3rzuZlzfLj1_c62Ljcah3iPXuMvaSm24lapwo5OMux2HvXouuM6wvX0iuIWwvX0WuEUw47bFwkWiNUGyBABKCEdBwSgp1Op_G1L-HeQWSdHFgbiiKbE4OTQ4FfUVI1HkwKyhLGmMR5XDUWO0WXbr19ngw8gkxldOAjAnIZhGCHME5lGFYObjOzS84gswHzUNyqipUgaiUxmVcrPSV7vkTiRLleJQA3IITLrwyCHMk9FbNG5S8JvENYEqC0jMAhQziMUsCRvy1RGmxA78Kgm_LTDwqyV8rHIcA_9VYxLxlZPAzElIphHGnILJn7D5fs3Hq75c89mab-b8-sZoKE5rDC0yhZJMoSQwlCSG-hVvf1pgHK5TVlqnoLTClLTGiKC9dbH-n7n1MFjaidHaCdeaSG5tKjGpHd8kXBKSFJBAAQDWlAsK46LKv8fxePkzjr33PR-Z_VEq0Bv9F4PVeiPqeYxw27hkFcq2UWVQ2TYqFle_erXDwsXLV5sZJV6_2uyBdzVtRtLB7lWgiewBE1kG_F4jmGCt-izEOYgzaPYv9i72_efnx9uv77cf326fP_3-8vX4k9U8udPl_vn5xcP5_vzs4cNfAAAA__8.DsWj1Fgs8rnX4nt5kVF4f0RVMd0D_EaSQ8E-I9D9N10VItYvGOhG1vxt18-R2U8PN-WW2P_cJDgTTtFgB-zQBQ\"\n}','成功',113,'2022-03-28 16:10:35','2022-03-28 16:10:35',NULL,'00009','测试中心回归测试集合',2,'接口','已完成','前置登陆'),(19,2,'前置条件',2,'{\n \"code\":500,\n \"message\":\"/account/token => Required request body is missing: public com.zoctan.api.core.response.Result com.zoctan.api.controller.AccountController.login(com.zoctan.api.entity.Account)\"\n}','成功',30,'2022-03-28 16:11:00','2022-03-28 16:11:00','admin','00009','正常新增环境',2,'接口','已完成','新增测试环境前置登陆');
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
-- Dumping data for table `tester`

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
  `variablesexpress` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '变量表达',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  `variablesdes` varchar(100) DEFAULT NULL COMMENT '变量描述',
  `valuetype` varchar(20) DEFAULT 'String' COMMENT 'String，Number，Array,Bool,其他',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testvariables`
--

LOCK TABLES `testvariables` WRITE;
/*!40000 ALTER TABLE `testvariables` DISABLE KEYS */;
INSERT INTO `testvariables` VALUES (1,'token','','$.data','测试中心登陆返回的登陆认证给其他接口使用','2022-03-28 13:03:26','2022-03-28 13:03:26','admin','测试中心登陆返回的登陆认证','String');
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='变量值表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testvariables_value`
--

LOCK TABLES `testvariables_value` WRITE;
/*!40000 ALTER TABLE `testvariables_value` DISABLE KEYS */;
INSERT INTO `testvariables_value` VALUES (1,2,'测试中心回归测试集合',2,'正常登陆',1,'token','','test','2022-03-28 13:05:55','2022-03-28 13:05:55','测试中心回归测试00001','接口'),(2,2,'测试中心回归测试集合',2,'正常登陆',1,'token','','test','2022-03-28 13:15:57','2022-03-28 13:15:57','测试中心回归00002','接口'),(3,2,'测试中心回归测试集合',2,'正常登陆',1,'token','','test','2022-03-28 14:47:14','2022-03-28 14:47:14','测试中心回归00002','接口'),(4,2,'测试中心回归测试集合',2,'正常登陆',1,'token','','test','2022-03-28 14:48:40','2022-03-28 14:48:40','000003','接口'),(5,2,'测试中心回归测试集合',2,'正常登陆',1,'token','','test','2022-03-28 14:56:35','2022-03-28 14:56:35','000004','接口'),(6,2,'测试中心回归测试集合',2,'正常登陆',1,'token','Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l72SFSEQhd_lxhv4s_7UzXwJ0y2GwbpY81fAjGtZhhprZmpkZmjg-1jXxxDpbmhoNpJzuvZ8B4aV2g8nvw-n80mNs11ONye1h0uUrzarlTf-tXJWDZPxZzWON9IdzWSC6Q6CslNnMFkfOrY3yulLZ7Bvo4oEpfW6LyHVoDXCi0xIkglEAuNJUuhmITD-S2FpCUFxCSFxQQFxWX44VaUEECWGNGWBpkBQm3Jq9kUXCugGdafXZbTBrksFZXaDryZVETapKjG_KcMmrJYo1KvSKyHxPXAHCWdGPFQFlg0ioUEYlIWBBgMY92Z1s1q0cWZbXcgsMWDYziw3ELNcRkxYLzHrVvRBhZhmte_UZMNu1Wreqcvmncps2q3N5qV6faTiHMXhNScmjkmcDRiJzWrXLv54fXvF1RX3trm04saK65qN1Y3G1RSw2mRw63jw5vUw4_pu6fn71rrYaBzqPXKNu6yt1IZbqQo3Osm423E4queC6ww72ieCWwg72meBWwQz275YuEi0JkiWAEAJ4SgoGCWFWv1_Q8q9h9wiKbo4EFc0JRYnh_pNBX3BSBQ5MGsoSxrjUeVw1BhtlsO6dTb4MDKJ8ZWTAMxJCKYRwhyBuVPemzl-h4ZXfAHmo6ZBGTVVykB0KqNSblb6YpfciWSpUhxqQA6BSRceOYS5N3oPZpsU_CZxTaDKAhKzAMUMYjFLwoZ8dYQpsQO_SsJvCwz8agkfq8Rj4L9qTCK-chKYOQnJNMKYUzD5Ezbfr_l41ZdrPlvzzTa3vjUaitMaQ4tMoSRTKAkMJYmhbsXbnxYYh-uUldYpKK0wJa0xwmtnt1D_z9x6GCztxGjthGtNJLc2lZjUgW8SLglJCkigAABrygWFcUHl3-MQX_6MY-99z0dmf5QK9EYPYrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff399uv7-cf35_frl85-v3-KfrOZ-O50fP799-ezpiye3jz7-AwAA__8.qhRtIUk8aVvo_8kmViqPp32PoCKHX02WtdOykt0B0s-_36hTW-3e9NC-dRQ8XQ9ussLGOKjpYRPTrB17ieiCxw','test','2022-03-28 15:00:40','2022-03-28 15:00:40','00005','接口'),(7,3,'测试中心性能测试集合',2,'正常登陆',1,'token','Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1ilAEIhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc5PXzy_u3328u72ycd_AAAA__8.s4BkPolAQeVOBTUoEYaiahg-bUZhfKM-Ecs8oBjU_01WX5MQeRPANIvPQUKttmTzysSNJ_xrFihfIDDWsHdE6w','test','2022-03-28 15:10:50','2022-03-28 15:10:50','性能测试0001','接口'),(8,2,'测试中心回归测试集合',2,'正常登陆',1,'token','Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd_l1imIgAhux0vQRl6v0TXaP9neJQhRQg0dLRUdJQXvgy6PgfHM2GOPU-FzRjnfsdfByoeT34fT-aTG2S6nm5PawyXKV5vVyhv_Wjmrhsn4sxrHG-mOZjLBdAdB2akzmKwPHdsb5fSlM9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDutfrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe7O6WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX239Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf6_IeXeQ26RFF0ciCuaEouTQ_2mgr5gJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7A3CvvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9sUvuRLJUKQ41IIfApAuPHMI8GL0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xoNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqNHMVitN6KecYTbxiWrULaNKoPKtlGxuPrVqx0WLl6-2swo8frVZg98qGk3kg52rwJNZA-YyDLg9xrBBGvVZyHOQZxBs3-xd7Hvv78-XX__uP78fv3y-c_Xb_FPVvOwnc63d89ePH_68vbuycd_AAAA__8.Gfu75QgV7jKFAEQCzToRmw0BCAOiumz6YzXXfNx59IYN_Qo77MNqTwT-0MjhJubjIJ78gf7AutGksc8DbFJ-iQ','test','2022-03-28 15:32:40','2022-03-28 15:32:40','测试数据库变量','接口'),(9,2,'测试中心回归测试集合',2,'正常登陆',1,'token','Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8QYgBrSajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetDrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCjvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gr0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZxO12evjjfPz8_uX92_vAXAAD__w.c1HyDniStoxGCHIleoaeNbVGKYdZ3uSComHWPuN_wnVMJppaLmtxhQThQK9V4R0_a8UdNxh0WZ1MOLtcXoop-w','test','2022-03-28 16:00:34','2022-03-28 16:00:34','000006','接口'),(10,2,'测试中心回归测试集合',2,'正常登陆',1,'token','Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYr7SA0GZcgXbndRmPUf7Ldza4QIcSQkRKRERJwHzQcA-OqsssuE-H3Svu-Z7cXa9-f_D6cLic1znY53Z3UHq5RvtqsVt7418pZNUzGX9Q43kl3NJMJpjsIyk6dwWR96NjeKKevncG-jSoSlNbrvoRUg9YILzIhSSYQCYwnSaGbhcD4L4WlJQTFJYTEBQXEZfnhVJUSQJQY0pQFmgJBbcqp2RddKKAb1KNel9EGuy4VlNkNvppURdikqsT8pgybsFqiUK9Kr4TE98AdJJwZ8VAVWDaIhAZhUBYGGgxg3JvVzWrRxpltdSGzxIBhO7PcQMxyGTFhvcSsW9EHFWKa1b5Tkw27Vat5py6bdyqzabc2m5fq9ZGKcxSH15yYOCZxNmAkNqtdu_jj9e0VV1fc2-bSihsrrms2VjcaV1PAapPBrePBm9fDjOu7pefvW-tio3Go98g17rK2UhtupSrc6CTjbsfhqJ4LrjPsaJ8IbiHsaJ8FbhHMbPti4SLRmiBZAgAlhKOgYJQUavW_DSn3DLlFUnRxIK5oSixODvWbCvqKkShyYNZQljTGo8rhqDHaLId162zwYWQS4ysnAZiTEEwjhDkC86i8N3P8Dg2v-ALMR02DMmqqlIHoVEal3Kz01S65E8lSpTjUgBwCky48cgjzZPQezDYp-E3imkCVBSRmAYoZxGKWhA356ghTYgd-lYTfFhj41RI-VonHwH_VmER85SQwcxKSaYQxp2DyJ2y-X_Pxqi_XfLbmm21ufWs0FKc1hhaZQkmmUBIYShJD3Yq3Py0wDtcpK61TUFphSlpjhNfObqH-n7n1MFjaidHaCdeaSG5tKjGpA98kXBKSFJBAAQDWlAsK44LKv8chvvwZx977no_M_igV6I3-i8FqvRH1jCPcNi5ZhbJtVBlUto2KxdWvXu2wcPHy1WZGidevNnvgQ027kXSwexVoInvARJYBv9cIJlirPgtxDuIMmv2LvYt9__n58fbr--3Ht9vnT7-_fI1_spqn7XS5f_Hw8vxwf38-f_gLAAD__w.pXlxNBB208eNSd1gIyWe8y1Eh2u_Wt6HubPwXyMC1S8rhaLEonoBC6uzf4zfm9aEudKgh-1sVq1ma5BaPRhtjw','test','2022-03-28 16:05:55','2022-03-28 16:05:55','00007','接口'),(11,2,'测试中心回归测试集合',1,'测试中心回归测试前置父条件',1,'usernamefromdb','admin','test','2022-03-28 16:09:15','2022-03-28 16:09:15','00008','数据库'),(12,2,'测试中心回归测试集合',2,'正常登陆',1,'token','Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrdhCajEuQrtxuozHqP9nuZhEihBgyUiIyQgLug4ZjYFxVdtllIvxead_37PZi7fuT34fT5aTG2S6nu5PawzXKl5vVyhv_Sjmrhsn4ixrHO-mOZjLBdAdB2akzmKwPHdsb5fS1M9i3UUWC0nrdl5Bq0BrhRSYkyQQigfEkKXSzEBj_pbC0hKC4hJC4oIC4LD-cqlICiBJDmrJAUyCoTTk1-6ILBXSDetTrMtpg16WCMrvBV5OqCJtUlZjflGETVksU6lXplZD4HriDhDMjHqoCywaR0CAMysJAgwGMe726WS3aOLOtLmSWGDBsZ5YbiFkuIyasl5h1K_qgQkyz2ndqsmG3ajXv1GXzTmU27dZm81K9PlJxjuLwmhMTxyTOBozEZrVrF3-8vr3i6op721xacWPFdc3G6kbjagpYbTK4dTx483qYcX279Px9a11sNA71HrnGXdZWasOtVIUbnWTc7Tgc1XPBdYYd7RPBLYQd7bPALYKZbV8sXCRaEyRLAKCEcBQUjJJCrf63IeXeQW6RFF0ciCuaEouTQ_2mgr5iJIocmDWUJY3xqHI4aow2y2HdOht8GJnE-MpJAOYkBNMIYY7APCrvzRy_Q8MrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gb0Hs00KfpO4JlBlAYlZgGIGsZglYUO-OsKU2IFfJeG3BQZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CZvv13y86ss1n635Zptb3xgNxWmNoUWmUJIplASGksRQt-LtTwuMw3XKSusUlFaYktYY4bWzW6j_Z249DJZ2YrR2wrUmklubSkzqwDcJl4QkBSRQAIA15YLCuKDy73GIL3_Gsfe-5yOzP0oFeqP_YrBab0Q94wi3jUtWoWwbVQaVbaNicfWrVzssXLx8tZlR4vWrzR74UNNuJB3sXgWayB4wkWXA7zWCCdaqz0KcgziDZv9i72Lff35-vP36fvvx7fb50-8vX-OfrOZpO13unz-8OD_cPzufP_wFAAD__w.SoqYO24kUPJixCZpzcc82QgINB87iiYupneu1k153_3lhEk1FopYOE9gHICQlWjDByPRM77-t3HSf8MwX0NGRw','test','2022-03-28 16:09:15','2022-03-28 16:09:15','00008','接口'),(13,2,'测试中心回归测试集合',1,'测试中心回归测试前置父条件',1,'usernamefromdb','admin','test','2022-03-28 16:10:35','2022-03-28 16:10:35','00009','数据库'),(14,2,'测试中心回归测试集合',2,'正常登陆',1,'token','Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SYrZhGajEuQrtxuozHqH8t2N4sQIcSQkRKRERJwHzQcg8ZVZZddJsLvlfZ9z24v1r4_hW04XU5qnO1yujupLV4P-dJZrYIJr5S3aphMuKhxvJPuaCYTTXcQlZ06g8mG2LGDUV5fO4PNjeogKK3XbYmpBq0RXmRCkkwgEhhPkkKdhcDjXwpLSwg6lhByLCjgWJYfTlUpAUSJIU1ZoCkQlFNezaHoQgHdoB71uow22nWpoMxu8NWkKsImVSXmN2XYhNUShXpVeiUkvgfuIOHMiIeqwLJBJDQIg7Iw0GAA41-vflaLNt641cfMEgOG7cxyAzHLZcSE9RKzbsUQVTzSrA6dmmzYrVrNO3XZvFOZTbu12bxUr49UnKM4vObExDGJswEjsVnt2sUfr2-vuLri3jaXVtxYcV2zsfrR-JoCVpsMbh0P3rzuZlzfLj1_c62Ljcah3iPXuMvaSm24lapwo5OMux2HvXouuM6wvX0iuIWwvX0WuEUw47bFwkWiNUGyBABKCEdBwSgp1Op_G1L-HeQWSdHFgbiiKbE4OTQ4FfUVI1HkwKyhLGmMR5XDUWO0WXbr19ngw8gkxldOAjAnIZhGCHME5lGFYObjOzS84gswHzUNyqipUgaiUxmVcrPSV7vkTiRLleJQA3IITLrwyCHMk9FbNG5S8JvENYEqC0jMAhQziMUsCRvy1RGmxA78Kgm_LTDwqyV8rHIcA_9VYxLxlZPAzElIphHGnILJn7D5fs3Hq75c89mab-b8-sZoKE5rDC0yhZJMoSQwlCSG-hVvf1pgHK5TVlqnoLTClLTGiKC9dbH-n7n1MFjaidHaCdeaSG5tKjGpHd8kXBKSFJBAAQDWlAsK46LKv8fxePkzjr33PR-Z_VEq0Bv9F4PVeiPqeYxw27hkFcq2UWVQ2TYqFle_erXDwsXLV5sZJV6_2uyBdzVtRtLB7lWgiewBE1kG_F4jmGCt-izEOYgzaPYv9i72_efnx9uv77cf326fP_3-8vX4k9U8udPl_vn5xcP5_vzs4cNfAAAA__8.DsWj1Fgs8rnX4nt5kVF4f0RVMd0D_EaSQ8E-I9D9N10VItYvGOhG1vxt18-R2U8PN-WW2P_cJDgTTtFgB-zQBQ','test','2022-03-28 16:10:35','2022-03-28 16:10:35','00009','接口');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variables`
--

LOCK TABLES `variables` WRITE;
/*!40000 ALTER TABLE `variables` DISABLE KEYS */;
INSERT INTO `variables` VALUES (1,'enviromentname','随机字符串','随机环境名','2022-03-28 15:04:32','2022-03-28 15:05:40','5');
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

-- Dump completed on 2022-03-29  9:01:37
