package com.hirain.dome;

import com.hirain.ptu.Application;
import com.hirain.ptu.common.utils.FtpOperation;
import com.hirain.ptu.dao.ComIdDataMapper;
import com.hirain.ptu.dao.CsPortDataMapper;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.CsPortData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FtpTest {

  @Autowired FtpOperation ftpOperation;

  @Test
  public void testGetAllFiles() {
    try {
      List<String> files = ftpOperation.getAllFiles();
      System.out.println(files);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
