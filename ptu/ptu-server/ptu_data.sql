/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : ptu_data

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-04-13 11:16:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_comid_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_comid_data`;
CREATE TABLE `t_comid_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `ip` varchar(15) NOT NULL,
  `com_id` int(10) unsigned NOT NULL,
  `period_stability_p_h_m` tinyint(4) NOT NULL,
  `lost_rate_p_h_m` tinyint(4) NOT NULL,
  `abnomal_lost_p_h_m` tinyint(4) NOT NULL,
  `window_time` int(11) NOT NULL,
  `period_mean` int(11) NOT NULL,
  `period_std` int(11) NOT NULL,
  `lost_rate` float NOT NULL,
  `lost_max_rate` float NOT NULL,
  `life_signal_stop_rate` float NOT NULL,
  `life_signal_stop_max_rate` float NOT NULL,
  PRIMARY KEY (`id`,`date`),
  KEY `date` (`date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1850153 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC
/*!50100 PARTITION BY RANGE (to_days(date))
(PARTITION p20200225 VALUES LESS THAN (737845) ENGINE = InnoDB,
 PARTITION p20200226 VALUES LESS THAN (737846) ENGINE = InnoDB,
 PARTITION p20200227 VALUES LESS THAN (737847) ENGINE = InnoDB,
 PARTITION p20200228 VALUES LESS THAN (737848) ENGINE = InnoDB,
 PARTITION p20200229 VALUES LESS THAN (737849) ENGINE = InnoDB,
 PARTITION p20200301 VALUES LESS THAN (737850) ENGINE = InnoDB,
 PARTITION p20200302 VALUES LESS THAN (737851) ENGINE = InnoDB,
 PARTITION p20200303 VALUES LESS THAN (737852) ENGINE = InnoDB,
 PARTITION p20200304 VALUES LESS THAN (737853) ENGINE = InnoDB,
 PARTITION p20200305 VALUES LESS THAN (737854) ENGINE = InnoDB,
 PARTITION p20200306 VALUES LESS THAN (737855) ENGINE = InnoDB,
 PARTITION p20200307 VALUES LESS THAN (737856) ENGINE = InnoDB,
 PARTITION p20200308 VALUES LESS THAN (737857) ENGINE = InnoDB,
 PARTITION p20200309 VALUES LESS THAN (737858) ENGINE = InnoDB,
 PARTITION p20200310 VALUES LESS THAN (737859) ENGINE = InnoDB,
 PARTITION p20200311 VALUES LESS THAN (737860) ENGINE = InnoDB,
 PARTITION p20200312 VALUES LESS THAN (737861) ENGINE = InnoDB,
 PARTITION p20200313 VALUES LESS THAN (737862) ENGINE = InnoDB,
 PARTITION p20200314 VALUES LESS THAN (737863) ENGINE = InnoDB,
 PARTITION p20200315 VALUES LESS THAN (737864) ENGINE = InnoDB,
 PARTITION p20200316 VALUES LESS THAN (737865) ENGINE = InnoDB,
 PARTITION p20200317 VALUES LESS THAN (737866) ENGINE = InnoDB,
 PARTITION p20200318 VALUES LESS THAN (737867) ENGINE = InnoDB,
 PARTITION p20200319 VALUES LESS THAN (737868) ENGINE = InnoDB,
 PARTITION p20200320 VALUES LESS THAN (737869) ENGINE = InnoDB,
 PARTITION p20200321 VALUES LESS THAN (737870) ENGINE = InnoDB,
 PARTITION p20200322 VALUES LESS THAN (737871) ENGINE = InnoDB,
 PARTITION p20200323 VALUES LESS THAN (737872) ENGINE = InnoDB,
 PARTITION p20200324 VALUES LESS THAN (737873) ENGINE = InnoDB,
 PARTITION p20200325 VALUES LESS THAN (737874) ENGINE = InnoDB,
 PARTITION p20200326 VALUES LESS THAN (737875) ENGINE = InnoDB,
 PARTITION p20200327 VALUES LESS THAN (737876) ENGINE = InnoDB,
 PARTITION p20200328 VALUES LESS THAN (737877) ENGINE = InnoDB,
 PARTITION p20200329 VALUES LESS THAN (737878) ENGINE = InnoDB,
 PARTITION p20200330 VALUES LESS THAN (737879) ENGINE = InnoDB,
 PARTITION p20200331 VALUES LESS THAN (737880) ENGINE = InnoDB,
 PARTITION p20200401 VALUES LESS THAN (737881) ENGINE = InnoDB,
 PARTITION p20200402 VALUES LESS THAN (737882) ENGINE = InnoDB,
 PARTITION p20200403 VALUES LESS THAN (737883) ENGINE = InnoDB,
 PARTITION p20200404 VALUES LESS THAN (737884) ENGINE = InnoDB,
 PARTITION p20200405 VALUES LESS THAN (737885) ENGINE = InnoDB,
 PARTITION p20200406 VALUES LESS THAN (737886) ENGINE = InnoDB,
 PARTITION p20200407 VALUES LESS THAN (737887) ENGINE = InnoDB,
 PARTITION p20200408 VALUES LESS THAN (737888) ENGINE = InnoDB,
 PARTITION p20200409 VALUES LESS THAN (737889) ENGINE = InnoDB,
 PARTITION p20200410 VALUES LESS THAN (737890) ENGINE = InnoDB,
 PARTITION p20200411 VALUES LESS THAN (737891) ENGINE = InnoDB,
 PARTITION p20200412 VALUES LESS THAN (737892) ENGINE = InnoDB,
 PARTITION p20200413 VALUES LESS THAN (737893) ENGINE = InnoDB,
 PARTITION p20200414 VALUES LESS THAN (737894) ENGINE = InnoDB,
 PARTITION p20200415 VALUES LESS THAN (737895) ENGINE = InnoDB,
 PARTITION p20200416 VALUES LESS THAN (737896) ENGINE = InnoDB,
 PARTITION p20200417 VALUES LESS THAN (737897) ENGINE = InnoDB,
 PARTITION p20200418 VALUES LESS THAN (737898) ENGINE = InnoDB,
 PARTITION p20200419 VALUES LESS THAN (737899) ENGINE = InnoDB,
 PARTITION p20200420 VALUES LESS THAN (737900) ENGINE = InnoDB,
 PARTITION p20200421 VALUES LESS THAN (737901) ENGINE = InnoDB,
 PARTITION p20200422 VALUES LESS THAN (737902) ENGINE = InnoDB,
 PARTITION p20200423 VALUES LESS THAN (737903) ENGINE = InnoDB,
 PARTITION p20200424 VALUES LESS THAN (737904) ENGINE = InnoDB,
 PARTITION p20200425 VALUES LESS THAN (737905) ENGINE = InnoDB,
 PARTITION p20200426 VALUES LESS THAN (737906) ENGINE = InnoDB,
 PARTITION p20200427 VALUES LESS THAN (737907) ENGINE = InnoDB,
 PARTITION p20200428 VALUES LESS THAN (737908) ENGINE = InnoDB,
 PARTITION p20200429 VALUES LESS THAN (737909) ENGINE = InnoDB,
 PARTITION p20200430 VALUES LESS THAN (737910) ENGINE = InnoDB,
 PARTITION p20200501 VALUES LESS THAN (737911) ENGINE = InnoDB,
 PARTITION p20200502 VALUES LESS THAN (737912) ENGINE = InnoDB,
 PARTITION p20200503 VALUES LESS THAN (737913) ENGINE = InnoDB,
 PARTITION p20200504 VALUES LESS THAN (737914) ENGINE = InnoDB,
 PARTITION p20200505 VALUES LESS THAN (737915) ENGINE = InnoDB) */;

-- ----------------------------
-- Records of t_comid_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_comid_object`
-- ----------------------------
DROP TABLE IF EXISTS `t_comid_object`;
CREATE TABLE `t_comid_object` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `com_id` int(11) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `multicast_address` varchar(15) DEFAULT NULL COMMENT '组播地址',
  `cycle` int(11) DEFAULT NULL COMMENT '周期（us）',
  `carriage_position` int(11) DEFAULT NULL COMMENT '车厢位置',
  `unit` varchar(20) DEFAULT NULL COMMENT '单元',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2269 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comid_object
-- ----------------------------
INSERT INTO `t_comid_object` VALUES ('1945', '5500', '10.0.5.30', null, null, '5', null, '5车显示器1');
INSERT INTO `t_comid_object` VALUES ('1946', '5600', '10.0.15.31', null, null, '5', null, '5车显示器2');
INSERT INTO `t_comid_object` VALUES ('1947', '3040', '10.0.5.200', null, null, '5', null, '5车PIS源端口A');
INSERT INTO `t_comid_object` VALUES ('1948', '3041', '10.0.15.201', null, null, '5', null, '5车PIS源端口B');
INSERT INTO `t_comid_object` VALUES ('1949', '3340', '10.0.5.230', null, null, '5', null, '5车EEMS源端口数据');
INSERT INTO `t_comid_object` VALUES ('1950', '3341', '10.0.5.230', null, null, '5', null, '5车EEMS发显示屏数据');
INSERT INTO `t_comid_object` VALUES ('1951', '3740', '10.0.5.60', null, null, '5', null, '5车PHM端口1');
INSERT INTO `t_comid_object` VALUES ('1952', '3741', '10.0.5.60', null, null, '5', null, '5车PHM端口2');
INSERT INTO `t_comid_object` VALUES ('1953', '3742', '10.0.5.60', null, null, '5', null, '5车PHM端口3');
INSERT INTO `t_comid_object` VALUES ('1954', '3743', '10.0.5.60', null, null, '5', null, '5车PHM端口4');
INSERT INTO `t_comid_object` VALUES ('1955', '1001', '10.0.1.10', null, null, '1', null, '重联数据端口');
INSERT INTO `t_comid_object` VALUES ('1956', '1100', '10.0.1.10', null, null, '1', null, '本单元CCU统计的轴温信息和IO信息等显示器用WTB未传信息（上传ETB）');
INSERT INTO `t_comid_object` VALUES ('1957', '1701', '10.0.1.10', null, null, '1', null, '本编组1-4车CCU统计的轴温信息和IO信息等显示器用ETB未传信息');
INSERT INTO `t_comid_object` VALUES ('1958', '1702', '10.0.1.10', null, null, '1', null, '本编组5-8车CCU统计的轴温信息和IO信息等显示器用ETB未传信息');
INSERT INTO `t_comid_object` VALUES ('1959', '1102', '10.0.1.10', null, null, '1', null, '本单元CCU统计的空压机信息、空转滑行信息、三级故障数据(小环网使用1102过ETB)');
INSERT INTO `t_comid_object` VALUES ('1960', '1801', '10.0.1.10', null, null, '1', null, '本编组1-4车CCU统计的空压机信息、空转滑行信息、三级故障数据');
INSERT INTO `t_comid_object` VALUES ('1961', '1802', '10.0.1.10', null, null, '1', null, '本编组5-8车CCU统计的空压机信息、空转滑行信息、三级故障数据');
INSERT INTO `t_comid_object` VALUES ('1962', '1200', '10.0.1.10', null, null, '1', null, 'CCU发各子系统端口数据');
INSERT INTO `t_comid_object` VALUES ('1963', '1201', '10.0.1.10', null, null, '1', null, 'CCU发制动系统端口数据');
INSERT INTO `t_comid_object` VALUES ('1964', '1301', '10.0.1.10', null, null, '1', null, 'CCU送显示器牵引单元1数据');
INSERT INTO `t_comid_object` VALUES ('1965', '1302', '10.0.1.10', null, null, '1', null, 'CCU送显示器牵引单元2数据');
INSERT INTO `t_comid_object` VALUES ('1966', '1303', '10.0.1.10', null, null, '1', null, 'CCU送显示器牵引单元3数据');
INSERT INTO `t_comid_object` VALUES ('1967', '1304', '10.0.1.10', null, null, '1', null, 'CCU送显示器牵引单元4数据');
INSERT INTO `t_comid_object` VALUES ('1968', '1001', '10.0.1.11', null, null, '1', null, '重联数据端口');
INSERT INTO `t_comid_object` VALUES ('1969', '1100', '10.0.1.11', null, null, '1', null, '本单元CCU统计的轴温信息和IO信息等显示器用WTB未传信息（上传ETB）');
INSERT INTO `t_comid_object` VALUES ('1970', '1701', '10.0.1.11', null, null, '1', null, '本编组1-4车CCU统计的轴温信息和IO信息等显示器用ETB未传信息');
INSERT INTO `t_comid_object` VALUES ('1971', '1702', '10.0.1.11', null, null, '1', null, '本编组5-8车CCU统计的轴温信息和IO信息等显示器用ETB未传信息');
INSERT INTO `t_comid_object` VALUES ('1972', '1102', '10.0.1.11', null, null, '1', null, '本单元CCU统计的空压机信息、空转滑行信息、三级故障数据(小环网使用1102过ETB)');
INSERT INTO `t_comid_object` VALUES ('1973', '1801', '10.0.1.11', null, null, '1', null, '本编组1-4车CCU统计的空压机信息、空转滑行信息、三级故障数据');
INSERT INTO `t_comid_object` VALUES ('1974', '1802', '10.0.1.11', null, null, '1', null, '本编组5-8车CCU统计的空压机信息、空转滑行信息、三级故障数据');
INSERT INTO `t_comid_object` VALUES ('1975', '1200', '10.0.1.11', null, null, '1', null, 'CCU发各子系统端口数据');
INSERT INTO `t_comid_object` VALUES ('1976', '1201', '10.0.1.11', null, null, '1', null, 'CCU发制动系统端口数据');
INSERT INTO `t_comid_object` VALUES ('1977', '1301', '10.0.1.11', null, null, '1', null, 'CCU送显示器牵引单元1数据');
INSERT INTO `t_comid_object` VALUES ('1978', '1302', '10.0.1.11', null, null, '1', null, 'CCU送显示器牵引单元2数据');
INSERT INTO `t_comid_object` VALUES ('1979', '1303', '10.0.1.11', null, null, '1', null, 'CCU送显示器牵引单元3数据');
INSERT INTO `t_comid_object` VALUES ('1980', '1304', '10.0.1.11', null, null, '1', null, 'CCU送显示器牵引单元4数据');
INSERT INTO `t_comid_object` VALUES ('1981', '1411', '10.0.1.40', null, null, '1', null, 'X车客室IOM_84RA.1源端口A');
INSERT INTO `t_comid_object` VALUES ('1982', '1421', '10.0.2.40', null, null, '2', null, 'X车客室IOM_84RA.1源端口A');
INSERT INTO `t_comid_object` VALUES ('1983', '1431', '10.0.3.40', null, null, '3', null, 'X车客室IOM_84RA.1源端口A');
INSERT INTO `t_comid_object` VALUES ('1984', '1441', '10.0.4.40', null, null, '4', null, 'X车客室IOM_84RA.1源端口A');
INSERT INTO `t_comid_object` VALUES ('1985', '1412', '10.0.11.41', null, null, '1', null, 'X车客室IOM_84RA.1源端口B');
INSERT INTO `t_comid_object` VALUES ('1986', '1422', '10.0.12.41', null, null, '2', null, 'X车客室IOM_84RA.1源端口B');
INSERT INTO `t_comid_object` VALUES ('1987', '1432', '10.0.13.41', null, null, '3', null, 'X车客室IOM_84RA.1源端口B');
INSERT INTO `t_comid_object` VALUES ('1988', '1442', '10.0.14.41', null, null, '4', null, 'X车客室IOM_84RA.1源端口B');
INSERT INTO `t_comid_object` VALUES ('1989', '1413', '10.0.1.42', null, null, '1', null, 'X车(3/6除外)车客室IOM84_RA.2源端口A；');
INSERT INTO `t_comid_object` VALUES ('1990', '1423', '10.0.2.42', null, null, '2', null, 'X车(3/6除外)车客室IOM84_RA.2源端口A；');
INSERT INTO `t_comid_object` VALUES ('1991', '1453', '10.0.3.44', null, null, '3', null, '3/6车IOM_84RB源端口A，X等于5');
INSERT INTO `t_comid_object` VALUES ('1992', '1443', '10.0.4.42', null, null, '4', null, 'X车(3/6除外)车客室IOM84_RA.2源端口A；');
INSERT INTO `t_comid_object` VALUES ('1993', '1414', '10.0.11.43', null, null, '1', null, 'X车(3/6除外)客室IOM84_RA.2源端口B；');
INSERT INTO `t_comid_object` VALUES ('1994', '1424', '10.0.12.43', null, null, '2', null, 'X车(3/6除外)客室IOM84_RA.2源端口B；');
INSERT INTO `t_comid_object` VALUES ('1995', '1454', '10.0.13.45', null, null, '3', null, '3/6车IOM_84RB源端口B ，X等于5');
INSERT INTO `t_comid_object` VALUES ('1996', '1444', '10.0.14.43', null, null, '4', null, 'X车(3/6除外)客室IOM84_RA.2源端口B；');
INSERT INTO `t_comid_object` VALUES ('1997', '1415', '10.0.1.44', null, null, '1', null, '1/8车司机室IOM_60R.1源端口A');
INSERT INTO `t_comid_object` VALUES ('1998', '1416', '10.0.11.45', null, null, '1', null, '1/8车司机室IOM_60R.1源端口B');
INSERT INTO `t_comid_object` VALUES ('1999', '1425', '10.0.1.47', null, null, '1', null, '1/8车司机室IOM_60R.2源端口A');
INSERT INTO `t_comid_object` VALUES ('2000', '1426', '10.0.11.48', null, null, '1', null, '1/8车司机室IOM_60R.2源端口B');
INSERT INTO `t_comid_object` VALUES ('2001', '1500', '10.0.1.30', null, null, '1', null, '1/8车显示器1');
INSERT INTO `t_comid_object` VALUES ('2002', '1600', '10.0.1.31', null, null, '1', null, '1/8车显示器2');
INSERT INTO `t_comid_object` VALUES ('2003', '1550', '10.0.1.32', null, null, '1', null, '1/8车抬头显示器');
INSERT INTO `t_comid_object` VALUES ('2004', '2020', '10.0.2.101', null, null, '2', null, 'X车TCU源端口A');
INSERT INTO `t_comid_object` VALUES ('2005', '2040', '10.0.4.101', null, null, '4', null, 'X车TCU源端口A');
INSERT INTO `t_comid_object` VALUES ('2006', '2021', '10.0.2.102', null, null, '2', null, 'X车TCU源端口B');
INSERT INTO `t_comid_object` VALUES ('2007', '2041', '10.0.4.102', null, null, '4', null, 'X车TCU源端口B');
INSERT INTO `t_comid_object` VALUES ('2008', '2210', '10.0.1.122', null, null, '1', null, 'X车BCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2009', '2220', '10.0.2.122', null, null, '2', null, 'X车BCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2010', '2230', '10.0.3.122', null, null, '3', null, 'X车BCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2011', '2240', '10.0.4.122', null, null, '4', null, 'X车BCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2012', '2210', '10.0.11.122', null, null, '1', null, 'X车BCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2013', '2220', '10.0.12.122', null, null, '2', null, 'X车BCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2014', '2230', '10.0.13.122', null, null, '3', null, 'X车BCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2015', '2240', '10.0.14.122', null, null, '4', null, 'X车BCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2016', '2211', '10.0.1.123', null, null, '1', null, 'X车BCU2源端口A');
INSERT INTO `t_comid_object` VALUES ('2017', '2221', '10.0.2.123', null, null, '2', null, 'X车BCU2源端口A');
INSERT INTO `t_comid_object` VALUES ('2018', '2231', '10.0.3.123', null, null, '3', null, 'X车BCU2源端口A');
INSERT INTO `t_comid_object` VALUES ('2019', '2241', '10.0.4.123', null, null, '4', null, 'X车BCU2源端口A');
INSERT INTO `t_comid_object` VALUES ('2020', '2211', '10.0.11.123', null, null, '1', null, 'X车BCU2源端口B');
INSERT INTO `t_comid_object` VALUES ('2021', '2221', '10.0.12.123', null, null, '2', null, 'X车BCU2源端口B');
INSERT INTO `t_comid_object` VALUES ('2022', '2231', '10.0.13.123', null, null, '3', null, 'X车BCU2源端口B');
INSERT INTO `t_comid_object` VALUES ('2023', '2241', '10.0.14.123', null, null, '4', null, 'X车BCU2源端口B');
INSERT INTO `t_comid_object` VALUES ('2024', '2212', '10.0.1.122', null, null, '1', null, '1/8车TBM源端口A，BCU1控制A');
INSERT INTO `t_comid_object` VALUES ('2025', '2212', '10.0.11.122', null, null, '1', null, '1/8车TBM源端口B，BCU1控制B');
INSERT INTO `t_comid_object` VALUES ('2026', '2212', '10.0.1.123', null, null, '1', null, '1/8车TBM源端口A，BCU2控制A');
INSERT INTO `t_comid_object` VALUES ('2027', '2212', '10.0.11.123', null, null, '1', null, '1/8车TBM源端口B，BCU2控制B');
INSERT INTO `t_comid_object` VALUES ('2028', '2213', '10.0.1.122', null, null, '1', null, '1/8车SBM源端口A，BCU1控制A');
INSERT INTO `t_comid_object` VALUES ('2029', '2213', '10.0.11.122', null, null, '1', null, '1/8车SBM源端口B，BCU1控制B');
INSERT INTO `t_comid_object` VALUES ('2030', '2213', '10.0.1.123', null, null, '1', null, '1/8车SBM源端口A，BCU2控制A');
INSERT INTO `t_comid_object` VALUES ('2031', '2213', '10.0.11.123', null, null, '1', null, '1/8车SBM源端口B，BCU2控制B');
INSERT INTO `t_comid_object` VALUES ('2032', '2310', '10.0.1.130', null, null, '1', null, 'X车HVAC源端口A');
INSERT INTO `t_comid_object` VALUES ('2033', '2320', '10.0.2.130', null, null, '2', null, 'X车HVAC源端口A');
INSERT INTO `t_comid_object` VALUES ('2034', '2330', '10.0.3.130', null, null, '3', null, 'X车HVAC源端口A');
INSERT INTO `t_comid_object` VALUES ('2035', '2340', '10.0.4.130', null, null, '4', null, 'X车HVAC源端口A');
INSERT INTO `t_comid_object` VALUES ('2036', '2311', '10.0.1.131', null, null, '1', null, 'X车HVAC源端口B');
INSERT INTO `t_comid_object` VALUES ('2037', '2321', '10.0.2.131', null, null, '2', null, 'X车HVAC源端口B');
INSERT INTO `t_comid_object` VALUES ('2038', '2331', '10.0.3.131', null, null, '3', null, 'X车HVAC源端口B');
INSERT INTO `t_comid_object` VALUES ('2039', '2341', '10.0.4.131', null, null, '4', null, 'X车HVAC源端口B');
INSERT INTO `t_comid_object` VALUES ('2040', '2410', '10.0.1.140', null, null, '1', null, 'X车EDCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2041', '2420', '10.0.2.140', null, null, '2', null, 'X车EDCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2042', '2430', '10.0.3.140', null, null, '3', null, 'X车EDCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2043', '2440', '10.0.4.140', null, null, '4', null, 'X车EDCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2044', '2411', '10.0.11.141', null, null, '1', null, 'X车EDCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2045', '2421', '10.0.12.141', null, null, '2', null, 'X车EDCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2046', '2431', '10.0.13.141', null, null, '3', null, 'X车EDCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2047', '2441', '10.0.14.141', null, null, '4', null, 'X车EDCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2048', '2418', '10.0.11.141', null, null, '1', null, 'X车EDCU1发送PHM主机源端口');
INSERT INTO `t_comid_object` VALUES ('2049', '2428', '10.0.12.141', null, null, '2', null, 'X车EDCU1发送PHM主机源端口');
INSERT INTO `t_comid_object` VALUES ('2050', '2438', '10.0.13.141', null, null, '3', null, 'X车EDCU1发送PHM主机源端口');
INSERT INTO `t_comid_object` VALUES ('2051', '2448', '10.0.14.141', null, null, '4', null, 'X车EDCU1发送PHM主机源端口');
INSERT INTO `t_comid_object` VALUES ('2052', '2510', '10.0.1.150', null, null, '1', null, 'X车FAS源端口A');
INSERT INTO `t_comid_object` VALUES ('2053', '2520', '10.0.2.150', null, null, '2', null, 'X车FAS源端口A');
INSERT INTO `t_comid_object` VALUES ('2054', '2530', '10.0.3.150', null, null, '3', null, 'X车FAS源端口A');
INSERT INTO `t_comid_object` VALUES ('2055', '2540', '10.0.4.150', null, null, '4', null, 'X车FAS源端口A');
INSERT INTO `t_comid_object` VALUES ('2056', '2511', '10.0.11.151', null, null, '1', null, 'X车FAS源端口B');
INSERT INTO `t_comid_object` VALUES ('2057', '2521', '10.0.12.151', null, null, '2', null, 'X车FAS源端口B');
INSERT INTO `t_comid_object` VALUES ('2058', '2531', '10.0.13.151', null, null, '3', null, 'X车FAS源端口B');
INSERT INTO `t_comid_object` VALUES ('2059', '2541', '10.0.14.151', null, null, '4', null, 'X车FAS源端口B');
INSERT INTO `t_comid_object` VALUES ('2060', '2610', '10.0.1.164', null, null, '1', null, 'X车安全监控源端口1');
INSERT INTO `t_comid_object` VALUES ('2061', '2620', '10.0.2.164', null, null, '2', null, 'X车安全监控源端口1');
INSERT INTO `t_comid_object` VALUES ('2062', '2630', '10.0.3.164', null, null, '3', null, 'X车安全监控源端口1');
INSERT INTO `t_comid_object` VALUES ('2063', '2640', '10.0.4.164', null, null, '4', null, 'X车安全监控源端口1');
INSERT INTO `t_comid_object` VALUES ('2064', '2611', '10.0.11.165', null, null, '1', null, 'X车安全监控源端口2');
INSERT INTO `t_comid_object` VALUES ('2065', '2621', '10.0.12.165', null, null, '2', null, 'X车安全监控源端口2');
INSERT INTO `t_comid_object` VALUES ('2066', '2631', '10.0.13.165', null, null, '3', null, 'X车安全监控源端口2');
INSERT INTO `t_comid_object` VALUES ('2067', '2641', '10.0.14.165', null, null, '4', null, 'X车安全监控源端口2');
INSERT INTO `t_comid_object` VALUES ('2068', '2810', '10.0.1.180', null, null, '1', null, 'X车BC1源端口1A');
INSERT INTO `t_comid_object` VALUES ('2069', '2812', '10.0.11.182', null, null, '1', null, 'X车BC1源端口1B');
INSERT INTO `t_comid_object` VALUES ('2070', '2811', '10.0.1.181', null, null, '1', null, 'X车BC2源端口2A');
INSERT INTO `t_comid_object` VALUES ('2071', '2813', '10.0.11.183', null, null, '1', null, 'X车BC2源端口2B');
INSERT INTO `t_comid_object` VALUES ('2072', '2930', '10.0.3.190', null, null, '3', null, '3/6车PCU控制1源端口');
INSERT INTO `t_comid_object` VALUES ('2073', '2931', '10.0.13.191', null, null, '3', null, '3/6车PCU控制2源端口');
INSERT INTO `t_comid_object` VALUES ('2074', '3110', '10.0.1.210', null, null, '1', null, '1/8车WTD源端口A');
INSERT INTO `t_comid_object` VALUES ('2075', '3111', '10.0.11.211', null, null, '1', null, '1/8车WTD源端口B');
INSERT INTO `t_comid_object` VALUES ('2076', '3112', '10.0.11.210', null, null, '1', null, '1/8车WTD落地数据发送网络源端口');
INSERT INTO `t_comid_object` VALUES ('2077', '3330', '10.0.3.230', null, null, '3', null, '3/6车EEMS源端口');
INSERT INTO `t_comid_object` VALUES ('2078', '3410', '10.0.1.240', null, null, '1', null, '1/8车ATO1/源端口A');
INSERT INTO `t_comid_object` VALUES ('2079', '3411', '10.0.11.241', null, null, '1', null, '1/8车ATO2源端口B');
INSERT INTO `t_comid_object` VALUES ('2080', '3410', '10.0.1.242', null, null, '1', null, '1/8车ATO3源端口A');
INSERT INTO `t_comid_object` VALUES ('2081', '3411', '10.0.11.243', null, null, '1', null, '1/8车ATO4源端口B');
INSERT INTO `t_comid_object` VALUES ('2082', '3510', '10.0.1.90', null, null, '1', null, 'X车EADS源端口');
INSERT INTO `t_comid_object` VALUES ('2083', '3520', '10.0.2.90', null, null, '2', null, 'X车EADS源端口');
INSERT INTO `t_comid_object` VALUES ('2084', '3530', '10.0.3.90', null, null, '3', null, 'X车EADS源端口');
INSERT INTO `t_comid_object` VALUES ('2085', '3540', '10.0.4.90', null, null, '4', null, 'X车EADS源端口');
INSERT INTO `t_comid_object` VALUES ('2086', '3550', '10.0.5.90', null, null, '1', null, '1/8车总配电柜EADS的X=5');
INSERT INTO `t_comid_object` VALUES ('2087', '3012', '10.0.1.202', null, null, '1', null, 'X车给水卫生控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2088', '3022', '10.0.2.202', null, null, '2', null, 'X车给水卫生控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2089', '3032', '10.0.3.202', null, null, '3', null, 'X车给水卫生控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2090', '3042', '10.0.4.202', null, null, '4', null, 'X车给水卫生控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2091', '3013', '10.0.1.203', null, null, '1', null, 'X车智能照明控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2092', '3023', '10.0.2.203', null, null, '2', null, 'X车智能照明控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2093', '3033', '10.0.3.203', null, null, '3', null, 'X车智能照明控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2094', '3043', '10.0.4.203', null, null, '4', null, 'X车智能照明控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2095', '8511', '10.0.1.51', null, null, '1', null, '1车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2096', '8512', '10.0.1.52', null, null, '1', null, '1车SRU板卡2数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2097', '8513', '10.0.1.53', null, null, '1', null, '1车SRU板卡3数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2098', '8514', '10.0.1.54', null, null, '1', null, '1车SRU板卡4数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2099', '8520', '10.0.2.50', null, null, '2', null, '2车SRU-BN');
INSERT INTO `t_comid_object` VALUES ('2100', '8521', '10.0.2.51', null, null, '2', null, '2车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2101', '8522', '10.0.2.52', null, null, '2', null, '2车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2102', '8530', '10.0.3.50', null, null, '3', null, '3车SRU-BN');
INSERT INTO `t_comid_object` VALUES ('2103', '8531', '10.0.3.51', null, null, '3', null, '3车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2104', '8532', '10.0.3.52', null, null, '3', null, '3车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2105', '8540', '10.0.4.50', null, null, '4', null, '4车SRU-BN');
INSERT INTO `t_comid_object` VALUES ('2106', '8541', '10.0.4.51', null, null, '4', null, '4车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2107', '8542', '10.0.4.52', null, null, '4', null, '4车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2108', '8601', '10.0.0.1', null, null, '1', null, 'ETBN1交换机数据');
INSERT INTO `t_comid_object` VALUES ('2109', '8602', '10.0.0.2', null, null, '1', null, 'ETBN2交换机数据');
INSERT INTO `t_comid_object` VALUES ('2110', '1101', '10.0.1.10', null, null, '1', null, '主备监听（网关1）');
INSERT INTO `t_comid_object` VALUES ('2111', '1103', '10.0.1.11', null, null, '1', null, '主备监听（网关2）');
INSERT INTO `t_comid_object` VALUES ('2112', '1001', '172.16.1.10', null, null, '8', null, '重联数据端口');
INSERT INTO `t_comid_object` VALUES ('2113', '1100', '172.16.1.10', null, null, '8', null, '本单元CCU统计的轴温信息和IO信息等显示器用WTB未传信息（上传ETB）');
INSERT INTO `t_comid_object` VALUES ('2114', '1701', '172.16.1.10', null, null, '8', null, '本编组1-4车CCU统计的轴温信息和IO信息等显示器用ETB未传信息');
INSERT INTO `t_comid_object` VALUES ('2115', '1702', '172.16.1.10', null, null, '8', null, '本编组5-8车CCU统计的轴温信息和IO信息等显示器用ETB未传信息');
INSERT INTO `t_comid_object` VALUES ('2116', '1102', '172.16.1.10', null, null, '8', null, '本单元CCU统计的空压机信息、空转滑行信息、三级故障数据(小环网使用1102过ETB)');
INSERT INTO `t_comid_object` VALUES ('2117', '1801', '172.16.1.10', null, null, '8', null, '本编组1-4车CCU统计的空压机信息、空转滑行信息、三级故障数据');
INSERT INTO `t_comid_object` VALUES ('2118', '1802', '172.16.1.10', null, null, '8', null, '本编组5-8车CCU统计的空压机信息、空转滑行信息、三级故障数据');
INSERT INTO `t_comid_object` VALUES ('2119', '1200', '172.16.1.10', null, null, '8', null, 'CCU发各子系统端口数据');
INSERT INTO `t_comid_object` VALUES ('2120', '1201', '172.16.1.10', null, null, '8', null, 'CCU发制动系统端口数据');
INSERT INTO `t_comid_object` VALUES ('2121', '1301', '172.16.1.10', null, null, '8', null, 'CCU送显示器牵引单元1数据');
INSERT INTO `t_comid_object` VALUES ('2122', '1302', '172.16.1.10', null, null, '8', null, 'CCU送显示器牵引单元2数据');
INSERT INTO `t_comid_object` VALUES ('2123', '1303', '172.16.1.10', null, null, '8', null, 'CCU送显示器牵引单元3数据');
INSERT INTO `t_comid_object` VALUES ('2124', '1304', '172.16.1.10', null, null, '8', null, 'CCU送显示器牵引单元4数据');
INSERT INTO `t_comid_object` VALUES ('2125', '1001', '172.16.1.11', null, null, '8', null, '重联数据端口');
INSERT INTO `t_comid_object` VALUES ('2126', '1100', '172.16.1.11', null, null, '8', null, '本单元CCU统计的轴温信息和IO信息等显示器用WTB未传信息（上传ETB）');
INSERT INTO `t_comid_object` VALUES ('2127', '1701', '172.16.1.11', null, null, '8', null, '本编组1-4车CCU统计的轴温信息和IO信息等显示器用ETB未传信息');
INSERT INTO `t_comid_object` VALUES ('2128', '1702', '172.16.1.11', null, null, '8', null, '本编组5-8车CCU统计的轴温信息和IO信息等显示器用ETB未传信息');
INSERT INTO `t_comid_object` VALUES ('2129', '1102', '172.16.1.11', null, null, '8', null, '本单元CCU统计的空压机信息、空转滑行信息、三级故障数据(小环网使用1102过ETB)');
INSERT INTO `t_comid_object` VALUES ('2130', '1801', '172.16.1.11', null, null, '8', null, '本编组1-4车CCU统计的空压机信息、空转滑行信息、三级故障数据');
INSERT INTO `t_comid_object` VALUES ('2131', '1802', '172.16.1.11', null, null, '8', null, '本编组5-8车CCU统计的空压机信息、空转滑行信息、三级故障数据');
INSERT INTO `t_comid_object` VALUES ('2132', '1200', '172.16.1.11', null, null, '8', null, 'CCU发各子系统端口数据');
INSERT INTO `t_comid_object` VALUES ('2133', '1201', '172.16.1.11', null, null, '8', null, 'CCU发制动系统端口数据');
INSERT INTO `t_comid_object` VALUES ('2134', '1301', '172.16.1.11', null, null, '8', null, 'CCU送显示器牵引单元1数据');
INSERT INTO `t_comid_object` VALUES ('2135', '1302', '172.16.1.11', null, null, '8', null, 'CCU送显示器牵引单元2数据');
INSERT INTO `t_comid_object` VALUES ('2136', '1303', '172.16.1.11', null, null, '8', null, 'CCU送显示器牵引单元3数据');
INSERT INTO `t_comid_object` VALUES ('2137', '1304', '172.16.1.11', null, null, '8', null, 'CCU送显示器牵引单元4数据');
INSERT INTO `t_comid_object` VALUES ('2138', '1411', '172.16.1.40', null, null, '8', null, 'X车客室IOM_84RA.1源端口A');
INSERT INTO `t_comid_object` VALUES ('2139', '1421', '172.16.2.40', null, null, '7', null, 'X车客室IOM_84RA.1源端口A');
INSERT INTO `t_comid_object` VALUES ('2140', '1431', '172.16.3.40', null, null, '6', null, 'X车客室IOM_84RA.1源端口A');
INSERT INTO `t_comid_object` VALUES ('2141', '1441', '172.16.4.40', null, null, '5', null, 'X车客室IOM_84RA.1源端口A');
INSERT INTO `t_comid_object` VALUES ('2142', '1412', '172.16.11.41', null, null, '8', null, 'X车客室IOM_84RA.1源端口B');
INSERT INTO `t_comid_object` VALUES ('2143', '1422', '172.16.12.41', null, null, '7', null, 'X车客室IOM_84RA.1源端口B');
INSERT INTO `t_comid_object` VALUES ('2144', '1432', '172.16.13.41', null, null, '6', null, 'X车客室IOM_84RA.1源端口B');
INSERT INTO `t_comid_object` VALUES ('2145', '1442', '172.16.14.41', null, null, '5', null, 'X车客室IOM_84RA.1源端口B');
INSERT INTO `t_comid_object` VALUES ('2146', '1413', '172.16.1.42', null, null, '8', null, 'X车(3/6除外)车客室IOM84_RA.2源端口A；');
INSERT INTO `t_comid_object` VALUES ('2147', '1423', '172.16.2.42', null, null, '7', null, 'X车(3/6除外)车客室IOM84_RA.2源端口A；');
INSERT INTO `t_comid_object` VALUES ('2148', '1453', '172.16.3.44', null, null, '6', null, '3/6车IOM_84RB源端口A，X等于5');
INSERT INTO `t_comid_object` VALUES ('2149', '1443', '172.16.4.42', null, null, '5', null, 'X车(3/6除外)车客室IOM84_RA.2源端口A；');
INSERT INTO `t_comid_object` VALUES ('2150', '1414', '172.16.11.43', null, null, '8', null, 'X车(3/6除外)客室IOM84_RA.2源端口B；');
INSERT INTO `t_comid_object` VALUES ('2151', '1424', '172.16.12.43', null, null, '7', null, 'X车(3/6除外)客室IOM84_RA.2源端口B；');
INSERT INTO `t_comid_object` VALUES ('2152', '1454', '172.16.13.45', null, null, '6', null, '3/6车IOM_84RB源端口B ，X等于5');
INSERT INTO `t_comid_object` VALUES ('2153', '1444', '172.16.14.43', null, null, '5', null, 'X车(3/6除外)客室IOM84_RA.2源端口B；');
INSERT INTO `t_comid_object` VALUES ('2154', '1415', '172.16.1.44', null, null, '8', null, '1/8车司机室IOM_60R.1源端口A');
INSERT INTO `t_comid_object` VALUES ('2155', '1416', '172.16.11.45', null, null, '8', null, '1/8车司机室IOM_60R.1源端口B');
INSERT INTO `t_comid_object` VALUES ('2156', '1425', '172.16.1.47', null, null, '8', null, '1/8车司机室IOM_60R.2源端口A');
INSERT INTO `t_comid_object` VALUES ('2157', '1426', '172.16.11.48', null, null, '8', null, '1/8车司机室IOM_60R.2源端口B');
INSERT INTO `t_comid_object` VALUES ('2158', '1500', '172.16.1.30', null, null, '8', null, '1/8车显示器1');
INSERT INTO `t_comid_object` VALUES ('2159', '1600', '172.16.1.31', null, null, '8', null, '1/8车显示器2');
INSERT INTO `t_comid_object` VALUES ('2160', '1550', '172.16.1.32', null, null, '8', null, '1/8车抬头显示器');
INSERT INTO `t_comid_object` VALUES ('2161', '2020', '172.16.2.101', null, null, '7', null, 'X车TCU源端口A');
INSERT INTO `t_comid_object` VALUES ('2162', '2040', '172.16.4.101', null, null, '5', null, 'X车TCU源端口A');
INSERT INTO `t_comid_object` VALUES ('2163', '2021', '172.16.2.102', null, null, '7', null, 'X车TCU源端口B');
INSERT INTO `t_comid_object` VALUES ('2164', '2041', '172.16.4.102', null, null, '5', null, 'X车TCU源端口B');
INSERT INTO `t_comid_object` VALUES ('2165', '2210', '172.16.1.122', null, null, '8', null, 'X车BCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2166', '2220', '172.16.2.122', null, null, '7', null, 'X车BCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2167', '2230', '172.16.3.122', null, null, '6', null, 'X车BCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2168', '2240', '172.16.4.122', null, null, '5', null, 'X车BCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2169', '2210', '172.16.11.122', null, null, '8', null, 'X车BCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2170', '2220', '172.16.12.122', null, null, '7', null, 'X车BCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2171', '2230', '172.16.13.122', null, null, '6', null, 'X车BCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2172', '2240', '172.16.14.122', null, null, '5', null, 'X车BCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2173', '2211', '172.16.1.123', null, null, '8', null, 'X车BCU2源端口A');
INSERT INTO `t_comid_object` VALUES ('2174', '2221', '172.16.2.123', null, null, '7', null, 'X车BCU2源端口A');
INSERT INTO `t_comid_object` VALUES ('2175', '2231', '172.16.3.123', null, null, '6', null, 'X车BCU2源端口A');
INSERT INTO `t_comid_object` VALUES ('2176', '2241', '172.16.4.123', null, null, '5', null, 'X车BCU2源端口A');
INSERT INTO `t_comid_object` VALUES ('2177', '2211', '172.16.11.123', null, null, '8', null, 'X车BCU2源端口B');
INSERT INTO `t_comid_object` VALUES ('2178', '2221', '172.16.12.123', null, null, '7', null, 'X车BCU2源端口B');
INSERT INTO `t_comid_object` VALUES ('2179', '2231', '172.16.13.123', null, null, '6', null, 'X车BCU2源端口B');
INSERT INTO `t_comid_object` VALUES ('2180', '2241', '172.16.14.123', null, null, '5', null, 'X车BCU2源端口B');
INSERT INTO `t_comid_object` VALUES ('2181', '2212', '172.16.1.122', null, null, '8', null, '1/8车TBM源端口A，BCU1控制A');
INSERT INTO `t_comid_object` VALUES ('2182', '2212', '172.16.11.122', null, null, '8', null, '1/8车TBM源端口B，BCU1控制B');
INSERT INTO `t_comid_object` VALUES ('2183', '2212', '172.16.1.123', null, null, '8', null, '1/8车TBM源端口A，BCU2控制A');
INSERT INTO `t_comid_object` VALUES ('2184', '2212', '172.16.11.123', null, null, '8', null, '1/8车TBM源端口B，BCU2控制B');
INSERT INTO `t_comid_object` VALUES ('2185', '2213', '172.16.1.122', null, null, '8', null, '1/8车SBM源端口A，BCU1控制A');
INSERT INTO `t_comid_object` VALUES ('2186', '2213', '172.16.11.122', null, null, '8', null, '1/8车SBM源端口B，BCU1控制B');
INSERT INTO `t_comid_object` VALUES ('2187', '2213', '172.16.1.123', null, null, '8', null, '1/8车SBM源端口A，BCU2控制A');
INSERT INTO `t_comid_object` VALUES ('2188', '2213', '172.16.11.123', null, null, '8', null, '1/8车SBM源端口B，BCU2控制B');
INSERT INTO `t_comid_object` VALUES ('2189', '2310', '172.16.1.130', null, null, '8', null, 'X车HVAC源端口A');
INSERT INTO `t_comid_object` VALUES ('2190', '2320', '172.16.2.130', null, null, '7', null, 'X车HVAC源端口A');
INSERT INTO `t_comid_object` VALUES ('2191', '2330', '172.16.3.130', null, null, '6', null, 'X车HVAC源端口A');
INSERT INTO `t_comid_object` VALUES ('2192', '2340', '172.16.4.130', null, null, '5', null, 'X车HVAC源端口A');
INSERT INTO `t_comid_object` VALUES ('2193', '2311', '172.16.1.131', null, null, '8', null, 'X车HVAC源端口B');
INSERT INTO `t_comid_object` VALUES ('2194', '2321', '172.16.2.131', null, null, '7', null, 'X车HVAC源端口B');
INSERT INTO `t_comid_object` VALUES ('2195', '2331', '172.16.3.131', null, null, '6', null, 'X车HVAC源端口B');
INSERT INTO `t_comid_object` VALUES ('2196', '2341', '172.16.4.131', null, null, '5', null, 'X车HVAC源端口B');
INSERT INTO `t_comid_object` VALUES ('2197', '2410', '172.16.1.140', null, null, '8', null, 'X车EDCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2198', '2420', '172.16.2.140', null, null, '7', null, 'X车EDCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2199', '2430', '172.16.3.140', null, null, '6', null, 'X车EDCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2200', '2440', '172.16.4.140', null, null, '5', null, 'X车EDCU1源端口A');
INSERT INTO `t_comid_object` VALUES ('2201', '2411', '172.16.11.141', null, null, '8', null, 'X车EDCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2202', '2421', '172.16.12.141', null, null, '7', null, 'X车EDCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2203', '2431', '172.16.13.141', null, null, '6', null, 'X车EDCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2204', '2441', '172.16.14.141', null, null, '5', null, 'X车EDCU1源端口B');
INSERT INTO `t_comid_object` VALUES ('2205', '2418', '172.16.11.141', null, null, '8', null, 'X车EDCU1发送PHM主机源端口');
INSERT INTO `t_comid_object` VALUES ('2206', '2428', '172.16.12.141', null, null, '7', null, 'X车EDCU1发送PHM主机源端口');
INSERT INTO `t_comid_object` VALUES ('2207', '2438', '172.16.13.141', null, null, '6', null, 'X车EDCU1发送PHM主机源端口');
INSERT INTO `t_comid_object` VALUES ('2208', '2448', '172.16.14.141', null, null, '5', null, 'X车EDCU1发送PHM主机源端口');
INSERT INTO `t_comid_object` VALUES ('2209', '2510', '172.16.1.150', null, null, '8', null, 'X车FAS源端口A');
INSERT INTO `t_comid_object` VALUES ('2210', '2520', '172.16.2.150', null, null, '7', null, 'X车FAS源端口A');
INSERT INTO `t_comid_object` VALUES ('2211', '2530', '172.16.3.150', null, null, '6', null, 'X车FAS源端口A');
INSERT INTO `t_comid_object` VALUES ('2212', '2540', '172.16.4.150', null, null, '5', null, 'X车FAS源端口A');
INSERT INTO `t_comid_object` VALUES ('2213', '2511', '172.16.11.151', null, null, '8', null, 'X车FAS源端口B');
INSERT INTO `t_comid_object` VALUES ('2214', '2521', '172.16.12.151', null, null, '7', null, 'X车FAS源端口B');
INSERT INTO `t_comid_object` VALUES ('2215', '2531', '172.16.13.151', null, null, '6', null, 'X车FAS源端口B');
INSERT INTO `t_comid_object` VALUES ('2216', '2541', '172.16.14.151', null, null, '5', null, 'X车FAS源端口B');
INSERT INTO `t_comid_object` VALUES ('2217', '2610', '172.16.1.164', null, null, '8', null, 'X车安全监控源端口1');
INSERT INTO `t_comid_object` VALUES ('2218', '2620', '172.16.2.164', null, null, '7', null, 'X车安全监控源端口1');
INSERT INTO `t_comid_object` VALUES ('2219', '2630', '172.16.3.164', null, null, '6', null, 'X车安全监控源端口1');
INSERT INTO `t_comid_object` VALUES ('2220', '2640', '172.16.4.164', null, null, '5', null, 'X车安全监控源端口1');
INSERT INTO `t_comid_object` VALUES ('2221', '2611', '172.16.11.165', null, null, '8', null, 'X车安全监控源端口2');
INSERT INTO `t_comid_object` VALUES ('2222', '2621', '172.16.12.165', null, null, '7', null, 'X车安全监控源端口2');
INSERT INTO `t_comid_object` VALUES ('2223', '2631', '172.16.13.165', null, null, '6', null, 'X车安全监控源端口2');
INSERT INTO `t_comid_object` VALUES ('2224', '2641', '172.16.14.165', null, null, '5', null, 'X车安全监控源端口2');
INSERT INTO `t_comid_object` VALUES ('2225', '2810', '172.16.1.180', null, null, '8', null, 'X车BC1源端口1A');
INSERT INTO `t_comid_object` VALUES ('2226', '2812', '172.16.11.182', null, null, '8', null, 'X车BC1源端口1B');
INSERT INTO `t_comid_object` VALUES ('2227', '2811', '172.16.1.181', null, null, '8', null, 'X车BC2源端口2A');
INSERT INTO `t_comid_object` VALUES ('2228', '2813', '172.16.11.183', null, null, '8', null, 'X车BC2源端口2B');
INSERT INTO `t_comid_object` VALUES ('2229', '2930', '172.16.3.190', null, null, '6', null, '3/6车PCU控制1源端口');
INSERT INTO `t_comid_object` VALUES ('2230', '2931', '172.16.13.191', null, null, '6', null, '3/6车PCU控制2源端口');
INSERT INTO `t_comid_object` VALUES ('2231', '3110', '172.16.1.210', null, null, '8', null, '1/8车WTD源端口A');
INSERT INTO `t_comid_object` VALUES ('2232', '3111', '172.16.11.211', null, null, '8', null, '1/8车WTD源端口B');
INSERT INTO `t_comid_object` VALUES ('2233', '3112', '172.16.11.210', null, null, '8', null, '1/8车WTD落地数据发送网络源端口');
INSERT INTO `t_comid_object` VALUES ('2234', '3330', '172.16.3.230', null, null, '6', null, '3/6车EEMS源端口');
INSERT INTO `t_comid_object` VALUES ('2235', '3410', '172.16.1.240', null, null, '8', null, '1/8车ATO1/源端口A');
INSERT INTO `t_comid_object` VALUES ('2236', '3411', '172.16.11.241', null, null, '8', null, '1/8车ATO2源端口B');
INSERT INTO `t_comid_object` VALUES ('2237', '3410', '172.16.1.242', null, null, '8', null, '1/8车ATO3源端口A');
INSERT INTO `t_comid_object` VALUES ('2238', '3411', '172.16.11.243', null, null, '8', null, '1/8车ATO4源端口B');
INSERT INTO `t_comid_object` VALUES ('2239', '3510', '172.16.1.90', null, null, '8', null, 'X车EADS源端口');
INSERT INTO `t_comid_object` VALUES ('2240', '3520', '172.16.2.90', null, null, '7', null, 'X车EADS源端口');
INSERT INTO `t_comid_object` VALUES ('2241', '3530', '172.16.3.90', null, null, '6', null, 'X车EADS源端口');
INSERT INTO `t_comid_object` VALUES ('2242', '3540', '172.16.4.90', null, null, '5', null, 'X车EADS源端口');
INSERT INTO `t_comid_object` VALUES ('2243', '3550', '172.16.5.90', null, null, '8', null, '1/8车总配电柜EADS的X=5');
INSERT INTO `t_comid_object` VALUES ('2244', '3012', '172.16.1.202', null, null, '8', null, 'X车给水卫生控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2245', '3022', '172.16.2.202', null, null, '7', null, 'X车给水卫生控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2246', '3032', '172.16.3.202', null, null, '6', null, 'X车给水卫生控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2247', '3042', '172.16.4.202', null, null, '5', null, 'X车给水卫生控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2248', '3013', '172.16.1.203', null, null, '8', null, 'X车智能照明控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2249', '3023', '172.16.2.203', null, null, '7', null, 'X车智能照明控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2250', '3033', '172.16.3.203', null, null, '6', null, 'X车智能照明控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2251', '3043', '172.16.4.203', null, null, '5', null, 'X车智能照明控制器源端口');
INSERT INTO `t_comid_object` VALUES ('2252', '8511', '172.16.1.51', null, null, '8', null, '1车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2253', '8512', '172.16.1.52', null, null, '8', null, '1车SRU板卡2数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2254', '8513', '172.16.1.53', null, null, '8', null, '1车SRU板卡3数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2255', '8514', '172.16.1.54', null, null, '8', null, '1车SRU板卡4数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2256', '8520', '172.16.2.50', null, null, '7', null, '2车SRU-BN');
INSERT INTO `t_comid_object` VALUES ('2257', '8521', '172.16.2.51', null, null, '7', null, '2车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2258', '8522', '172.16.2.52', null, null, '7', null, '2车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2259', '8530', '172.16.3.50', null, null, '6', null, '3车SRU-BN');
INSERT INTO `t_comid_object` VALUES ('2260', '8531', '172.16.3.51', null, null, '6', null, '3车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2261', '8532', '172.16.3.52', null, null, '6', null, '3车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2262', '8540', '172.16.4.50', null, null, '5', null, '4车SRU-BN');
INSERT INTO `t_comid_object` VALUES ('2263', '8541', '172.16.4.51', null, null, '5', null, '4车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2264', '8542', '172.16.4.52', null, null, '5', null, '4车SRU板卡1数据：SRU-CN');
INSERT INTO `t_comid_object` VALUES ('2265', '8601', '172.16.0.1', null, null, '8', null, 'ETBN1交换机数据');
INSERT INTO `t_comid_object` VALUES ('2266', '8602', '172.16.0.2', null, null, '8', null, 'ETBN2交换机数据');
INSERT INTO `t_comid_object` VALUES ('2267', '1101', '172.16.1.10', null, null, '8', null, '主备监听（网关1）');
INSERT INTO `t_comid_object` VALUES ('2268', '1103', '172.16.1.11', null, null, '8', null, '主备监听（网关2）');

-- ----------------------------
-- Table structure for `t_condition`
-- ----------------------------
DROP TABLE IF EXISTS `t_condition`;
CREATE TABLE `t_condition` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `condition_name` varchar(50) NOT NULL COMMENT '条件名称',
  `expression` varchar(255) NOT NULL COMMENT '逻辑表达式',
  `type` varchar(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_condition
-- ----------------------------
INSERT INTO `t_condition` VALUES ('3', '22', 'lostRatePHM=1', 'ComId');
INSERT INTO `t_condition` VALUES ('4', 'cs2', 'linkPHM=0', 'CsPort');
INSERT INTO `t_condition` VALUES ('6', '11', 'lostRate>2&&periodStd<4&&lostRate<3', 'ComId');
INSERT INTO `t_condition` VALUES ('7', '33', 'periodStabilityPHM>1&&periodStd<1&&periodMean>1', 'ComId');

-- ----------------------------
-- Table structure for `t_csport_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_csport_data`;
CREATE TABLE `t_csport_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `ip` varchar(15) NOT NULL,
  `com_id` int(10) unsigned NOT NULL,
  `port` int(10) unsigned NOT NULL,
  `link_p_h_m` tinyint(4) NOT NULL,
  `link_flash_p_h_m` tinyint(4) NOT NULL,
  `rx_traffic_p_h_m` tinyint(4) NOT NULL,
  `rx_err_rate_p_h_m` tinyint(4) NOT NULL,
  `rx_err_predict_p_h_m` tinyint(4) NOT NULL,
  `tx_traffic_p_h_m` tinyint(4) NOT NULL,
  `tx_err_rate_p_h_m` tinyint(4) NOT NULL,
  `tx_err_predict_p_h_m` tinyint(4) NOT NULL,
  `enable` tinyint(4) NOT NULL,
  `link_mean` float NOT NULL,
  `rx_mcast` int(11) NOT NULL,
  `rx_traffic_mean` int(11) NOT NULL,
  `rx_traffic_std` int(11) NOT NULL,
  `rx_err_rate_mean` int(11) NOT NULL,
  `rx_err_rate_std` int(11) NOT NULL,
  `tx_mcast` int(11) NOT NULL,
  `tx_traffic_mean` int(11) NOT NULL,
  `tx_traffic_std` int(11) NOT NULL,
  `tx_err_rate_mean` int(11) NOT NULL,
  `tx_err_rate_std` int(11) NOT NULL,
  PRIMARY KEY (`id`,`date`),
  KEY `date` (`date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=145441 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC
/*!50100 PARTITION BY RANGE (to_days(date))
(PARTITION p20200225 VALUES LESS THAN (737845) ENGINE = InnoDB,
 PARTITION p20200226 VALUES LESS THAN (737846) ENGINE = InnoDB,
 PARTITION p20200227 VALUES LESS THAN (737847) ENGINE = InnoDB,
 PARTITION p20200228 VALUES LESS THAN (737848) ENGINE = InnoDB,
 PARTITION p20200229 VALUES LESS THAN (737849) ENGINE = InnoDB,
 PARTITION p20200301 VALUES LESS THAN (737850) ENGINE = InnoDB,
 PARTITION p20200302 VALUES LESS THAN (737851) ENGINE = InnoDB,
 PARTITION p20200303 VALUES LESS THAN (737852) ENGINE = InnoDB,
 PARTITION p20200304 VALUES LESS THAN (737853) ENGINE = InnoDB,
 PARTITION p20200305 VALUES LESS THAN (737854) ENGINE = InnoDB,
 PARTITION p20200306 VALUES LESS THAN (737855) ENGINE = InnoDB,
 PARTITION p20200307 VALUES LESS THAN (737856) ENGINE = InnoDB,
 PARTITION p20200308 VALUES LESS THAN (737857) ENGINE = InnoDB,
 PARTITION p20200309 VALUES LESS THAN (737858) ENGINE = InnoDB,
 PARTITION p20200310 VALUES LESS THAN (737859) ENGINE = InnoDB,
 PARTITION p20200311 VALUES LESS THAN (737860) ENGINE = InnoDB,
 PARTITION p20200312 VALUES LESS THAN (737861) ENGINE = InnoDB,
 PARTITION p20200313 VALUES LESS THAN (737862) ENGINE = InnoDB,
 PARTITION p20200314 VALUES LESS THAN (737863) ENGINE = InnoDB,
 PARTITION p20200315 VALUES LESS THAN (737864) ENGINE = InnoDB,
 PARTITION p20200316 VALUES LESS THAN (737865) ENGINE = InnoDB,
 PARTITION p20200317 VALUES LESS THAN (737866) ENGINE = InnoDB,
 PARTITION p20200318 VALUES LESS THAN (737867) ENGINE = InnoDB,
 PARTITION p20200319 VALUES LESS THAN (737868) ENGINE = InnoDB,
 PARTITION p20200320 VALUES LESS THAN (737869) ENGINE = InnoDB,
 PARTITION p20200321 VALUES LESS THAN (737870) ENGINE = InnoDB,
 PARTITION p20200322 VALUES LESS THAN (737871) ENGINE = InnoDB,
 PARTITION p20200323 VALUES LESS THAN (737872) ENGINE = InnoDB,
 PARTITION p20200324 VALUES LESS THAN (737873) ENGINE = InnoDB,
 PARTITION p20200325 VALUES LESS THAN (737874) ENGINE = InnoDB,
 PARTITION p20200326 VALUES LESS THAN (737875) ENGINE = InnoDB,
 PARTITION p20200327 VALUES LESS THAN (737876) ENGINE = InnoDB,
 PARTITION p20200328 VALUES LESS THAN (737877) ENGINE = InnoDB,
 PARTITION p20200329 VALUES LESS THAN (737878) ENGINE = InnoDB,
 PARTITION p20200330 VALUES LESS THAN (737879) ENGINE = InnoDB,
 PARTITION p20200331 VALUES LESS THAN (737880) ENGINE = InnoDB,
 PARTITION p20200401 VALUES LESS THAN (737881) ENGINE = InnoDB,
 PARTITION p20200402 VALUES LESS THAN (737882) ENGINE = InnoDB,
 PARTITION p20200403 VALUES LESS THAN (737883) ENGINE = InnoDB,
 PARTITION p20200404 VALUES LESS THAN (737884) ENGINE = InnoDB,
 PARTITION p20200405 VALUES LESS THAN (737885) ENGINE = InnoDB,
 PARTITION p20200406 VALUES LESS THAN (737886) ENGINE = InnoDB,
 PARTITION p20200407 VALUES LESS THAN (737887) ENGINE = InnoDB,
 PARTITION p20200408 VALUES LESS THAN (737888) ENGINE = InnoDB,
 PARTITION p20200409 VALUES LESS THAN (737889) ENGINE = InnoDB,
 PARTITION p20200410 VALUES LESS THAN (737890) ENGINE = InnoDB,
 PARTITION p20200411 VALUES LESS THAN (737891) ENGINE = InnoDB,
 PARTITION p20200412 VALUES LESS THAN (737892) ENGINE = InnoDB,
 PARTITION p20200413 VALUES LESS THAN (737893) ENGINE = InnoDB,
 PARTITION p20200414 VALUES LESS THAN (737894) ENGINE = InnoDB,
 PARTITION p20200415 VALUES LESS THAN (737895) ENGINE = InnoDB,
 PARTITION p20200416 VALUES LESS THAN (737896) ENGINE = InnoDB,
 PARTITION p20200417 VALUES LESS THAN (737897) ENGINE = InnoDB,
 PARTITION p20200418 VALUES LESS THAN (737898) ENGINE = InnoDB,
 PARTITION p20200419 VALUES LESS THAN (737899) ENGINE = InnoDB,
 PARTITION p20200420 VALUES LESS THAN (737900) ENGINE = InnoDB,
 PARTITION p20200421 VALUES LESS THAN (737901) ENGINE = InnoDB,
 PARTITION p20200422 VALUES LESS THAN (737902) ENGINE = InnoDB,
 PARTITION p20200423 VALUES LESS THAN (737903) ENGINE = InnoDB,
 PARTITION p20200424 VALUES LESS THAN (737904) ENGINE = InnoDB,
 PARTITION p20200425 VALUES LESS THAN (737905) ENGINE = InnoDB,
 PARTITION p20200426 VALUES LESS THAN (737906) ENGINE = InnoDB,
 PARTITION p20200427 VALUES LESS THAN (737907) ENGINE = InnoDB,
 PARTITION p20200428 VALUES LESS THAN (737908) ENGINE = InnoDB,
 PARTITION p20200429 VALUES LESS THAN (737909) ENGINE = InnoDB,
 PARTITION p20200430 VALUES LESS THAN (737910) ENGINE = InnoDB,
 PARTITION p20200501 VALUES LESS THAN (737911) ENGINE = InnoDB,
 PARTITION p20200502 VALUES LESS THAN (737912) ENGINE = InnoDB,
 PARTITION p20200503 VALUES LESS THAN (737913) ENGINE = InnoDB,
 PARTITION p20200504 VALUES LESS THAN (737914) ENGINE = InnoDB,
 PARTITION p20200505 VALUES LESS THAN (737915) ENGINE = InnoDB) */;

-- ----------------------------
-- Records of t_csport_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_csport_object`
-- ----------------------------
DROP TABLE IF EXISTS `t_csport_object`;
CREATE TABLE `t_csport_object` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `com_id` int(11) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `train_no` int(11) NOT NULL COMMENT '车号',
  `card_no` int(11) NOT NULL COMMENT '板卡号',
  `port` int(11) NOT NULL COMMENT '端口号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1321 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_csport_object
-- ----------------------------
INSERT INTO `t_csport_object` VALUES ('1001', '8511', '10.0.1.51', '1', '1', '1');
INSERT INTO `t_csport_object` VALUES ('1002', '8511', '10.0.1.51', '1', '1', '2');
INSERT INTO `t_csport_object` VALUES ('1003', '8511', '10.0.1.51', '1', '1', '3');
INSERT INTO `t_csport_object` VALUES ('1004', '8511', '10.0.1.51', '1', '1', '4');
INSERT INTO `t_csport_object` VALUES ('1005', '8511', '10.0.1.51', '1', '1', '5');
INSERT INTO `t_csport_object` VALUES ('1006', '8511', '10.0.1.51', '1', '1', '6');
INSERT INTO `t_csport_object` VALUES ('1007', '8511', '10.0.1.51', '1', '1', '7');
INSERT INTO `t_csport_object` VALUES ('1008', '8511', '10.0.1.51', '1', '1', '8');
INSERT INTO `t_csport_object` VALUES ('1009', '8511', '10.0.1.51', '1', '1', '9');
INSERT INTO `t_csport_object` VALUES ('1010', '8511', '10.0.1.51', '1', '1', '10');
INSERT INTO `t_csport_object` VALUES ('1011', '8511', '10.0.1.51', '1', '1', '11');
INSERT INTO `t_csport_object` VALUES ('1012', '8511', '10.0.1.51', '1', '1', '12');
INSERT INTO `t_csport_object` VALUES ('1013', '8511', '10.0.1.51', '1', '1', '13');
INSERT INTO `t_csport_object` VALUES ('1014', '8511', '10.0.1.51', '1', '1', '14');
INSERT INTO `t_csport_object` VALUES ('1015', '8511', '10.0.1.51', '1', '1', '15');
INSERT INTO `t_csport_object` VALUES ('1016', '8511', '10.0.1.51', '1', '1', '16');
INSERT INTO `t_csport_object` VALUES ('1017', '8512', '10.0.1.52', '1', '2', '1');
INSERT INTO `t_csport_object` VALUES ('1018', '8512', '10.0.1.52', '1', '2', '2');
INSERT INTO `t_csport_object` VALUES ('1019', '8512', '10.0.1.52', '1', '2', '3');
INSERT INTO `t_csport_object` VALUES ('1020', '8512', '10.0.1.52', '1', '2', '4');
INSERT INTO `t_csport_object` VALUES ('1021', '8512', '10.0.1.52', '1', '2', '5');
INSERT INTO `t_csport_object` VALUES ('1022', '8512', '10.0.1.52', '1', '2', '6');
INSERT INTO `t_csport_object` VALUES ('1023', '8512', '10.0.1.52', '1', '2', '7');
INSERT INTO `t_csport_object` VALUES ('1024', '8512', '10.0.1.52', '1', '2', '8');
INSERT INTO `t_csport_object` VALUES ('1025', '8512', '10.0.1.52', '1', '2', '9');
INSERT INTO `t_csport_object` VALUES ('1026', '8512', '10.0.1.52', '1', '2', '10');
INSERT INTO `t_csport_object` VALUES ('1027', '8512', '10.0.1.52', '1', '2', '11');
INSERT INTO `t_csport_object` VALUES ('1028', '8512', '10.0.1.52', '1', '2', '12');
INSERT INTO `t_csport_object` VALUES ('1029', '8512', '10.0.1.52', '1', '2', '13');
INSERT INTO `t_csport_object` VALUES ('1030', '8512', '10.0.1.52', '1', '2', '14');
INSERT INTO `t_csport_object` VALUES ('1031', '8512', '10.0.1.52', '1', '2', '15');
INSERT INTO `t_csport_object` VALUES ('1032', '8512', '10.0.1.52', '1', '2', '16');
INSERT INTO `t_csport_object` VALUES ('1033', '8513', '10.0.1.53', '1', '3', '1');
INSERT INTO `t_csport_object` VALUES ('1034', '8513', '10.0.1.53', '1', '3', '2');
INSERT INTO `t_csport_object` VALUES ('1035', '8513', '10.0.1.53', '1', '3', '3');
INSERT INTO `t_csport_object` VALUES ('1036', '8513', '10.0.1.53', '1', '3', '4');
INSERT INTO `t_csport_object` VALUES ('1037', '8513', '10.0.1.53', '1', '3', '5');
INSERT INTO `t_csport_object` VALUES ('1038', '8513', '10.0.1.53', '1', '3', '6');
INSERT INTO `t_csport_object` VALUES ('1039', '8513', '10.0.1.53', '1', '3', '7');
INSERT INTO `t_csport_object` VALUES ('1040', '8513', '10.0.1.53', '1', '3', '8');
INSERT INTO `t_csport_object` VALUES ('1041', '8513', '10.0.1.53', '1', '3', '9');
INSERT INTO `t_csport_object` VALUES ('1042', '8513', '10.0.1.53', '1', '3', '10');
INSERT INTO `t_csport_object` VALUES ('1043', '8513', '10.0.1.53', '1', '3', '11');
INSERT INTO `t_csport_object` VALUES ('1044', '8513', '10.0.1.53', '1', '3', '12');
INSERT INTO `t_csport_object` VALUES ('1045', '8513', '10.0.1.53', '1', '3', '13');
INSERT INTO `t_csport_object` VALUES ('1046', '8513', '10.0.1.53', '1', '3', '14');
INSERT INTO `t_csport_object` VALUES ('1047', '8513', '10.0.1.53', '1', '3', '15');
INSERT INTO `t_csport_object` VALUES ('1048', '8513', '10.0.1.53', '1', '3', '16');
INSERT INTO `t_csport_object` VALUES ('1049', '8514', '10.0.1.54', '1', '4', '1');
INSERT INTO `t_csport_object` VALUES ('1050', '8514', '10.0.1.54', '1', '4', '2');
INSERT INTO `t_csport_object` VALUES ('1051', '8514', '10.0.1.54', '1', '4', '3');
INSERT INTO `t_csport_object` VALUES ('1052', '8514', '10.0.1.54', '1', '4', '4');
INSERT INTO `t_csport_object` VALUES ('1053', '8514', '10.0.1.54', '1', '4', '5');
INSERT INTO `t_csport_object` VALUES ('1054', '8514', '10.0.1.54', '1', '4', '6');
INSERT INTO `t_csport_object` VALUES ('1055', '8514', '10.0.1.54', '1', '4', '7');
INSERT INTO `t_csport_object` VALUES ('1056', '8514', '10.0.1.54', '1', '4', '8');
INSERT INTO `t_csport_object` VALUES ('1057', '8514', '10.0.1.54', '1', '4', '9');
INSERT INTO `t_csport_object` VALUES ('1058', '8514', '10.0.1.54', '1', '4', '10');
INSERT INTO `t_csport_object` VALUES ('1059', '8514', '10.0.1.54', '1', '4', '11');
INSERT INTO `t_csport_object` VALUES ('1060', '8514', '10.0.1.54', '1', '4', '12');
INSERT INTO `t_csport_object` VALUES ('1061', '8514', '10.0.1.54', '1', '4', '13');
INSERT INTO `t_csport_object` VALUES ('1062', '8514', '10.0.1.54', '1', '4', '14');
INSERT INTO `t_csport_object` VALUES ('1063', '8514', '10.0.1.54', '1', '4', '15');
INSERT INTO `t_csport_object` VALUES ('1064', '8514', '10.0.1.54', '1', '4', '16');
INSERT INTO `t_csport_object` VALUES ('1065', '8521', '10.0.2.51', '2', '1', '1');
INSERT INTO `t_csport_object` VALUES ('1066', '8521', '10.0.2.51', '2', '1', '2');
INSERT INTO `t_csport_object` VALUES ('1067', '8521', '10.0.2.51', '2', '1', '3');
INSERT INTO `t_csport_object` VALUES ('1068', '8521', '10.0.2.51', '2', '1', '4');
INSERT INTO `t_csport_object` VALUES ('1069', '8521', '10.0.2.51', '2', '1', '5');
INSERT INTO `t_csport_object` VALUES ('1070', '8521', '10.0.2.51', '2', '1', '6');
INSERT INTO `t_csport_object` VALUES ('1071', '8521', '10.0.2.51', '2', '1', '7');
INSERT INTO `t_csport_object` VALUES ('1072', '8521', '10.0.2.51', '2', '1', '8');
INSERT INTO `t_csport_object` VALUES ('1073', '8521', '10.0.2.51', '2', '1', '9');
INSERT INTO `t_csport_object` VALUES ('1074', '8521', '10.0.2.51', '2', '1', '10');
INSERT INTO `t_csport_object` VALUES ('1075', '8521', '10.0.2.51', '2', '1', '11');
INSERT INTO `t_csport_object` VALUES ('1076', '8521', '10.0.2.51', '2', '1', '12');
INSERT INTO `t_csport_object` VALUES ('1077', '8521', '10.0.2.51', '2', '1', '13');
INSERT INTO `t_csport_object` VALUES ('1078', '8521', '10.0.2.51', '2', '1', '14');
INSERT INTO `t_csport_object` VALUES ('1079', '8521', '10.0.2.51', '2', '1', '15');
INSERT INTO `t_csport_object` VALUES ('1080', '8521', '10.0.2.51', '2', '1', '16');
INSERT INTO `t_csport_object` VALUES ('1081', '8522', '10.0.2.52', '2', '2', '1');
INSERT INTO `t_csport_object` VALUES ('1082', '8522', '10.0.2.52', '2', '2', '2');
INSERT INTO `t_csport_object` VALUES ('1083', '8522', '10.0.2.52', '2', '2', '3');
INSERT INTO `t_csport_object` VALUES ('1084', '8522', '10.0.2.52', '2', '2', '4');
INSERT INTO `t_csport_object` VALUES ('1085', '8522', '10.0.2.52', '2', '2', '5');
INSERT INTO `t_csport_object` VALUES ('1086', '8522', '10.0.2.52', '2', '2', '6');
INSERT INTO `t_csport_object` VALUES ('1087', '8522', '10.0.2.52', '2', '2', '7');
INSERT INTO `t_csport_object` VALUES ('1088', '8522', '10.0.2.52', '2', '2', '8');
INSERT INTO `t_csport_object` VALUES ('1089', '8522', '10.0.2.52', '2', '2', '9');
INSERT INTO `t_csport_object` VALUES ('1090', '8522', '10.0.2.52', '2', '2', '10');
INSERT INTO `t_csport_object` VALUES ('1091', '8522', '10.0.2.52', '2', '2', '11');
INSERT INTO `t_csport_object` VALUES ('1092', '8522', '10.0.2.52', '2', '2', '12');
INSERT INTO `t_csport_object` VALUES ('1093', '8522', '10.0.2.52', '2', '2', '13');
INSERT INTO `t_csport_object` VALUES ('1094', '8522', '10.0.2.52', '2', '2', '14');
INSERT INTO `t_csport_object` VALUES ('1095', '8522', '10.0.2.52', '2', '2', '15');
INSERT INTO `t_csport_object` VALUES ('1096', '8522', '10.0.2.52', '2', '2', '16');
INSERT INTO `t_csport_object` VALUES ('1097', '8531', '10.0.3.51', '3', '1', '1');
INSERT INTO `t_csport_object` VALUES ('1098', '8531', '10.0.3.51', '3', '1', '2');
INSERT INTO `t_csport_object` VALUES ('1099', '8531', '10.0.3.51', '3', '1', '3');
INSERT INTO `t_csport_object` VALUES ('1100', '8531', '10.0.3.51', '3', '1', '4');
INSERT INTO `t_csport_object` VALUES ('1101', '8531', '10.0.3.51', '3', '1', '5');
INSERT INTO `t_csport_object` VALUES ('1102', '8531', '10.0.3.51', '3', '1', '6');
INSERT INTO `t_csport_object` VALUES ('1103', '8531', '10.0.3.51', '3', '1', '7');
INSERT INTO `t_csport_object` VALUES ('1104', '8531', '10.0.3.51', '3', '1', '8');
INSERT INTO `t_csport_object` VALUES ('1105', '8531', '10.0.3.51', '3', '1', '9');
INSERT INTO `t_csport_object` VALUES ('1106', '8531', '10.0.3.51', '3', '1', '10');
INSERT INTO `t_csport_object` VALUES ('1107', '8531', '10.0.3.51', '3', '1', '11');
INSERT INTO `t_csport_object` VALUES ('1108', '8531', '10.0.3.51', '3', '1', '12');
INSERT INTO `t_csport_object` VALUES ('1109', '8531', '10.0.3.51', '3', '1', '13');
INSERT INTO `t_csport_object` VALUES ('1110', '8531', '10.0.3.51', '3', '1', '14');
INSERT INTO `t_csport_object` VALUES ('1111', '8531', '10.0.3.51', '3', '1', '15');
INSERT INTO `t_csport_object` VALUES ('1112', '8531', '10.0.3.51', '3', '1', '16');
INSERT INTO `t_csport_object` VALUES ('1113', '8532', '10.0.3.52', '3', '2', '1');
INSERT INTO `t_csport_object` VALUES ('1114', '8532', '10.0.3.52', '3', '2', '2');
INSERT INTO `t_csport_object` VALUES ('1115', '8532', '10.0.3.52', '3', '2', '3');
INSERT INTO `t_csport_object` VALUES ('1116', '8532', '10.0.3.52', '3', '2', '4');
INSERT INTO `t_csport_object` VALUES ('1117', '8532', '10.0.3.52', '3', '2', '5');
INSERT INTO `t_csport_object` VALUES ('1118', '8532', '10.0.3.52', '3', '2', '6');
INSERT INTO `t_csport_object` VALUES ('1119', '8532', '10.0.3.52', '3', '2', '7');
INSERT INTO `t_csport_object` VALUES ('1120', '8532', '10.0.3.52', '3', '2', '8');
INSERT INTO `t_csport_object` VALUES ('1121', '8532', '10.0.3.52', '3', '2', '9');
INSERT INTO `t_csport_object` VALUES ('1122', '8532', '10.0.3.52', '3', '2', '10');
INSERT INTO `t_csport_object` VALUES ('1123', '8532', '10.0.3.52', '3', '2', '11');
INSERT INTO `t_csport_object` VALUES ('1124', '8532', '10.0.3.52', '3', '2', '12');
INSERT INTO `t_csport_object` VALUES ('1125', '8532', '10.0.3.52', '3', '2', '13');
INSERT INTO `t_csport_object` VALUES ('1126', '8532', '10.0.3.52', '3', '2', '14');
INSERT INTO `t_csport_object` VALUES ('1127', '8532', '10.0.3.52', '3', '2', '15');
INSERT INTO `t_csport_object` VALUES ('1128', '8532', '10.0.3.52', '3', '2', '16');
INSERT INTO `t_csport_object` VALUES ('1129', '8541', '10.0.4.51', '4', '1', '1');
INSERT INTO `t_csport_object` VALUES ('1130', '8541', '10.0.4.51', '4', '1', '2');
INSERT INTO `t_csport_object` VALUES ('1131', '8541', '10.0.4.51', '4', '1', '3');
INSERT INTO `t_csport_object` VALUES ('1132', '8541', '10.0.4.51', '4', '1', '4');
INSERT INTO `t_csport_object` VALUES ('1133', '8541', '10.0.4.51', '4', '1', '5');
INSERT INTO `t_csport_object` VALUES ('1134', '8541', '10.0.4.51', '4', '1', '6');
INSERT INTO `t_csport_object` VALUES ('1135', '8541', '10.0.4.51', '4', '1', '7');
INSERT INTO `t_csport_object` VALUES ('1136', '8541', '10.0.4.51', '4', '1', '8');
INSERT INTO `t_csport_object` VALUES ('1137', '8541', '10.0.4.51', '4', '1', '9');
INSERT INTO `t_csport_object` VALUES ('1138', '8541', '10.0.4.51', '4', '1', '10');
INSERT INTO `t_csport_object` VALUES ('1139', '8541', '10.0.4.51', '4', '1', '11');
INSERT INTO `t_csport_object` VALUES ('1140', '8541', '10.0.4.51', '4', '1', '12');
INSERT INTO `t_csport_object` VALUES ('1141', '8541', '10.0.4.51', '4', '1', '13');
INSERT INTO `t_csport_object` VALUES ('1142', '8541', '10.0.4.51', '4', '1', '14');
INSERT INTO `t_csport_object` VALUES ('1143', '8541', '10.0.4.51', '4', '1', '15');
INSERT INTO `t_csport_object` VALUES ('1144', '8541', '10.0.4.51', '4', '1', '16');
INSERT INTO `t_csport_object` VALUES ('1145', '8542', '10.0.4.52', '4', '2', '1');
INSERT INTO `t_csport_object` VALUES ('1146', '8542', '10.0.4.52', '4', '2', '2');
INSERT INTO `t_csport_object` VALUES ('1147', '8542', '10.0.4.52', '4', '2', '3');
INSERT INTO `t_csport_object` VALUES ('1148', '8542', '10.0.4.52', '4', '2', '4');
INSERT INTO `t_csport_object` VALUES ('1149', '8542', '10.0.4.52', '4', '2', '5');
INSERT INTO `t_csport_object` VALUES ('1150', '8542', '10.0.4.52', '4', '2', '6');
INSERT INTO `t_csport_object` VALUES ('1151', '8542', '10.0.4.52', '4', '2', '7');
INSERT INTO `t_csport_object` VALUES ('1152', '8542', '10.0.4.52', '4', '2', '8');
INSERT INTO `t_csport_object` VALUES ('1153', '8542', '10.0.4.52', '4', '2', '9');
INSERT INTO `t_csport_object` VALUES ('1154', '8542', '10.0.4.52', '4', '2', '10');
INSERT INTO `t_csport_object` VALUES ('1155', '8542', '10.0.4.52', '4', '2', '11');
INSERT INTO `t_csport_object` VALUES ('1156', '8542', '10.0.4.52', '4', '2', '12');
INSERT INTO `t_csport_object` VALUES ('1157', '8542', '10.0.4.52', '4', '2', '13');
INSERT INTO `t_csport_object` VALUES ('1158', '8542', '10.0.4.52', '4', '2', '14');
INSERT INTO `t_csport_object` VALUES ('1159', '8542', '10.0.4.52', '4', '2', '15');
INSERT INTO `t_csport_object` VALUES ('1160', '8542', '10.0.4.52', '4', '2', '16');
INSERT INTO `t_csport_object` VALUES ('1161', '8511', '172.16.1.51', '8', '1', '1');
INSERT INTO `t_csport_object` VALUES ('1162', '8511', '172.16.1.51', '8', '1', '2');
INSERT INTO `t_csport_object` VALUES ('1163', '8511', '172.16.1.51', '8', '1', '3');
INSERT INTO `t_csport_object` VALUES ('1164', '8511', '172.16.1.51', '8', '1', '4');
INSERT INTO `t_csport_object` VALUES ('1165', '8511', '172.16.1.51', '8', '1', '5');
INSERT INTO `t_csport_object` VALUES ('1166', '8511', '172.16.1.51', '8', '1', '6');
INSERT INTO `t_csport_object` VALUES ('1167', '8511', '172.16.1.51', '8', '1', '7');
INSERT INTO `t_csport_object` VALUES ('1168', '8511', '172.16.1.51', '8', '1', '8');
INSERT INTO `t_csport_object` VALUES ('1169', '8511', '172.16.1.51', '8', '1', '9');
INSERT INTO `t_csport_object` VALUES ('1170', '8511', '172.16.1.51', '8', '1', '10');
INSERT INTO `t_csport_object` VALUES ('1171', '8511', '172.16.1.51', '8', '1', '11');
INSERT INTO `t_csport_object` VALUES ('1172', '8511', '172.16.1.51', '8', '1', '12');
INSERT INTO `t_csport_object` VALUES ('1173', '8511', '172.16.1.51', '8', '1', '13');
INSERT INTO `t_csport_object` VALUES ('1174', '8511', '172.16.1.51', '8', '1', '14');
INSERT INTO `t_csport_object` VALUES ('1175', '8511', '172.16.1.51', '8', '1', '15');
INSERT INTO `t_csport_object` VALUES ('1176', '8511', '172.16.1.51', '8', '1', '16');
INSERT INTO `t_csport_object` VALUES ('1177', '8512', '172.16.1.52', '8', '2', '1');
INSERT INTO `t_csport_object` VALUES ('1178', '8512', '172.16.1.52', '8', '2', '2');
INSERT INTO `t_csport_object` VALUES ('1179', '8512', '172.16.1.52', '8', '2', '3');
INSERT INTO `t_csport_object` VALUES ('1180', '8512', '172.16.1.52', '8', '2', '4');
INSERT INTO `t_csport_object` VALUES ('1181', '8512', '172.16.1.52', '8', '2', '5');
INSERT INTO `t_csport_object` VALUES ('1182', '8512', '172.16.1.52', '8', '2', '6');
INSERT INTO `t_csport_object` VALUES ('1183', '8512', '172.16.1.52', '8', '2', '7');
INSERT INTO `t_csport_object` VALUES ('1184', '8512', '172.16.1.52', '8', '2', '8');
INSERT INTO `t_csport_object` VALUES ('1185', '8512', '172.16.1.52', '8', '2', '9');
INSERT INTO `t_csport_object` VALUES ('1186', '8512', '172.16.1.52', '8', '2', '10');
INSERT INTO `t_csport_object` VALUES ('1187', '8512', '172.16.1.52', '8', '2', '11');
INSERT INTO `t_csport_object` VALUES ('1188', '8512', '172.16.1.52', '8', '2', '12');
INSERT INTO `t_csport_object` VALUES ('1189', '8512', '172.16.1.52', '8', '2', '13');
INSERT INTO `t_csport_object` VALUES ('1190', '8512', '172.16.1.52', '8', '2', '14');
INSERT INTO `t_csport_object` VALUES ('1191', '8512', '172.16.1.52', '8', '2', '15');
INSERT INTO `t_csport_object` VALUES ('1192', '8512', '172.16.1.52', '8', '2', '16');
INSERT INTO `t_csport_object` VALUES ('1193', '8513', '172.16.1.53', '8', '3', '1');
INSERT INTO `t_csport_object` VALUES ('1194', '8513', '172.16.1.53', '8', '3', '2');
INSERT INTO `t_csport_object` VALUES ('1195', '8513', '172.16.1.53', '8', '3', '3');
INSERT INTO `t_csport_object` VALUES ('1196', '8513', '172.16.1.53', '8', '3', '4');
INSERT INTO `t_csport_object` VALUES ('1197', '8513', '172.16.1.53', '8', '3', '5');
INSERT INTO `t_csport_object` VALUES ('1198', '8513', '172.16.1.53', '8', '3', '6');
INSERT INTO `t_csport_object` VALUES ('1199', '8513', '172.16.1.53', '8', '3', '7');
INSERT INTO `t_csport_object` VALUES ('1200', '8513', '172.16.1.53', '8', '3', '8');
INSERT INTO `t_csport_object` VALUES ('1201', '8513', '172.16.1.53', '8', '3', '9');
INSERT INTO `t_csport_object` VALUES ('1202', '8513', '172.16.1.53', '8', '3', '10');
INSERT INTO `t_csport_object` VALUES ('1203', '8513', '172.16.1.53', '8', '3', '11');
INSERT INTO `t_csport_object` VALUES ('1204', '8513', '172.16.1.53', '8', '3', '12');
INSERT INTO `t_csport_object` VALUES ('1205', '8513', '172.16.1.53', '8', '3', '13');
INSERT INTO `t_csport_object` VALUES ('1206', '8513', '172.16.1.53', '8', '3', '14');
INSERT INTO `t_csport_object` VALUES ('1207', '8513', '172.16.1.53', '8', '3', '15');
INSERT INTO `t_csport_object` VALUES ('1208', '8513', '172.16.1.53', '8', '3', '16');
INSERT INTO `t_csport_object` VALUES ('1209', '8514', '172.16.1.54', '8', '4', '1');
INSERT INTO `t_csport_object` VALUES ('1210', '8514', '172.16.1.54', '8', '4', '2');
INSERT INTO `t_csport_object` VALUES ('1211', '8514', '172.16.1.54', '8', '4', '3');
INSERT INTO `t_csport_object` VALUES ('1212', '8514', '172.16.1.54', '8', '4', '4');
INSERT INTO `t_csport_object` VALUES ('1213', '8514', '172.16.1.54', '8', '4', '5');
INSERT INTO `t_csport_object` VALUES ('1214', '8514', '172.16.1.54', '8', '4', '6');
INSERT INTO `t_csport_object` VALUES ('1215', '8514', '172.16.1.54', '8', '4', '7');
INSERT INTO `t_csport_object` VALUES ('1216', '8514', '172.16.1.54', '8', '4', '8');
INSERT INTO `t_csport_object` VALUES ('1217', '8514', '172.16.1.54', '8', '4', '9');
INSERT INTO `t_csport_object` VALUES ('1218', '8514', '172.16.1.54', '8', '4', '10');
INSERT INTO `t_csport_object` VALUES ('1219', '8514', '172.16.1.54', '8', '4', '11');
INSERT INTO `t_csport_object` VALUES ('1220', '8514', '172.16.1.54', '8', '4', '12');
INSERT INTO `t_csport_object` VALUES ('1221', '8514', '172.16.1.54', '8', '4', '13');
INSERT INTO `t_csport_object` VALUES ('1222', '8514', '172.16.1.54', '8', '4', '14');
INSERT INTO `t_csport_object` VALUES ('1223', '8514', '172.16.1.54', '8', '4', '15');
INSERT INTO `t_csport_object` VALUES ('1224', '8514', '172.16.1.54', '8', '4', '16');
INSERT INTO `t_csport_object` VALUES ('1225', '8521', '172.16.2.51', '7', '1', '1');
INSERT INTO `t_csport_object` VALUES ('1226', '8521', '172.16.2.51', '7', '1', '2');
INSERT INTO `t_csport_object` VALUES ('1227', '8521', '172.16.2.51', '7', '1', '3');
INSERT INTO `t_csport_object` VALUES ('1228', '8521', '172.16.2.51', '7', '1', '4');
INSERT INTO `t_csport_object` VALUES ('1229', '8521', '172.16.2.51', '7', '1', '5');
INSERT INTO `t_csport_object` VALUES ('1230', '8521', '172.16.2.51', '7', '1', '6');
INSERT INTO `t_csport_object` VALUES ('1231', '8521', '172.16.2.51', '7', '1', '7');
INSERT INTO `t_csport_object` VALUES ('1232', '8521', '172.16.2.51', '7', '1', '8');
INSERT INTO `t_csport_object` VALUES ('1233', '8521', '172.16.2.51', '7', '1', '9');
INSERT INTO `t_csport_object` VALUES ('1234', '8521', '172.16.2.51', '7', '1', '10');
INSERT INTO `t_csport_object` VALUES ('1235', '8521', '172.16.2.51', '7', '1', '11');
INSERT INTO `t_csport_object` VALUES ('1236', '8521', '172.16.2.51', '7', '1', '12');
INSERT INTO `t_csport_object` VALUES ('1237', '8521', '172.16.2.51', '7', '1', '13');
INSERT INTO `t_csport_object` VALUES ('1238', '8521', '172.16.2.51', '7', '1', '14');
INSERT INTO `t_csport_object` VALUES ('1239', '8521', '172.16.2.51', '7', '1', '15');
INSERT INTO `t_csport_object` VALUES ('1240', '8521', '172.16.2.51', '7', '1', '16');
INSERT INTO `t_csport_object` VALUES ('1241', '8522', '172.16.2.52', '7', '2', '1');
INSERT INTO `t_csport_object` VALUES ('1242', '8522', '172.16.2.52', '7', '2', '2');
INSERT INTO `t_csport_object` VALUES ('1243', '8522', '172.16.2.52', '7', '2', '3');
INSERT INTO `t_csport_object` VALUES ('1244', '8522', '172.16.2.52', '7', '2', '4');
INSERT INTO `t_csport_object` VALUES ('1245', '8522', '172.16.2.52', '7', '2', '5');
INSERT INTO `t_csport_object` VALUES ('1246', '8522', '172.16.2.52', '7', '2', '6');
INSERT INTO `t_csport_object` VALUES ('1247', '8522', '172.16.2.52', '7', '2', '7');
INSERT INTO `t_csport_object` VALUES ('1248', '8522', '172.16.2.52', '7', '2', '8');
INSERT INTO `t_csport_object` VALUES ('1249', '8522', '172.16.2.52', '7', '2', '9');
INSERT INTO `t_csport_object` VALUES ('1250', '8522', '172.16.2.52', '7', '2', '10');
INSERT INTO `t_csport_object` VALUES ('1251', '8522', '172.16.2.52', '7', '2', '11');
INSERT INTO `t_csport_object` VALUES ('1252', '8522', '172.16.2.52', '7', '2', '12');
INSERT INTO `t_csport_object` VALUES ('1253', '8522', '172.16.2.52', '7', '2', '13');
INSERT INTO `t_csport_object` VALUES ('1254', '8522', '172.16.2.52', '7', '2', '14');
INSERT INTO `t_csport_object` VALUES ('1255', '8522', '172.16.2.52', '7', '2', '15');
INSERT INTO `t_csport_object` VALUES ('1256', '8522', '172.16.2.52', '7', '2', '16');
INSERT INTO `t_csport_object` VALUES ('1257', '8531', '172.16.3.51', '6', '1', '1');
INSERT INTO `t_csport_object` VALUES ('1258', '8531', '172.16.3.51', '6', '1', '2');
INSERT INTO `t_csport_object` VALUES ('1259', '8531', '172.16.3.51', '6', '1', '3');
INSERT INTO `t_csport_object` VALUES ('1260', '8531', '172.16.3.51', '6', '1', '4');
INSERT INTO `t_csport_object` VALUES ('1261', '8531', '172.16.3.51', '6', '1', '5');
INSERT INTO `t_csport_object` VALUES ('1262', '8531', '172.16.3.51', '6', '1', '6');
INSERT INTO `t_csport_object` VALUES ('1263', '8531', '172.16.3.51', '6', '1', '7');
INSERT INTO `t_csport_object` VALUES ('1264', '8531', '172.16.3.51', '6', '1', '8');
INSERT INTO `t_csport_object` VALUES ('1265', '8531', '172.16.3.51', '6', '1', '9');
INSERT INTO `t_csport_object` VALUES ('1266', '8531', '172.16.3.51', '6', '1', '10');
INSERT INTO `t_csport_object` VALUES ('1267', '8531', '172.16.3.51', '6', '1', '11');
INSERT INTO `t_csport_object` VALUES ('1268', '8531', '172.16.3.51', '6', '1', '12');
INSERT INTO `t_csport_object` VALUES ('1269', '8531', '172.16.3.51', '6', '1', '13');
INSERT INTO `t_csport_object` VALUES ('1270', '8531', '172.16.3.51', '6', '1', '14');
INSERT INTO `t_csport_object` VALUES ('1271', '8531', '172.16.3.51', '6', '1', '15');
INSERT INTO `t_csport_object` VALUES ('1272', '8531', '172.16.3.51', '6', '1', '16');
INSERT INTO `t_csport_object` VALUES ('1273', '8532', '172.16.3.52', '6', '2', '1');
INSERT INTO `t_csport_object` VALUES ('1274', '8532', '172.16.3.52', '6', '2', '2');
INSERT INTO `t_csport_object` VALUES ('1275', '8532', '172.16.3.52', '6', '2', '3');
INSERT INTO `t_csport_object` VALUES ('1276', '8532', '172.16.3.52', '6', '2', '4');
INSERT INTO `t_csport_object` VALUES ('1277', '8532', '172.16.3.52', '6', '2', '5');
INSERT INTO `t_csport_object` VALUES ('1278', '8532', '172.16.3.52', '6', '2', '6');
INSERT INTO `t_csport_object` VALUES ('1279', '8532', '172.16.3.52', '6', '2', '7');
INSERT INTO `t_csport_object` VALUES ('1280', '8532', '172.16.3.52', '6', '2', '8');
INSERT INTO `t_csport_object` VALUES ('1281', '8532', '172.16.3.52', '6', '2', '9');
INSERT INTO `t_csport_object` VALUES ('1282', '8532', '172.16.3.52', '6', '2', '10');
INSERT INTO `t_csport_object` VALUES ('1283', '8532', '172.16.3.52', '6', '2', '11');
INSERT INTO `t_csport_object` VALUES ('1284', '8532', '172.16.3.52', '6', '2', '12');
INSERT INTO `t_csport_object` VALUES ('1285', '8532', '172.16.3.52', '6', '2', '13');
INSERT INTO `t_csport_object` VALUES ('1286', '8532', '172.16.3.52', '6', '2', '14');
INSERT INTO `t_csport_object` VALUES ('1287', '8532', '172.16.3.52', '6', '2', '15');
INSERT INTO `t_csport_object` VALUES ('1288', '8532', '172.16.3.52', '6', '2', '16');
INSERT INTO `t_csport_object` VALUES ('1289', '8541', '172.16.4.51', '5', '1', '1');
INSERT INTO `t_csport_object` VALUES ('1290', '8541', '172.16.4.51', '5', '1', '2');
INSERT INTO `t_csport_object` VALUES ('1291', '8541', '172.16.4.51', '5', '1', '3');
INSERT INTO `t_csport_object` VALUES ('1292', '8541', '172.16.4.51', '5', '1', '4');
INSERT INTO `t_csport_object` VALUES ('1293', '8541', '172.16.4.51', '5', '1', '5');
INSERT INTO `t_csport_object` VALUES ('1294', '8541', '172.16.4.51', '5', '1', '6');
INSERT INTO `t_csport_object` VALUES ('1295', '8541', '172.16.4.51', '5', '1', '7');
INSERT INTO `t_csport_object` VALUES ('1296', '8541', '172.16.4.51', '5', '1', '8');
INSERT INTO `t_csport_object` VALUES ('1297', '8541', '172.16.4.51', '5', '1', '9');
INSERT INTO `t_csport_object` VALUES ('1298', '8541', '172.16.4.51', '5', '1', '10');
INSERT INTO `t_csport_object` VALUES ('1299', '8541', '172.16.4.51', '5', '1', '11');
INSERT INTO `t_csport_object` VALUES ('1300', '8541', '172.16.4.51', '5', '1', '12');
INSERT INTO `t_csport_object` VALUES ('1301', '8541', '172.16.4.51', '5', '1', '13');
INSERT INTO `t_csport_object` VALUES ('1302', '8541', '172.16.4.51', '5', '1', '14');
INSERT INTO `t_csport_object` VALUES ('1303', '8541', '172.16.4.51', '5', '1', '15');
INSERT INTO `t_csport_object` VALUES ('1304', '8541', '172.16.4.51', '5', '1', '16');
INSERT INTO `t_csport_object` VALUES ('1305', '8542', '172.16.4.52', '5', '2', '1');
INSERT INTO `t_csport_object` VALUES ('1306', '8542', '172.16.4.52', '5', '2', '2');
INSERT INTO `t_csport_object` VALUES ('1307', '8542', '172.16.4.52', '5', '2', '3');
INSERT INTO `t_csport_object` VALUES ('1308', '8542', '172.16.4.52', '5', '2', '4');
INSERT INTO `t_csport_object` VALUES ('1309', '8542', '172.16.4.52', '5', '2', '5');
INSERT INTO `t_csport_object` VALUES ('1310', '8542', '172.16.4.52', '5', '2', '6');
INSERT INTO `t_csport_object` VALUES ('1311', '8542', '172.16.4.52', '5', '2', '7');
INSERT INTO `t_csport_object` VALUES ('1312', '8542', '172.16.4.52', '5', '2', '8');
INSERT INTO `t_csport_object` VALUES ('1313', '8542', '172.16.4.52', '5', '2', '9');
INSERT INTO `t_csport_object` VALUES ('1314', '8542', '172.16.4.52', '5', '2', '10');
INSERT INTO `t_csport_object` VALUES ('1315', '8542', '172.16.4.52', '5', '2', '11');
INSERT INTO `t_csport_object` VALUES ('1316', '8542', '172.16.4.52', '5', '2', '12');
INSERT INTO `t_csport_object` VALUES ('1317', '8542', '172.16.4.52', '5', '2', '13');
INSERT INTO `t_csport_object` VALUES ('1318', '8542', '172.16.4.52', '5', '2', '14');
INSERT INTO `t_csport_object` VALUES ('1319', '8542', '172.16.4.52', '5', '2', '15');
INSERT INTO `t_csport_object` VALUES ('1320', '8542', '172.16.4.52', '5', '2', '16');

-- ----------------------------
-- Table structure for `t_data_overview`
-- ----------------------------
DROP TABLE IF EXISTS `t_data_overview`;
CREATE TABLE `t_data_overview` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(15) NOT NULL,
  `com_id` int(10) unsigned NOT NULL,
  `port` int(10) unsigned NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1929 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_data_overview
-- ----------------------------
INSERT INTO `t_data_overview` VALUES ('1285', '10.0.1.51', '8511', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1286', '10.0.1.51', '8511', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1287', '10.0.1.51', '8511', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1288', '10.0.1.51', '8511', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1289', '10.0.1.51', '8511', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1290', '10.0.1.51', '8511', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1291', '10.0.1.51', '8511', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1292', '10.0.1.51', '8511', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1293', '10.0.1.51', '8511', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1294', '10.0.1.51', '8511', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1295', '10.0.1.51', '8511', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1296', '10.0.1.51', '8511', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1297', '10.0.1.51', '8511', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1298', '10.0.1.51', '8511', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1299', '10.0.1.51', '8511', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1300', '10.0.1.51', '8511', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1301', '10.0.1.52', '8512', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1302', '10.0.1.52', '8512', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1303', '10.0.1.52', '8512', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1304', '10.0.1.52', '8512', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1305', '10.0.1.52', '8512', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1306', '10.0.1.52', '8512', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1307', '10.0.1.52', '8512', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1308', '10.0.1.52', '8512', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1309', '10.0.1.52', '8512', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1310', '10.0.1.52', '8512', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1311', '10.0.1.52', '8512', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1312', '10.0.1.52', '8512', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1313', '10.0.1.52', '8512', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1314', '10.0.1.52', '8512', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1315', '10.0.1.52', '8512', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1316', '10.0.1.52', '8512', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1317', '10.0.1.53', '8513', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1318', '10.0.1.53', '8513', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1319', '10.0.1.53', '8513', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1320', '10.0.1.53', '8513', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1321', '10.0.1.53', '8513', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1322', '10.0.1.53', '8513', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1323', '10.0.1.53', '8513', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1324', '10.0.1.53', '8513', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1325', '10.0.1.53', '8513', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1326', '10.0.1.53', '8513', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1327', '10.0.1.53', '8513', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1328', '10.0.1.53', '8513', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1329', '10.0.1.53', '8513', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1330', '10.0.1.53', '8513', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1331', '10.0.1.53', '8513', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1332', '10.0.1.53', '8513', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1333', '10.0.1.54', '8514', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1334', '10.0.1.54', '8514', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1335', '10.0.1.54', '8514', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1336', '10.0.1.54', '8514', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1337', '10.0.1.54', '8514', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1338', '10.0.1.54', '8514', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1339', '10.0.1.54', '8514', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1340', '10.0.1.54', '8514', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1341', '10.0.1.54', '8514', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1342', '10.0.1.54', '8514', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1343', '10.0.1.54', '8514', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1344', '10.0.1.54', '8514', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1345', '10.0.1.54', '8514', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1346', '10.0.1.54', '8514', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1347', '10.0.1.54', '8514', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1348', '10.0.1.54', '8514', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1349', '10.0.2.51', '8521', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1350', '10.0.2.51', '8521', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1351', '10.0.2.51', '8521', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1352', '10.0.2.51', '8521', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1353', '10.0.2.51', '8521', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1354', '10.0.2.51', '8521', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1355', '10.0.2.51', '8521', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1356', '10.0.2.51', '8521', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1357', '10.0.2.51', '8521', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1358', '10.0.2.51', '8521', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1359', '10.0.2.51', '8521', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1360', '10.0.2.51', '8521', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1361', '10.0.2.51', '8521', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1362', '10.0.2.51', '8521', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1363', '10.0.2.51', '8521', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1364', '10.0.2.51', '8521', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1365', '10.0.2.52', '8522', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1366', '10.0.2.52', '8522', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1367', '10.0.2.52', '8522', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1368', '10.0.2.52', '8522', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1369', '10.0.2.52', '8522', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1370', '10.0.2.52', '8522', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1371', '10.0.2.52', '8522', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1372', '10.0.2.52', '8522', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1373', '10.0.2.52', '8522', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1374', '10.0.2.52', '8522', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1375', '10.0.2.52', '8522', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1376', '10.0.2.52', '8522', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1377', '10.0.2.52', '8522', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1378', '10.0.2.52', '8522', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1379', '10.0.2.52', '8522', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1380', '10.0.2.52', '8522', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1381', '10.0.3.51', '8531', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1382', '10.0.3.51', '8531', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1383', '10.0.3.51', '8531', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1384', '10.0.3.51', '8531', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1385', '10.0.3.51', '8531', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1386', '10.0.3.51', '8531', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1387', '10.0.3.51', '8531', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1388', '10.0.3.51', '8531', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1389', '10.0.3.51', '8531', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1390', '10.0.3.51', '8531', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1391', '10.0.3.51', '8531', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1392', '10.0.3.51', '8531', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1393', '10.0.3.51', '8531', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1394', '10.0.3.51', '8531', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1395', '10.0.3.51', '8531', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1396', '10.0.3.51', '8531', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1397', '10.0.3.52', '8532', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1398', '10.0.3.52', '8532', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1399', '10.0.3.52', '8532', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1400', '10.0.3.52', '8532', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1401', '10.0.3.52', '8532', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1402', '10.0.3.52', '8532', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1403', '10.0.3.52', '8532', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1404', '10.0.3.52', '8532', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1405', '10.0.3.52', '8532', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1406', '10.0.3.52', '8532', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1407', '10.0.3.52', '8532', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1408', '10.0.3.52', '8532', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1409', '10.0.3.52', '8532', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1410', '10.0.3.52', '8532', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1411', '10.0.3.52', '8532', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1412', '10.0.3.52', '8532', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1413', '10.0.4.51', '8541', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1414', '10.0.4.51', '8541', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1415', '10.0.4.51', '8541', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1416', '10.0.4.51', '8541', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1417', '10.0.4.51', '8541', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1418', '10.0.4.51', '8541', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1419', '10.0.4.51', '8541', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1420', '10.0.4.51', '8541', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1421', '10.0.4.51', '8541', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1422', '10.0.4.51', '8541', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1423', '10.0.4.51', '8541', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1424', '10.0.4.51', '8541', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1425', '10.0.4.51', '8541', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1426', '10.0.4.51', '8541', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1427', '10.0.4.51', '8541', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1428', '10.0.4.51', '8541', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1429', '10.0.4.52', '8542', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1430', '10.0.4.52', '8542', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1431', '10.0.4.52', '8542', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1432', '10.0.4.52', '8542', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1433', '10.0.4.52', '8542', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1434', '10.0.4.52', '8542', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1435', '10.0.4.52', '8542', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1436', '10.0.4.52', '8542', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1437', '10.0.4.52', '8542', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1438', '10.0.4.52', '8542', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1439', '10.0.4.52', '8542', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1440', '10.0.4.52', '8542', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1441', '10.0.4.52', '8542', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1442', '10.0.4.52', '8542', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1443', '10.0.4.52', '8542', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1444', '10.0.4.52', '8542', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1445', '172.16.1.51', '8511', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1446', '172.16.1.51', '8511', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1447', '172.16.1.51', '8511', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1448', '172.16.1.51', '8511', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1449', '172.16.1.51', '8511', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1450', '172.16.1.51', '8511', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1451', '172.16.1.51', '8511', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1452', '172.16.1.51', '8511', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1453', '172.16.1.51', '8511', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1454', '172.16.1.51', '8511', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1455', '172.16.1.51', '8511', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1456', '172.16.1.51', '8511', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1457', '172.16.1.51', '8511', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1458', '172.16.1.51', '8511', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1459', '172.16.1.51', '8511', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1460', '172.16.1.51', '8511', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1461', '172.16.1.52', '8512', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1462', '172.16.1.52', '8512', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1463', '172.16.1.52', '8512', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1464', '172.16.1.52', '8512', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1465', '172.16.1.52', '8512', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1466', '172.16.1.52', '8512', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1467', '172.16.1.52', '8512', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1468', '172.16.1.52', '8512', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1469', '172.16.1.52', '8512', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1470', '172.16.1.52', '8512', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1471', '172.16.1.52', '8512', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1472', '172.16.1.52', '8512', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1473', '172.16.1.52', '8512', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1474', '172.16.1.52', '8512', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1475', '172.16.1.52', '8512', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1476', '172.16.1.52', '8512', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1477', '172.16.1.53', '8513', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1478', '172.16.1.53', '8513', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1479', '172.16.1.53', '8513', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1480', '172.16.1.53', '8513', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1481', '172.16.1.53', '8513', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1482', '172.16.1.53', '8513', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1483', '172.16.1.53', '8513', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1484', '172.16.1.53', '8513', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1485', '172.16.1.53', '8513', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1486', '172.16.1.53', '8513', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1487', '172.16.1.53', '8513', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1488', '172.16.1.53', '8513', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1489', '172.16.1.53', '8513', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1490', '172.16.1.53', '8513', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1491', '172.16.1.53', '8513', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1492', '172.16.1.53', '8513', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1493', '172.16.1.54', '8514', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1494', '172.16.1.54', '8514', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1495', '172.16.1.54', '8514', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1496', '172.16.1.54', '8514', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1497', '172.16.1.54', '8514', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1498', '172.16.1.54', '8514', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1499', '172.16.1.54', '8514', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1500', '172.16.1.54', '8514', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1501', '172.16.1.54', '8514', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1502', '172.16.1.54', '8514', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1503', '172.16.1.54', '8514', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1504', '172.16.1.54', '8514', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1505', '172.16.1.54', '8514', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1506', '172.16.1.54', '8514', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1507', '172.16.1.54', '8514', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1508', '172.16.1.54', '8514', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1509', '172.16.2.51', '8521', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1510', '172.16.2.51', '8521', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1511', '172.16.2.51', '8521', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1512', '172.16.2.51', '8521', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1513', '172.16.2.51', '8521', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1514', '172.16.2.51', '8521', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1515', '172.16.2.51', '8521', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1516', '172.16.2.51', '8521', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1517', '172.16.2.51', '8521', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1518', '172.16.2.51', '8521', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1519', '172.16.2.51', '8521', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1520', '172.16.2.51', '8521', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1521', '172.16.2.51', '8521', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1522', '172.16.2.51', '8521', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1523', '172.16.2.51', '8521', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1524', '172.16.2.51', '8521', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1525', '172.16.2.52', '8522', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1526', '172.16.2.52', '8522', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1527', '172.16.2.52', '8522', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1528', '172.16.2.52', '8522', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1529', '172.16.2.52', '8522', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1530', '172.16.2.52', '8522', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1531', '172.16.2.52', '8522', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1532', '172.16.2.52', '8522', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1533', '172.16.2.52', '8522', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1534', '172.16.2.52', '8522', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1535', '172.16.2.52', '8522', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1536', '172.16.2.52', '8522', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1537', '172.16.2.52', '8522', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1538', '172.16.2.52', '8522', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1539', '172.16.2.52', '8522', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1540', '172.16.2.52', '8522', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1541', '172.16.3.51', '8531', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1542', '172.16.3.51', '8531', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1543', '172.16.3.51', '8531', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1544', '172.16.3.51', '8531', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1545', '172.16.3.51', '8531', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1546', '172.16.3.51', '8531', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1547', '172.16.3.51', '8531', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1548', '172.16.3.51', '8531', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1549', '172.16.3.51', '8531', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1550', '172.16.3.51', '8531', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1551', '172.16.3.51', '8531', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1552', '172.16.3.51', '8531', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1553', '172.16.3.51', '8531', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1554', '172.16.3.51', '8531', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1555', '172.16.3.51', '8531', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1556', '172.16.3.51', '8531', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1557', '172.16.3.52', '8532', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1558', '172.16.3.52', '8532', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1559', '172.16.3.52', '8532', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1560', '172.16.3.52', '8532', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1561', '172.16.3.52', '8532', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1562', '172.16.3.52', '8532', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1563', '172.16.3.52', '8532', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1564', '172.16.3.52', '8532', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1565', '172.16.3.52', '8532', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1566', '172.16.3.52', '8532', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1567', '172.16.3.52', '8532', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1568', '172.16.3.52', '8532', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1569', '172.16.3.52', '8532', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1570', '172.16.3.52', '8532', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1571', '172.16.3.52', '8532', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1572', '172.16.3.52', '8532', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1573', '172.16.4.51', '8541', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1574', '172.16.4.51', '8541', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1575', '172.16.4.51', '8541', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1576', '172.16.4.51', '8541', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1577', '172.16.4.51', '8541', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1578', '172.16.4.51', '8541', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1579', '172.16.4.51', '8541', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1580', '172.16.4.51', '8541', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1581', '172.16.4.51', '8541', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1582', '172.16.4.51', '8541', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1583', '172.16.4.51', '8541', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1584', '172.16.4.51', '8541', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1585', '172.16.4.51', '8541', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1586', '172.16.4.51', '8541', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1587', '172.16.4.51', '8541', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1588', '172.16.4.51', '8541', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1589', '172.16.4.52', '8542', '1', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1590', '172.16.4.52', '8542', '2', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1591', '172.16.4.52', '8542', '3', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1592', '172.16.4.52', '8542', '4', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1593', '172.16.4.52', '8542', '5', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1594', '172.16.4.52', '8542', '6', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1595', '172.16.4.52', '8542', '7', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1596', '172.16.4.52', '8542', '8', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1597', '172.16.4.52', '8542', '9', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1598', '172.16.4.52', '8542', '10', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1599', '172.16.4.52', '8542', '11', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1600', '172.16.4.52', '8542', '12', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1601', '172.16.4.52', '8542', '13', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1602', '172.16.4.52', '8542', '14', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1603', '172.16.4.52', '8542', '15', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1604', '172.16.4.52', '8542', '16', '2020-02-17 09:06:25', '2020-02-17 09:07:15');
INSERT INTO `t_data_overview` VALUES ('1605', '10.0.5.30', '5500', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1606', '10.0.15.31', '5600', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1607', '10.0.5.200', '3040', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1608', '10.0.15.201', '3041', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1609', '10.0.5.230', '3340', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1610', '10.0.5.230', '3341', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1611', '10.0.5.60', '3740', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1612', '10.0.5.60', '3741', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1613', '10.0.5.60', '3742', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1614', '10.0.5.60', '3743', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1615', '10.0.1.10', '1001', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1616', '10.0.1.10', '1100', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1617', '10.0.1.10', '1701', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1618', '10.0.1.10', '1702', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1619', '10.0.1.10', '1102', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1620', '10.0.1.10', '1801', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1621', '10.0.1.10', '1802', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1622', '10.0.1.10', '1200', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1623', '10.0.1.10', '1201', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1624', '10.0.1.10', '1301', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1625', '10.0.1.10', '1302', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1626', '10.0.1.10', '1303', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1627', '10.0.1.10', '1304', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1628', '10.0.1.11', '1001', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1629', '10.0.1.11', '1100', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1630', '10.0.1.11', '1701', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1631', '10.0.1.11', '1702', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1632', '10.0.1.11', '1102', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1633', '10.0.1.11', '1801', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1634', '10.0.1.11', '1802', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1635', '10.0.1.11', '1200', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1636', '10.0.1.11', '1201', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1637', '10.0.1.11', '1301', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1638', '10.0.1.11', '1302', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1639', '10.0.1.11', '1303', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1640', '10.0.1.11', '1304', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1641', '10.0.1.40', '1411', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1642', '10.0.2.40', '1421', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1643', '10.0.3.40', '1431', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1644', '10.0.4.40', '1441', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1645', '10.0.11.41', '1412', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1646', '10.0.12.41', '1422', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1647', '10.0.13.41', '1432', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1648', '10.0.14.41', '1442', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1649', '10.0.1.42', '1413', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1650', '10.0.2.42', '1423', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1651', '10.0.3.44', '1453', '0', '2020-02-17 10:45:28', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1652', '10.0.4.42', '1443', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1653', '10.0.11.43', '1414', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1654', '10.0.12.43', '1424', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1655', '10.0.13.45', '1454', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1656', '10.0.14.43', '1444', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1657', '10.0.1.44', '1415', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1658', '10.0.11.45', '1416', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1659', '10.0.1.47', '1425', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1660', '10.0.11.48', '1426', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1661', '10.0.1.30', '1500', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1662', '10.0.1.31', '1600', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:23');
INSERT INTO `t_data_overview` VALUES ('1663', '10.0.1.32', '1550', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1664', '10.0.2.101', '2020', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1665', '10.0.4.101', '2040', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1666', '10.0.2.102', '2021', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1667', '10.0.4.102', '2041', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1668', '10.0.1.122', '2210', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1669', '10.0.2.122', '2220', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1670', '10.0.3.122', '2230', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1671', '10.0.4.122', '2240', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1672', '10.0.11.122', '2210', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1673', '10.0.12.122', '2220', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1674', '10.0.13.122', '2230', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1675', '10.0.14.122', '2240', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1676', '10.0.1.123', '2211', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1677', '10.0.2.123', '2221', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1678', '10.0.3.123', '2231', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1679', '10.0.4.123', '2241', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1680', '10.0.11.123', '2211', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1681', '10.0.12.123', '2221', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1682', '10.0.13.123', '2231', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1683', '10.0.14.123', '2241', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1684', '10.0.1.122', '2212', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1685', '10.0.11.122', '2212', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1686', '10.0.1.123', '2212', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1687', '10.0.11.123', '2212', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1688', '10.0.1.122', '2213', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1689', '10.0.11.122', '2213', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1690', '10.0.1.123', '2213', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1691', '10.0.11.123', '2213', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1692', '10.0.1.130', '2310', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1693', '10.0.2.130', '2320', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1694', '10.0.3.130', '2330', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1695', '10.0.4.130', '2340', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1696', '10.0.1.131', '2311', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1697', '10.0.2.131', '2321', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1698', '10.0.3.131', '2331', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1699', '10.0.4.131', '2341', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1700', '10.0.1.140', '2410', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1701', '10.0.2.140', '2420', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1702', '10.0.3.140', '2430', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1703', '10.0.4.140', '2440', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1704', '10.0.11.141', '2411', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1705', '10.0.12.141', '2421', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1706', '10.0.13.141', '2431', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1707', '10.0.14.141', '2441', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1708', '10.0.11.141', '2418', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1709', '10.0.12.141', '2428', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1710', '10.0.13.141', '2438', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1711', '10.0.14.141', '2448', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1712', '10.0.1.150', '2510', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1713', '10.0.2.150', '2520', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1714', '10.0.3.150', '2530', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1715', '10.0.4.150', '2540', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1716', '10.0.11.151', '2511', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1717', '10.0.12.151', '2521', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1718', '10.0.13.151', '2531', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1719', '10.0.14.151', '2541', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1720', '10.0.1.164', '2610', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1721', '10.0.2.164', '2620', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1722', '10.0.3.164', '2630', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1723', '10.0.4.164', '2640', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1724', '10.0.11.165', '2611', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1725', '10.0.12.165', '2621', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1726', '10.0.13.165', '2631', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1727', '10.0.14.165', '2641', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1728', '10.0.1.180', '2810', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1729', '10.0.11.182', '2812', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1730', '10.0.1.181', '2811', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1731', '10.0.11.183', '2813', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1732', '10.0.3.190', '2930', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1733', '10.0.13.191', '2931', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1734', '10.0.1.210', '3110', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1735', '10.0.11.211', '3111', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1736', '10.0.11.211', '3112', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1737', '10.0.3.230', '3330', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1738', '10.0.1.240', '3410', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1739', '10.0.11.241', '3411', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1740', '10.0.1.242', '3410', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1741', '10.0.11.243', '3411', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1742', '10.0.1.90', '3510', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1743', '10.0.2.90', '3520', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1744', '10.0.3.90', '3530', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1745', '10.0.4.90', '3540', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1746', '10.0.5.90', '3550', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1747', '10.0.1.202', '3012', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1748', '10.0.2.202', '3022', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1749', '10.0.3.202', '3032', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1750', '10.0.4.202', '3042', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1751', '10.0.1.203', '3013', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1752', '10.0.2.203', '3023', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1753', '10.0.3.203', '3033', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1754', '10.0.4.203', '3043', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1755', '10.0.1.51', '8511', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1756', '10.0.1.52', '8512', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1757', '10.0.1.53', '8513', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1758', '10.0.1.54', '8514', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1759', '10.0.2.50', '8520', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1760', '10.0.2.51', '8521', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1761', '10.0.2.52', '8522', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1762', '10.0.3.50', '8530', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1763', '10.0.3.51', '8531', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1764', '10.0.3.52', '8532', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1765', '10.0.4.50', '8540', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1766', '10.0.4.51', '8541', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1767', '10.0.4.52', '8542', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1768', '10.0.0.1', '8601', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1769', '10.0.0.2', '8602', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1770', '10.0.1.10', '1101', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1771', '10.0.1.11', '1103', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1772', '172.16.1.10', '1001', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1773', '172.16.1.10', '1100', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1774', '172.16.1.10', '1701', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1775', '172.16.1.10', '1702', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1776', '172.16.1.10', '1102', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1777', '172.16.1.10', '1801', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1778', '172.16.1.10', '1802', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1779', '172.16.1.10', '1200', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1780', '172.16.1.10', '1201', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1781', '172.16.1.10', '1301', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1782', '172.16.1.10', '1302', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1783', '172.16.1.10', '1303', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1784', '172.16.1.10', '1304', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1785', '172.16.1.11', '1001', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1786', '172.16.1.11', '1100', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1787', '172.16.1.11', '1701', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1788', '172.16.1.11', '1702', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1789', '172.16.1.11', '1102', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1790', '172.16.1.11', '1801', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1791', '172.16.1.11', '1802', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1792', '172.16.1.11', '1200', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1793', '172.16.1.11', '1201', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1794', '172.16.1.11', '1301', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1795', '172.16.1.11', '1302', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1796', '172.16.1.11', '1303', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1797', '172.16.1.11', '1304', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1798', '172.16.1.40', '1411', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1799', '172.16.2.40', '1421', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1800', '172.16.3.40', '1431', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1801', '172.16.4.40', '1441', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1802', '172.16.11.41', '1412', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1803', '172.16.12.41', '1422', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1804', '172.16.13.41', '1432', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1805', '172.16.14.41', '1442', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1806', '172.16.1.42', '1413', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1807', '172.16.2.42', '1423', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1808', '172.16.3.44', '1453', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1809', '172.16.4.42', '1443', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1810', '172.16.11.43', '1414', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1811', '172.16.12.43', '1424', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1812', '172.16.13.45', '1454', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1813', '172.16.14.43', '1444', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1814', '172.16.1.44', '1415', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1815', '172.16.11.45', '1416', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1816', '172.16.1.47', '1425', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1817', '172.16.11.48', '1426', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1818', '172.16.1.30', '1500', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1819', '172.16.1.31', '1600', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1820', '172.16.1.32', '1550', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1821', '172.16.2.101', '2020', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1822', '172.16.4.101', '2040', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1823', '172.16.2.102', '2021', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1824', '172.16.4.102', '2041', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1825', '172.16.1.122', '2210', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1826', '172.16.2.122', '2220', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1827', '172.16.3.122', '2230', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1828', '172.16.4.122', '2240', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1829', '172.16.11.122', '2210', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1830', '172.16.12.122', '2220', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1831', '172.16.13.122', '2230', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1832', '172.16.14.122', '2240', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1833', '172.16.1.123', '2211', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1834', '172.16.2.123', '2221', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1835', '172.16.3.123', '2231', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1836', '172.16.4.123', '2241', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1837', '172.16.11.123', '2211', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1838', '172.16.12.123', '2221', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1839', '172.16.13.123', '2231', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1840', '172.16.14.123', '2241', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1841', '172.16.1.122', '2212', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1842', '172.16.11.122', '2212', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1843', '172.16.1.123', '2212', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1844', '172.16.11.123', '2212', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1845', '172.16.1.122', '2213', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1846', '172.16.11.122', '2213', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1847', '172.16.1.123', '2213', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1848', '172.16.11.123', '2213', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1849', '172.16.1.130', '2310', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1850', '172.16.2.130', '2320', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1851', '172.16.3.130', '2330', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1852', '172.16.4.130', '2340', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1853', '172.16.1.131', '2311', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1854', '172.16.2.131', '2321', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1855', '172.16.3.131', '2331', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1856', '172.16.4.131', '2341', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1857', '172.16.1.140', '2410', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1858', '172.16.2.140', '2420', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1859', '172.16.3.140', '2430', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1860', '172.16.4.140', '2440', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1861', '172.16.11.141', '2411', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1862', '172.16.12.141', '2421', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1863', '172.16.13.141', '2431', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1864', '172.16.14.141', '2441', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1865', '172.16.11.141', '2418', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1866', '172.16.12.141', '2428', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1867', '172.16.13.141', '2438', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1868', '172.16.14.141', '2448', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1869', '172.16.1.150', '2510', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1870', '172.16.2.150', '2520', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1871', '172.16.3.150', '2530', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1872', '172.16.4.150', '2540', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1873', '172.16.11.151', '2511', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1874', '172.16.12.151', '2521', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1875', '172.16.13.151', '2531', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1876', '172.16.14.151', '2541', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1877', '172.16.1.164', '2610', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1878', '172.16.2.164', '2620', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1879', '172.16.3.164', '2630', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1880', '172.16.4.164', '2640', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1881', '172.16.11.165', '2611', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1882', '172.16.12.165', '2621', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1883', '172.16.13.165', '2631', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1884', '172.16.14.165', '2641', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1885', '172.16.1.180', '2810', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1886', '172.16.11.182', '2812', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1887', '172.16.1.181', '2811', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1888', '172.16.11.183', '2813', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1889', '172.16.3.190', '2930', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1890', '172.16.13.191', '2931', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1891', '172.16.1.210', '3110', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1892', '172.16.11.211', '3111', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1893', '172.16.11.211', '3112', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1894', '172.16.3.230', '3330', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1895', '172.16.1.240', '3410', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1896', '172.16.11.241', '3411', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1897', '172.16.1.242', '3410', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1898', '172.16.11.243', '3411', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1899', '172.16.1.90', '3510', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1900', '172.16.2.90', '3520', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1901', '172.16.3.90', '3530', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1902', '172.16.4.90', '3540', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1903', '172.16.5.90', '3550', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1904', '172.16.1.202', '3012', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1905', '172.16.2.202', '3022', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1906', '172.16.3.202', '3032', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1907', '172.16.4.202', '3042', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1908', '172.16.1.203', '3013', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1909', '172.16.2.203', '3023', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1910', '172.16.3.203', '3033', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1911', '172.16.4.203', '3043', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1912', '172.16.1.51', '8511', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1913', '172.16.1.52', '8512', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1914', '172.16.1.53', '8513', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1915', '172.16.1.54', '8514', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1916', '172.16.2.50', '8520', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1917', '172.16.2.51', '8521', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1918', '172.16.2.52', '8522', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1919', '172.16.3.50', '8530', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1920', '172.16.3.51', '8531', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1921', '172.16.3.52', '8532', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1922', '172.16.4.50', '8540', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1923', '172.16.4.51', '8541', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1924', '172.16.4.52', '8542', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1925', '172.16.0.1', '8601', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1926', '172.16.0.2', '8602', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1927', '172.16.1.10', '1101', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');
INSERT INTO `t_data_overview` VALUES ('1928', '172.16.1.11', '1103', '0', '2020-02-17 10:45:29', '2020-02-17 10:46:24');

-- ----------------------------
-- Table structure for `t_downloaded_file`
-- ----------------------------
DROP TABLE IF EXISTS `t_downloaded_file`;
CREATE TABLE `t_downloaded_file` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(34) NOT NULL COMMENT '已下载文件名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=269 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_downloaded_file
-- ----------------------------

-- ----------------------------
-- Table structure for `t_target_config`
-- ----------------------------
DROP TABLE IF EXISTS `t_target_config`;
CREATE TABLE `t_target_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `target_ip` varchar(15) NOT NULL,
  `target_path` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_target_config
-- ----------------------------
INSERT INTO `t_target_config` VALUES ('1', 'localhost', 'E:/test');
