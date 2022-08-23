#!/bin/bash
# $1 新镜像id或名字
NAME=weather-push
if [ -z "$1" ];then
    echo "请输入新镜像id或名字！"
    exit 0
fi
old_container=`docker ps -q --filter "name=$NAME" --format "{{.ID}}" | awk '{print $1}'`
echo "old_container=" $old_container
old_image=`docker ps -q --filter "name=$NAME" --format "{{.Image}}" | awk '{print $1}'`
echo "old_image=" $old_image
docker stop $old_container
docker rm $old_container
docker rmi $old_image
new_container=`docker run -d -p 8080:8080 --name="$NAME" $1`
echo "new_container=" $new_container
