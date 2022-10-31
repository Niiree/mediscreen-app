FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
CMD [ "spring-boot:run" ]
COPY microservice-patient mediscreen.jar
EXPOSE 9000
ENTRYPOINT exec java $JAVA_OPTS -jar comdockerdevenvironments.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar comdockerdevenvironments.jar
