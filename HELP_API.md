# 星愿墙
接口文档

***版本: 1.2.6***

**联系方式:**  
开发者邮箱  
admin@conststar.cn  




## 通用响应内容
| 状态码 | 描述 |
| ---- | ----------- |
| 201 | 操作失败 |
| 1000 | 服务器错误 |
| 10001 | 参数无效 |
| 10002 | 参数无效 |
| 10003 | 参数类型错误 |
| 10004 | 参数缺失 |
| 20001 | 用户未登录 |
| 20002 | 账号不存在或密码错误 |
| 20003 | 账号已被禁用 |
| 20004 | 用户不存在 |
| 20005 | 用户已存在 |
| 30001 | 某业务出现问题 |
| 40001 | 系统繁忙，请稍后重试 |
| 50001 | 数据未找到 |
| 50002 | 数据有误 |
| 50003 | 数据已存在 |
| 50004 | 查询出错 |
| 60001 | 内部系统接口调用异常 |
| 60002 | 外部系统接口调用异常 |
| 60003 | 该接口禁止访问 |
| 60004 | 接口地址无效 |
| 60005 | 接口请求超时 |
| 60006 | 接口负载过高 |
| 70001 | 无权限访问 |



## 接口列表
### 评论内容

#### 发布评论

POST: /api/comment/add

##### 描述

发布评论，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| tableId | query | 帖子id | Yes | integer |
| content | query | 内容 | Yes | string |
| images | query | 图片列表 | Yes | string |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 获取帖子评论总数

GET: /api/comment/count

##### 描述

