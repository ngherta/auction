# First stage: complete build environment
FROM maven:3.8.4-openjdk-11 AS builder

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean package -DskipTests

RUN ls -lah target 

# Second stage: minimal runtime environment
FROM openjdk:11-jre-slim

# copy jar from the first stage
COPY --from=builder target/auction-0.0.1-SNAPSHOT.jar /srv/app.jar

EXPOSE 8081

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/srv/app.jar"]



