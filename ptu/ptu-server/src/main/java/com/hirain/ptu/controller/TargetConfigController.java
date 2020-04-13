package com.hirain.ptu.controller;

import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.model.TargetConfig;
import com.hirain.ptu.service.TargetConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/3/27 18:45
 * @describe 参数配置Controller
 */
@RestController
@RequestMapping("/targetConfig")
public class TargetConfigController {

  @Autowired TargetConfigService targetConfigService;

  @GetMapping("/getTargetConfig")
  public ResponseBo getTargetConfig() {
    List<TargetConfig> targetConfigs = targetConfigService.selectAll();
    return ResponseBo.ok(targetConfigs.get(0));
  }

  @PostMapping("/setTargetConfig")
  public ResponseBo setTargetConfig(TargetConfig targetConfig) {
    targetConfigService.updateNotNull(targetConfig);
    return ResponseBo.ok();
  }
}
