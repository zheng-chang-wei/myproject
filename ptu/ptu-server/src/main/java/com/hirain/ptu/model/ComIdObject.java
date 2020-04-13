package com.hirain.ptu.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "t_comid_object")
public class ComIdObject implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "JDBC")
  private Integer id;

  private Integer comId;

  private String ip;

  private String multicastAddress;

  private Integer cycle;

  private Integer carriagePosition;

  private String unit;

  private String remark;
}
