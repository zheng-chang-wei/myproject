package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.common.model.CommonParms;
import com.hirain.ptu.common.model.DataOverview;
import com.hirain.ptu.model.CsPortData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CsPortDataMapper extends CommonMapper<CsPortData> {

  @Cacheable(value = "csPortDataService", key = "'getTableData' + #p0")
  @SelectProvider(type = CsPortDataMapperProvider.class, method = "getTableData")
  List<CsPortData> getTableData(CommonParms commonParms);

  @Delete("delete from t_csport_data where date < #{deadLineTime}")
  void deleteByTime(String deadLineTime);

  @Select("SELECT MIN(date) start_time,MAX(date) end_time from t_csport_data")
  DataOverview selectTimeRange();

  class CsPortDataMapperProvider {
    public String getTableData(CommonParms parm) {
      StringBuilder sql = new StringBuilder("select * from t_csport_data");
      sql.append(
              " where ip=#{ip} and com_id=#{comId} and port=#{port} and date_format(date,'%Y-%m-%d') = #{time} and ")
          .append(parm.getLogicalCondition());
      return sql.toString();
    }
  }
}
