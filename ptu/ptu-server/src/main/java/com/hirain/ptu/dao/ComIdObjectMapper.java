package com.hirain.ptu.dao;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.model.ComIdObject;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ComIdObjectMapper extends CommonMapper<ComIdObject> {
  @Select("select * from t_comid_object order by com_id")
  List<ComIdObject> selectAllOrderByComId();

  @Select("SELECT * from t_comid_object ORDER BY com_id LIMIT 10")
  List<ComIdObject> select10();
}
