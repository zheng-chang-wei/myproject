package com.hirain.ptu.service;

import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.DataOverview;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author changwei.zheng
 * @date 2020/4/3 16:27
 * @describe
 */
public interface DownloadService {
  void download() throws Exception;
}
