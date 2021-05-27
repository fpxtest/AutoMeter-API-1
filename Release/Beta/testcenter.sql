/*
 Navicat Premium Data Transfer

 Source Server         : 5173mysql-testeNV
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.130.70
 Source Database       : testcenter

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : utf-8

 Date: 05/27/2021 10:23:15 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
--  Records of `account`
-- ----------------------------
BEGIN;
INSERT INTO `account` VALUES ('1', 'admin@qq.com', 'admin', '$2a$10$wg0f10n.30UbU.9hPpucbef/ya62LdTKs72xJfjxvTFsL0Xaewbra', '2019-07-01 00:00:00', '2021-05-27 00:36:14'), ('2', 'editor@qq.com', 'editor', '$2a$10$/m4SgZ.ZFVZ7rcbQpJW2tezmuhf/UzQtpAtXb0WZiAE3TeHxq2DYu', '2019-07-02 00:00:00', '2019-07-02 00:00:00'), ('3', 'test@qq.com', 'test', '$2a$10$NGJEkH3bl7rwgk0ZYChT4.tWTm28jOY9GaeMfj2kYZ2qFB4Ed9sW2', '2019-07-03 00:00:00', '2021-04-23 15:14:05'), ('4', '2712066939@qq.com', 'test2', '$2a$10$vnmEvOON9w9RxTiT1kfcY.DHtBuuZ6ebqZVI92B9lZyeRPhnzEW96', '2020-03-28 16:21:32', '2020-04-21 22:20:22'), ('5', 'test@123.com', 'testttt', '$2a$10$PYJn21dsOACyhPmI02HAD.3Jm.oo.h2xEXUnj0wkmZr4PoSPFWHTa', '2020-04-20 14:46:51', null), ('6', '123@133.com', 'testaaa', '$2a$10$QzffPC8b8zaxH867/KeV1eTTdMXNY/0ikWskcN51ETGMe2YsMxdlK', '2020-04-20 14:48:19', null), ('7', '123@123.com', 'aaa', '$2a$10$HPKeTNj7TGKcZmqtUVMgmOSbRnyTxfQM8obo47hL2kzuEnzdzI19S', '2020-04-20 14:56:04', null), ('8', 'q@1.com', 'q11111', '$2a$10$bGkDt6OtQ4xfDxk8DxL1fudo/WLTqjUo/vnxIzvDkeUCwNfAwfU7.', '2020-04-20 15:47:37', null), ('10', '22223333@123.com', '22222', '$2a$10$XqfUMYIMClikSnZTQAhNMOYKcGddYHQXItS4UbSkvQTWTJH5BkLsa', '2020-04-20 15:48:03', null), ('11', '12121@12121.COM', '2121212', '$2a$10$QP5CFuA80yEiC4oGWc8rv.U01dr1YRXOLFKzpjxY0fDO.Vj7knBcW', '2020-04-21 18:12:34', null), ('12', '123131@123213.com', 'test001', '$2a$10$ftjsgs9xyWeQomGSUwfVL.0jM88E13WKzKX0P1rSBDPIXJKEBCH6a', '2020-04-21 18:59:10', '2020-04-21 22:21:01'), ('13', '1211@1231.com', 'xxxx', '$2a$10$Fx4zpCfum/1.AhZNXCCLe.nQqof5LXzRWr8tsR/jP1rUJLcs0.T.y', '2020-04-22 22:38:59', null), ('14', 'admin@123.com', 'newtest', '$2a$10$dvTjUy/M2QNrnFT5PK9JUueBbIL5/CmuOjG1dfRyol4kg8Css/fuG', '2020-12-22 11:49:36', '2020-12-22 11:50:03');
COMMIT;

-- ----------------------------
--  Table structure for `account_role`
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
  `account_id` bigint(20) unsigned NOT NULL COMMENT '用户Id',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色Id',
  PRIMARY KEY (`account_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `account_role_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `account_role_fk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

-- ----------------------------
--  Records of `account_role`
-- ----------------------------
BEGIN;
INSERT INTO `account_role` VALUES ('1', '1'), ('2', '2'), ('5', '2'), ('6', '2'), ('7', '2'), ('10', '2'), ('11', '2'), ('4', '4'), ('3', '7'), ('8', '7'), ('12', '7'), ('13', '7'), ('14', '10');
COMMIT;

-- ----------------------------
--  Table structure for `api`
-- ----------------------------
DROP TABLE IF EXISTS `api`;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='发布单元表';

-- ----------------------------
--  Records of `api`
-- ----------------------------
BEGIN;
INSERT INTO `api` VALUES ('1', '22', 'marketingservice', '获取短链', 'get', '普通方式', 'redeem/ui/retrySendSmsOrFindShortUrl', '', 'json', '', '2020-12-07 16:06:15', '2021-04-26 16:04:27', 'admin'), ('2', '1', 'accountservice', '新获取短链', 'get', '普通方式', 'redeem/ui/retrySendSmsOrFindShortUrl', '', 'json', '', '2021-04-02 19:10:52', '2021-04-26 16:04:34', 'admin'), ('3', '19', 'cornerservice', '新获取短链', 'get', '普通方式', '111', '', 'json', '', '2021-04-18 23:06:49', '2021-04-18 23:06:49', null), ('5', '1', 'accountservice', 'test1', 'get', '普通方式', 'redeem/ui/retrySendSmsOrFindShortUrl', '', 'json', '', '2021-04-02 19:10:52', '2021-04-19 23:04:30', null), ('6', '2', 'paymentservice', 'test1', 'get', '普通方式', 'redeem/ui/retrySendSmsOrFindShortUrl', '', 'json', '', '2021-04-02 19:10:52', '2021-04-02 19:04:09', null), ('7', '19', 'cornerservice', 'test10', 'get', '普通方式', 'redeem/ui/retrySendSmsOrFindShortUrl', '', 'json', '', '2021-04-02 19:10:52', '2021-04-02 19:04:09', null), ('8', '1', 'accountservice', 'testnopara', 'get', 'restful', '/test', '', 'json', '', '2021-04-19 23:55:06', '2021-04-19 23:55:06', null), ('9', '19', 'cornerservice', 'copynopara', 'get', 'restful', '/test', '', 'json', '', '2021-04-19 23:55:06', '2021-04-19 23:55:06', null), ('10', '20', 'regressionservice', 'x', 'get', 'restful', 'x', '', 'json', '', '2021-04-26 16:34:28', '2021-04-26 16:34:28', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `api_casedata`
-- ----------------------------
DROP TABLE IF EXISTS `api_casedata`;
CREATE TABLE `api_casedata` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例名',
  `apiparam` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api参数',
  `apiparamvalue` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用例参数值',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api属性类型，header，body',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COMMENT='api用例数据表';

-- ----------------------------
--  Records of `api_casedata`
-- ----------------------------
BEGIN;
INSERT INTO `api_casedata` VALUES ('1', '1', '获取用户信息正确', 'goodsThirdPartyType', 'ALIPAY', 'Params', 'memo', '2020-12-07 16:12:05', '2020-12-07 16:12:05'), ('2', '1', '获取用户信息正确', 'orderId', '2012030033263636700', 'Params', 'memo', '2020-12-07 16:12:05', '2020-12-07 16:12:05'), ('3', '1', '获取用户信息正确', 'status', 'OFF', 'Params', 'memo', '2020-12-07 16:12:05', '2020-12-07 16:12:05'), ('4', '2', '获取用户信息性能', 'goodsThirdPartyType', 'ALIPAY', 'Params', 'memo', '2020-12-07 16:12:38', '2020-12-07 16:12:38'), ('5', '2', '获取用户信息性能', 'orderId', '2012030033263636700', 'Params', 'memo', '2020-12-07 16:12:38', '2020-12-07 16:12:38'), ('6', '2', '获取用户信息性能', 'status', 'OFF', 'Params', 'memo', '2020-12-07 16:12:38', '2020-12-07 16:12:38'), ('58', '39', '获取用户信息正确', 'goodsThirdPartyType', 'ALIPAY', 'Params', 'memo', '2020-12-07 16:12:05', '2020-12-07 16:12:05'), ('59', '39', '获取用户信息正确', 'orderId', '2012030033263636700', 'Params', 'memo', '2020-12-07 16:12:05', '2020-12-07 16:12:05'), ('60', '39', '获取用户信息正确', 'status', 'OFF', 'Params', 'memo', '2020-12-07 16:12:05', '2020-12-07 16:12:05');
COMMIT;

-- ----------------------------
--  Table structure for `api_params`
-- ----------------------------
DROP TABLE IF EXISTS `api_params`;
CREATE TABLE `api_params` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiId',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api名',
  `deployunitid` bigint(20) unsigned NOT NULL COMMENT '发布单元Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'api属性类型，header，body',
  `keyname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'key名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='api参数表';

-- ----------------------------
--  Records of `api_params`
-- ----------------------------
BEGIN;
INSERT INTO `api_params` VALUES ('1', '1', '获取短链', '22', 'marketingservice', 'Params', 'goodsThirdPartyType,orderId,status', '2020-12-07 16:06:41', '2021-04-26 16:04:40', 'admin'), ('2', '2', '新获取短链', '1', 'accountservice', 'Params', 'goodsThirdPartyType,orderId,status', '2021-04-02 19:11:30', '2021-04-26 19:04:09', 'admin'), ('4', '5', 'test1', '1', 'accountservice', 'Params', 'goodsThirdPartyType,orderId,status', '2021-04-02 19:11:30', '2021-04-02 19:11:30', null), ('5', '6', 'test1', '1', 'accountservice', 'Params', 'goodsThirdPartyType,orderId,status', '2021-04-02 19:11:30', '2021-04-02 19:11:30', null), ('6', '7', 'test10', '19', 'cornerservice', 'Params', 'goodsThirdPartyType,orderId,status', '2021-04-02 19:11:30', '2021-04-02 19:11:30', null), ('7', '6', 'test1', '2', 'paymentservice', 'Header', '1,2', '2021-04-26 16:46:56', '2021-04-26 16:46:56', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `apicases`
-- ----------------------------
DROP TABLE IF EXISTS `apicases`;
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
  `level` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '优先级',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COMMENT='api用例表';

-- ----------------------------
--  Records of `apicases`
-- ----------------------------
BEGIN;
INSERT INTO `apicases` VALUES ('1', '1', '获取短链', '22', 'marketingservice', '获取用户信息正确', 'httpapi', '功能', '1', '1', '获取用户信息正确', '$.message=Success|$.result=http://b6i.cn/4Vgxk|$.code=0', '', '', '', '2020-12-07 16:07:39', '2021-05-26 23:05:18', 'admin'), ('2', '1', '获取短链', '22', 'marketingservice', '获取用户信息性能111', 'retrySendSmsOrFindShortUrl', '性能', '3', '4', '获取用户信息正确', '$.code=500|$.message=服务器伐开心,我们正在想办法', '', '', '', '2020-12-07 16:36:06', '2021-05-12 15:05:25', 'admin'), ('39', '1', '获取短链', '22', 'marketingservice', '复制用户信息正确', 'httpapi', '功能', '1', '1', '获取用户信息正确', '$.message=Success|$.result=http://b6i.cn/4Vgxk|$.code=0', '', '', '', '2020-12-07 16:07:39', '2021-05-26 23:05:18', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `apicases_condition`
-- ----------------------------
DROP TABLE IF EXISTS `apicases_condition`;
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

-- ----------------------------
--  Table structure for `apicases_condition_report`
-- ----------------------------
DROP TABLE IF EXISTS `apicases_condition_report`;
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
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8mb4 COMMENT='api用例前后置条件运行报告表';

-- ----------------------------
--  Records of `apicases_condition_report`
-- ----------------------------
BEGIN;
INSERT INTO `apicases_condition_report` VALUES ('1', '1', '12', '1', '2021-3-16-000001', '5', '前置', '功能', '成功', '', '2021-03-16 11:23:58', '2021-03-16 11:23:58', 'admin'), ('2', '1', '12', '1', '2021-3-16-000001', '5', '前置', '功能', '成功', '', '2021-03-16 11:23:58', '2021-03-16 11:23:58', 'admin'), ('3', '1', '12', '1', '2021-3-16-000001', '5', '后置', '功能', '成功', '', '2021-03-16 11:24:02', '2021-03-16 11:24:02', 'admin'), ('4', '1', '12', '1', '2021-3-16-000001', '5', '后置', '功能', '成功', '', '2021-03-16 11:24:02', '2021-03-16 11:24:02', 'admin'), ('5', '1', '12', '2', '2021-3-16-0000002', '5', '前置', '功能', '成功', '', '2021-03-16 11:37:41', '2021-03-16 11:37:41', 'admin'), ('6', '1', '12', '2', '2021-3-16-0000002', '5', '前置', '功能', '成功', '', '2021-03-16 11:37:41', '2021-03-16 11:37:41', 'admin'), ('7', '1', '12', '2', '2021-3-16-0000002', '5', '后置', '功能', '成功', '', '2021-03-16 11:37:44', '2021-03-16 11:37:44', 'admin'), ('8', '1', '12', '2', '2021-3-16-0000002', '5', '后置', '功能', '成功', '', '2021-03-16 11:37:44', '2021-03-16 11:37:44', 'admin'), ('9', '1', '12', '2', 'xxxxxxxxxxxxxxxxxxxxx', '5', '后置', '性能', '成功', '', '2021-03-16 12:42:24', '2021-03-16 12:42:24', 'admin'), ('10', '1', '12', '2', 'xxxxxxxxxxxxxxxxxxxxx', '5', '后置', '性能', '成功', '', '2021-03-16 12:42:24', '2021-03-16 12:42:24', 'admin'), ('11', '1', '12', '2', 'xxxxxxxxxxxxxxxxxxxxx', '5', '后置', '性能', '成功', '', '2021-03-16 12:53:04', '2021-03-16 12:53:04', 'admin'), ('12', '1', '12', '2', 'xxxxxxxxxxxxxxxxxxxxx', '5', '后置', '性能', '成功', '', '2021-03-16 12:53:04', '2021-03-16 12:53:04', 'admin'), ('13', '1', '12', '3', '2021-3-16-000003', '5', '前置', '功能', '成功', '', '2021-03-16 12:55:33', '2021-03-16 12:55:33', 'admin'), ('14', '1', '12', '3', '2021-3-16-000003', '5', '前置', '功能', '成功', '', '2021-03-16 12:55:33', '2021-03-16 12:55:33', 'admin'), ('15', '1', '12', '3', '2021-3-16-000003', '5', '后置', '功能', '成功', '', '2021-03-16 12:55:39', '2021-03-16 12:55:39', 'admin'), ('16', '1', '12', '3', '2021-3-16-000003', '5', '后置', '功能', '成功', '', '2021-03-16 12:55:39', '2021-03-16 12:55:39', 'admin'), ('17', '1', '12', '4', '2021-3-16-00000004', '5', '前置', '功能', '成功', '', '2021-03-16 13:00:24', '2021-03-16 13:00:24', 'admin'), ('18', '1', '12', '4', '2021-3-16-00000004', '5', '前置', '功能', '成功', '', '2021-03-16 13:00:24', '2021-03-16 13:00:24', 'admin'), ('19', '1', '12', '4', '2021-3-16-00000004', '5', '后置', '功能', '成功', '', '2021-03-16 13:00:27', '2021-03-16 13:00:27', 'admin'), ('20', '1', '12', '4', '2021-3-16-00000004', '5', '后置', '功能', '成功', '', '2021-03-16 13:00:27', '2021-03-16 13:00:27', 'admin'), ('21', '1', '12', '5', '2021-3-17-0000001', '5', '前置', '功能', '成功', '', '2021-03-17 10:08:12', '2021-03-17 10:08:12', 'admin'), ('22', '1', '12', '5', '2021-3-17-0000001', '5', '前置', '功能', '成功', '', '2021-03-17 10:08:12', '2021-03-17 10:08:12', 'admin'), ('23', '1', '12', '5', '2021-3-17-0000001', '5', '后置', '功能', '成功', '', '2021-03-17 10:08:15', '2021-03-17 10:08:15', 'admin'), ('24', '1', '12', '5', '2021-3-17-0000001', '5', '后置', '功能', '成功', '', '2021-03-17 10:08:15', '2021-03-17 10:08:15', 'admin'), ('25', '1', '12', '6', '2021-3-17-0000002', '5', '前置', '功能', '成功', '', '2021-03-17 11:04:05', '2021-03-17 11:04:05', 'admin'), ('26', '1', '12', '6', '2021-3-17-0000002', '5', '前置', '功能', '成功', '', '2021-03-17 11:04:05', '2021-03-17 11:04:05', 'admin'), ('27', '1', '12', '6', '2021-3-17-0000002', '5', '后置', '功能', '成功', '', '2021-03-17 11:04:09', '2021-03-17 11:04:09', 'admin'), ('28', '1', '12', '6', '2021-3-17-0000002', '5', '后置', '功能', '成功', '', '2021-03-17 11:04:09', '2021-03-17 11:04:09', 'admin'), ('29', '1', '12', '7', '2021-3-19-00001', '5', '前置', '功能', '成功', '', '2021-03-19 15:43:23', '2021-03-19 15:43:23', 'admin'), ('30', '1', '12', '7', '2021-3-19-00001', '5', '前置', '功能', '成功', '', '2021-03-19 15:43:23', '2021-03-19 15:43:23', 'admin'), ('31', '1', '12', '7', '2021-3-19-00001', '5', '后置', '功能', '成功', '', '2021-03-19 15:43:27', '2021-03-19 15:43:27', 'admin'), ('32', '1', '12', '7', '2021-3-19-00001', '5', '后置', '功能', '成功', '', '2021-03-19 15:43:27', '2021-03-19 15:43:27', 'admin'), ('33', '1', '12', '11', '2021-3-20-00004', '5', '前置', '功能', '成功', '', '2021-03-20 14:45:11', '2021-03-20 14:45:11', 'admin'), ('34', '1', '12', '11', '2021-3-20-00004', '5', '前置', '功能', '成功', '', '2021-03-20 14:45:11', '2021-03-20 14:45:11', 'admin'), ('35', '1', '12', '11', '2021-3-20-00004', '5', '后置', '功能', '成功', '', '2021-03-20 14:45:13', '2021-03-20 14:45:13', 'admin'), ('36', '1', '12', '11', '2021-3-20-00004', '5', '后置', '功能', '成功', '', '2021-03-20 14:45:13', '2021-03-20 14:45:13', 'admin'), ('37', '1', '12', '12', '2021-3-20-000005', '5', '前置', '功能', '成功', '', '2021-03-20 14:55:44', '2021-03-20 14:55:44', 'admin'), ('38', '1', '12', '12', '2021-3-20-000005', '5', '前置', '功能', '成功', '', '2021-03-20 14:55:44', '2021-03-20 14:55:44', 'admin'), ('39', '1', '12', '12', '2021-3-20-000005', '5', '后置', '功能', '成功', '', '2021-03-20 14:55:48', '2021-03-20 14:55:48', 'admin'), ('40', '1', '12', '12', '2021-3-20-000005', '5', '后置', '功能', '成功', '', '2021-03-20 14:55:48', '2021-03-20 14:55:48', 'admin'), ('41', '1', '12', '13', '2021-3-22-00001', '5', '前置', '功能', '成功', '', '2021-03-21 14:44:45', '2021-03-21 14:44:45', 'admin'), ('42', '1', '12', '13', '2021-3-22-00001', '5', '前置', '功能', '成功', '', '2021-03-21 14:44:45', '2021-03-21 14:44:45', 'admin'), ('43', '6', '12', '13', '2021-3-22-00001', '5', '前置', '功能', '成功', '', '2021-03-21 14:44:46', '2021-03-21 14:44:46', 'admin'), ('44', '6', '12', '13', '2021-3-22-00001', '5', '前置', '功能', '成功', '', '2021-03-21 14:44:46', '2021-03-21 14:44:46', 'admin'), ('45', '7', '12', '13', '2021-3-22-00001', '5', '前置', '功能', '成功', '', '2021-03-21 14:44:50', '2021-03-21 14:44:50', 'admin'), ('46', '7', '12', '13', '2021-3-22-00001', '5', '前置', '功能', '成功', '', '2021-03-21 14:44:50', '2021-03-21 14:44:50', 'admin'), ('47', '1', '12', '13', '2021-3-22-00001', '5', '后置', '功能', '成功', '', '2021-03-21 14:44:56', '2021-03-21 14:44:56', 'admin'), ('48', '1', '12', '13', '2021-3-22-00001', '5', '后置', '功能', '成功', '', '2021-03-21 14:44:56', '2021-03-21 14:44:56', 'admin'), ('49', '6', '12', '13', '2021-3-22-00001', '5', '后置', '功能', '成功', '', '2021-03-21 14:44:57', '2021-03-21 14:44:57', 'admin'), ('50', '6', '12', '13', '2021-3-22-00001', '5', '后置', '功能', '成功', '', '2021-03-21 14:44:57', '2021-03-21 14:44:57', 'admin'), ('51', '7', '12', '13', '2021-3-22-00001', '5', '后置', '功能', '成功', '', '2021-03-21 14:44:58', '2021-03-21 14:44:58', 'admin'), ('52', '7', '12', '13', '2021-3-22-00001', '5', '后置', '功能', '成功', '', '2021-03-21 14:44:58', '2021-03-21 14:44:58', 'admin'), ('53', '6', '12', '14', '2021-3-22-000002', '5', '前置', '功能', '成功', '', '2021-03-21 15:02:29', '2021-03-21 15:02:29', 'admin'), ('54', '6', '12', '14', '2021-3-22-000002', '5', '前置', '功能', '成功', '', '2021-03-21 15:02:29', '2021-03-21 15:02:29', 'admin'), ('55', '1', '12', '14', '2021-3-22-000002', '5', '前置', '功能', '成功', '', '2021-03-21 15:02:31', '2021-03-21 15:02:31', 'admin'), ('56', '1', '12', '14', '2021-3-22-000002', '5', '前置', '功能', '成功', '', '2021-03-21 15:02:31', '2021-03-21 15:02:31', 'admin'), ('57', '7', '12', '14', '2021-3-22-000002', '5', '前置', '功能', '成功', '', '2021-03-21 15:02:35', '2021-03-21 15:02:35', 'admin'), ('58', '7', '12', '14', '2021-3-22-000002', '5', '前置', '功能', '成功', '', '2021-03-21 15:02:35', '2021-03-21 15:02:35', 'admin'), ('59', '6', '12', '14', '2021-3-22-000002', '5', '后置', '功能', '成功', '', '2021-03-21 15:02:37', '2021-03-21 15:02:37', 'admin'), ('60', '6', '12', '14', '2021-3-22-000002', '5', '后置', '功能', '成功', '', '2021-03-21 15:02:37', '2021-03-21 15:02:37', 'admin'), ('61', '1', '12', '14', '2021-3-22-000002', '5', '后置', '功能', '成功', '', '2021-03-21 15:02:39', '2021-03-21 15:02:39', 'admin'), ('62', '1', '12', '14', '2021-3-22-000002', '5', '后置', '功能', '成功', '', '2021-03-21 15:02:39', '2021-03-21 15:02:39', 'admin'), ('63', '7', '12', '14', '2021-3-22-000002', '5', '后置', '功能', '成功', '', '2021-03-21 15:02:41', '2021-03-21 15:02:41', 'admin'), ('64', '7', '12', '14', '2021-3-22-000002', '5', '后置', '功能', '成功', '', '2021-03-21 15:02:41', '2021-03-21 15:02:41', 'admin'), ('65', '1', '12', '15', '2021-3-22-000000003', '5', '前置', '功能', '成功', '', '2021-03-21 15:05:34', '2021-03-21 15:05:34', 'admin'), ('66', '1', '12', '15', '2021-3-22-000000003', '5', '前置', '功能', '成功', '', '2021-03-21 15:05:34', '2021-03-21 15:05:34', 'admin'), ('67', '1', '12', '15', '2021-3-22-000000003', '5', '后置', '功能', '成功', '', '2021-03-21 15:05:38', '2021-03-21 15:05:38', 'admin'), ('68', '1', '12', '15', '2021-3-22-000000003', '5', '后置', '功能', '成功', '', '2021-03-21 15:05:38', '2021-03-21 15:05:38', 'admin'), ('69', '2', '13', '29', '2021-4-6-00002xn', '5', '前置', '性能', '成功', '', '2021-04-06 09:36:54', '2021-04-06 09:36:54', 'admin'), ('70', '2', '13', '29', '2021-4-6-00002xn', '5', '前置', '性能', '成功', '', '2021-04-06 09:36:54', '2021-04-06 09:36:54', 'admin'), ('71', '2', '13', '29', '2021-4-6-00002xn', '5', '后置', '性能', '成功', '', '2021-04-06 09:36:58', '2021-04-06 09:36:58', 'admin'), ('72', '2', '13', '29', '2021-4-6-00002xn', '5', '后置', '性能', '成功', '', '2021-04-06 09:36:58', '2021-04-06 09:36:58', 'admin'), ('73', '2', '13', '30', '2021-4-6-00003xn', '5', '前置', '性能', '成功', '', '2021-04-06 09:45:45', '2021-04-06 09:45:45', 'admin'), ('74', '2', '13', '30', '2021-4-6-00003xn', '5', '前置', '性能', '成功', '', '2021-04-06 09:45:45', '2021-04-06 09:45:45', 'admin'), ('75', '2', '13', '30', '2021-4-6-00003xn', '5', '后置', '性能', '成功', '', '2021-04-06 09:45:49', '2021-04-06 09:45:49', 'admin'), ('76', '2', '13', '30', '2021-4-6-00003xn', '5', '后置', '性能', '成功', '', '2021-04-06 09:45:49', '2021-04-06 09:45:49', 'admin'), ('77', '2', '13', '33', '2021-4-006xn', '5', '前置', '性能', '成功', '', '2021-04-06 10:01:14', '2021-04-06 10:01:14', 'admin'), ('78', '2', '13', '33', '2021-4-006xn', '5', '前置', '性能', '成功', '', '2021-04-06 10:01:14', '2021-04-06 10:01:14', 'admin'), ('79', '2', '13', '33', '2021-4-006xn', '5', '后置', '性能', '成功', '', '2021-04-06 10:01:19', '2021-04-06 10:01:19', 'admin'), ('80', '2', '13', '33', '2021-4-006xn', '5', '后置', '性能', '成功', '', '2021-04-06 10:01:19', '2021-04-06 10:01:19', 'admin'), ('81', '2', '13', '34', '2021-4-6-00007xn', '5', '前置', '性能', '成功', '', '2021-04-06 10:09:21', '2021-04-06 10:09:21', 'admin'), ('82', '2', '13', '34', '2021-4-6-00007xn', '5', '前置', '性能', '成功', '', '2021-04-06 10:09:21', '2021-04-06 10:09:21', 'admin'), ('83', '2', '13', '34', '2021-4-6-00007xn', '5', '后置', '性能', '成功', '', '2021-04-06 10:09:25', '2021-04-06 10:09:25', 'admin'), ('84', '2', '13', '34', '2021-4-6-00007xn', '5', '后置', '性能', '成功', '', '2021-04-06 10:09:25', '2021-04-06 10:09:25', 'admin'), ('85', '8', '13', '35', '2021-4-6-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 10:33:52', '2021-04-06 10:33:52', 'admin'), ('86', '8', '13', '35', '2021-4-6-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 10:33:52', '2021-04-06 10:33:52', 'admin'), ('87', '2', '13', '35', '2021-4-6-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 10:33:53', '2021-04-06 10:33:53', 'admin'), ('88', '2', '13', '35', '2021-4-6-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 10:33:53', '2021-04-06 10:33:53', 'admin'), ('89', '8', '13', '35', '2021-4-6-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 10:34:03', '2021-04-06 10:34:03', 'admin'), ('90', '8', '13', '35', '2021-4-6-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 10:34:03', '2021-04-06 10:34:03', 'admin'), ('91', '2', '13', '35', '2021-4-6-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 10:34:03', '2021-04-06 10:34:03', 'admin'), ('92', '2', '13', '35', '2021-4-6-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 10:34:03', '2021-04-06 10:34:03', 'admin'), ('93', '2', '13', '36', '2021-4-6-00008-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 11:39:56', '2021-04-06 11:39:56', 'admin'), ('94', '2', '13', '36', '2021-4-6-00008-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 11:39:56', '2021-04-06 11:39:56', 'admin'), ('95', '2', '13', '36', '2021-4-6-00008-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 11:40:02', '2021-04-06 11:40:02', 'admin'), ('96', '2', '13', '36', '2021-4-6-00008-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 11:40:02', '2021-04-06 11:40:02', 'admin'), ('97', '8', '13', '36', '2021-4-6-00008-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 11:53:19', '2021-04-06 11:53:19', 'admin'), ('98', '8', '13', '36', '2021-4-6-00008-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 11:53:19', '2021-04-06 11:53:19', 'admin'), ('99', '8', '13', '36', '2021-4-6-00008-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 11:53:23', '2021-04-06 11:53:23', 'admin'), ('100', '8', '13', '36', '2021-4-6-00008-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 11:53:23', '2021-04-06 11:53:23', 'admin'), ('101', '2', '13', '37', '2021-4-6-0000009-2-dep', '5', '前置', '性能', '成功', '', '2021-04-06 15:07:24', '2021-04-06 15:07:24', 'admin'), ('102', '2', '13', '37', '2021-4-6-0000009-2-dep', '5', '前置', '性能', '成功', '', '2021-04-06 15:07:24', '2021-04-06 15:07:24', 'admin'), ('103', '2', '13', '37', '2021-4-6-0000009-2-dep', '5', '后置', '性能', '成功', '', '2021-04-06 15:07:35', '2021-04-06 15:07:35', 'admin'), ('104', '2', '13', '37', '2021-4-6-0000009-2-dep', '5', '后置', '性能', '成功', '', '2021-04-06 15:07:35', '2021-04-06 15:07:35', 'admin'), ('105', '8', '13', '37', '2021-4-6-0000009-2-dep', '5', '前置', '性能', '成功', '', '2021-04-06 15:07:52', '2021-04-06 15:07:52', 'admin'), ('106', '8', '13', '37', '2021-4-6-0000009-2-dep', '5', '前置', '性能', '成功', '', '2021-04-06 15:07:52', '2021-04-06 15:07:52', 'admin'), ('107', '8', '13', '37', '2021-4-6-0000009-2-dep', '5', '后置', '性能', '成功', '', '2021-04-06 15:07:57', '2021-04-06 15:07:57', 'admin'), ('108', '8', '13', '37', '2021-4-6-0000009-2-dep', '5', '后置', '性能', '成功', '', '2021-04-06 15:07:57', '2021-04-06 15:07:57', 'admin'), ('109', '2', '13', '38', '2021-4-6-100001-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 15:43:08', '2021-04-06 15:43:08', 'admin'), ('110', '2', '13', '38', '2021-4-6-100001-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 15:43:08', '2021-04-06 15:43:08', 'admin'), ('111', '2', '13', '38', '2021-4-6-100001-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 15:43:15', '2021-04-06 15:43:15', 'admin'), ('112', '2', '13', '38', '2021-4-6-100001-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 15:43:15', '2021-04-06 15:43:15', 'admin'), ('113', '8', '13', '38', '2021-4-6-100001-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 15:43:26', '2021-04-06 15:43:26', 'admin'), ('114', '8', '13', '38', '2021-4-6-100001-2deploy', '5', '前置', '性能', '成功', '', '2021-04-06 15:43:26', '2021-04-06 15:43:26', 'admin'), ('115', '8', '13', '38', '2021-4-6-100001-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 15:43:30', '2021-04-06 15:43:30', 'admin'), ('116', '8', '13', '38', '2021-4-6-100001-2deploy', '5', '后置', '性能', '成功', '', '2021-04-06 15:43:30', '2021-04-06 15:43:30', 'admin'), ('117', '2', '13', '39', '2021-4-6-1000010-1de', '5', '前置', '性能', '成功', '', '2021-04-06 16:06:32', '2021-04-06 16:06:32', 'admin'), ('118', '2', '13', '39', '2021-4-6-1000010-1de', '5', '前置', '性能', '成功', '', '2021-04-06 16:06:32', '2021-04-06 16:06:32', 'admin'), ('119', '2', '13', '39', '2021-4-6-1000010-1de', '5', '后置', '性能', '成功', '', '2021-04-06 16:06:42', '2021-04-06 16:06:42', 'admin'), ('120', '2', '13', '39', '2021-4-6-1000010-1de', '5', '后置', '性能', '成功', '', '2021-04-06 16:06:42', '2021-04-06 16:06:42', 'admin'), ('121', '2', '13', '40', '2021-4-6-100020-1de', '5', '前置', '性能', '成功', '', '2021-04-06 16:29:51', '2021-04-06 16:29:51', 'admin'), ('122', '2', '13', '40', '2021-4-6-100020-1de', '5', '前置', '性能', '成功', '', '2021-04-06 16:29:51', '2021-04-06 16:29:51', 'admin'), ('123', '2', '13', '40', '2021-4-6-100020-1de', '5', '后置', '性能', '成功', '', '2021-04-06 16:29:59', '2021-04-06 16:29:59', 'admin'), ('124', '2', '13', '40', '2021-4-6-100020-1de', '5', '后置', '性能', '成功', '', '2021-04-06 16:29:59', '2021-04-06 16:29:59', 'admin'), ('125', '2', '13', '41', '2021-4-6-1000030-1de', '5', '前置', '性能', '成功', '', '2021-04-06 16:50:28', '2021-04-06 16:50:28', 'admin'), ('126', '2', '13', '41', '2021-4-6-1000030-1de', '5', '前置', '性能', '成功', '', '2021-04-06 16:50:28', '2021-04-06 16:50:28', 'admin'), ('127', '2', '13', '41', '2021-4-6-1000030-1de', '5', '后置', '性能', '成功', '', '2021-04-06 16:50:35', '2021-04-06 16:50:35', 'admin'), ('128', '2', '13', '41', '2021-4-6-1000030-1de', '5', '后置', '性能', '成功', '', '2021-04-06 16:50:35', '2021-04-06 16:50:35', 'admin'), ('129', '2', '13', '42', '2021-4-6-10000040', '5', '前置', '性能', '成功', '', '2021-04-06 16:55:13', '2021-04-06 16:55:13', 'admin'), ('130', '2', '13', '42', '2021-4-6-10000040', '5', '前置', '性能', '成功', '', '2021-04-06 16:55:13', '2021-04-06 16:55:13', 'admin'), ('131', '2', '13', '42', '2021-4-6-10000040', '5', '后置', '性能', '成功', '', '2021-04-06 16:55:17', '2021-04-06 16:55:17', 'admin'), ('132', '2', '13', '42', '2021-4-6-10000040', '5', '后置', '性能', '成功', '', '2021-04-06 16:55:17', '2021-04-06 16:55:17', 'admin'), ('133', '2', '13', '43', '2021-4-6-10000060', '5', '前置', '性能', '成功', '', '2021-04-06 17:00:59', '2021-04-06 17:00:59', 'admin'), ('134', '2', '13', '43', '2021-4-6-10000060', '5', '前置', '性能', '成功', '', '2021-04-06 17:00:59', '2021-04-06 17:00:59', 'admin'), ('135', '2', '13', '43', '2021-4-6-10000060', '5', '后置', '性能', '成功', '', '2021-04-06 17:01:04', '2021-04-06 17:01:04', 'admin'), ('136', '2', '13', '43', '2021-4-6-10000060', '5', '后置', '性能', '成功', '', '2021-04-06 17:01:04', '2021-04-06 17:01:04', 'admin'), ('137', '2', '13', '44', '2021-4-6-10000070', '5', '前置', '性能', '成功', '', '2021-04-06 17:12:00', '2021-04-06 17:12:00', 'admin'), ('138', '2', '13', '44', '2021-4-6-10000070', '5', '前置', '性能', '成功', '', '2021-04-06 17:12:00', '2021-04-06 17:12:00', 'admin'), ('139', '2', '13', '44', '2021-4-6-10000070', '5', '后置', '性能', '成功', '', '2021-04-06 17:12:03', '2021-04-06 17:12:03', 'admin'), ('140', '2', '13', '44', '2021-4-6-10000070', '5', '后置', '性能', '成功', '', '2021-04-06 17:12:03', '2021-04-06 17:12:03', 'admin'), ('141', '2', '13', '45', 'xxxxxx', '5', '前置', '性能', '成功', '', '2021-04-06 17:20:26', '2021-04-06 17:20:26', 'admin'), ('142', '2', '13', '45', 'xxxxxx', '5', '前置', '性能', '成功', '', '2021-04-06 17:20:26', '2021-04-06 17:20:26', 'admin'), ('143', '2', '13', '45', 'xxxxxx', '5', '后置', '性能', '成功', '', '2021-04-06 17:20:35', '2021-04-06 17:20:35', 'admin'), ('144', '2', '13', '45', 'xxxxxx', '5', '后置', '性能', '成功', '', '2021-04-06 17:20:35', '2021-04-06 17:20:35', 'admin'), ('145', '2', '13', '46', 'xxxxx11', '5', '前置', '性能', '成功', '', '2021-04-06 17:24:02', '2021-04-06 17:24:02', 'admin'), ('146', '2', '13', '46', 'xxxxx11', '5', '前置', '性能', '成功', '', '2021-04-06 17:24:02', '2021-04-06 17:24:02', 'admin'), ('147', '2', '13', '46', 'xxxxx11', '5', '后置', '性能', '成功', '', '2021-04-06 17:24:06', '2021-04-06 17:24:06', 'admin'), ('148', '2', '13', '46', 'xxxxx11', '5', '后置', '性能', '成功', '', '2021-04-06 17:24:06', '2021-04-06 17:24:06', 'admin'), ('149', '2', '13', '47', '4-7-00001', '5', '前置', '性能', '成功', '', '2021-04-07 09:09:51', '2021-04-07 09:09:51', 'admin'), ('150', '2', '13', '47', '4-7-00001', '5', '前置', '性能', '成功', '', '2021-04-07 09:09:51', '2021-04-07 09:09:51', 'admin'), ('151', '2', '13', '47', '4-7-00001', '5', '后置', '性能', '成功', '', '2021-04-07 09:09:57', '2021-04-07 09:09:57', 'admin'), ('152', '2', '13', '47', '4-7-00001', '5', '后置', '性能', '成功', '', '2021-04-07 09:09:57', '2021-04-07 09:09:57', 'admin'), ('153', '2', '13', '48', '4-7-0002', '5', '前置', '性能', '成功', '', '2021-04-07 09:14:36', '2021-04-07 09:14:36', 'admin'), ('154', '2', '13', '48', '4-7-0002', '5', '前置', '性能', '成功', '', '2021-04-07 09:14:36', '2021-04-07 09:14:36', 'admin'), ('155', '2', '13', '48', '4-7-0002', '5', '后置', '性能', '成功', '', '2021-04-07 09:14:44', '2021-04-07 09:14:44', 'admin'), ('156', '2', '13', '48', '4-7-0002', '5', '后置', '性能', '成功', '', '2021-04-07 09:14:44', '2021-04-07 09:14:44', 'admin'), ('157', '8', '13', '49', '4-7-00001-2de', '5', '前置', '性能', '成功', '', '2021-04-07 09:51:31', '2021-04-07 09:51:31', 'admin'), ('158', '8', '13', '49', '4-7-00001-2de', '5', '前置', '性能', '成功', '', '2021-04-07 09:51:31', '2021-04-07 09:51:31', 'admin'), ('159', '2', '13', '49', '4-7-00001-2de', '5', '前置', '性能', '成功', '', '2021-04-07 09:51:31', '2021-04-07 09:51:31', 'admin'), ('160', '2', '13', '49', '4-7-00001-2de', '5', '前置', '性能', '成功', '', '2021-04-07 09:51:31', '2021-04-07 09:51:31', 'admin'), ('161', '8', '13', '49', '4-7-00001-2de', '5', '后置', '性能', '成功', '', '2021-04-07 09:51:41', '2021-04-07 09:51:41', 'admin'), ('162', '8', '13', '49', '4-7-00001-2de', '5', '后置', '性能', '成功', '', '2021-04-07 09:51:41', '2021-04-07 09:51:41', 'admin'), ('163', '2', '13', '49', '4-7-00001-2de', '5', '后置', '性能', '成功', '', '2021-04-07 09:51:43', '2021-04-07 09:51:43', 'admin'), ('164', '2', '13', '49', '4-7-00001-2de', '5', '后置', '性能', '成功', '', '2021-04-07 09:51:43', '2021-04-07 09:51:43', 'admin'), ('165', '2', '17', '20', 'xn0003', '5', '前置', '性能', '成功', '', '2021-04-28 14:36:07', '2021-04-28 14:36:07', 'admin'), ('166', '2', '17', '20', 'xn0003', '5', '前置', '性能', '成功', '', '2021-04-28 14:36:07', '2021-04-28 14:36:07', 'admin'), ('167', '2', '17', '20', 'xn0003', '5', '后置', '性能', '成功', '', '2021-04-28 14:36:14', '2021-04-28 14:36:14', 'admin'), ('168', '2', '17', '20', 'xn0003', '5', '后置', '性能', '成功', '', '2021-04-28 14:36:14', '2021-04-28 14:36:14', 'admin'), ('169', '2', '13', '24', '2021-5-7-0005', '5', '前置', '性能', '成功', '', '2021-05-07 17:09:17', '2021-05-07 17:09:17', 'admin'), ('170', '2', '13', '24', '2021-5-7-0005', '5', '前置', '性能', '成功', '', '2021-05-07 17:09:17', '2021-05-07 17:09:17', 'admin'), ('171', '2', '13', '24', '2021-5-7-0005', '5', '后置', '性能', '成功', '', '2021-05-07 17:09:23', '2021-05-07 17:09:23', 'admin'), ('172', '2', '13', '24', '2021-5-7-0005', '5', '后置', '性能', '成功', '', '2021-05-07 17:09:23', '2021-05-07 17:09:23', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `apicases_performanceapdex`
-- ----------------------------
DROP TABLE IF EXISTS `apicases_performanceapdex`;
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

-- ----------------------------
--  Table structure for `apicases_performancestatistics`
-- ----------------------------
DROP TABLE IF EXISTS `apicases_performancestatistics`;
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='api用例性能统计表';

-- ----------------------------
--  Records of `apicases_performancestatistics`
-- ----------------------------
BEGIN;
INSERT INTO `apicases_performancestatistics` VALUES ('4', '2', '13', 'xn100000001', '2.02', '5', '4', '4', '100.00', '655.50', '185.00', '1366.00', '535.50', '1366.00', '1366.00', '1366.00', '2.50', '0.00', '0.00', '2020-12-17 11:34:50', '2020-12-17 11:34:50', null), ('5', '2', '13', 'xn10000003', '3.96', '5', '4', '4', '100.00', '1430.50', '143.00', '2934.00', '1322.50', '2934.00', '2934.00', '2934.00', '1.20', '0.00', '0.00', '2020-12-17 11:39:40', '2020-12-17 11:39:40', null), ('6', '2', '13', 'xn100000004', '1.48', '5', '9', '9', '100.00', '262.33', '134.00', '844.00', '142.00', '844.00', '844.00', '844.00', '8.00', '0.00', '0.00', '2020-12-17 11:43:20', '2020-12-17 11:43:20', null), ('7', '2', '13', 'xn100000004', '1.48', '5', '9', '9', '100.00', '436.78', '98.00', '1407.00', '120.00', '1407.00', '1407.00', '1407.00', '5.40', '0.00', '0.00', '2020-12-21 08:47:15', '2020-12-21 08:47:15', null), ('8', '2', '13', '2021-4-6-00007xn', '1.79', '5', '9', '9', '100.00', '342.44', '110.00', '1091.00', '142.00', '1091.00', '1091.00', '1091.00', '6.40', '0.00', '0.00', '2021-04-06 10:25:40', '2021-04-06 10:25:40', null), ('9', '8', '13', '2021-4-6-2deploy', '6.60', '5', '4', '4', '100.00', '2773.00', '244.00', '5560.00', '2644.00', '5560.00', '5560.00', '5560.00', '0.60', '0.00', '0.00', '2021-04-06 10:34:20', '2021-04-06 10:34:20', null), ('10', '2', '13', '2021-4-6-2deploy', '5.43', '5', '9', '9', '100.00', '1147.67', '246.00', '3186.00', '417.00', '3186.00', '3186.00', '3186.00', '2.20', '0.00', '0.00', '2021-04-06 10:34:20', '2021-04-06 10:34:20', null), ('11', '2', '13', '2021-4-6-00008-2deploy', '3.84', '5', '9', '9', '100.00', '1038.44', '116.00', '3204.00', '129.00', '3204.00', '3204.00', '3204.00', '2.60', '0.00', '0.00', '2021-04-06 15:05:48', '2021-04-06 15:05:48', null), ('12', '8', '13', '2021-4-6-00008-2deploy', '1.91', '5', '4', '4', '100.00', '582.50', '155.00', '1178.00', '498.50', '1178.00', '1178.00', '1178.00', '2.60', '0.00', '0.00', '2021-04-06 15:05:48', '2021-04-06 15:05:48', null), ('13', '2', '13', '2021-4-6-0000009-2-dep', '5.08', '5', '9', '9', '100.00', '1360.22', '145.00', '4040.00', '241.00', '4040.00', '4040.00', '4040.00', '1.90', '0.00', '0.00', '2021-04-06 15:07:40', '2021-04-06 15:07:40', null), ('14', '8', '13', '2021-4-6-0000009-2-dep', '1.64', '5', '4', '4', '100.00', '464.25', '139.00', '1037.00', '340.50', '1037.00', '1037.00', '1037.00', '3.30', '0.00', '0.00', '2021-04-06 15:08:20', '2021-04-06 15:08:20', null), ('15', '2', '13', '2021-4-6-100001-2deploy', '4.34', '5', '9', '9', '100.00', '1110.22', '123.00', '3329.00', '137.00', '3329.00', '3329.00', '3329.00', '2.40', '0.00', '0.00', '2021-04-06 15:43:20', '2021-04-06 15:43:20', null), ('16', '8', '13', '2021-4-6-100001-2deploy', '1.69', '5', '4', '4', '100.00', '495.00', '139.00', '1058.00', '391.50', '1058.00', '1058.00', '1058.00', '3.10', '0.00', '0.00', '2021-04-06 15:43:40', '2021-04-06 15:43:40', null), ('17', '2', '13', '2021-4-6-1000010-1de', '4.73', '5', '9', '9', '100.00', '1178.33', '121.00', '3522.00', '171.00', '3522.00', '3522.00', '3522.00', '2.20', '0.00', '0.00', '2021-04-06 16:07:00', '2021-04-06 16:07:00', null), ('18', '2', '13', '2021-4-6-100020-1de', '4.57', '5', '9', '9', '100.00', '1174.33', '145.00', '3506.00', '167.00', '3506.00', '3506.00', '3506.00', '2.20', '0.00', '0.00', '2021-04-06 16:30:20', '2021-04-06 16:30:20', null), ('19', '2', '13', '2021-4-6-1000030-1de', '4.03', '5', '9', '9', '100.00', '1043.11', '124.00', '3172.00', '146.00', '3172.00', '3172.00', '3172.00', '2.50', '0.00', '0.00', '2021-04-06 16:50:40', '2021-04-06 16:50:40', null), ('20', '2', '13', '2021-4-6-10000040', '2.02', '5', '9', '9', '100.00', '391.67', '121.00', '1118.00', '238.00', '1118.00', '1118.00', '1118.00', '5.60', '0.00', '0.00', '2021-04-06 16:55:20', '2021-04-06 16:55:20', null), ('21', '2', '13', '2021-4-6-10000060', '1.94', '5', '1', '1', '100.00', '1319.00', '1319.00', '1319.00', '1319.00', '1319.00', '1319.00', '1319.00', '0.70', '0.00', '0.00', '2021-04-06 17:01:20', '2021-04-06 17:01:20', null), ('22', '2', '13', 'xxxxxx', '4.11', '5', '1', '1', '100.00', '3666.00', '3666.00', '3666.00', '3666.00', '3666.00', '3666.00', '3666.00', '0.20', '0.00', '0.00', '2021-04-06 17:20:40', '2021-04-06 17:20:40', null), ('23', '2', '13', 'xxxxx11', '1.68', '5', '1', '0', '0.00', '1267.00', '1267.00', '1267.00', '1267.00', '1267.00', '1267.00', '1267.00', '0.70', '0.00', '0.00', '2021-04-06 17:24:20', '2021-04-06 17:24:20', null), ('24', '2', '13', '4-7-00001', '2.34', '5', '1', '0', '0.00', '1755.00', '1755.00', '1755.00', '1755.00', '1755.00', '1755.00', '1755.00', '0.50', '0.00', '0.00', '2021-04-07 09:10:00', '2021-04-07 09:10:00', null), ('25', '2', '13', '4-7-0002', '3.34', '5', '12', '0', '0.00', '541.25', '157.00', '1855.00', '235.50', '1743.40', '1855.00', '1855.00', '4.70', '0.00', '0.00', '2021-04-07 09:15:00', '2021-04-07 09:15:00', null), ('26', '8', '13', '4-7-00001-2de', '4.40', '5', '4', '4', '100.00', '1602.50', '167.00', '3316.00', '1463.50', '3316.00', '3316.00', '3316.00', '1.10', '0.00', '0.00', '2021-04-07 09:52:00', '2021-04-07 09:52:00', null), ('27', '2', '13', '4-7-00001-2de', '4.31', '5', '12', '0', '0.00', '702.17', '148.00', '2419.00', '232.50', '2332.00', '2419.00', '2419.00', '3.70', '0.00', '0.00', '2021-04-07 09:52:00', '2021-04-07 09:52:00', null), ('28', '2', '17', 'xn0003', '5.03', '5', '12', '0', '0.00', '1038.50', '144.00', '4049.00', '208.00', '3873.80', '4049.00', '4049.00', '2.60', '0.00', '0.00', '2021-04-28 14:36:20', '2021-04-28 14:36:20', null), ('29', '2', '13', '2021-5-7-0005', '2.19', '5', '12', '0', '0.00', '343.17', '126.00', '1234.00', '163.00', '1143.70', '1234.00', '1234.00', '6.90', '0.00', '0.00', '2021-05-07 17:09:40', '2021-05-07 17:09:40', null);
COMMIT;

-- ----------------------------
--  Table structure for `apicases_report`
-- ----------------------------
DROP TABLE IF EXISTS `apicases_report`;
CREATE TABLE `apicases_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `respone` varchar(5000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '返回结果',
  `assertvalue` varchar(5000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言详细经过',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '期望值',
  `errorinfo` varchar(5000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '错误信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='api用例报告表';

-- ----------------------------
--  Records of `apicases_report`
-- ----------------------------
BEGIN;
INSERT INTO `apicases_report` VALUES ('1', '1', '12', '502', '8', '失败', '', '', '42174', '$.message=Success|$.result=http://b6i.cn/4Vgxk|$.code=0', '请求url:https://marketing-ui.confucius.mobi/redeem/ui/retrySendSmsOrFindShortUrl?orderId=2012030033263636700&goodsThirdPartyType=ALIPAY&status=OFF发生异常，原因：marketing-ui.confucius.mobi: 未知的名称或服务', '2021-05-26 16:47:59', '2021-05-26 16:47:59', 'admin'), ('2', '1', '12', '521', '8', '失败', '', '', '43312', '$.message=Success|$.result=http://b6i.cn/4Vgxk|$.code=0', '请求url:https://marketing-ui.confucius.mobi/redeem/ui/retrySendSmsOrFindShortUrl?orderId=2012030033263636700&goodsThirdPartyType=ALIPAY&status=OFF发生异常，原因：marketing-ui.confucius.mobi: 未知的名称或服务', '2021-05-26 16:56:53', '2021-05-26 16:56:53', 'admin'), ('3', '39', '12', '521', '8', '失败', '', '', '42116', '$.message=Success|$.result=http://b6i.cn/4Vgxk|$.code=0', '请求url:https://marketing-ui.confucius.mobi/redeem/ui/retrySendSmsOrFindShortUrl?orderId=2012030033263636700&goodsThirdPartyType=ALIPAY&status=OFF发生异常，原因：marketing-ui.confucius.mobi: 未知的名称或服务', '2021-05-26 16:56:54', '2021-05-26 16:56:54', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `apicases_report_performance`
-- ----------------------------
DROP TABLE IF EXISTS `apicases_report_performance`;
CREATE TABLE `apicases_report_performance` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) unsigned NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) unsigned NOT NULL COMMENT '执行机id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `respone` varchar(5000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '返回结果',
  `assertvalue` varchar(5000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言详细经过',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '期望值',
  `errorinfo` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '错误信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='api用例报告表';

-- ----------------------------
--  Table structure for `apicases_reportstatics`
-- ----------------------------
DROP TABLE IF EXISTS `apicases_reportstatics`;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='api计划批量用例统计报告表';

-- ----------------------------
--  Records of `apicases_reportstatics`
-- ----------------------------
BEGIN;
INSERT INTO `apicases_reportstatics` VALUES ('1', '12', '22', '502', '8', '1', '0', '1', '42174', '2021-05-26 16:47:59', '2021-05-26 16:47:59', 'admin'), ('2', '12', '22', '521', '8', '1', '0', '1', '43312', '2021-05-26 16:56:53', '2021-05-26 16:56:53', 'admin'), ('3', '12', '22', '521', '8', '1', '0', '1', '42116', '2021-05-26 16:56:54', '2021-05-26 16:56:54', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `deployunit`
-- ----------------------------
DROP TABLE IF EXISTS `deployunit`;
CREATE TABLE `deployunit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发布单元名',
  `protocal` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '协议',
  `port` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '访问端口',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT NULL COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='发布单元表';

-- ----------------------------
--  Records of `deployunit`
-- ----------------------------
BEGIN;
INSERT INTO `deployunit` VALUES ('1', 'accountservice', 'https', '8080', '账务服务', '2019-07-01 00:00:00', '2021-04-26 19:04:33', 'admin'), ('2', 'paymentservice', 'rpc', '8081', '支付服务', '2019-07-01 00:00:00', '2021-05-27 03:05:00', 'admin'), ('15', 'testservice', 'https', '8080', '测试中心', '2020-10-17 17:46:17', '2021-05-27 03:05:04', 'admin'), ('19', 'cornerservice', 'https', '80', 'cornerservice', '2020-11-16 20:26:10', '2021-05-27 03:05:12', 'admin'), ('20', 'regressionservice', 'python', '8081', 'regressionservice', '2020-12-01 14:06:08', '2021-05-27 03:05:24', 'admin'), ('22', 'marketingservice', 'https', '80', '市场服务', '2020-12-07 16:05:33', '2021-05-27 03:05:35', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `dicname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '字典类名',
  `diccode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典编码',
  `dicitemname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项名',
  `dicitmevalue` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT NULL COMMENT '上一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
--  Records of `dictionary`
-- ----------------------------
BEGIN;
INSERT INTO `dictionary` VALUES ('1', '测试环境', 'testenviroment1', '功能测试环境1', 'te112', '2019-07-01 00:00:00', '2020-04-21 17:04:47'), ('2', '测试环境', 'testenviroment', '功能测试环境2', 'te2', '2019-07-01 00:00:00', '2020-04-22 22:04:11'), ('3', '测试人员职务', 'testerlevel', '初级测试工程师', 'juniortester', '2019-07-01 00:00:00', '2019-07-01 00:00:00'), ('4', '测试人员职务', 'testerlevel', '中级测试工程师', 'middletester', '2019-07-01 00:00:00', '2019-07-01 00:00:00'), ('5', 'http请求方式', 'httpmethod', '查询', 'get', '2020-04-17 17:57:30', '2019-07-01 00:00:00'), ('6', 'http请求方式', 'httpmethod', '提交数据', 'post', '2020-04-17 17:59:02', '2020-11-15 15:11:35'), ('9', '测试环境', '测试环境', '测试环境', '测试环境', '2020-04-20 15:42:10', null), ('13', '测试组织', 'testerorg', '测试一组', 'testfirstgroup', '2020-04-22 20:40:09', '2020-04-22 20:04:52'), ('16', '业务线', 'businessunit', '机票线', 'airplane', '2020-04-24 14:29:53', null), ('17', '访问方式', 'httpvisittype', 'http访问方式', 'get', '2020-05-18 21:05:24', null), ('18', '访问方式', 'httpvisittype', 'http访问方式', 'post', '2020-05-18 21:05:55', null), ('19', '服务器作用', 'machineuse', '部署', '部署发布单元', '2020-09-09 12:26:21', '2020-09-09 12:09:53'), ('20', '服务器作用', 'machineuse', '执行测试', '功能测试用例执行机', '2020-09-09 12:27:40', '2020-09-09 12:09:26'), ('21', '服务器作用', 'machineuse', '执行测试', '性能测试执行机', '2020-09-09 12:29:25', null), ('25', '准入准出条件', 'casecondition', '用例数据库准入准出条件', '数据库', '2020-11-02 08:27:51', null), ('26', '准入准出条件', 'casecondition', '用例接口准入准出条件', '接口', '2020-11-02 08:28:15', null), ('27', '数据库类型', 'DBType', '用例前后置数据库类型', 'Mysql', '2020-11-02 08:29:29', null), ('28', '数据库类型', 'DBType', '用例前后置数据库类型', 'Oracle', '2020-11-02 08:30:29', null), ('29', '数据库类型', 'DBType', '用例前后置数据库类型', 'Sqlserver', '2020-11-02 08:31:05', null), ('34', '环境部署内容', 'machinedeploy', '数据库', 'mysql', '2020-11-06 15:43:18', null), ('35', '环境部署内容', 'machinedeploy', '数据库', 'oracle', '2020-11-06 15:43:36', null), ('36', '环境部署内容', 'machinedeploy', 'nosql', 'redis', '2020-11-06 15:43:54', null), ('37', '环境部署内容', 'machinedeploy', 'nosql', 'mongodb', '2020-11-06 15:44:15', null), ('39', 'api请求格式', 'requestcontentype', '请求数据格式', 'form表单', '2020-11-10 08:43:55', null), ('40', 'api请求格式', 'requestcontentype', '请求数据格式', 'json', '2020-11-10 08:44:19', null), ('41', 'api响应格式', 'responecontenttype', '响应数据格式', 'json', '2020-11-10 08:50:28', null), ('42', 'api响应格式', 'responecontenttype', '响应数据格式', 'html', '2020-11-10 08:55:14', null), ('43', 'http请求方式', 'httpmethod', '更新', 'put', '2020-11-15 15:41:55', null), ('44', 'http请求方式', 'httpmethod', '删除', 'delete', '2020-11-15 15:42:14', null), ('45', '访问方式', 'httpvisittype', '更新', 'put', '2020-11-15 15:47:03', null), ('46', '访问方式', 'httpvisittype', '删除', 'delete', '2020-11-15 15:47:20', null), ('47', '功能执行机最大并发数', 'FunctionJmeterProcess', '功能执行机并发Jmeter进程', '2', '2020-11-28 17:02:39', '2021-04-02 12:04:57'), ('49', 'python路径', 'pythonpath', '交易python文件路径', '/Users/fanseasn/Work/ValueCode/ifpay_boss/hellopython', '2020-12-03 10:51:33', null), ('51', '调度服务', 'dispatchservice', 'dispatchservice', '127.0.0.1:8082', '2021-03-21 14:51:02', null), ('52', '性能执行机Jmeter并发数', 'PerformanceJmeterProcess', '性能测试Jmeter并行数', '1', '2021-04-07 09:08:10', '2021-05-26 23:05:05'), ('53', '执行计划业务类型', 'planbusinesstype', '执行计划业务类型', '常规测试', '2021-04-20 17:24:17', null), ('54', '执行计划业务类型', 'planbusinesstype', '执行计划业务类型', 'CI自动化测试', '2021-04-20 17:24:48', null);
COMMIT;

-- ----------------------------
--  Table structure for `dispatch`
-- ----------------------------
DROP TABLE IF EXISTS `dispatch`;
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
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'jmeter-class',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'jmeter-class',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '待分配，已分配，已结束',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `plantype` varchar(20) DEFAULT NULL COMMENT '计划类型',
  `threadnum` bigint(20) DEFAULT NULL COMMENT '线程数',
  `loops` bigint(20) DEFAULT NULL COMMENT '循环数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='调度表';

-- ----------------------------
--  Records of `dispatch`
-- ----------------------------
BEGIN;
INSERT INTO `dispatch` VALUES ('1', '12', '兑换服务回归测试', '1', '502', '8', '功能测试机器1', '1', '获取用户信息正确', 'httpapi', 'marketingservice', '$.message=Success|$.result=http://b6i.cn/4Vgxk|$.code=0', '已完成', '2021-05-27 00:05:27', '2021-05-26 16:47:59', '功能', '1', '1'), ('2', '12', '兑换服务回归测试', '2', '521', '8', '功能测试机器1', '1', '获取用户信息正确', 'httpapi', 'marketingservice', '$.message=Success|$.result=http://b6i.cn/4Vgxk|$.code=0', '已完成', '2021-05-27 00:05:01', '2021-05-26 16:56:53', '功能', '1', '1'), ('3', '12', '兑换服务回归测试', '2', '521', '8', '功能测试机器1', '39', '复制用户信息正确', 'httpapi', 'marketingservice', '$.message=Success|$.result=http://b6i.cn/4Vgxk|$.code=0', '已完成', '2021-05-27 00:05:01', '2021-05-26 16:56:54', '功能', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `enviroment`
-- ----------------------------
DROP TABLE IF EXISTS `enviroment`;
CREATE TABLE `enviroment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境名',
  `envtype` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境类型',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COMMENT='环境表';

-- ----------------------------
--  Records of `enviroment`
-- ----------------------------
BEGIN;
INSERT INTO `enviroment` VALUES ('5', '功能测试环境', '功能', '测试部门功能测试环境', '2020-11-20 16:37:39', '2021-04-23 19:04:31', 'test'), ('19', '性能测试环境', '性能', '测试部门性能测试环境', '2020-12-07 16:36:59', '2021-04-26 19:04:22', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `enviroment_assemble`
-- ----------------------------
DROP TABLE IF EXISTS `enviroment_assemble`;
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='环境组件表';

-- ----------------------------
--  Records of `enviroment_assemble`
-- ----------------------------
BEGIN;
INSERT INTO `enviroment_assemble` VALUES ('1', '账户mysql数据库', 'mysql', 'username=root,password=root,db=admin_test,port=3306', 'test', '2019-07-01 00:00:00', '2020-11-29 20:11:08', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `envmachine`
-- ----------------------------
DROP TABLE IF EXISTS `envmachine`;
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COMMENT='环境服务器表';

-- ----------------------------
--  Records of `envmachine`
-- ----------------------------
BEGIN;
INSERT INTO `envmachine` VALUES ('16', '5', '功能测试环境', '7', '自己的测试服务器', '2020-10-26 17:03:39', '2021-05-13 09:05:30', 'admin'), ('20', '19', '性能测试环境1', '7', '自己的测试服务器', '2020-12-07 16:37:11', '2020-12-07 16:37:11', null), ('21', '5', '功能测试环境', '11', '4', '2021-04-23 19:38:48', '2021-04-23 19:38:48', null), ('22', '19', '性能测试环境', '12', '6', '2021-04-26 19:24:51', '2021-04-26 19:24:51', 'admin'), ('23', '5', '功能测试环境', '7', '测试服务器', '2021-05-13 09:53:38', '2021-05-13 09:53:38', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `executeplan`
-- ----------------------------
DROP TABLE IF EXISTS `executeplan`;
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='执行计划表';

-- ----------------------------
--  Records of `executeplan`
-- ----------------------------
BEGIN;
INSERT INTO `executeplan` VALUES ('12', '5', '功能测试环境', '兑换服务回归测试', 'finish', '功能', '兑换服务回归测试', '2020-12-07 16:08:46', '2021-05-27 03:05:22', 'CI自动化测试', 'admin', '多机执行'), ('13', '5', '性能测试环境1', '兑换服务性能测试', 'finish', '性能', '兑换服务性能测试', '2020-12-07 16:38:32', '2021-05-27 03:05:31', '常规测试', 'admin', '单机运行');
COMMIT;

-- ----------------------------
--  Table structure for `executeplan_testcase`
-- ----------------------------
DROP TABLE IF EXISTS `executeplan_testcase`;
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='执行计划用例表';

-- ----------------------------
--  Records of `executeplan_testcase`
-- ----------------------------
BEGIN;
INSERT INTO `executeplan_testcase` VALUES ('10', '17', '1', '22', 'marketingservice', '获取短链', '2', '获取用户信息性能', '2021-04-28 14:04:12', '2021-04-28 14:04:12', null), ('11', '20', '1', '22', 'marketingservice', '获取短链', '1', '获取用户信息正确111', '2021-05-15 23:05:39', '2021-05-15 23:05:39', null), ('13', '20', '1', '22', 'marketingservice', '获取短链', '38', '掉地1', '2021-05-16 09:05:45', '2021-05-16 09:05:45', null), ('14', '20', '3', '19', 'cornerservice', '新获取短链', '22', '2', '2021-05-16 09:05:36', '2021-05-16 09:05:36', null), ('15', '20', '3', '19', 'cornerservice', '新获取短链', '23', '3', '2021-05-16 10:05:28', '2021-05-16 10:05:28', null), ('16', '20', '3', '19', 'cornerservice', '新获取短链', '24', '4', '2021-05-16 10:05:28', '2021-05-16 10:05:28', null), ('17', '20', '3', '19', 'cornerservice', '新获取短链', '25', '5', '2021-05-16 10:05:28', '2021-05-16 10:05:28', null), ('22', '20', '2', '1', 'accountservice', '新获取短链', '8', '新获取短链正确', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('23', '20', '2', '1', 'accountservice', '新获取短链', '28', '测试创建者', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('24', '20', '2', '1', 'accountservice', '新获取短链', '29', 'creator', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('25', '20', '2', '1', 'accountservice', '新获取短链', '30', '1111122', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('26', '20', '2', '1', 'accountservice', '新获取短链', '31', 'xxxxz', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('27', '20', '2', '1', 'accountservice', '新获取短链', '32', 'cccccccc', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('28', '20', '2', '1', 'accountservice', '新获取短链', '33', 'vvvvv', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('29', '20', '2', '1', 'accountservice', '新获取短链', '34', 'copy1', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('30', '20', '2', '1', 'accountservice', '新获取短链', '35', 'copy2', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('31', '20', '2', '1', 'accountservice', '新获取短链', '36', 'copy3', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('32', '20', '2', '1', 'accountservice', '新获取短链', '37', 'copy4', '2021-05-16 11:05:07', '2021-05-16 11:05:07', 'admin'), ('33', '21', '2', '1', 'accountservice', '新获取短链', '8', '新获取短链正确', '2021-05-16 11:05:33', '2021-05-16 11:05:33', 'admin'), ('34', '12', '1', '22', 'marketingservice', '获取短链', '1', '获取用户信息正确', '2021-05-26 23:05:09', '2021-05-26 23:05:09', 'admin'), ('35', '12', '1', '22', 'marketingservice', '获取短链', '39', '复制用户信息正确', '2021-05-27 00:05:46', '2021-05-27 00:05:46', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `executeplanbatch`
-- ----------------------------
DROP TABLE IF EXISTS `executeplanbatch`;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='执行计划表';

-- ----------------------------
--  Records of `executeplanbatch`
-- ----------------------------
BEGIN;
INSERT INTO `executeplanbatch` VALUES ('1', '12', '502', '2021-05-27 00:17:26', '2021-05-27 00:17:26', 'admin', '已完成', '平台', '兑换服务回归测试'), ('2', '12', '521', '2021-05-27 00:26:00', '2021-05-27 00:26:00', 'admin', '已完成', '平台', '兑换服务回归测试');
COMMIT;

-- ----------------------------
--  Table structure for `macdepunit`
-- ----------------------------
DROP TABLE IF EXISTS `macdepunit`;
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COMMENT='服务器发布单元表';

-- ----------------------------
--  Records of `macdepunit`
-- ----------------------------
BEGIN;
INSERT INTO `macdepunit` VALUES ('8', '1', null, 'accountservice', '发布单元', '7', '自己的测试服务器', '5', '功能测试环境', '域名', 'marketing-ui.confucius.mobi', '2020-11-16 10:16:33', '2021-04-26 19:04:35', 'admin'), ('9', '19', null, 'cornerservice', '发布单元', '7', '测试服务器', '5', '功能测试环境', '域名', 'api.cdmtzz.com', '2020-11-16 20:54:52', '2021-04-02 19:04:37', null), ('10', null, '1', '账户mysql数据库', '组件', '7', '自己的测试服务器', '5', '功能测试环境', '', '', '2020-11-29 15:32:24', '2021-04-02 19:04:27', null), ('11', '22', null, 'marketingservice', '发布单元', '7', '自己的测试服务器', '5', '功能测试环境', '域名', 'marketing-ui.confucius.mobi', '2020-12-07 16:10:06', '2021-04-02 19:04:35', null), ('12', '22', null, 'marketingservice', '发布单元', '7', '自己的测试服务器', '19', '性能测试环境', '域名', 'marketing-ui.confucius.mobi', '2020-12-07 16:37:58', '2021-04-02 19:04:48', null), ('19', null, '10', '19', '组件', '8', '1', '19', '性能测试环境', '', '', '2021-04-26 19:25:50', '2021-04-26 19:25:50', 'admin'), ('20', '19', null, 'cornerservice', '发布单元', '12', '6', '5', '功能测试环境', 'ip', '', '2021-05-13 10:36:24', '2021-05-13 10:36:24', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `machine`
-- ----------------------------
DROP TABLE IF EXISTS `machine`;
CREATE TABLE `machine` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '机器名',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'ip',
  `cpu` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'cpu',
  `disk` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'disk',
  `mem` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '内存',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT NULL COMMENT '上一次修改时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='服务器表';

-- ----------------------------
--  Records of `machine`
-- ----------------------------
BEGIN;
INSERT INTO `machine` VALUES ('7', '测试服务器', '127.0.0.1', '4', '100G', '8G', '2020-10-17 17:53:43', '2020-10-17 17:53:43', null);
COMMIT;

-- ----------------------------
--  Table structure for `performancereportsource`
-- ----------------------------
DROP TABLE IF EXISTS `performancereportsource`;
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COMMENT='性能报告路径表';

-- ----------------------------
--  Records of `performancereportsource`
-- ----------------------------
BEGIN;
INSERT INTO `performancereportsource` VALUES ('4', '13', '17', 'xn00000009', '5', '2', 'retrySendSmsOrFindShortUrl', '1.00', '/Users/fanseasn/Desktop/testresult/13-2-xn00000009', '已解析', '2020-12-17 10:16:56', '2020-12-17 10:16:56'), ('5', '13', '18', 'xn10000000', '5', '2', 'retrySendSmsOrFindShortUrl', '1.61', '/Users/fanseasn/Desktop/testresult/13-2-xn10000000', '已解析', '2020-12-17 10:29:36', '2020-12-17 10:29:36'), ('6', '13', '19', 'xn100000001', '5', '2', 'retrySendSmsOrFindShortUrl', '2.02', '/Users/fanseasn/Desktop/testresult/13-2-xn100000001', '已解析', '2020-12-17 11:17:17', '2020-12-17 11:17:17'), ('7', '13', '20', 'xn10000003', '5', '2', 'retrySendSmsOrFindShortUrl', '3.96', '/Users/fanseasn/Desktop/testresult/13-2-xn10000003', '已解析', '2020-12-17 11:39:19', '2020-12-17 11:39:19'), ('8', '13', '21', 'xn100000004', '5', '2', 'retrySendSmsOrFindShortUrl', '1.48', '/Users/fanseasn/Desktop/testresult/13-2-xn100000004', '已解析', '2020-12-17 11:42:58', '2020-12-17 11:42:58'), ('9', '12', '41', 'xxxxxxxxxxxxxxxxxxxxx', '5', '1', 'retrySendSmsOrFindShortUrl', '7757459.87', '/Users/fanseasn/Desktop/testresult/13-2-x100001', '待解析', '2021-03-16 11:15:53', '2021-03-16 11:15:53'), ('10', '12', '41', 'xxxxxxxxxxxxxxxxxxxxx', '5', '1', 'retrySendSmsOrFindShortUrl', '7757681.87', '/Users/fanseasn/Desktop/testresult/13-2-x100001', '待解析', '2021-03-16 11:19:33', '2021-03-16 11:19:33'), ('11', '12', '2', 'xxxxxxxxxxxxxxxxxxxxx', '5', '1', 'retrySendSmsOrFindShortUrl', '7763264.03', '/Users/fanseasn/Desktop/testresult/13-2-x100001', '待解析', '2021-03-16 12:52:35', '2021-03-16 12:52:35'), ('12', '12', '2', 'xxxxxxxxxxxxxxxxxxxxx', '5', '1', 'retrySendSmsOrFindShortUrl', '7763322.61', '/Users/fanseasn/Desktop/testresult/13-2-x100001', '待解析', '2021-03-16 12:53:35', '2021-03-16 12:53:35'), ('13', '13', '34', '2021-4-6-00007xn', '5', '2', 'HttpApiPerformance', '1.79', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-00007xn', '已解析', '2021-04-06 10:09:25', '2021-04-06 10:09:25'), ('14', '13', '35', '2021-4-6-2deploy', '5', '8', 'HttpApiPerformance', '6.60', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-8-2021-4-6-2deploy', '已解析', '2021-04-06 10:34:03', '2021-04-06 10:34:03'), ('15', '13', '35', '2021-4-6-2deploy', '5', '2', 'HttpApiPerformance', '5.43', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-2deploy', '已解析', '2021-04-06 10:34:03', '2021-04-06 10:34:03'), ('16', '13', '36', '2021-4-6-00008-2deploy', '5', '2', 'HttpApiPerformance', '3.84', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-00008-2deploy', '已解析', '2021-04-06 11:40:02', '2021-04-06 11:40:02'), ('17', '13', '36', '2021-4-6-00008-2deploy', '5', '8', 'HttpApiPerformance', '1.91', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-8-2021-4-6-00008-2deploy', '已解析', '2021-04-06 11:53:23', '2021-04-06 11:53:23'), ('18', '13', '37', '2021-4-6-0000009-2-dep', '5', '2', 'HttpApiPerformance', '5.08', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-0000009-2-dep', '已解析', '2021-04-06 15:07:35', '2021-04-06 15:07:35'), ('19', '13', '37', '2021-4-6-0000009-2-dep', '5', '8', 'HttpApiPerformance', '1.64', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-8-2021-4-6-0000009-2-dep', '已解析', '2021-04-06 15:07:57', '2021-04-06 15:07:57'), ('20', '13', '38', '2021-4-6-100001-2deploy', '5', '2', 'HttpApiPerformance', '4.34', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-100001-2deploy', '已解析', '2021-04-06 15:43:15', '2021-04-06 15:43:15'), ('21', '13', '38', '2021-4-6-100001-2deploy', '5', '8', 'HttpApiPerformance', '1.69', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-8-2021-4-6-100001-2deploy', '已解析', '2021-04-06 15:43:30', '2021-04-06 15:43:30'), ('22', '13', '39', '2021-4-6-1000010-1de', '5', '2', 'HttpApiPerformance', '4.73', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-1000010-1de', '已解析', '2021-04-06 16:06:42', '2021-04-06 16:06:42'), ('23', '13', '40', '2021-4-6-100020-1de', '5', '2', 'HttpApiPerformance', '4.57', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-100020-1de', '已解析', '2021-04-06 16:29:59', '2021-04-06 16:29:59'), ('24', '13', '41', '2021-4-6-1000030-1de', '5', '2', 'HttpApiPerformance', '4.03', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-1000030-1de', '已解析', '2021-04-06 16:50:35', '2021-04-06 16:50:35'), ('25', '13', '42', '2021-4-6-10000040', '5', '2', 'HttpApiPerformance', '2.02', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-10000040', '已解析', '2021-04-06 16:55:17', '2021-04-06 16:55:17'), ('26', '13', '43', '2021-4-6-10000060', '5', '2', 'HttpApiPerformance', '1.94', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-10000060', '已解析', '2021-04-06 17:01:04', '2021-04-06 17:01:04'), ('27', '13', '44', '2021-4-6-10000070', '5', '2', 'HttpApiPerformance', '0.49', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-4-6-10000070', '待解析', '2021-04-06 17:12:03', '2021-04-06 17:12:03'), ('28', '13', '45', 'xxxxxx', '5', '2', 'HttpApiPerformance', '4.11', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-xxxxxx', '已解析', '2021-04-06 17:20:35', '2021-04-06 17:20:35'), ('29', '13', '46', 'xxxxx11', '5', '2', 'HttpApiPerformance', '1.68', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-xxxxx11', '已解析', '2021-04-06 17:24:06', '2021-04-06 17:24:06'), ('30', '13', '47', '4-7-00001', '5', '2', 'HttpApiPerformance', '2.34', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-4-7-00001', '已解析', '2021-04-07 09:09:57', '2021-04-07 09:09:57'), ('31', '13', '48', '4-7-0002', '5', '2', 'HttpApiPerformance', '3.34', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-4-7-0002', '已解析', '2021-04-07 09:14:44', '2021-04-07 09:14:44'), ('32', '13', '49', '4-7-00001-2de', '5', '8', 'HttpApiPerformance', '4.40', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-8-4-7-00001-2de', '已解析', '2021-04-07 09:51:41', '2021-04-07 09:51:41'), ('33', '13', '49', '4-7-00001-2de', '5', '2', 'HttpApiPerformance', '4.31', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-4-7-00001-2de', '已解析', '2021-04-07 09:51:43', '2021-04-07 09:51:43'), ('34', '17', '20', 'xn0003', '5', '2', 'HttpApiPerformance', '5.03', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/17-2-xn0003', '已解析', '2021-04-28 14:36:14', '2021-04-28 14:36:14'), ('35', '13', '24', '2021-5-7-0005', '5', '2', 'HttpApiPerformance', '2.19', '/Users/fanseasn/Work/QAP/testplantform/slaverservice/performancereport/13-2-2021-5-7-0005', '已解析', '2021-05-07 17:09:23', '2021-05-07 17:09:23');
COMMIT;

-- ----------------------------
--  Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限Id',
  `resource` varchar(256) NOT NULL COMMENT '权限对应的资源',
  `code` varchar(256) NOT NULL COMMENT '权限的代码/通配符,对应代码中@hasAuthority(xx)',
  `handle` varchar(256) NOT NULL COMMENT '对应的资源操作',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
--  Records of `permission`
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES ('1', '账号', 'account:list', '列表'), ('2', '账号', 'account:detail', '详情'), ('3', '账号', 'account:add', '添加'), ('4', '账号', 'account:update', '更新'), ('5', '账号', 'account:delete', '删除'), ('6', '账号', 'account:search', '搜索'), ('7', '角色', 'role:list', '列表'), ('8', '角色', 'role:detail', '详情'), ('9', '角色', 'role:add', '添加'), ('10', '角色', 'role:update', '更新'), ('11', '角色', 'role:delete', '删除'), ('12', '角色', 'role:search', '搜索'), ('13', '测试表', 'table:list', '列表'), ('14', '字典', 'dictionary:list', '列表'), ('15', '字典', 'dictionary:add', '添加'), ('16', '字典', 'dictionary:search', '搜索'), ('17', '字典', 'dictionary:update', '修改'), ('18', '字典', 'dictionary:delete', '删除'), ('19', '发布单元', 'depunit:list', '列表'), ('20', '发布单元', 'depunit:detail', '详情'), ('21', '发布单元', 'depunit:add', '添加'), ('22', '发布单元', 'depunit:update', '更新'), ('23', '发布单元', 'depunit:delete', '删除'), ('24', '发布单元', 'depunit:search', '搜索'), ('25', '测试环境', 'enviroment:list', '列表'), ('26', '测试环境', 'enviroment:detail', '详情'), ('27', '测试环境', 'enviroment:add', '添加'), ('28', '测试环境', 'enviroment:update', '更新'), ('29', '测试环境', 'enviroment:delete', '删除'), ('30', '测试环境', 'enviroment:search', '搜索'), ('31', '服务器', 'machine:list', '列表'), ('32', '服务器', 'machine:detail', '详情'), ('33', '服务器', 'machine:add', '添加'), ('34', '服务器', 'machine:update', '更新'), ('35', '服务器', 'machine:delete', '删除'), ('36', '服务器', 'machine:search', '搜索'), ('37', '测试人员', 'tester:list', '列表'), ('38', '测试人员', 'tester:detail', '详情'), ('39', '测试人员', 'tester:add', '添加'), ('40', '测试人员', 'tester:update', '更新'), ('41', '测试人员', 'tester:delete', '删除'), ('42', '测试人员', 'tester:search', '搜索'), ('43', 'api', 'api:list', '列表'), ('44', 'api', 'api:detail', '详情'), ('45', 'api', 'api:add', '添加'), ('46', 'api', 'api:update', '更新'), ('47', 'api', 'api:delete', '删除'), ('48', 'api', 'api:search', '搜索'), ('49', 'api参数', 'apiparams:list', '列表'), ('50', 'api参数', 'apiparams:detail', '详情'), ('51', 'api参数', 'apiparams:add', '添加'), ('52', 'api参数', 'apiparams:update', '更新'), ('53', 'api参数', 'apiparams:delete', '删除'), ('54', 'api参数', 'apiparams:search', '搜索'), ('55', '环境服务器', 'envmachine:list', '列表'), ('56', '环境服务器', 'envmachine:detail', '详情'), ('57', '环境服务器', 'envmachine:add', '添加'), ('58', '环境服务器', 'envmachine:update', '更新'), ('59', '环境服务器', 'envmachine:delete', '删除'), ('60', '环境服务器', 'envmachine:search', '搜索'), ('61', '服务器发布单元', 'macdepunit:list', '列表'), ('62', '服务器发布单元', 'macdepunit:detail', '详情'), ('63', '服务器发布单元', 'macdepunit:add', '添加'), ('64', '服务器发布单元', 'macdepunit:update', '更新'), ('65', '服务器发布单元', 'macdepunit:delete', '删除'), ('66', '服务器发布单元', 'macdepunit:search', '搜索'), ('67', 'API用例库', 'apicases:list', '列表'), ('68', 'API用例库', 'apicases:detail', '详情'), ('69', 'API用例库', 'apicases:add', '增加'), ('70', 'API用例库', 'apicases:update', '更新'), ('71', 'API用例库', 'apicases:delete', '删除'), ('72', 'API用例库', 'apicases:search', '查询'), ('73', '执行机', 'slaver:list', '列表'), ('74', '执行机', 'slaver:detail', '详情'), ('75', '执行机', 'slaver:add', '增加'), ('76', '执行机', 'slaver:update', '更新'), ('77', '执行机', 'slaver:delete', '删除'), ('78', '执行机', 'slaver:search', '查询'), ('79', '执行计划', 'executeplan:list', '列表'), ('80', '执行计划', 'executeplan:detail', '详情'), ('81', '执行计划', 'executeplan:add', '增加'), ('82', '执行计划', 'executeplan:update', '更新'), ('83', '执行计划', 'executeplan:delete', '删除'), ('84', '执行计划', 'executeplan:search', '查询'), ('85', 'api报告', 'apireport:list', '列表'), ('86', 'api报告', 'apireport:detail', '详情'), ('87', 'api报告', 'apireport:add', '增加'), ('88', 'api报告', 'apireport:update', '更新'), ('89', 'api报告', 'apireport:delete', '删除'), ('90', 'api报告', 'apireport:search', '查询'), ('91', 'API用例库', 'apicases:params', '参数'), ('92', '用例前后条件', 'apicases_condition:list', '列表'), ('93', '用例前后条件', 'apicases_condition:detail', '详情'), ('94', '用例前后条件', 'apicases_condition:add', '增加'), ('95', '用例前后条件', 'apicases_condition:update', '更新'), ('96', '用例前后条件', 'apicases_condition:delete', '删除'), ('97', '用例前后条件', 'apicases_condition:search', '查询'), ('98', '环境组件', 'enviroment_assemble:list', '列表'), ('99', '环境组件', 'enviroment_assemble:detail', '详情'), ('100', '环境组件', 'enviroment_assemble:add', '增加'), ('101', '环境组件', 'enviroment_assemble:update', '更新'), ('102', '环境组件', 'enviroment_assemble:delete', '删除'), ('103', '环境组件', 'enviroment_assemble:search', '查询'), ('104', '调度', 'dispatch:list', '列表'), ('105', '调度', 'dispatch:detail', '详情'), ('106', '调度', 'dispatch:add', '增加'), ('107', '调度', 'dispatch:update', '更新'), ('108', '调度', 'dispatch:delete', '删除'), ('109', '调度', 'dispatch:search', '查询'), ('110', '性能报告', 'apiperformancereport:list', '列表'), ('111', '性能报告', 'apiperformancereport:detail', '列表'), ('112', '性能报告', 'apiperformancereport:add', '列表'), ('113', '性能报告', 'apiperformancereport:update', '列表'), ('114', '性能报告', 'apiperformancereport:delete', '列表'), ('115', '性能报告', 'apiperformancereport:search', '列表'), ('116', '性能报告', 'apiperformancestatistics:list', '列表'), ('117', '性能报告', 'apiperformancestatistics:detail', '详情'), ('118', '性能报告', 'apiperformancestatistics:add', '增加'), ('119', '性能报告', 'apiperformancestatistics:update', '更新'), ('120', '性能报告', 'apiperformancestatistics:delete', '删除'), ('121', '性能报告', 'apiperformancestatistics:search', '查询'), ('122', '功能报告统计', 'apireportstatics:list', '列表'), ('123', '功能报告统计', 'apireportstatics:search', '查询'), ('124', '执行计划批次', 'executeplanbatch:list', '列表'), ('125', '执行计划批次', 'executeplanbatch:detail', '详情'), ('126', '执行计划批次', 'executeplanbatch:add', '增加'), ('127', '执行计划批次', 'executeplanbatch:update', '更新'), ('128', '执行计划批次', 'executeplanbatch:delete', '删除'), ('129', '执行计划批次', 'executeplanbatch:search', '查询');
COMMIT;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', '超级管理员', '2019-07-01 00:00:00', '2019-07-01 00:00:00'), ('2', '普通用户', '2019-07-01 00:00:00', '2019-07-01 00:00:00'), ('3', '测试', '2019-07-01 00:00:00', '2019-07-01 00:00:00'), ('4', '测试1', null, null), ('5', '滚滚滚', null, '2020-04-22 14:57:16'), ('7', 'test', '2020-04-21 18:58:13', '2020-12-22 11:47:05'), ('8', '测试post', '2020-09-21 17:29:07', null), ('9', 'adadada', '2020-09-22 20:34:11', null), ('10', 'newtest', null, '2020-12-22 16:35:09');
COMMIT;

-- ----------------------------
--  Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色Id',
  `permission_id` bigint(20) unsigned NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permission_fk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

-- ----------------------------
--  Records of `role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES ('3', '1'), ('5', '1'), ('8', '1'), ('9', '1'), ('5', '2'), ('9', '2'), ('5', '3'), ('9', '3'), ('5', '4'), ('9', '4'), ('3', '5'), ('5', '5'), ('8', '5'), ('9', '5'), ('5', '6'), ('9', '6'), ('5', '7'), ('8', '7'), ('9', '7'), ('5', '8'), ('9', '8'), ('9', '9'), ('9', '10'), ('8', '11'), ('9', '11'), ('9', '12'), ('7', '19'), ('10', '19'), ('7', '20'), ('10', '20'), ('7', '21'), ('10', '21'), ('7', '22'), ('10', '22'), ('10', '23'), ('7', '24'), ('10', '24'), ('7', '25'), ('10', '25'), ('7', '26'), ('10', '26'), ('7', '27'), ('10', '27'), ('7', '28'), ('10', '28'), ('7', '29'), ('10', '29'), ('7', '30'), ('10', '30'), ('7', '31'), ('10', '31'), ('7', '32'), ('10', '32'), ('7', '33'), ('10', '33'), ('7', '34'), ('10', '34'), ('7', '35'), ('10', '35'), ('7', '36'), ('10', '36'), ('10', '37'), ('10', '38'), ('10', '39'), ('10', '40'), ('10', '41'), ('10', '42'), ('7', '43'), ('10', '43'), ('7', '44'), ('10', '44'), ('7', '45'), ('10', '45'), ('7', '46'), ('10', '46'), ('7', '47'), ('10', '47'), ('7', '48'), ('10', '48'), ('7', '49'), ('10', '49'), ('7', '50'), ('10', '50'), ('7', '51'), ('10', '51'), ('7', '52'), ('10', '52'), ('7', '53'), ('10', '53'), ('7', '54'), ('10', '54'), ('7', '55'), ('10', '55'), ('7', '56'), ('10', '56'), ('7', '57'), ('10', '57'), ('7', '58'), ('10', '58'), ('7', '59'), ('10', '59'), ('7', '60'), ('10', '60'), ('7', '61'), ('10', '61'), ('7', '62'), ('10', '62'), ('7', '63'), ('10', '63'), ('7', '64'), ('10', '64'), ('7', '65'), ('10', '65'), ('7', '66'), ('10', '66'), ('7', '67'), ('10', '67'), ('7', '68'), ('10', '68'), ('7', '69'), ('10', '69'), ('7', '70'), ('10', '70'), ('7', '71'), ('10', '71'), ('7', '72'), ('10', '72'), ('7', '73'), ('10', '73'), ('7', '74'), ('10', '74'), ('7', '75'), ('10', '75'), ('7', '76'), ('10', '76'), ('7', '77'), ('10', '77'), ('7', '78'), ('10', '78'), ('7', '79'), ('10', '79'), ('7', '80'), ('10', '80'), ('7', '81'), ('10', '81'), ('7', '82'), ('10', '82'), ('7', '83'), ('10', '83'), ('7', '84'), ('10', '84'), ('7', '85'), ('10', '85'), ('7', '86'), ('10', '86'), ('7', '87'), ('10', '87'), ('7', '88'), ('10', '88'), ('7', '89'), ('10', '89'), ('7', '90'), ('10', '90'), ('7', '91'), ('10', '91'), ('7', '92'), ('10', '92'), ('7', '93'), ('10', '93'), ('7', '94'), ('10', '94'), ('7', '95'), ('10', '95'), ('7', '96'), ('10', '96'), ('7', '97'), ('10', '97'), ('7', '98'), ('10', '98'), ('7', '99'), ('10', '99'), ('7', '100'), ('10', '100'), ('7', '101'), ('10', '101'), ('7', '102'), ('10', '102'), ('7', '103'), ('10', '103'), ('7', '104'), ('10', '104'), ('7', '105'), ('10', '105'), ('7', '106'), ('10', '106'), ('7', '107'), ('10', '107'), ('7', '108'), ('10', '108'), ('7', '109'), ('10', '109'), ('7', '110'), ('10', '110'), ('7', '111'), ('10', '111'), ('7', '112'), ('10', '112'), ('7', '113'), ('10', '113'), ('7', '114'), ('10', '114'), ('7', '115'), ('10', '115'), ('7', '116'), ('10', '116'), ('7', '117'), ('10', '117'), ('7', '118'), ('10', '118'), ('7', '119'), ('10', '119'), ('7', '120'), ('10', '120'), ('7', '121'), ('10', '121');
COMMIT;

-- ----------------------------
--  Table structure for `slaver`
-- ----------------------------
DROP TABLE IF EXISTS `slaver`;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='服务器表';

-- ----------------------------
--  Records of `slaver`
-- ----------------------------
BEGIN;
INSERT INTO `slaver` VALUES ('8', '功能测试机器1', '127.0.0.1', '8081', '', '功能', 'slaver', '2021-05-26 23:05:35', '2021-05-26 23:05:54');
COMMIT;

-- ----------------------------
--  Table structure for `statics_deployunitandcases`
-- ----------------------------
DROP TABLE IF EXISTS `statics_deployunitandcases`;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='api发布单元用例统计报告表';

-- ----------------------------
--  Records of `statics_deployunitandcases`
-- ----------------------------
BEGIN;
INSERT INTO `statics_deployunitandcases` VALUES ('1', '22', 'marketingservice', '0.00', '3', '0', '3', '127602', '2021-05-25 00:00:00', '2021-05-27 10:05:59', '2021-05-27 10:05:59');
COMMIT;

-- ----------------------------
--  Table structure for `statics_planandcases`
-- ----------------------------
DROP TABLE IF EXISTS `statics_planandcases`;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='api计划用例统计报告表';

-- ----------------------------
--  Records of `statics_planandcases`
-- ----------------------------
BEGIN;
INSERT INTO `statics_planandcases` VALUES ('1', '12', '兑换服务回归测试', '0.00', '3', '0', '3', '127602', '2021-05-25', '2021-05-27 10:05:59', '2021-05-27 10:05:59');
COMMIT;

-- ----------------------------
--  Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
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

-- ----------------------------
--  Records of `test`
-- ----------------------------
BEGIN;
INSERT INTO `test` VALUES ('1', 'admin11111@qq.com', 'admin', '$2a$10$wg0f10n.30UbU.9hPpucbef/ya62LdTKs72xJfjxvTFsL0Xaewbra', '2019-07-01 00:00:00', '2019-07-01 00:00:00'), ('2', 'editor@qq.com', 'editor', '$2a$10$/m4SgZ.ZFVZ7rcbQpJW2tezmuhf/UzQtpAtXb0WZiAE3TeHxq2DYu', '2019-07-02 00:00:00', '2019-07-02 00:00:00'), ('3', 'test@qq.com', 'test', '$2a$10$.0gBYBHAtdkxUUQNg3kII.fqGOngF4BLe8JavthZFalt2QIXHlrhm', '2019-07-03 00:00:00', '2019-07-03 00:00:00');
COMMIT;

-- ----------------------------
--  Table structure for `tester`
-- ----------------------------
DROP TABLE IF EXISTS `tester`;
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

-- ----------------------------
--  Records of `tester`
-- ----------------------------
BEGIN;
INSERT INTO `tester` VALUES ('1', '范伟杰', '初级测试工程师', '测试一组', '技术全面', '2019-07-01 00:00:00', '2020-04-22 21:04:52'), ('2', '孙亮亮', '初级测试工程师', '测试一组', '技术全面', '2019-07-01 00:00:00', '2020-04-22 22:04:19'), ('4', '紫燕', '测试攻城狮', '二组', '技术全面', '2020-04-22 16:36:02', '2020-04-22 16:36:02'), ('5', '孙亮', '初级测试工程师', '测试一组', '技术全面', '2020-04-22 21:20:22', '2020-04-22 21:04:41'), ('6', '许建峰', '初级测试工程师', '测试一组', '技术全面', '2020-04-22 21:25:22', '2020-04-22 21:25:22'), ('7', 'x', '初级测试工程师', '测试一组', 'x', '2020-04-22 22:31:43', '2020-04-22 22:04:07'), ('8', 'z', '初级测试工程师', '测试一组', 'z', '2020-04-22 22:34:19', '2020-04-22 22:34:19');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
