FROM gradle:jdk17 as builder
WORKDIR /app
COPY . .
RUN gradle clean build

FROM findepi/graalvm:java17
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
COPY --from=builder /app/*.json /app/particle-firebase-admin-sdk.json
ENTRYPOINT java -jar app.jar
