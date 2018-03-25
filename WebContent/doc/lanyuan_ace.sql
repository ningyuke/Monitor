/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50639
 Source Host           : localhost:3306
 Source Schema         : lanyuan_ace

 Target Server Type    : MySQL
 Target Server Version : 50639
 File Encoding         : 65001

 Date: 24/03/2018 22:23:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ly_buttom
-- ----------------------------
DROP TABLE IF EXISTS `ly_buttom`;
CREATE TABLE `ly_buttom`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `buttom` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_buttom
-- ----------------------------
INSERT INTO `ly_buttom` VALUES (1, 'Add', '<button type=\"button\" id=\"addFun\" class=\"btn btn-primary marR10\">Add</button>', '');
INSERT INTO `ly_buttom` VALUES (2, 'Edit', '<button type=\"button\" id=\"editFun\" class=\"btn btn-info marR10\">Edit</button>', NULL);
INSERT INTO `ly_buttom` VALUES (3, 'Delete', '<button type=\"button\" id=\"delFun\" class=\"btn btn-danger marR10\">Delete</button>', NULL);
INSERT INTO `ly_buttom` VALUES (4, 'Upload', '<button type=\"button\" id=\"upLoad\" class=\"btn btn-primary marR10\">Upload</button>', NULL);
INSERT INTO `ly_buttom` VALUES (5, 'Download', '<button type=\"button\" id=\"downLoad\" class=\"btn btn-primary marR10\">Download</button>', NULL);
INSERT INTO `ly_buttom` VALUES (6, 'Up', '<button type=\"button\" id=\"lyGridUp\" class=\"btn btn-success marR10\">Up</button>', NULL);
INSERT INTO `ly_buttom` VALUES (7, 'Down', '<button type=\"button\" id=\"lyGridDown\" class=\"btn btn btn-grey marR10\">Down</button>', NULL);
INSERT INTO `ly_buttom` VALUES (8, 'Assign Permissions', '<button type=\"button\" id=\"permissions\" class=\"btn btn btn-grey marR10\">Assign Permissions</button>', NULL);

