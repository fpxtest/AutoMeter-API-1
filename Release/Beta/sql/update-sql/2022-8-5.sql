DROP TABLE IF EXISTS `mockmodel`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mockmodel`
(
    `id`            bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `modelcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '模块编码',
    `modelname`  varchar(64) CHARACTER  SET utf8 COLLATE utf8_bin COMMENT '模块名',
    `memo`  varchar(64) CHARACTER  SET utf8 COLLATE utf8_bin COMMENT '备注',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    `creator`    varchar(10) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='mock模块表';
 
 
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(233, 'mock模块', 'mockmodel:search', '搜索');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(234, 'mock模块', 'mockmodel:add', '添加');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(235, 'mock模块', 'mockmodel:delete', '删除');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(236, 'mock模块', 'mockmodel:update', '更新');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(237, 'mock模块', 'mockmodel:detail', '修改');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(238, 'mock模块', 'mockmodel:list', '查询');
 
 
 DROP TABLE IF EXISTS `mockapi`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mockapi`
(
    `id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `modelid`  bigint(20) unsigned NOT NULL  COMMENT '模块Id',
    `modelname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '模块名',
    `apiname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '接口',
    `apiurl`   varchar(200) CHARACTER  SET utf8 COLLATE utf8_bin COMMENT 'url',
    `apitype`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '接口类型',
    `requesttype`   varchar(20) CHARACTER  SET utf8 COLLATE utf8_bin COMMENT '请求类型',
    `timeout`  bigint(20) unsigned NOT NULL  COMMENT '超时',
    `memo`  varchar(64) CHARACTER  SET utf8 COLLATE utf8_bin COMMENT '备注',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    `creator`    varchar(10) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='mock接口表';
 
 INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(239, 'mock接口', 'mockapi:search', '搜索');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(240, 'mock接口', 'mockapi:add', '添加');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(241, 'mock接口', 'mockapi:delete', '删除');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(242, 'mock接口', 'mockapi:update', '更新');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(243, 'mock接口', 'mockapi:detail', '修改');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(244, 'mock接口', 'mockapi:list', '查询');
 
 
 
 DROP TABLE IF EXISTS `mockapirespone`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mockapirespone`
(
    `id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `apiid`  bigint(20) unsigned NOT NULL  COMMENT '接口Id',
    `responecode`  varchar(20) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '接口',
    `responecontent`  text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '响应内容',
    `status`  varchar(20) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '状态',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    `creator`    varchar(10) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='mock接口响应表';
 
  INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(245, 'mock接口响应', 'mockapirespone:search', '搜索');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(246, 'mock接口响应', 'mockapirespone:add', '添加');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(247, 'mock接口响应', 'mockapirespone:delete', '删除');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(248, 'mock接口响应', 'mockapirespone:update', '更新');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(249, 'mock接口响应', 'mockapirespone:detail', '修改');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(250, 'mock接口响应', 'mockapirespone:list', '查询');

