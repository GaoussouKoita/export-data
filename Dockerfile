FROM maven:3.6.0-jdk-11-slim AS mvn
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim
LABEL author="Koita100185 <Gaoussou.Koita@orange-sontel.com>"
LABEL app=MY-TEST
LABEL version=1.0.0
WORKDIR  /usr/local/app/
COPY --from=mvn /home/app/target/*.jar  /usr/local/app/my-app.jar
COPY docker-run.sh ./run.sh
EXPOSE 8080
ENTRYPOINT ["sh","/usr/local/app/run.sh"]
