<p align="center"><img src="https://github.com/star-love-letter/.github/blob/main/img/logo.png" height="80" alt="星愿墙"></p>

## 校园小情书后端

SpringBoot+MyBatis+shiro 完整的表白墙项目



### 功能

- 登录&注册
- 卡通头像
- 匿名表白
- 发布评论
- 点赞
- 搜索
- 我的帖子
- 旋转验证码
- 邮箱验证码
- 微信小程序登录
- 邮箱提醒
- 图片上传
- 后台审核
- 用户举报
- 用户封禁
- 后台日志
- 数据库备份
- Docker部署



### 演示地址

https://wall.conststar.cn/



### 完整项目

https://github.com/star-love-letter


### 快速使用
```shell
docker run -d --name wall -p 8089:8089 -v /root/wall:/data wall
```



### 主要的库

| 技术              | 版本    | 功能说明     | 官网/文档                                      |
| ----------------- | ------- | ------------ | ---------------------------------------------- |
| Spring            |         | 框架         | https://spring.io/                             |
| SpringBoot        | 2.6.7   | 框架         | https://spring.io/projects/spring-boot         |
| MyBatis           | 2.2.2   | 持久层框架   | http://www.mybatis.org/mybatis-3/zh/index.html |
| Shiro             | 1.9.0   | 安全框架     | https://logging.apache.org/log4j/2.x/          |
| Swagger-Springfox | 3.0.0   | 接口文档框架 | http://springfox.github.io/springfox/          |
| Lombok            | 1.18.24 | 减少代码冗余 | https://projectlombok.org/                     |



### 更新日志

[UPDATE_LOG.md](UPDATE_LOG.md)



### 协议

[MIT License](LICENSE.txt)



**有问题欢迎通过[issues](/issues)反馈，感谢您的帮助**
