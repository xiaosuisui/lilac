/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : luma

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-10-04 22:16:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_basic_college
-- ----------------------------
DROP TABLE IF EXISTS `tb_basic_college`;
CREATE TABLE `tb_basic_college` (
  `id` char(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `baikeUrl` varchar(255) DEFAULT NULL,
  `gaokaoUrl` varchar(255) DEFAULT NULL,
  `subject` varchar(64) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `level` varchar(64) DEFAULT NULL,
  `feature` varchar(64) DEFAULT NULL,
  `postgraduate` char(64) DEFAULT NULL,
  `satisfaction` varchar(64) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `delFlag` char(64) DEFAULT NULL,
  `isEnable` char(64) DEFAULT NULL,
  `ext1` varchar(255) DEFAULT NULL,
  `ext2` varchar(255) DEFAULT NULL,
  `ext3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` char(64) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
SET FOREIGN_KEY_CHECKS=1;
