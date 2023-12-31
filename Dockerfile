# 该镜像需要依赖的基础镜像

FROM lingman/openjdk17

LABEL maintainer=jacky

COPY  target/springbootstandardproject-1.0.0.jar springbootstandardproject-1.0.0.jar

EXPOSE 8080

CMD ["java", "-jar", "springbootstandardproject-1.0.0.jar" , "--spring.profiles.active=prod"]


