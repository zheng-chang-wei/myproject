package com.hirain.ptu.controller;

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

/**
 * @author changwei.zheng
 * @date 2020/4/3 9:45
 * @describe 数据下载
 */
@RestController
@RequestMapping("/download")
public class DownloadController {
  @Autowired DownloadService downloadService;
  @Autowired DataOverviewService dataOverviewService;

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
    return ResponseBo.ok(dataOverviewService.getDataOverview());
  }

  @PostMapping("/delete")
  public ResponseBo delete(Integer index, String deadline) throws ParseException {
    switch (index) {
      case 0:
        comIdDataService.deleteByTime(deadline);
        break;
      case 1:
        csPortDataService.deleteByTime(deadline);
        break;
    }
    WebSocketServer.sendMessage("admin", new WebSocketResponse(1,null));
    return ResponseBo.ok();
  }

  @PostMapping("/dropTable")
  public ResponseBo dropTable(Integer index) {
    switch (index) {
      case 0:
        comIdDataService.dropTable();
        break;
      case 1:
        csPortDataService.dropTable();
        break;
    }
    WebSocketServer.sendMessage("admin", new WebSocketResponse(1, null));
    return ResponseBo.ok();
  }
}
