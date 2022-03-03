ALTER TABLE testcenter.apicases drop COLUMN `level`;

ALTER TABLE testcenter.apicases ADD COLUMN `level` bigint(20) unsigned NOT NULL COMMENT '优先级';
