FROM openjdk:8
USER root
WORKDIR /home/slaverservice
COPY ./slaverservice.jar slaverservice.jar
COPY ./config/application.yml application.yml
COPY ./apache-jmeter-5.3 apache-jmeter-5.3
COPY ./servicejmxcase servicejmxcase
#在环境准备好后修改apache-jmeter-5.3/bin/jmeter的文件格式为unix
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone \
 && sed -i s@/deb.debian.org/@/mirrors.aliyun.com/@g /etc/apt/sources.list \
 && apt-get clean \
 && apt-get update -y \
 && apt-get install vim -y \
 && apt-get install tofrodos -y \
 && fromdos apache-jmeter-5.3/bin/jmeter
ENV JAVA_TOOL_OPTIONS="-Xms256m -Xmx256m -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8081
ENTRYPOINT java -jar $JAVA_TOOL_OPTIONS slaverservice.jar --spring.config.location=application.yml