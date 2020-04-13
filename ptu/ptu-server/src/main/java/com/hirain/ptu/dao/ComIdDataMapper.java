package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.common.model.CommonParms;
import com.hirain.ptu.common.model.CommonParms2;
import com.hirain.ptu.model.ComIdData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;

import java.text.MessageFormat;
import java.util.List;

public interface ComIdDataMapper extends CommonMapper<ComIdData> {

  @Select("show tables like #{tableName}")
  List<String> getTableByTableName(String tableName);

  @Cacheable(value = "comIdObjService", key = "'getTableData' +#p0")
  @SelectProvider(type = ComIdObjMapperProvider.class, method = "getTableData2")
  List<ComIdData> getTableData(CommonParms2 commonParms);

  //  @SelectProvider(type = ComIdObjMapperProvider.class, method = "getTableData")
  //  List<ComIdData> getTableData(CommonParms commonParms);

  @SelectProvider(type = ComIdObjMapperProvider.class, method = "getChartData")
  List<ComIdData> getChartData(CommonParms2 commonParms);

  @Delete("delete from t_comid_data where date < #{deadLineTime}")
  void deleteByTime(String deadLineTime);

  class ComIdObjMapperProvider {

    public String getTableData2(CommonParms2 parm) {
      StringBuilder sql = new StringBuilder("select * from t_comid_data");
      sql.append(
              " where ip=#{ip} and com_id=#{comId} and date_format(date,'%Y-%m-%d') = #{time} and ")
          .append(parm.getLogicalCondition());
      return sql.toString();
    }

    public String getTableData(CommonParms parm) {
      StringBuilder sql = new StringBuilder("select * ");
      sql.append(" from ")
          .append(parm.getTableName())
          .append(" where date_format(date,'%Y-%m-%d') = #{time} and ")
          .append(parm.getLogicalCondition());
      return sql.toString();
    }

    public String getChartData(CommonParms2 parm) {
      StringBuilder sql = new StringBuilder("select ");
      List<String> features = parm.getFeatures();
      for (String feature : features) {
        sql.append(feature + ",");
      }
      sql.append("date");
      sql.append(" from t_comid_data");
      sql.append(
              " where ip=#{ip} and com_id=#{comId} and date_format(date,'%Y-%m-%d') = #{time} and ")
              .append(parm.getLogicalCondition());
      return sql.toString();
    }

    public String batDataAdd(String tableName, List<ComIdData> list) {
      StringBuilder sb = new StringBuilder();
      sb.append("insert into ")
          .append(tableName)
          .append(
              "(date, period_stability_p_h_m,lost_rate_p_h_m,abnomal_lost_p_h_m,window_time,period_mean,period_std,lost_rate,lost_max_rate,life_signal_stop_rate,life_signal_stop_max_rate) values ");
      MessageFormat mf =
          new MessageFormat(
              "(#'{'list[{0}].date},#'{'list[{0}].periodStabilityPHM},#'{'list[{0}].lostRatePHM},#'{'list[{0}].abnomalLostPHM},#'{'list[{0}].windowTime},#'{'list[{0}].periodMean},#'{'list[{0}].periodStd},#'{'list[{0}].lostRate},#'{'list[{0}].lostMaxRate},#'{'list[{0}].lifeSignalStopRate},#'{'list[{0}].lifeSignalStopMaxRate})");
      for (int i = 0; i < list.size(); i++) {
        sb.append(mf.format(new Object[] {i}));
        if (i < list.size() - 1) {
          sb.append(",");
        }
      }
      return sb.toString();
    }
  }
}
