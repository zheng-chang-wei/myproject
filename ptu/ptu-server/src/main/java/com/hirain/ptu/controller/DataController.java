package com.hirain.ptu.controller;

import com.hirain.ptu.common.annotation.Log;
import com.hirain.ptu.common.model.TableNameConstant;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.CsPortData;
import com.hirain.ptu.service.ComIdDataService;
import com.hirain.ptu.service.CsPortDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.ptu.common.model.DisplayDataCommonRequest;
import com.hirain.ptu.common.model.ResponseBo;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataController {

  @Autowired ComIdDataService comIdDataService;
  @Autowired CsPortDataService csPortDataService;

  @GetMapping("/getComIdTableDatas")
  @Log(TableNameConstant.COMID_DATA_TABLE_NAME)
  public ResponseBo getComIdTableDatas(DisplayDataCommonRequest commonRequest) {
    List<ComIdData> tableDatas = comIdDataService.getComIdTableDatas(commonRequest);
    Map<String, Object> map = new HashMap<>();
    map.put("rows", tableDatas);
    map.put("total", tableDatas.size());
    return ResponseBo.ok(map);
  }

  @GetMapping("/getCsPortTableDatas")
  @Log(TableNameConstant.CSPROT_DATA_TABLE_NAME)
  public ResponseBo getCsPortTableDatas(DisplayDataCommonRequest commonRequest) {
    List<CsPortData> tableDatas = csPortDataService.getCsPortTableDatas(commonRequest);
    Map<String, Object> map = new HashMap<>();
    map.put("rows", tableDatas);
    map.put("total", tableDatas.size());
    return ResponseBo.ok(map);
  }

  @GetMapping("/getComIdChartData")
  @Log(TableNameConstant.COMID_DATA_TABLE_NAME)
  public ResponseBo getComIdChartData(DisplayDataCommonRequest commonRequest) throws Exception {
    return ResponseBo.ok(comIdDataService.getChartData(commonRequest));
  }

  @GetMapping("/getCsPortChartData")
  @Log(TableNameConstant.CSPROT_DATA_TABLE_NAME)
  public ResponseBo getCsPortChartData(DisplayDataCommonRequest commonRequest) throws Exception {
    return ResponseBo.ok(csPortDataService.getChartData(commonRequest));
  }
}
