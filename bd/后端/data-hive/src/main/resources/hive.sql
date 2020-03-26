create table if not exists [table_name]
(
  project STRING,
  train STRING,
  carriageId INT,
  doorId INT,
  timestamp DATE,
  data STRING
)
partitioned by (year STRING, month STRING, date STRING)
row format delimited fields terminated by ',' lines terminated by '\n'


insert into table [table_name] partition(year,month,date)
select #{project},#{train},#{carriageId},#{doorId},#{timestamp},#{data},#{year},#{month},#{date}

