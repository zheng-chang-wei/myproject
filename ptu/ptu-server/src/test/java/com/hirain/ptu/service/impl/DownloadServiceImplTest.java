package com.hirain.ptu.service.impl;

import com.hirain.BaseTest;
import com.hirain.ptu.Application;
import com.hirain.ptu.dao.DownloadedFileMapper;
import com.hirain.ptu.handler.CsvDataHandler;
import com.hirain.ptu.model.DownloadedFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/4/4 20:46
 * @describe
 */

public class DownloadServiceImplTest extends BaseTest {

  @Autowired CsvDataHandler csvHandler;
  @Autowired DownloadedFileMapper downloadedFileMapper;

  @Test
  public void download() throws Exception {
    List<String> allFiles = getAllFiles();
    List<DownloadedFile> downloadedFiles = downloadedFileMapper.selectAll();
    for (String fileName : allFiles) {
      if (!isDownloaded(downloadedFiles, fileName)) {
        InputStream inputStream = downloadFile(fileName);
        if (fileName.startsWith("ComID")) {
          csvHandler.readComIdCSV(inputStream, fileName);
        } else if (fileName.startsWith("CSport")) {
          csvHandler.readCsPortCSV(inputStream, fileName);
        }
      }
    }
  }

  private InputStream downloadFile(String fileName) throws FileNotFoundException {
    String path = "E:\\01项目相关\\PTU\\英可测试数据--给长伟\\";
    return new FileInputStream(path + fileName + ".csv");
  }

  private List<String> getAllFiles() {
    List<String> list = new ArrayList<>();
    list.add("ComID20200217092432_00000111");
    list.add("ComID20200217104528_00000111");
//    list.add("ComID20200217121322_00000111");
//
//    list.add("ComID20200217122245_00000111");
//
//    list.add("ComID20200217123206_00000111");
//
//    list.add("ComID20200220230522_00000111");
//
//    list.add("ComID20200220231626_00000111");
//
//    list.add("ComID20200220232808_00000111");
//
//    list.add("CSport20200217090619_11001000");
//
//    list.add("CSport20200217092432_11111000");
//
//    list.add("CSport20200217124218_11001000");
//
//    list.add("CSport20200217130207_11111000");
//
//    list.add("CSport20200217130702_11111000");
//
//    list.add("CSport20200217131216_11001000");
//
//    list.add("CSport20200217131736_11001000");
//    list.add("CSport20200217132328_11001000");
//    list.add("CSport20200220225557_11001000");
//    list.add("CSport20200304171845_10000000");
    return list;
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
