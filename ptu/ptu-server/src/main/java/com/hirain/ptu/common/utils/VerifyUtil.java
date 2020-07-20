package com.hirain.ptu.common.utils;

import java.util.regex.Pattern;

/**
 * @author changwei.zheng
 * @date 2020/6/23 10:35
 * @describe
 */
public class VerifyUtil {

  public static boolean verifyIp(String ip) {
    String ipReg =
        "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    Pattern ipPattern = Pattern.compile(ipReg);
    return ipPattern.matcher(ip).matches();
  }
}
