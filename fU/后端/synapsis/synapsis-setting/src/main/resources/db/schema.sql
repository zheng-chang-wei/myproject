CREATE TABLE t_setting(
id int not null auto_increment,
name varchar(100) not null,
line varchar(20),
train varchar(20),
last_modify timestamp,
raw_strategy int,
result_strategy int,
raw_space int,
result_space int,
time_on boolean,
selected boolean,
PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_board_setting(
id int not null auto_increment,
setting_id int not null,
enable bool,
ip varchar(20),
slot_id int,
type varchar(50),
filename varchar(255),
PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_algorithm_setting(
id int not null auto_increment,
setting_id int not null,
name varchar(100) not null,
code int,
slot_id int,
type int,
enable bool,
filename varchar(255),
subsystem_id int,
PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_subsystem(
id int not null auto_increment,
name varchar(50) not null,
description varchar(200),
PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_variable_group(
id int not null auto_increment,
type varchar(10),
consumption_id bigint,
multicast_ip varchar(20),
multicast_port int,
PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_time_setting(
setting_id int not null,
group_id int,
PRIMARY KEY(setting_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_storage_variable(
setting_id int not null,
group_id int,
PRIMARY KEY(setting_id,group_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_board_variable(
board_id int not null,
group_id int not null,
PRIMARY KEY(board_id,group_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_algorithm_variable(
algorithm_id int not null,
group_id int not null,
PRIMARY KEY(algorithm_id,group_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_ad_variable(
id bigint not null auto_increment,
group_id int not null,
chn_id int,
sample_rate double,
clock_src double,
frequence_factor double,
signal_name varchar(100),
PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_mvb_variable(
id bigint not null auto_increment,
group_id int,
device int COMMENT '设备地址',
device_name varchar(100),
carriage varchar(100),
system varchar(100),
port int,
fcode int,
data_type int,
byte_offset int,
bit_offset int,
bit_len int,
name varchar(100),
unit varchar(100),
signal_name varchar(200),
PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_ecn_variable(
id bigint not null auto_increment,
group_id int,
source_ip varchar(20),
com_id bigint,
data_type int,
byte_offset int,
bit_offset int,
bit_len int,
name varchar(100),
unit varchar(100),
signal_name varchar(200),
PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


