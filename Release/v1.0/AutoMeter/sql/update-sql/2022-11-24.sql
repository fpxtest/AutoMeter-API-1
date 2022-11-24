DROP TABLE IF EXISTS `deployunit_model`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deployunit_model`
(
    `id`            bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `deployunitid`  bigint(20) unsigned  NOT NULL  COMMENT '微服务id',
    `modelname`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL  COMMENT '模块',
    `memo`   varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL  COMMENT '描述',
    `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='微服务模块表';