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
  private Integer code;
  private Object data;
}
