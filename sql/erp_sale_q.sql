/*
Navicat MySQL Data Transfer

Source Server         : 虚拟机MySQl
Source Server Version : 50716
Source Host           : 192.168.150.128:3306
Source Database       : erp_sale_q

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-07-10 11:20:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for booking_guide
-- ----------------------------
DROP TABLE IF EXISTS `booking_guide`;
CREATE TABLE `booking_guide` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) DEFAULT NULL COMMENT '团id',
  `createuser_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `createuser_name` varchar(255) DEFAULT NULL COMMENT '创建人名称',
  `modifyuser_id` bigint(20) DEFAULT NULL COMMENT '最后一次修改人id',
  `modifyuser_name` varchar(255) DEFAULT NULL COMMENT '最后一次修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `guide_id` bigint(20) DEFAULT NULL,
  `guide_name` varchar(255) DEFAULT NULL COMMENT '导游名称',
  `mobile` varchar(255) DEFAULT NULL COMMENT '导游联系电话',
  `status` int(4) DEFAULT NULL COMMENT '1 正常 2 停用 3 删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='团、计调安排 导游 信息';

-- ----------------------------
-- Table structure for booking_supplier
-- ----------------------------
DROP TABLE IF EXISTS `booking_supplier`;
CREATE TABLE `booking_supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `createuser_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `createuser_name` varchar(255) DEFAULT NULL COMMENT '操作用户',
  `supplier_type` int(2) DEFAULT NULL COMMENT '供应商类型',
  `group_code` varchar(255) DEFAULT NULL COMMENT '拼团号',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商id company_poi.id',
  `supplier_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `item_id` bigint(20) DEFAULT NULL COMMENT 'supplier_item.id',
  `item_name` varchar(255) DEFAULT NULL COMMENT 'supplier_item.item_name',
  `item_date` datetime DEFAULT NULL COMMENT '日期',
  `item_price` decimal(8,4) DEFAULT NULL COMMENT '单价',
  `item_num` decimal(8,4) DEFAULT NULL COMMENT '预定的数量',
  `item_num_minus` decimal(8,4) DEFAULT NULL COMMENT '免减少',
  `item_total` decimal(18,4) DEFAULT NULL COMMENT '总价 = （item_num - item_num_minus）*item_price',
  `item_brief` varchar(255) DEFAULT NULL COMMENT '备注',
  `cash_type` int(4) DEFAULT NULL COMMENT '结算方式',
  `modifyuser_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `modifyuser_name` varchar(255) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL COMMENT '最后一次修改人',
  `guide_id` bigint(20) DEFAULT NULL COMMENT '导游id',
  `guide_name` varchar(255) DEFAULT NULL COMMENT '导游名称',
  `guide_total` decimal(18,4) DEFAULT NULL COMMENT '导游报账金额',
  `status` int(2) DEFAULT NULL COMMENT '状态 1 正常 2 停用 3删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计调管理 安排、房、餐、门票、其他';

-- ----------------------------
-- Table structure for qemot_comment
-- ----------------------------
DROP TABLE IF EXISTS `qemot_comment`;
CREATE TABLE `qemot_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emot_id` bigint(11) DEFAULT NULL,
  `tid` varchar(50) DEFAULT NULL,
  `uid` varchar(100) DEFAULT NULL,
  `muid` varchar(100) DEFAULT NULL,
  `mname` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4,
  `create_time2` varchar(50) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  `reply_num` varchar(3) DEFAULT NULL,
  `create_time1` varchar(50) DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qemot_comment_reply
-- ----------------------------
DROP TABLE IF EXISTS `qemot_comment_reply`;
CREATE TABLE `qemot_comment_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_id` bigint(20) DEFAULT NULL COMMENT '评论id',
  `uid` varchar(250) DEFAULT NULL COMMENT '评论者id',
  `content` text CHARACTER SET utf8mb4,
  `create_time0` varchar(50) DEFAULT NULL,
  `create_time2` varchar(50) DEFAULT NULL,
  `create_time3` varchar(35) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `source_name` varchar(255) DEFAULT NULL,
  `tid` varchar(50) DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qemot_info
-- ----------------------------
DROP TABLE IF EXISTS `qemot_info`;
CREATE TABLE `qemot_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `cmtnum` int(4) DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4,
  `createTime` varchar(35) DEFAULT NULL,
  `created_time` varchar(20) DEFAULT NULL,
  `editMask` varchar(35) DEFAULT NULL,
  `fwdnum` int(3) DEFAULT NULL,
  `name` varchar(300) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pictotal` int(4) DEFAULT NULL,
  `right` varchar(50) DEFAULT NULL,
  `rt_sum` varchar(10) DEFAULT NULL,
  `secret` varchar(10) DEFAULT NULL,
  `tid` varchar(100) DEFAULT NULL,
  `ugc_right` varchar(100) DEFAULT NULL,
  `uid` varchar(30) DEFAULT NULL,
  `wbid` varchar(100) DEFAULT NULL,
  `lbs_id` varchar(50) DEFAULT NULL,
  `lbs_idname` varchar(255) DEFAULT NULL,
  `lbs_name` varchar(255) DEFAULT NULL,
  `lbs_pos_x` varchar(30) DEFAULT NULL,
  `lbs_pos_y` varchar(30) DEFAULT NULL,
  `source_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qemot_pic
-- ----------------------------
DROP TABLE IF EXISTS `qemot_pic`;
CREATE TABLE `qemot_pic` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `tid` varchar(100) DEFAULT NULL,
  `emot_id` bigint(11) DEFAULT NULL,
  `uid` varchar(20) DEFAULT NULL,
  `height` varchar(6) DEFAULT NULL,
  `width` varchar(6) DEFAULT NULL,
  `url1` varchar(300) DEFAULT NULL,
  `url2` varchar(300) DEFAULT NULL,
  `url3` varchar(300) DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qmsg_info
-- ----------------------------
DROP TABLE IF EXISTS `qmsg_info`;
CREATE TABLE `qmsg_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msgid` varchar(20) DEFAULT NULL,
  `secret` varchar(20) DEFAULT NULL,
  `pasterid` varchar(50) DEFAULT NULL,
  `bmp` varchar(255) DEFAULT NULL,
  `pubtime` varchar(100) DEFAULT NULL,
  `modifytime` varchar(100) DEFAULT NULL,
  `effect` varchar(10) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `uin` varchar(50) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `capacity` varchar(255) DEFAULT NULL,
  `html_content` longtext,
  `uid` varchar(50) DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for qmsg_info_reply
-- ----------------------------
DROP TABLE IF EXISTS `qmsg_info_reply`;
CREATE TABLE `qmsg_info_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` mediumtext,
  `time` varchar(50) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `msg_id` bigint(20) DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for qphoto_img
-- ----------------------------
DROP TABLE IF EXISTS `qphoto_img`;
CREATE TABLE `qphoto_img` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(20) DEFAULT NULL,
  `browser` varchar(20) DEFAULT NULL,
  `cameratype` varchar(255) DEFAULT NULL,
  `cp_flag` varchar(20) DEFAULT NULL,
  `cp_x` varchar(20) DEFAULT NULL,
  `cp_y` varchar(20) DEFAULT NULL,
  `desc` text,
  `forum` varchar(20) DEFAULT NULL,
  `frameno` varchar(100) DEFAULT NULL,
  `height` varchar(20) DEFAULT NULL,
  `is_video` varchar(20) DEFAULT NULL,
  `modifytime` varchar(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `origin_url` text,
  `owner` varchar(50) DEFAULT NULL,
  `ownername` varchar(100) DEFAULT NULL,
  `photocubage` varchar(50) DEFAULT NULL,
  `phototype` varchar(25) DEFAULT NULL,
  `picrefer` varchar(20) DEFAULT NULL,
  `platformId` varchar(20) DEFAULT NULL,
  `platformSubId` varchar(20) DEFAULT NULL,
  `poiname` text,
  `pre` varchar(300) DEFAULT NULL,
  `rawshoottime` varchar(50) DEFAULT NULL,
  `uploadtime` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `width` varchar(20) DEFAULT NULL,
  `photo_id` bigint(20) DEFAULT NULL,
  `uid` varchar(20) DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for qphoto_info
-- ----------------------------
DROP TABLE IF EXISTS `qphoto_info`;
CREATE TABLE `qphoto_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `allow_access` varchar(20) DEFAULT NULL,
  `anonymity` varchar(20) DEFAULT NULL,
  `bitmap` varchar(50) DEFAULT NULL,
  `classid` varchar(20) DEFAULT NULL,
  `comment` varchar(20) DEFAULT NULL,
  `createtime` varchar(50) DEFAULT NULL,
  `desc` text,
  `handset` varchar(100) DEFAULT NULL,
  `topicid` varchar(255) DEFAULT NULL,
  `lastuploadtime` varchar(50) DEFAULT NULL,
  `modifytime` varchar(50) DEFAULT NULL,
  `name` text,
  `order` varchar(20) DEFAULT NULL,
  `pre` text,
  `priv` varchar(20) DEFAULT NULL,
  `totalnum` varchar(20) DEFAULT NULL,
  `viewtype` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `uid` varchar(20) DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for quser_info
-- ----------------------------
DROP TABLE IF EXISTS `quser_info`;
CREATE TABLE `quser_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL,
  `nickname` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `sex_type` varchar(5) DEFAULT NULL,
  `age` varchar(5) DEFAULT NULL,
  `birthyear` varchar(16) DEFAULT NULL,
  `birthday` varchar(16) DEFAULT NULL,
  `bloodtype` varchar(5) DEFAULT NULL,
  `country` varchar(25) DEFAULT NULL,
  `province` varchar(25) DEFAULT NULL,
  `city` varchar(25) DEFAULT NULL,
  `career` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `hc` varchar(30) DEFAULT NULL,
  `hp` varchar(30) DEFAULT NULL,
  `hco` varchar(30) DEFAULT NULL,
  `marriage` varchar(6) DEFAULT NULL,
  `msg_num` int(10) DEFAULT NULL,
  `emot_num` int(10) DEFAULT NULL COMMENT '说说num',
  `flag` int(2) DEFAULT NULL,
  `photo_num` int(10) DEFAULT NULL,
  `img_num` int(10) DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for quser_info_n
-- ----------------------------
DROP TABLE IF EXISTS `quser_info_n`;
CREATE TABLE `quser_info_n` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` bigint(15) DEFAULT NULL,
  `create_gmt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for supplier_item
-- ----------------------------
DROP TABLE IF EXISTS `supplier_item`;
CREATE TABLE `supplier_item` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商id company_poi.id',
  `item_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `item_seq` int(5) DEFAULT NULL COMMENT '序号',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
