# 星愿墙开发手册

版本 1.1.0

by 赵国庆



## 项目内容

### 更新日志

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

| 数值 | 状态名    | 说明         |
| ---- | --------- | ------------ |
| 0    | NONE      | 成功         |
| 100  | DEFAULT   | 默认错误     |
| 101  | NOT_LOGIN | 没有登录账号 |
| 200  | DEADLY    | 致命错误     |



## 账号操作



### 登录

#### 示例

post: http://localhost:8080/api/user/login



##### 参数示范 x-www-form-urlencoded

```ini
email = 1164442003@qq.com
password = xxxx
```



##### 成功返回内容示范

```json
{
    "msg": "登录成功",
    "code": 0,
    "user": {
        "lastTime": 1624597471000,
        "createTime": 1624164437000,
        "name": "赵国庆",
        "id": 2,
        "email": "1164442003@qq.com"
    }
}
```



#### 调用方式

```
url: /api/user/login;
method:post;
params:{
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



### 退出登录

#### 示例

post: http://localhost:8080/api/user/quit



##### 成功返回内容示范

```json
{"msg":"账号已退出","code":0}
```



#### 调用方式

```
url: /api/user/quit;
method:post;
```



##### 调用参数

无



##### 返回内容

无额外内容



### 注册

#### 示例

post: http://localhost:8080/api/user/add



##### 成功返回内容示范

```json
{"msg":"注册成功","code":0}
```



#### 调用方式

```
url: /api/user/add;
method:post;
params:{
    email:xxxx,
    password:xxxx,
    name:xxx
}
```



##### 调用参数

| 参数     | 值类型 | 说明     |
| -------- | ------ | -------- |
| email    | string | 邮箱     |
| password | string | 密码     |
| name     | string | 用户名称 |



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
        "email": "1164442003@qq.com",
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
            "recipientSex": 2,
            "supportCount": 0,
            "createTime": 1621819684000,
            "sender": "人红尘",
            "recipient": "任洪琛",
            "senderSex": 0,
            "id": 89,
            "content": "好牛啊",
            "commentCount": 0
        },
        {
            "recipientSex": 2,
            "supportCount": 0,
            "createTime": 1621819650000,
            "sender": "王钦宇",
            "recipient": "段富强",
            "senderSex": 1,
            "id": 88,
            "content": "xxx",
            "commentCount": 3
        },
        {
            "recipientSex": 1,
            "supportCount": 0,
            "createTime": 1621498594000,
            "sender": "嘎嘎",
            "recipient": "丫丫",
            "senderSex": 1,
            "id": 87,
            "content": "爱你哦，臭宝",
            "commentCount": 0
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
| supportCount | int    | 点赞数量                           |
| commentCount | int    | 评论数量                           |





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
        "recipientSex": 2,
        "supportCount": 2,
        "createTime": 1619418459000,
        "sender": "表白者",
        "recipient": "被表白者",
        "senderSex": 1,
        "id": 1,
        "content": "表白内容",
        "commentCount": 0
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
| id           | int    | 表白帖子id表白帖子id               |
| sender       | string | 表白者                             |
| senderSex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient    | string | 被表白者                           |
| recipientSex | int    | 被表白者 1为男性 2为女性 0为未知   |
| createTime   | long   | 创建表白的时间 时间戳              |
| content      | string | 表白内容                           |
| supportCount | int    | 点赞数量                           |
| commentCount | int    | 评论数量                           |



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
            "senderSex": 2,
            "id": 75,
            "content": "好",
            "commentCount": 0
        },
        {
            "recipientSex": 0,
            "supportCount": 1,
            "createTime": 1619424609000,
            "sender": "赵国庆",
            "recipient": "赵国庆庆",
            "senderSex": 2,
            "id": 3,
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
| supportCount | int    | 点赞数量                           |
| commentCount | int    | 评论数量                           |



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
```



#### 成功返回内容示范

```json
{"msg":"发布成功","code":0}
```



### 调用方式

```
url: /api/table/add;
method:post;
data:{
    "sender": xxx,
    "senderSex": xxx,
    "recipient": xxx,
    "recipientSex": xxx,
    "content": xxx
}
```



#### 调用参数

| 参数         | 值类型 | 说明                               |
| ------------ | ------ | ---------------------------------- |
| sender       | string | 表白者                             |
| senderSex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient    | string | 被表白者                           |
| recipientSex | int    | 被表白者 1为男性 2为女性 0为未知   |
| content      | string | 表白内容                           |



#### 返回内容

无额外内容



### 点赞

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
}
```



##### 调用参数

| 参数 | 值类型 | 说明   |
| ---- | ------ | ------ |
| id   | int    | 帖子id |



#### 返回内容

无额外内容





### 取消点赞

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
            "name": "任洪琛他爹",
            "tableId": 86,
            "id": 33,
            "userId": 0,
            "content": "臭傻*"
        },
        {
            "createTime": 1621502882000,
            "name": "任洪琛",
            "tableId": 86,
            "id": 32,
            "userId": 0,
            "content": "你kin你擦"
        },
        {
            "createTime": 1621497073000,
            "name": "徐爽",
            "tableId": 86,
            "id": 31,
            "userId": 0,
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



###### comments列表

| 参数       | 值类型 | 说明                  |
| ---------- | ------ | --------------------- |
| id         | int    | 评论id                |
| tableId    | int    | 对应的帖子id          |
| userId     | int    | 对应的用户id          |
| name       | string | 发表评论者            |
| createTime | long   | 创建评论的时间 时间戳 |
| content    | string | 评论内容              |





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
name = 评论者
content = 评论内容
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
    "content": xxx
}
```



##### 调用参数

| 参数    | 值类型 | 说明         |
| ------- | ------ | ------------ |
| tableId | int    | 对应的帖子id |
| name    | string | 评论者名     |
| content | string | 评论内容     |



##### 返回内容

无额外内容