ALTER TABLE testcenter.account add COLUMN nickname varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '昵称';
 

DROP TABLE IF EXISTS `project_account`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_account`
(
    `id`            bigint(20) unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `projectid`  bigint(20) unsigned  NOT NULL  COMMENT '客户名',
    `accountid`  bigint(20) unsigned  NOT NULL  COMMENT '手机号',
    `projectname` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目名',
    `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
    `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='项目成员表';