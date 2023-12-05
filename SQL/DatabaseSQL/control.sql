/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 100427
 Source Host           : localhost:3306
 Source Schema         : control

 Target Server Type    : MySQL
 Target Server Version : 100427
 File Encoding         : 65001

 Date: 05/12/2023 19:38:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_file_configs
-- ----------------------------
DROP TABLE IF EXISTS `data_file_configs`;
CREATE TABLE `data_file_configs`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `source_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `format` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `flag` bit(1) NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_file_configs
-- ----------------------------
INSERT INTO `data_file_configs` VALUES (1, 'XSKT', 'Kết quả xổ số', 'https://xoso.com.vn/', 'E:\\WareHouse', '.xlsx', b'1', '2023-12-03 16:42:57', '9999-12-31 23:59:59');

-- ----------------------------
-- Table structure for data_files
-- ----------------------------
DROP TABLE IF EXISTS `data_files`;
CREATE TABLE `data_files`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `df_config_id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `file_timestamp` datetime NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_data_files_df_config`(`df_config_id` ASC) USING BTREE,
  CONSTRAINT `fk_data_files_df_config` FOREIGN KEY (`df_config_id`) REFERENCES `data_file_configs` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 509 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_files
-- ----------------------------
INSERT INTO `data_files` VALUES (1, 1, NULL, 'FINISHED', '2023-12-02 16:45:38', NULL, '2023-12-03 16:45:53', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (281, 1, 'XSKT_02_12_2023', 'CRAWLING', '2023-12-04 02:59:38', 'Data is crawling', '2023-12-04 02:59:38', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (282, 1, 'XSKT_02_12_2023', 'CRAWLING', '2023-12-04 03:00:41', 'Data is crawling', '2023-12-04 03:00:41', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (284, 1, 'XSKT_02_12_2023', 'CRAWLING', '2023-12-04 03:04:08', 'Data is crawling', '2023-12-04 03:04:08', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (285, 1, 'XSKT_02_12_2023', 'CRAWLED', '2023-12-04 03:04:15', 'Data is crawled', '2023-12-04 03:04:15', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (286, 1, 'XSKT_02_12_2023', 'EXTRACTING', '2023-12-04 03:04:15', 'Data is extracting', '2023-12-04 03:04:15', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (287, 1, 'XSKT_02_12_2023', 'EXTRACTED', '2023-12-04 03:04:15', 'Data is extracted', '2023-12-04 03:04:15', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (288, 1, 'XSKT_02_12_2023', 'Tranforming', '2023-12-04 03:04:15', 'Data is tranforming', '2023-12-04 03:04:15', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (289, 1, 'XSKT_02_12_2023', 'Tranformed', '2023-12-04 03:04:16', 'Data is tranformed', '2023-12-04 03:04:16', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (290, 1, 'XSKT_02_12_2023', 'LoadingWH', '2023-12-04 03:04:16', 'Data is loadingwh', '2023-12-04 03:04:16', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (291, 1, 'XSKT_02_12_2023', 'LoadedWH', '2023-12-04 03:04:16', 'Data is loadedwh', '2023-12-04 03:04:16', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (292, 1, 'XSKT_02_12_2023', 'Aggregating', '2023-12-04 03:04:16', 'Data is aggregating', '2023-12-04 03:04:16', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (293, 1, 'XSKT_02_12_2023', 'Aggregated', '2023-12-04 03:04:16', 'Data is aggregated', '2023-12-04 03:04:16', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (294, 1, 'XSKT_02_12_2023', 'LoadingM', '2023-12-04 03:04:16', 'Data is loadingm', '2023-12-04 03:04:16', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (295, 1, 'XSKT_02_12_2023', 'LoadedM', '2023-12-04 03:04:16', 'Data is loadedm', '2023-12-04 03:04:16', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (296, 1, 'XSKT_02_12_2023', 'FINISHED', '2023-12-04 03:04:16', 'Data is finished', '2023-12-04 03:04:16', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (415, 1, 'XSKT_04_12_2023', 'CRAWLING', '2023-12-04 12:20:17', 'Data is crawling', '2023-12-04 12:20:17', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (416, 1, 'XSKT_04_12_2023', 'ERROR', '2023-12-04 12:20:18', 'Fail to crawl data: org.jsoup.HttpStatusException: HTTP error fetching URL. Status=404, URL=[https://xoso.com.vn/404.html]', '2023-12-04 12:20:18', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (417, 1, 'XSKT_04_12_2023', 'ERROR', '2023-12-04 12:20:19', 'Fail to crawl data: org.jsoup.HttpStatusException: HTTP error fetching URL. Status=404, URL=[https://xoso.com.vn/404.html]', '2023-12-04 12:20:19', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (418, 1, 'XSKT_04_12_2023', 'ERROR', '2023-12-04 12:20:19', 'Fail to crawl data: org.jsoup.HttpStatusException: HTTP error fetching URL. Status=404, URL=[https://xoso.com.vn/404.html]', '2023-12-04 12:20:19', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (419, 1, 'XSKT_04_12_2023', 'CRAWLED', '2023-12-04 12:20:19', 'Data is crawled', '2023-12-04 12:20:19', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (420, 1, 'XSKT_04_12_2023', 'EXTRACTING', '2023-12-04 12:20:19', 'Data is extracting', '2023-12-04 12:20:19', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (421, 1, 'XSKT_04_12_2023', 'EXTRACTED', '2023-12-04 12:20:20', 'Data is extracted', '2023-12-04 12:20:20', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (422, 1, 'XSKT_04_12_2023', 'TRANSFORMING', '2023-12-04 12:20:20', 'Data is transforming', '2023-12-04 12:20:20', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (423, 1, 'XSKT_04_12_2023', 'TRANSFORMED', '2023-12-04 12:20:20', 'Data is transformed', '2023-12-04 12:20:20', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (424, 1, 'XSKT_04_12_2023', 'LOADINGWH', '2023-12-04 12:20:20', 'Data is loadingwh', '2023-12-04 12:20:20', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (425, 1, 'XSKT_04_12_2023', 'LOADEDWH', '2023-12-04 12:20:20', 'Data is loadedwh', '2023-12-04 12:20:20', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (426, 1, 'XSKT_04_12_2023', 'AGGREGATING', '2023-12-04 12:20:20', 'Data is aggregating', '2023-12-04 12:20:20', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (427, 1, 'XSKT_04_12_2023', 'AGGREGATED', '2023-12-04 12:20:20', 'Data is aggregated', '2023-12-04 12:20:20', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (428, 1, 'XSKT_04_12_2023', 'LOADINGM', '2023-12-04 12:20:20', 'Data is loadingm', '2023-12-04 12:20:20', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (429, 1, 'XSKT_04_12_2023', 'LOADEDM', '2023-12-04 12:20:21', 'Data is loadedm', '2023-12-04 12:20:21', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (430, 1, 'XSKT_04_12_2023', 'FINISHED', '2023-12-04 12:20:21', 'Data is finished', '2023-12-04 12:20:21', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (468, 1, 'XSKT_04_12_2023', 'CRAWLING', '2023-12-04 12:38:03', 'Data is crawling', '2023-12-04 12:38:03', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (469, 1, 'XSKT_04_12_2023', 'CRAWLED', '2023-12-04 12:38:09', 'Data is crawled', '2023-12-04 12:38:09', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (470, 1, 'XSKT_04_12_2023', 'EXTRACTING', '2023-12-04 12:38:09', 'Data is extracting', '2023-12-04 12:38:09', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (471, 1, 'XSKT_04_12_2023', 'ERROR', '2023-12-04 12:38:09', 'Fail to load to staging', '2023-12-04 12:38:09', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (472, 1, 'XSKT_04_12_2023', 'EXTRACTING', '2023-12-04 12:41:41', 'Data is extracting', '2023-12-04 12:41:41', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (473, 1, 'XSKT_04_12_2023', 'ERROR', '2023-12-04 12:41:41', 'Fail to load to stagingjava.sql.SQLSyntaxErrorException: PROCEDURE control.LoadToStaging1 does not exist', '2023-12-04 12:41:41', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (474, 1, 'XSKT_04_12_2023', 'EXTRACTING', '2023-12-04 12:45:27', 'Data is extracting', '2023-12-04 12:45:27', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (475, 1, 'XSKT_04_12_2023', 'EXTRACTED', '2023-12-04 12:45:28', 'Data is extracted', '2023-12-04 12:45:28', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (476, 1, 'XSKT_04_12_2023', 'TRANSFORMING', '2023-12-04 12:45:28', 'Data is transforming', '2023-12-04 12:45:28', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (477, 1, 'XSKT_04_12_2023', 'ERROR', '2023-12-04 12:45:28', 'Fail to tranform: java.sql.SQLSyntaxErrorException: PROCEDURE control.TranferToFact1 does not exist', '2023-12-04 12:45:28', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (478, 1, 'XSKT_04_12_2023', 'TRANSFORMING', '2023-12-04 12:46:45', 'Data is transforming', '2023-12-04 12:46:45', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (479, 1, 'XSKT_04_12_2023', 'TRANSFORMED', '2023-12-04 12:46:45', 'Data is transformed', '2023-12-04 12:46:45', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (480, 1, 'XSKT_04_12_2023', 'LOADINGWH', '2023-12-04 12:46:45', 'Data is loadingwh', '2023-12-04 12:46:45', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (481, 1, 'XSKT_04_12_2023', 'LOADEDWH', '2023-12-04 12:46:45', 'Data is loadedwh', '2023-12-04 12:46:45', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (482, 1, 'XSKT_04_12_2023', 'AGGREGATING', '2023-12-04 12:46:45', 'Data is aggregating', '2023-12-04 12:46:45', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (483, 1, 'XSKT_04_12_2023', 'ERROR', '2023-12-04 12:46:45', 'Fail to aggregate data: java.sql.SQLSyntaxErrorException: PROCEDURE control.AggregateData1 does not exist', '2023-12-04 12:46:45', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (484, 1, 'XSKT_04_12_2023', 'AGGREGATING', '2023-12-04 12:47:28', 'Data is aggregating', '2023-12-04 12:47:28', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (485, 1, 'XSKT_04_12_2023', 'AGGREGATED', '2023-12-04 12:47:28', 'Data is aggregated', '2023-12-04 12:47:28', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (486, 1, 'XSKT_04_12_2023', 'LOADINGM', '2023-12-04 12:47:28', 'Data is loadingm', '2023-12-04 12:47:28', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (487, 1, 'XSKT_04_12_2023', 'ERROR', '2023-12-04 12:47:28', 'Fail to load to mart: java.sql.SQLSyntaxErrorException: PROCEDURE control.LoadToMart1 does not exist', '2023-12-04 12:47:28', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (488, 1, 'XSKT_04_12_2023', 'LOADINGM', '2023-12-04 12:47:42', 'Data is loadingm', '2023-12-04 12:47:42', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (489, 1, 'XSKT_04_12_2023', 'LOADEDM', '2023-12-04 12:47:42', 'Data is loadedm', '2023-12-04 12:47:42', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (490, 1, 'XSKT_04_12_2023', 'FINISHED', '2023-12-04 12:47:42', 'Data is finished', '2023-12-04 12:47:42', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (491, 1, 'XSKT_05_12_2023', 'CRAWLING', '2023-12-05 19:34:10', 'Data is crawling', '2023-12-05 19:34:10', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (492, 1, 'XSKT_05_12_2023', 'CRAWLING', '2023-12-05 19:34:31', 'Data is crawling', '2023-12-05 19:34:31', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (493, 1, 'XSKT_05_12_2023', 'CRAWLED', '2023-12-05 19:34:37', 'Data is crawled', '2023-12-05 19:34:37', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (494, 1, 'XSKT_05_12_2023', 'EXTRACTING', '2023-12-05 19:34:37', 'Data is extracting', '2023-12-05 19:34:37', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (495, 1, 'XSKT_05_12_2023', 'EXTRACTED', '2023-12-05 19:34:37', 'Data is extracted', '2023-12-05 19:34:37', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (496, 1, 'XSKT_05_12_2023', 'TRANSFORMING', '2023-12-05 19:34:37', 'Data is transforming', '2023-12-05 19:34:37', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (497, 1, 'XSKT_05_12_2023', 'TRANSFORMED', '2023-12-05 19:34:37', 'Data is transformed', '2023-12-05 19:34:37', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (498, 1, 'XSKT_05_12_2023', 'LOADINGWH', '2023-12-05 19:34:37', 'Data is loadingwh', '2023-12-05 19:34:37', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (499, 1, 'XSKT_05_12_2023', 'LOADEDWH', '2023-12-05 19:34:37', 'Data is loadedwh', '2023-12-05 19:34:37', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (500, 1, 'XSKT_05_12_2023', 'AGGREGATING', '2023-12-05 19:34:37', 'Data is aggregating', '2023-12-05 19:34:37', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (501, 1, 'XSKT_05_12_2023', 'ERROR', '2023-12-05 19:34:38', 'Fail to aggregate data: java.sql.SQLSyntaxErrorException: Unknown column \'date\' in \'where clause\'', '2023-12-05 19:34:38', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (502, 1, 'XSKT_05_12_2023', 'AGGREGATING', '2023-12-05 19:36:51', 'Data is aggregating', '2023-12-05 19:36:51', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (503, 1, 'XSKT_05_12_2023', 'ERROR', '2023-12-05 19:36:51', 'Fail to aggregate data: java.sql.SQLSyntaxErrorException: PROCEDURE control.AggregateData does not exist', '2023-12-05 19:36:51', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (504, 1, 'XSKT_05_12_2023', 'AGGREGATING', '2023-12-05 19:37:03', 'Data is aggregating', '2023-12-05 19:37:03', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (505, 1, 'XSKT_05_12_2023', 'AGGREGATED', '2023-12-05 19:37:03', 'Data is aggregated', '2023-12-05 19:37:03', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (506, 1, 'XSKT_05_12_2023', 'LOADINGM', '2023-12-05 19:37:03', 'Data is loadingm', '2023-12-05 19:37:03', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (507, 1, 'XSKT_05_12_2023', 'LOADEDM', '2023-12-05 19:37:03', 'Data is loadedm', '2023-12-05 19:37:03', '9999-12-31 23:59:59');
INSERT INTO `data_files` VALUES (508, 1, 'XSKT_05_12_2023', 'FINISHED', '2023-12-05 19:37:03', 'Data is finished', '2023-12-05 19:37:03', '9999-12-31 23:59:59');

-- ----------------------------
-- Procedure structure for AggregateData
-- ----------------------------
DROP PROCEDURE IF EXISTS `AggregateData`;
delimiter ;;
CREATE PROCEDURE `AggregateData`()
BEGIN
		TRUNCATE TABLE data_aggregate.lottery_result_aggregate;
    -- Tạo bảng tạm thời để lưu các giá trị biến đổi
    CREATE TEMPORARY TABLE temp_lottery_result_fact AS
    SELECT
        lr.id,
        rd.name AS region,
        pd.name AS province,
        dd.full_date AS date,
        prize.name AS prize,
        lr.winning_number
    FROM
        warehouse.lottery_result_fact lr
    JOIN warehouse.region_dim rd ON lr.region_key = rd.region_key
    JOIN warehouse.province_dim pd ON lr.province_key = pd.province_key
    JOIN warehouse.prize_dim prize ON lr.prize_key = prize.prize_key
		JOIN warehouse.date_dim dd ON lr.date_key = dd.date_key
		WHERE DATEDIFF(CURDATE(), dd.full_date) <= 30;
    -- Chèn dữ liệu mới từ bảng tạm thời vào bảng lottery_result_mart trong database data_mart
		INSERT INTO data_aggregate.lottery_result_aggregate (id, region, province, date, prize, winning_number)
		SELECT id, region, province, date, prize, winning_number FROM temp_lottery_result_fact;

    -- Xóa bảng tạm thời
    DROP TEMPORARY TABLE IF EXISTS temp_lottery_result;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for CopyDataFromStagingToWarehouse
-- ----------------------------
DROP PROCEDURE IF EXISTS `CopyDataFromStagingToWarehouse`;
delimiter ;;
CREATE PROCEDURE `CopyDataFromStagingToWarehouse`()
BEGIN
		TRUNCATE TABLE warehouse.lottery_result;
    -- Sao chép dữ liệu từ bảng staging sang bảng warehouse
    INSERT INTO warehouse.lottery_result (region, province, date, prize, winning_number)
    SELECT region, province, date, prize, winning_number
    FROM staging.lottery_result_staging;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for InsertErrorStatus
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertErrorStatus`;
delimiter ;;
CREATE PROCEDURE `InsertErrorStatus`(IN id_config INT,
    IN `status` VARCHAR(255),
		IN note VARCHAR(255),
		IN date VARCHAR(255))
