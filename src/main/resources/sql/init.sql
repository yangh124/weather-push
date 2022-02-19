/*
 Navicat Premium Data Transfer

 Source Server         : zeda
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 47.114.72.36:13333
 Source Schema         : zdassistant_dev

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 19/02/2022 15:39:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fc_timing_category
-- ----------------------------
DROP TABLE IF EXISTS `fc_timing_category`;
CREATE TABLE `fc_timing_category` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `uuid` varchar(32) NOT NULL,
                                      `law_firm_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '律所(分所)id',
                                      `name` varchar(200) NOT NULL COMMENT '类型名称',
                                      `is_disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0-启用 1-禁用）',
                                      `create_time` datetime(6) NOT NULL COMMENT '创建时间',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `uniq_index` (`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='计时类型表';

SET FOREIGN_KEY_CHECKS = 1;
