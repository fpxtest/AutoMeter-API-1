DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project`
(
    `id`            bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '项目Id',
    `projectname`         varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '项目名',
    `status`          varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '项目状态',
    `start_time` datetime DEFAULT NOW() COMMENT '开始时间',
    `end_time` datetime DEFAULT NOW() COMMENT '结束时间',
    `memo`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '项目描述',
    `creator`          varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='项目迭代表';
  
 INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(197, '项目迭代', 'project:search', '搜索');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(198, '项目迭代', 'project:add', '添加');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(199, '项目迭代', 'project:delete', '删除');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(200, '项目迭代', 'project:update', '更新');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(201, '项目迭代', 'project:detail', '修改');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(202, '项目迭代', 'project:list', '查询');