 ALTER TABLE testcenter.executeplan_params modify column keyvalue text  ;
 
ALTER TABLE testcenter.testcondition ADD COLUMN deployunitname varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '发布单元名';

ALTER TABLE testcenter.testcondition ADD COLUMN apiname varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'api名';

ALTER TABLE testcenter.testcondition ADD COLUMN `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid';

ALTER TABLE testcenter.testcondition ADD COLUMN `deployunitid` bigint(20) unsigned NOT NULL COMMENT 'deployunitid';


DROP TABLE IF EXISTS `scriptvariables`;

CREATE TABLE `scriptvariables`
(
    `id`            bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `scriptvariablesname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '变量名',
    `variablesdes` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '变量描述',
    `valuetype`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '变量值类型',
    `memo`          varchar(200) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '备注',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    `creator`    varchar(10) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='脚本变量表';
 
 
 INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(209, '脚本变量', 'scriptvariables:search', '搜索');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(210, '脚本变量', 'scriptvariables:add', '添加');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(211, '脚本变量', 'scriptvariables:delete', '删除');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(212, '脚本变量', 'scriptvariables:update', '更新');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(213, '脚本变量', 'scriptvariables:detail', '修改');
INSERT INTO testcenter.permission(id, resource, code, handle) VALUES(214, '脚本变量', 'scriptvariables:list', '查询');