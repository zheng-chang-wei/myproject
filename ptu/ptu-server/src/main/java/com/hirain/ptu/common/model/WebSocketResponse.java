package com.hirain.ptu.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author changwei.zheng
 * @date 2020/4/8 17:23
 * @describe
 */
@Data
@AllArgsConstructor
public class WebSocketResponse {
  /**
   * 1:删除数据
   * 2:要下载文件的个数
   * 3:一个文件下载成功
   * 500:下载文件失败
   */
  private Integer code;
  private Object data;
}
