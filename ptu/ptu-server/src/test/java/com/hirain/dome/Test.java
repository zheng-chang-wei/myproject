package com.hirain.dome;

import com.hirain.ptu.common.model.AttributeMappingConfigurationData;
import com.hirain.ptu.model.ComIdData;
import com.hirain.ptu.model.CsPortData;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Test {

  public static void main(String[] args) throws Exception {
    String s = "1.00";
    Float aFloat = Float.valueOf(s);
    int i = aFloat.intValue();
    System.out.println(i);
  }

  private static List<String> partitions() {
    final List<String> partitions = new ArrayList<>();
    final LocalDateTime date = LocalDateTime.now();
    LocalDateTime nextMonth = date.plusMonths(1);
    for (int i = 70; i >= 0; i--) {
      LocalDateTime dateTime = nextMonth.plusDays(-i);
      final String time = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      partitions.add(time);
    }
    return partitions;
  }
}
