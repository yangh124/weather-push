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

 Date: 26/02/2022 15:16:33
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
                               `utime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                               `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-正常 1-删除',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='节假日';

-- ----------------------------
-- Table structure for sys_tag
-- ----------------------------
CREATE TABLE `sys_tag` (
                           `id` bigint NOT NULL,
                           `tag_id` int NOT NULL DEFAULT '0' COMMENT '企业微信tag_id（城市id）',
                           `tag_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '企业微信tag_name（城市name）',
                           `code` char(9) NOT NULL DEFAULT '' COMMENT '地区编码',
                           `ctime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                           `utime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='企业微信标签';

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `sys_sch_task` (
                                `id` bigint NOT NULL,
                                `task_name` varchar(50) NOT NULL COMMENT '任务名称',
                                `cron_exp` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
                                `task_desc` varchar(255) DEFAULT NULL COMMENT '任务描述',
                                `ctime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                `utime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='定时任务';

-- ----------------------------
-- Records of sys_sch_task
-- ----------------------------
BEGIN;
INSERT INTO `sys_sch_task` (`id`, `task_name`, `cron_exp`, `task_desc`, `ctime`, `utime`) VALUES (1515617545057488898, 'scheduledTask1', '0 10 7 * * ?', '', '2022-04-17 17:05:56', '2022-04-17 17:22:04');
INSERT INTO `sys_sch_task` (`id`, `task_name`, `cron_exp`, `task_desc`, `ctime`, `utime`) VALUES (1515617635495071746, 'scheduledTask2', '0 5 8 * * ?', '', '2022-04-17 17:06:18', NULL);
INSERT INTO `sys_sch_task` (`id`, `task_name`, `cron_exp`, `task_desc`, `ctime`, `utime`) VALUES (1515617715220402177, 'scheduledTask3', '0 30 20 * * ?', '', '2022-04-17 17:06:37', NULL);
COMMIT;