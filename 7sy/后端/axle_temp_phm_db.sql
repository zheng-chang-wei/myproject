/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : axle_temp_phm_db

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-09-28 17:06:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_exception_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_exception_data`;

-- ----------------------------
-- Records of t_exception_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_initial_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_initial_data`;


-- ----------------------------
-- Records of t_initial_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '操作用户',
  `OPERATION` text COMMENT '操作内容',
  `TIME` decimal(11,0) DEFAULT NULL COMMENT '耗时',
  `METHOD` text COMMENT '操作方法',
  `PARAMS` text COMMENT '方法参数',
  `IP` varchar(64) DEFAULT NULL COMMENT '操作者IP',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `location` varchar(50) DEFAULT NULL COMMENT '操作地点',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `MENU_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `PARENT_ID` bigint(20) NOT NULL COMMENT '上级菜单ID',
  `MENU_NAME` varchar(50) NOT NULL COMMENT '菜单/按钮名称',
  `URL` varchar(100) DEFAULT NULL COMMENT '菜单URL',
  `PERMS` text COMMENT '权限标识',
  `ICON` varchar(50) DEFAULT NULL COMMENT '图标',
  `TYPE` char(2) NOT NULL COMMENT '类型 0菜单 1按钮',
  `ORDER_NUM` bigint(20) DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('33', '0', '轴温异常统计', '', '', 'el-icon-s-data', '0', null, '2019-03-23 15:32:39', '2019-05-28 18:41:12');
INSERT INTO `t_menu` VALUES ('34', '33', '车辆层级', '/statistics/statisticsByCar', '', '', '0', null, '2019-03-21 15:32:49', '2019-03-21 17:57:26');
INSERT INTO `t_menu` VALUES ('35', '33', '测点层级', '/statistics/statisticsByPoint', '', '', '0', null, '2019-03-21 15:33:00', '2019-03-21 17:57:38');
INSERT INTO `t_menu` VALUES ('40', '0', '系统管理', '', '', 'icon-system', '0', null, '2019-03-24 15:34:28', '2019-05-28 18:39:28');
INSERT INTO `t_menu` VALUES ('41', '40', '用户管理', '/system/user', '', '', '0', null, '2019-03-21 15:34:43', '2019-03-21 15:35:15');
INSERT INTO `t_menu` VALUES ('42', '41', '新增用户', '', '', '', '1', null, '2019-03-21 15:35:53', null);
INSERT INTO `t_menu` VALUES ('43', '41', '编辑用户', '', '', '', '1', null, '2019-03-21 15:36:16', null);
INSERT INTO `t_menu` VALUES ('44', '41', '删除用户', '', '', '', '1', null, '2019-03-21 15:36:30', null);
INSERT INTO `t_menu` VALUES ('45', '40', '角色管理', '/system/role', '', '', '0', null, '2019-03-21 15:36:58', '2019-03-21 15:37:13');
INSERT INTO `t_menu` VALUES ('46', '45', '新增角色', '', '', '', '1', null, '2019-03-21 15:37:40', null);
INSERT INTO `t_menu` VALUES ('47', '45', '修改角色', '', '', '', '1', null, '2019-03-21 15:37:56', null);
INSERT INTO `t_menu` VALUES ('48', '45', '删除角色', '', '', '', '1', null, '2019-03-21 15:38:09', null);
INSERT INTO `t_menu` VALUES ('49', '40', '菜单管理', '/system/menu', '', '', '0', null, '2019-03-21 15:38:33', null);
INSERT INTO `t_menu` VALUES ('50', '49', '新增菜单', '', '', '', '1', null, '2019-03-21 15:38:49', null);
INSERT INTO `t_menu` VALUES ('51', '49', '编辑菜单', '', '', '', '1', null, '2019-03-21 15:39:06', null);
INSERT INTO `t_menu` VALUES ('52', '49', '删除菜单', '', '', '', '1', null, '2019-03-21 15:39:18', null);
INSERT INTO `t_menu` VALUES ('53', '40', '日志管理', '/system/log', '', '', '0', null, '2019-04-01 22:34:16', null);
INSERT INTO `t_menu` VALUES ('55', '40', '在线用户', '/system/online', '', '', '0', null, '2019-04-01 22:50:51', null);
INSERT INTO `t_menu` VALUES ('56', '40', '平台数据管理', '/system/data', '', '', '0', null, '2019-04-02 16:52:51', null);

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `ROLE_NAME` varchar(100) NOT NULL COMMENT '角色名称',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', '超级管理员', '2017-12-27 16:23:11', '2019-03-11 19:22:34');
INSERT INTO `t_role` VALUES ('2', '管理员', '负责用户的增删改操作', '2018-01-09 15:32:41', '2019-03-11 19:23:14');
INSERT INTO `t_role` VALUES ('3', '普通用户', '查看用户，无相应操作权限', '2018-01-09 15:56:30', '2019-03-11 19:23:43');

-- ----------------------------
-- Table structure for `t_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `MENU_ID` bigint(20) NOT NULL COMMENT '菜单/按钮ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('3', '34');
INSERT INTO `t_role_menu` VALUES ('3', '35');
INSERT INTO `t_role_menu` VALUES ('3', '33');
INSERT INTO `t_role_menu` VALUES ('1', '34');
INSERT INTO `t_role_menu` VALUES ('1', '35');
INSERT INTO `t_role_menu` VALUES ('1', '42');
INSERT INTO `t_role_menu` VALUES ('1', '43');
INSERT INTO `t_role_menu` VALUES ('1', '44');
INSERT INTO `t_role_menu` VALUES ('1', '53');
INSERT INTO `t_role_menu` VALUES ('1', '55');
INSERT INTO `t_role_menu` VALUES ('1', '56');
INSERT INTO `t_role_menu` VALUES ('1', '33');
INSERT INTO `t_role_menu` VALUES ('1', '41');
INSERT INTO `t_role_menu` VALUES ('1', '40');
INSERT INTO `t_role_menu` VALUES ('2', '34');
INSERT INTO `t_role_menu` VALUES ('2', '35');
INSERT INTO `t_role_menu` VALUES ('2', '42');
INSERT INTO `t_role_menu` VALUES ('2', '43');
INSERT INTO `t_role_menu` VALUES ('2', '44');
INSERT INTO `t_role_menu` VALUES ('2', '53');
INSERT INTO `t_role_menu` VALUES ('2', '33');
INSERT INTO `t_role_menu` VALUES ('2', '41');
INSERT INTO `t_role_menu` VALUES ('2', '40');

