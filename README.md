## 项目介绍

通过企业微信（微信插件）实现微信每日天气定时推送

## 功能介绍

### 首页

> **提供节假日、调休补班信息查看**

![首页](http://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2023/04/02/16804225092237.jpg)

### 任务管理

> 定时推送任务管理

使用Cron表达式定时执行推送Job。

![任务管理](http://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2023/04/02/16804227868870.jpg)

### 地区管理

> 推送天气地区管理

![地区管理](http://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2023/04/02/16804228462658.jpg)

### 成员管理

> 地区成员管理

成员接收所在地区天气预报

![16804229065475](http://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2023/04/02/16804229065475.jpg)

### 系统管理

> 密码修改

![系统管理](http://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2023/04/02/16804230312036.jpg)

### 微信端推送效果

> 效果展示

![效果展示](https://img-blog.csdnimg.cn/img_convert/7eb2c0132b46fdec6f661eec6f321efc.png)

## 包结构

```
.
├── deploy                                  # 部署脚本
├── src         
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── yh
│   │   │           └── weatherpush
│   │   │               ├── component       # 登陆授权相关（JWT）
│   │   │               ├── config
│   │   │               ├── controller
│   │   │               ├── dto
│   │   │               ├── entity
│   │   │               ├── enums
│   │   │               ├── exception
│   │   │               ├── manager         # 通用业务层（第三方api封装）
│   │   │               ├── mapper
│   │   │               ├── quartz          # 定时job
│   │   │               └── service
│   │   └── resources
│   │       ├── lua                         # lua脚本
│   │       ├── mapper
│   │       ├── script                      # Python脚本
│   │       └── sql                         # init sql
│   └── test
│       └── java
│           └── com
│               └── yh
│                   └── weatherpush
│                       └── generator       # mybatis-plus generator
.
```

## 技术选型

| 技术           | 说明        | 官网                                         |
|--------------|-----------|--------------------------------------------|
| SpringBoot   | Web应用开发框架 | https://spring.io/projects/spring-boot     |
| Sa-Token     | 认证授权      | https://spring.io/projects/spring-security |
| MyBatis-Plus | ORM框架     | https://baomidou.com/                      |
| Mysql        | 关系型数据库    | https://www.mysql.com/cn/                  |
| Redis        | 内存数据存储    | https://redis.io/                          |
| Nginx        | 静态资源服务器   | https://www.nginx.com/                     |
| Docker       | 应用容器引擎    | https://www.docker.com                     |
| JWT          | JWT登录支持   | https://github.com/jwtk/jjwt               |
| Swagger-UI   | API文档生成工具 | https://github.com/swagger-api/swagger-ui  |
| Redisson     | redis客户端  | https://github.com/redisson/redisson       |
| Quartz       | 任务调度框架    | http://www.quartz-scheduler.org/           |

## 数据库表结构

![表结构](http://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2023/04/02/16804223643686.jpg)

**待续...**

## License

[Apache License 2.0](https://github.com/yangh124/weather-push/blob/master/LICENSE) license.

Copyright (c) 2022-present yangh124