-- ----------------------------
-- Table structure for ly_log
-- ----------------------------
DROP TABLE IF EXISTS `ly_log`;
CREATE TABLE `ly_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `module` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `methods` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `actionTime` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `userIP` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `operTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `description` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_log
-- ----------------------------
INSERT INTO `ly_log` VALUES (57, 'admin', 'System Management', 'User management-Add user', '10ms', '127.0.0.1', '2018-03-19 21:46:35', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (58, 'admin', 'Monitoring System', 'Real-time monitoring-Warning parameter setting', '9ms', '127.0.0.1', '2018-03-19 21:48:27', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (59, 'test', 'System Management', 'Role management-Add role', '20ms', '127.0.0.1', '2018-03-19 21:54:18', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (60, 'test', 'System Management', 'Role management-Modify role', '26ms', '127.0.0.1', '2018-03-19 21:54:34', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (61, 'test', 'System Management', 'User management-Modify user', '5ms', '127.0.0.1', '2018-03-19 21:54:44', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (62, 'admin', 'System Management', 'User management-Add user', '2ms', '127.0.0.1', '2018-03-19 21:56:18', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (63, 'admin', 'Monitoring System', 'Real-time monitoring-Warning parameter setting', '7ms', '127.0.0.1', '2018-03-19 21:57:08', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (64, 'admin', 'Monitoring System', 'Real-time monitoring-Warning parameter setting', '9ms', '127.0.0.1', '2018-03-19 21:57:34', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (65, 'admin', 'Monitoring System', 'Real-time monitoring-Warning parameter setting', '7ms', '127.0.0.1', '2018-03-19 21:57:52', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (66, 'admin', 'System Management', 'User management-Add user', '8ms', '127.0.0.1', '2018-03-19 22:09:04', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (67, 'admin', 'System Management', 'Role management-Modify role', '30ms', '127.0.0.1', '2018-03-19 22:30:59', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (68, 'test', 'System Management', 'User management-Add user', '7ms', '127.0.0.1', '2018-03-19 22:31:46', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (69, 'admin', 'System Management', 'Role management-Modify role', '48ms', '127.0.0.1', '2018-03-23 19:33:09', 'Excute successfully!');
INSERT INTO `ly_log` VALUES (70, 'admin', 'System Management', 'User management-Add user', '7ms', '127.0.0.1', '2018-03-23 19:33:49', 'Excute successfully!');

-- ----------------------------
-- Table structure for ly_param
-- ----------------------------
DROP TABLE IF EXISTS `ly_param`;
CREATE TABLE `ly_param`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `value` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updateTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_param
-- ----------------------------
INSERT INTO `ly_param` VALUES (1, 'cpu', '99', '2018-03-19 21:57:52');
INSERT INTO `ly_param` VALUES (2, 'ram', '99', '2018-03-13 01:03:15');
INSERT INTO `ly_param` VALUES (3, 'jvm', '100', '2018-03-08 22:37:45');
INSERT INTO `ly_param` VALUES (4, 'email', '332909637@qq.com', '2018-03-09 07:21:36');

-- ----------------------------
-- Table structure for ly_res_role
-- ----------------------------
DROP TABLE IF EXISTS `ly_res_role`;
CREATE TABLE `ly_res_role`  (
  `resId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`, `resId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_res_role
-- ----------------------------
INSERT INTO `ly_res_role` VALUES (1, 2);
INSERT INTO `ly_res_role` VALUES (2, 2);
INSERT INTO `ly_res_role` VALUES (3, 2);
INSERT INTO `ly_res_role` VALUES (5, 2);
INSERT INTO `ly_res_role` VALUES (6, 2);
INSERT INTO `ly_res_role` VALUES (7, 2);
INSERT INTO `ly_res_role` VALUES (8, 2);
INSERT INTO `ly_res_role` VALUES (9, 2);
INSERT INTO `ly_res_role` VALUES (10, 2);
INSERT INTO `ly_res_role` VALUES (11, 2);
INSERT INTO `ly_res_role` VALUES (25, 2);
INSERT INTO `ly_res_role` VALUES (26, 2);
INSERT INTO `ly_res_role` VALUES (27, 2);
INSERT INTO `ly_res_role` VALUES (28, 2);
INSERT INTO `ly_res_role` VALUES (32, 2);
INSERT INTO `ly_res_role` VALUES (33, 2);
INSERT INTO `ly_res_role` VALUES (40, 2);
INSERT INTO `ly_res_role` VALUES (25, 3);
INSERT INTO `ly_res_role` VALUES (26, 3);
INSERT INTO `ly_res_role` VALUES (27, 3);
INSERT INTO `ly_res_role` VALUES (28, 3);
INSERT INTO `ly_res_role` VALUES (32, 3);
INSERT INTO `ly_res_role` VALUES (33, 3);
INSERT INTO `ly_res_role` VALUES (40, 3);
INSERT INTO `ly_res_role` VALUES (25, 4);
INSERT INTO `ly_res_role` VALUES (26, 4);
INSERT INTO `ly_res_role` VALUES (27, 4);
INSERT INTO `ly_res_role` VALUES (28, 4);
INSERT INTO `ly_res_role` VALUES (32, 4);
INSERT INTO `ly_res_role` VALUES (33, 4);
INSERT INTO `ly_res_role` VALUES (40, 4);

-- ----------------------------
-- Table structure for ly_resources
-- ----------------------------
DROP TABLE IF EXISTS `ly_resources`;
CREATE TABLE `ly_resources`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `resKey` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `resUrl` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `level` int(4) DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `btn` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ishide` int(3) DEFAULT 0,
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_resources
-- ----------------------------
INSERT INTO `ly_resources` VALUES (1, 'Fundamental System', 0, 'system', '0', 'system', 6, 'fa-desktop', NULL, 0, 'Fundamental System');
INSERT INTO `ly_resources` VALUES (2, 'User Management', 1, 'account', '1', '/system/user/list.shtml', 7, NULL, NULL, 0, 'User Management');
INSERT INTO `ly_resources` VALUES (3, 'Role Management', 1, 'role', '1', '/system/role/list.shtml', 11, 'fa-list', NULL, 0, 'Role Management');
INSERT INTO `ly_resources` VALUES (5, 'Add User', 2, 'account_add', '2', '/system/user/add.shtml', 8, NULL, 'addAccount,addAccount,btn btn-primary marR10,Add', 0, '');
INSERT INTO `ly_resources` VALUES (6, 'Modify User', 2, 'account_edit', '2', '/system/user/edit.shtml', 9, NULL, 'editAccount,editAccount,btn btn-info marR10,Edit', 0, '');
INSERT INTO `ly_resources` VALUES (7, 'Delete User', 2, 'account_delete', '2', '/system/user/deleteByIds.shtml', 10, NULL, 'delAccount,delAccount,btn btn-danger marR10,Delete', 0, '');
INSERT INTO `ly_resources` VALUES (8, 'Add Role', 3, 'role_add', '2', '/system/role/add.shtml', 12, NULL, 'addrole,addrole,btn btn-primary marR10,Add', 0, '');
INSERT INTO `ly_resources` VALUES (9, 'Modify Role', 3, 'role_edit', '2', '/system/role/edit.shtml', 13, NULL, 'editrole,editrole,btn btn-info marR10,Edit', 0, '');
INSERT INTO `ly_resources` VALUES (10, 'Delete Role', 3, 'role_delete', '2', '/system/role/deleteByIds.shtml', 14, NULL, 'delrole,delrole,btn btn-danger marR10,Delete', 0, '');
INSERT INTO `ly_resources` VALUES (11, 'Role Permission', 3, 'role_perss', '2', '/resources/permissions.shtml', 15, '无', 'permissions,permissions,btn btn-grey marR10,Assign Permissions', 0, '');
INSERT INTO `ly_resources` VALUES (25, 'Login Information', 0, 'ly_login', '0', 'ly_login', 22, 'fa-calendar', NULL, 0, 'Login Information');
INSERT INTO `ly_resources` VALUES (26, 'User Login Record', 25, 'ly_log_list', '1', '/list.shtml?page_path=/system/userlogin/list', 23, NULL, NULL, 0, NULL);
INSERT INTO `ly_resources` VALUES (27, 'Operation Log ', 0, 'ly_log', '0', 'ly_log', 24, 'fa-picture-o', NULL, 0, 'Operation Log');
INSERT INTO `ly_resources` VALUES (28, 'Query Log', 27, 'ly_log', '1', '/list.shtml?page_path=/system/log/list', 25, NULL, 'downLoad,btn btn-info marR10,Download', 0, NULL);
INSERT INTO `ly_resources` VALUES (32, 'Monitoring System', 0, 'monitor', '0', 'monitor', 26, 'fa-tag', NULL, 0, 'Monitoring System');
INSERT INTO `ly_resources` VALUES (33, 'Real-time Monitoring', 32, 'sysmonitor', '1', '/system/monitor/monitor.shtml', 27, NULL, NULL, 0, 'Real-time Monitoring');
INSERT INTO `ly_resources` VALUES (40, 'Warning List', 32, 'monitor_warn', '1', '/system/monitor/list.shtml', 28, NULL, NULL, 0, 'Warning List');
INSERT INTO `ly_resources` VALUES (94, 'Add', 93, 'ckey_gg_addFun', '2', 'rfrf', 45, NULL, 'addFun,addFun,btn btn-primary marR10,Add', 0, NULL);
INSERT INTO `ly_resources` VALUES (95, 'Edit', 93, 'ckey_gg_editFun', '2', 'rfr', 46, NULL, 'editFun,editFun,btn btn-info marR10,Edit', 0, NULL);
INSERT INTO `ly_resources` VALUES (96, 'Delete', 93, 'ckey_gg_delFun', '2', 'rfr', 47, NULL, 'delFun,delFun,btn btn-danger marR10,Delete', 0, NULL);

-- ----------------------------
-- Table structure for ly_role
-- ----------------------------
DROP TABLE IF EXISTS `ly_role`;
CREATE TABLE `ly_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `state` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `roleKey` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_role
-- ----------------------------
INSERT INTO `ly_role` VALUES (2, '1', 'Administrator', 'admin', 'Mange system');
INSERT INTO `ly_role` VALUES (3, '1', 'Ordinary Role', 'simple', 'Ordinary role');
INSERT INTO `ly_role` VALUES (4, '1', 'Root', 'root', 'All permission');

-- ----------------------------
-- Table structure for ly_server_info
-- ----------------------------
DROP TABLE IF EXISTS `ly_server_info`;
CREATE TABLE `ly_server_info`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `threshold` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `currentValue` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `emailText` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `operTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_server_info
-- ----------------------------
INSERT INTO `ly_server_info` VALUES (5, 'cpu', '2%', '11%', 'Current CPU usage:11%, over preset value:2%, Please deal with it in time. ', '332909637@qq.com', '2018-03-19 21:57:43');
INSERT INTO `ly_server_info` VALUES (6, 'cpu', '2%', '6%', 'Current CPU usage:6%, over preset value:2%, Please deal with it in time. ', '332909637@qq.com', '2018-03-19 21:57:48');
INSERT INTO `ly_server_info` VALUES (7, 'cpu', '2%', '5%', 'Current CPU usage:5%, over preset value:2%, Please deal with it in time. ', '332909637@qq.com', '2018-03-19 21:57:51');

-- ----------------------------
-- Table structure for ly_user
-- ----------------------------
DROP TABLE IF EXISTS `ly_user`;
CREATE TABLE `ly_user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `accountName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `credentialsSalt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `locked` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0',
  `createTime` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `deletestatus` int(1) DEFAULT 0 COMMENT '逻辑删除状态0:存在1:删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_user
-- ----------------------------
INSERT INTO `ly_user` VALUES (1, 'admin', 'admin', '78e21a6eb88529eab722793a448ed394', '4157c3feef4a6ed91b2c28cf4392f2d1', 'Administrator', '1', '2018-03-19 22:31:25', 0);
INSERT INTO `ly_user` VALUES (31, 'test', 'test', '456133361002c613b6adc6b49b9c6c09', '42b4da04b94cdcbcb6dfc4ab08ceab40', NULL, '1', '2018-03-19 22:31:26', 0);
INSERT INTO `ly_user` VALUES (33, '123', '123', '8c722dd3e8a7b172ae94edea8c5bc344', 'df2582eb5d6a50c1411fc6db59140912', NULL, '1', '2018-03-23 19:33:49', 0);

-- ----------------------------
-- Table structure for ly_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ly_user_role`;
CREATE TABLE `ly_user_role`  (
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`userId`, `roleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_user_role
-- ----------------------------
INSERT INTO `ly_user_role` VALUES (1, 2);
INSERT INTO `ly_user_role` VALUES (26, 5);
INSERT INTO `ly_user_role` VALUES (31, 3);
INSERT INTO `ly_user_role` VALUES (33, 3);

-- ----------------------------
-- Table structure for ly_userlogin
-- ----------------------------
DROP TABLE IF EXISTS `ly_userlogin`;
CREATE TABLE `ly_userlogin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `accountName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `loginTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `loginIP` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ly_user_loginlist`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 252 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ly_userlogin
-- ----------------------------
INSERT INTO `ly_userlogin` VALUES (94, 1, 'admin', '2016-05-05 17:42:42', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (95, 1, 'admin', '2016-05-05 17:48:05', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (96, 1, 'admin', '2016-05-05 17:48:34', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (97, 1, 'admin', '2016-05-06 12:59:25', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (98, 1, 'admin', '2016-05-06 13:04:08', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (99, 1, 'admin', '2016-05-06 13:07:15', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (100, 1, 'admin', '2016-05-06 13:13:21', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (101, 1, 'admin', '2016-05-06 13:16:31', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (102, 1, 'admin', '2016-05-06 14:06:18', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (103, 1, 'admin', '2016-05-06 14:21:40', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (104, 1, 'admin', '2016-05-06 14:25:38', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (105, 1, 'admin', '2016-05-06 14:30:23', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (106, 1, 'admin', '2016-05-06 14:38:24', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (107, 1, 'admin', '2016-05-06 14:41:21', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (108, 1, 'admin', '2016-05-06 14:51:15', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (109, 1, 'admin', '2016-05-06 14:53:44', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (110, 1, 'admin', '2016-05-06 14:55:54', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (111, 1, 'admin', '2016-05-06 15:03:33', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (112, 1, 'admin', '2016-05-06 15:17:45', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (113, 1, 'admin', '2016-05-06 15:28:31', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (114, 1, 'admin', '2016-05-06 15:32:54', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (115, 1, 'admin', '2016-05-06 15:58:54', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (116, 1, 'admin', '2016-05-06 16:05:50', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (117, 1, 'admin', '2016-05-06 16:12:04', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (118, 1, 'admin', '2016-05-06 16:47:23', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (119, 1, 'admin', '2016-05-06 18:11:48', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (120, 1, 'admin', '2016-05-06 19:11:04', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (121, 1, 'admin', '2016-05-06 19:12:47', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (122, 1, 'admin', '2018-03-08 23:59:12', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (123, 1, 'admin', '2018-03-08 23:59:13', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (124, 1, 'admin', '2018-03-08 23:59:14', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (125, 1, 'admin', '2018-02-04 10:57:35', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (126, 1, 'admin', '2018-03-08 23:59:14', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (127, 1, 'admin', '2018-03-08 23:59:16', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (128, 1, 'admin', '2018-03-08 23:59:17', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (129, 1, 'admin', '2018-03-08 23:59:18', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (130, 1, 'admin', '2018-03-08 23:59:19', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (131, 1, 'admin', '2018-03-08 23:59:21', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (132, 1, 'admin', '2018-03-08 23:59:23', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (133, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (134, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (135, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (136, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (137, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (138, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (139, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (140, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (141, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (142, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (143, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (144, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (145, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (146, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (147, 23, 'sha', '2018-02-27 21:18:54', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (148, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (149, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (150, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (151, 1, 'admin', '2018-03-08 23:59:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (152, 1, 'admin', '2018-02-28 22:11:34', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (153, 1, 'admin', '2018-03-05 22:23:57', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (154, 1, 'admin', '2018-03-05 22:29:34', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (155, 1, 'admin', '2018-03-05 22:29:58', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (156, 1, 'admin', '2018-03-05 22:44:04', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (157, 1, 'admin', '2018-03-05 22:52:05', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (158, 1, 'admin', '2018-03-05 23:23:30', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (159, 1, 'admin', '2018-03-05 23:25:11', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (160, 1, 'admin', '2018-03-05 23:25:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (161, 1, 'admin', '2018-03-05 23:26:48', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (162, 1, 'admin', '2018-03-05 23:31:29', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (163, 1, 'admin', '2018-03-06 21:12:42', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (164, 1, 'admin', '2018-03-06 21:48:23', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (165, 1, 'admin', '2018-03-06 21:59:24', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (166, 1, 'admin', '2018-03-06 22:45:24', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (167, 1, 'admin', '2018-03-06 23:27:01', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (168, 1, 'admin', '2018-03-06 23:56:26', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (169, 1, 'admin', '2018-03-06 23:57:15', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (170, 1, 'admin', '2018-03-06 23:58:13', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (171, 1, 'admin', '2018-03-07 21:34:05', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (172, 1, 'admin', '2018-03-09 00:03:11', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (173, 1, 'admin', '2018-03-09 00:14:35', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (174, 1, 'admin', '2018-03-09 00:20:26', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (175, 1, 'admin', '2018-03-09 00:26:29', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (176, 1, 'admin', '2018-03-09 00:33:55', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (177, 1, 'admin', '2018-03-09 07:09:25', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (178, 1, 'admin', '2018-03-09 07:14:55', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (179, 1, 'admin', '2018-03-09 07:47:03', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (180, 1, 'admin', '2018-03-09 23:30:23', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (181, 1, 'admin', '2018-03-09 23:39:08', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (182, 1, 'admin', '2018-03-09 23:45:52', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (183, 1, 'admin', '2018-03-09 23:56:33', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (184, 1, 'admin', '2018-03-09 23:57:42', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (185, 24, 'jack', '2018-03-09 23:58:18', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (186, 1, 'admin', '2018-03-12 23:15:56', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (187, 1, 'admin', '2018-03-12 23:18:25', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (188, 1, 'admin', '2018-03-12 23:26:10', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (189, 1, 'admin', '2018-03-12 23:34:55', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (190, 1, 'admin', '2018-03-12 23:46:55', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (191, 1, 'admin', '2018-03-13 01:00:57', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (192, 1, 'admin', '2018-03-12 23:50:42', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (193, 1, 'admin', '2018-03-13 00:07:13', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (194, 1, 'admin', '2018-03-13 00:18:11', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (195, 1, 'admin', '2018-03-17 21:45:16', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (196, 1, 'admin', '2018-03-18 00:33:13', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (197, 1, 'admin', '2018-03-18 00:54:40', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (198, 1, 'admin', '2018-03-18 01:06:53', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (199, 1, 'admin', '2018-03-18 01:09:06', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (200, 1, 'admin', '2018-03-18 01:13:07', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (201, 1, 'admin', '2018-03-18 02:18:10', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (202, 1, 'admin', '2018-03-18 02:43:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (203, 1, 'admin', '2018-03-18 02:50:43', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (204, 1, 'admin', '2018-03-18 16:17:50', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (205, 1, 'admin', '2018-03-18 23:21:12', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (206, 1, 'admin', '2018-03-18 23:27:29', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (207, 1, 'admin', '2018-03-18 23:28:59', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (208, 1, 'admin', '2018-03-18 23:49:50', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (209, 1, 'admin', '2018-03-19 00:01:40', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (210, 1, 'admin', '2018-03-19 00:44:56', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (211, 1, 'admin', '2018-03-19 01:15:17', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (212, 1, 'admin', '2018-03-19 01:16:51', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (213, 1, 'admin', '2018-03-19 02:04:26', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (214, 1, 'admin', '2018-03-19 02:25:49', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (215, 1, 'admin', '2018-03-19 02:26:28', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (216, 1, 'admin', '2018-03-19 02:32:22', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (217, 1, 'admin', '2018-03-19 02:32:31', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (218, 26, 'test', '2018-03-19 02:33:33', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (219, 1, 'admin', '2018-03-19 03:20:20', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (220, 1, 'admin', '2018-03-19 14:07:53', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (221, 1, 'admin', '2018-03-19 14:34:58', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (222, 1, 'admin', '2018-03-19 14:35:22', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (223, 1, 'admin', '2018-03-19 14:43:55', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (224, 1, 'admin', '2018-03-19 20:27:18', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (225, 1, 'admin', '2018-03-19 20:34:01', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (226, 1, 'admin', '2018-03-19 20:55:19', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (227, 1, 'admin', '2018-03-19 20:55:33', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (228, 1, 'admin', '2018-03-19 20:57:03', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (229, 1, 'admin', '2018-03-19 20:59:41', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (230, 1, 'admin', '2018-03-19 21:12:14', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (231, 1, 'admin', '2018-03-19 21:17:49', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (232, 1, 'admin', '2018-03-19 21:29:57', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (233, 1, 'admin', '2018-03-19 21:31:38', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (234, 1, 'admin', '2018-03-19 21:32:48', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (235, 1, 'admin', '2018-03-19 21:46:14', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (236, 26, 'test', '2018-03-19 21:49:52', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (237, 1, 'admin', '2018-03-19 21:55:22', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (238, 1, 'admin', '2018-03-19 21:55:33', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (239, 1, 'admin', '2018-03-19 21:55:47', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (240, 1, 'admin', '2018-03-19 21:56:01', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (241, 1, 'admin', '2018-03-19 21:56:05', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (242, 1, 'admin', '2018-03-19 22:06:29', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (243, 1, 'admin', '2018-03-19 22:29:40', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (244, 1, 'admin', '2018-03-19 22:30:49', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (245, 31, 'test', '2018-03-19 22:31:13', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (246, 1, 'admin', '2018-03-23 19:29:25', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (247, 1, 'admin', '2018-03-23 19:30:24', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (248, 1, 'admin', '2018-03-23 19:33:00', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (249, 33, '123', '2018-03-23 19:33:54', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (250, 1, 'admin', '2018-03-24 00:35:13', '127.0.0.1');
INSERT INTO `ly_userlogin` VALUES (251, 1, 'admin', '2018-03-24 00:35:24', '127.0.0.1');

SET FOREIGN_KEY_CHECKS = 1;
