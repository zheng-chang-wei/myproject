package com.hirain.ptu.controller;

import com.hirain.ptu.common.annotation.Log;
import com.hirain.ptu.common.model.*;
import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.model.CsPortObject;
import com.hirain.ptu.service.ObjectConfigService;
import com.hirain.ptu.service.OverviewService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author changwei.zheng
 * @date 2020/4/3 9:45
 * @describe 数据总览
 */
@RestController
@RequestMapping("/overview")
public class OverviewController {

  @Autowired OverviewService overviewService;

  @GetMapping("/listComIds")
  @Log(TableNameConstant.COMID_DATA_TABLE_NAME)
  public ResponseBo listComIds(CommonParms CommonParms) {
    return ResponseBo.ok(overviewService.listComIdsOverview(CommonParms));
  }

  @GetMapping("/listCsPorts")
  @Log(TableNameConstant.CSPROT_DATA_TABLE_NAME)
  public ResponseBo listCsPorts(CommonParms CommonParms) {
    return ResponseBo.ok(overviewService.listCsPorts(CommonParms));
  }
}
