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

ALTER TABLE testcenter.api_casedata MODIFY COLUMN apiparamvalue varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '用例参数值';

INSERT INTO `permission` VALUES (172,'数据库条件','dbcondition:list','列表')
,(173,'数据库条件','dbcondition:detail','详情')
,(174,'数据库条件','dbcondition:add','添加')
,(175,'数据库条件','dbcondition:update','更新')
,(176,'数据库条件','dbcondition:delete','删除')
,(177,'数据库条件','dbcondition:search','搜索');

ALTER  table `condition_api` add column subconditionname varchar(20) Comment '子条件名';
ALTER  table `condition_script` add column subconditionname varchar(20) Comment '子条件名';

ALTER  table `condition_db` add column assembleid bigint(20) unsigned Comment '组件id';
ALTER  table `condition_db` add column assemblename varchar(20) Comment '组件名';
ALTER  table `condition_db` add column conditionname varchar(20) Comment '条件名';
ALTER  table `condition_db` add column enviromentname varchar(20) Comment '环境名';
ALTER  table `condition_db` add column subconditionname varchar(20) Comment '子条件名';

ALTER TABLE testcenter.condition_db MODIFY COLUMN dbcontent varchar(200) NULL COMMENT 'db执行内容';
ALTER TABLE testcenter.condition_db MODIFY COLUMN connectstr varchar(200) NULL COMMENT 'db连接字';

ALTER  table `testcondition_report` add column subconditionname varchar(20) Comment '子条件名';





