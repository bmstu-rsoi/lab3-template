cd CarsService && ./gradlew build --exclude-task test
cd ..
cd PaymentService && ./gradlew build --exclude-task test
cd ..
cd RentalService && ./gradlew build --exclude-task test
cd ..
cd GatewayService && mvn -B clean package -DskipTests