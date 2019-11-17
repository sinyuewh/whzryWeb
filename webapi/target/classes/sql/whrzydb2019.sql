/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : whrzydb2019

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-11-03 11:20:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `addressbook`
-- ----------------------------
DROP TABLE IF EXISTS `addressbook`;
CREATE TABLE `addressbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `num` int(11) DEFAULT NULL,
  `sname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `kind` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `officeAddr` varchar(255) DEFAULT NULL,
  `officeTel` varchar(255) DEFAULT NULL,
  `remark` text,
  `workPlace` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5k8t7324rt2chwbdea51pjhh7` (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of addressbook
-- ----------------------------

-- ----------------------------
-- Table structure for `attachment`
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `fileDir` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `parentId` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `saveFileName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gyu4oljjfqua73ukwj6xx7g7t` (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `filedata`
-- ----------------------------
DROP TABLE IF EXISTS `filedata`;
CREATE TABLE `filedata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `fileKind` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `fileTitle` varchar(255) DEFAULT NULL,
  `filedir` varchar(255) DEFAULT NULL,
  `filekeys` varchar(255) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of filedata
-- ----------------------------

-- ----------------------------
-- Table structure for `filekind`
-- ----------------------------
DROP TABLE IF EXISTS `filekind`;
CREATE TABLE `filekind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `fileKind` varchar(255) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of filekind
-- ----------------------------

-- ----------------------------
-- Table structure for `infodata`
-- ----------------------------
DROP TABLE IF EXISTS `infodata`;
CREATE TABLE `infodata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `infoKind` varchar(255) DEFAULT NULL,
  `str1` varchar(255) DEFAULT NULL,
  `str10` varchar(255) DEFAULT NULL,
  `str11` varchar(255) DEFAULT NULL,
  `str12` varchar(255) DEFAULT NULL,
  `str13` varchar(255) DEFAULT NULL,
  `str14` varchar(255) DEFAULT NULL,
  `str15` varchar(255) DEFAULT NULL,
  `str16` varchar(255) DEFAULT NULL,
  `str17` varchar(255) DEFAULT NULL,
  `str18` varchar(255) DEFAULT NULL,
  `str19` varchar(255) DEFAULT NULL,
  `str2` varchar(255) DEFAULT NULL,
  `str20` varchar(255) DEFAULT NULL,
  `str21` varchar(255) DEFAULT NULL,
  `str22` varchar(255) DEFAULT NULL,
  `str23` varchar(255) DEFAULT NULL,
  `str24` varchar(255) DEFAULT NULL,
  `str25` varchar(255) DEFAULT NULL,
  `str26` varchar(255) DEFAULT NULL,
  `str27` varchar(255) DEFAULT NULL,
  `str28` varchar(255) DEFAULT NULL,
  `str29` varchar(255) DEFAULT NULL,
  `str3` varchar(255) DEFAULT NULL,
  `str30` varchar(255) DEFAULT NULL,
  `str4` varchar(255) DEFAULT NULL,
  `str5` varchar(255) DEFAULT NULL,
  `str6` varchar(255) DEFAULT NULL,
  `str7` varchar(255) DEFAULT NULL,
  `str8` varchar(255) DEFAULT NULL,
  `str9` varchar(255) DEFAULT NULL,
  `time1` datetime DEFAULT NULL,
  `time2` datetime DEFAULT NULL,
  `time3` datetime DEFAULT NULL,
  `time4` datetime DEFAULT NULL,
  `time5` datetime DEFAULT NULL,
  `txt1` text,
  `txt10` text,
  `txt2` text,
  `txt3` text,
  `txt4` text,
  `txt5` text,
  `txt6` text,
  `txt7` text,
  `txt8` text,
  `txt9` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sk80u9ktstjtsuncqkcssi5gb` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of infodata
-- ----------------------------
INSERT INTO `infodata` VALUES ('2', null, '2019-11-02 17:44:04', '034e7277f1d34879ac63fe0d7ff79a91', null, '2019-11-02 17:44:04', '0', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, '1', null, null, null, null, null, null, null, null, null, null, '1', null, '', '', '', '', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('3', null, '2019-11-02 17:44:16', '3d4f4b165a3b4fcfbaf72944933594c0', null, '2019-11-02 17:44:16', '0', '0', '1', '2', null, null, null, null, null, null, null, null, null, null, '2', null, null, null, null, null, null, null, null, null, null, '2', null, '2', '2', '2', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('4', null, '2019-11-02 17:45:06', 'b3bd2bc495c140e68dc2af1ba64a48f6', null, '2019-11-02 17:45:06', '0', '0', '1', '3', null, null, null, null, null, null, null, null, null, null, '3', null, null, null, null, null, null, null, null, null, null, '3', null, '3', '3', '3', '3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('5', null, '2019-11-02 18:03:37', '614042072c7345019c06b8a5b4208dec', null, '2019-11-02 18:03:37', '0', '0', '1', '3', null, null, null, null, null, null, null, null, null, null, '3', null, null, null, null, null, null, null, null, null, null, '3', null, '3', '3', '3', '3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('6', null, '2019-11-02 18:04:34', '2ee73d8b21284b97a02d4db6cf435ef6', null, '2019-11-02 18:04:34', '0', '0', '1', '3', null, null, null, null, null, null, null, null, null, null, '3', null, null, null, null, null, null, null, null, null, null, '3', null, '3', '3', '3', '3', '3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('7', null, '2019-11-02 18:07:37', '6bfcf03b35a2478497ff7985112fd4d9', null, '2019-11-02 18:07:37', '0', '0', '1', '区域1', null, null, null, null, null, null, null, null, null, null, '3', null, null, null, null, null, null, null, null, null, null, '3', null, '3', '3', '3', '3', '3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('19', null, '2019-11-02 20:22:16', 'f24df8c7f05b489aaf5c69bf95425d11', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称1', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('20', null, '2019-11-02 20:22:16', '90f1273ad78a40f48bdf8bd0e8ab24f8', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称2', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('21', null, '2019-11-02 20:22:16', '23de0268fb9a4d00b983f49967aefae0', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称3', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('22', null, '2019-11-02 20:22:16', '0b1e62f83cd549188e2d3b56f88aaf21', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称4', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('23', null, '2019-11-02 20:22:16', 'eb3a34c2c3e64b63904ec4de5c4ff629', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称5', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('24', null, '2019-11-02 20:22:16', 'bfc46ab8d23e4bb08ffb0feb5761e5e2', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称6', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('25', null, '2019-11-02 20:22:16', '774d31baf3d14ef78f698abc4f095c52', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称7', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('26', null, '2019-11-02 20:22:16', '4175c9b80cae4420bc943a0d3080f965', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称8', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('27', null, '2019-11-02 20:22:16', '4b48cd3eeb4946009bfdb3adfec7630b', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称9', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('28', null, '2019-11-02 20:22:16', '08f933e49b3c46b68518be2a5b966dfe', null, '2019-11-02 20:22:16', '0', '0', '1', '区域名称10', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `infodata` VALUES ('29', null, '2019-11-02 20:22:36', '3719b1d5b2f84b6ea32593217314d5d2', null, '2019-11-02 20:22:36', '0', '0', '1', '区域名称1', null, null, null, null, null, null, null, null, null, null, '特色领域1', null, null, null, null, null, null, null, null, null, null, '组织管理机构1', null, '基础条件1', '政策制度1', '推进情况1', '联系人1', '联系电话1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `orgs`
-- ----------------------------
DROP TABLE IF EXISTS `orgs`;
CREATE TABLE `orgs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `fax` varchar(10) DEFAULT NULL,
  `latitude` varchar(10) DEFAULT NULL,
  `linkMan` varchar(50) DEFAULT NULL,
  `linkTel` varchar(50) DEFAULT NULL,
  `longitude` varchar(10) DEFAULT NULL,
  `orgCode` varchar(50) DEFAULT NULL,
  `orgName` varchar(50) NOT NULL,
  `parentId` int(11) NOT NULL,
  `postCode` varchar(10) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jcd3y1iltig1wldme7e0x3ymk` (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of orgs
-- ----------------------------

-- ----------------------------
-- Table structure for `permissions`
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `parentId` int(11) NOT NULL,
  `permissionsId` varchar(50) NOT NULL,
  `permissionsName` varchar(50) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9byb1svenu9ftlfpir1gvqnim` (`permissionsId`),
  UNIQUE KEY `UK_ackmue6hx11yg61gp79qhnu4d` (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for `rolepermissions`
-- ----------------------------
DROP TABLE IF EXISTS `rolepermissions`;
CREATE TABLE `rolepermissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `permissionsId` varchar(50) NOT NULL,
  `roleName` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q4y0n55ulqmfi0rvfblcuhwo6` (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of rolepermissions
-- ----------------------------

-- ----------------------------
-- Table structure for `roles`
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `roleName` varchar(50) NOT NULL,
  `wbId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s54ajns0g53wb0pk08dqomvo4` (`roleName`),
  UNIQUE KEY `UK_enfixv3f96b4w8axesn9s1eg8` (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of roles
-- ----------------------------

-- ----------------------------
-- Table structure for `userroles`
-- ----------------------------
DROP TABLE IF EXISTS `userroles`;
CREATE TABLE `userroles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `roleName` varchar(50) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bdya3t06n2v3xb8tn5v2cj2jn` (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of userroles
-- ----------------------------

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createPerson` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `guid` varchar(50) DEFAULT NULL,
  `modifyPerson` varchar(20) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `loginCount` int(11) NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `paentId` int(11) NOT NULL,
  `password` varchar(50) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `roleName` varchar(255) DEFAULT NULL,
  `sex` int(11) NOT NULL,
  `signaturePicture` varchar(255) DEFAULT NULL,
  `userClass` int(11) NOT NULL,
  `userLoginId` varchar(50) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `userType` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_aywnhbkpk280os8ek8efua7af` (`userLoginId`),
  UNIQUE KEY `UK_t3jymapvd2j4p0e3rictxcfa7` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'jsj', null, '1', 'jsj', null, '0', '0', null, '0', '1397', '0', '35eed3c2a73ec9f9a8de3fae992d2951', null, null, null, '0', null, '0', 'admin', '管理员', '0');
