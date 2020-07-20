CREATE TABLE ROOT.T_SETTING (
	ID int not null generated by default as identity,
	NAME VARCHAR(100) NOT NULL,
	LINE VARCHAR(20),
	TRAIN VARCHAR(20),
	LAST_MODIFY TIMESTAMP,
	SELECTED BOOLEAN,
	PRIMARY KEY (ID)
);


CREATE TABLE t_board_setting(
id int not null generated by default as identity,
setting_id int not null,
enable boolean,
slot_id int,
type varchar(50),
content varchar(128),
PRIMARY KEY(id)
);

CREATE TABLE t_algorithm_setting(
id int not null generated by default as identity,
setting_id int not null,
name varchar(100) not null,
code int,
slot_id int,
type int,
enable boolean,
filename varchar(255),
file_original_name varchar(255),
subsystem_id int,
video_ip varchar(20),
video_url varchar(255),
PRIMARY KEY(id)
);

CREATE TABLE t_subsystem(
id int not null generated by default as identity,
name varchar(50) not null,
description varchar(200),
PRIMARY KEY(id)
);

CREATE TABLE t_variable_group(
id int not null generated by default as identity,
type varchar(10),
consumption_id bigint,
multicast_ip varchar(20),
multicast_port int,
slot_id int,
PRIMARY KEY(id)
);

CREATE TABLE t_time_setting(
setting_id int not null,
type varchar(20),
group_id int,
PRIMARY KEY(setting_id)
);

CREATE TABLE t_storage_variable(
setting_id int not null,
group_id int,
PRIMARY KEY(setting_id,group_id)
);

CREATE TABLE t_board_variable(
board_id int not null,
group_id int not null,
PRIMARY KEY(board_id,group_id)
);

CREATE TABLE t_algorithm_variable(
algorithm_id int not null,
group_id int not null,
PRIMARY KEY(algorithm_id,group_id)
);

CREATE TABLE t_ad_variable(
id bigint not null generated by default as identity,
group_id int not null,
chn_id int,
sample_rate double,
clock_src double,
frequence_factor double,
signal_name varchar(100),
PRIMARY KEY(id)
);

CREATE TABLE t_mvb_variable(
id bigint not null generated by default as identity,
group_id int,
device int,
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
);

CREATE TABLE t_ecn_variable(
id bigint not null generated by default as identity,
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
);

create table t_store_setting (
	setting_id integer not null,
	raw_strategy integer,
	result_strategy integer,
	raw_space integer,
	result_space integer,
	PRIMARY KEY(setting_id)
);

