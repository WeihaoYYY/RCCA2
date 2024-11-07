# # 基础镜像
# FROM openjdk:17-jdk-slim

# MAINTAINER "Weihao Yue <yue032994@outlook.com>"
# VOLUME /tmp
# # 将jar包添加到容器中并更名为app.jar
# ADD RCCA2-0.0.1-SNAPSHOT.jar r2.jar
# EXPOSE 8080
# # 入口
# ENTRYPOINT ["java", "-jar", "/r2.jar"]


# Stage 1: Build the JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/RCCA2-0.0.1-SNAPSHOT.jar r2.jar
EXPOSE 8080
CMD ["java", "-jar", "r2.jar"]
