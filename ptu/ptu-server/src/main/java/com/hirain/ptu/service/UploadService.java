package com.hirain.ptu.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author changwei.zheng
 * @date 2020/5/6 19:08
 * @describe
 */
public interface UploadService {
  String uploadFile(MultipartFile file) throws  Exception;
}
