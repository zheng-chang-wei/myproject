package com.hirain.ptu.common.utils;

import com.hirain.ptu.common.model.AttributeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author changwei.zheng
 * @date 2020/4/1 14:40
 * @describe 驼峰命名转换
 */
public class HumpConversion {
  public static final char UNDERLINE = '_';

  public static String getExpression(String expression) {
    // 驼峰式命名list
    List<String> humpList = getFeatures(expression);
    for (String feature : humpList) {
      // 将表达式中的驼峰命名方式改为下划线
      expression =
          expression.replace(
              feature, camelToUnderline(AttributeMap.featuresMap.get(feature.trim())));
      expression = expression.replace("==", "=");
      expression = expression.replace("&&", " and ");
      expression = expression.replace("||", " or ");
    }
    return expression;
  }

  public static List<String> getFeatures(String expressionString) {
    // 驼峰式命名list
    List<String> humpList = new ArrayList<>();
    String[] ands = expressionString.split("&&");
    for (String and : ands) {
      String[] ors = and.split("\\|\\|");
      for (String or : ors) {
        String logicalOperator = getLogicalOperator(or);
        humpList.add(or.split(logicalOperator)[0]);
      }
    }
    return humpList;
  }

  public static String getLogicalOperator(String element) {
    String[] logicalOperatorOptions = {">", "<", "==", "!="};
    for (int index = 0; index < logicalOperatorOptions.length; index++) {
      String logicalOperator = logicalOperatorOptions[index];
      if (element.indexOf(logicalOperator) != -1) {
        return logicalOperator;
      }
    }
    return null;
  }

  /**
   * 驼峰格式字符串转换为下划线格式字符串
   *
   * @param param
   * @return
   */
  public static String camelToUnderline(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (Character.isUpperCase(c)) {
        sb.append(UNDERLINE);
        sb.append(Character.toLowerCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * 下划线格式字符串转换为驼峰格式字符串
   *
   * @param param
   * @return
   */
  public static String underlineToCamel(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (c == UNDERLINE) {
        if (++i < len) {
          sb.append(Character.toUpperCase(param.charAt(i)));
        }
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * 下划线格式字符串转换为驼峰格式字符串2
   *
   * @param param
   * @return
   */
  public static String underlineToCamel2(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    StringBuilder sb = new StringBuilder(param);
    Matcher mc = Pattern.compile("_").matcher(param);
    int i = 0;
    while (mc.find()) {
      int position = mc.end() - (i++);
      sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    String aaa = "app_version_fld";
    System.out.println(underlineToCamel(aaa));
    System.out.println(underlineToCamel2(aaa));
    aaa = "appVersionFld";
    System.out.println(camelToUnderline(aaa));
  }
}
