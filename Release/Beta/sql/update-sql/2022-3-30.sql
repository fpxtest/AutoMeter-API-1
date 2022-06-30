
ALTER TABLE testcenter.apicases_variables ADD COLUMN `apiid` bigint(20) unsigned NOT NULL COMMENT 'apiid';

ALTER TABLE testcenter.apicases_variables ADD COLUMN `deployunitid` bigint(20) unsigned NOT NULL COMMENT 'deployunitid';

ALTER TABLE testcenter.dictionary MODIFY COLUMN dicitmevalue varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典项值';


