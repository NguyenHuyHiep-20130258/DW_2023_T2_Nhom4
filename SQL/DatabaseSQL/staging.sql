/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 100427
 Source Host           : localhost:3306
 Source Schema         : staging

 Target Server Type    : MySQL
 Target Server Version : 100427
 File Encoding         : 65001

 Date: 05/12/2023 19:39:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lottery_result_staging
-- ----------------------------
DROP TABLE IF EXISTS `lottery_result_staging`;
CREATE TABLE `lottery_result_staging`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `prize` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `winning_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lottery_result_staging
-- ----------------------------
INSERT INTO `lottery_result_staging` VALUES (1, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 8', '68');
INSERT INTO `lottery_result_staging` VALUES (2, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 7', '884');
INSERT INTO `lottery_result_staging` VALUES (3, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 6', '2592');
INSERT INTO `lottery_result_staging` VALUES (4, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 6', '8387');
INSERT INTO `lottery_result_staging` VALUES (5, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 6', '2605');
INSERT INTO `lottery_result_staging` VALUES (6, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 5', '1157');
INSERT INTO `lottery_result_staging` VALUES (7, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 4', '46307');
INSERT INTO `lottery_result_staging` VALUES (8, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 4', '23330');
INSERT INTO `lottery_result_staging` VALUES (9, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 4', '68951');
INSERT INTO `lottery_result_staging` VALUES (10, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 4', '68771');
INSERT INTO `lottery_result_staging` VALUES (11, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 4', '65416');
INSERT INTO `lottery_result_staging` VALUES (12, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 4', '18164');
INSERT INTO `lottery_result_staging` VALUES (13, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 4', '51816');
INSERT INTO `lottery_result_staging` VALUES (14, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 3', '45024');
INSERT INTO `lottery_result_staging` VALUES (15, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 3', '38626');
INSERT INTO `lottery_result_staging` VALUES (16, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 2', '92912');
INSERT INTO `lottery_result_staging` VALUES (17, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải 1', '54285');
INSERT INTO `lottery_result_staging` VALUES (18, 'Miền Trung', 'Đắk Lắk', '2023-12-05', 'Giải ĐB', '915617');
INSERT INTO `lottery_result_staging` VALUES (19, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 8', '71');
INSERT INTO `lottery_result_staging` VALUES (20, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 7', '232');
INSERT INTO `lottery_result_staging` VALUES (21, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 6', '7520');
INSERT INTO `lottery_result_staging` VALUES (22, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 6', '5355');
INSERT INTO `lottery_result_staging` VALUES (23, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 6', '0254');
INSERT INTO `lottery_result_staging` VALUES (24, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 5', '6111');
INSERT INTO `lottery_result_staging` VALUES (25, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 4', '68940');
INSERT INTO `lottery_result_staging` VALUES (26, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 4', '78859');
INSERT INTO `lottery_result_staging` VALUES (27, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 4', '63202');
INSERT INTO `lottery_result_staging` VALUES (28, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 4', '63380');
INSERT INTO `lottery_result_staging` VALUES (29, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 4', '41038');
INSERT INTO `lottery_result_staging` VALUES (30, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 4', '58109');
INSERT INTO `lottery_result_staging` VALUES (31, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 4', '96364');
INSERT INTO `lottery_result_staging` VALUES (32, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 3', '15038');
INSERT INTO `lottery_result_staging` VALUES (33, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 3', '05598');
INSERT INTO `lottery_result_staging` VALUES (34, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 2', '14031');
INSERT INTO `lottery_result_staging` VALUES (35, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải 1', '15759');
INSERT INTO `lottery_result_staging` VALUES (36, 'Miền Trung', 'Quảng Nam', '2023-12-05', 'Giải ĐB', '784924');
INSERT INTO `lottery_result_staging` VALUES (37, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 8', '51');
INSERT INTO `lottery_result_staging` VALUES (38, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 7', '498');
INSERT INTO `lottery_result_staging` VALUES (39, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 6', '6753');
INSERT INTO `lottery_result_staging` VALUES (40, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 6', '3249');
INSERT INTO `lottery_result_staging` VALUES (41, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 6', '0477');
INSERT INTO `lottery_result_staging` VALUES (42, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 5', '6437');
INSERT INTO `lottery_result_staging` VALUES (43, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 4', '41519');
INSERT INTO `lottery_result_staging` VALUES (44, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 4', '97806');
INSERT INTO `lottery_result_staging` VALUES (45, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 4', '21418');
INSERT INTO `lottery_result_staging` VALUES (46, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 4', '59949');
INSERT INTO `lottery_result_staging` VALUES (47, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 4', '50991');
INSERT INTO `lottery_result_staging` VALUES (48, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 4', '44946');
INSERT INTO `lottery_result_staging` VALUES (49, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 4', '94796');
INSERT INTO `lottery_result_staging` VALUES (50, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 3', '67107');
INSERT INTO `lottery_result_staging` VALUES (51, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 3', '20027');
INSERT INTO `lottery_result_staging` VALUES (52, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 2', '65244');
INSERT INTO `lottery_result_staging` VALUES (53, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải 1', '11772');
INSERT INTO `lottery_result_staging` VALUES (54, 'Miền Nam', 'Bến Tre', '2023-12-05', 'Giải ĐB', '249968');
INSERT INTO `lottery_result_staging` VALUES (55, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 8', '44');
INSERT INTO `lottery_result_staging` VALUES (56, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 7', '536');
INSERT INTO `lottery_result_staging` VALUES (57, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 6', '1281');
INSERT INTO `lottery_result_staging` VALUES (58, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 6', '1955');
INSERT INTO `lottery_result_staging` VALUES (59, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 6', '3907');
INSERT INTO `lottery_result_staging` VALUES (60, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 5', '0798');
INSERT INTO `lottery_result_staging` VALUES (61, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 4', '86697');
INSERT INTO `lottery_result_staging` VALUES (62, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 4', '51490');
INSERT INTO `lottery_result_staging` VALUES (63, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 4', '70210');
INSERT INTO `lottery_result_staging` VALUES (64, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 4', '24365');
INSERT INTO `lottery_result_staging` VALUES (65, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 4', '90393');
INSERT INTO `lottery_result_staging` VALUES (66, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 4', '15983');
INSERT INTO `lottery_result_staging` VALUES (67, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 4', '60035');
INSERT INTO `lottery_result_staging` VALUES (68, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 3', '73067');
INSERT INTO `lottery_result_staging` VALUES (69, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 3', '84940');
INSERT INTO `lottery_result_staging` VALUES (70, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 2', '21435');
INSERT INTO `lottery_result_staging` VALUES (71, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải 1', '97678');
INSERT INTO `lottery_result_staging` VALUES (72, 'Miền Nam', 'Vũng Tàu', '2023-12-05', 'Giải ĐB', '772661');
INSERT INTO `lottery_result_staging` VALUES (73, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 8', '48');
INSERT INTO `lottery_result_staging` VALUES (74, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 7', '720');
INSERT INTO `lottery_result_staging` VALUES (75, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 6', '3518');
INSERT INTO `lottery_result_staging` VALUES (76, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 6', '0826');
INSERT INTO `lottery_result_staging` VALUES (77, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 6', '9339');
INSERT INTO `lottery_result_staging` VALUES (78, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 5', '8491');
INSERT INTO `lottery_result_staging` VALUES (79, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 4', '77573');
INSERT INTO `lottery_result_staging` VALUES (80, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 4', '73386');
INSERT INTO `lottery_result_staging` VALUES (81, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 4', '57625');
INSERT INTO `lottery_result_staging` VALUES (82, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 4', '94284');
INSERT INTO `lottery_result_staging` VALUES (83, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 4', '88136');
INSERT INTO `lottery_result_staging` VALUES (84, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 4', '73266');
INSERT INTO `lottery_result_staging` VALUES (85, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 4', '32316');
INSERT INTO `lottery_result_staging` VALUES (86, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 3', '91672');
INSERT INTO `lottery_result_staging` VALUES (87, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 3', '78975');
INSERT INTO `lottery_result_staging` VALUES (88, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 2', '83478');
INSERT INTO `lottery_result_staging` VALUES (89, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải 1', '02523');
INSERT INTO `lottery_result_staging` VALUES (90, 'Miền Nam', 'Bạc Liêu', '2023-12-05', 'Giải ĐB', '006152');
INSERT INTO `lottery_result_staging` VALUES (91, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải ĐB', '47521');
INSERT INTO `lottery_result_staging` VALUES (92, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 1', '07540');
INSERT INTO `lottery_result_staging` VALUES (93, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 2', '75731');
INSERT INTO `lottery_result_staging` VALUES (94, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 2', '73475');
INSERT INTO `lottery_result_staging` VALUES (95, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 3', '91273');
INSERT INTO `lottery_result_staging` VALUES (96, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 3', '55144');
INSERT INTO `lottery_result_staging` VALUES (97, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 3', '86736');
INSERT INTO `lottery_result_staging` VALUES (98, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 3', '99900');
INSERT INTO `lottery_result_staging` VALUES (99, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 3', '82341');
INSERT INTO `lottery_result_staging` VALUES (100, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 3', '11775');
INSERT INTO `lottery_result_staging` VALUES (101, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 4', '4831');
INSERT INTO `lottery_result_staging` VALUES (102, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 4', '7851');
INSERT INTO `lottery_result_staging` VALUES (103, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 4', '5581');
INSERT INTO `lottery_result_staging` VALUES (104, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 4', '2662');
INSERT INTO `lottery_result_staging` VALUES (105, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 5', '4138');
INSERT INTO `lottery_result_staging` VALUES (106, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 5', '8878');
INSERT INTO `lottery_result_staging` VALUES (107, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 5', '7384');
INSERT INTO `lottery_result_staging` VALUES (108, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 5', '1966');
INSERT INTO `lottery_result_staging` VALUES (109, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 5', '7257');
INSERT INTO `lottery_result_staging` VALUES (110, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 5', '5976');
INSERT INTO `lottery_result_staging` VALUES (111, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 6', '206');
INSERT INTO `lottery_result_staging` VALUES (112, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 6', '459');
INSERT INTO `lottery_result_staging` VALUES (113, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 6', '399');
INSERT INTO `lottery_result_staging` VALUES (114, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 7', '66');
INSERT INTO `lottery_result_staging` VALUES (115, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 7', '61');
INSERT INTO `lottery_result_staging` VALUES (116, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 7', '51');
INSERT INTO `lottery_result_staging` VALUES (117, 'Miền Bắc', 'Quảng Ninh', '2023-12-05', 'Giải 7', '07');

SET FOREIGN_KEY_CHECKS = 1;
