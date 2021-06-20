## 表白墙

简单的表白墙网站



### 目录

- back   *后端代码（java）*
- web   *前端代码（vue）*



### 演示

[演示页面](http://biaobai.xiaoxiaoge.cn)



### 接口手册&数据库表结构

[HELP_API](/HELP_API.md)



### 快速使用

1. 编译vue并替换到webapp（注意保留webapp/WEB-INF文件夹）

2. 创建数据库，[数据库表结构](/HELP_API.md#数据库表结构)

3. 修改resources/db.properties 设置数据库

	```properties
	 # JDBC
	 driver=com.mysql.cj.jdbc.Driver
	 # 数据库连接
	 url=jdbc:mysql://xxx.xxx.xxx.xxx:3306/xxxx?userSSL=false&useUnicode=true&characterEncoding=UTF-8
	 # 数据库账号
	 username=xxx
	 # 数据库密码
	 password=xxx
	```



### 技术

##### 后端技术

| 技术    | 功能说明     | 官网/文档                                      |
| ------- | ------------ | ---------------------------------------------- |
| Maven   | 项目构建管理 | http://maven.apache.org/                       |
| MyBatis | 持久层框架   | http://www.mybatis.org/mybatis-3/zh/index.html |
| LOG4J   | 日志         | https://logging.apache.org/log4j/2.x/          |

##### 前端技术

| 技术          | 功能说明               | 官网/文档                                        |
| ------------- | ---------------------- | ------------------------------------------------ |
| vue           | 渐进式 JavaScript 框架 | https://cn.vuejs.org/                            |
| element       | 网站快速成型工具       | https://element.eleme.cn/#                       |
| QRCode        | 前端二维码生成器       | http://davidshimjs.github.io/qrcodejs/           |
| Vue-baberrage | Vue弹幕插件            | http://blog.chenhaotaishuaile.com/vue-baberrage/ |



## 开源许可证

[GPL-3.0 license](/LICENSE)



### 项目完善中....