BEGIN
    DECLARE formatted_date VARCHAR(255);
		
		SELECT created_at INTO formatted_date
    FROM data_files
    WHERE df_config_id = id_config
    ORDER BY created_at DESC
    LIMIT 1;
		
		SET formatted_date = DATE_FORMAT(date, '%d_%m_%Y');
		SET @df_name = CONCAT('XSKT_', formatted_date);
		
    INSERT INTO data_files(df_config_id, name, `status`, note)
    VALUES (id_config, @df_name, `status`, note);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for InsertStatus
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertStatus`;
delimiter ;;
CREATE PROCEDURE `InsertStatus`(IN id_config INT,
    IN `status` VARCHAR(255),
		IN date VARCHAR(255))
BEGIN
    DECLARE formatted_date VARCHAR(255);
		DECLARE statusLC VARCHAR(255);
		
		SELECT created_at INTO formatted_date
    FROM data_files
    WHERE df_config_id = id_config
    ORDER BY created_at DESC
    LIMIT 1;
		
		SET formatted_date = DATE_FORMAT(date, '%d_%m_%Y');
		
		SET statusLC = LOWER(`status`);
		
		SET @df_name = CONCAT('XSKT_', formatted_date);
    SET @note = CONCAT('Data is ', statusLC);
		
    INSERT INTO data_files(df_config_id, name, `status`, note)
    VALUES (id_config, @df_name, `status`, @note);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for LoadToMart
-- ----------------------------
DROP PROCEDURE IF EXISTS `LoadToMart`;
delimiter ;;
CREATE PROCEDURE `LoadToMart`()
BEGIN
		TRUNCATE TABLE datamart.lottery_result_mart;
		INSERT INTO datamart.lottery_result_mart (id, region, province, date, prize, winning_number)
		SELECT id, region, province, date, prize, winning_number FROM data_aggregate.lottery_result_aggregate;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for LoadToStaging
-- ----------------------------
DROP PROCEDURE IF EXISTS `LoadToStaging`;
delimiter ;;
CREATE PROCEDURE `LoadToStaging`(in_region VARCHAR(255),
    in_province VARCHAR(255),
    in_date DATE,
    in_prize VARCHAR(255),
    in_winning_number VARCHAR(255))
BEGIN
    INSERT INTO staging.lottery_result_staging (region, province, date, prize, winning_number)
    VALUES (in_region, in_province, in_date, in_prize, in_winning_number);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for LoadToWareHouse
-- ----------------------------
DROP PROCEDURE IF EXISTS `LoadToWareHouse`;
delimiter ;;
CREATE PROCEDURE `LoadToWareHouse`()
BEGIN
		INSERT INTO warehouse.lottery_result_fact (region_key, province_key, date_key, prize_key, winning_number, created_at, updated_at)
		SELECT region_key, province_key, date_key, prize_key, winning_number, created_at, updated_at from control.temp_fact;
    DROP TABLE IF EXISTS control.temp_fact;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for TranferToFact
-- ----------------------------
DROP PROCEDURE IF EXISTS `TranferToFact`;
delimiter ;;
CREATE PROCEDURE `TranferToFact`()
BEGIN
 DROP TEMPORARY TABLE IF EXISTS temp_fact;
CREATE TEMPORARY TABLE temp_fact AS
    SELECT
        rd.region_key,
        pd.province_key,
        dd.date_key,
        prd.prize_key,
        lr.winning_number,
        COALESCE(dd.date_key, CURRENT_DATE) AS created_at,
        99999 AS updated_at
    FROM
         warehouse.lottery_result lr
    LEFT JOIN
        warehouse.region_dim rd ON lr.region = rd.name
    LEFT JOIN
        warehouse.province_dim pd ON lr.province = pd.name
    LEFT JOIN
        warehouse.date_dim dd ON lr.date = dd.full_date
    LEFT JOIN
        warehouse.prize_dim prd ON lr.prize = prd.name;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for TransformPrizeDim
-- ----------------------------
DROP PROCEDURE IF EXISTS `TransformPrizeDim`;
delimiter ;;
CREATE PROCEDURE `TransformPrizeDim`()
BEGIN
 INSERT IGNORE INTO warehouse.prize_dim (name, dt_changed, dt_expired)
    SELECT DISTINCT
        lr.prize,
				 NOW(),  -- Thêm ngày hiện tại vào trường created_at
        '9999-12-31 23:59:59'   -- Thêm giá trị 99999 vào trường updated_at
    FROM
        warehouse.lottery_result lr
				
    WHERE
        lr.prize NOT IN (SELECT name FROM warehouse.prize_dim);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for TransformProvinceDim
-- ----------------------------
DROP PROCEDURE IF EXISTS `TransformProvinceDim`;
delimiter ;;
CREATE PROCEDURE `TransformProvinceDim`()
BEGIN
 INSERT IGNORE INTO warehouse.province_dim (name, dt_changed, dt_expired)
    SELECT DISTINCT
        lr.province, 
				 NOW(),  -- Thêm ngày hiện tại vào trường created_at
         '9999-12-31 23:59:59'   -- Thêm giá trị 99999 vào trường updated_at
    FROM
        warehouse.lottery_result lr
				
    WHERE
        lr.province NOT IN (SELECT name FROM warehouse.province_dim);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for TransformRegionDim
-- ----------------------------
DROP PROCEDURE IF EXISTS `TransformRegionDim`;
delimiter ;;
CREATE PROCEDURE `TransformRegionDim`()
BEGIN
    -- Thêm các giá trị mới vào bảng region_dim nếu chưa tồn tại
    INSERT IGNORE INTO warehouse.region_dim (name, dt_changed, dt_expired)
    SELECT DISTINCT
        lr.region,
				 NOW(),  -- Thêm ngày hiện tại vào trường created_at
         '9999-12-31 23:59:59'   -- Thêm giá trị 99999 vào trường updated_at
    FROM
        warehouse.lottery_result lr
    WHERE
        lr.region NOT IN (SELECT name FROM warehouse.region_dim);
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table data_files
-- ----------------------------
DROP TRIGGER IF EXISTS `before_insert_data_files`;
delimiter ;;
CREATE TRIGGER `before_insert_data_files` BEFORE INSERT ON `data_files` FOR EACH ROW BEGIN
    SET NEW.created_at = NOW();
    SET NEW.updated_at = '9999-12-31 23:59:59';
    SET NEW.file_timestamp = NOW();
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
