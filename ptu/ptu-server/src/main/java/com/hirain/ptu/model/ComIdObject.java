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

  /** 组播地址 */
  private String multicastAddress;
  /** 周期 */
  private Integer cycle;
  /** 报文长度 */
  private Integer packetLength;
  /** 车厢位置 */
  private Integer carriagePosition;
  /** 备注 */
  private String remark1;

  private String remark2;
}
