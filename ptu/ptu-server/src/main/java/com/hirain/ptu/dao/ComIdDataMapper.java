package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.common.model.ComIdDataOverview;
import com.hirain.ptu.common.model.CommonParms;
import com.hirain.ptu.common.model.DataOverview;
import com.hirain.ptu.model.ComIdData;
import org.apache.commons.lang3.StringUtils;
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

  @Select(
      "SELECT SUM(period_stability_p_h_m) period_stability_p_h_m,SUM(lost_rate_p_h_m) lost_rate_p_h_m,SUM(abnomal_lost_p_h_m) abnomal_lost_p_h_m,AVG((frame_cnt/window_time)*1000000) frame_cnt, ip,com_id from t_comid_data where date>=#{startTime} and date<=#{endTime} GROUP BY ip,com_id order by com_id")
  List<ComIdDataOverview> getComIdDataOverview(CommonParms commonParms);

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
          " where ip=#{ip} and com_id=#{comId} and date >= #{startTime} and date <= #{endTime} ");
      if (StringUtils.isNotEmpty(parm.getLogicalCondition())) {
        sql.append("and " + parm.getLogicalCondition());
      }
      sql.append(" order by date");
      return sql.toString();
    }
  }
}
