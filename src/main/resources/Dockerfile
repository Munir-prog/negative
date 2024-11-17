FROM alpine:3 as base
RUN apk add git openjdk17
RUN git clone https://github.com/Munir-prog/spring.git
WORKDIR spring
RUN ./gradlew bootJar

FROM alpine:3 as result
RUN apk add openjdk17
WORKDIR /app
COPY --from=base /spring/build/libs/spring*.jar ./service.jar
COPY application-dev.yaml .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "service.jar"]
CMD ["--spring.config.location=classpath:/application.yml,file:application-dev.yaml"]
