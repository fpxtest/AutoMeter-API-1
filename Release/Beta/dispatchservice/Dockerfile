FROM openjdk:8
USER root
WORKDIR /home/dispatchservice
COPY dispatchservice.jar dispatchservice.jar
COPY config/application.yml application.yml
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone \
 && sed -i s@/deb.debian.org/@/mirrors.aliyun.com/@g /etc/apt/sources.list \
 && apt-get clean \
 && apt-get update -y \
 && apt-get install vim -y
VOLUME ["/home/dispatchservice/log"]
ENV JAVA_TOOL_OPTIONS="-Xms256m -Xmx256m -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8082
ENTRYPOINT java -jar $JAVA_TOOL_OPTIONS dispatchservice.jar --spring.config.location=application.yml