/*
SQLyog Ultimate v8.32 
MySQL - 5.5.27 : Database - djq-estate
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`djq-estate` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `djq-estate`;

/*Table structure for table `tb_activity` */

DROP TABLE IF EXISTS `tb_activity`;

CREATE TABLE `tb_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `title` varchar(100) NOT NULL COMMENT '活动标题',
  `address` varchar(200) NOT NULL COMMENT '活动地点',
  `organizer` varchar(100) NOT NULL COMMENT '举办单位',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '活动开始时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '活动截止时间',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态:0-活动有效（默认），1-活动过期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='活动表';

/*Table structure for table `tb_admin` */

DROP TABLE IF EXISTS `tb_admin`;

CREATE TABLE `tb_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `name` varchar(50) NOT NULL COMMENT '管理员名称',
  `password` varchar(50) NOT NULL COMMENT '密码 123-$apr1$200316$rlkozvkUbVvMiwvyHXOFg1',
  `repassword` varchar(50) DEFAULT NULL COMMENT '确认密码',
  `idcard` varchar(18) NOT NULL COMMENT '身份证号',
  `telephone` varchar(11) NOT NULL COMMENT '联系方式',
  `sex` char(1) NOT NULL DEFAULT '0' COMMENT '性别:0-男（默认），1-女',
  `remark` varchar(200) DEFAULT '无' COMMENT '备注（默认无）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '出生日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='管理员表';

/*Table structure for table `tb_building` */

DROP TABLE IF EXISTS `tb_building`;

CREATE TABLE `tb_building` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '楼栋ID',
  `name` varchar(50) NOT NULL COMMENT '栋数名称',
  `total_households` int(6) NOT NULL COMMENT '总户数',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='楼栋表';

/*Table structure for table `tb_car` */

DROP TABLE IF EXISTS `tb_car`;

CREATE TABLE `tb_car` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '车辆ID',
  `picture` varchar(100) DEFAULT NULL COMMENT '车辆照片',
  `owner_id` int(11) NOT NULL COMMENT '所属成员ID（业主）',
  `owner_name` varchar(50) NOT NULL COMMENT '所属成员姓名（业主）',
  `parking_id` int(11) NOT NULL DEFAULT '0' COMMENT '车位ID',
  `telephone` varchar(11) NOT NULL COMMENT '联系电话',
  `parking_code` varchar(20) NOT NULL DEFAULT '无' COMMENT '车位编号',
  `parking_total_fee` float NOT NULL COMMENT '车位费用',
  `color` varchar(10) NOT NULL COMMENT '车辆颜色',
  `car_number` varchar(20) NOT NULL COMMENT '车牌号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='车辆表';

/*Table structure for table `tb_charge` */

DROP TABLE IF EXISTS `tb_charge`;

CREATE TABLE `tb_charge` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收费明细ID',
  `detail` varchar(500) NOT NULL COMMENT '收费项目名称',
  `contractor` varchar(50) NOT NULL COMMENT '承办人名称',
  `owner_id` int(11) NOT NULL COMMENT '业主ID',
  `owner_name` varchar(50) NOT NULL COMMENT '业主姓名',
  `telephone` varchar(11) NOT NULL COMMENT '联系电话',
  `pay_money` float NOT NULL COMMENT '应收金额(￥)，单位分',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态：0-未缴费（默认），1-已缴费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='收费名细表';

/*Table structure for table `tb_complaint` */

DROP TABLE IF EXISTS `tb_complaint`;

CREATE TABLE `tb_complaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '投诉ID',
  `house_name` varchar(50) NOT NULL COMMENT '所属楼栋名称',
  `house_id` int(11) NOT NULL COMMENT '所属楼栋ID',
  `owner_id` int(11) NOT NULL COMMENT '投诉人员（业主）ID',
  `owner_name` varchar(40) NOT NULL COMMENT '投诉人员（业主）名称',
  `telephone` varchar(11) NOT NULL COMMENT '联系电话',
  `description` varchar(500) NOT NULL COMMENT '投诉具体描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态：0-待处理（默认）1-已处理',
  `result` varchar(500) DEFAULT NULL COMMENT '投诉结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='投诉表';

/*Table structure for table `tb_house` */

DROP TABLE IF EXISTS `tb_house`;

CREATE TABLE `tb_house` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '房屋ID',
  `building_name` varchar(50) NOT NULL COMMENT '所属栋数名称',
  `building_id` int(11) NOT NULL COMMENT '所属栋数ID',
  `code` varchar(50) NOT NULL COMMENT '房产编码',
  `name` varchar(50) NOT NULL COMMENT '房产名称',
  `room_num` varchar(3) NOT NULL COMMENT '房间号',
  `unit` varchar(10) NOT NULL COMMENT '单元',
  `floor` varchar(3) NOT NULL COMMENT '楼层',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `live_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入住时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='房屋表';

