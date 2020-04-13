package com.hirain.ptu.common.model;

import lombok.Data;

/**
 * @author changwei.zheng
 * @date 2020/4/10 9:42
 * @describe
 */
@Data
public class ListComIdObjRequest {
  private String comId;
  private String ip;
  private String carriagePosition;
  private String remark;
}
