create table t_user(
id int not null generated by default as identity,
username varchar(20) not null,
password varchar(20) not null,
primary key(id)
);

create table t_role(
id int not null generated by default as identity,
rolename varchar(20) not null,
cname varchar(20) not null,
primary key(id)
);

create table t_permission(
id int not null generated by default as identity,
permission varchar(20) not null,
cname varchar(20) not null,
primary key(id)
);

create table t_user_role(
user_id int not null,
role_id int not null,
primary key(user_id,role_id)
);

create table t_role_permission(
role_id int not null,
permission_id int not null,
primary key(role_id,permission_id)
);