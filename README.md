## 企业微信配置
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
## 天气开放平台配置
### 进入[和风天气开放平台](https://dev.qweather.com/)，点击注册
***注册完成后建议申请认证个人开发者***（支持更多接口）
![16361640883550](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/06/16361640883550.jpg)
### 登录控制台，点击应用管理，创建应用，获取到应用的KEY
![16361644923902](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2021/11/06/16361644923902.jpg)
## 项目开发
前端项目：[weather-push-admin](https://github.com/yangh124/weather-push-admin)
后台项目：[weather-push](https://github.com/yangh124/weather-push)
## 定时任务配置
### 执行执行初始化sql
在后台项目resources/sql/init.sql
### 运行前后端项目
![16561503716829](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2022/06/25/16561503716829.jpg)

![16561503961795](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2022/06/25/16561503961795.jpg)
### 登录后台
使用默认账号密码登录：admin 123456
![16561505074675](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2022/06/25/16561505074675.jpg)
### 配置地区
即配置需要发送天气预报的地区
**注：这里的地区对应企业微信通讯录中的标签**
> 地区管理 -> 添加
![16561506595631](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2022/06/25/16561506595631.jpg)
### 地区绑定成员
> 成员管理 -> 选择左边的地区 -> 点击添加成员
**注：这里的成员对应企业微信通讯录中的成员**
![16561508273991](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2022/06/25/16561508273991.jpg)
### 创建定时任务
![16561517473902](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2022/06/25/16561517473902.jpg)

## 最终效果
![16561520350860](https://yh-blog-files.oss-cn-hangzhou.aliyuncs.com/2022/06/25/16561520350860.jpg)
