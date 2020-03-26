insert into t_setting
values
(1,'配置1','北京1号线','1','2019-12-03 18:30:00',1,20,1,80,true,true);



insert into t_algorithm_setting
values(1,1,'算法1',1,1,1,true,'xxx.py',1);

insert into t_board_setting
values(1,1,true,'127.0.0.1',1,'CARD_TYPE_MVB','xxx.xls');

insert into t_variable_group
values
(1,'AD',1,'127.0.0.1',23),
(2,'MVB',1,'127.0.0.1',24),
(3,'ECN',1,'127.0.0.1',25);

insert into t_mvb_variable
values
(1,2,1,'TSB_1','车厢1','受电弓',2,4,4,1,1,1,'volume','A','电流'),
(2,2,2,'TSB_2','车厢1','受电弓',6,4,4,2,0,1,'press','V','电压');

insert into t_ecn_variable
values
(1,3,'127.0.0.1',1,4,1,1,1,'volumn','A','电流'),
(2,3,'127.0.0.1',1,4,1,1,1,'press','V','电压');

insert into t_ad_variable
values
(1,1,1,1.0,1.0,1.0,'电压');

insert into t_algorithm_variable values(1,3);

insert into t_board_variable values(1,2);

insert into t_storage_variable values
(1,1),
(1,2),
(1,3);

insert into t_time_setting values
(1,2);

insert into t_subsystem
values(1,'受电弓','');