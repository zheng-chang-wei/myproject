package com.hirain.ptu.service;

import com.hirain.ptu.model.Condition;

import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/3/30 14:18
 * @describe
 */
public interface ConditionService extends IService<Condition> {

  boolean isRepeat(Condition condition);

  List<Condition> listCondition(String type);
}
