package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.model.DataOverview;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface DataOverviewMapper extends CommonMapper<DataOverview> {

  @Select(
          "SELECT * FROM t_data_overview WHERE `type`=#{type}")
    DataOverview selectByType(String type);

  @Delete("delete from t_data_overview WHERE `type`=#{type}")
  void deleteByType(String type);
}
