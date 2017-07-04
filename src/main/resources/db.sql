-- 数据库
CREATE DATABASE qingta;

-- 创建表  用户表
DROP TABLE IF EXISTS `tab_user`;
CREATE TABLE `tab_user` (
  `id` varchar(45) NOT NULL,
  `account` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `orgId` varchar(45) DEFAULT NULL,
  `organization` varchar(45) DEFAULT NULL,
  `roles` varchar(10) DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 组织表
DROP TABLE IF EXISTS `tab_adminorg`;
CREATE TABLE `tab_adminorg` (
  `Id` varchar(44) NOT NULL COMMENT '注解id',
  `organization` varchar(80) NOT NULL COMMENT '组织名称',
  `parentOrgId` varchar(44)  COMMENT '上级组织id',
  `parentOrgName` varchar(80)  COMMENT '上级组织名称',
  `floor` int(10) DEFAULT NULL COMMENT '组织层级',
  `status` int(4) NOT NULL COMMENT '状态',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tab_article` (
  `id` varchar(44) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `local` varchar(255) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `time` varchar(500) DEFAULT NULL,
  `content` longtext,
  `source` varchar(1000) DEFAULT NULL,
  `vid` varchar(50) DEFAULT NULL,
  `date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `type` varchar(255) DEFAULT NULL,
  `isRelease` int(1) DEFAULT '0',
  `isInclude` int(1) DEFAULT '0',
  `isAdded` int(1) DEFAULT '0',
  `isInstructions` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tab_distribute` (
  `id` varchar(55) NOT NULL,
  `disContent` longtext,
  `instructionId` varchar(55) NOT NULL,
  `instructionUserId` varchar(55) NOT NULL,
  `disUserIds` varchar(500) NOT NULL,
  `attachmentPath` varchar(200) DEFAULT NULL,
  `isRead` int(11) NOT NULL DEFAULT '0',
  `createTime` date NOT NULL,
  `createUserId` varchar(55) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tab_feedback` (
  `id` varchar(55) NOT NULL,
  `instrutionId` varchar(55) NOT NULL,
  `content` longtext,
  `attachmentPath` varchar(200) DEFAULT NULL,
  `isRead` int(11) NOT NULL DEFAULT '0',
  `createUserId` varchar(55) NOT NULL,
  `createTime` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tab_instructions` (
  `id` varchar(55) NOT NULL,
  `articleId` varchar(55) NOT NULL,
  `content` longtext,
  `status` int(11) NOT NULL DEFAULT '1',
  `createUserId` varchar(45) NOT NULL,
  `createTime` date NOT NULL,
  `attachmentPath` varchar(200) DEFAULT NULL,
  `isRead` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
