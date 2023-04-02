## 项目介绍
通过企业微信（微信插件）实现微信每日天气定时推送

## 包结构
```
.
├── deploy                                  # 部署脚本
├── src         
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── yh
│   │   │           └── weatherpush
│   │   │               ├── component       # 登陆授权相关（JWT）
│   │   │               ├── config
│   │   │               ├── controller
│   │   │               ├── dto
│   │   │               ├── entity
│   │   │               ├── enums
│   │   │               ├── exception
│   │   │               ├── manager         # 通用业务层（第三方api封装）
│   │   │               ├── mapper
│   │   │               ├── quartz          # 定时job
│   │   │               └── service
│   │   └── resources
│   │       ├── lua                         # lua脚本
│   │       ├── mapper
│   │       ├── script                      # Python脚本
│   │       └── sql                         # init sql
│   └── test
│       └── java
│           └── com
│               └── yh
│                   └── weatherpush
│                       └── generator       # mybatis-plus generator
.
```

## 技术选型

| 技术                 | 说明                | 官网                                           |
| -------------------- | ------------------- | ---------------------------------------------- |
| SpringBoot           | Web应用开发框架      | https://spring.io/projects/spring-boot            |
| SpringSecurity       | 认证授权        | https://spring.io/projects/spring-security               |
| MyBatis-Plus         | ORM框架             | http://www.mybatis.org/mybatis-3/zh/index.html|
| Mysql                | 关系型数据库         | https://www.mysql.com/cn/                                                         |
| Redis                | 内存数据存储         | https://redis.io/             |
| Nginx                | 静态资源服务器      | https://www.nginx.com/                         |
| Docker               | 应用容器引擎        | https://www.docker.com                         
| JWT                  | JWT登录支持         | https://github.com/jwtk/jjwt         |
| Swagger-UI           | API文档生成工具      | https://github.com/swagger-api/swagger-ui      |
| Redisson             | redis客户端        | https://github.com/redisson/redisson|
| Quartz             | 任务调度框架       | http://www.quartz-scheduler.org/|

## 数据库表结构

![表结构设计](http://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2023/04/02/16804065842905.jpg)

**待续...**
