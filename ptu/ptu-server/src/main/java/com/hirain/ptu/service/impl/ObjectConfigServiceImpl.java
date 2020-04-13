package com.hirain.ptu.service.impl;

import com.hirain.ptu.common.config.CommonMapper;
import com.hirain.ptu.common.utils.ReadExcelUtil;
import com.hirain.ptu.dao.ComIdObjectMapper;
import com.hirain.ptu.dao.CsPortObjectMapper;
import com.hirain.ptu.model.ComIdObject;
import com.hirain.ptu.model.CsPortObject;
import com.hirain.ptu.service.ObjectConfigService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hirain.ptu.common.utils.ReadExcelUtil.getCellValue;

/**
 * @author changwei.zheng
 * @date 2020/3/27 18:31
 * @describe 对象配置service
 */
@Service
public class ObjectConfigServiceImpl implements ObjectConfigService {

  @Autowired ComIdObjectMapper comIdObjectMapper;
  @Autowired CsPortObjectMapper csPortObjectMapper;

  @Override
  public void comIdImport(MultipartFile file) throws Exception {

    File targetFile = transferFile(file);
    Workbook workbook = ReadExcelUtil.createWorkbook(targetFile.getAbsolutePath());
    Sheet sheet = workbook.getSheetAt(0);
    List<ComIdObject> comIdObjectList = new ArrayList<>();
    for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);
      ComIdObject comIdObject = new ComIdObject();
      comIdObject.setComId(Integer.valueOf(getCellValue(row.getCell(1))));
      comIdObject.setIp(getCellValue(row.getCell(2)));
      comIdObject.setCarriagePosition(Integer.valueOf(getCellValue(row.getCell(3))));
      comIdObject.setRemark(getCellValue(row.getCell(4)));
      comIdObjectList.add(comIdObject);
    }
    deleteAll(comIdObjectMapper);
    comIdObjectMapper.insertList(comIdObjectList);
    targetFile.delete();
  }

  @Override
  public void csPortImport(MultipartFile file) throws Exception {

    File targetFile = transferFile(file);
    Workbook workbook = ReadExcelUtil.createWorkbook(targetFile.getAbsolutePath());
    Sheet sheet = workbook.getSheetAt(0);
    List<CsPortObject> comIdObjectList = new ArrayList<>();
    for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);
      CsPortObject csPortObject = new CsPortObject();
      csPortObject.setComId(Integer.valueOf(getCellValue(row.getCell(1))));
      csPortObject.setIp(getCellValue(row.getCell(2)));
      csPortObject.setTrainNo(Integer.valueOf(getCellValue(row.getCell(3))));
      csPortObject.setCardNo(Integer.valueOf(getCellValue(row.getCell(4))));
      csPortObject.setPort(Integer.valueOf(getCellValue(row.getCell(5))));
      comIdObjectList.add(csPortObject);
    }
    deleteAll(csPortObjectMapper);
    csPortObjectMapper.insertList(comIdObjectList);
    targetFile.delete();
  }

  @Override
  public List<ComIdObject> listComIds() {
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
}
