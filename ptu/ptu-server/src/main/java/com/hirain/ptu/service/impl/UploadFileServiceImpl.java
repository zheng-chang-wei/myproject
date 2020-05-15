package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.common.model.WebSocketResponse;
import com.hirain.ptu.handler.CsvDataHandler;
import com.hirain.ptu.service.DownloadService;
import com.hirain.ptu.service.UploadService;
import com.hirain.ptu.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author changwei.zheng
 * @date 2020/5/6 19:08
 * @describe
 */
@Service
public class UploadFileServiceImpl implements UploadService {
  @Autowired CsvDataHandler csvDataHandler;

  @Autowired DownloadService downloadService;

  @Override
  public String uploadFile(MultipartFile multipartFile) throws Exception {
    String fileName = multipartFile.getOriginalFilename();
    if (!downloadService.isExist(fileName)){
      File file = transferFile(multipartFile);
      FileInputStream inputStream = new FileInputStream(file);
      if (fileName.startsWith("ComID")) {
        csvDataHandler.readComIdCSV(inputStream, fileName);
        WebSocketServer.sendMessage("admin", new WebSocketResponse(4, ResponseBo.ok(fileName+" 上传完成")));
      } else if (fileName.startsWith("CSport")) {
        csvDataHandler.readCsPortCSV(inputStream, fileName);
        WebSocketServer.sendMessage("admin", new WebSocketResponse(4, ResponseBo.ok(fileName+" 上传完成")));
      } else {
        WebSocketServer.sendMessage("admin", new WebSocketResponse(4, ResponseBo.error(fileName+" 上传失败，文件名不合法")));
      }
      file.delete();
    }else {
      WebSocketServer.sendMessage("admin", new WebSocketResponse(4, ResponseBo.warn(fileName+" 已上传")));
    }
    return fileName;
  }

  private File transferFile(MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      throw new IOException("文件为空");
    }
    String uploadRoot = System.getProperty("user.dir") + "//imgs";
    File root = new File(uploadRoot);
    if (!root.exists()) {
      root.mkdirs();
    }
    String extension = getExtension(file.getOriginalFilename());
    String filename = UUID.randomUUID().toString() + extension;
    File serverFile = new File(root, filename);
    file.transferTo(serverFile);
    return serverFile;
  }

  private String getExtension(String filename) {
    int lastIndexOf = filename.lastIndexOf(".");
    String extension = filename.substring(lastIndexOf);
    return extension;
  }
}
