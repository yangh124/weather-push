# 基础镜像 我的服务器是arm架构，使用houwm/jdk8:arm64，x86架构用java:8
FROM java:8

MAINTAINER yanghao<yh.124@qq.com>

# 参数
ARG JAR_FILE

# 环境变量
ENV TZ=Asia/Shanghai LANG=C.UTF-8
ENV PARAMS="--server.port=8080 --spring.profiles.active=prod -Xms512m -Xmx512m"

# 设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 复制jar包
COPY target/${JAR_FILE} /app.jar

# 暴露端口
EXPOSE 8080

# 工作目录
WORKDIR /

# 执行命令
ENTRYPOINT ["/bin/sh","-c","java -jar /app.jar ${PARAMS}"]