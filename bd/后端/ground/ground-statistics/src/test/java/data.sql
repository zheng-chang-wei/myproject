insert into t_project(id,name,city,line,remarks)
values(1,'深圳地铁7号线','深圳','7','');

insert into t_train(id,project_id,train_no,train_status,door_count,train_online)
values(1,1,'717',1,100,1);

insert into t_project_user values
(1,1);


INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(11, 30, 1, '2019-12-09 17:28:45.000', 1, 1, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(12, 30, 1, '2019-12-09 17:30:03.000', 1, 1, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(13, 43, 343, '2019-09-23 11:23:23.000', 1, 1, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(19, 40, 1, '2019-10-31 15:32:01.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(20, 40, 1, '2019-10-31 15:38:07.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(21, 40, 1, '2019-10-31 15:55:30.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(22, 40, 1, '2019-10-31 16:00:22.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(23, 40, 1, '2019-10-31 16:40:27.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(24, 32, 1, '2019-10-31 17:38:48.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(25, 32, 1, '2019-10-31 17:40:16.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(26, 40, 1, '2019-10-31 17:57:38.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(27, 30, 1, '2019-10-31 18:10:39.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(28, 40, 1, '2019-10-31 19:47:36.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(29, 30, 1, '2019-12-09 19:48:21.000', 6, 2, 0, 0);
INSERT INTO t_fault_detail
(id, fault_info_id, train_id, fault_time, car_no, door_addr, debug_mode, statistics)
VALUES(30, 42, 1, '2019-12-09 16:00:22.000', 5, 3, 0, 0);

INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(30, 1, '门电机开路故障', 1);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(31, 1, '门电机过流故障', 2);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(32, 1, '门门板开关故障', 3);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(33, 1, '门绿色环线故障', 4);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(34, 1, '门锁闭开关故障', 5);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(35, 1, '门编码器故障', 6);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(36, 1, '门开关门超时故障', 7);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(37, 1, '门输出短路故障', 8);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(38, 1, '外网通讯故障', 9);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(39, 1, '内网通讯故障', 10);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(40, 1, '隔离信号', 11);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(41, 1, '防挤压信号', 12);
INSERT INTO t_fault_info
(id, project_id, fault_name, fault_code)
VALUES(42, 1, '紧急解锁信号', 13);


INSERT INTO t_subhealth_detail
(id, subhealth_type_id, start_time, end_time, train_id, car_no, door_addr, debug_mode, statistics)
VALUES(192, 5, '2019-12-09 16:24:01.000', '2019-11-04 16:24:05.000', 1, 1, 5, 0, NULL);
INSERT INTO t_subhealth_detail
(id, subhealth_type_id, start_time, end_time, train_id, car_no, door_addr, debug_mode, statistics)
VALUES(190, 5, '2019-12-09 16:23:53.000', '2019-11-04 16:23:57.000', 1, 4, 1, 0, NULL);
INSERT INTO t_subhealth_detail
(id, subhealth_type_id, start_time, end_time, train_id, car_no, door_addr, debug_mode, statistics)
VALUES(191, 5, '2019-12-09 16:23:53.000', '2019-11-04 16:23:57.000', 1, 1, 4, 0, NULL);
INSERT INTO t_subhealth_detail
(id, subhealth_type_id, start_time, end_time, train_id, car_no, door_addr, debug_mode, statistics)
VALUES(187, 6, '2019-11-04 16:17:51.000', '2019-11-04 16:17:54.000', 1, 2, 7, 0, NULL);
INSERT INTO t_subhealth_detail
(id, subhealth_type_id, start_time, end_time, train_id, car_no, door_addr, debug_mode, statistics)
VALUES(188, 6, '2019-11-04 16:17:51.000', '2019-11-04 16:17:54.000', 1, 6, 2, 0, NULL);
INSERT INTO t_subhealth_detail
(id, subhealth_type_id, start_time, end_time, train_id, car_no, door_addr, debug_mode, statistics)
VALUES(185, 6, '2019-11-04 16:17:47.000', '2019-11-04 16:17:51.000', 1, 5, 5, 0, NULL);
INSERT INTO t_subhealth_detail
(id, subhealth_type_id, start_time, end_time, train_id, car_no, door_addr, debug_mode, statistics)
VALUES(189, 6, '2019-11-04 16:17:47.000', '2019-11-04 16:17:51.000', 1, 1, 8, 0, NULL);
INSERT INTO t_subhealth_detail
(id, subhealth_type_id, start_time, end_time, train_id, car_no, door_addr, debug_mode, statistics)
VALUES(184, 5, '2019-11-04 16:17:42.000', '2019-11-04 16:17:46.000', 1, 2, 4, 0, NULL);

-- 预插入数据
-- 用户权限相关数据

-- 角色数据
INSERT INTO t_ground_role (GROUND_ROLE_ID, ROLE_NAME, REMARK, CREATE_TIME, MODIFY_TIME)
VALUES
(1, '管理员', '管理员', '2017-12-27 16:23:11.000', NULL),
(2, '普通用户', '普通用户', '2017-12-27 16:25:09.000', NULL),
(3, '业主', '业主', '2019-09-19 19:00:00.000', NULL);

-- 菜单数据
INSERT INTO t_menu (MENU_ID, PARENT_ID, MENU_NAME, URL, PERMS, ICON, TYPE, ORDER_NUM, CREATE_TIME, MODIFY_TIME)
VALUES
(1, 0, '实时监控', '/realtimemonitor/realtimemonitor', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL),
(2, 0, '故障信息', '/faultinformation/showFaultInformation', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL),
(3, 0, '亚健康预警', '/unhealthymonitor/unhealthymonitor', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL),
(4, 0, '寿命预警', '/lifemonitor/lifemonitor', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL),
(5, 0, '维修履历', '/repairhistory/repairhistory', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL),
(6, 0, '系统设置', '/system/systemsetting', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL),
(7, 0, '权限管理', '/system/authorityManagement', NULL, NULL, '0', NULL, '2019-09-19 19:00:00.000', NULL);

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

insert into t_push
(type,code,num,level,treatment_id,repair_id)
values
(1,5,21,3,207,155),
(1,6,22,2,3,156);

INSERT INTO t_treatment_suggestion
(id, outline, suggestion)
VALUES
(1, '清客处理', '清客处理'),
(2, '下线处理', '下线处理'),
(3, '回库处理', '对正线载客运营无影响，建议完成当日运营后回库检查'),
(101, '清客退出服务', '清客退出服务'),
(102, '清客退出服务', '清客退出服务，备用模式，救援'),
(103, '清客下线', '打备用模式，清客下线'),
(104, '清客退出服务', '1.按压=41-S101【MVB复位】按钮，故障消失继续运营，无效则清客退出服务；
2.如无法动车，清客后启动备用模式；
3.无效则重启列车，否则请求救援。'),
(105, '清客下线', '打备用模式，清客下线'),
(106, '清客下线', '清客下线'),
(107, '清客退出服务', '切除相应转向架B09，清客退出服务'),
(108, '清客退出服务', '1.清客退出服务；
2.无效重启列车'),
(109, '终点站退出服务', '1.一个DC/DC充电机严重故障，运营到终点站退出服务；
2.两个DC/DC充电机严重故障；
（1）维持进站，分合=31-F102【蓄电池充电机】空开，故障消失则终点站退出服务；
（2）无效则清客退出服务。'),
(110, '终点站退出服务', '终点站退出服务，气压急剧下降当前站清客退出服务'),
(111, '终点站退出服务', '风压大于700Kpa终点站退出服务，否则清客退出服务'),
(112, '清客退出服务', '1.按压【再关门】按钮2秒以上；
2.无效则清客后将=81-S110【门关好旁路】开关打至合位退出服务；
3.如无法动车，启动备用模式；
4.无效则重启列车，备用模式动车，否则请求救援。'),
(113, '清客退出服务', '1.运行至前方站内，断合一次=41-F104【列车控制显示器】电源空开，若车辆屏恢复正常，继续运营；
无效则清客退出服务；
2.如无法牵引，启动备用模式，本站退出服务。'),
(201, '终点站退出服务', '终点站退出服务'),
(202, '终点站退出服务', '切除相应转向架B09，终点站退出服务'),
(203, '当前站退出服务', '当前站退出服务'),
(204, '紧急停车', '1.检查操作端紧急停车按钮，若被拍下则恢复并重新升弓；
2.无效则合=31-S103【升弓允许旁路】后再升弓；
3.仍无效则重启列车，重复2）步骤，否则请求救援。'),
(205, '回库处理', '检查客室照明是否正常，若正常则继续运营，待回库后处理，若照明故障则运行至终点站，退出服务'),
(206, '继续运营', '1.单个PECU显红，报告当值行调，必要时由驻站人员协助处理；
2.整节/整列车PECU显红，操作=45-S01【广播复位】旋钮进行广播复位，复位后故障消除则继续运营，否则终点站退出服务。'),
(207, '终点站退出服务', '本站或下一站升降弓，故障存在清客退出服务，故障消失终点站退出服务'),
(208, '终点站退出服务', '重新分合列车控制单元空开，有效则终点站下线'),
(300, '回库处理', '继续运营，当天运营结束后回库处理'),
(301, '回库处理', '驻站人员确认车门状态继续运营，当天运营结束后回库处理'),
(401, '继续运营', '1.跟踪3个站以上；
2.继续运营'),
(402, '继续运营', '驻站人员跟车3个站'),
(403, '继续运营', '1.在车辆屏确认火警，按停蜂鸣器响声；
2.报告当值行调，到达前方站确认列车安全状态，若无异常情况，继续运营，必要时由驻站人员协助处理。'),
(404, '根据HMI信息处理', '查看HMI列车牵引制动级位设置'),
(405, '根据HMI信息处理', '1.查看HMI屏确定故障具体信息；
2.根据故障信息进行相应处理。'),
(406, '继续运营', '1.查看受电弓状态；
2.状态正常则重新降弓后升弓（升降弓需间隔15S）。'),
(407, '继续运营', '1.查看受电弓状态；
2.状态正常则重新升降弓（升降弓需间隔15S）。'),
(408, '继续运营', '1.合警惕旁路；
2.无效启用备用模式。'),
(409, '继续运营', '1.启用备用模式；
2.无效分合空开；
3.则重启列车。'),
(410, '继续运营', '1.启用备用模式；
2.无效则重启列车。'),
(411, '继续运营', '1.手动关闭车门；
2.将该故障车门隔离或切除；
3.拉好黄色警示标识；
4.继续运营。'),
(412, '继续运营', '操作本弓隔离旋钮导致则无需处理'),
(413, '继续运营', '合库用电源盖旁路');


INSERT INTO t_repair_suggestion
(id, suggestion)
VALUES
(101, '1.检测探测控制器电源是否开启.线缆连接是否正确；
2.已开启电源、线缆连接正确则更换探测控制器。'),
(102, '1.检查显示器接线；
2.接线无问题更换显示器。'),
(103, '1.查看HMI屏确定故障车门具体位置；
2.使用PTU、DSS维护软件下载故障信息；
3.根据软件提示信息进行相应处理。'),
(104, '1.查看HMI屏确定故障车门具体位置；
2.使用PTU、DSS维护软件下载故障信息；
3.根据软件提示信息进行相应处理；
4.下载并查看相应时间段视频。'),
(105, '1.查看HMI屏确定故障具体位置，查看被切除B09有无异常；
2.使用PTU下载对应EP2002阀故障信息；
3.根据EP2002阀故障信息进行相应处理
4.重新进行制动自检，确保通过。'),
(106, '1.查看HMI屏确定故障具体位置；
2.复位系统、检测线缆连接是否正确，复位系统后仍然，线缆连接正确则更换探测器。'),
(107, '1.查看HMI屏确定故障具体位置；
2.根据故障信息进行相应处理。'),
(108, '1.查看HMI屏确定故障具体位置；
2.检查接线是否紧固，更换故障驱动电源。'),
(109, '1.查看HMI屏确定故障具体位置；
2.检查烟温主机故障记录、探测器状态参数，开启探测器；
3.更换故障烟温探测器。'),
(110, '1.查看HMI屏确定故障具体位置；
2.使用PTU、DCU软件下载故障信息；
3.根据软件提示信息进行相应处理。'),
(111, '1.查看HMI屏确定故障具体位置；
2.使用PTU、DSS软件下载故障信息；
3.根据软件提示信息进行相应处理。'),
(112, '1.查看HMI屏确定故障具体位置；
2.使用PTU、SIV软件下载故障信息；
3.根据软件提示信息进行相应处理。'),
(113, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应EDRM故障信息；
3.根据EDRM故障信息进行相应处理。'),
(114, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应EP2002阀故障信息；
3.根据EP2002阀故障信息进行相应处理；
4.重新进行制动自检，确保通过。'),
(115, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应EP2002阀故障信息；
3.根据EP2002阀故障信息进行相应处理；
4.测试列车是否漏气，检查管路有无严重漏气，空压机有无正常工作；
5.重新进行制动自检，确保通过。'),
(116, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应EP2002阀故障信息；
3.根据EP2002阀故障信息进行相应处理,
4.进行管路侧漏实验，检查制动压力是否在正常范围；
5.重新进行制动自检，确保通过。'),
(117, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应EP2002阀故障信息；
3.根据EP2002阀故障信息进行相应处理；
4.重新进行制动自检，确保通过。'),
(118, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.安排动火更换冷凝风机。'),
(119, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.安排动火更换通风机。'),
(120, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.安排动火更换温度传感器。'),
(121, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.安排动火更换压缩机。'),
(122, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.安排更换低压压力开关。'),
(123, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.安排更换风阀执行器。'),
(124, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.安排更换高压压力开关。'),
(125, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.安排更换紧急逆变器。'),
(126, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.安排更换温度传感器。'),
(127, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应KPC故障信息；
3.根据故障信息处理故障安排更换故障件。'),
(128, '1.查看HMI屏确定故障具体位置；
2.使用PTU下载对应事件记录故障信息；
3.根据故障信息进行相应处理。'),
(129, '1.查看HMI屏确定故障具体位置；
2.是否电源故障；
3.是否为单机故障。还是所有LCD故障，单机故障，更换LCD；
4.所有故障，更换客室控制主机本地控制单元；更换客室控制主机交换机模块；
5.或LCD逐个上电。排查LCD故障'),
(130, '1.查看HMI屏确定故障具体位置；
2.用ping包软件检查烟温主机是否在线，以太网是否有断点；
3.更换故障烟温主机。'),
(131, '1.查看HMI屏确定故障具体信息；
2.根据故障信息进行相应处理。'),
(132, '1.查看监视电路图；
2.检查司控器状态。'),
(133, '1.查看数据检查方向信号；
2.检查方向监视继电器；
3.检查故障对应车厢牵引逆变器。'),
(134, '1.查看数据检查牵引制动；
2.检查牵引制动监视继电器；
3.检查故障对应车厢牵引逆变器。'),
(135, '1.查看数据检查牵引制动；
2.检查牵引制动监视继电器；
3.检查故障对应车厢牵引逆变器。'),
(136, '1.查看司机室占有电路监视；
2.检查司控器锁芯行程开关。'),
(137, '1.是否电源故障；
2.是否为单机故障。还是所有PECU故障，单机故障更换PECU；
3.所有故障更换客室控制主机本地控制单元，或PECU逐个上电排查PECU故障。'),
(138, '检查ATC信号设备'),
(139, '检查监视电路状态'),
(140, '检查紧急牵引模式电路'),
(141, '1.检查警惕按钮电路状态；
2.检查警惕按钮。'),
(142, '检查三位置隔离开关监视旁路'),
(143, '检查升弓管路状态'),
(144, '检查升降弓监视旁路'),
(145, '回库检查升降弓监视电路'),
(146, '回库检查受电弓状态'),
(147, '回库检查司控器状态'),
(148, '回库检查速度传感器'),
(149, '回库后更换轴承'),
(150, '回库检查，对故障部件进行更换'),
(151, '回库由信号人员处理'),
(152, '架修时更换轴承'),
(153, '联系厂家处理'),
(154, '终点站退出服务'),
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