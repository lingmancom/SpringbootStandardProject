# 该镜像需要依赖的基础镜像

FROM lingman/openjdk17

LABEL maintainer=jacky

COPY  target/springbootstandardproject-1.0.0.jar springbootstandardproject-app.jar

EXPOSE 8080

CMD ["java", "-jar", "springbootstandardproject-app.jar" , "--spring.profiles.active=prod"]


