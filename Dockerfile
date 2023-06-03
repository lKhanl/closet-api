FROM debian:stable-slim
RUN apt update && apt -y upgrade
RUN apt install -y openjdk-17-jdk maven
RUN apt clean
RUN rm -rf /var/lib/apt/lists/*

ARG BUILD_ENV=prod
ENV RUN_ENV ${BUILD_ENV}

WORKDIR /app
COPY . .

RUN mvn clean install -DskipTests

RUN ls ./target
#EXPOSE 4001
ENTRYPOINT ["java","-jar","./target/app.jar", "--spring.profiles.active=${RUN_ENV}"]