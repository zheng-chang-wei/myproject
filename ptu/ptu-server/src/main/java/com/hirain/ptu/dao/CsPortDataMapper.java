package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.common.model.CommonParms;
import com.hirain.ptu.common.model.CsPortDataOverview;
import com.hirain.ptu.common.model.DataOverview;
import com.hirain.ptu.model.CsPortData;
import org.apache.commons.lang3.StringUtils;
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

  @Select(
      "SELECT SUM(link_p_h_m) link_p_h_m, SUM(link_flash_p_h_m) link_flash_p_h_m, SUM(rx_traffic_p_h_m) rx_traffic_p_h_m, SUM(rx_err_rate_p_h_m) rx_err_rate_p_h_m, SUM(rx_err_predict_p_h_m) rx_err_predict_p_h_m, SUM(tx_traffic_p_h_m) tx_traffic_p_h_m, SUM(tx_err_rate_p_h_m) tx_err_rate_p_h_m, SUM(tx_err_predict_p_h_m) tx_err_predict_p_h_m, SUM(ENABLE) ENABLE, ip, com_id, PORT FROM t_csport_data where date>=#{startTime} and date<=#{endTime} GROUP BY ip, com_id, PORT")
  List<CsPortDataOverview> getCsPortDataOverview(CommonParms commonParms);

  class CsPortDataMapperProvider {
    public String getTableData(CommonParms parm) {
      StringBuilder sql = new StringBuilder("select * from t_csport_data");
      sql.append(
          " where ip=#{ip} and com_id=#{comId} and port=#{port} and  date >= #{startTime} and date <= #{endTime} ");
      if (StringUtils.isNotEmpty(parm.getLogicalCondition())) {
        sql.append("and " + parm.getLogicalCondition());
      }
      sql.append(" order by date");
      return sql.toString();
    }
  }
}
