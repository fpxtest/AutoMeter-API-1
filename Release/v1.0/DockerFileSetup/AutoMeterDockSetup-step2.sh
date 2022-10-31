#!/bin/sh

echo "AutoMeter开始构建镜像。。。。。。。。。。。。。"
cd ../AutoMeter/slaverservice
docker build -t slaverservice:1.0 .
cd ../conditionservice
docker build -t conditionservice:1.0 .
cd ../dispatchservice
docker build -t dispatchservice:1.0 .
cd ../mockservice
docker build -t mockservice:1.0 .
cd ../testcenterservice
docker build -t testcenterservice:1.0 .
cd ../testcenterapp
docker build -t testcenterapp:1.0 .

echo "AutoMeter开始启动。。。。。。。。。。。。。"
docker run -p 80:80 --name testcenterapp -d testcenterapp:1.0
docker run -p 8080:8080 --name testcenterservice -d testcenterservice:1.0
docker run -p 8083:8083 --name conditionservice -d conditionservice:1.0
docker run -p 8082:8082 --name dispatchservice -d dispatchservice:1.0
docker run -p 8081:8081 --name slaverservice -d slaverservice:1.0
docker run -p 8084:8084 --name mockservice -d mockservice:1.0

echo "AutoMeter-启动成功，请执行完/Beta/sql/update-sql目录下的增量sql后，请确保服务器的8080端口可以访问，访问入口 http://服务器ip  默认管理员账户密码为admin admin123"