FROM openjdk:21
COPY build/libs/PaymentService-0.0.1-SNAPSHOT.jar payment.jar
EXPOSE 8050
ENTRYPOINT ["java", "-jar", "payment.jar"]