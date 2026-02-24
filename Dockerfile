FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/update-0.0.1-SNAPSHOT.jar .

EXPOSE 8011

# Spring profiles
ENV SPRING_PROFILES_ACTIVE=production,global

# Disable Eureka registration/fetch
#ENV EUREKA_CLIENT_REGISTER_WITH_EUREKA=false
#ENV EUREKA_CLIENT_FETCH_REGISTRY=false


ENV MANAGEMENT_METRICS_ENABLE_ALL=false
ENV MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=health,info

CMD ["java","-Xms128m","-Xmx256m","-Dspring.main.allow-bean-definition-overriding=true","-jar","update-0.0.1-SNAPSHOT.jar"]
