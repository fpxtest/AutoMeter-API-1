ALTER TABLE testcenter.api MODIFY COLUMN modelname varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '模块名';
ALTER TABLE testcenter.deployunit_model MODIFY COLUMN modelname varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '模块';
ALTER TABLE testcenter.apicases MODIFY COLUMN modelname varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '模块名';