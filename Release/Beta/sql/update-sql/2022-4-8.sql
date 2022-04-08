DROP TABLE IF EXISTS `apicases_debug_condition`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apicases_debug_condition`
(
    `id`            bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `deployunitid`    bigint(20) unsigned  COMMENT '发布单元Id',
    `deployunitname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '发布单元',
    `caseid`  bigint(20) unsigned  COMMENT '用例id',
    `casename`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '用例名',
    `conditionname`  varchar(500) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '条件名',
    `conditionid`  bigint(20) unsigned  COMMENT '条件id',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    `creator`    varchar(10) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='用例调试全局条件表';
