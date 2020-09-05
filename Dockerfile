FROM openjdk:14-jdk as builder
MAINTAINER William Brawner <me@wbrawner.com>

RUN mkdir -p /home/gradle
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN /home/gradle/src/gradlew --console=plain --no-daemon server:bootJar

FROM openjdk:14-jdk-slim
EXPOSE 8080
COPY --from=builder /home/gradle/src/server/build/libs/flayre-server.jar flayre-server.jar
CMD /usr/local/openjdk-14/bin/java $JVM_ARGS -jar /flayre-server.jar
