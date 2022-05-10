DROP TABLE IF EXISTS `process_testcase`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_testcase`
(
    `id`            bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `executeplanid`  bigint(20) unsigned  NOT NULL COMMENT '执行计划id',
    `apiid`  bigint(20) unsigned  NOT NULL COMMENT 'apiid',
    `deployunitid`  bigint(20) unsigned  NOT NULL COMMENT '发布单元id',
    `deployunitname`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '发布单元',
    `apiname`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'API名',
    `testcaseid`       bigint(20) unsigned  NOT NULL COMMENT '用例id',
    `casename`      varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用例名',
    `ordernum`  bigint(20) unsigned  NOT NULL COMMENT '顺序',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='流程用例表';
 
 
 
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(215, '流程用例', 'processtestcase:search', '搜索');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(216, '流程用例', 'processtestcase:add', '添加');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(217, '流程用例', 'processtestcase:delete', '删除');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(218, '流程用例', 'processtestcase:update', '更新');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(219, '流程用例', 'processtestcase:detail', '修改');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(220, '流程用例', 'processtestcase:list', '查询');

 ALTER TABLE testcenter.executeplan_testcase ADD COLUMN caseorder bigint(20) unsigned COMMENT '用例顺序';
