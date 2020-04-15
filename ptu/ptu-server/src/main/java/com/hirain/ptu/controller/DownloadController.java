package com.hirain.ptu.controller;

import com.hirain.ptu.common.model.DataOverview;
import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.common.model.TableNameConstant;
import com.hirain.ptu.common.model.WebSocketResponse;
import com.hirain.ptu.service.*;
import com.hirain.ptu.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/4/3 9:45
 * @describe 数据下载
 */
@RestController
@RequestMapping("/download")
public class DownloadController {
  @Autowired DownloadService downloadService;

  @Autowired ComIdDataService comIdDataService;
  @Autowired CsPortDataService csPortDataService;

  @Autowired ManageService manageService;

  @GetMapping("/download")
  public ResponseBo download() {
    try {
      downloadService.download();
      return ResponseBo.ok();
    } catch (Exception e) {
      WebSocketServer.sendMessage("admin", new WebSocketResponse(500, null));
      return ResponseBo.error("数据下载失败");
    }
  }

  @GetMapping("/getDataOverview")
  public ResponseBo getDataOverview() {
    List<DataOverview> dataOverviews = new ArrayList<>();
    DataOverview comIdDataOverview = comIdDataService.selectTimeRange();
    DataOverview csPortDataOverview = csPortDataService.selectTimeRange();
    if (comIdDataOverview != null) {
      comIdDataOverview.setName("ComId对象数据时间范围");
      comIdDataOverview.setType(TableNameConstant.COMID_TYPE);
      dataOverviews.add(comIdDataOverview);
    }
    if (csPortDataOverview != null) {
      csPortDataOverview.setName("CsPort对象数据时间范围");
      csPortDataOverview.setType(TableNameConstant.CSPORT_TYPE);
      dataOverviews.add(csPortDataOverview);
    }
    return ResponseBo.ok(dataOverviews);
  }

  @PostMapping("/delete")
  public ResponseBo delete(String type, String deadline) throws ParseException {
    switch (type) {
      case TableNameConstant.COMID_TYPE:
        comIdDataService.deleteByTime(deadline);
        break;
      case TableNameConstant.CSPORT_TYPE:
        csPortDataService.deleteByTime(deadline);
        break;
    }
    WebSocketServer.sendMessage("admin", new WebSocketResponse(1, null));
    return ResponseBo.ok();
  }

  @PostMapping("/dropTable")
  public ResponseBo dropTable(String type) {
    switch (type) {
      case TableNameConstant.COMID_TYPE:
        comIdDataService.dropTable();
        break;
      case TableNameConstant.CSPORT_TYPE:
        csPortDataService.dropTable();
        break;
    }
    WebSocketServer.sendMessage("admin", new WebSocketResponse(1, null));
    return ResponseBo.ok();
  }

  @PostMapping("/clearDownloadedFiles")
  public ResponseBo clearDownloadedFiles() {
    downloadService.clearDownloadedFiles();
    return ResponseBo.ok();
  }
}
