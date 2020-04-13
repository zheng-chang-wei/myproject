package com.hirain.ptu.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "t_csport_object")
public class CsPortObject implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "JDBC")
  private Integer id;

  private Integer comId;

  private String ip;

  private Integer trainNo;

  private Integer cardNo;

  private Integer port;
}
