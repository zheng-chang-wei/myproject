-- 预插入数据
-- 用户权限相关数据

-- 角色数据
INSERT INTO t_ground_role (GROUND_ROLE_ID, ROLE_NAME, REMARK, CREATE_TIME, MODIFY_TIME)
VALUES
(1, '管理员', '管理员', '2017-12-27 16:23:11.000', NULL),
(2, '普通用户', '普通用户', '2017-12-27 16:25:09.000', NULL),
(3, '业主', '业主', '2019-09-19 19:00:00.000', NULL);

-- 菜单数据
INSERT INTO `t_menu` VALUES ('1', '0', '实时监控', '/realtimemonitor', null, null, '0', null, '2019-09-19 19:00:00', null);
INSERT INTO `t_menu` VALUES ('2', '0', '故障信息', '/faultinformation', null, null, '0', null, '2019-09-19 19:00:00', null);
INSERT INTO `t_menu` VALUES ('3', '0', '亚健康预警', '/unhealthymonitor', null, null, '0', null, '2019-09-19 19:00:00', null);
INSERT INTO `t_menu` VALUES ('4', '0', '寿命预警', '/lifemonitor', null, null, '0', null, '2019-09-19 19:00:00', null);
INSERT INTO `t_menu` VALUES ('5', '0', '维修履历', '/repairhistory', null, null, '0', null, '2019-09-19 19:00:00', null);
INSERT INTO `t_menu` VALUES ('6', '0', '系统设置', '/system', null, null, '0', null, '2019-09-19 19:00:00', null);
INSERT INTO `t_menu` VALUES ('7', '0', '权限管理', '/authorityManagement', null, null, '0', null, '2019-09-19 19:00:00', null);
INSERT INTO `t_menu` VALUES ('8', '0', '统计', '/statistics', null, null, '0', null, '2020-02-11 16:07:21', null);

-- 角色-菜单关系
INSERT INTO t_ground_role_menu (GROUND_ROLE_ID, MENU_ID)
VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(3, 1),
(3, 2),
(3, 4),
(3, 3);

-- 管理员用户
INSERT INTO t_user (USER_ID, USER_NAME, PASSWORD, NAME,CREATE_TIME)
VALUES(1, 'admin', 'MQ==', 'admin','2019-09-20 11:00:00');

-- 用户-角色关系
INSERT INTO t_ground_user_role (USER_ID, GROUND_ROLE_ID)
VALUES(1, 1);

-- 维修角色
INSERT INTO t_repair_role (repair_role_id, role_name, remark)
VALUES
(1, '售后工程师', '创建工单'),
(2, '售后审核员', '售后审核'),
(3, '质量调查员', '质量调查'),
(4, '质量审核员', '质量审核'),
(5, '工程师', '问题解决'),
(6, '问题跟踪', '跟踪');

-- 维修过程信息
INSERT INTO t_step_type (type, role_id)
VALUES
('关闭', 1),
('创建工单', 1),
('售后审核', 2),
('质量审核', 4),
('质量调查', 3),
('跟踪', 6),
('问题解决', 5);

insert into t_subhealth_type (subhealth_name,subhealth_code)
values
('行程开关异常',1),
('电机工况异常',2),
('闭锁组件异常',3),
('车门V型',4),
('开关门阻力过大',5),
('尺带张紧力过松',6),
('网络干扰异常',7);

INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 1, 21, 3, 3, 1, '电机回路断开、开关门无动作、电机电流为0');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 2, 29, 3, 3, 2, '开门过程中，电机电流持续超过设定的电流值1秒钟以上');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 3, 26, 3, 3, 3, '车门打开时，门板开关信号仍为触发状态');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 4, 40, 3, 3, 4, '车门关闭后，本门绿色环线两路信号不一致');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 5, 25, 3, 3, 5, '车门打开时，锁闭开关信号仍为触发状态');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 6, 23, 3, 3, 6, '编码器信号为非法状态且持续时间超过1秒钟');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 7, 24, 3, 3, 7, '开关门时间超过8秒钟');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 8, 31, 3, 3, 8, '输出回路的电流大于设定值，触发硬件保护信号');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 9, 50, 2, 3, 9, 'MVB授时在3秒钟内无变化');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 10, 30, 3, 3, 10, '车门数据ID跳变，不连续');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 11, 11, 2, 3, NULL, '隔离信号有效');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 12, 15, 3, 3, NULL, '关门过程中遇到障碍物');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(0, 13, 12, 2, 3, NULL, '紧急解锁信号有效');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(1, 1, 1, 3, 3, 16, '1.关门过程中关门到位开关没触发而锁闭开关触发;2.关门过程中关门到位开关触发而锁闭开关没触发且锁闭组件无异常 或 1. 门完全打开后而关门到位开关不释放;2.根据编码器值和关门时间判断，门关到位置后，关门到位开关不触发');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(1, 2, 2, 3, 3, 15, '1.在正常开关门过程且无防挤压无故障;2.电流曲线与正常车门电流曲线趋势不同且抖动剧烈');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(1, 3, 3, 3, 3, 14, '逻辑与算法驱动');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(1, 4, 4, 3, 3, 13, '数据算法驱动');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(1, 5, 5, 3, 3, 12, '数据算法驱动');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(1, 6, 6, 3, 3, 17, '数据算法驱动');
INSERT INTO t_push
(`type`, code, num, `level`, treatment_id, repair_id, description)
VALUES(1, 7, 7, 3, 3, 11, '短时间内（1秒）多个（5个以上）车门数据ID不连续但没有下线');


