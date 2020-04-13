package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.common.model.CommonParms;
import com.hirain.ptu.common.model.CommonParms2;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.CsPortData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;

import java.text.MessageFormat;
import java.util.List;

public interface CsPortDataMapper extends CommonMapper<CsPortData> {

  @Cacheable(value = "comIdObjService", key = "'getTableData' + #p0")
  @SelectProvider(type = CsPortDataMapperProvider.class, method = "getTableData2")
  List<CsPortData> getTableData(CommonParms2 commonParms);

  //  @SelectProvider(type = CsPortDataMapperProvider.class, method = "getTableData")
  //  List<CsPortData> getTableData(CommonParms commonParms);

  @SelectProvider(type = CsPortDataMapperProvider.class, method = "getChartData")
  List<CsPortData> getChartData(CommonParms2 commonParms);

  @Delete("delete from t_csport_data where date < #{deadLineTime}")
  void deleteByTime(String deadLineTime);

  class CsPortDataMapperProvider {
    public String getTableData2(CommonParms2 parm) {
      StringBuilder sql = new StringBuilder("select * from t_csport_data");
      sql.append(
              " where ip=#{ip} and com_id=#{comId} and port=#{port} and date_format(date,'%Y-%m-%d') = #{time} and ")
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
      sql.append(" from t_csport_data");
      sql.append(
              " where ip=#{ip} and com_id=#{comId} and port=#{port} and date_format(date,'%Y-%m-%d') = #{time} and ")
              .append(parm.getLogicalCondition());
      return sql.toString();
    }
    public String batDataAdd(String tableName, List<CsPortData> list) {
      StringBuilder sb = new StringBuilder();
      sb.append("insert into ")
          .append(tableName)
          .append(
              "(date, link_p_h_m,link_flash_p_h_m,rx_traffic_p_h_m,rx_err_rate_p_h_m,rx_err_predict_p_h_m,"
                  + "tx_traffic_p_h_m,tx_err_rate_p_h_m,tx_err_predict_p_h_m,enable,link_mean,"
                  + "rx_mcast,rx_traffic_mean,rx_traffic_std,rx_err_rate_mean,rx_err_rate_std,"
                  + "tx_mcast,tx_traffic_mean,tx_traffic_std,tx_err_rate_mean,tx_err_rate_std) values ");
      MessageFormat mf =
          new MessageFormat(
              "(#'{'list[{0}].date},#'{'list[{0}].linkPHM},#'{'list[{0}].linkFlashPHM},#'{'list[{0}].rxTrafficPHM},#'{'list[{0}].rxErrRatePHM},#'{'list[{0}].rxErrPredictPHM},"
                  + "#'{'list[{0}].txTrafficPHM},#'{'list[{0}].txErrRatePHM},#'{'list[{0}].txErrPredictPHM},#'{'list[{0}].enable},#'{'list[{0}].linkMean},"
                  + "#'{'list[{0}].rxMcast},#'{'list[{0}].rxTrafficMean},#'{'list[{0}].rxTrafficStd},#'{'list[{0}].rxErrRateMean},#'{'list[{0}].rxErrRateStd}"
                  + ",#'{'list[{0}].txMcast},#'{'list[{0}].txTrafficMean},#'{'list[{0}].txTrafficStd},#'{'list[{0}].txErrRateMean},#'{'list[{0}].txErrRateStd})");
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
