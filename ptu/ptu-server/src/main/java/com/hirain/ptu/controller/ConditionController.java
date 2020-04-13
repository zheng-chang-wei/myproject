package com.hirain.ptu.controller;

import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.model.Condition;
import com.hirain.ptu.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author changwei.zheng
 * @date 2020/3/30 14:00
 * @describe 逻辑条件配置controller
 */
@RestController
@RequestMapping("/condition")
public class ConditionController {

  @Autowired ConditionService conditionService;

  @GetMapping("/listCondition")
  public ResponseBo listCondition(String type) {
    return ResponseBo.ok(conditionService.listCondition(type));
  }

  @PostMapping("/saveCondition")
  public ResponseBo saveCondition(Condition condition) {
    boolean repeat = conditionService.isRepeat(condition);
    if (repeat) {
      return ResponseBo.error("条件名称重复");
    } else {
      if (condition.getId() == null) {
        conditionService.save(condition);
      } else {
        conditionService.updateNotNull(condition);
      }
      return ResponseBo.ok();
    }
  }

  @PostMapping("/deleteCondition")
  public ResponseBo deleteCondition(String[] ids) {
    return ResponseBo.ok(conditionService.batchDelete(Arrays.asList(ids), "id", Condition.class));
  }
}