-- ----------------------------
-- Table structure for `t_train_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_train_info`;
CREATE TABLE `t_train_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '机车ID',
  `train_num` varchar(20) DEFAULT NULL COMMENT '机车车号',
  `train_type` varchar(20) DEFAULT NULL COMMENT '机车车型',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_train_info
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `USERNAME` varchar(50) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(128) NOT NULL COMMENT '密码',
  `NAME` varchar(50) NOT NULL COMMENT '用户姓名',
  `ID_NUM` varchar(20) NOT NULL,
  `DEPT_NAME` varchar(128) NOT NULL,
  `PARENT_ID` bigint(20) NOT NULL COMMENT '创建者id',
  `PARENT_NAME` varchar(50) NOT NULL COMMENT '创建者名称',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', 'MTIzNDU2', 'aaaa', '123', 'a', '1', '11', '2019-02-21 12:02:55');
INSERT INTO `t_user` VALUES ('2', 'test', 'MTIzNDU2', '1', '1', '1', '1', 'aaaa', '2019-09-20 10:41:42');
INSERT INTO `t_user` VALUES ('3', 'xusheng', 'MTIzNDU2', 'xs', '23', 'reibd', '1', 'aaaa', '2019-09-20 13:36:19');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1');
INSERT INTO `t_user_role` VALUES ('2', '2');
INSERT INTO `t_user_role` VALUES ('3', '2');
