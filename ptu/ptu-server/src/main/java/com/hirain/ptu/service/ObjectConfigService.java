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
  @CacheEvict(value = "objectConfig",allEntries = true)
  void comIdImport(MultipartFile file) throws Exception;

  @CacheEvict(value = "objectConfig",allEntries = true)
  void csPortImport(MultipartFile file) throws Exception;

  @Cacheable(value = "objectConfig", key = "'listComIds'")
  List<ComIdObject> listComIds();

  @Cacheable(value = "objectConfig", key = "'listCsPorts'")
  List<CsPortObject> listCsPorts();
}
