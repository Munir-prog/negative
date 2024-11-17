FROM alpine:3 as base
RUN apk add git openjdk17
RUN git clone https://github.com/Munir-prog/negative.git && pwd
WORKDIR /negative
RUN ./mvnw package

FROM alpine:3 as result
RUN apk add openjdk17
WORKDIR /negative
COPY --from=base /negative/target/negative*.jar ./app.jar
# COPY application-dev.yaml .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
# CMD ["--spring.config.location=classpath:/application.yml,file:application-dev.yaml"]
