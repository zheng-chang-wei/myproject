package com.hirain.ptu.controller;

import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.common.model.WebSocketResponse;
import com.hirain.ptu.service.UploadService;
import com.hirain.ptu.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author changwei.zheng
 * @date 2020/4/3 9:45
 * @describe 数据上传
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadFileController {
  @Autowired UploadService uploadService;

  @PostMapping("/uploadFile")
  public ResponseBo uploadFile(MultipartFile file) throws Exception {
    String fileName = file.getOriginalFilename();
    try {
      uploadService.uploadFile(file);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      WebSocketServer.sendMessage(
          "admin", new WebSocketResponse(4, ResponseBo.error(fileName + " 上传失败")));
    }
    return ResponseBo.ok(fileName);
  }
}
