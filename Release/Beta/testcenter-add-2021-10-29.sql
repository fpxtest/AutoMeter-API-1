ALTER  table `api_params` add column keynamebak varchar(1000) Comment 'Key名冗余';

-- testcenter.apicases_assert definition
DROP TABLE IF EXISTS `apicases_assert`;

CREATE TABLE `apicases_assert` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '断言Id',
  `caseid` bigint(20) unsigned NOT NULL COMMENT '用例id',
  `asserttype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言类型',
  `assertsubtype` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言子类型',
  `assertvalues` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '断言值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastmodify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上一次修改时间',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `expression` varchar(20) DEFAULT NULL COMMENT '断言表达式',
  `assertcondition` varchar(20) DEFAULT NULL COMMENT '断言条件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='断言表';


ALTER  table `apicases_assert` add column assertvaluetype varchar(20) Comment '断言值类型';
