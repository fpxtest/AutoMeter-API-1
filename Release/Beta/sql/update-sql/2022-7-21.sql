ALTER TABLE testcenter.globalheader_params MODIFY COLUMN keyvalue varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '值';
ALTER TABLE testcenter.api_casedata MODIFY COLUMN apiparam varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'api参数';
