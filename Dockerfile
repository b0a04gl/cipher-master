
FROM maven:3.9.4-eclipse-temurin-17 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B package --file pom.xml -DskipTests


FROM  eclipse-temurin:17-jdk
COPY --from=build /workspace/target/*jar-with-dependencies.jar app.jar
EXPOSE 6911
ENTRYPOINT ["java","-jar","app.jar"]