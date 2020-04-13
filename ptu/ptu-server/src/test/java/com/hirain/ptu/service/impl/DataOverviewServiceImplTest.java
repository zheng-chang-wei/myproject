package com.hirain.ptu.service.impl;

import com.hirain.ptu.Application;
import com.hirain.ptu.service.DataOverviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author changwei.zheng
 * @date 2020/4/7 18:48
 * @describe
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DataOverviewServiceImplTest {

  @Autowired DataOverviewService dataOverviewService;

  @Test
  public void test1() {
    dataOverviewService.test();
  }
}
