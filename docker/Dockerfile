FROM eclipse-temurin:17-jdk

WORKDIR /app

# 빌드된 JAR을 복사
COPY build/libs/yabba-place-0.0.1-SNAPSHOT.jar app.jar

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
