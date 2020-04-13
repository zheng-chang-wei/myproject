package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.common.model.WebSocketResponse;
import com.hirain.ptu.common.utils.FtpOperation;
import com.hirain.ptu.dao.ComIdDataMapper;
import com.hirain.ptu.dao.CsPortDataMapper;
import com.hirain.ptu.dao.DownloadedFileMapper;
import com.hirain.ptu.handler.CsvDataHandler;
import com.hirain.ptu.model.DownloadedFile;
import com.hirain.ptu.service.DownloadService;
import com.hirain.ptu.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/4/3 16:27
 * @describe
 */
@Service
public class DownloadServiceImpl implements DownloadService {

  @Autowired FtpOperation ftpOperation;

  @Autowired DownloadedFileMapper downloadedFileMapper;

  @Autowired CsvDataHandler csvDataHandler;

  @Override
  public void download() throws Exception {
    List<String> needDownloadedFiles = getNeedDownloadFiles();
    WebSocketServer.sendMessage("admin", new WebSocketResponse(2,needDownloadedFiles.size()));
    for (String fileName : needDownloadedFiles) {
      InputStream inputStream = ftpOperation.downloadFile(fileName);
      if (inputStream!=null){
        if (fileName.startsWith("ComID")) {
          csvDataHandler.readComIdCSV(inputStream, fileName);
        } else if (fileName.startsWith("CSport")) {
          csvDataHandler.readCsPortCSV(inputStream, fileName);
        }
      }
    }
  }

  private List<String> getNeedDownloadFiles() throws Exception {
    List<String> allFiles = ftpOperation.getAllFiles();
    List<DownloadedFile> downloadedFiles = downloadedFileMapper.selectAll();
    List<String> needDownloadedFiles = new ArrayList<>();
    for (String fileName : allFiles) {
      if (!isDownloaded(downloadedFiles, fileName)) {
        needDownloadedFiles.add(fileName);
      }
    }
    return needDownloadedFiles;
  }

  private boolean isDownloaded(List<DownloadedFile> downloadedFiles, String fileName) {
    for (DownloadedFile file : downloadedFiles) {
      if (file.getFileName().equals(fileName)) {
        return true;
      }
    }
    return false;
  }
}
