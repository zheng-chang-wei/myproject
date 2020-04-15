package com.hirain.ptu.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;

import java.io.Serializable;
import java.util.Date;

/**
 * @author changwei.zheng
 * @date 2020/4/15 10:57
 * @describe
 */
@Data
public class DataOverview implements Serializable {

  private String type;
  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date startTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date endTime;
}
