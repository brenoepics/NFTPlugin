DROP TABLE IF EXISTS `special_looks`;
CREATE TABLE `special_looks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` enum('setid','figure') NOT NULL DEFAULT 'figure',
  `figure` varchar(255) DEFAULT NULL,
  `setid` int DEFAULT NULL,
  `effect` int NOT NULL DEFAULT '0',
  UNIQUE KEY `id` (`id`,`figure`,`setid`)
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

ALTER TABLE `permissions`
ADD COLUMN `cmd_update_looks`  enum('1','0') NOT NULL DEFAULT '0';