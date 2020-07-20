package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.common.exception.CustomException;
import com.hirain.ptu.common.model.AttributeMap;
import com.hirain.ptu.common.utils.ReadExcelUtil;
import com.hirain.ptu.common.utils.VerifyUtil;
import com.hirain.ptu.dao.ComIdObjectMapper;
import com.hirain.ptu.dao.CsPortObjectMapper;
import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.model.CsPortObject;
import com.hirain.ptu.service.ObjectConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.hirain.ptu.common.utils.ReadExcelUtil.getCellValue;

/**
 * @author changwei.zheng
 * @date 2020/3/27 18:31
 * @describe 对象配置service
 */
@Service
@Slf4j
public class ObjectConfigServiceImpl implements ObjectConfigService {

  @Autowired ComIdObjectMapper comIdObjectMapper;
  @Autowired CsPortObjectMapper csPortObjectMapper;

  @Override
  public void comIdImport(MultipartFile file) throws Exception {
    File targetFile = transferFile(file);
    try {
      List<ComIdObject> comIdObjectList = getComIdObjectsFromFile(targetFile);
      deleteAll(comIdObjectMapper);
      comIdObjectMapper.insertList(comIdObjectList);
    } catch (Exception e) {
      throw e;
    } finally {
      targetFile.delete();
    }
  }

  @Override
  public void csPortImport(MultipartFile file) throws Exception {
    File targetFile = transferFile(file);
    try {
      List<CsPortObject> comIdObjectList = getCsPortObjectsFromFile(targetFile);
      deleteAll(csPortObjectMapper);
      csPortObjectMapper.insertList(comIdObjectList);
    } catch (Exception e) {
      throw e;
    } finally {
      targetFile.delete();
    }
  }

  @Override
  public List<ComIdObject> listComIds() {
    log.info("listComIds");
    return comIdObjectMapper.selectAllOrderByComId();
  }

  @Override
  public List<CsPortObject> listCsPorts() {
    return csPortObjectMapper.selectAllOrderByComId();
  }

  private File transferFile(MultipartFile multipartFile) throws IOException {
    String realPath = System.getProperty("user.dir");
    String originalFilename = multipartFile.getOriginalFilename();
    File targetFile = new File(realPath + File.separator + originalFilename);
    multipartFile.transferTo(targetFile);
    return targetFile;
  }

  private void deleteAll(CommonMapper mapper) {
    for (Object record : mapper.selectAll()) {
      mapper.delete(record);
    }
  }

  private List<ComIdObject> getComIdObjectsFromFile(File targetFile) throws Exception {
    Workbook workbook = ReadExcelUtil.createWorkbook(targetFile.getAbsolutePath());
    Sheet sheet = workbook.getSheetAt(0);
    List<ComIdObject> comIdObjectList = new ArrayList<>();
    try {
      List<String> heads = getHeads(sheet);
      verifyTopRow(heads, AttributeMap.comIdMapData);
      for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        ComIdObject comIdObject = new ComIdObject();
        setProperty(heads, row, comIdObject, AttributeMap.comIdMapData);
        comIdObjectList.add(comIdObject);
      }
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }

    return comIdObjectList;
  }

  private List<CsPortObject> getCsPortObjectsFromFile(File targetFile) throws Exception {
    Workbook workbook = ReadExcelUtil.createWorkbook(targetFile.getAbsolutePath());
    Sheet sheet = workbook.getSheetAt(0);
    List<CsPortObject> csPortObjects = new ArrayList<>();
    try {
      List<String> heads = getHeads(sheet);
      verifyTopRow(heads, AttributeMap.csPortMapData);
      for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        CsPortObject csPortObject = new CsPortObject();
        setProperty(heads, row, csPortObject, AttributeMap.csPortMapData);
        csPortObjects.add(csPortObject);
      }
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
    return csPortObjects;
  }

  private List<String> getHeads(Sheet sheet) {
    List<String> heads = new ArrayList<>();
    Row firstRow = sheet.getRow(0);
    for (int j = 0; j < firstRow.getLastCellNum(); j++) {
      String cellValue = getCellValue(firstRow.getCell(j));
      heads.add(cellValue);
    }
    return heads;
  }

  private void setProperty(List<String> heads, Row row, Object obj, Map<String, String> map)
      throws IllegalAccessException, InvocationTargetException {
    for (int j = 1; j < row.getLastCellNum(); j++) {
      String cellValue = getCellValue(row.getCell(j));
      String proName = map.get(heads.get(j));
      if ("ip".equals(proName)) {
        if (!VerifyUtil.verifyIp(cellValue)) {
          throw new CustomException("文件第" + (row.getRowNum() + 1) + "行IP格式错误");
        }
      }
      BeanUtils.setProperty(obj, proName, cellValue);
    }
  }

  /** 校验首行是否符合要求 */
  private boolean verifyTopRow(List<String> heads, Map<String, String> map) {
    for (int i = 1; i < heads.size(); i++) {
      String head = heads.get(i);
      // 判断
      if (!map.containsKey(head)) {
        throw new CustomException("文件格式错误");
      }
    }
    return true;
  }
}
