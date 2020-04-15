package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.common.model.CommonParms;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.DataOverview;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ComIdDataMapper extends CommonMapper<ComIdData> {

  @Update("ALERT TABLE t_comid_data DROP PARTITION #{partitionName}")
  void deletePartitionByName(String partitionName);

  @Select("show tables like #{tableName}")
  List<String> getTableByTableName(String tableName);

  @Cacheable(value = "comIdDataService", key = "'getTableData' +#p0")
  @SelectProvider(type = ComIdObjMapperProvider.class, method = "getTableData")
  List<ComIdData> getTableData(CommonParms commonParms);

  @Delete("delete from t_comid_data where date < #{deadLineTime}")
  void deleteByTime(String deadLineTime);

  @Select("SELECT MIN(date) start_time,MAX(date) end_time from t_comid_data")
  DataOverview selectTimeRange();

  class ComIdObjMapperProvider {

    public String getTableData(CommonParms parm) {
      StringBuilder sql = new StringBuilder("select * from t_comid_data");
      sql.append(
              " where ip=#{ip} and com_id=#{comId} and date_format(date,'%Y-%m-%d') = #{time} and ")
          .append(parm.getLogicalCondition());
      return sql.toString();
    }
  }
}