/*Table structure for table `tb_owner` */

DROP TABLE IF EXISTS `tb_owner`;

CREATE TABLE `tb_owner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '业主ID',
  `house_id` int(11) NOT NULL COMMENT '所属房产',
  `house_name` varchar(50) NOT NULL COMMENT '房产名称',
  `name` varchar(50) NOT NULL COMMENT '成员名称',
  `password` varchar(50) NOT NULL COMMENT '密码 123-$apr1$200316$rlkozvkUbVvMiwvyHXOFg1',
  `repassword` varchar(50) DEFAULT NULL COMMENT '确认密码',
  `idcard` varchar(18) NOT NULL COMMENT '身份证号',
  `telephone` varchar(11) NOT NULL COMMENT '联系方式',
  `sex` char(1) NOT NULL DEFAULT '0' COMMENT '性别:0-男（默认），1-女',
  `type` char(1) NOT NULL DEFAULT '0' COMMENT '类型:0-户主（默认），1-成员',
  `remark` varchar(200) DEFAULT '无' COMMENT '备注（默认无）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '出生日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='业主表';

/*Table structure for table `tb_parking` */

DROP TABLE IF EXISTS `tb_parking`;

CREATE TABLE `tb_parking` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '车位ID',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态:0-启用（默认），1-不启用',
  `code` varchar(20) NOT NULL COMMENT '车位编号',
  `total_fee` float NOT NULL COMMENT '总费用(￥)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='车位表';

/*Table structure for table `tb_repair` */

DROP TABLE IF EXISTS `tb_repair`;

CREATE TABLE `tb_repair` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '维修ID',
  `house_id` int(11) NOT NULL COMMENT '所属房产ID',
  `house_name` varchar(50) NOT NULL COMMENT '所属房产名称',
  `owner_name` varchar(50) NOT NULL COMMENT '报修人员（业主）名称',
  `owner_id` int(11) NOT NULL COMMENT '报修人员（业主）ID',
  `telephone` varchar(11) NOT NULL COMMENT '联系电话',
  `description` varchar(500) NOT NULL COMMENT '报修描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态：0-待处理（默认），1-已处理',
  `result` varchar(500) DEFAULT NULL COMMENT '报修结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='维修表';

/*Table structure for table `tb_root` */

DROP TABLE IF EXISTS `tb_root`;

CREATE TABLE `tb_root` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `name` varchar(50) NOT NULL COMMENT '超级管理员名称',
  `password` varchar(50) NOT NULL COMMENT '密码 123-$apr1$200316$rlkozvkUbVvMiwvyHXOFg1',
  `repassword` varchar(50) DEFAULT NULL COMMENT '确认密码',
  `idcard` varchar(18) NOT NULL COMMENT '身份证号',
  `telephone` varchar(11) NOT NULL COMMENT '联系方式',
  `sex` char(1) NOT NULL DEFAULT '0' COMMENT '性别:0-男（默认），1-女',
  `remark` varchar(200) DEFAULT '无' COMMENT '备注（默认无）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '出生日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='超级管理员表';

/*Table structure for table `tb_tradingstream` */

DROP TABLE IF EXISTS `tb_tradingstream`;

CREATE TABLE `tb_tradingstream` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '交易流水ID',
  `charge_id` int(11) NOT NULL COMMENT '收费项目ID',
  `owner_id` int(11) NOT NULL COMMENT '业主ID',
  `owner_name` varchar(50) NOT NULL COMMENT '业主姓名',
  `detail` varchar(500) NOT NULL COMMENT '具体明细',
  `telephone` varchar(11) NOT NULL COMMENT '联系电话',
  `pay_money` float NOT NULL COMMENT '所收金额(￥)，单位分',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='收费名细表';

/*Table structure for table `tb_worker` */

DROP TABLE IF EXISTS `tb_worker`;

CREATE TABLE `tb_worker` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '维修工ID',
  `name` varchar(50) NOT NULL COMMENT '维修工名称',
  `password` varchar(50) NOT NULL COMMENT '密码 123-$apr1$200316$rlkozvkUbVvMiwvyHXOFg1',
  `repassword` varchar(50) DEFAULT NULL COMMENT '确认密码',
  `idcard` varchar(18) NOT NULL COMMENT '身份证号',
  `telephone` varchar(11) NOT NULL COMMENT '联系方式',
  `sex` char(1) NOT NULL DEFAULT '0' COMMENT '性别:0-男（默认），1-女',
  `remark` varchar(200) DEFAULT '无' COMMENT '备注（默认无）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '出生日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='维修工表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
