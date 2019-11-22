FROM gradle:5.4.1-jdk8-alpine AS build_stage

COPY --chown=gradle:gradle ./api-common /project/api-common
COPY --chown=gradle:gradle ./banking-api /project/banking-api

WORKDIR /project

COPY ./api-common/build.gradle ./api-common/settings.gradle ./api-common/
COPY ./banking-api/build.gradle ./banking-api/settings.gradle ./banking-api/

COPY ./api-common/src ./api-common/src/
COPY ./banking-api/src  ./banking-api/src

RUN gradle assemble -p=./api-common && cp ./api-common/build/libs/*.jar ./banking-api/build/libs
RUN gradle assemble -p=./banking-api

FROM openjdk:8-jre-alpine

WORKDIR /project

COPY --from=build_stage /project/banking-api/build/libs/banking-api-0.0.1.jar ./build/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/app.jar"]

