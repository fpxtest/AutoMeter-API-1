ALTER TABLE testcenter.condition_db MODIFY COLUMN conditionname varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '条件名';
ALTER TABLE testcenter.condition_db MODIFY COLUMN assemblename varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '组件名';
ALTER TABLE testcenter.condition_db MODIFY COLUMN enviromentname varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '环境名';
ALTER TABLE testcenter.condition_script MODIFY COLUMN conditionname varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '条件名';
ALTER TABLE testcenter.condition_order MODIFY COLUMN conditionname varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '父条件名';
ALTER TABLE testcenter.condition_api MODIFY COLUMN subconditionname varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '子条件名';
ALTER TABLE testcenter.condition_db MODIFY COLUMN subconditionname varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '子条件名';
ALTER TABLE testcenter.condition_order MODIFY COLUMN subconditionname varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '子条件名';
ALTER TABLE testcenter.condition_script MODIFY COLUMN subconditionname varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '子条件名';
ALTER TABLE testcenter.testvariables MODIFY COLUMN variablesexpress varchar(210) CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '变量表达';

