#!/bin/bash
if [ -z "$1" ];then
    echo "请输入加密口令！"
    exit 0
fi
# 设置环境变量
export ENCRYPT=$1
VERSION=`find ../target -name "*.jar" | awk -F- '{print $3 "-" $4}' | awk -F.jar '{print $1}'`
echo '当前build版本：'$VERSION
JAR_FILE_NAME=weather-push-$VERSION.jar
TAG=registry.cn-hangzhou.aliyuncs.com/yh123/weather-push:$VERSION
docker build -t $TAG --build-arg JAR_FILE=$JAR_FILE_NAME --build-arg ENCRYPT=$ENCRYPT ../
# 取消环境变量
unset ENCRYPT