#!/bin/sh
ip=`ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"`
echo $ip
sed -i ”“ "s@127.0.0.1@${ip}@" ../Beta/conditionservice/config/application.yml 
sed -i ”“ "s@127.0.0.1@${ip}@" ../Beta/dispatchservice/config/application.yml 
sed -i ”“ "s@127.0.0.1@${ip}@" ../Beta/slaverservice/config/application.yml 
sed -i ”“ "s@127.0.0.1@${ip}@" ../Beta/testcenterservice/config/application.yml 
sed -i ”“ "s@127.0.0.1@${ip}@" ../Beta/testcenterapp/dist/static/config.js
echo "修改IP成功"
docker-compose -f docker-compose.yaml up -d 
echo "AutoMeter部署成功，执行完增量sql后，访问入口 http://$ip:8084  默认账户密码admin admin123"