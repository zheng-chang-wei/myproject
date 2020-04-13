package com.hirain.ptu.common.utils;

import com.hirain.ptu.model.TargetConfig;
import com.hirain.ptu.service.TargetConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/4/3 10:04
 * @describe 文件上传下载工具类
 */
@Service
@Slf4j
public class FtpOperation {

  @Value("${ftp.username}")
  private String userName;

  @Value("${ftp.password}")
  private String passWord;

  @Value("${ftp.port}")
  private int port;
  /** 目标IP */
  private String targetIp;

  /** 文件存放的目录 */
  private String targetFilePath;

  @Autowired TargetConfigService targetConfigService;
  /** 连接到ftp服务器 */
  public FTPClient ftpClient() throws Exception {
    // ftp客户端
    FTPClient ftpClient = new FTPClient();
    setIpAndFilePath();
    if (!ftpClient.isConnected()) {
      int reply;
      try {

        ftpClient.connect(targetIp, port);
        ftpClient.login(userName, passWord);
        ftpClient.setControlEncoding("utf-8"); // 设置ftp字符集
        // 设置传输二进制文件
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        ftpClient.enterLocalPassiveMode();

        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
          ftpClient.disconnect();
          log.error("connectToServer FTP server refused connection.");
        }
        return ftpClient;
      } catch (FTPConnectionClosedException ex) {
        log.error(
            "服务器:IP：" + targetIp + "没有连接数！there are too many connected users,please try later", ex);
        throw ex;
      } catch (Exception e) {
        log.error("登录ftp服务器【" + targetIp + "】失败", e);
        throw e;
      }
    }
    return null;
  }

  private void setIpAndFilePath() {
    List<TargetConfig> targetConfigs = targetConfigService.selectAll();
    if (targetConfigs.size() != 0) {
      TargetConfig targetConfig = targetConfigs.get(0);
      targetIp = targetConfig.getTargetIp();
      targetFilePath = targetConfig.getTargetPath();
    }
  }


  public List<String> getAllFiles() throws Exception {
    FTPClient ftpClient = ftpClient();
    if (!ftpClient.isConnected()) {
      return null;
    }
    final List<String> names = new ArrayList<String>();
    try {
      ftpClient.changeWorkingDirectory(targetFilePath);
      final String[] listNames = ftpClient.listNames();
      for (final String name : listNames) {
        if (name.endsWith(".csv")) {
          names.add(name);
        }
      }
    } catch (IOException e) {
      throw e;
    }finally {
      closeConnect(ftpClient);
    }
    return names;
  }
  /**
   * 功能：根据文件名称，下载文件流
   *
   * @param filename
   * @return
   * @throws IOException
   */
  public InputStream downloadFile(String filename) throws Exception {
    FTPClient ftpClient = ftpClient();
    if (!ftpClient.isConnected()) {
      return null;
    }
    InputStream in = null;
    try {
      ftpClient.changeWorkingDirectory(targetFilePath);
      // ftp文件获取文件
      in = ftpClient.retrieveFileStream(filename);
    } catch (FTPConnectionClosedException e) {
      log.error("ftp连接被关闭！", e);
      throw e;
    } catch (Exception e) {
      log.error("ERR : downloadFile  " + filename + " from ftp : failed!", e);
      throw e;
    } finally {
      closeConnect(ftpClient);
    }
    return in;
  }

  /** 功能：关闭连接 */
  private void closeConnect(FTPClient ftpClient) throws IOException {
    try {
      if (ftpClient != null && ftpClient.isConnected()) {
        ftpClient.logout();
        ftpClient.disconnect();
      }
    } catch (Exception e) {
      log.error("ftp连接关闭失败！", e);
      throw  e;
    }
  }
}
