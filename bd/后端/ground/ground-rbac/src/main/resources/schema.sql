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
