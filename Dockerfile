FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD build/libs/gs-spring-boot-docker-0.1.0.jar app.jar
RUN sh -c 'touch /app.jar'
CMD ["java","-Djava.security.egd=filDe:/dev/./urandom","-jar","/app.jar"]