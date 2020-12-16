FROM maven:3.6.3-openjdk-11-slim as builder

WORKDIR /usr/app/

ADD pom.xml /usr/app/
ADD src/ /usr/app/src/

RUN mvn install -Dmaven.test.skip=true

FROM openjdk:11-jre-slim as final
EXPOSE 8052
COPY --from=builder /usr/app/target/repairservice-0.0.1-SNAPSHOT.jar /app/main.jar

ENTRYPOINT [ "java", "-jar", "/app/main.jar" ]