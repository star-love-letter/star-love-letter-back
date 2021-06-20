# 表白墙开发手册

版本 1.0.9

by 赵国庆



### 更新日志

#### 1.0.9

修复了搜索总数量参数错误



#### 1.0.8

修复了搜索帖子参数错误

添加了搜索总数量接口



#### 1.0.7

添加了搜索帖子分页

添加了搜索帖子评论数量



#### 1.0.6

添加了查询评论数量



#### 1.0.5

修复了点赞请求参数错误

修复了评论请求URL错误



#### 1.0.4

修复了点赞请求方式错误



#### 1.0.3

修复了手册一些值类型错误



#### 1.0.2

添加了 搜索接口、点赞接口



#### 1.0.1

初始版本



# 数据库表结构

```sql
--
-- 表的结构 `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(11) NOT NULL COMMENT '评论id',
  `p_id` int(11) DEFAULT NULL COMMENT '对应的帖子id',
  `name` varchar(6) NOT NULL COMMENT '评论者姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `content` varchar(160) NOT NULL COMMENT '评论内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `post`
--

CREATE TABLE IF NOT EXISTS `post` (
  `id` int(11) NOT NULL COMMENT '表白id',
  `sender` varchar(6) NOT NULL COMMENT '发送者',
  `sender_sex` bit(2) NOT NULL COMMENT '发送者性别',
  `recipient` varchar(6) NOT NULL COMMENT '被表白者',
  `recipient_sex` bit(2) NOT NULL COMMENT '被表白者性别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `content` varchar(160) NOT NULL COMMENT '表白内容',
  `thumbs_up` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '点赞'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `p_id` (`p_id`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id';
--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表白id';
--
-- 限制导出的表
--

--
-- 限制表 `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `p_id` FOREIGN KEY (`p_id`) REFERENCES `post` (`id`) ON DELETE CASCADE;
```





# 帖子内容



## 获取分页帖子列表

### 示例

get: http://localhost:8080/biaobai/post?type=1&page_index=1&page_size=3



#### 成功返回内容示范

```json
{
    "code": 1,
    "message": "查询成功",
    "posts": [
        {
            "comment_count": 7,
            "create_time": 1620733303000,
            "sender": "表白者",
            "recipient_sex": 2,
            "sender_sex": 1,
            "recipient": "被表白者",
            "id": 49,
            "thumbs_up": 0,
            "content": "表白内容"
        },
        {
            "comment_count": 0,
            "create_time": 1620733302000,
            "sender": "表白者",
            "recipient_sex": 2,
            "sender_sex": 1,
            "recipient": "被表白者",
            "id": 46,
            "thumbs_up": 0,
            "content": "表白内容"
        },
        {
            "comment_count": 0,
            "create_time": 1620733302000,
            "sender": "表白者",
            "recipient_sex": 2,
            "sender_sex": 1,
            "recipient": "被表白者",
            "id": 47,
            "thumbs_up": 0,
            "content": "表白内容"
        }
    ]
}
```



#### 错误返回内容示范

```json
{"code":-1,"message":"没有查询到结果"}
```





### 调用方式

```
url: /post;
method:get;
params:{
    type:1,
    page_index:xxxx,
    page_size:xxxx
}
```



#### 调用参数

| 参数       | 值类型  | 说明                              |
| ---------- | ------- | --------------------------------- |
| type       | 1(固定) | 类型（当前为获取分页帖子列表）    |
| page_index | int     | 第几页（不得小于1）               |
| page_size  | int     | 每页有多少个表白内容（不得小于1） |



#### 返回内容

| 参数    | 值类型 | 说明                     |
| ------- | ------ | ------------------------ |
| code    | int    | 1为获取成功 -1为获取失败 |
| message | string | 返回消息                 |
| posts   | array  | 表白内容列表             |



##### posts列表

| 参数          | 值类型 | 说明                               |
| ------------- | ------ | ---------------------------------- |
| id            | int    | 表白帖子id                         |
| sender        | string | 表白者                             |
| sender_sex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient     | string | 被表白者                           |
| recipient_sex | int    | 被表白者 1为男性 2为女性 0为未知   |
| create_time   | long   | 创建表白的时间 时间戳              |
| content       | string | 表白内容                           |
| thumbs_up     | int    | 点赞数量                           |
| comment_count | int    | 评论数量                           |





## 获取帖子总数量

**说明:可以用来计算总页数**



### 示范

get: http://localhost:8080/biaobai/post?type=3



#### 成功返回内容示范

```json
{"code":1,"count":34,"message":"查询成功"}
```



#### 错误返回内容示范

```json
{"code":-1,"message":"参数有误"}
```



### 调用方式

```
url: /post;
method:get;
params:{
    type:3
}
```



#### 调用参数

| 参数 | 值类型  | 说明                         |
| ---- | ------- | ---------------------------- |
| type | 3(固定) | 类型（当前为获取帖子总数量） |



#### 返回内容

| 参数    | 值类型 | 说明                     |
| ------- | ------ | ------------------------ |
| code    | int    | 1为获取成功 -1为获取失败 |
| message | string | 返回消息                 |
| count   | int    | 帖子总数量               |





## 获取单个帖子内容

### 示范

get: http://localhost:8080/biaobai/post?type=2&id=2



#### 成功返回内容示范

```json
{
    "comment_count": 7,
    "code": 1,
    "create_time": 1619354696000,
    "sender": "赵国庆",
    "recipient_sex": 1,
    "sender_sex": 0,
    "recipient": "赵国庆庆",
    "id": 2,
    "message": "查询成功",
    "thumbs_up": 52,
    "content": "1223"
}
```



#### 错误返回内容示范

```json
{"code":-1,"message":"没有此表白"}
```



### 调用方式

```
url: /post;
method:get;
params:{
    type:2,
    id:xxx
}
```



#### 调用参数

| 参数 | 值类型  | 说明                           |
| ---- | ------- | ------------------------------ |
| type | 2(固定) | 类型（当前为获取单个帖子内容） |
| id   | int     | 帖子id                         |



#### 返回内容

| 参数          | 值类型 | 说明                               |
| ------------- | ------ | ---------------------------------- |
| code          | int    | 1为获取成功 -1为获取失败           |
| message       | string | 返回消息                           |
| id            | int    | 表白帖子id表白帖子id               |
| sender        | string | 表白者                             |
| sender_sex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient     | string | 被表白者                           |
| recipient_sex | int    | 被表白者 1为男性 2为女性 0为未知   |
| create_time   | long   | 创建表白的时间 时间戳              |
| content       | string | 表白内容                           |
| thumbs_up     | int    | 点赞数量                           |
| comment_count | int    | 评论数量                           |



## 搜索帖子

get: http://localhost:8080/biaobai/post?type=4&keyword=表白&page_index=1&page_size=3



#### 成功返回内容示范

```json
{
    "code": 1,
    "message": "查询成功",
    "posts": [
        {
            "comment_count": 0,
            "create_time": 1620733376000,
            "sender": "表白者",
            "recipient_sex": 2,
            "sender_sex": 1,
            "recipient": "被表白者",
            "id": 57,
            "thumbs_up": 0,
            "content": "表白内容"
        },
        {
            "comment_count": 0,
            "create_time": 1620733347000,
            "sender": "表白者",
            "recipient_sex": 2,
            "sender_sex": 1,
            "recipient": "被表白者",
            "id": 56,
            "thumbs_up": 0,
            "content": "表白内容"
        },
        {
            "comment_count": 0,
            "create_time": 1620733305000,
            "sender": "表白者",
            "recipient_sex": 2,
            "sender_sex": 1,
            "recipient": "被表白者",
            "id": 53,
            "thumbs_up": 0,
            "content": "表白内容"
        }
    ]
}
```



#### 错误返回内容示范

```json
{"code":-1,"message":"没有查询到结果"}
```



### 调用方式

```
url: /post;
method:get;
params:{
    type:4,
  	keyword:xxx,
  	page_index:xxxx,
    page_size:xxxx
}
```





#### 调用参数

| 参数       | 值类型  | 说明                              |
| ---------- | ------- | --------------------------------- |
| type       | 4(固定) | 类型（当前为搜索帖子）            |
| keyword    | string  | 关键词                            |
| page_index | int     | 第几页（不得小于1）               |
| page_size  | int     | 每页有多少个表白内容（不得小于1） |



#### 返回内容

| 参数    | 值类型 | 说明                     |
| ------- | ------ | ------------------------ |
| code    | int    | 1为获取成功 -1为获取失败 |
| message | string | 返回消息                 |
| posts   | array  | 表白内容列表             |



##### posts列表

| 参数          | 值类型 | 说明                               |
| ------------- | ------ | ---------------------------------- |
| id            | int    | 表白帖子id                         |
| sender        | string | 表白者                             |
| sender_sex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient     | string | 被表白者                           |
| recipient_sex | int    | 被表白者 1为男性 2为女性 0为未知   |
| create_time   | long   | 创建表白的时间 时间戳              |
| content       | string | 表白内容                           |
| thumbs_up     | int    | 点赞数量                           |
| comment_count | int    | 评论数量                           |



## 搜索总数量

**说明:可以用来计算总页数**



### 示范

get: http://localhost:8080/biaobai/post?type=5&keyword=表白



#### 成功返回内容示范

```json
{"code":1,"count":34,"message":"查询成功"}
```



#### 错误返回内容示范

```json
{"code":-1,"message":"参数有误"}
```



### 调用方式

```
url: /post;
method:get;
params:{
    type:5,
    keyword:xxx
}
```



#### 调用参数

| 参数    | 值类型  | 说明                         |
| ------- | ------- | ---------------------------- |
| type    | 5(固定) | 类型（当前为获取帖子总数量） |
| keyword | string  | 关键词                       |



#### 返回内容

| 参数    | 值类型 | 说明                     |
| ------- | ------ | ------------------------ |
| code    | int    | 1为获取成功 -1为获取失败 |
| message | string | 返回消息                 |
| count   | int    | 帖子总数量               |



## 发布表白



### 示范

post: http://localhost:8080/biaobai/post

#### 参数示范

```json
{
    "sender": "表白者",
    "sender_sex": 1,
    "recipient": "被表白者",
    "recipient_sex": 2,
    "content": "表白内容"
}
```





#### 成功返回内容示范

```json
{"code":1,"message":"发布成功"}
```



#### 错误返回内容示范

```json
{"code":-1,"message":"参数有误"}
```



### 调用方式

```
url: /post;
method:post;
data:{
    "sender": xxx,
    "sender_sex": xxx,
    "recipient": xxx,
    "recipient_sex": xxx,
    "content": xxx
}
```



#### 调用参数

| 参数          | 值类型 | 说明                               |
| ------------- | ------ | ---------------------------------- |
| sender        | string | 表白者                             |
| sender_sex    | int    | 表白者性别 1为男性 2为女性 0为未知 |
| recipient     | string | 被表白者                           |
| recipient_sex | int    | 被表白者 1为男性 2为女性 0为未知   |
| content       | string | 表白内容                           |



#### 返回内容

| 参数    | 值类型 | 说明                     |
| ------- | ------ | ------------------------ |
| code    | int    | 1为获取成功 -1为获取失败 |
| message | string | 返回消息                 |



## 点赞

### 示范

put: http://localhost:8080/biaobai/post

### 参数示范

```json
{
    "type":1,
    "id":2,
    "thumbs_up":0
}
```



#### 成功返回内容示范

```json
{"code":1,"message":"成功"}
```



#### 错误返回内容示范

```json
{"code":-1,"message":"####"}
```



### 调用方式

```
url: /post;
method:put;
data:{
    "type":1,
    "id":xxx,
    "thumbs_up":xxx
}
```



#### 调用参数

| 参数      | 值类型  | 说明                                     |
| --------- | ------- | ---------------------------------------- |
| type      | 1(固定) | 类型（当前为点赞）                       |
| id        | int     | 帖子id                                   |
| thumbs_up | bool    | 是否为点赞（true：点赞  false:取消点赞） |







# 评论内容



## 获取帖子分页评论列表

### 示范

get: http://localhost:8080/biaobai/comment?type=1&p_id=2&page_index=1&page_size=3



#### 成功返回内容示范

```json
{
    "comments": [
        {
            "create_time": 1619420275000,
            "name": "赵国庆",
            "id": 1,
            "p_id": 2,
            "content": "内容内容内容"
        },
        {
            "create_time": 1619420700000,
            "name": "赵国庆庆",
            "id": 2,
            "p_id": 2,
            "content": "内容测试"
        }
    ],
    "code": 1,
    "message": "查询成功"
}
```



### 错误返回内容示范

```json
{"code":-1,"message":"没有查询到结果"}
```



### 调用方式

```
url: /comment;
method:get;
params:{
    "type": 1,
    "p_id": xxx,
    "page_index": xxx,
    "page_size": xxx
}
```



#### 调用参数

| 参数       | 值类型  | 说明                              |
| ---------- | ------- | --------------------------------- |
| type       | 1(固定) | 类型（当前为获取分页帖子列表）    |
| p_id       | int     | 对应的帖子id                      |
| page_index | int     | 第几页（不得小于1）               |
| page_size  | int     | 每页有多少个评论内容（不得小于1） |



#### 返回内容

| 参数     | 值类型 | 说明                     |
| -------- | ------ | ------------------------ |
| code     | int    | 1为获取成功 -1为获取失败 |
| message  | string | 返回消息                 |
| comments | array  | 评论列表                 |



##### comments列表

| 参数        | 值类型 | 说明                  |
| ----------- | ------ | --------------------- |
| id          | int    | 评论id                |
| p_id        | int    | 对应的帖子id          |
| name        | string | 发表评论者            |
| create_time | long   | 创建评论的时间 时间戳 |
| content     | string | 评论内容              |





## 获取帖子评论总数

**说明:可以用来计算总页数**



### 示范

get: http://localhost:8080/biaobai/comment?type=3&p_id=2



#### 成功返回内容示范

```json
{"code":1,"count":2,"message":"查询成功"}
```



#### 错误返回内容示范

```json
{"code":-1,"message":"参数有误"}
```



### 调用方式

```
url: /comment;
method:get;
params:{
    type:3,
    p_id:xxx
}
```



#### 调用参数

| 参数 | 值类型  | 说明                         |
| ---- | ------- | ---------------------------- |
| type | 3(固定) | 类型（当前为获取评论总数量） |
| p_id | int     | 对应的帖子id                 |



#### 返回内容

| 参数    | 值类型 | 说明                     |
| ------- | ------ | ------------------------ |
| code    | int    | 1为获取成功 -1为获取失败 |
| message | string | 返回消息                 |
| count   | int    | 评论总数量               |





## 发布评论



### 示范

post: http://localhost:8080/biaobai/comment

#### 参数示范

```json
{
    "p_id": 2,
    "name": "评论者",
    "content": "评论内容"
}
```





#### 成功返回内容示范

```json
{"code":1,"message":"发布成功"}
```



#### 错误返回内容示范

```json
{"code":-1,"message":"参数有误"}
```



### 调用方式

```
url: /comment;
method:post;
data:{
    "p_id": xxx,
    "name": xxx,
    "content": xxx
}
```



#### 调用参数

| 参数    | 值类型 | 说明         |
| ------- | ------ | ------------ |
| p_id    | int    | 对应的帖子id |
| name    | string | 评论者名     |
| content | string | 评论内容     |



#### 返回内容

| 参数    | 值类型 | 说明                     |
| ------- | ------ | ------------------------ |
| code    | int    | 1为获取成功 -1为获取失败 |
| message | string | 返回消息                 |