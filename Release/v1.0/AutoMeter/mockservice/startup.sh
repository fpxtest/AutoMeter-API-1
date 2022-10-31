#!/bin/sh
echo "start.................."
nohup java -Xms256m -Xmx256m -Xmn128m -XX:ReservedCodeCacheSize=120M -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:+UseConcMarkSweepGC -XX:MaxTenuringThreshold=6 -XX:+ExplicitGCInvokesConcurrent -XX:-UseBiasedLocking -XX:-UseCounterDecay -XX:AutoBoxCacheMax=20000 -XX:+PerfDisableSharedMem -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -jar /app/AutoMeter/mockservice/mockservice.jar --spring.config.location=/app/AutoMeter/mockservice/config/application.yml &
echo "start mockservice ok..........."
