/*
 Navicat Premium Data Transfer

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : 192.168.3.3:3306
 Source Schema         : weather

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 20/02/2022 14:05:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_holiday
-- ----------------------------
DROP TABLE IF EXISTS `sys_holiday`;
CREATE TABLE `sys_holiday` (
                               `id` bigint NOT NULL,
                               `holiday_name` varchar(50) NOT NULL DEFAULT '' COMMENT '节假日名称',
                               `holiday_date` date NOT NULL COMMENT '日期',
                               `is_off_day` tinyint(1) NOT NULL COMMENT '是否放假（0-补班 1-放假）',
                               `year` char(4) NOT NULL DEFAULT '' COMMENT '所在年份',
                               `ctime` timestamp NOT NULL COMMENT '创建时间',
                               `utime` timestamp NULL DEFAULT NULL COMMENT '修改时间',
                               `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-正常 1-删除',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='节假日';

-- ----------------------------
-- Records of sys_holiday
-- ----------------------------
BEGIN;
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440323325953, '元旦', '2022-01-01', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440457543682, '元旦', '2022-01-02', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440461737985, '元旦', '2022-01-03', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440465932289, '春节', '2022-01-29', 0, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440470126593, '春节', '2022-01-30', 0, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440478515201, '春节', '2022-01-31', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440482709506, '春节', '2022-02-01', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440486903810, '春节', '2022-02-02', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440491098114, '春节', '2022-02-03', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440495292417, '春节', '2022-02-04', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440499486722, '春节', '2022-02-05', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440499486723, '春节', '2022-02-06', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440503681026, '清明节', '2022-04-02', 0, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440507875329, '清明节', '2022-04-03', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440507875330, '清明节', '2022-04-04', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440512069633, '清明节', '2022-04-05', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440516263938, '劳动节', '2022-04-24', 0, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440520458242, '劳动节', '2022-04-30', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440524652546, '劳动节', '2022-05-01', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440524652547, '劳动节', '2022-05-02', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440528846850, '劳动节', '2022-05-03', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440533041153, '劳动节', '2022-05-04', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440533041154, '劳动节', '2022-05-07', 0, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440537235457, '端午节', '2022-06-03', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440541429762, '端午节', '2022-06-04', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440545624066, '端午节', '2022-06-05', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440545624067, '中秋节', '2022-09-10', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440549818370, '中秋节', '2022-09-11', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440549818371, '中秋节', '2022-09-12', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440554012673, '国庆节', '2022-10-01', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440554012674, '国庆节', '2022-10-02', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440558206977, '国庆节', '2022-10-03', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440558206978, '国庆节', '2022-10-04', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440562401282, '国庆节', '2022-10-05', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440562401283, '国庆节', '2022-10-06', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440566595586, '国庆节', '2022-10-07', 1, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440566595587, '国庆节', '2022-10-08', 0, '2022', '2022-02-20 11:50:27', NULL, 0);
INSERT INTO `sys_holiday` (`id`, `holiday_name`, `holiday_date`, `is_off_day`, `year`, `ctime`, `utime`, `is_delete`) VALUES (1495244440570789890, '国庆节', '2022-10-09', 0, '2022', '2022-02-20 11:50:27', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_tag
-- ----------------------------
DROP TABLE IF EXISTS `sys_tag`;
CREATE TABLE `sys_tag` (
                           `id` bigint NOT NULL,
                           `tag_id` int NOT NULL DEFAULT '0' COMMENT '企业微信tag_id（城市id）',
                           `tag_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '企业微信tag_name（城市name）',
                           `code` char(9) NOT NULL DEFAULT '' COMMENT '地区编码',
                           `ctime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                           `utime` timestamp NULL DEFAULT NULL COMMENT '修改时间',
                           `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-正常 1-删除',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='企业微信标签';

-- ----------------------------
-- Records of sys_tag
-- ----------------------------
BEGIN;
INSERT INTO `sys_tag` (`id`, `tag_id`, `tag_name`, `code`, `ctime`, `utime`, `is_delete`) VALUES (1495242150915371009, 1, '杭州', '101210109', '2022-02-20 11:41:23', NULL, 0);
INSERT INTO `sys_tag` (`id`, `tag_id`, `tag_name`, `code`, `ctime`, `utime`, `is_delete`) VALUES (1495242151020228609, 2, '开化', '101211003', '2022-02-20 11:41:23', NULL, 0);
INSERT INTO `sys_tag` (`id`, `tag_id`, `tag_name`, `code`, `ctime`, `utime`, `is_delete`) VALUES (1495242151024422914, 3, '嘉定', '101020500', '2022-02-20 11:41:23', NULL, 0);
INSERT INTO `sys_tag` (`id`, `tag_id`, `tag_name`, `code`, `ctime`, `utime`, `is_delete`) VALUES (1495242151024422915, 4, '椒江', '101210611', '2022-02-20 11:41:23', NULL, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
