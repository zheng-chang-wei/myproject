CREATE TABLE `t_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `city` varchar(20) DEFAULT NULL,
  `line` varchar(20) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_train (
id int(11) NOT NULL AUTO_INCREMENT,
mac1 varchar(20) DEFAULT NULL,
mac2 varchar(20) DEFAULT NULL,
project_id int(11),
train_no varchar(20) NOT NULL,
config_info text,
protocol_file text,
train_status tinyint(1) NOT NULL,
serverip1 varchar(20) DEFAULT NULL,
serverip2 varchar(20) DEFAULT NULL,
train_online tinyint(1) DEFAULT '0',
offsets int(11) DEFAULT NULL,
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 车载端表
CREATE TABLE t_train_dm (
train_id int(11) NOT NULL,
storage_ratio int(11) NOT NULL,
fault_storage_ratio int(11) NOT NULL,
start_date date DEFAULT NULL,
end_date date DEFAULT NULL,
delete_date date DEFAULT NULL,
PRIMARY KEY (train_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 项目-用户表
CREATE TABLE t_project_user (
  USER_ID bigint(20) NOT NULL,
  project_ID bigint(20) NOT NULL,
  PRIMARY key(USER_ID,project_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户表
CREATE TABLE t_user (
  USER_ID bigint(20) NOT NULL AUTO_INCREMENT,
  USER_NAME varchar(50) NOT NULL,
  PASSWORD varchar(128) NOT NULL,
  NAME varchar(20) NOT NULL,
  DEPT_ID bigint(20) DEFAULT NULL,
  EMAIL varchar(128) DEFAULT NULL,
  MOBILE varchar(20) DEFAULT NULL,
  WECHAT varchar(30) DEFAULT NULL,
  CREATE_TIME datetime NOT NULL,
  MODIFY_TIME datetime DEFAULT NULL,
  PRIMARY KEY (USER_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 部门表
CREATE TABLE t_dept (
  DEPT_ID bigint(20) NOT NULL AUTO_INCREMENT,
  PARENT_ID bigint(20) NOT NULL,
  DEPT_NAME varchar(100) NOT NULL,
  CREATE_TIME datetime DEFAULT NULL,
  PRIMARY KEY (DEPT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 角色表
CREATE TABLE t_ground_role (
  GROUND_ROLE_ID bigint(20) NOT NULL AUTO_INCREMENT,
  ROLE_NAME varchar(100) NOT NULL,
  REMARK varchar(100) DEFAULT NULL,
  CREATE_TIME datetime DEFAULT NULL,
  MODIFY_TIME datetime DEFAULT NULL,
  PRIMARY KEY (GROUND_ROLE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 菜单表
CREATE TABLE t_menu (
  MENU_ID bigint(20) NOT NULL AUTO_INCREMENT,
  PARENT_ID bigint(20) NOT NULL ,
  MENU_NAME varchar(50) NOT NULL ,
  URL varchar(100) DEFAULT NULL ,
  PERMS text,
  ICON varchar(50) DEFAULT NULL ,
  TYPE char(2) NOT NULL ,
  ORDER_NUM bigint(20) DEFAULT NULL,
  CREATE_TIME datetime NOT NULL,
  MODIFY_TIME datetime DEFAULT NULL,
  PRIMARY KEY (MENU_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 维修角色表
CREATE TABLE t_repair_role (
  repair_role_id int(11) NOT NULL,
  role_name varchar(20) DEFAULT NULL,
  remark varchar(100) DEFAULT NULL,
  PRIMARY KEY (repair_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户-角色表
CREATE TABLE t_ground_user_role (
  USER_ID bigint(20) NOT NULL,
  GROUND_ROLE_ID bigint(20) NOT NULL,
  PRIMARY key(USER_ID,GROUND_ROLE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 角色-菜单表
CREATE TABLE t_ground_role_menu (
  GROUND_ROLE_ID bigint(20) NOT NULL,
  MENU_ID bigint(20) NOT NULL,
  PRIMARY KEY(GROUND_ROLE_ID,MENU_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户-维修角色表
CREATE TABLE t_repair_user_role (
  user_id bigint(20) NOT NULL,
  repair_role_id int(11) NOT NULL,
  PRIMARY KEY (user_id,repair_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 维修履历相关数据表
-- 外部故障表
CREATE TABLE t_external_fault (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  train_id varchar(50) DEFAULT NULL,
  door_id varchar(50) DEFAULT NULL,
  fault_mode varchar(50) DEFAULT NULL,
  time date DEFAULT NULL,
  statistics tinyint(1) DEFAULT '0',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 工单表
CREATE TABLE t_worksheet (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id bigint(20) DEFAULT NULL,
  state varchar(20) DEFAULT NULL,
  process_instance_id varchar(100) DEFAULT NULL,
  create_time date DEFAULT NULL,
  step_id bigint(20) DEFAULT NULL,
  fault_type int(11) DEFAULT NULL,
  fault_name varchar(50) DEFAULT NULL,
  fault_time datetime DEFAULT NULL,
  fault_mode varchar(100) DEFAULT NULL,
  train_id varchar(20) DEFAULT NULL,
  door_id varchar(20) DEFAULT NULL,
  detail_id bigint(20) DEFAULT NULL,
  fault_id bigint(20) DEFAULT NULL,
  fault_code int(11) DEFAULT NULL,
  project_id int(11),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 工单详情表
CREATE TABLE t_workdetail (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  project_id int(11) DEFAULT NULL,
  kilometers int(11) DEFAULT NULL,
  train_id varchar(20) DEFAULT NULL,
  door_id varchar(20) DEFAULT NULL,
  fault_time datetime DEFAULT NULL,
  effect varchar(30) DEFAULT NULL,
  door_type varchar(20) DEFAULT NULL,
  stage varchar(20) DEFAULT NULL,
  level_one varchar(20) DEFAULT NULL,
  level_two varchar(20) DEFAULT NULL,
  mode varchar(20) DEFAULT NULL,
  location varchar(20) DEFAULT NULL,
  component varchar(20) DEFAULT NULL,
  count int(11) DEFAULT NULL,
  need_parts tinyint(1) DEFAULT NULL,
  part_name varchar(20) DEFAULT NULL,
  part_count int(11) DEFAULT NULL,
  need_report tinyint(1) DEFAULT NULL,
  temporary longtext,
  description longtext,
  reason longtext,
  image_path text,
  processor_count int(11) DEFAULT NULL,
  processor varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 维修过程信息表
CREATE TABLE t_step_type (
  type varchar(20) NOT NULL,
  role_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 维修过程表
CREATE TABLE t_step (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  sheet_id bigint(20) NOT NULL,
  seq int(11) NOT NULL DEFAULT '1',
  start_time datetime DEFAULT NULL,
  end_time datetime DEFAULT NULL,
  auditor_id bigint(20) DEFAULT NULL,
  result varchar(10) DEFAULT NULL,
  content longtext,
  type varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_push (
  type int(11) NOT NULL,
  code int(11) NOT NULL,
  num int(11) DEFAULT NULL,
  level int(11) DEFAULT NULL,
  treatment_id int(11) DEFAULT NULL,
  repair_id int(11) DEFAULT NULL,
  description varchar(100) DEFAULT NULL,
  PRIMARY KEY (type,code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_repair_suggestion (
  id int(11) NOT NULL,
  suggestion text,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_treatment_suggestion (
  id int(11) NOT NULL,
  outline varchar(200) DEFAULT NULL,
  suggestion text,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;