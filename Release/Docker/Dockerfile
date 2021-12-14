#添加Java启动的必要镜像
FROM  java:8
#复制jar文件和配置文件所在的目录到容器里
ADD  testcenterservice.jar  /testcenterservice.jar
ADD  config       /config
#声明需要暴露的端口
EXPOSE  8080
#配置容器启动后执行的命令,并指定使用项目外部的配置文件
ENTRYPOINT  ["java", "-Xms256m", "-Xmx256m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/testcenterservice.jar", "--spring.config.location=/config/application.yml"]