获取帖子评论总数，返回评论总数，用来计算页数

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| tableId | query | 帖子id | Yes | integer |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«int»](#统一响应实体«int») |



#### 获取帖子分页评论列表

GET: /api/comment/pageList

##### 描述

获取帖子分页评论列表，返回评论列表

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| tableId | query | 帖子id | Yes | integer |
| pageIndex | query | 页索引 | Yes | integer |
| pageSize | query | 页大小 | Yes | integer |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«List«评论实体»»](#统一响应实体«List«评论实体»») |



### 文件操作

#### 上传图片

POST: /api/file/image

##### 描述

上传图片，返回上传的图片名

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«string»](#统一响应实体«string») |



#### 获取图片

GET: /api/file/image/{image}

##### 描述

获取图片，返回图片文件

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| image | path | 图片文件名 | Yes | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | */* | string |



### 帖子内容

#### 发布表白

POST: /api/table/add

##### 描述

发布表白，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| sender | query | 表白者名称 | Yes | string |
| senderSex | query | 表白者性别 | Yes | integer |
| recipient | query | 被表白者名称 | Yes | string |
| recipientSex | query | 被表白者性别 | Yes | integer |
| anonymous | query | 是否匿名 | Yes | boolean |
| content | query | 表白内容 | Yes | string |
| images | query | 图片列表 | Yes | string |
| notifyEmail | query | 是否邮箱通知 | Yes | boolean |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 获取帖子总数

GET: /api/table/count

##### 描述

获取帖子总数，返回帖子总数，用来计算页数

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«int»](#统一响应实体«int») |



#### 获取帖子分页列表

GET: /api/table/pageList

##### 描述

获取帖子分页列表，返回帖子列表

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| pageIndex | query | 页索引 | Yes | integer |
| pageSize | query | 页大小 | Yes | integer |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«List«帖子实体»»](#统一响应实体«List«帖子实体»») |



#### 获取搜索帖子总数

GET: /api/table/searchCount

##### 描述

获取搜索帖子总数，返回帖子数量

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| keyword | query | 关键词 | Yes | string |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«int»](#统一响应实体«int») |



#### 搜索帖子

GET: /api/table/searchList

##### 描述

搜索帖子，返回帖子列表

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| keyword | query | 关键词 | Yes | string |
| pageIndex | query | 页索引 | Yes | integer |
| pageSize | query | 页大小 | Yes | integer |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«List«帖子实体»»](#统一响应实体«List«帖子实体»») |



#### 点赞

PUT: /api/table/support

##### 描述

点赞，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| tableId | query | 帖子id | Yes | integer |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 取消点赞

DELETE: /api/table/support

##### 描述

取消点赞，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| tableId | query | 帖子id | Yes | integer |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 获取单个帖子内容

GET: /api/table/table

##### 描述

获取单个帖子内容，返回单个帖子

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| id | query | 帖子id | Yes | integer |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«帖子实体»](#统一响应实体«帖子实体») |



### 账号操作

#### 通过邮箱注册

POST: /api/user/addByEmail

##### 描述

通过邮箱注册账号，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| email | query | 邮箱 | Yes | string |
| password | query | 密码 | Yes | string |
| name | query | 名称 | Yes | string |
| emailCode | query | 邮箱验证码 | Yes | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 通过微信注册

POST: /api/user/addByWeChat

##### 描述

通过微信注册账号，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| code | query | 临时登录凭证 | Yes | string |
| password | query | 密码 | Yes | string |
| name | query | 名称 | Yes | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 绑定邮箱

POST: /api/user/bindEmail

##### 描述

绑定微信,会覆盖之前绑定的微信，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| email | query | 邮箱 | Yes | string |
| emailCode | query | 邮箱验证码 | Yes | string |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 绑定微信

POST: /api/user/bindWeChatByCode

##### 描述

绑定微信,会覆盖之前绑定的微信，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| code | query | 临时登录凭证 | Yes | string |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 获取邮箱验证码

GET: /api/user/emailCode

##### 描述

获取邮箱验证码，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| email | query | 邮箱 | Yes | string |
| angle | query | 旋转验证码度数 | Yes | integer |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 是否通过微信注册过

GET: /api/user/isAddedByWeChat

##### 描述

是否通过微信注册过，返回true/false

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| code | query | 临时登录凭证 | Yes | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«boolean»](#统一响应实体«boolean») |



#### 登录

POST: /api/user/login

##### 描述

登录用户，返回token

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| id | query | 用户id或邮箱 | Yes | string |
| password | query | 密码 | Yes | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«string»](#统一响应实体«string») |



#### 微信登录

POST: /api/user/loginByWeChat

##### 描述

微信登录，返回token

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| code | query | 临时登录凭证 | Yes | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 退出登录

POST: /api/user/logout

##### 描述

退出登录，不返回内容

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«object»](#统一响应实体«object») |



#### 获取旋转验证码

GET: /api/user/rotateCode

##### 描述

获取旋转验证码，返回图片base64

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| email | query | 邮箱 | Yes | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«string»](#统一响应实体«string») |



#### 获取登录用户信息

GET: /api/user/user

##### 描述

获取登录用户信息，返回用户公开信息

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«用户实体»](#统一响应实体«用户实体») |



#### 获取用户公开信息

GET: /api/user/userPublic

##### 描述

获取用户公开信息，返回用户公开信息

##### 参数

| 参数名 | 参数位于 | 描述 | 必须 | 参数类型 |
| ---- | ---------- | ----------- | -------- | ---- |
| id | query | 用户id | Yes | integer |
| token | header | token | No | string |

##### 响应
| 状态码 | 描述 | 请求头 | 返回类型 |
| ---- | ----------- | ------ |------ |
| 200 | OK | application/json;charset=UTF-8 | [统一响应实体«用户公开信息»](#统一响应实体«用户公开信息») |



## 返回类型


#### 帖子实体

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| anonymous | boolean | 是否为匿名 | No |
| commentCount | integer | 评论数量 | No |
| content | string | 表白内容 | No |
| createTime | dateTime | 创建时间 | No |
| id | integer | 帖子id | No |
| images | string | 图片列表 | No |
| notifyEmail | boolean | 是否邮箱通知 | No |
| recipient | string | 被表白者姓名 | No |
| recipientSex | integer | 被表白者性别 | No |
| sender | string | 表白者姓名 | No |
| senderSex | integer | 表白者性别 | No |
| status | integer | 状态 0为正常 1为待审核 -1为封禁 | No |
| support | boolean | 是否点过赞 | No |
| supportCount | integer | 点赞数量 | No |
| userPublic | [用户公开信息](#用户公开信息) | 对应的用户公开信息 | No |



#### 用户公开信息

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| createTime | dateTime | 注册时间 | No |
| email | string | 邮箱 | No |
| id | integer | 账号id | No |
| lastTime | dateTime | 最近登录时间 | No |
| name | string | 名称 | No |
| studentId | string | 学号 | No |



#### 用户实体

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| createTime | dateTime | 注册时间 | No |
| email | string | 邮箱 | No |
| id | integer | 账号id | No |
| lastTime | dateTime | 最近登录时间 | No |
| name | string | 名称 | No |
| status | integer | 状态 0为正常 1为待审核 -1为封禁 | No |
| student_id | string | 学号 | No |



#### 统一响应实体«List«帖子实体»»

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| code | integer | 状态码 | Yes |
| data | [ [帖子实体](#帖子实体) ] | 数据 | No |
| message | string | 消息 | Yes |



#### 统一响应实体«List«评论实体»»

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| code | integer | 状态码 | Yes |
| data | [ [评论实体](#评论实体) ] | 数据 | No |
| message | string | 消息 | Yes |



#### 统一响应实体«boolean»

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| code | integer | 状态码 | Yes |
| data | boolean | 数据 | No |
| message | string | 消息 | Yes |



#### 统一响应实体«int»

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| code | integer | 状态码 | Yes |
| data | integer | 数据 | No |
| message | string | 消息 | Yes |



#### 统一响应实体«object»

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| code | integer | 状态码 | Yes |
| data | object | 数据 | No |
| message | string | 消息 | Yes |



#### 统一响应实体«string»

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| code | integer | 状态码 | Yes |
| data | string | 数据 | No |
| message | string | 消息 | Yes |



#### 统一响应实体«帖子实体»

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| code | integer | 状态码 | Yes |
| data | [帖子实体](#帖子实体) | 数据 | No |
| message | string | 消息 | Yes |



#### 统一响应实体«用户公开信息»

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| code | integer | 状态码 | Yes |
| data | [用户公开信息](#用户公开信息) | 数据 | No |
| message | string | 消息 | Yes |



#### 统一响应实体«用户实体»

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| code | integer | 状态码 | Yes |
| data | [用户实体](#用户实体) | 数据 | No |
| message | string | 消息 | Yes |



#### 评论实体

| 参数名 | 类型 | 描述 | 必须 |
| ---- | ---- | ----------- | -------- |
| content | string | 评论内容 | No |
| createTime | dateTime | 评论创建时间 | No |
| id | integer | 评论id | No |
| images | string | 图片列表 | No |
| status | integer | 状态 0为正常 1为待审核 -1为封禁 | No |
| tableId | integer | 帖子id | No |
| userId | integer | 评论者id | No |
| userPublic | [用户公开信息](#用户公开信息) | 对应的用户公开信息 | No |

