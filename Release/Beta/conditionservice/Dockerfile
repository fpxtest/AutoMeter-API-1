FROM openjdk:8
USER root
WORKDIR /home/conditionservice
COPY conditionservice.jar conditionservice.jar
COPY config/application.yml application.yml
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone \
 && sed -i s@/deb.debian.org/@/mirrors.aliyun.com/@g /etc/apt/sources.list \
 && apt-get clean \
 && apt-get update -y \
 && apt-get install vim -y
ENV JAVA_TOOL_OPTIONS="-Xms256m -Xmx256m -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8083
ENTRYPOINT java -jar $JAVA_TOOL_OPTIONS conditionservice.jar --spring.config.location=application.yml