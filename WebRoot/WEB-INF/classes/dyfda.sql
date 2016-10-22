/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : dyfda

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-10-17 00:03:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bus_loaninfo
-- ----------------------------
DROP TABLE IF EXISTS `bus_loaninfo`;
CREATE TABLE `bus_loaninfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `surveyOrgName` varchar(100) DEFAULT NULL COMMENT '调查机构名称',
  `surveyPersonName` varchar(100) DEFAULT NULL COMMENT '调查人姓名',
  `surveyPhone` varchar(100) DEFAULT NULL COMMENT '调查人联系电话',
  `applicationName` varchar(100) DEFAULT NULL COMMENT '借款申请人姓名',
  `applicationAmount` varchar(100) DEFAULT NULL COMMENT '申请金额',
  `applicationTerm` varchar(100) DEFAULT NULL COMMENT '申请期限',
  `loanType` varchar(100) DEFAULT NULL COMMENT '申请贷款类型',
  `urgentCont` varchar(100) DEFAULT NULL COMMENT '紧急联系人',
  `urgentContPhone` varchar(100) DEFAULT NULL COMMENT '紧急联系人电话',
  `urgentContAddress` varchar(100) DEFAULT NULL COMMENT '紧急联系人地址',
  `relationship` varchar(100) DEFAULT NULL COMMENT '关系',
  `taobaoTreeDiamondMore` varchar(100) DEFAULT NULL COMMENT '淘宝三钻以上',
  `otherPlatform` varchar(100) DEFAULT NULL COMMENT '其他平台有营业执照',
  `operatingPeriodMore` varchar(100) DEFAULT NULL COMMENT '持续经营期限一年以上',
  `shopOwner` varchar(100) DEFAULT NULL COMMENT '是否网商店铺注册所有人',
  `haveGuarantor` varchar(100) DEFAULT NULL COMMENT '非网商店铺注册所有人，但追加所有人担保',
  `shopController` varchar(100) DEFAULT NULL COMMENT '是否网商店铺实际控制人',
  `salesOfMore` varchar(100) DEFAULT NULL COMMENT '近一年月均销售额10万以上',
  `than3credit` varchar(100) DEFAULT NULL COMMENT '授信合作金融机构（含我行）原则上不超过3家，最多不超过5家',
  `notOverdue` varchar(100) DEFAULT NULL COMMENT '借款企业、借款企业法人代表、实际控制人近2年内无经营性逾期、欠息记录',
  `perNotOverdue` varchar(100) DEFAULT NULL COMMENT '个人消费类贷款无累计5次或连续3次的逾期、欠息记录',
  `legalPerson` varchar(100) DEFAULT NULL COMMENT '法人姓名',
  `gender` varchar(100) DEFAULT NULL COMMENT '性别',
  `idCard` varchar(100) DEFAULT NULL COMMENT '身份证',
  `companyName` varchar(100) DEFAULT NULL COMMENT '公司名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bus_loaninfo
-- ----------------------------
INSERT INTO `bus_loaninfo` VALUES ('14', 'test', 'test', '13533201020', 'test', '10W', '1年', '个体工商户', '小红', '13533201021', '广州', '朋友', '是', '是', '是', '是', '否', '是', '是', '是', '是', '是', '小张', '男', '440785198906161122', '飞腾资讯');
INSERT INTO `bus_loaninfo` VALUES ('15', '小王', '王强', '13888888888', '小红', '10万', '12年', '小企业主', '张蔷', '13111111111', '江门', '姐妹', '否', '否', '是', '是', '是', '是', '是', '是', '是', '是', '小王', '男', '440785198906164477', '爱美化妆');

-- ----------------------------
-- Table structure for site_main
-- ----------------------------
DROP TABLE IF EXISTS `site_main`;
CREATE TABLE `site_main` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '网站简称',
  `domain` varchar(50) DEFAULT NULL COMMENT '域名 不带http://',
  `link` varchar(200) DEFAULT NULL COMMENT '网址连接 带http://',
  `state` int(1) DEFAULT NULL COMMENT '状态 0禁用 1可用 2审核中',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `pic` varchar(200) DEFAULT NULL COMMENT '图片URL',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '删除状态 0=未删除 1=删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of site_main
-- ----------------------------

-- ----------------------------
-- Table structure for site_type
-- ----------------------------
DROP TABLE IF EXISTS `site_type`;
CREATE TABLE `site_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id 主键',
  `name` varchar(20) NOT NULL COMMENT '分类名称',
  `code` varchar(10) DEFAULT NULL COMMENT 'code 英文和数字',
  `rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `descr` varchar(250) DEFAULT NULL COMMENT '描述',
  `state` int(1) DEFAULT NULL COMMENT '状态 0=可用,1=禁用',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of site_type
-- ----------------------------
INSERT INTO `site_type` VALUES ('1', '美女论坛', 'mnlt', '0', '美女论坛', '0', '2012-12-23 20:06:45', '2012-12-23 20:06:45');
INSERT INTO `site_type` VALUES ('2', '美女网站', 'mnwz', '0', '11', '0', '2012-12-23 20:30:39', '2013-01-06 19:05:45');

-- ----------------------------
-- Table structure for site_type_rel
-- ----------------------------
DROP TABLE IF EXISTS `site_type_rel`;
CREATE TABLE `site_type_rel` (
  `siteId` int(11) NOT NULL COMMENT '站点id 关联：site_main.id',
  `siteTypeId` int(11) NOT NULL COMMENT '站点分类id 关联：site_type.id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of site_type_rel
-- ----------------------------
INSERT INTO `site_type_rel` VALUES ('1', '2');
INSERT INTO `site_type_rel` VALUES ('1', '1');
INSERT INTO `site_type_rel` VALUES ('2', '1');
INSERT INTO `site_type_rel` VALUES ('2', '2');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(100) DEFAULT NULL COMMENT '系统url',
  `parentId` int(10) DEFAULT NULL COMMENT ' 父id 关联sys_menu.id',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0=未删除，1=已删除',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `actions` varchar(500) DEFAULT '0' COMMENT '注册Action 按钮|分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '商贷管理', '', null, '0', '2016-10-13 22:50:19', null, '1', '');
INSERT INTO `sys_menu` VALUES ('2', '菜单管理', '/sysMenu/menu.shtml', '14', '0', '2012-12-23 18:18:32', '2013-01-13 02:29:33', '0', 'dataList.do');
INSERT INTO `sys_menu` VALUES ('3', '站点管理', '', null, '1', '2012-12-23 20:26:35', '2012-12-23 21:16:51', '1', '');
INSERT INTO `sys_menu` VALUES ('4', '站点信息管理', '/siteMain/list.shtml', '3', '1', '2012-12-23 20:26:53', '2016-05-27 14:53:47', '0', 'dataList.do|/siteType/typeListJson.do');
INSERT INTO `sys_menu` VALUES ('5', '站点类型', '/siteType/list.shtml', '3', '1', '2012-12-23 20:28:23', '2016-05-27 14:52:32', '0', 'dataList.do');
INSERT INTO `sys_menu` VALUES ('6', '操作员管理', '/sysUser/list.shtml', '14', '0', '2012-12-23 22:15:33', '2016-10-16 19:47:07', '0', 'dataList.do');
INSERT INTO `sys_menu` VALUES ('7', '角色管理', '/sysRole/role.shtml', '14', '0', '2012-12-24 22:17:51', '2013-01-13 01:15:00', '0', 'dataList.do|/sysMenu/getMenuTree.do');
INSERT INTO `sys_menu` VALUES ('8', '操作员授权', '/sysUser/userRole.shtml', '14', '0', '2013-01-06 11:42:26', '2013-01-14 11:35:04', '0', 'userList.do|/sysRole/loadRoleList.do');
INSERT INTO `sys_menu` VALUES ('9', '测试菜单', '', null, '1', '2016-05-27 15:03:51', '2016-05-27 15:05:08', '1', '');
INSERT INTO `sys_menu` VALUES ('10', '子菜单测试', 'www.mn606.com', '9', '1', '2016-05-27 15:04:14', '2016-05-27 15:06:15', '2', '');
INSERT INTO `sys_menu` VALUES ('11', 'test20160623', 'x', '9', '0', '2016-06-23 11:48:48', '2016-06-23 11:48:59', '0', 'x');
INSERT INTO `sys_menu` VALUES ('12', '组织机构', '/sysOrg/org.shtml', '14', '0', '2016-07-11 13:41:02', '2016-07-12 16:44:31', '0', '');
INSERT INTO `sys_menu` VALUES ('13', '微信', '/wechat/wechatServer.do', '9', '0', '2016-09-28 23:06:18', '2016-09-28 23:23:19', '2', '1');
INSERT INTO `sys_menu` VALUES ('14', '系统管理', '', null, '0', '2012-12-23 17:21:58', '2013-01-10 22:30:50', '1', '');
INSERT INTO `sys_menu` VALUES ('15', '信息维护', '/sysOrg/org.shtml', null, '1', '2016-10-13 22:53:34', '2016-10-13 22:54:34', '0', 'add|read');
INSERT INTO `sys_menu` VALUES ('16', '信息维护', '/BusLoan/loan.shtml', '1', '0', '2016-10-13 22:55:04', '2016-10-16 19:46:10', '0', '');

-- ----------------------------
-- Table structure for sys_menu_btn
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_btn`;
CREATE TABLE `sys_menu_btn` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuid` int(11) NOT NULL COMMENT ' 菜单id关联 sys_menu.id',
  `btnName` varchar(30) DEFAULT NULL COMMENT '按钮名称',
  `btnType` varchar(30) DEFAULT NULL COMMENT '按钮类型，用于列表页显示的按钮',
  `actionUrls` varchar(250) DEFAULT NULL COMMENT 'url注册，用"," 分隔 。用于权限控制UR',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu_btn
-- ----------------------------
INSERT INTO `sys_menu_btn` VALUES ('5', '2', '添加', 'add', 'save.do');
INSERT INTO `sys_menu_btn` VALUES ('6', '2', '修改', 'edit', 'getId.do|save.do');
INSERT INTO `sys_menu_btn` VALUES ('7', '2', '删除', 'remove', 'delete.do');
INSERT INTO `sys_menu_btn` VALUES ('8', '6', '添加', 'add', 'save.do');
INSERT INTO `sys_menu_btn` VALUES ('9', '6', '修改', 'edit', 'getId.do|save.do');
INSERT INTO `sys_menu_btn` VALUES ('10', '6', '修改密码', 'editPwd', 'updatePwd.do');
INSERT INTO `sys_menu_btn` VALUES ('11', '6', '删除', 'remove', 'delete.do');
INSERT INTO `sys_menu_btn` VALUES ('12', '7', '添加', 'add', 'save.do');
INSERT INTO `sys_menu_btn` VALUES ('13', '7', '修改', 'edit', 'getId.do|save.do');
INSERT INTO `sys_menu_btn` VALUES ('14', '7', '删除', 'remove', 'delete.do');
INSERT INTO `sys_menu_btn` VALUES ('15', '8', '授权', 'authRole', '/sysUser/getUser.do|/sysUser/addUserRole.do');
INSERT INTO `sys_menu_btn` VALUES ('33', '11', '添加', 'add', 'save.do');
INSERT INTO `sys_menu_btn` VALUES ('34', '11', '修改', 'edit', 'getId.do|save.do');
INSERT INTO `sys_menu_btn` VALUES ('35', '11', '删除', 'remove', 'delete.do');
INSERT INTO `sys_menu_btn` VALUES ('39', '12', '添加', 'add', 'save.do');
INSERT INTO `sys_menu_btn` VALUES ('40', '12', '修改', 'edit', 'getId.do|save.do');
INSERT INTO `sys_menu_btn` VALUES ('41', '12', '删除', 'remove', 'delete.do');
INSERT INTO `sys_menu_btn` VALUES ('42', '12', '只读', 'read', 'dataList.do');
INSERT INTO `sys_menu_btn` VALUES ('43', '13', '添加', 'add', 'save.do');
INSERT INTO `sys_menu_btn` VALUES ('44', '13', '修改', 'edit', 'getId.do|save.do');
INSERT INTO `sys_menu_btn` VALUES ('45', '13', '删除', 'remove', 'delete.do');
INSERT INTO `sys_menu_btn` VALUES ('46', '13', '只读', 'read', 'dataList.do');
INSERT INTO `sys_menu_btn` VALUES ('47', '14', '添加', 'add', 'save.do');
INSERT INTO `sys_menu_btn` VALUES ('48', '14', '修改', 'edit', 'getId.do|save.do');
INSERT INTO `sys_menu_btn` VALUES ('49', '14', '删除', 'remove', 'delete.do');
INSERT INTO `sys_menu_btn` VALUES ('50', '14', '只读', 'read', 'dataList.do');
INSERT INTO `sys_menu_btn` VALUES ('55', '16', '添加', 'add', 'save.do');
INSERT INTO `sys_menu_btn` VALUES ('56', '16', '修改', 'edit', 'getId.do|save.do');
INSERT INTO `sys_menu_btn` VALUES ('57', '16', '删除', 'remove', 'delete.do');
INSERT INTO `sys_menu_btn` VALUES ('58', '16', '只读', 'read', 'dataList.do');
INSERT INTO `sys_menu_btn` VALUES ('59', '16', '生成文书', 'edit', 'createWords.do');

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `oId` varchar(40) COLLATE utf8_bin NOT NULL,
  `ciID` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `pId` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `no` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `memo` text COLLATE utf8_bin,
  `isChecked` smallint(6) DEFAULT NULL,
  `isLocked` smallint(6) DEFAULT NULL,
  `usId` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `uName` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `uDate` datetime DEFAULT NULL,
  `opId` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `oCode` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `level` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `headName` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `hPhone` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`oId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES ('12233', '32323', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_org` VALUES ('222', '223', '333', '233', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `roleName` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createBy` int(11) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateBy` int(11) DEFAULT NULL COMMENT '修改人',
  `state` int(1) DEFAULT NULL COMMENT '状态0=可用 1=禁用',
  `descr` varchar(200) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '2013-01-05 16:07:00', null, '2013-01-14 11:28:29', null, '0', null);
INSERT INTO `sys_role` VALUES ('3', '管理员', '2013-01-06 10:45:06', null, '2013-01-14 11:22:38', null, '0', null);
INSERT INTO `sys_role` VALUES ('18', '站点管理', '2013-01-13 01:21:46', null, '2013-01-13 01:21:54', null, '0', '站点管理');
INSERT INTO `sys_role` VALUES ('20', '普通用户', '2016-05-27 14:24:38', null, '2016-07-12 15:41:37', null, '0', null);
INSERT INTO `sys_role` VALUES ('22', '商贷管理员', '2016-10-16 19:30:04', null, '2016-10-16 21:53:17', null, '0', null);

-- ----------------------------
-- Table structure for sys_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_rel`;
CREATE TABLE `sys_role_rel` (
  `roleId` int(11) NOT NULL COMMENT '角色主键 sys_role.id',
  `objId` int(11) NOT NULL COMMENT '关联主键 type=0管理sys_menu.id, type=1关联sys_user.id',
  `relType` int(1) DEFAULT NULL COMMENT '关联类型 0=菜单,1=用户'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_rel
-- ----------------------------
INSERT INTO `sys_role_rel` VALUES ('1', '3', '1');
INSERT INTO `sys_role_rel` VALUES ('18', '19', '2');
INSERT INTO `sys_role_rel` VALUES ('18', '20', '2');
INSERT INTO `sys_role_rel` VALUES ('18', '16', '2');
INSERT INTO `sys_role_rel` VALUES ('18', '17', '2');
INSERT INTO `sys_role_rel` VALUES ('18', '5', '1');
INSERT INTO `sys_role_rel` VALUES ('3', '5', '1');
INSERT INTO `sys_role_rel` VALUES ('3', '8', '0');
INSERT INTO `sys_role_rel` VALUES ('3', '1', '0');
INSERT INTO `sys_role_rel` VALUES ('3', '2', '0');
INSERT INTO `sys_role_rel` VALUES ('3', '6', '0');
INSERT INTO `sys_role_rel` VALUES ('3', '7', '0');
INSERT INTO `sys_role_rel` VALUES ('3', '5', '2');
INSERT INTO `sys_role_rel` VALUES ('3', '8', '2');
INSERT INTO `sys_role_rel` VALUES ('3', '9', '2');
INSERT INTO `sys_role_rel` VALUES ('3', '12', '2');
INSERT INTO `sys_role_rel` VALUES ('3', '13', '2');
INSERT INTO `sys_role_rel` VALUES ('3', '15', '2');
INSERT INTO `sys_role_rel` VALUES ('1', '19', '2');
INSERT INTO `sys_role_rel` VALUES ('1', '20', '2');
INSERT INTO `sys_role_rel` VALUES ('1', '21', '2');
INSERT INTO `sys_role_rel` VALUES ('1', '16', '2');
INSERT INTO `sys_role_rel` VALUES ('1', '17', '2');
INSERT INTO `sys_role_rel` VALUES ('1', '18', '2');
INSERT INTO `sys_role_rel` VALUES ('18', '6', '1');
INSERT INTO `sys_role_rel` VALUES ('3', '6', '1');
INSERT INTO `sys_role_rel` VALUES ('1', '6', '1');
INSERT INTO `sys_role_rel` VALUES ('20', '1', '0');
INSERT INTO `sys_role_rel` VALUES ('20', '12', '0');
INSERT INTO `sys_role_rel` VALUES ('20', '42', '2');
INSERT INTO `sys_role_rel` VALUES ('20', '26', '2');
INSERT INTO `sys_role_rel` VALUES ('20', '16', '2');
INSERT INTO `sys_role_rel` VALUES ('20', '17', '2');
INSERT INTO `sys_role_rel` VALUES ('20', '22', '2');
INSERT INTO `sys_role_rel` VALUES ('20', '7', '1');
INSERT INTO `sys_role_rel` VALUES ('22', '12', '1');
INSERT INTO `sys_role_rel` VALUES ('22', '1', '0');
INSERT INTO `sys_role_rel` VALUES ('22', '16', '0');
INSERT INTO `sys_role_rel` VALUES ('22', '2', '0');
INSERT INTO `sys_role_rel` VALUES ('22', '6', '0');
INSERT INTO `sys_role_rel` VALUES ('22', '7', '0');
INSERT INTO `sys_role_rel` VALUES ('22', '8', '0');
INSERT INTO `sys_role_rel` VALUES ('22', '14', '0');
INSERT INTO `sys_role_rel` VALUES ('22', '55', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '56', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '57', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '58', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '59', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '5', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '6', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '7', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '8', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '9', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '10', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '11', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '12', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '13', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '14', '2');
INSERT INTO `sys_role_rel` VALUES ('22', '15', '2');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `email` varchar(50) NOT NULL COMMENT '邮箱也是登录帐号',
  `pwd` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `nickName` varchar(50) DEFAULT NULL COMMENT '昵称',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0=可用,1=禁用',
  `loginCount` int(11) DEFAULT NULL COMMENT '登录总次数',
  `loginTime` datetime DEFAULT NULL COMMENT '最后登录时间',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '删除状态 0=未删除,1=已删除',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `createBy` int(11) DEFAULT NULL COMMENT '创建人',
  `updateBy` int(11) DEFAULT NULL COMMENT '修改人',
  `superAdmin` int(1) NOT NULL DEFAULT '0' COMMENT '是否超级管理员 0= 不是，1=是',
  `kind` varchar(30) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `oId` varchar(40) DEFAULT NULL,
  `oName` varchar(100) DEFAULT NULL,
  `addr` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `memo` text,
  `roleName` varchar(100) DEFAULT NULL,
  `roleId` varchar(40) DEFAULT NULL,
  `pId` varchar(40) DEFAULT NULL,
  `pName` varchar(100) DEFAULT NULL,
  `uDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin@qq.com', 'C33367701511B4F6020EC61DED352059', '超级大Boss', '0', '299', '2016-10-16 21:52:44', '0', '2012-12-23 23:01:15', '2016-10-16 21:52:45', null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, '2016-10-16 21:52:45');
INSERT INTO `sys_user` VALUES ('3', '362217990@qq.com', 'E10ADC3949BA59ABBE56E057F20F883E', 'vowo', '0', '1', '2013-01-07 12:53:29', '0', '2012-12-23 23:17:39', '2013-01-13 03:33:41', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('5', 'wolf@qq.com', 'E10ADC3949BA59ABBE56E057F20F883E', '大灰狼', '0', '69', '2013-01-14 14:32:12', '0', '2013-01-07 12:30:10', '2013-01-14 14:32:12', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('6', 'youke@qq.com', null, ' 游客', '0', null, null, '0', '2013-01-13 03:41:32', '2013-01-13 03:41:32', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('7', 'xuanaw@163.com', '202CB962AC59075B964B07152D234B70', 'xuanaw', '0', '4', '2016-07-12 15:42:34', '0', '2016-05-27 14:23:10', '2016-07-12 15:42:34', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, '2016-07-12 15:42:34');
INSERT INTO `sys_user` VALUES ('8', 'xuanaw1@163.com', null, 'aw', '0', null, null, '0', '2016-06-15 17:22:17', '2016-06-15 17:22:17', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('9', 'test@test.com', '202CB962AC59075B964B07152D234B70', 'test2', '0', '2', '2016-06-28 11:44:58', '0', '2016-06-22 10:47:33', '2016-06-28 11:44:58', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('10', '123@123.com', null, '123', '0', null, null, '0', '2016-06-28 16:29:56', '2016-06-28 16:29:56', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('11', 'koala@163.com', null, 'koala', '0', null, null, '0', '2016-06-29 09:29:22', '2016-06-29 09:29:22', null, null, '0', '企业', '女', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('12', 'leader@163.com', 'E10ADC3949BA59ABBE56E057F20F883E', '商贷信息员', '0', '19', '2016-10-16 23:55:41', '0', '2016-10-16 19:26:33', '2016-10-16 23:55:41', null, null, '0', '企业', '男', null, null, null, null, null, null, null, null, null, null, '2016-10-16 23:55:41');
