package com.hirain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Test {

  public static void main(String[] args) {
    int i = 1;
    i = i++;
    i=++i;
    System.out.println(i);
    // getFile(new File("C:\\Users\\changwei.zheng\\Desktop\\code.txt"));
    //		writerFile("C:\\Users\\changwei.zheng\\Desktop\\code.txt",
    // "C:\\Users\\changwei.zheng\\Desktop\\code1.txt");
  }

  private static void getFile(File file) {
    File[] files = file.listFiles();
    for (File f : files) {
      if (f.isDirectory()) {
        getFile(f);
      } else {
        if (f.getName().contains(".css")) {
          try {
            writerFile(f.getAbsolutePath(), "C:\\Users\\changwei.zheng\\Desktop\\code1.txt");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private static void writerFile(String inputFile, String writerFile) {
    try {
      // BufferedReader br = new BufferedReader(new FileReader(inputFile));
      BufferedWriter bw =
          new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writerFile)));
      InputStreamReader sr = new InputStreamReader(new FileInputStream(inputFile), "utf-8");
      BufferedReader br = new BufferedReader(sr);
      String line = "";
      while ((line = br.readLine()) != null) {
        if (line.equals("")) {
          continue;
        }
        if (line.contains("//")) {
          continue;
        }
        if (line.contains("\\t")) {
          continue;
        }
        if (line.contains("*")) {
          continue;
        }
        if (line.contains("/*")) {
          continue;
        }
        String newLine = new String(line.getBytes("utf-8"));
        bw.write(line);
        bw.newLine();
      }
      br.close();
      bw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
