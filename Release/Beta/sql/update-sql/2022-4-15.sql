DROP TABLE IF EXISTS `routeperformancereport`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `routeperformancereport`
(
    `id`            bigint(20) unsigned   NOT NULL AUTO_INCREMENT COMMENT '路由Id',
    `executeplanid`        bigint(20) unsigned  COMMENT '集合id',
    `tablename`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '表名',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='性能结果路由表';

  DROP TABLE IF EXISTS `performancereportfilelog`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `performancereportfilelog`
(
    `id`            bigint(20) unsigned   NOT NULL AUTO_INCREMENT COMMENT 'Id',
   `execplanid`     bigint(20) unsigned   NOT NULL  COMMENT 'execplanid',
   `batchid`     bigint(20) unsigned   NOT NULL  COMMENT 'batchid',
   `caseid`     bigint(20) unsigned   NOT NULL  COMMENT 'caseid',
   `slaverid`     bigint(20) unsigned   NOT NULL  COMMENT 'slaverid',
    `filename`          varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件名，planid+batchid+slaverid',
    `status`          varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态',
    `create_time` datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='性能报告文件记录表';

ALTER TABLE testcenter.performancereportsource ADD COLUMN totalcasenums bigint(20) unsigned   NOT NULL COMMENT '用例数';
ALTER TABLE testcenter.performancereportsource ADD COLUMN totalcasepassnums  bigint(20) unsigned   NOT NULL COMMENT '用例成功数';
ALTER TABLE testcenter.performancereportsource ADD COLUMN totalcasefailnums  bigint(20) unsigned   NOT NULL COMMENT '用例失败数';
 