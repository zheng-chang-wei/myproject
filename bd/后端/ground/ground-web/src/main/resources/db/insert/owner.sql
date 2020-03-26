INSERT INTO phm_bd_ground.t_ground_role (GROUND_ROLE_ID, ROLE_NAME, REMARK, CREATE_TIME, MODIFY_TIME)
VALUES
(3, '业主', '业主', '2019-09-19 19:00:00.000', NULL);

INSERT INTO phm_bd_ground.t_menu (MENU_ID, PARENT_ID, MENU_NAME, URL, PERMS, ICON, `TYPE`, ORDER_NUM, CREATE_TIME, MODIFY_TIME)
VALUES
(1, 0, '实时监控', '/realtimemonitor/realtimemonitor', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL),
(2, 0, '故障信息', '/faultinformation/showFaultInformation', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL),
(3, 0, '亚健康预警', '/unhealthymonitor/unhealthymonitor', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL),
(4, 0, '寿命预警', '/lifemonitor/lifemonitor', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL);

INSERT INTO phm_bd_ground.t_ground_role_menu (GROUND_ROLE_ID, MENU_ID)
VALUES
(3, 1),
(3, 2),
(3, 4),
(3, 3);
-- 密码：admin
INSERT INTO phm_bd_ground.t_user (USER_ID, USER_NAME, PASSWORD, NAME,CREATE_TIME)
VALUES(1, 'admin', 'YWRtaW4=', 'admin','2019-09-20 11:00:00');

INSERT INTO phm_bd_ground.t_ground_user_role (USER_ID, GROUND_ROLE_ID)
VALUES(1, 3);

INSERT INTO t_fault
(id, fault_name, fault_code, fault_num, `level`)
values
(1, '门电机开路故障', 1, 21, 3),
(2, '门电机过流故障', 2, 29, 3),
(3, '门门板开关故障', 3, 26, 3),
(4, '门绿色环线故障', 4, 40, 3),
(5, '门锁闭开关故障', 5, 25, 3),
(6, '门编码器故障', 6, 23, 3),
(7, '门开关门超时故障', 7, 24, 3),
(8, '门输出短路故障', 8, 31, 3),
(9, '外网通讯故障', 9, 50, 2),
(10, '内网通讯故障', 10, 30, 3),
(11, '隔离信号', 11, 11, 2),
(12, '防挤压信号', 12, 15, 3),
(13, '紧急解锁信号', 13, 12, 2);

insert into t_subhealth_type (subhealth_name,subhealth_code)
values
('电机工况异常',1),
('闭锁组件异常',2),
('行程开关异常',3),
('车门V型',4),
('开关门阻力过大',5),
('尺带张紧力过紧',6),
('尺带张紧力过松',7);

INSERT INTO t_push
(type, code, num, level, treatment_id, repair_id, description)
VALUES
(0, 1, 21, 3, 3, 155, '电机回路断开、开关门无动作、电机电流为0'),
(0, 2, 29, 3, 3, 156, '开门过程中，电机电流持续超过设定的电流值1秒钟以上'),
(0, 3, 26, 3, 3, 157, '车门打开时，门板开关信号仍为触发状态'),
(0, 4, 40, 3, 3, 158, '车门关闭后，本门绿色环线两路信号不一致'),
(0, 5, 25, 3, 3, 159, '车门打开时，锁闭开关信号仍为触发状态'),
(0, 6, 23, 3, 3, 160, '编码器信号为非法状态且持续时间超过1秒钟'),
(0, 7, 24, 3, 3, 161, '开关门时间超过8秒钟'),
(0, 8, 31, 3, 3, 162, '输出回路的电流大于设定值，触发硬件保护信号'),
(0, 9, 50, 2, 3, 163, '由网络判断'),
(0, 10, 30, 3, 3, 164, '由网络判断'),
(0, 11, 11, 2, 3, NULL, '隔离信号有效'),
(0, 12, 15, 3, 3, NULL, '关门过程中遇到障碍物'),
(0, 13, 12, 2, 3, NULL, '紧急解锁信号有效');

INSERT INTO t_treatment_suggestion
(id, outline, suggestion)
VALUES
(1, '清客处理', '清客处理'),
(2, '下线处理', '下线处理'),
(3, '回库处理', '对正线载客运营无影响，建议完成当日运营后回库检查');


INSERT INTO t_repair_suggestion
(id, suggestion)
VALUES
(155, '1.检查有无卡滞；
2.查电源电压是否有浪涌或脉冲；
3.是否有区域干扰；
4.检查电机有无损坏。'),
(156, '1.检查有无卡滞；
2.查电源电压是否有浪涌或脉冲；
3.电流传感器是否异常；
4.检查电机有无损坏。'),
(157, '1.是否干扰或者误按；
2.检查线路和连接件。'),
(158, '1.检查锁闭开关是否能正常触发；
2.检查门到位开关是否能正常触发；
3.检查紧急解锁开关是否误触发；
4.检查以上3个开关线路和连接件。'),
(159, '1.是否干扰或者误按；
2.检查线路和连接件。'),
(160, '1.检查是否有异物；
2.检查编码器是否异常；
3.检查驱动机构是否磨损。'),
(161, '1.开关门过程中是否有外力阻挡或机械卡滞；
2.检查线路和连接件。'),
(162, '1.开关门指示灯和其接线是否有故障；
2.车侧灯和其接线是否有故障；
3.蜂鸣器和其接线是否有故障。'),
(163, '1.通信的电气连线中断，或者连接器虚接，接触不良；
2.通信版出现故障不能正确识别外网通信数据。'),
(164, '1.内网通信的电气线路中断，或者连接器虚接，接触不良；
2.内网通信版不能识别网络通信数据；
3.软件逻辑出错，导致通信异常。');