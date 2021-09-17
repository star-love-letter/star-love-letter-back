SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE IF NOT EXISTS `admin` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `type` tinyint(4) NOT NULL COMMENT '管理级别'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `c3p0TestTable` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(11) NOT NULL COMMENT '评论id',
  `table_id` int(11) DEFAULT NULL COMMENT '对应的帖子id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `content` varchar(160) NOT NULL COMMENT '评论内容',
  `images` varchar(255) DEFAULT NULL COMMENT '图像列表',
  `name` varchar(255) DEFAULT NULL COMMENT '匿名名称',
  `anonymous` bit(1) NOT NULL COMMENT '是否为匿名',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0为正常 1为待审核 -1为封禁'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `support` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `table_id` int(11) NOT NULL COMMENT '帖子id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `table` (
  `id` int(11) NOT NULL COMMENT '表白id',
  `sender` varchar(6) NOT NULL COMMENT '发送者',
  `sender_sex` bit(2) NOT NULL COMMENT '发送者性别',
  `recipient` varchar(6) NOT NULL COMMENT '被表白者',
  `recipient_sex` bit(2) NOT NULL COMMENT '被表白者性别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `content` varchar(160) NOT NULL COMMENT '表白内容',
  `images` varchar(255) DEFAULT NULL COMMENT '图像列表',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `anonymous` bit(1) NOT NULL COMMENT '是否为匿名',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0为正常 1为待审核 -1为封禁'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL,
  `email` char(30) NOT NULL COMMENT '邮箱',
  `password` char(255) NOT NULL COMMENT '密码',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `student_id` int(11) DEFAULT NULL COMMENT '学号',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `last_time` datetime NOT NULL COMMENT '最近登录时间',
  `token` char(255) DEFAULT NULL COMMENT 'token',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0为正常 1为待审核 -1为封禁'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `admin`
  ADD PRIMARY KEY (`user_id`) USING BTREE;

ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `table_id` (`table_id`),
  ADD KEY `user_id` (`user_id`);

ALTER TABLE `support`
  ADD PRIMARY KEY (`user_id`,`table_id`),
  ADD KEY `thumbs_table_id` (`table_id`);

ALTER TABLE `table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `table_user_id` (`user_id`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD UNIQUE KEY `user_mail` (`email`) USING BTREE,
  ADD UNIQUE KEY `user_student_id` (`student_id`) USING BTREE,
  ADD UNIQUE KEY `user_token` (`token`) USING BTREE;


ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id';
ALTER TABLE `table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表白id';
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `admin`
  ADD CONSTRAINT `admin_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `comment`
  ADD CONSTRAINT `comment_table_id` FOREIGN KEY (`table_id`) REFERENCES `table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `support`
  ADD CONSTRAINT `thumbs_table_id` FOREIGN KEY (`table_id`) REFERENCES `table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `thumbs_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `table`
  ADD CONSTRAINT `table_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
