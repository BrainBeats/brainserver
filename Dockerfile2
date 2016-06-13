FROM java:8
RUN mkdir -p /usr/src/app
COPY . /usr/src/app/
RUN chmod +x /usr/src/app/gradlew
RUN ./usr/src/app/gradlew build

EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/src/app/build/libs/gs-spring-boot-docker-0.1.0.jar"]
