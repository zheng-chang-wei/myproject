insert into t_project(id,name,city,line,remarks)
values(1,'深圳地铁7号线','深圳','7','');

insert into t_train(id,project_id,train_no,train_status)
values(1,1,'717',1);

insert into t_project_user values
(1,1);

-- 角色数据
INSERT INTO t_ground_role (GROUND_ROLE_ID, ROLE_NAME, REMARK, CREATE_TIME, MODIFY_TIME)
VALUES
(1, '管理员', '管理员', '2017-12-27 16:23:11.000', NULL),
(2, '普通用户', '普通用户', '2017-12-27 16:25:09.000', NULL),
(3, '业主', '业主', '2019-09-19 19:00:00.000', NULL);

-- 菜单数据
INSERT INTO t_menu (MENU_ID, PARENT_ID, MENU_NAME, URL, PERMS, ICON, `TYPE`, ORDER_NUM, CREATE_TIME, MODIFY_TIME)
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

insert into t_repair_user_role values
(1,1);
