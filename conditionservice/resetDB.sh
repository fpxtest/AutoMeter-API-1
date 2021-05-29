#!/bin/bash
for i in $(find /Users/fanseasn/Work/QAP/spring-boot-vue-admin/api/src/test/resources/sql/dev/*.sql) ; do
  /usr/local/MySQL/bin/mysql -u root -p 123456 admin_test < ${i};
done
