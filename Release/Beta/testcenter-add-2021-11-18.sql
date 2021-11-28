ALTER TABLE testcenter.testcondition_report MODIFY COLUMN conditiontype bigint(20) NULL COMMENT '前置，后置';
ALTER TABLE testcenter.testcondition_report MODIFY COLUMN subconditiontype bigint(20) NULL COMMENT '子条件类型，接口，数据库，脚本其他';

DROP TABLE IF EXISTS `condition_script`;
CREATE TABLE `condition_script`
(
    `id`            bigint(20) unsigned    NOT NULL AUTO_INCREMENT COMMENT 'Id',
    `conditionid`    bigint(20) unsigned  COMMENT '条件id，只取类型为用例',
    `conditionname`  varchar(50) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '条件名',
    `script`          varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '脚本',
    `create_time`   datetime DEFAULT NOW() COMMENT '创建时间',
    `lastmodify_time`    datetime DEFAULT NOW() COMMENT '上一次修改时间',
    `creator`    varchar(10) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='脚本条件表';



INSERT INTO `permission` VALUES (166,'脚本条件','scriptcondition:list','列表')
,(167,'脚本条件','scriptcondition:detail','详情')
,(168,'脚本条件','scriptcondition:add','添加')
,(169,'脚本条件','scriptcondition:update','更新')
,(170,'脚本条件','scriptcondition:delete','删除')
,(171,'脚本条件','scriptcondition:search','搜索');
