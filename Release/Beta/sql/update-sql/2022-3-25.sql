DROP TABLE IF EXISTS `dbvariables`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dbvariables`
(
    `id`            bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `dbvariablesname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '变量名',
    `variablesdes` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '变量描述',
    `valuetype`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '变量值类型',
    `creator`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建人',
    `memo`          varchar(200) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '备注',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据库变量表';
  
 

DROP TABLE IF EXISTS `dbcondition_variables`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dbcondition_variables`
(
    `id`            bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `dbconditionid`  bigint(20) unsigned    NOT NULL  COMMENT '用例Id',
    `dbconditionname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例名',
    `variablesid`  bigint(20) unsigned    NOT NULL  COMMENT '变量Id',
    `variablesname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '变量名',
    `fieldname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '字段名',
    `roworder`  bigint(20) unsigned    NOT NULL  COMMENT '行序号',
    `memo`          varchar(200) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '备注',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据库条件变量表';

  ALTER TABLE testcenter.testvariables_value ADD COLUMN `variablestype` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '变量类型';



INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(191, '数据库变量', 'dbvariables:search', '搜索');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(192, '数据库变量', 'dbvariables:add', '添加');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(193, '数据库变量', 'dbvariables:delete', '删除');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(194, '数据库变量', 'dbvariables:update', '更新');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(195, '数据库变量', 'dbvariables:detail', '修改');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(196, '数据库变量', 'dbvariables:list', '查询');

