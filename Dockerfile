FROM maven:3-openjdk-11-slim as builder
WORKDIR /build/
COPY pom.xml .mvn/settings.xml /build/
RUN mvn -s settings.xml org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
COPY . /build/
RUN mvn -s settings.xml package -DskipTests=true
RUN java -Djarmode=layertools -jar /build/target/application.jar extract --destination /output/

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /output/dependencies/ /app/
COPY --from=builder /output/snapshot-dependencies/ /app/
COPY --from=builder /output/spring-boot-loader/ /app/
COPY --from=builder /output/application/ /app/
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
