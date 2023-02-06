#use Gradle image for the build process
FROM gradle:7.4.1-jdk17-alpine as GRADLE_BUILD

#create and use gradle user
USER gradle:gradle

#copy the source to the container
COPY --chown=gradle:gradle ./ ./bidding-service

WORKDIR ./bidding-service


#build
RUN gradle clean build --no-daemon

#use Alpine JRE for the final image
FROM openjdk:17-slim

RUN adduser --system --group docker
USER docker:docker

COPY --from=GRADLE_BUILD /home/gradle/bidding-service/build/libs/bidding-service-0.0.1-SNAPSHOT.jar /bidding-service.jar

ENTRYPOINT ["java","-jar","/bidding-service.jar"]