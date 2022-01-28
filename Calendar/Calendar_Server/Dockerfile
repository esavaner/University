FROM maven:3.5.4
WORKDIR /usr/src/app
COPY . .
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/src/app/target/calendarserver-0.0.1-SNAPSHOT.jar"]
