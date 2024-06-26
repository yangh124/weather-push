/*
 Navicat Premium Data Transfer

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : 127.0.0.1:3306
 Source Schema         : weather

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 04/05/2022 12:34:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
                                      `SCHED_NAME` varchar(120) NOT NULL,
                                      `TRIGGER_NAME` varchar(200) NOT NULL,
                                      `TRIGGER_GROUP` varchar(200) NOT NULL,
                                      `BLOB_DATA` blob,
                                      PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                      CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
                                  `SCHED_NAME` varchar(120) NOT NULL,
                                  `CALENDAR_NAME` varchar(200) NOT NULL,
                                  `CALENDAR` blob NOT NULL,
                                  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
                                      `SCHED_NAME` varchar(120) NOT NULL,
                                      `TRIGGER_NAME` varchar(200) NOT NULL,
                                      `TRIGGER_GROUP` varchar(200) NOT NULL,
                                      `CRON_EXPRESSION` varchar(200) NOT NULL,
                                      `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
                                      PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                      CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
                                       `SCHED_NAME` varchar(120) NOT NULL,
                                       `ENTRY_ID` varchar(95) NOT NULL,
                                       `TRIGGER_NAME` varchar(200) NOT NULL,
                                       `TRIGGER_GROUP` varchar(200) NOT NULL,
                                       `INSTANCE_NAME` varchar(200) NOT NULL,
                                       `FIRED_TIME` bigint NOT NULL,
                                       `SCHED_TIME` bigint NOT NULL,
                                       `PRIORITY` int NOT NULL,
                                       `STATE` varchar(16) NOT NULL,
                                       `JOB_NAME` varchar(200) DEFAULT NULL,
                                       `JOB_GROUP` varchar(200) DEFAULT NULL,
                                       `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
                                       `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
                                       PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
                                    `SCHED_NAME` varchar(120) NOT NULL,
                                    `JOB_NAME` varchar(200) NOT NULL,
                                    `JOB_GROUP` varchar(200) NOT NULL,
                                    `DESCRIPTION` varchar(250) DEFAULT NULL,
                                    `JOB_CLASS_NAME` varchar(250) NOT NULL,
                                    `IS_DURABLE` varchar(1) NOT NULL,
                                    `IS_NONCONCURRENT` varchar(1) NOT NULL,
                                    `IS_UPDATE_DATA` varchar(1) NOT NULL,
                                    `REQUESTS_RECOVERY` varchar(1) NOT NULL,
                                    `JOB_DATA` blob,
                                    PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
                              `SCHED_NAME` varchar(120) NOT NULL,
                              `LOCK_NAME` varchar(40) NOT NULL,
                              PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
                                            `SCHED_NAME` varchar(120) NOT NULL,
                                            `TRIGGER_GROUP` varchar(200) NOT NULL,
                                            PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
                                        `SCHED_NAME` varchar(120) NOT NULL,
                                        `INSTANCE_NAME` varchar(200) NOT NULL,
                                        `LAST_CHECKIN_TIME` bigint NOT NULL,
                                        `CHECKIN_INTERVAL` bigint NOT NULL,
                                        PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
                                        `SCHED_NAME` varchar(120) NOT NULL,
                                        `TRIGGER_NAME` varchar(200) NOT NULL,
                                        `TRIGGER_GROUP` varchar(200) NOT NULL,
                                        `REPEAT_COUNT` bigint NOT NULL,
                                        `REPEAT_INTERVAL` bigint NOT NULL,
                                        `TIMES_TRIGGERED` bigint NOT NULL,
                                        PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                        CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
                                         `SCHED_NAME` varchar(120) NOT NULL,
                                         `TRIGGER_NAME` varchar(200) NOT NULL,
                                         `TRIGGER_GROUP` varchar(200) NOT NULL,
                                         `STR_PROP_1` varchar(512) DEFAULT NULL,
                                         `STR_PROP_2` varchar(512) DEFAULT NULL,
                                         `STR_PROP_3` varchar(512) DEFAULT NULL,
                                         `INT_PROP_1` int DEFAULT NULL,
                                         `INT_PROP_2` int DEFAULT NULL,
                                         `LONG_PROP_1` bigint DEFAULT NULL,
                                         `LONG_PROP_2` bigint DEFAULT NULL,
                                         `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
                                         `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
                                         `BOOL_PROP_1` varchar(1) DEFAULT NULL,
                                         `BOOL_PROP_2` varchar(1) DEFAULT NULL,
                                         PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                         CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
                                 `SCHED_NAME` varchar(120) NOT NULL,
                                 `TRIGGER_NAME` varchar(200) NOT NULL,
                                 `TRIGGER_GROUP` varchar(200) NOT NULL,
                                 `JOB_NAME` varchar(200) NOT NULL,
                                 `JOB_GROUP` varchar(200) NOT NULL,
                                 `DESCRIPTION` varchar(250) DEFAULT NULL,
                                 `NEXT_FIRE_TIME` bigint DEFAULT NULL,
                                 `PREV_FIRE_TIME` bigint DEFAULT NULL,
                                 `PRIORITY` int DEFAULT NULL,
                                 `TRIGGER_STATE` varchar(16) NOT NULL,
                                 `TRIGGER_TYPE` varchar(8) NOT NULL,
                                 `START_TIME` bigint NOT NULL,
                                 `END_TIME` bigint DEFAULT NULL,
                                 `CALENDAR_NAME` varchar(200) DEFAULT NULL,
                                 `MISFIRE_INSTR` smallint DEFAULT NULL,
                                 `JOB_DATA` blob,
                                 PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                 KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
                                 CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `username`  varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                             `password`  varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                             `avatar`    varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         DEFAULT NULL COMMENT '头像',
                             `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                             `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
                             `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
                             `status`    int                                                          NOT NULL DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
                             `is_delete` tinyint(1)                                                   NOT NULL DEFAULT '0' COMMENT '0-正常 1-删除',
                             `ctime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                             `utime`     timestamp                                                    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                             PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT ='后台用户表';

-- ----------------------------
-- Table structure for sys_holiday
-- ----------------------------
DROP TABLE IF EXISTS `sys_holiday`;
CREATE TABLE `sys_holiday` (
                               `id` int NOT NULL AUTO_INCREMENT,
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
-- Table structure for sys_sch_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_sch_task`;
CREATE TABLE `sys_sch_task` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `task_name` varchar(50) NOT NULL COMMENT '任务名称',
                                `cron_exp`  varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'cron表达式',
                                `task_desc` varchar(255) DEFAULT NULL COMMENT '任务描述',
                                `status` tinyint NOT NULL DEFAULT '0' COMMENT '0-启动 1-暂停',
                                `is_delete` tinyint(1)                                                   NOT NULL DEFAULT '0' COMMENT '0-正常 1-删除',
                                `ctime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                `utime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='定时任务';

-- ----------------------------
-- Table structure for sys_location
-- ----------------------------
DROP TABLE IF EXISTS `sys_location`;
CREATE TABLE `sys_location`
(
    `id`            int                                                          NOT NULL AUTO_INCREMENT,
    `location_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '地区id',
    `code`          char(9)                                                      NOT NULL DEFAULT '' COMMENT '地区编码',
    `tag_id`        int                                                          NOT NULL COMMENT '企业微信标签id',
    `is_delete`     tinyint                                                      NOT NULL DEFAULT '0' COMMENT '0-正常 1-删除',
    `ctime`         timestamp                                                    NOT NULL COMMENT '创建时间',
    `utime`         timestamp                                                    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT ='地区信息';

-- ----------------------------
-- Table structure for sys_task_rel_location
-- ----------------------------
DROP TABLE IF EXISTS `sys_task_rel_location`;
CREATE TABLE `sys_task_rel_location`
(
    `id`          int        NOT NULL AUTO_INCREMENT,
    `task_id`     int        NOT NULL COMMENT '任务id',
    `location_id` int        NOT NULL COMMENT '地区id',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-正常 1-删除',
    `ctime`       timestamp  NULL     DEFAULT NULL COMMENT '创建时间',
    `utime`       timestamp  NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT ='任务关联地区';

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `sys_admin` (`username`, `password`, `avatar`, `ctime`)
VALUES ('admin', '$2a$10$uj25NbcLtPsHabSbkiQA4OzmjuFeocupzJdBkJyJH/EUKIoCiUOHq', '', NOW());
