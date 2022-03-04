ALTER TABLE testcenter.apicases drop COLUMN `level`;

ALTER TABLE testcenter.apicases ADD COLUMN `level` bigint(20) unsigned NOT NULL COMMENT '优先级';

INSERT INTO testcenter.variables (variablesname,variablestype,memo,create_time,lastmodify_time,variablecondition) VALUES
	 ('TestGuid','GUID','在使用的地方输入[TestGuid],运行时实际值例如：aaaaa-bbbbb-cccccc','2022-02-15 07:55:22','2022-02-15 07:55:22',''),
	 ('TestRadomString','随机字符串','在使用的地方输入[TestRadomString],变量条件为希望的长度 例如为3，运行时实际值例如：abc','2022-02-15 07:55:22','2022-02-23 09:18:18','3'),
	 ('TestRadomNumber','随机整数','在使用的地方输入[TestRadomNumber],变量条件为起始和结束的数字,运行时实际值例如：123','2022-02-15 07:55:22','2022-02-15 07:55:22','1,100'),
	 ('TestGetCurrentTime','当前时间','在使用的地方输入[TestGetCurrentTime],运行时实际值例如：2022-1-1 12:21:12','2022-02-17 08:01:28','2022-02-23 09:17:50',''),
	 ('TestGetRadmonIP','随机IP','在使用的地方输入[TestGetRadmonIP]，运行时实际值例如：192.168.2.2','2022-02-17 08:01:28','2022-02-17 08:01:28',''),
	 ('TestGetRadmomDouble','随机小数','在使用的地方输入[TestGetRadmomDouble],变量条件为起始和结束的数字，运行时实际值保留2位小数例如：123.45','2022-02-17 08:01:28','2022-02-23 09:18:08','200,300');

INSERT INTO testcenter.dictionary
(dicname, diccode, dicitemname, dicitmevalue, create_time, lastmodify_time)
VALUES('环境部署内容', 'machinedeploy', '数据库', 'pgsql', '2020-11-06 15:43:36', '2021-09-24 16:09:08');