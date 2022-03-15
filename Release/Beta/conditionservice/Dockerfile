FROM openjdk:8
USER root
WORKDIR /home/conditionservice
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone && apt-get update -y && apt-get install vim -y
COPY ./conditionservice.jar conditionservice.jar
COPY ./config/application.yml application.yml
ENV JAVA_TOOL_OPTIONS="-Xms256m -Xmx256m -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8083
ENTRYPOINT java -jar $JAVA_TOOL_OPTIONS conditionservice.jar --spring.config.location=application.yml