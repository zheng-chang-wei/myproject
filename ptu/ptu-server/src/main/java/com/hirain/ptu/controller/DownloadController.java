package com.hirain.ptu.controller;

import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.common.model.WebSocketResponse;
import com.hirain.ptu.service.ComIdDataService;
import com.hirain.ptu.service.CsPortDataService;
import com.hirain.ptu.service.DataOverviewService;
import com.hirain.ptu.service.DownloadService;
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

  @GetMapping("/download")
  public ResponseBo download()  {
    try{
      downloadService.download();
      return ResponseBo.ok();
    }catch (Exception e){
      WebSocketServer.sendMessage("admin", new WebSocketResponse(500, null));
      return ResponseBo.error("数据下载失败");
    }
  }

  @GetMapping("/getDataOverview")
  public ResponseBo getDataOverview() {
    return ResponseBo.ok(dataOverviewService.selectAll());
  }

  @PostMapping("/delete")
  public ResponseBo delete(String deadline) throws ParseException {
    comIdDataService.deleteByTime(deadline);
    csPortDataService.deleteByTime(deadline);
    dataOverviewService.deleteByTime(deadline);
    return ResponseBo.ok();
  }
}
