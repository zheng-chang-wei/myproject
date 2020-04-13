package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.model.CsPortObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CsPortObjectMapper extends CommonMapper<CsPortObject> {
  @Select("select * from t_csport_object order by com_id")
  List<CsPortObject> selectAllOrderByComId();

  @Select("select * from t_csport_object order by com_id limit 10")
  List<CsPortObject> select10();
}
