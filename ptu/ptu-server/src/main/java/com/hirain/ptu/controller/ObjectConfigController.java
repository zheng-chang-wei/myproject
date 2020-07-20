package com.hirain.ptu.controller;

import com.hirain.ptu.common.model.ListComIdObjRequest;
import com.hirain.ptu.common.model.ListCsPortObjRequest;
import com.hirain.ptu.common.model.QueryRequest;
import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.model.CsPortObject;
import com.hirain.ptu.service.ObjectConfigService;
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
 * @date 2020/6/23 14:00
 * @describe 对象配置相关接口
 */
@RestController
@RequestMapping("/objectConfig")
public class ObjectConfigController {

  @Autowired ObjectConfigService objectConfigService;

  @GetMapping("/listComIds")
  public ResponseBo listComIds(QueryRequest request, ListComIdObjRequest listComIdObjRequest) {
    List<ComIdObject> comIdObjects = objectConfigService.listComIds();
    if (request.getPageSize()==null){
      return ResponseBo.ok(comIdObjects);
    }
    // 符合条件的数据
    List<ComIdObject> comIdObjectRightList = new ArrayList<>();
    for (ComIdObject comIdObject : comIdObjects) {
      if (isRight(listComIdObjRequest, comIdObject)){
        comIdObjectRightList.add(comIdObject);
      }
    }
    // 分页后的数据
    List<ComIdObject> comIdObjectPageList = new ArrayList<>();
    for (int i = (request.getPageNum() - 1) * request.getPageSize();
        i < (request.getPageNum() - 1) * request.getPageSize() + request.getPageSize();
        i++) {
      if (i == comIdObjectRightList.size()) {
        break;
      }
      comIdObjectPageList.add(comIdObjectRightList.get(i));
    }
    Map<String, Object> map = new HashMap<>();
    map.put("rows", comIdObjectPageList);
    map.put("total", comIdObjectRightList.size());
    return ResponseBo.ok(map);
  }

  @GetMapping("/listCsPorts")
  public ResponseBo listCsPorts(QueryRequest request, ListCsPortObjRequest listCsPortObjRequest) {
    List<CsPortObject> csPortObjects = objectConfigService.listCsPorts();
    if (request.getPageSize()==null){
      return ResponseBo.ok(csPortObjects);
    }
    // 符合条件的数据
    List<CsPortObject> csPortObjectRightList = new ArrayList<>();
    for (CsPortObject csPortObject : csPortObjects) {
      if (isRight(listCsPortObjRequest, csPortObject)){
        csPortObjectRightList.add(csPortObject);
      }
    }
    // 分页后的数据
    List<CsPortObject> csPortObjectPageList = new ArrayList<>();
    for (int i = (request.getPageNum() - 1) * request.getPageSize();
         i < (request.getPageNum() - 1) * request.getPageSize() + request.getPageSize();
         i++) {
      if (i == csPortObjectRightList.size()) {
        break;
      }
      csPortObjectPageList.add(csPortObjectRightList.get(i));
    }
    Map<String, Object> map = new HashMap<>();
    map.put("rows", csPortObjectPageList);
    map.put("total", csPortObjectRightList.size());
    return ResponseBo.ok(map);
  }



  @PostMapping("/comIdImport")
  public ResponseBo comIdImport(MultipartFile file) throws Exception {
    objectConfigService.comIdImport(file);
    return ResponseBo.ok();
  }

  @PostMapping("/csPortImport")
  public ResponseBo csPortImport(MultipartFile file) throws Exception {
    objectConfigService.csPortImport(file);
    return ResponseBo.ok();
  }
  private boolean isRight(ListComIdObjRequest listComIdObjRequest, ComIdObject comIdObject) {
    if (StringUtils.isNotEmpty(listComIdObjRequest.getComId())) {
      if (!String.valueOf(comIdObject.getComId()).contains(listComIdObjRequest.getComId())) {
        return false;
      }
    }
    if (StringUtils.isNotEmpty(listComIdObjRequest.getIp())) {
      if (!String.valueOf(comIdObject.getIp()).contains(listComIdObjRequest.getIp())) {
        return false;
      }
    }
    if (StringUtils.isNotEmpty(listComIdObjRequest.getCarriagePosition())) {
      if (!String.valueOf(comIdObject.getCarriagePosition())
              .contains(listComIdObjRequest.getCarriagePosition())) {
        return false;
      }
    }
    if (StringUtils.isNotEmpty(listComIdObjRequest.getRemark())) {
      if (!String.valueOf(comIdObject.getRemark1()).contains(listComIdObjRequest.getRemark())) {
        return false;
      }
    }
    return true;
  }
  private boolean isRight(ListCsPortObjRequest listCsPortObjRequest, CsPortObject csPortObject) {
    if (StringUtils.isNotEmpty(listCsPortObjRequest.getComId())) {
      if (!String.valueOf(csPortObject.getComId()).contains(listCsPortObjRequest.getComId())) {
        return false;
      }
    }
    if (StringUtils.isNotEmpty(listCsPortObjRequest.getIp())) {
      if (!csPortObject.getIp().contains(listCsPortObjRequest.getIp())) {
        return false;
      }
    }
    if (StringUtils.isNotEmpty(listCsPortObjRequest.getCardNo())) {
      if (!String.valueOf(csPortObject.getCardNo())
              .contains(listCsPortObjRequest.getCardNo())) {
        return false;
      }
    }
    if (StringUtils.isNotEmpty(listCsPortObjRequest.getPort())) {
      if (!String.valueOf(csPortObject.getPort()).contains(listCsPortObjRequest.getPort())) {
        return false;
      }
    }   if (StringUtils.isNotEmpty(listCsPortObjRequest.getTrainNo())) {
      if (!String.valueOf(csPortObject.getTrainNo()).contains(listCsPortObjRequest.getTrainNo())) {
        return false;
      }
    }
    return true;
  }
}
