ALTER TABLE testcenter.apicases add COLUMN modelname varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模块名';

ALTER TABLE testcenter.apicases add COLUMN modelid bigint(20) unsigned DEFAULT 0 COMMENT '模块id';