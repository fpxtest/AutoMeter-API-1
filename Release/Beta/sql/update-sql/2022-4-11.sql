ALTER TABLE testcenter.dispatch ADD COLUMN creator varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建人';

ALTER TABLE testcenter.performancereportsource ADD COLUMN creator varchar(64) CHARACTER SET utf8 COLLATE utf8_bin COMMENT '创建人';