INSERT INTO t_treatment_suggestion
(id, outline, suggestion)
VALUES
(1, '清客处理', '清客处理'),
(2, '下线处理', '下线处理'),
(3, '回库处理', '对正线载客运营无影响，建议完成当日运营后回库检查');

INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(1, '1.电机连接线是否虚接；
2.检查电机有无损坏；
3.检查门控器是否损坏', '1.紧固电机连接线端子或者更换电机连接线；
2.更换电机；
3.更换门控器');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(2, '1.检查锁闭机构有无卡滞；
2.检查是否存在异物；
3.检查门控器是否异常；
4.检查电机是否有卡滞', '1.调整锁闭机构；
2.清除机构中的异物；
3.更换门控器；
4.更换电机');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(3, '1.检查开关间隙是否符合标准；
2.检查开关连接线是否异常；
3.检查开关触点是否有粘联；
4.检查开关安装扭矩是否符合标准', '1.调节开关间隙；
2.紧固或更换开关连接线；
3.更换开关；
4.调整开关扭矩');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(4, '1.检查锁闭开关是否能正常触发；
2.检查门到位开关是否能正常触发；
3.检查紧急解锁开关是否误触发；
4.检查隔离开关是否误触发；
5.检查以上4个开关下述内容（1）检查开关间隙是否符合标准；
（2）检查开关连接线是否异常；
（3）检查开关触点是否有粘联；
（4） 检查开关安装扭矩是否符合标准', '1.调节开关间隙；
2.紧固或更换开；
3.关连接线；
4.更换开关；
5.调整开关扭矩');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(5, '1.检查开关间隙是否符合标准；
2.检查开关连接线是否异常；
3.检查开关触点是否有粘联；
4.检查开关安装扭矩是否符合标准', '1.调节开关间隙；
2.紧固或更换开；
3.关连接线；
4.更换开关；
5.调整开关扭矩');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(6, '1.检查编码器线是否虚接；
2.检查编码器是否异常；
3.检查门控器是否异常', '1.紧固或更换编码器线缆；
2.更换电机编码器；
3.更换门控器');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(7, '1.检查是否有外力阻挡或机械卡滞；
2.检查门控器线路和连接件', '1.清除异物；
2.调整机械结构；
3.更换门控器');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(8, '检查输出电路的接线是否有虚接或短路', '1.调整异常接线部位；
2.排除短路故障部件');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(9, '1.通信的电气连线中断，或者连接器虚接，接触不良；
2.检查门控器是否异常', '1.紧固或更换电气连接线；
2.更换门控器');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(10, '1.内网通信的电气线路中断，或者连接器虚接，接触不良；
2.检查门控器是否异常', '1.紧固或更换电气连接线；
2.更换门控器');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(11, '1.检查通讯线缆是否虚接；
2.检查门控器是否在线异常', '1.紧固或更换通讯线缆；
2.更换门控器');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(12, '旋转立柱下摆臂的上下高度', '调整旋转立柱下摆臂的上下高度');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(13, '门板上的偏心销', '调节门板上的偏心销至车门V型正常');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(14, '1.检查锁闭大拉杆是否过紧；
2.检查锁闭件铜垫片是否磨损；
3.检查电机是否松动或动作异常', '1.调整锁闭大拉杆；
2.更换锁闭磨损件；
3.紧固或更换电机');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(15, '1.检查电机支架是否紧固；
2.检查电机固定螺栓是否松动', '1.紧固或更换电机支架；
2.紧固或更换电机固定螺栓；
3.更换电机');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(16, '1检查开关间隙是否符合标准；
2.检查开关连接线是否异常；
3.检查开关触点是否有粘联；
4. 检查开关安装扭矩是否符合标准', '1.调节开关间隙；
2.紧固或更换开；
3.关连接线；
4.更换开关；
5.调整开关扭矩');
INSERT INTO phm_bd_ground.t_repair_suggestion
(id, suggestion, solution)
VALUES(17, '1.检查齿带是否有开裂；
2.检查齿带是否张紧力变松；
3.检查齿带压板是否松动', '1.调节齿带安装座的紧锢螺栓；
2.齿带松紧调节螺栓');
