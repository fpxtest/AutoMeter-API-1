DROP TABLE IF EXISTS `globalheader`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globalheader`
(
    `id`            bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `globalheadername`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '全局header名',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='全局Header';
 
 
 DROP TABLE IF EXISTS `globalheader_params`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globalheader_params`
(
    `id`            bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `globalheaderid` bigint(20) unsigned  NOT NULL  COMMENT 'globalheaderId',
    `keyname`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'key名',
    `keyvalue`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '值',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='全局Header参数';
 
 
 DROP TABLE IF EXISTS `globalheaderuse`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globalheaderuse`
(
    `id`            bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `globalheaderid` bigint(20) unsigned  NOT NULL  COMMENT 'globalheaderId',
    `executeplanid` bigint(20) unsigned  NOT NULL  COMMENT '集合Id',
    `globalheadername`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '全局header名',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='集合全局Header';
 
 
 
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(221, '全局Header', 'globalheader:search', '搜索');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(222, '全局Header', 'globalheader:add', '添加');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(223, '全局Header', 'globalheader:delete', '删除');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(224, '全局Header', 'globalheader:update', '更新');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(225, '全局Header', 'globalheader:detail', '修改');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(226, '全局Header', 'globalheader:list', '查询');
 
 

DROP TABLE IF EXISTS `globalvariables`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globalvariables`
(
    `id`            bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `keyname`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'key名',
    `keyvalue`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '值',
    `memo`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='全局变量';
 
 INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(227, '全局变量', 'globalvariables:search', '搜索');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(228, '全局变量', 'globalvariables:add', '添加');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(229, '全局变量', 'globalvariables:delete', '删除');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(230, '全局变量', 'globalvariables:update', '更新');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(231, '全局变量', 'globalvariables:detail', '修改');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(232, '全局变量', 'globalvariables:list', '查询');
 