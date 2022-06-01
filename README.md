# 通过企业微信实现微信每日推送天气
## 一、企业微信配置
### 进入[企业微信官网](https://work.weixin.qq.com)，点击立即注册
![16361632863091](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/06/16361632863091.jpg)
![16361633233327](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/06/16361633233327.jpg)
### 登录企业微信管理后台，找到应用管理，点击创建自建应用（例如这里我创建了一个名称为天气推送的应用）
![16361635628391](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/06/16361635628391.jpg)
### 点击打开创建的应用，获取开发相关的配置信息（AgentId,Secret）
![16361637536484](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/06/16361637536484.jpg)
AgentId：这个应用的唯一标识
Secret：密钥（需要发送到企业微信app才能查看）
### 到此所有企业微信配置结束
## 二、天气开放平台配置
### 进入[和风天气开放平台](https://dev.qweather.com/)，点击注册
***注册完成后建议申请认证个人开发者***（支持更多接口）
![16361640883550](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/06/16361640883550.jpg)
### 登录控制台，点击应用管理，创建应用，获取到应用的KEY
![16361644923902](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/06/16361644923902.jpg)
## 三、项目开发
### 项目配置
![16361649123436](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/06/16361649123436.jpg)
#### pom引入依赖
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.6</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.yh</groupId>
    <artifactId>weather-push</artifactId>
    <version>0.0.2-SNAPSHOT</version>
    <name>weather-push</name>
    <description>weather-push</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.78</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                    <finalName>${project.name}</finalName>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```
#### application.yml配置
```
server:
  port: 6666
spring:
  application:
    name: weather-push

#企业微信
qywx:
  corpid: #我的企业-企业信息-企业ID
  corpsecret: #应用管理-你的自建应用-Secret
  agentid: #应用管理-你的自建应用-AgentId
  token-url: https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=${qywx.corpid}&corpsecret=${qywx.corpsecret}
  send-url: https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN
  label-url: https://qyapi.weixin.qq.com/cgi-bin/tag/list?access_token=ACCESS_TOKEN

#和风天气
hfweather: 
  key: #创建的应用的KEY
  #实时天气
  get-url: https://devapi.qweather.com/v7/weather/now?location=code&key=${hfweather.key}
  #城市信息
  city-url: https://geoapi.qweather.com/v2/city/lookup?location=cityName&key=${hfweather.key}&number=1
  #天气插件，和风天气插件
  plugin-url: https://widget-page.qweather.net/h5/index.html?md=0531246&bg=1&lc=accu&key=xxxxxxxxxx&v=xxxxxx
  #24h天气
  hour-url: https://devapi.qweather.com/v7/weather/24h?location=code&key=${hfweather.key}
  # 穿衣指数  type=3
  index-url: https://devapi.qweather.com/v7/indices/1d?type=3&location=code&key=${hfweather.key}
  # 天气预警
  warn-url: https://devapi.qweather.com/v7/warning/now?location=code&key=${hfweather.key}
  # 3天天气
  day-url: https://devapi.qweather.com/v7/weather/3d?location=code&key=${hfweather.key}

```
### 代码实现
#### 和风天气API接入(WeatherService)
```
/**
     * 获取今日天气
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> getTodayWeather(List<TagLocation> tags);

    /**
     *  获取明日天气
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> getTomWeather(List<TagLocation> tags);


    /**
     * 通过地名查询 location  code
     *
     * @param cityList
     * @return {椒江=101210101, 嘉定=101210101, 杭州=101210101, 开化=101210101}
     */
    Map<Integer, String> getLocations(List<Tag> cityList);


    /**
     *  获取天气灾害预警
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> getWeatherWarn(List<TagLocation> tags);
```
#### 企业微信API接入(PushService)
```
    /**
     * 发送文本消息
     */
    void pushWeatherMsg(String token, Map<Integer, String> weatherMap);

    /**
     * 获取用户标签
     * @param token
     * @return
     */
    List<Tag> getTags(String token);

    /**
     * 获取token
     * @return
     */
    String getToken();
```
### 使用Spring Task实现定时推送消息
```
    /**
     * 今日天气
     * 每天08:05推送
     */
    @Scheduled(cron = "0 5 8 * * ?")
    public void scheduledTask2() {
        String token = pushService.getToken();
        List<TagLocation> list = jsonConfig.getList();
        Map<Integer, String> map = weatherService.getTodayWeather(list);
        pushService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format + " -> 天气推送成功");
    }

    /**
     * 明日天气
     * 每天20:30推送
     */
    @Scheduled(cron = "0 30 20 * * ?")
    public void scheduledTask3() {
        String token = pushService.getToken();
        List<TagLocation> list = jsonConfig.getList();
        Map<Integer, String> map = weatherService.getTomWeather(list);
        pushService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format + " -> 天气推送成功");
    }

    /**
     * 天气灾害预警
     * 每小时获取天气灾害预警信息，有预警信息则推送至微信
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void scheduledTask4() {
        List<TagLocation> list = jsonConfig.getList();
        Map<Integer, String> map = weatherService.getWeatherWarn(list);
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        String token = pushService.getToken();
        pushService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format + " -> 天气灾害预警成功");
    }
```
## 四、最终效果
![IMG_2B45C9D58196-1](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/09/img2b45c9d581961.jpeg)

前端项目地址：[weather-push-admin](https://github.com/yangh124/weather-push-admin)

