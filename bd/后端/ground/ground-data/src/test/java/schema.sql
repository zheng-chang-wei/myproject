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

-- 总容量表
CREATE TABLE t_big (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  used bigint(20) DEFAULT NULL,
  capacity bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 项目数据表
CREATE TABLE t_big_project (
  project varchar(100) NOT NULL,
  used bigint(20) DEFAULT NULL,
  PRIMARY KEY (project)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 车辆数据表
CREATE TABLE t_big_train (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  train_id int(11) DEFAULT NULL,
  start_day date DEFAULT NULL,
  end_day date DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
