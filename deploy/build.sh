#!/bin/bash
jar_file_name=`find ../target -name "*.jar" | awk -F/ '{print $3}'`
echo $jar_file_name
if [ -z "$1" ];then
    echo "请输入版本号！"
    exit 0
fi
docker build -t registry.cn-hangzhou.aliyuncs.com/yh123/weather-push:$1 --build-arg JAR_FILE=$jar_file_name ../