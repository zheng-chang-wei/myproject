package com.hirain.ptu.service.impl;

import com.hirain.ptu.dao.ConditionMapper;
import com.hirain.ptu.model.Condition;
import com.hirain.ptu.service.ConditionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/3/30 14:18
 * @describe
 */
@Service
public class ConditionServiceImpl extends BaseService<Condition> implements ConditionService {
  @Autowired ConditionMapper conditionMapper;

  @Override
  public List<Condition> listCondition(String type) {
    Example example = new Example(Condition.class);
    Example.Criteria criteria = example.createCriteria();
    if (StringUtils.isNotEmpty(type)) {
      criteria.andCondition("type =", type);
    }
    return conditionMapper.selectByExample(example);
  }

  /**
   * 条件名称是否重复
   *
   * @param condition
   * @return
   */
  @Override
  public boolean isRepeat(Condition condition) {
    Condition selectCondition = selectByName(condition.getConditionName());
    if (condition.getId() == null) {
      if (selectCondition == null) {
        return false;
      } else {
        return true;
      }
    } else {
      if (selectCondition == null) {
        return false;
      } else if (selectCondition.getId().equals(condition.getId())) {
        return false;
      } else {
        return true;
      }
    }
  }

  private Condition selectByName(String name) {
    Example example = new Example(Condition.class);
    Example.Criteria criteria = example.createCriteria();
    if (StringUtil.isNotEmpty(name)) {
      criteria.andEqualTo("conditionName", name);
    }
    List<Condition> list = conditionMapper.selectByExample(example);
    if (list != null && list.size() != 0) {
      return list.get(0);
    }
    return null;
  }
}
