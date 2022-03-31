DROP TABLE IF EXISTS `condition_delay`;
CREATE TABLE `condition_delay`
(
    `id`            bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `conditionid`    bigint(20) unsigned  COMMENT '父条件id',
    `conditionname`     varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '父条件名',
    `subconditionname`     varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '子条件名',
    `delaytime`    bigint(20) unsigned  COMMENT '延时时间',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    `creator`    varchar(10) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='延时子条件表';

