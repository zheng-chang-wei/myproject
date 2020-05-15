package com.hirain.dome;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test {

  public static void main(String[] args) throws Exception {
    String[] items = {"1", "2"};
    for (String item : items) {
      item = "3";
    }
    for (int i=0;i<items.length;i++){
      items[i]="3";
    }
    System.out.println(items);
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
