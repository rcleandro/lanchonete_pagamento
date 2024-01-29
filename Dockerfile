FROM maven:3-openjdk-17

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package

RUN mvn dependency:go-offline

COPY . .

RUN mvn package -DskipTests

EXPOSE 8080

ENV MYSQL_HOST=postech
ENV MYSQL_ROOT_PASSWORD=postech
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=lanchonete
ENV MYSQL_USER=admin
ENV MYSQL_PASSWORD=lanchonete

ADD /target/lanchonete-0.0.1-SNAPSHOT.jar lanchonete-pagamentos.jar

ENTRYPOINT ["java", "-jar", "lanchonete.jar"]