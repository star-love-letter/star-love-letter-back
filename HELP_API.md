# 星愿墙开发手册

by 赵国庆



## 项目内容

### 更新日志

### 1.1.9

使用email绑定验证码

- [获取图形验证码-调用参数](#获取图形验证码)



#### 1.1.8

修复登录调用参数错误

- [登录-调用参数](#登录)



#### 1.1.7

token绑定用户，登录获取token

修改了退出登录接口路径

- [登录-返回内容](#登录)
- ~~获取token接口~~
- [退出登录-url](#退出登录)



#### 1.1.6

添加了token



#### 1.1.5

发送邮箱前验证图片验证码

注册账号添加了参数演示



#### 1.1.4

修复了注册演示参数有误



#### 1.1.3

在获取帖子/评论中添加了用户公开信息

删除了获取帖子/评论的用户信息接口



#### 1.1.2

添加了上传文件接口

添加了获取用户公开信息接口

更新了发布表白、发布评论接口





#### 1.1.1

添加了图片验证码

添加了邮箱验证码





#### 1.1.0

全新版本



[查看完整更新日志](UPDATE_LOG.md)



### 数据库表结构

[wall.sql](wall.sql)





## 通用返回内容

### 示例

```json
{"msg":"消息内容","code":100}
```



### 返回内容

| 参数 | 值类型 | 说明     |
| ---- | ------ | -------- |
| msg  | string | 消息内容 |
| code | int    | 状态码   |



#### 状态码

| 数值 | 状态名         | 说明         |
| ---- | -------------- | ------------ |
| 0    | NONE           | 成功         |
| 100  | DEFAULT        | 错误         |
| 101  | NOT_LOGIN      | 没有登录账号 |
| 200  | DEADLY         | 致命错误     |
| 201  | DEADLY_SYSTEEM | 系统致命错误 |





## 账号操作

### 登录

#### 示例

post: http://localhost:8080/api/user/login



##### 参数示范 x-www-form-urlencoded

```ini
email = admin@conststar.cn
password = xxxx
```



##### 成功返回内容示范

```json
{
    "msg": "登录成功",
    "code": 0,
    "token": "1164442003@qq.com 83B9197E8F0572E87BAB4B8128C7299752E67B6B0355F1E8F330A072F3BF0E78"
}
```



#### 调用方式

```
url: /api/user/login;
method:post;
data:{
    email:xxxx,
    password:xxxx
}
```



##### 调用参数

| 参数     | 值类型 | 说明 |
| -------- | ------ | ---- |
| email    | string | 邮箱 |
| password | string | 密码 |



##### 返回内容

| 参数  | 值类型 | 说明  |
| ----- | ------ | ----- |
| token | string | token |





### 退出登录

#### 示例

post: http://localhost:8080/api/user/logout



##### 成功返回内容示范

```json
{"msg":"账号已退出","code":0}
```



#### 调用方式

```
url: /api/user/quit;
method:post;
header:{
	token:xxx
}
```



##### 调用参数

无



##### 返回内容

无额外内容





### 注册

#### 示例

post: http://localhost:8080/api/user/add



##### 参数示范 x-www-form-urlencoded

```ini
email = admin@conststar.cn
password = 123123456
name = 用户名称
imageCode = zgq6
emailCode = 6hhh
```



##### 成功返回内容示范

```json
{"msg":"注册成功","code":0}
```



#### 调用方式

```
url: /api/user/add;
method:post;
data:{
    email:xxxx,
    password:xxxx,
    name:xxx,
    imageCode:xxxx,
    emailCode:xxxx
}
```



##### 调用参数

| 参数      | 值类型 | 说明                          |
| --------- | ------ | ----------------------------- |
| email     | string | 邮箱                          |
| password  | string | 密码                          |
| name      | string | 用户名称                      |
| imageCode | string | [图片验证码](#获取图片验证码) |
| emailCode | string | [邮箱验证码](#获取邮箱验证码) |



##### 返回内容

无额外内容





### 获取用户信息

#### 示例

get: http://localhost:8080/api/user/user



##### 成功返回内容示范

```json
{
    "msg": "获取成功",
    "code": 0,
    "user": {
        "createTime": 1624164437000,
        "email": "admin@conststar.cn",
        "id": 2,
        "lastTime": 1624608382000,
        "name": "赵国庆"
    }
}
```



#### 调用方式

```
url: /api/user/user;
method:get;
header:{
	token:xxx
}
```



##### 调用参数

无



##### 返回内容

| 参数 | 值类型 | 说明     |
| ---- | ------ | -------- |
| user | object | 用户内容 |



###### user内容

| 参数       | 值类型 | 说明                  |
| ---------- | ------ | --------------------- |
| id         | int    | 用户id                |
| lastTime   | long   | 上次登录的时间 时间戳 |
| createTime | long   | 注册账号的时间 时间戳 |
| name       | string | 用户名称              |
| email      | string | 邮箱                  |





### 获取用户公开信息

#### 示例

get: http://localhost:8080/api/user/userPublic



##### 成功返回内容示范

```json
{
    "msg": "获取成功",
    "code": 0,
    "userPublic": {
        "createTime": 1624164392000,
        "email": "admin@conststar.cn",
        "id": 1,
        "lastTime": 1624164392000,
        "name": "12321"
    }
}
```



#### 调用方式

```
url: /api/user/userPublic;
method:get;
params:{
    id:xxxx
};
header:{
	token:xxx
}
```



##### 调用参数

| 参数 | 值类型 | 说明   |
| ---- | ------ | ------ |
| id   | int    | 用户id |



##### 返回内容

| 参数       | 值类型 | 说明     |
| ---------- | ------ | -------- |
| userPublic | object | 用户内容 |



###### userPublic内容

| 参数       | 值类型 | 说明                  |
| ---------- | ------ | --------------------- |
| id         | int    | 用户id                |
| email      | string | 用户邮箱              |
| createTime | long   | 注册账号的时间 时间戳 |
| lastTime   | long   | 上次登录的时间 时间戳 |
| name       | string | 用户名称              |





### 获取图形验证码

#### 示例

get: http://localhost:8080/api/user/verifyImage



##### 成功返回内容示范

```json
{"msg":"获取成功","image":"data:image/png;base64,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","code":0}
```



#### 调用方式

```
url: /api/user/verifyImage;
method:get;
params:{
    email:xxxx
}
```



##### 调用参数

| 参数  | 值类型 | 说明             |
| ----- | ------ | ---------------- |
| email | string | 获取验证码的邮箱 |



##### 返回内容

| 参数  | 值类型 | 说明              |
| ----- | ------ | ----------------- |
| image | string | 图形验证码 base64 |





### 获取邮箱验证码

#### 示例

get: http://localhost:8080/api/user/verifyEmail?email=admin@conststar.cn?imageCode=zgq6



##### 成功返回内容示范

```java
{"msg":"获取成功","code":0}
```



#### 调用方式

```
url: /api/user/verifyEmail;
method:get;
params:{
	imageCode:xxxx,
    email:xxxx
}
```



##### 调用参数

| 参数      | 值类型 | 说明             |
| --------- | ------ | ---------------- |
| email     | string | 获取验证码的邮箱 |
| imageCode | string | 图片验证码       |



##### 返回内容

无额外内容





## 文件操作



### 上传图片

#### 示例

post: http://localhost:8080/api/file/image



##### 参数示范 form-data

```ini
file = Cache_7b9745cb0eebc9cc.jpg
```



##### 成功返回内容示范

```java
{"msg":"上传成功","file":"5d8dffb031cf465bbc5edaca5378eccf.jpg","code":0}
```



#### 调用方式

```
url: /api/file/image;
method:post;
data:{
    file:xxxx
};
header:{
	token:xxx
}
```



##### 调用参数

| 参数 | 值类型 | 说明                                                |
| ---- | ------ | --------------------------------------------------- |
| file | file   | 上传的图片文件 支持[JPG, BMP, GIF, WBMP, PNG, JPEG] |



##### 返回内容

| 参数 | 值类型 | 说明           |
| ---- | ------ | -------------- |
| file | string | 已上传的文件id |





### 下载图片

#### 示例

get: http://localhost:8080/api/file/image/5d8dffb031cf465bbc5edaca5378eccf.jpg



##### 成功返回内容示范

返回图片文件



#### 调用方式

```
url: /api/file/image/{image};
method:get;
```



##### 调用参数

| 参数    | 值类型 | 说明               |
| ------- | ------ | ------------------ |
| {image} | url    | 已添加使用的文件id |



##### 返回内容

无





## 帖子内容



### 获取帖子分页列表

#### 示例

get: http://localhost:8080/api/table/pageList?pageIndex=1&pageSize=3



##### 成功返回内容示范

```json
{
    "msg": "获取成功",
    "code": 0,
    "list": [
        {
            "recipientSex": 1,
            "images": "[\"78b440903f1241e8a7f50d8c26f3ec07.jpg\"]",
            "supportCount": 0,
            "createTime": 1622093601000,
            "sender": "马屹东",
            "recipient": "辛金达",
            "anonymous": false,
            "senderSex": 1,
            "userPublic": {
                "lastTime": 1624164392000,
                "createTime": 1624164392000,
                "name": "12321",
                "id": 1,
                "email": "admin@conststar.cn"
            },
            "id": 0,
            "content": "哦不错呦",
            "commentCount": 1
        },
        {
            "recipientSex": 2,
            "supportCount": 0,
            "createTime": 1621819684000,
            "sender": "人红尘",
            "recipient": "任洪琛",
            "anonymous": false,
            "senderSex": 0,
            "userPublic": {
                "lastTime": 1624164392000,
                "createTime": 1624164392000,
                "name": "12321",
                "id": 1,
                "email": "admin@conststar.cn"
            },
            "id": 0,
            "content": "好牛啊",
            "commentCount": 0
        },
        {
            "recipientSex": 2,
            "supportCount": 0,
            "createTime": 1621819650000,
            "sender": "王钦宇",
            "recipient": "段富强",
            "anonymous": true,
            "senderSex": 1,
            "id": 0,
            "content": "SB",
            "commentCount": 3
        }
    ]
}
```





#### 调用方式

```
url: /api/table/pageList;
method:get;
params:{
    pageIndex:xxxx,
    pageSize:xxxx
}
```



##### 调用参数

| 参数      | 值类型 | 说明                              |
| --------- | ------ | --------------------------------- |
| pageIndex | int    | 第几页（不得小于1）               |
| pageSize  | int    | 每页有多少个表白内容（不得小于1） |



##### 返回内容

| 参数 | 值类型 | 说明         |
| ---- | ------ | ------------ |
| list | array  | 表白内容列表 |



###### list列表

| 参数         | 值类型 | 说明                               |
| ------------ | ------ | ---------------------------------- |
| id           | int    | 表白帖子id                         |
| sender       | string | 表白者                             |
| senderSex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient    | string | 被表白者                           |
| recipientSex | int    | 被表白者 1为男性 2为女性 0为未知   |
| createTime   | long   | 创建表白的时间 时间戳              |
| content      | string | 表白内容                           |
| anonymous    | bool   | 是否为匿名                         |
| supportCount | int    | 点赞数量                           |
| commentCount | int    | 评论数量                           |
| images       | string | 帖子图片  [下载图片](#下载图片)    |
| userPublic   | object | 用户公开信息（非匿名）             |



###### userPublic内容

| 参数       | 值类型 | 说明                  |
| ---------- | ------ | --------------------- |
| id         | int    | 用户id                |
| email      | string | 用户邮箱              |
| lastTime   | long   | 上次登录的时间 时间戳 |
| createTime | long   | 注册账号的时间 时间戳 |
| name       | string | 用户名称              |





### 获取帖子总数量

**说明:可以用来计算总页数**



#### 示范

get: http://localhost:8080/api/table/count



##### 成功返回内容示范

```json
{"msg":"获取成功","code":0,"count":15}
```



#### 调用方式

```
url: /api/table/count;
method:get;
```



##### 调用参数

无



##### 返回内容

| 参数  | 值类型 | 说明       |
| ----- | ------ | ---------- |
| count | int    | 帖子总数量 |





### 获取单个帖子内容

#### 示范

get: http://localhost:8080/api/table/table?id=1



##### 成功返回内容示范

```json
{
    "msg": "获取成功",
    "code": 0,
    "table": {
        "recipientSex": 1,
        "images": "[\"78b440903f1241e8a7f50d8c26f3ec07.jpg\"]",
        "supportCount": 0,
        "createTime": 1622093601000,
        "sender": "马屹东",
        "recipient": "辛金达",
        "anonymous": false,
        "senderSex": 1,
        "userPublic": {
            "lastTime": 1624164392000,
            "createTime": 1624164392000,
            "name": "12321",
            "id": 1,
            "email": "admin@conststar.cn"
        },
        "id": 0,
        "content": "哦不错呦",
        "commentCount": 1
    }
}
```



#### 调用方式

```
url: /api/table/table;
method:get;
params:{
    id:xxx
}
```



##### 调用参数

| 参数 | 值类型 | 说明   |
| ---- | ------ | ------ |
| id   | int    | 帖子id |



##### 返回内容

| 参数  | 值类型 | 说明     |
| ----- | ------ | -------- |
| table | object | 帖子内容 |



###### table内容

| 参数         | 值类型 | 说明                               |
| ------------ | ------ | ---------------------------------- |
| id           | int    | 表白帖子id                         |
| sender       | string | 表白者                             |
| senderSex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient    | string | 被表白者                           |
| recipientSex | int    | 被表白者 1为男性 2为女性 0为未知   |
| createTime   | long   | 创建表白的时间 时间戳              |
| content      | string | 表白内容                           |
| anonymous    | bool   | 是否为匿名                         |
| supportCount | int    | 支持数量                           |
| commentCount | int    | 评论数量                           |
| images       | string | 帖子图片  [下载图片](#下载图片)    |
| userPublic   | object | 用户公开信息（非匿名）             |



######  userPublic内容

| 参数       | 值类型 | 说明                  |
| ---------- | ------ | --------------------- |
| id         | int    | 用户id                |
| email      | string | 用户邮箱              |
| lastTime   | long   | 上次登录的时间 时间戳 |
| createTime | long   | 注册账号的时间 时间戳 |
| name       | string | 用户名称              |





### 搜索帖子

#### 示范

get: http://localhost:8080/api/table/searchList?pageIndex=1&pageSize=2&keyword=赵国庆



##### 成功返回内容示范

```json
{
    "msg": "获取成功",
    "code": 0,
    "list": [
        {
            "recipientSex": 0,
            "supportCount": 0,
            "createTime": 1620781831000,
            "sender": "赵国庆",
            "recipient": "赵国庆庆",
            "anonymous": false,
            "senderSex": 2,
            "userPublic": {
                "lastTime": 1624164392000,
                "createTime": 1624164392000,
                "name": "12321",
                "id": 1,
                "email": "admin@conststar.cn"
            },
            "id": 0,
            "content": "好",
            "commentCount": 0
        },
        {
            "recipientSex": 0,
            "supportCount": 1,
            "createTime": 1619424609000,
            "sender": "赵国庆",
            "recipient": "赵国庆庆",
            "anonymous": false,
            "senderSex": 2,
            "userPublic": {
                "lastTime": 1624164392000,
                "createTime": 1624164392000,
                "name": "12321",
                "id": 1,
                "email": "admin@conststar.cn"
            },
            "id": 0,
            "content": "内容测试",
            "commentCount": 3
        }
    ]
}
```



#### 调用方式

```
url: /api/table/searchList;
method:get;
params:{
  	keyword:xxx,
  	pageIndex:xxxx,
    pageSize:xxxx
}
```



##### 调用参数

| 参数      | 值类型 | 说明                              |
| --------- | ------ | --------------------------------- |
| keyword   | string | 关键词                            |
| pageIndex | int    | 第几页（不得小于1）               |
| pageSize  | int    | 每页有多少个表白内容（不得小于1） |



##### 返回内容

| 参数 | 值类型 | 说明         |
| ---- | ------ | ------------ |
| list | array  | 表白内容列表 |



###### list列表

| 参数         | 值类型 | 说明                               |
| ------------ | ------ | ---------------------------------- |
| id           | int    | 表白帖子id                         |
| sender       | string | 表白者                             |
| senderSex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient    | string | 被表白者                           |
| recipientSex | int    | 被表白者 1为男性 2为女性 0为未知   |
| createTime   | long   | 创建表白的时间 时间戳              |
| content      | string | 表白内容                           |
| supportCount | int    | 支持数量                           |
| commentCount | int    | 评论数量                           |
| userPublic   | object | 用户公开信息（非匿名）             |



######  userPublic内容

| 参数       | 值类型 | 说明                  |
| ---------- | ------ | --------------------- |
| id         | int    | 用户id                |
| email      | string | 用户邮箱              |
| lastTime   | long   | 上次登录的时间 时间戳 |
| createTime | long   | 注册账号的时间 时间戳 |
| name       | string | 用户名称              |





### 搜索总数量

**说明:可以用来计算总页数**



#### 示范

get: http://localhost:8080/api/table/searchCount?keyword=赵国庆



##### 成功返回内容示范

```json
{"msg":"获取成功","code":0,"count":4}
```



#### 调用方式

```
url: /api/table/searchCount;
method:get;
params:{
    keyword:xxx
}
```



##### 调用参数

| 参数    | 值类型 | 说明   |
| ------- | ------ | ------ |
| keyword | string | 关键词 |



##### 返回内容

| 参数  | 值类型 | 说明       |
| ----- | ------ | ---------- |
| count | int    | 帖子总数量 |





### 发布表白



#### 示范

post: http://localhost:8080/api/table/add



##### 参数示范 x-www-form-urlencoded

```ini
sender = 表白者
senderSex = 1
recipient = 被表白者
recipientSex =2
content = 表白内容
anonymous = true
images = ["5d8dffb031cf465bbc5edaca5378eccf.jpg"]
```



##### 成功返回内容示范

```json
{"msg":"发布成功","code":0}
```



#### 调用方式

```
url: /api/table/add;
method:post;
data:{
    "sender": xxx,
    "senderSex": xxx,
    "recipient": xxx,
    "recipientSex": xxx,
    "content": xxx,
    "anonymous":xxx,
    "images":["xxx","xxx"]
};
header:{
	token:xxx
}
```



##### 调用参数

| 参数         | 值类型 | 说明                               |
| ------------ | ------ | ---------------------------------- |
| sender       | string | 表白者                             |
| senderSex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient    | string | 被表白者                           |
| recipientSex | int    | 被表白者 1为男性 2为女性 0为未知   |
| content      | string | 表白内容                           |
| anonymous    | bool   | 是否匿名                           |
| images       | array  | 图片列表  [上传图片](#上传图片)    |



##### 返回内容

无额外内容





### 支持（点赞）

#### 示范

put: http://localhost:8080/api/table/support?tableId=3



##### 成功返回内容示范

```json
{"msg":"已点赞","code":0}
```





#### 调用方式

```
url: /api/table/support;
method:put;
data:{
    "tableId":xxx
};
header:{
	token:xxx
}
```



##### 调用参数

| 参数 | 值类型 | 说明   |
| ---- | ------ | ------ |
| id   | int    | 帖子id |



#### 返回内容

无额外内容





### 取消支持（取消点赞）

#### 示范

delete: http://localhost:8080/api/table/support?tableId=3



##### 成功返回内容示范

```json
{"msg":"已取消点赞","code":0}
```





#### 调用方式

```
url: /api/table/support;
method:delete;
data:{
    "tableId":xxx
};
header:{
	token:xxx
}
```



##### 调用参数

| 参数 | 值类型 | 说明   |
| ---- | ------ | ------ |
| id   | int    | 帖子id |



#### 返回内容

无额外内容





## 评论内容



### 获取帖子分页评论列表

#### 示范

get: http://localhost:8080/api/comment/pageList?pageIndex=1&pageSize=3&tableId=86



##### 成功返回内容示范

```json
{
    "msg": "获取成功",
    "code": 0,
    "list": [
        {
            "createTime": 1621504182000,
            "anonymous": true,
            "tableId": 86,
            "id": 0,
            "userId": 1,
            "content": "臭傻*"
        },
        {
            "createTime": 1621502882000,
            "anonymous": false,
            "tableId": 86,
            "userPublic": {
                "lastTime": 1624164392000,
                "createTime": 1624164392000,
                "name": "12321",
                "id": 1,
                "email": "admin@conststar.cn"
            },
            "id": 0,
            "userId": 1,
            "content": "你kin你擦"
        },
        {
            "createTime": 1621497073000,
            "anonymous": false,
            "tableId": 86,
            "userPublic": {
                "lastTime": 1624164392000,
                "createTime": 1624164392000,
                "name": "12321",
                "id": 1,
                "email": "admin@conststar.cn"
            },
            "id": 0,
            "userId": 1,
            "content": "不会吧不会吧不会真的有人这么自恋吧？"
        }
    ]
}
```



#### 调用方式

```
url: /api/comment/pageList;
method:get;
params:{
    "tableId": xxx,
    "pageIndex": xxx,
    "pageSize": xxx
}
```



##### 调用参数

| 参数      | 值类型 | 说明                              |
| --------- | ------ | --------------------------------- |
| tableId   | int    | 对应的帖子id                      |
| pageIndex | int    | 第几页（不得小于1）               |
| pageSize  | int    | 每页有多少个评论内容（不得小于1） |



##### 返回内容

| 参数 | 值类型 | 说明     |
| ---- | ------ | -------- |
| list | array  | 评论列表 |



###### list列表

| 参数       | 值类型 | 说明                            |
| ---------- | ------ | ------------------------------- |
| id         | int    | 评论id                          |
| tableId    | int    | 对应的帖子id                    |
| userId     | int    | 对应的用户id                    |
| name       | string | 匿名名称                        |
| createTime | long   | 创建评论的时间 时间戳           |
| content    | string | 评论内容                        |
| anonymous  | bool   | 是否为匿名                      |
| images     | string | 帖子图片  [下载图片](#下载图片) |
| userPublic | object | 用户公开信息（非匿名）          |



######  userPublic内容

| 参数       | 值类型 | 说明                  |
| ---------- | ------ | --------------------- |
| id         | int    | 用户id                |
| email      | string | 用户邮箱              |
| lastTime   | long   | 上次登录的时间 时间戳 |
| createTime | long   | 注册账号的时间 时间戳 |
| name       | string | 用户名称              |





### 获取帖子评论总数

**说明:可以用来计算总页数**



#### 示范

get: http://localhost:8080/api/comment/count?tableId=86



##### 成功返回内容示范

```json
{"msg":"获取成功","code":0,"count":4}
```



#### 调用方式

```
url: /api/comment/count;
method:get;
params:{
    tableId:xxx
}
```



##### 调用参数

| 参数    | 值类型  | 说明                         |
| ------- | ------- | ---------------------------- |
| type    | 3(固定) | 类型（当前为获取评论总数量） |
| tableId | int     | 对应的帖子id                 |



##### 返回内容

| 参数  | 值类型 | 说明       |
| ----- | ------ | ---------- |
| count | int    | 评论总数量 |





### 发布评论



#### 示范

post: http://localhost:8080/api/comment/add



##### 参数示范 x-www-form-urlencoded

```ini
tableId = 2
name = 匿名名称
content = 评论内容
anonymous = true
images = ["5d8dffb031cf465bbc5edaca5378eccf.jpg"]
```





##### 成功返回内容示范

```json
{"msg":"发布成功","code":0}
```



#### 调用方式

```
url: /api/comment/add;
method:post;
data:{
    "tableId": xxx,
    "name": xxx,
    "content": xxx,
    "anonymous":xxx
    "images":["xxx","xxx"]
};
header:{
	token:xxx
}
```



##### 调用参数

| 参数      | 值类型 | 说明                            |
| --------- | ------ | ------------------------------- |
| tableId   | int    | 对应的帖子id                    |
| name      | string | 匿名名称【仅在匿名下使用】      |
| content   | string | 评论内容                        |
| anonymous | bool   | 是否匿名                        |
| images    | array  | 图片列表  [上传图片](#上传图片) |



##### 返回内容

无额外内容

