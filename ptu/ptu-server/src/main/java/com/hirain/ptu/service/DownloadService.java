package com.hirain.ptu.service;

/**
 * @author changwei.zheng
 * @date 2020/4/3 16:27
 * @describe
 */
public interface DownloadService {
  void download() throws Exception;

  void clearDownloadedFiles();
}
