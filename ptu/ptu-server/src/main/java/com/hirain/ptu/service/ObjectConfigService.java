package com.hirain.ptu.service;

import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.model.CsPortObject;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ObjectConfigService {
  void comIdImport(MultipartFile file) throws Exception;

  void csPortImport(MultipartFile file) throws Exception;

  List<ComIdObject> listComIds();

  List<CsPortObject> listCsPorts();
}
