insert into t_project(id,name,city,line,remarks)
values(1,'深圳地铁7号线','深圳','7','');

insert into t_train(id,project_id,train_no,train_status)
values(1,1,'717',1);

INSERT INTO t_digital_param
(id, param_key, name, unit, param_type)
VALUES(1, 'Rs', '电机电阻', 'Ω', '电机参数');
INSERT INTO t_digital_param
(id, param_key, name, unit, param_type)
VALUES(2, 'Ls', '电机电感', 'H', '电机参数');
INSERT INTO t_digital_param
(id, param_key, name, unit, param_type)
VALUES(3, 'Flux', '电机磁链', 'V.s', '电机参数');
INSERT INTO t_digital_param
(id, param_key, name, unit, param_type)
VALUES(4, 'Kt', '电机力矩常数', 'Nm/A', '电机参数');
INSERT INTO t_digital_param
(id, param_key, name, unit, param_type)
VALUES(5, 'J', '负载等效转动惯量', 'kg.m²', '机械参数');
INSERT INTO t_digital_param
(id, param_key, name, unit, param_type)
VALUES(6, 'Damping', '负载阻尼系数', 'Nm.s', '机械参数');
INSERT INTO t_digital_param
(id, param_key, name, unit, param_type)
VALUES(7, 'Tf', '静态摩擦转矩', 'Nm', '机械参数');
