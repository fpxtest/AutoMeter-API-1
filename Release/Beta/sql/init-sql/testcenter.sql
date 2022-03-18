/*
 Navicat Premium Data Transfer

 Source Server         : aotu_meter
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : testcenter

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 17/03/2022 16:35:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮箱',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `register_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '上一次登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ix_account_name`(`name`) USING BTREE,
  UNIQUE INDEX `ix_account_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'admin@qq.com', 'admin', '$2a$10$wg0f10n.30UbU.9hPpucbef/ya62LdTKs72xJfjxvTFsL0Xaewbra', '2019-07-01 00:00:00', '2021-12-08 14:43:41');
INSERT INTO `account` VALUES (2, 'editor@qq.com', 'editor', '$2a$10$/m4SgZ.ZFVZ7rcbQpJW2tezmuhf/UzQtpAtXb0WZiAE3TeHxq2DYu', '2019-07-02 00:00:00', '2019-07-02 00:00:00');
INSERT INTO `account` VALUES (3, 'test@qq.com', 'test', '$2a$10$NGJEkH3bl7rwgk0ZYChT4.tWTm28jOY9GaeMfj2kYZ2qFB4Ed9sW2', '2019-07-03 00:00:00', '2021-04-23 15:14:05');

-- ----------------------------
-- Table structure for account_role
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role`  (
  `account_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户Id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色Id',
  PRIMARY KEY (`account_id`, `role_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `account_role_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `account_role_fk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_role
-- ----------------------------
INSERT INTO `account_role` VALUES (1, 1);
INSERT INTO `account_role` VALUES (2, 2);

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitid` bigint(20) UNSIGNED NOT NULL COMMENT 'DeployUnitId',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发布单元名',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '接口名',
  `visittype` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '访问方式，字典表获取',
  `apistyle` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'restful,普通方式',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'url访问路径',
  `requestcontenttype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求数据格式，form表单，json',
  `responecontenttype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '响应数据格式，form表单，json',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `requesttype` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'Body传值' COMMENT '请求传值方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '发布单元表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of api
-- ----------------------------
INSERT INTO `api` VALUES (12, 27, 'testcenterservice', '测试中心登陆', 'post', '普通方式', '/account/token', 'json', 'json', '登陆', '2021-07-15 16:42:08', '2021-07-15 16:42:08', 'admin', 'Body传值');
INSERT INTO `api` VALUES (13, 27, 'testcenterservice', '获取执行计划统计数据', 'get', '普通方式', '/executeplan/getstaticsplan', '/', 'json', '获取执行计划统计数据', '2021-07-15 16:48:54', '2021-12-07 19:10:12', 'admin', 'Body传值');
INSERT INTO `api` VALUES (20, 27, 'testcenterservice', '添加环境', 'post', '普通方式', '/enviroment', 'json', 'json', '', '2021-10-27 17:39:56', '2021-10-27 17:39:56', 'admin', 'Body传值');
INSERT INTO `api` VALUES (21, 27, 'testcenterservice', '测试没有参数api', 'get', '普通方式', '/test', '/', 'json', '', '2021-11-30 21:47:05', '2021-12-07 19:10:08', 'admin', 'Body传值');
INSERT INTO `api` VALUES (24, 27, 'testcenterservice', '添加环境复制', 'post', '普通方式', '/enviroment', 'json', 'json', '', '2021-10-27 17:39:56', '2021-10-27 17:39:56', 'admin', 'Body传值');
INSERT INTO `api` VALUES (26, 27, 'testcenterservice', '复制登陆', 'post', '普通方式', '/account/token', 'json', 'json', '登陆', '2021-12-07 20:23:12', '2021-12-07 20:23:12', 'admin', 'Body传值');

-- ----------------------------
-- Table structure for api_casedata
-- ----------------------------
DROP TABLE IF EXISTS `api_casedata`;
CREATE TABLE `api_casedata`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例名',
  `apiparam` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'api参数',
  `apiparamvalue` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '用例参数值',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'api属性类型，header，body',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `paramstype` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 215 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api用例数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of api_casedata
-- ----------------------------
INSERT INTO `api_casedata` VALUES (61, 43, '正确用户名密码登陆', 'name', 'admin', 'Params', 'memo', '2021-07-15 16:07:40', '2021-07-15 16:07:40', NULL);
INSERT INTO `api_casedata` VALUES (62, 43, '正确用户名密码登陆', 'password', 'admin123', 'Params', 'memo', '2021-07-15 16:07:40', '2021-07-15 16:07:40', NULL);
INSERT INTO `api_casedata` VALUES (68, 47, '获取执行计划统计数据列表', 'Authorization', '$token', 'Header', 'memo', '2021-10-10 17:10:04', '2021-10-10 17:10:04', NULL);
INSERT INTO `api_casedata` VALUES (178, 51, '新增测试环境', 'Authorization', '$token', 'Header', 'memo', '2021-10-27 20:10:39', '2021-10-27 20:10:39', NULL);
INSERT INTO `api_casedata` VALUES (200, 51, '新增测试环境', 'creator', '测试', 'Body', 'memo', '2021-11-28 09:11:30', '2021-11-28 09:11:30', NULL);
INSERT INTO `api_casedata` VALUES (201, 51, '新增测试环境', 'envtype', '功能', 'Body', 'memo', '2021-11-28 09:11:30', '2021-11-28 09:11:30', NULL);
INSERT INTO `api_casedata` VALUES (202, 51, '新增测试环境', 'memo', 'eb0f0f5c87b3ae54e8610caf4ad38cc3', 'Body', 'memo', '2021-11-28 09:11:30', '2021-11-28 09:11:30', NULL);
INSERT INTO `api_casedata` VALUES (203, 51, '新增测试环境', 'id', '', 'Body', 'memo', '2021-11-28 09:11:30', '2021-11-28 09:11:30', NULL);
INSERT INTO `api_casedata` VALUES (204, 51, '新增测试环境', 'enviromentname', 'test2', 'Body', 'memo', '2021-11-28 09:11:30', '2021-11-28 09:11:30', NULL);
INSERT INTO `api_casedata` VALUES (214, 44, '获取执行计划统计数据列表', 'Authorization', 'Bearer eyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0l71uFTEQhd9l6zQIAuR2vARt5PUaXUv7J9u7CkKUUENHS0VHScH7oPAYGM-MPfY4Veac0T3fuV4n9-bD4I9xuAxqWuw63AzqCNco3-xWK2_8W-WsGmfjL2qabqQ7mdkE010EZefOYrY-dGxvlNPXzuLYJxUJSuvtWEOqQTPCi0xIkglEAuNJUuhuITD-pLA0QlAcISQOFBDH8uJUlRJAlBjSlAWaAkHtyqnFF10ooBvUvd7WyQa7rRWU2Q2-2lRF2KaqxPymDNuwWqJQr0qvhMT3wB0knBnxUBVYNoiEBmFQFgYaDGDcu80tatXGmX1zIbPEgmE7u9xA7HIZsWG9xK5b0QcVYprVvlOTLbtVq32nLtt3KrNttzbbl-r1kYpzFIfXnJg4JnE2YCQ2q127-PL69oqrK-5tc2nFjRXXdRprBNcIqa2E4VYCcQNR3CKY2Y_VwtHSTJAsAYASwlFQMEoKtfo_Q7n3kFskRRcH4oqmxOLkUL-roK8YiSIHZg1lSWM8qhyOGqPNelq3LQY_KpjE-MpJAOYkBNMIYY7A3CvvzRI_qhpe8QWYr5oGZdVUKQvRqaxKuUXpq11zJ5KlSnGoATkEJl145BDmwegjmH1WcLm5JlBlAYlZgGIGsZglYWO-OsKU2JFfJeG3BUZ-tYSPVeIx8F81JhFfOQnMnIRkGmHMKZj8CJvn1zy86sk1j615Zm7Di5oGjMM5ZaU5BaUJU9KMEV47u4f671rrYbC0E6O1E641kdzaVGJWp3HAhpGQpIAECgAwUy4ojAsq_8qF-LGVcezDqucjs79KBXqrJzFYrbeinnGFbxtHVqG8bVQZVN42KhZ3Vt_wa4eFn-03-9rMqLP9Rl-bPfCp5sNIOti9CrSRPWAjy4DfawQbrPX316fH3z8ef35__PL5z9dv8d8h87APl2cvn7--u719dffi4z8AAAD__w.yM7VEtbXO80nSTUfZQaJBYxMpW2puRHtcvLBrlp8RnVv8OaAArnQ41c9nw5rBYvWIJTNTgSCtYHr6Ji_01MIpg', 'Header', 'memo', '2021-12-07 17:12:01', '2021-12-07 17:12:01', NULL);

-- ----------------------------
-- Table structure for api_params
-- ----------------------------
DROP TABLE IF EXISTS `api_params`;
CREATE TABLE `api_params`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `apiid` bigint(20) UNSIGNED NOT NULL COMMENT 'apiId',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'api名',
  `deployunitid` bigint(20) UNSIGNED NOT NULL COMMENT '发布单元Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发布单元名',
  `propertytype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'api属性类型，header，body',
  `keyname` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'key名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `keydefaultvalue` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Key默认值',
  `keytype` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api参数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of api_params
-- ----------------------------
INSERT INTO `api_params` VALUES (10, 12, '测试中心登陆', 27, 'testcenterservice', 'Params', 'name,password', '2021-07-15 16:42:46', '2021-12-07 19:43:34', 'admin', 'name,password', NULL);
INSERT INTO `api_params` VALUES (11, 13, '获取执行计划统计数据', 27, 'testcenterservice', 'Header', 'Authorization', '2021-07-15 16:49:34', '2021-07-15 16:49:34', 'admin', 'Authorization', NULL);
INSERT INTO `api_params` VALUES (26, 20, '添加环境', 27, 'testcenterservice', 'Body', 'creator,envtype,memo,id,enviromentname', '2021-10-27 17:10:30', '2021-12-07 20:06:30', 'admin', '{\n  \"id\": \"\",\n  \"enviromentname\": \"teste2\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}', NULL);
INSERT INTO `api_params` VALUES (27, 20, '添加环境', 27, 'testcenterservice', 'Header', 'Authorization', '2021-10-27 20:10:19', '2021-12-07 19:46:21', 'admin', 'Authorization', NULL);
INSERT INTO `api_params` VALUES (29, 24, '添加环境复制', 27, 'testcenterservice', 'Body', 'creator,envtype,memo,id,enviromentname', '2021-10-27 17:10:30', '2021-12-07 20:06:30', 'admin', '{\n  \"id\": \"\",\n  \"enviromentname\": \"teste2\",\n  \"envtype\": \"功能\",\n  \"memo\": \"\",\n  \"creator\": \"admin\"\n}', NULL);
INSERT INTO `api_params` VALUES (30, 24, '添加环境复制', 27, 'testcenterservice', 'Header', 'Authorization', '2021-10-27 20:10:19', '2021-12-07 19:46:21', 'admin', 'Authorization', NULL);
INSERT INTO `api_params` VALUES (32, 26, '复制登陆', 27, 'testcenterservice', 'Params', 'name,password', '2021-12-07 20:23:12', '2021-12-07 20:23:12', 'admin', 'name,password', NULL);

-- ----------------------------
-- Table structure for apicases
-- ----------------------------
DROP TABLE IF EXISTS `apicases`;
CREATE TABLE `apicases`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `apiid` bigint(20) UNSIGNED NOT NULL COMMENT 'apiid',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'API',
  `deployunitid` bigint(20) UNSIGNED NOT NULL COMMENT '发布单元id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发布单元',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例名',
  `casejmxname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例jmx名，和jmx文件名对齐',
  `casetype` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类型，功能，性能',
  `threadnum` bigint(20) UNSIGNED NOT NULL COMMENT '线程数',
  `loops` bigint(20) UNSIGNED NOT NULL COMMENT '循环数',
  `casecontent` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例内容，以英文逗号分开，提供jar获取自定义期望结果A：1的值，入参为冒号左边的内容',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '期望值',
  `middleparam` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '中间变量',
  `level` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '优先级',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api用例表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases
-- ----------------------------
INSERT INTO `apicases` VALUES (43, 12, '测试中心登陆', 27, 'testcenterservice', '正确用户名密码登陆', 'httpapi', '功能', 1, 1, '正确用户名密码登陆返回token', '$.code=200', '', '1', '正确用户名密码登陆返回token', '2021-07-15 16:44:22', '2021-12-08 14:43:29', 'admin');
INSERT INTO `apicases` VALUES (44, 13, '获取执行计划统计数据', 27, 'testcenterservice', '获取执行计划统计数据列表', 'httpapi', '功能', 1, 1, '获取执行计划统计数据列表', '$.code=200', '', '1', '兑换服务回归测试', '2021-07-15 16:51:32', '2021-10-10 18:14:48', 'admin');
INSERT INTO `apicases` VALUES (47, 13, '获取执行计划统计数据', 27, 'testcenterservice', '获取执行计划统计数据性能用例', 'httpapi', '性能', 2, 2, '获取执行计划统计数据列表', '$.code=200', '', '1', '兑换服务回归测试', '2021-07-15 16:51:32', '2021-10-10 18:14:35', 'admin');
INSERT INTO `apicases` VALUES (51, 20, '添加环境', 27, 'testcenterservice', '新增测试环境', 'httpapi', '功能', 1, 1, '新增测试环境', '$.code=200', '', '', '', '2021-10-27 17:41:28', '2021-10-27 17:41:28', 'admin');
INSERT INTO `apicases` VALUES (55, 21, '测试没有参数api', 27, 'testcenterservice', '测试无参数api用例', 'httpapi', '功能', 1, 1, '测试无参数api用例', '', '', '', '', '2021-11-30 21:47:50', '2021-11-30 21:47:50', 'admin');

-- ----------------------------
-- Table structure for apicases_assert
-- ----------------------------
DROP TABLE IF EXISTS `apicases_assert`;
CREATE TABLE `apicases_assert`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '断言Id',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例id',
  `asserttype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '断言类型',
  `assertsubtype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '断言子类型',
  `assertvalues` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '断言值',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `expression` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '断言表达式',
  `assertcondition` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '断言条件',
  `assertvaluetype` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '断言值类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '断言表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases_assert
-- ----------------------------
INSERT INTO `apicases_assert` VALUES (10, 50, 'Respone', 'Code', 'respone', '2021-11-01 15:12:23', '2021-11-01 15:12:23', 'admin', 'res', '<', NULL);
INSERT INTO `apicases_assert` VALUES (11, 50, 'Json', 'Code', 'jsonvalue111', '2021-11-01 15:12:52', '2021-11-01 15:12:52', 'admin', 'jsonnnn', '=', NULL);
INSERT INTO `apicases_assert` VALUES (19, 51, 'Json', '', '200', '2021-11-07 16:41:29', '2021-11-07 16:41:29', 'admin', '$.code', '=', 'int');
INSERT INTO `apicases_assert` VALUES (20, 47, 'Json', '', '200', '2021-11-07 17:29:28', '2021-11-07 17:29:28', 'admin', '$.code', '=', 'int');
INSERT INTO `apicases_assert` VALUES (21, 44, 'Respone', '文本', '兑换服务性能测试', '2021-11-10 13:50:26', '2021-11-10 21:41:00', 'admin', '', 'Contain', 'String');
INSERT INTO `apicases_assert` VALUES (22, 54, 'Json', '', '200', '2021-11-07 16:41:29', '2021-11-07 16:41:29', 'admin', '$.code', '=', 'int');

-- ----------------------------
-- Table structure for apicases_condition
-- ----------------------------
DROP TABLE IF EXISTS `apicases_condition`;
CREATE TABLE `apicases_condition`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `execplanid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '执行计划Id',
  `execplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '执行计划名',
  `target` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '条件目标',
  `caseid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用例id',
  `envassemid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '环境组件id',
  `casedeployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例所属发布单元',
  `caseapiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例所属api',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例名',
  `basetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '前置，后置',
  `conditionbasetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据库，接口',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据库：mysql，oracle，sqlserver，接口：http,https,dubbo',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '包含调用接口的发布单元',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '调用接口的api',
  `conditionvalue` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '条件值，如果是数据库为sql，如果是接口为用例名',
  `conditioncaseid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '条件值id，如果是数据库为空，如果是接口为用例id',
  `connectstr` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '连接字',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api用例条件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases_condition
-- ----------------------------

-- ----------------------------
-- Table structure for apicases_condition_report
-- ----------------------------
DROP TABLE IF EXISTS `apicases_condition_report`;
CREATE TABLE `apicases_condition_report`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `batchid` bigint(20) UNSIGNED NOT NULL COMMENT '批次id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) UNSIGNED NOT NULL COMMENT '执行机id',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '前置，后置',
  `casetype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '功能，性能',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `errorinfo` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '错误信息',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api用例前后置条件运行报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases_condition_report
-- ----------------------------

-- ----------------------------
-- Table structure for apicases_performanceapdex
-- ----------------------------
DROP TABLE IF EXISTS `apicases_performanceapdex`;
CREATE TABLE `apicases_performanceapdex`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) UNSIGNED NOT NULL COMMENT '执行机id',
  `apdex` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'apdex',
  `toleration` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'toleration',
  `frustration` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'frustration',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api用例性能apdex表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases_performanceapdex
-- ----------------------------

-- ----------------------------
-- Table structure for apicases_performancestatistics
-- ----------------------------
DROP TABLE IF EXISTS `apicases_performancestatistics`;
CREATE TABLE `apicases_performancestatistics`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '批次',
  `runtime` double(11, 2) NOT NULL COMMENT '运行时长,秒',
  `slaverid` bigint(20) UNSIGNED NOT NULL COMMENT '执行机id',
  `samples` bigint(20) UNSIGNED NOT NULL COMMENT '样本',
  `errorcount` bigint(20) UNSIGNED NOT NULL COMMENT '错误次数',
  `errorrate` double(11, 2) NOT NULL COMMENT '错误率',
  `average` double(11, 2) NOT NULL COMMENT '平均数',
  `min` double(11, 2) NOT NULL COMMENT '最小值',
  `max` double(11, 2) NOT NULL COMMENT '最大值',
  `median` double(11, 2) NOT NULL COMMENT '中间值',
  `nzpct` double(11, 2) NOT NULL COMMENT '90pct',
  `nfpct` double(11, 2) NOT NULL COMMENT '95pct',
  `nnpct` double(11, 2) NOT NULL COMMENT '99pct',
  `tps` double(11, 2) NOT NULL COMMENT 'tps',
  `receivekbsec` double(11, 2) NOT NULL COMMENT '每秒接受Kb数',
  `sendkbsec` double(11, 2) NOT NULL COMMENT '每秒发送Kb数',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api用例性能统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases_performancestatistics
-- ----------------------------

-- ----------------------------
-- Table structure for apicases_report
-- ----------------------------
DROP TABLE IF EXISTS `apicases_report`;
CREATE TABLE `apicases_report`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) UNSIGNED NOT NULL COMMENT '执行机id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `respone` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '返回结果',
  `assertvalue` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '断言详细经过',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `expect` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '期望值',
  `errorinfo` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '错误信息',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `requestheader` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求头数据',
  `requestdatas` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求数据',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `requestmethod` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api用例报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases_report
-- ----------------------------

-- ----------------------------
-- Table structure for apicases_report_performance
-- ----------------------------
DROP TABLE IF EXISTS `apicases_report_performance`;
CREATE TABLE `apicases_report_performance`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例id',
  `testplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) UNSIGNED NOT NULL COMMENT '执行机id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '运行结果，成功，失败，异常',
  `respone` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '返回结果',
  `assertvalue` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '断言详细经过',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `expect` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '期望值',
  `errorinfo` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '错误信息',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `requestheader` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求头数据',
  `requestdatas` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求数据',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `requestmethod` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api用例报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases_report_performance
-- ----------------------------

-- ----------------------------
-- Table structure for apicases_reportstatics
-- ----------------------------
DROP TABLE IF EXISTS `apicases_reportstatics`;
CREATE TABLE `apicases_reportstatics`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `deployunitid` bigint(20) UNSIGNED NOT NULL COMMENT '发单单元id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '批次',
  `slaverid` bigint(20) UNSIGNED NOT NULL COMMENT '执行机id',
  `totalcases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalpasscases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalfailcases` bigint(20) NOT NULL COMMENT '用例总数',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api计划批量用例统计报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases_reportstatics
-- ----------------------------

-- ----------------------------
-- Table structure for apicases_variables
-- ----------------------------
DROP TABLE IF EXISTS `apicases_variables`;
CREATE TABLE `apicases_variables`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例名',
  `variablesid` bigint(20) UNSIGNED NOT NULL COMMENT '变量Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '变量名',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `deployunitname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布单元',
  `apiname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api用例变量表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apicases_variables
-- ----------------------------
INSERT INTO `apicases_variables` VALUES (5, 43, '正确用户名密码登陆', 1, 'token', 'logintoken', '2021-10-10 17:50:22', '2021-10-10 17:50:22', 'admin', 'testcenterservice', '测试中心登陆');

-- ----------------------------
-- Table structure for condition_api
-- ----------------------------
DROP TABLE IF EXISTS `condition_api`;
CREATE TABLE `condition_api`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '条件id',
  `deployunitid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发布单元id',
  `caseid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '接口caseid',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `apiid` int(11) NOT NULL COMMENT 'apiid',
  `conditionname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条件名',
  `deployunitname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布单元名',
  `apiname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api名',
  `casename` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口名',
  `subconditionname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子条件名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '接口条件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of condition_api
-- ----------------------------
INSERT INTO `condition_api` VALUES (16, 1, 27, 43, '', '2021-10-10 17:38:39', '2021-12-08 14:32:10', 'admin', 12, '测试中心服务回归测试前置条件', 'testcenterservice', '测试中心登陆', '正确用户名密码登陆', '前置执行登陆获取token');

-- ----------------------------
-- Table structure for condition_db
-- ----------------------------
DROP TABLE IF EXISTS `condition_db`;
CREATE TABLE `condition_db`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '条件id',
  `enviromentid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '环境id',
  `dbtype` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据库类型',
  `dbcontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'db执行内容',
  `connectstr` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'db连接字',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `subconditionname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子条件名',
  `assembleid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '组件id',
  `assemblename` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名',
  `conditionname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条件名',
  `enviromentname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '环境名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'db条件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of condition_db
-- ----------------------------
INSERT INTO `condition_db` VALUES (8, 1, 5, 'Update', 'update condition_db set subconditionname = \'初始化sql语句\' where id=8\n\n', '', '', '2021-12-06 17:00:45', '2021-12-08 14:33:39', 'admin', '初始化sql操作', 1, '账户mysql数据库', '测试中心服务回归测试前置条件', '功能测试环境');
INSERT INTO `condition_db` VALUES (9, 3, 5, 'Update', 'update condition_db set subconditionname = \'初始化sql语句00\' where id=8\n', '', '', '2021-12-07 15:33:07', '2021-12-08 14:32:34', 'admin', '用例数据库条件测试', 1, '账户mysql数据库', '新增测试环境用例前置条件', '功能测试环境');

-- ----------------------------
-- Table structure for condition_order
-- ----------------------------
DROP TABLE IF EXISTS `condition_order`;
CREATE TABLE `condition_order`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '父条件id',
  `subconditionid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '子条件id',
  `conditionname` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '父条件名',
  `subconditiontype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '子条件类型',
  `subconditionname` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '子条件名',
  `orderstatus` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '条件状态',
  `conditionorder` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '条件顺序',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '条件顺序表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of condition_order
-- ----------------------------

-- ----------------------------
-- Table structure for condition_script
-- ----------------------------
DROP TABLE IF EXISTS `condition_script`;
CREATE TABLE `condition_script`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '条件id，只取类型为用例',
  `script` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '脚本',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `conditionname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条件名',
  `subconditionname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子条件名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '脚本条件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of condition_script
-- ----------------------------
INSERT INTO `condition_script` VALUES (9, 3, 'String name= AutoMeter.GetRequestValue(\"creator\",\"Body\")+AutoMeter.GetRequestValue(\"enviromentname\",\"Body\");\nString Result=MD5.encrypt(name);\nAutoMeter.SetRequestValue(\"memo\",\"Body\",Result);', '2021-11-20 19:47:00', '2021-12-08 14:36:22', 'admin', '新增测试环境用例前置条件', 'md5加密参数，修改Key值');

-- ----------------------------
-- Table structure for deployunit
-- ----------------------------
DROP TABLE IF EXISTS `deployunit`;
CREATE TABLE `deployunit`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发布单元名',
  `protocal` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '协议',
  `port` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '访问端口',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '发布单元表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of deployunit
-- ----------------------------
INSERT INTO `deployunit` VALUES (1, 'accountservice', 'https', '8080', '账务服务', '2019-07-01 00:00:00', '2021-04-26 19:04:33', 'admin');
INSERT INTO `deployunit` VALUES (19, 'cornerservice', 'https', '80', 'cornerservice', '2020-11-16 20:26:10', '2021-05-27 03:05:12', 'admin');
INSERT INTO `deployunit` VALUES (22, 'marketingservice', 'https', '80', '市场服务', '2020-12-07 16:05:33', '2021-05-27 03:05:35', 'admin');
INSERT INTO `deployunit` VALUES (27, 'testcenterservice', 'http', '8080', '测试中心服务', '2021-07-15 16:40:16', '2021-09-26 12:09:34', 'admin');

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `dicname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '字典类名',
  `diccode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典编码',
  `dicitemname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项名',
  `dicitmevalue` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项值',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES (1, '测试环境', 'testenviroment1', '功能测试环境1', 'te112', '2019-07-01 00:00:00', '2020-04-21 17:04:47');
INSERT INTO `dictionary` VALUES (2, '测试环境', 'testenviroment', '功能测试环境2', 'te2', '2019-07-01 00:00:00', '2021-10-10 10:51:08');
INSERT INTO `dictionary` VALUES (5, 'http请求方式', 'httpmethod', '查询', 'get', '2020-04-17 17:57:30', '2021-10-10 10:55:55');
INSERT INTO `dictionary` VALUES (6, 'http请求方式', 'httpmethod', '提交数据', 'post', '2020-04-17 17:59:02', '2020-11-15 15:11:35');
INSERT INTO `dictionary` VALUES (9, '测试环境', '测试环境', '测试环境', '测试环境', '2020-04-20 15:42:10', '2021-09-24 16:09:53');
INSERT INTO `dictionary` VALUES (17, '访问方式', 'httpvisittype', 'http访问方式', 'get', '2020-05-18 21:05:24', '2021-09-24 16:09:55');
INSERT INTO `dictionary` VALUES (18, '访问方式', 'httpvisittype', 'http访问方式', 'post', '2020-05-18 21:05:55', '2021-09-24 16:09:57');
INSERT INTO `dictionary` VALUES (19, '服务器作用', 'machineuse', '部署', '部署发布单元', '2020-09-09 12:26:21', '2020-09-09 12:09:53');
INSERT INTO `dictionary` VALUES (20, '服务器作用', 'machineuse', '执行测试', '功能测试用例执行机', '2020-09-09 12:27:40', '2020-09-09 12:09:26');
INSERT INTO `dictionary` VALUES (21, '服务器作用', 'machineuse', '执行测试', '性能测试执行机', '2020-09-09 12:29:25', '2021-09-24 16:09:01');
INSERT INTO `dictionary` VALUES (27, '数据库类型', 'DBType', '用例前后置数据库类型', 'Mysql', '2020-11-02 08:29:29', '2021-10-10 10:56:03');
INSERT INTO `dictionary` VALUES (28, '数据库类型', 'DBType', '用例前后置数据库类型', 'Oracle', '2020-11-02 08:30:29', '2021-09-24 16:09:01');
INSERT INTO `dictionary` VALUES (29, '数据库类型', 'DBType', '用例前后置数据库类型', 'Sqlserver', '2020-11-02 08:31:05', '2021-09-24 16:09:03');
INSERT INTO `dictionary` VALUES (34, '环境部署内容', 'machinedeploy', '数据库', 'mysql', '2020-11-06 15:43:18', '2021-09-24 16:09:06');
INSERT INTO `dictionary` VALUES (35, '环境部署内容', 'machinedeploy', '数据库', 'oracle', '2020-11-06 15:43:36', '2021-09-24 16:09:08');
INSERT INTO `dictionary` VALUES (39, 'api请求格式', 'requestcontentype', '请求数据格式', 'form表单', '2020-11-10 08:43:55', '2021-09-24 16:09:15');
INSERT INTO `dictionary` VALUES (40, 'api请求格式', 'requestcontentype', '请求数据格式', 'json', '2020-11-10 08:44:19', '2021-09-24 16:09:17');
INSERT INTO `dictionary` VALUES (41, 'api响应格式', 'responecontenttype', '响应数据格式', 'json', '2020-11-10 08:50:28', '2021-09-24 16:09:19');
INSERT INTO `dictionary` VALUES (42, 'api响应格式', 'responecontenttype', '响应数据格式', 'html', '2020-11-10 08:55:14', '2021-09-24 16:09:33');
INSERT INTO `dictionary` VALUES (43, 'http请求方式', 'httpmethod', '更新', 'put', '2020-11-15 15:41:55', '2021-09-24 16:09:35');
INSERT INTO `dictionary` VALUES (44, 'http请求方式', 'httpmethod', '删除', 'delete', '2020-11-15 15:42:14', '2021-09-24 16:09:37');
INSERT INTO `dictionary` VALUES (45, '访问方式', 'httpvisittype', '更新', 'put', '2020-11-15 15:47:03', '2021-09-24 16:09:39');
INSERT INTO `dictionary` VALUES (46, '访问方式', 'httpvisittype', '删除', 'delete', '2020-11-15 15:47:20', '2021-09-24 16:09:41');
INSERT INTO `dictionary` VALUES (47, '功能执行机最大并发数', 'FunctionJmeterProcess', '功能执行机并发Jmeter进程', '2', '2020-11-28 17:02:39', '2021-04-02 12:04:57');
INSERT INTO `dictionary` VALUES (52, '性能执行机Jmeter并发数', 'PerformanceJmeterProcess', '性能测试Jmeter并行数', '1', '2021-04-07 09:08:10', '2021-05-26 23:05:05');
INSERT INTO `dictionary` VALUES (53, '执行计划业务类型', 'planbusinesstype', '执行计划业务类型', '常规测试', '2021-04-20 17:24:17', '2021-09-24 16:09:08');
INSERT INTO `dictionary` VALUES (54, '执行计划业务类型', 'planbusinesstype', '执行计划业务类型', 'CI自动化测试', '2021-04-20 17:24:48', '2021-09-24 16:09:10');

-- ----------------------------
-- Table structure for dispatch
-- ----------------------------
DROP TABLE IF EXISTS `dispatch`;
CREATE TABLE `dispatch`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `execplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划Id',
  `execplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '执行计划名',
  `batchid` bigint(20) UNSIGNED NOT NULL COMMENT '批次Id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '批次名',
  `slaverid` bigint(20) UNSIGNED NOT NULL COMMENT '执行机Id',
  `slavername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '执行机名',
  `testcaseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例Id',
  `testcasename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例名',
  `casejmxname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'jmeter-class',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发布单元',
  `expect` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'jmeter-class',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '待分配，已分配，已结束，调度异常',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `plantype` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划类型',
  `threadnum` bigint(20) NULL DEFAULT NULL COMMENT '线程数',
  `loops` bigint(20) NULL DEFAULT NULL COMMENT '循环数',
  `memo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dispatch
-- ----------------------------

-- ----------------------------
-- Table structure for enviroment
-- ----------------------------
DROP TABLE IF EXISTS `enviroment`;
CREATE TABLE `enviroment`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '环境名',
  `envtype` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境类型',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '环境表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enviroment
-- ----------------------------
INSERT INTO `enviroment` VALUES (5, '功能测试环境', '功能', '测试部门功能测试环境', '2020-11-20 16:37:39', '2021-10-10 10:48:23', 'admin');
INSERT INTO `enviroment` VALUES (19, '性能测试环境', '性能', '测试部门性能测试环境', '2020-12-07 16:36:59', '2021-10-10 10:48:27', 'admin');

-- ----------------------------
-- Table structure for enviroment_assemble
-- ----------------------------
DROP TABLE IF EXISTS `enviroment_assemble`;
CREATE TABLE `enviroment_assemble`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `assemblename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '环境组件名',
  `assembletype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'mysql，oracle，redis',
  `connectstr` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '连接字',
  `memo` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '环境描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '环境组件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enviroment_assemble
-- ----------------------------
INSERT INTO `enviroment_assemble` VALUES (1, '账户mysql数据库', 'mysql', 'test,test,3306,testcenter', 'test', '2019-07-01 00:00:00', '2021-12-06 16:19:12', 'admin');

-- ----------------------------
-- Table structure for envmachine
-- ----------------------------
DROP TABLE IF EXISTS `envmachine`;
CREATE TABLE `envmachine`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `envid` bigint(20) UNSIGNED NOT NULL COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '环境名',
  `machineid` bigint(20) UNSIGNED NOT NULL COMMENT '服务器Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '机器名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '环境服务器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of envmachine
-- ----------------------------
INSERT INTO `envmachine` VALUES (20, 19, '性能测试环境', 7, '测试服务器', '2020-12-07 16:37:11', '2021-10-10 10:48:34', 'admin');
INSERT INTO `envmachine` VALUES (21, 5, '功能测试环境', 11, '4', '2021-04-23 19:38:48', '2021-04-23 19:38:48', NULL);
INSERT INTO `envmachine` VALUES (22, 19, '性能测试环境', 12, '6', '2021-04-26 19:24:51', '2021-04-26 19:24:51', 'admin');
INSERT INTO `envmachine` VALUES (23, 5, '功能测试环境', 7, '测试服务器', '2021-05-13 09:53:38', '2021-10-10 10:10:00', 'admin');
INSERT INTO `envmachine` VALUES (24, 5, '功能测试环境', 25, '1111', '2021-09-24 15:38:23', '2021-09-24 15:38:23', 'admin');
INSERT INTO `envmachine` VALUES (25, 19, '性能测试环境', 28, '2', '2021-10-10 10:13:10', '2021-10-10 10:13:10', 'admin');

-- ----------------------------
-- Table structure for executeplan
-- ----------------------------
DROP TABLE IF EXISTS `executeplan`;
CREATE TABLE `executeplan`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '执行计划Id',
  `envid` bigint(20) UNSIGNED NOT NULL COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '环境名',
  `executeplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '执行计划名',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态，new，waiting，running，pause，finish',
  `usetype` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '运行类型，function，performance，来区分分配什么slaver',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `businesstype` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务类型，常规测试，CI自动化测试',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `runmode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运行模式，单机运行，多机并发',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '执行计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of executeplan
-- ----------------------------
INSERT INTO `executeplan` VALUES (22, 5, '功能测试环境', '测试中心服务回归测试', 'new', '功能', '测试中心服务API回归测试', '2021-07-15 16:52:41', '2021-12-08 14:26:35', '常规测试', 'admin', '多机执行');
INSERT INTO `executeplan` VALUES (24, 19, '性能测试环境', '测试中心性能测试', 'new', '性能', '', '2021-10-10 18:15:24', '2021-10-10 18:15:24', '常规测试', 'admin', '多机并行');

-- ----------------------------
-- Table structure for executeplan_params
-- ----------------------------
DROP TABLE IF EXISTS `executeplan_params`;
CREATE TABLE `executeplan_params`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `executeplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `paramstype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '参数类型',
  `keyname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Key',
  `keyvalue` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '值',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试集合全局参数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of executeplan_params
-- ----------------------------

-- ----------------------------
-- Table structure for executeplan_testcase
-- ----------------------------
DROP TABLE IF EXISTS `executeplan_testcase`;
CREATE TABLE `executeplan_testcase`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `executeplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `apiid` bigint(20) UNSIGNED NOT NULL COMMENT 'apiid',
  `deployunitid` bigint(20) UNSIGNED NOT NULL COMMENT '发布单元id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '发布单元',
  `apiname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'API名',
  `testcaseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用例名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '执行计划用例表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of executeplan_testcase
-- ----------------------------

-- ----------------------------
-- Table structure for executeplanbatch
-- ----------------------------
DROP TABLE IF EXISTS `executeplanbatch`;
CREATE TABLE `executeplanbatch`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '执行计划Id',
  `executeplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '批次名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态，new，waiting，running，pause，finish',
  `source` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源，平台，ci,其他',
  `executeplanname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划名',
  `exectype` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行类型，立即，某天定时，每天定时',
  `execdate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '执行计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of executeplanbatch
-- ----------------------------

-- ----------------------------
-- Table structure for macdepunit
-- ----------------------------
DROP TABLE IF EXISTS `macdepunit`;
CREATE TABLE `macdepunit`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `depunitid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发布单元Id',
  `assembleid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '组件Id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发布单元名',
  `assembletype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组件类型',
  `machineid` bigint(20) UNSIGNED NOT NULL COMMENT '服务器Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '机器名',
  `envid` bigint(20) UNSIGNED NOT NULL COMMENT '环境Id',
  `enviromentname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '环境名',
  `visittype` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '访问方式，ip,域名',
  `domain` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '服务域名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '服务器发布单元表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of macdepunit
-- ----------------------------
INSERT INTO `macdepunit` VALUES (8, 1, NULL, 'accountservice', '发布单元', 7, '自己的测试服务器', 5, '功能测试环境', '域名', 'marketing-ui.confucius.mobi', '2020-11-16 10:16:33', '2021-04-26 19:04:35', 'admin');
INSERT INTO `macdepunit` VALUES (9, 19, NULL, 'cornerservice', '发布单元', 7, '测试服务器', 5, '功能测试环境', '域名', 'api.cdmtzz.com', '2020-11-16 20:54:52', '2021-04-02 19:04:37', NULL);
INSERT INTO `macdepunit` VALUES (11, 22, NULL, 'marketingservice', '发布单元', 7, '自己的测试服务器', 5, '功能测试环境', '域名', 'marketing-ui.confucius.mobi', '2020-12-07 16:10:06', '2021-04-02 19:04:35', NULL);
INSERT INTO `macdepunit` VALUES (12, 22, NULL, 'marketingservice', '发布单元', 7, '自己的测试服务器', 19, '性能测试环境', '域名', 'marketing-ui.confucius.mobi', '2020-12-07 16:37:58', '2021-04-02 19:04:48', NULL);
INSERT INTO `macdepunit` VALUES (21, 27, NULL, 'testcenterservice', '发布单元', 7, '测试服务器', 5, '功能测试环境', 'ip', '', '2021-07-15 16:52:15', '2021-12-06 10:41:23', 'admin');
INSERT INTO `macdepunit` VALUES (23, 27, NULL, 'testcenterservice', '发布单元', 7, '测试服务器', 19, '性能测试环境', 'ip', '', '2021-10-10 18:16:40', '2021-10-10 18:16:40', 'admin');
INSERT INTO `macdepunit` VALUES (24, NULL, 1, '账户mysql数据库', '组件', 7, '测试服务器', 5, '功能测试环境', 'ip', '', '2021-12-06 10:06:45', '2021-12-06 17:44:52', 'admin');

-- ----------------------------
-- Table structure for machine
-- ----------------------------
DROP TABLE IF EXISTS `machine`;
CREATE TABLE `machine`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `machinename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '机器名',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'ip',
  `cpu` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'cpu',
  `disk` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'disk',
  `mem` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '内存',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '服务器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of machine
-- ----------------------------
INSERT INTO `machine` VALUES (7, '测试服务器', '127.0.0.1', '4', '100G', '8G', '2020-10-17 17:53:43', '2021-10-10 10:48:15', 'admin');

-- ----------------------------
-- Table structure for performancereportsource
-- ----------------------------
DROP TABLE IF EXISTS `performancereportsource`;
CREATE TABLE `performancereportsource`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '环境Id',
  `planid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划Id',
  `batchid` bigint(20) UNSIGNED NOT NULL COMMENT '批次Id',
  `batchname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '批次',
  `slaverid` bigint(20) UNSIGNED NOT NULL COMMENT '执行机Id',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例Id',
  `testclass` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '解析文件匹配sample',
  `runtime` double(11, 2) NOT NULL COMMENT '运行时长',
  `source` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '解析文件目录',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '待解析，已解析',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '性能报告路径表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of performancereportsource
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '权限Id',
  `resource` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限对应的资源',
  `code` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限的代码/通配符,对应代码中@hasAuthority(xx)',
  `handle` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对应的资源操作',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 191 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '账号', 'account:list', '列表');
INSERT INTO `permission` VALUES (2, '账号', 'account:detail', '详情');
INSERT INTO `permission` VALUES (3, '账号', 'account:add', '添加');
INSERT INTO `permission` VALUES (4, '账号', 'account:update', '更新');
INSERT INTO `permission` VALUES (5, '账号', 'account:delete', '删除');
INSERT INTO `permission` VALUES (6, '账号', 'account:search', '搜索');
INSERT INTO `permission` VALUES (7, '角色', 'role:list', '列表');
INSERT INTO `permission` VALUES (8, '角色', 'role:detail', '详情');
INSERT INTO `permission` VALUES (9, '角色', 'role:add', '添加');
INSERT INTO `permission` VALUES (10, '角色', 'role:update', '更新');
INSERT INTO `permission` VALUES (11, '角色', 'role:delete', '删除');
INSERT INTO `permission` VALUES (12, '角色', 'role:search', '搜索');
INSERT INTO `permission` VALUES (13, '测试表', 'table:list', '列表');
INSERT INTO `permission` VALUES (14, '字典', 'dictionary:list', '列表');
INSERT INTO `permission` VALUES (15, '字典', 'dictionary:add', '添加');
INSERT INTO `permission` VALUES (16, '字典', 'dictionary:search', '搜索');
INSERT INTO `permission` VALUES (17, '字典', 'dictionary:update', '修改');
INSERT INTO `permission` VALUES (18, '字典', 'dictionary:delete', '删除');
INSERT INTO `permission` VALUES (19, '发布单元', 'depunit:list', '列表');
INSERT INTO `permission` VALUES (20, '发布单元', 'depunit:detail', '详情');
INSERT INTO `permission` VALUES (21, '发布单元', 'depunit:add', '添加');
INSERT INTO `permission` VALUES (22, '发布单元', 'depunit:update', '更新');
INSERT INTO `permission` VALUES (23, '发布单元', 'depunit:delete', '删除');
INSERT INTO `permission` VALUES (24, '发布单元', 'depunit:search', '搜索');
INSERT INTO `permission` VALUES (25, '测试环境', 'enviroment:list', '列表');
INSERT INTO `permission` VALUES (26, '测试环境', 'enviroment:detail', '详情');
INSERT INTO `permission` VALUES (27, '测试环境', 'enviroment:add', '添加');
INSERT INTO `permission` VALUES (28, '测试环境', 'enviroment:update', '更新');
INSERT INTO `permission` VALUES (29, '测试环境', 'enviroment:delete', '删除');
INSERT INTO `permission` VALUES (30, '测试环境', 'enviroment:search', '搜索');
INSERT INTO `permission` VALUES (31, '服务器', 'machine:list', '列表');
INSERT INTO `permission` VALUES (32, '服务器', 'machine:detail', '详情');
INSERT INTO `permission` VALUES (33, '服务器', 'machine:add', '添加');
INSERT INTO `permission` VALUES (34, '服务器', 'machine:update', '更新');
INSERT INTO `permission` VALUES (35, '服务器', 'machine:delete', '删除');
INSERT INTO `permission` VALUES (36, '服务器', 'machine:search', '搜索');
INSERT INTO `permission` VALUES (37, '测试人员', 'tester:list', '列表');
INSERT INTO `permission` VALUES (38, '测试人员', 'tester:detail', '详情');
INSERT INTO `permission` VALUES (39, '测试人员', 'tester:add', '添加');
INSERT INTO `permission` VALUES (40, '测试人员', 'tester:update', '更新');
INSERT INTO `permission` VALUES (41, '测试人员', 'tester:delete', '删除');
INSERT INTO `permission` VALUES (42, '测试人员', 'tester:search', '搜索');
INSERT INTO `permission` VALUES (43, 'api', 'api:list', '列表');
INSERT INTO `permission` VALUES (44, 'api', 'api:detail', '详情');
INSERT INTO `permission` VALUES (45, 'api', 'api:add', '添加');
INSERT INTO `permission` VALUES (46, 'api', 'api:update', '更新');
INSERT INTO `permission` VALUES (47, 'api', 'api:delete', '删除');
INSERT INTO `permission` VALUES (48, 'api', 'api:search', '搜索');
INSERT INTO `permission` VALUES (49, 'api参数', 'apiparams:list', '列表');
INSERT INTO `permission` VALUES (50, 'api参数', 'apiparams:detail', '详情');
INSERT INTO `permission` VALUES (51, 'api参数', 'apiparams:add', '添加');
INSERT INTO `permission` VALUES (52, 'api参数', 'apiparams:update', '更新');
INSERT INTO `permission` VALUES (53, 'api参数', 'apiparams:delete', '删除');
INSERT INTO `permission` VALUES (54, 'api参数', 'apiparams:search', '搜索');
INSERT INTO `permission` VALUES (55, '环境服务器', 'envmachine:list', '列表');
INSERT INTO `permission` VALUES (56, '环境服务器', 'envmachine:detail', '详情');
INSERT INTO `permission` VALUES (57, '环境服务器', 'envmachine:add', '添加');
INSERT INTO `permission` VALUES (58, '环境服务器', 'envmachine:update', '更新');
INSERT INTO `permission` VALUES (59, '环境服务器', 'envmachine:delete', '删除');
INSERT INTO `permission` VALUES (60, '环境服务器', 'envmachine:search', '搜索');
INSERT INTO `permission` VALUES (61, '服务器发布单元', 'macdepunit:list', '列表');
INSERT INTO `permission` VALUES (62, '服务器发布单元', 'macdepunit:detail', '详情');
INSERT INTO `permission` VALUES (63, '服务器发布单元', 'macdepunit:add', '添加');
INSERT INTO `permission` VALUES (64, '服务器发布单元', 'macdepunit:update', '更新');
INSERT INTO `permission` VALUES (65, '服务器发布单元', 'macdepunit:delete', '删除');
INSERT INTO `permission` VALUES (66, '服务器发布单元', 'macdepunit:search', '搜索');
INSERT INTO `permission` VALUES (67, 'API用例库', 'apicases:list', '列表');
INSERT INTO `permission` VALUES (68, 'API用例库', 'apicases:detail', '详情');
INSERT INTO `permission` VALUES (69, 'API用例库', 'apicases:add', '增加');
INSERT INTO `permission` VALUES (70, 'API用例库', 'apicases:update', '更新');
INSERT INTO `permission` VALUES (71, 'API用例库', 'apicases:delete', '删除');
INSERT INTO `permission` VALUES (72, 'API用例库', 'apicases:search', '查询');
INSERT INTO `permission` VALUES (73, '执行机', 'slaver:list', '列表');
INSERT INTO `permission` VALUES (74, '执行机', 'slaver:detail', '详情');
INSERT INTO `permission` VALUES (75, '执行机', 'slaver:add', '增加');
INSERT INTO `permission` VALUES (76, '执行机', 'slaver:update', '更新');
INSERT INTO `permission` VALUES (77, '执行机', 'slaver:delete', '删除');
INSERT INTO `permission` VALUES (78, '执行机', 'slaver:search', '查询');
INSERT INTO `permission` VALUES (79, '执行计划', 'executeplan:list', '列表');
INSERT INTO `permission` VALUES (80, '执行计划', 'executeplan:detail', '详情');
INSERT INTO `permission` VALUES (81, '执行计划', 'executeplan:add', '增加');
INSERT INTO `permission` VALUES (82, '执行计划', 'executeplan:update', '更新');
INSERT INTO `permission` VALUES (83, '执行计划', 'executeplan:delete', '删除');
INSERT INTO `permission` VALUES (84, '执行计划', 'executeplan:search', '查询');
INSERT INTO `permission` VALUES (85, 'api报告', 'apireport:list', '列表');
INSERT INTO `permission` VALUES (86, 'api报告', 'apireport:detail', '详情');
INSERT INTO `permission` VALUES (87, 'api报告', 'apireport:add', '增加');
INSERT INTO `permission` VALUES (88, 'api报告', 'apireport:update', '更新');
INSERT INTO `permission` VALUES (89, 'api报告', 'apireport:delete', '删除');
INSERT INTO `permission` VALUES (90, 'api报告', 'apireport:search', '查询');
INSERT INTO `permission` VALUES (91, 'API用例库', 'apicases:params', '参数');
INSERT INTO `permission` VALUES (92, '用例前后条件', 'apicases_condition:list', '列表');
INSERT INTO `permission` VALUES (93, '用例前后条件', 'apicases_condition:detail', '详情');
INSERT INTO `permission` VALUES (94, '用例前后条件', 'apicases_condition:add', '增加');
INSERT INTO `permission` VALUES (95, '用例前后条件', 'apicases_condition:update', '更新');
INSERT INTO `permission` VALUES (96, '用例前后条件', 'apicases_condition:delete', '删除');
INSERT INTO `permission` VALUES (97, '用例前后条件', 'apicases_condition:search', '查询');
INSERT INTO `permission` VALUES (98, '环境组件', 'enviroment_assemble:list', '列表');
INSERT INTO `permission` VALUES (99, '环境组件', 'enviroment_assemble:detail', '详情');
INSERT INTO `permission` VALUES (100, '环境组件', 'enviroment_assemble:add', '增加');
INSERT INTO `permission` VALUES (101, '环境组件', 'enviroment_assemble:update', '更新');
INSERT INTO `permission` VALUES (102, '环境组件', 'enviroment_assemble:delete', '删除');
INSERT INTO `permission` VALUES (103, '环境组件', 'enviroment_assemble:search', '查询');
INSERT INTO `permission` VALUES (104, '调度', 'dispatch:list', '列表');
INSERT INTO `permission` VALUES (105, '调度', 'dispatch:detail', '详情');
INSERT INTO `permission` VALUES (106, '调度', 'dispatch:add', '增加');
INSERT INTO `permission` VALUES (107, '调度', 'dispatch:update', '更新');
INSERT INTO `permission` VALUES (108, '调度', 'dispatch:delete', '删除');
INSERT INTO `permission` VALUES (109, '调度', 'dispatch:search', '查询');
INSERT INTO `permission` VALUES (110, '性能报告', 'apiperformancereport:list', '列表');
INSERT INTO `permission` VALUES (111, '性能报告', 'apiperformancereport:detail', '列表');
INSERT INTO `permission` VALUES (112, '性能报告', 'apiperformancereport:add', '列表');
INSERT INTO `permission` VALUES (113, '性能报告', 'apiperformancereport:update', '列表');
INSERT INTO `permission` VALUES (114, '性能报告', 'apiperformancereport:delete', '列表');
INSERT INTO `permission` VALUES (115, '性能报告', 'apiperformancereport:search', '列表');
INSERT INTO `permission` VALUES (116, '性能报告', 'apiperformancestatistics:list', '列表');
INSERT INTO `permission` VALUES (117, '性能报告', 'apiperformancestatistics:detail', '详情');
INSERT INTO `permission` VALUES (118, '性能报告', 'apiperformancestatistics:add', '增加');
INSERT INTO `permission` VALUES (119, '性能报告', 'apiperformancestatistics:update', '更新');
INSERT INTO `permission` VALUES (120, '性能报告', 'apiperformancestatistics:delete', '删除');
INSERT INTO `permission` VALUES (121, '性能报告', 'apiperformancestatistics:search', '查询');
INSERT INTO `permission` VALUES (122, '功能报告统计', 'apireportstatics:list', '列表');
INSERT INTO `permission` VALUES (123, '功能报告统计', 'apireportstatics:search', '查询');
INSERT INTO `permission` VALUES (124, '执行计划批次', 'executeplanbatch:list', '列表');
INSERT INTO `permission` VALUES (125, '执行计划批次', 'executeplanbatch:detail', '详情');
INSERT INTO `permission` VALUES (126, '执行计划批次', 'executeplanbatch:add', '增加');
INSERT INTO `permission` VALUES (127, '执行计划批次', 'executeplanbatch:update', '更新');
INSERT INTO `permission` VALUES (128, '执行计划批次', 'executeplanbatch:delete', '删除');
INSERT INTO `permission` VALUES (129, '执行计划批次', 'executeplanbatch:search', '查询');
INSERT INTO `permission` VALUES (130, '条件管理', 'condition:list', '列表');
INSERT INTO `permission` VALUES (131, '条件管理', 'condition:search', '查询');
INSERT INTO `permission` VALUES (132, '条件管理', 'condition:add', '增加');
INSERT INTO `permission` VALUES (133, '条件管理', 'condition:detail', '详情');
INSERT INTO `permission` VALUES (134, '条件管理', 'condition:update', '更新');
INSERT INTO `permission` VALUES (135, '条件管理', 'condition:delete', '删除');
INSERT INTO `permission` VALUES (136, 'api条件', 'apicondition:list', '列表');
INSERT INTO `permission` VALUES (137, 'api条件', 'apicondition:search', '查询');
INSERT INTO `permission` VALUES (138, 'api条件', 'apicondition:add', '增加');
INSERT INTO `permission` VALUES (139, 'api条件', 'apicondition:detail', '详情');
INSERT INTO `permission` VALUES (140, 'api条件', 'apicondition:update', '更新');
INSERT INTO `permission` VALUES (141, 'api条件', 'apicondition:delete', '删除');
INSERT INTO `permission` VALUES (142, '变量管理', 'testvariables:list', '列表');
INSERT INTO `permission` VALUES (143, '变量管理', 'testvariables:search', '查询');
INSERT INTO `permission` VALUES (144, '变量管理', 'testvariables:add', '增加');
INSERT INTO `permission` VALUES (145, '变量管理', 'testvariables:detail', '详情');
INSERT INTO `permission` VALUES (146, '变量管理', 'testvariables:update', '更新');
INSERT INTO `permission` VALUES (147, '变量管理', 'testvariables:delete', '删除');
INSERT INTO `permission` VALUES (148, '用例变量', 'ApicasesVariables:list', '列表');
INSERT INTO `permission` VALUES (149, '用例变量', 'ApicasesVariables:search', '查询');
INSERT INTO `permission` VALUES (150, '用例变量', 'ApicasesVariables:add', '增加');
INSERT INTO `permission` VALUES (151, '用例变量', 'ApicasesVariables:detail', '详情');
INSERT INTO `permission` VALUES (152, '用例变量', 'ApicasesVariables:update', '更新');
INSERT INTO `permission` VALUES (153, '用例变量', 'ApicasesVariables:delete', '删除');
INSERT INTO `permission` VALUES (154, '变量值', 'testvariablesvalue:list', '列表');
INSERT INTO `permission` VALUES (155, '变量值', 'testvariablesvalue:search', '查询');
INSERT INTO `permission` VALUES (156, '变量值', 'testvariablesvalue:add', '增加');
INSERT INTO `permission` VALUES (157, '变量值', 'testvariablesvalue:detail', '详情');
INSERT INTO `permission` VALUES (158, '变量值', 'testvariablesvalue:update', '更新');
INSERT INTO `permission` VALUES (159, '变量值', 'testvariablesvalue:delete', '删除');
INSERT INTO `permission` VALUES (160, '条件报告', 'testconditionreport:list', '列表');
INSERT INTO `permission` VALUES (161, '条件报告', 'testconditionreport:search', '查询');
INSERT INTO `permission` VALUES (162, '条件报告', 'testconditionreport:add', '增加');
INSERT INTO `permission` VALUES (163, '条件报告', 'testconditionreport:detail', '详情');
INSERT INTO `permission` VALUES (164, '条件报告', 'testconditionreport:update', '更新');
INSERT INTO `permission` VALUES (165, '条件报告', 'testconditionreport:delete', '删除');
INSERT INTO `permission` VALUES (166, '脚本条件', 'scriptcondition:list', '列表');
INSERT INTO `permission` VALUES (167, '脚本条件', 'scriptcondition:detail', '详情');
INSERT INTO `permission` VALUES (168, '脚本条件', 'scriptcondition:add', '添加');
INSERT INTO `permission` VALUES (169, '脚本条件', 'scriptcondition:update', '更新');
INSERT INTO `permission` VALUES (170, '脚本条件', 'scriptcondition:delete', '删除');
INSERT INTO `permission` VALUES (171, '脚本条件', 'scriptcondition:search', '搜索');
INSERT INTO `permission` VALUES (172, '数据库条件', 'dbcondition:list', '列表');
INSERT INTO `permission` VALUES (173, '数据库条件', 'dbcondition:detail', '详情');
INSERT INTO `permission` VALUES (174, '数据库条件', 'dbcondition:add', '添加');
INSERT INTO `permission` VALUES (175, '数据库条件', 'dbcondition:update', '更新');
INSERT INTO `permission` VALUES (176, '数据库条件', 'dbcondition:delete', '删除');
INSERT INTO `permission` VALUES (177, '数据库条件', 'dbcondition:search', '搜索');
INSERT INTO `permission` VALUES (178, '条件顺序', 'conditionorder:list', '列表');
INSERT INTO `permission` VALUES (179, '条件顺序', 'conditionorder:detail', '详情');
INSERT INTO `permission` VALUES (180, '条件顺序', 'conditionorder:add', '添加');
INSERT INTO `permission` VALUES (181, '条件顺序', 'conditionorder:moveup', '上移');
INSERT INTO `permission` VALUES (182, '条件顺序', 'conditionorder:movedown', '下移');
INSERT INTO `permission` VALUES (183, '条件顺序', 'conditionorder:search', '搜索');
INSERT INTO `permission` VALUES (185, '随机变量', 'variables:list', '列表');
INSERT INTO `permission` VALUES (186, '随机变量', 'variables:detail', '详情');
INSERT INTO `permission` VALUES (187, '随机变量', 'variables:update', '修改');
INSERT INTO `permission` VALUES (188, '随机变量', 'variables:delete', '删除');
INSERT INTO `permission` VALUES (189, '随机变量', 'variables:add', '添加');
INSERT INTO `permission` VALUES (190, '随机变量', 'variables:search', '搜索');

-- ----------------------------
-- Table structure for planbantchexeclog
-- ----------------------------
DROP TABLE IF EXISTS `planbantchexeclog`;
CREATE TABLE `planbantchexeclog`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `batchid` bigint(20) UNSIGNED NOT NULL COMMENT '批次Id',
  `exec_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行时间',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `memo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计划执行日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of planbantchexeclog
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '2019-07-01 00:00:00', '2019-07-01 00:00:00');
INSERT INTO `role` VALUES (2, '普通用户', '2019-07-01 00:00:00', '2019-07-01 00:00:00');
INSERT INTO `role` VALUES (3, '测试', '2019-07-01 00:00:00', '2019-07-01 00:00:00');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色Id',
  `permission_id` bigint(20) UNSIGNED NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `role_permission_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permission_fk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (3, 1);
INSERT INTO `role_permission` VALUES (3, 5);

-- ----------------------------
-- Table structure for slaver
-- ----------------------------
DROP TABLE IF EXISTS `slaver`;
CREATE TABLE `slaver`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '执行机Id',
  `slavername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '执行机器名',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'ip',
  `port` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '端口',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态，idle，running',
  `stype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '执行机类型，功能机，性能机',
  `memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `macaddress` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'mac地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '服务器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of slaver
-- ----------------------------
INSERT INTO `slaver` VALUES (19, '执行机172.25.0.4', '172.25.0.4', '8081', '空闲', '功能', '执行机172.25.0.4', '2022-03-17 16:35:07', '2022-03-17 16:35:07', '02-42-AC-19-00-04');

-- ----------------------------
-- Table structure for statics_deployunitandcases
-- ----------------------------
DROP TABLE IF EXISTS `statics_deployunitandcases`;
CREATE TABLE `statics_deployunitandcases`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `deployunitid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `deployunitname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '计划名',
  `passrate` double(11, 2) NOT NULL COMMENT '成功率',
  `totalcases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalpasscases` bigint(20) NOT NULL COMMENT '用例成功总数',
  `totalfailcases` bigint(20) NOT NULL COMMENT '用例失败总数',
  `runtime` bigint(50) NOT NULL COMMENT '运行时长',
  `statics_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '统计日期',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api发布单元用例统计报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of statics_deployunitandcases
-- ----------------------------
INSERT INTO `statics_deployunitandcases` VALUES (1, 22, 'marketingservice', 0.00, 3, 0, 3, 127602, '2021-05-25 00:00:00', '2021-05-27 10:05:59', '2021-05-27 10:05:59');
INSERT INTO `statics_deployunitandcases` VALUES (2, 27, 'testcenterservice', 20.00, 5, 1, 4, 972, '2021-07-21 00:00:00', '2021-07-22 03:07:44', '2021-07-22 03:07:44');
INSERT INTO `statics_deployunitandcases` VALUES (3, 27, 'testcenterservice', 0.00, 18, 0, 18, 2472, '2021-11-21 00:00:00', '2021-11-22 21:11:05', '2021-11-22 21:11:05');
INSERT INTO `statics_deployunitandcases` VALUES (4, 27, 'testcenterservice', 0.00, 8, 0, 8, 3701, '2021-12-07 00:00:00', '2021-12-08 14:12:46', '2021-12-08 14:12:46');

-- ----------------------------
-- Table structure for statics_planandcases
-- ----------------------------
DROP TABLE IF EXISTS `statics_planandcases`;
CREATE TABLE `statics_planandcases`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testplanid` bigint(20) UNSIGNED NOT NULL COMMENT '执行计划id',
  `testplanname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '计划名',
  `passrate` double(11, 2) NOT NULL COMMENT '成功率',
  `totalcases` bigint(20) NOT NULL COMMENT '用例总数',
  `totalpasscases` bigint(20) NOT NULL COMMENT '用例成功总数',
  `totalfailcases` bigint(20) NOT NULL COMMENT '用例失败总数',
  `runtime` bigint(50) NOT NULL COMMENT '运行时长',
  `statics_date` date NOT NULL COMMENT '统计日期',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'api计划用例统计报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of statics_planandcases
-- ----------------------------
INSERT INTO `statics_planandcases` VALUES (1, 12, '兑换服务回归测试', 0.00, 3, 0, 3, 127602, '2021-05-25', '2021-05-27 10:05:59', '2021-05-27 10:05:59');
INSERT INTO `statics_planandcases` VALUES (2, 22, '测试中心测试api', 20.00, 5, 1, 4, 972, '2021-07-21', '2021-07-22 03:07:44', '2021-07-22 03:07:44');
INSERT INTO `statics_planandcases` VALUES (3, 22, '测试中心测试api', 0.00, 18, 0, 18, 2472, '2021-11-21', '2021-11-22 21:11:05', '2021-11-22 21:11:05');
INSERT INTO `statics_planandcases` VALUES (4, 22, '测试中心测试api', 0.00, 8, 0, 8, 3701, '2021-12-07', '2021-12-08 14:12:46', '2021-12-08 14:12:46');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮箱',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `register_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '上一次登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ix_account_name`(`name`) USING BTREE,
  UNIQUE INDEX `ix_account_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (1, 'admin11111@qq.com', 'admin', '$2a$10$wg0f10n.30UbU.9hPpucbef/ya62LdTKs72xJfjxvTFsL0Xaewbra', '2019-07-01 00:00:00', '2019-07-01 00:00:00');
INSERT INTO `test` VALUES (2, 'editor@qq.com', 'editor', '$2a$10$/m4SgZ.ZFVZ7rcbQpJW2tezmuhf/UzQtpAtXb0WZiAE3TeHxq2DYu', '2019-07-02 00:00:00', '2019-07-02 00:00:00');
INSERT INTO `test` VALUES (3, 'test@qq.com', 'test', '$2a$10$.0gBYBHAtdkxUUQNg3kII.fqGOngF4BLe8JavthZFalt2QIXHlrhm', '2019-07-03 00:00:00', '2019-07-03 00:00:00');

-- ----------------------------
-- Table structure for testcondition
-- ----------------------------
DROP TABLE IF EXISTS `testcondition`;
CREATE TABLE `testcondition`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '条件名',
  `objectid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '目标Id，计划，用例的id',
  `objectname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '目标名',
  `objecttype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '目标类型',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '条件类型，前置，后置，其他',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '条件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testcondition
-- ----------------------------
INSERT INTO `testcondition` VALUES (1, '测试中心服务回归测试前置条件', 22, '测试中心服务回归测试', '执行计划', '前置条件', '', '2021-09-28 10:12:03', '2021-12-08 14:29:35', 'admin');
INSERT INTO `testcondition` VALUES (3, '新增测试环境用例前置条件', 51, '新增测试环境', '测试用例', '前置条件', '', '2021-11-16 16:17:03', '2021-12-08 14:30:23', 'admin');

-- ----------------------------
-- Table structure for testcondition_report
-- ----------------------------
DROP TABLE IF EXISTS `testcondition_report`;
CREATE TABLE `testcondition_report`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `conditionid` bigint(20) NULL DEFAULT NULL COMMENT '条件id',
  `conditiontype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '前置，后置',
  `subconditionid` bigint(20) NULL DEFAULT NULL COMMENT '子条件id，接口，db，nosql条件id',
  `conditionresult` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '接口返回，数据库返回结果等等',
  `conditionstatus` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '条件完成状态，成功，失败',
  `runtime` bigint(20) NOT NULL COMMENT '运行时长',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `batchname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次',
  `planname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计划名',
  `testplanid` bigint(20) NULL DEFAULT NULL COMMENT '计划id',
  `subconditiontype` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子条件类型，接口，数据库，脚本其他',
  `status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `subconditionname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子条件名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '条件报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testcondition_report
-- ----------------------------

-- ----------------------------
-- Table structure for tester
-- ----------------------------
DROP TABLE IF EXISTS `tester`;
CREATE TABLE `tester`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testername` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '测试人员姓名',
  `testertitle` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '测试人员职务',
  `testerorg` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属组织',
  `testermemo` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试人员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tester
-- ----------------------------
INSERT INTO `tester` VALUES (1, '范伟杰', '初级测试工程师', '测试一组', '技术全面', '2019-07-01 00:00:00', '2020-04-22 21:04:52');
INSERT INTO `tester` VALUES (2, '孙亮亮', '初级测试工程师', '测试一组', '技术全面', '2019-07-01 00:00:00', '2020-04-22 22:04:19');
INSERT INTO `tester` VALUES (4, '紫燕', '测试攻城狮', '二组', '技术全面', '2020-04-22 16:36:02', '2020-04-22 16:36:02');
INSERT INTO `tester` VALUES (5, '孙亮', '初级测试工程师', '测试一组', '技术全面', '2020-04-22 21:20:22', '2020-04-22 21:04:41');
INSERT INTO `tester` VALUES (6, '许建峰', '初级测试工程师', '测试一组', '技术全面', '2020-04-22 21:25:22', '2020-04-22 21:25:22');
INSERT INTO `tester` VALUES (7, 'x', '初级测试工程师', '测试一组', 'x', '2020-04-22 22:31:43', '2020-04-22 22:04:07');
INSERT INTO `tester` VALUES (8, 'z', '初级测试工程师', '测试一组', 'z', '2020-04-22 22:34:19', '2020-04-22 22:34:19');

-- ----------------------------
-- Table structure for testvariables
-- ----------------------------
DROP TABLE IF EXISTS `testvariables`;
CREATE TABLE `testvariables`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `testvariablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '变量名',
  `testvariablestype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '变量类型，用例变量，全局变量',
  `variablesexpress` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '变量表达',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `variablesdes` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变量描述',
  `valuetype` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'String' COMMENT 'String，Number，Array,Bool,其他',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '变量表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testvariables
-- ----------------------------
INSERT INTO `testvariables` VALUES (1, 'token', '用例变量', '$.data', '登陆返回token,其他接口访问需要使用', '2021-09-28 10:13:08', '2021-12-08 14:27:57', 'admin', '登陆返回token', 'String');

-- ----------------------------
-- Table structure for testvariables_value
-- ----------------------------
DROP TABLE IF EXISTS `testvariables_value`;
CREATE TABLE `testvariables_value`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `planid` bigint(20) UNSIGNED NOT NULL COMMENT '计划Id',
  `planname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '计划名',
  `caseid` bigint(20) UNSIGNED NOT NULL COMMENT '用例Id',
  `casename` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用例名',
  `variablesid` bigint(20) UNSIGNED NOT NULL COMMENT '变量Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '变量名',
  `variablesvalue` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '变量值',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `batchname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '变量值表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testvariables_value
-- ----------------------------

-- ----------------------------
-- Table structure for variables
-- ----------------------------
DROP TABLE IF EXISTS `variables`;
CREATE TABLE `variables`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `variablesname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '变量名',
  `variablestype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '变量类型',
  `memo` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '变量描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lastmodify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次修改时间',
  `variablecondition` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变量条件',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '变量表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of variables
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
