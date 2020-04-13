package com.hirain.ptu.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Table(name = "t_comid_data")
public class ComIdData implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "JDBC")
  private Long id;

  @Transient private String day;
  @Transient private String time;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date date;

  private String ip;

  private Integer comId;

  private Byte periodStabilityPHM;

  private Byte lostRatePHM;

  private Byte abnomalLostPHM;

  private Integer windowTime;

  private Integer periodMean;

  private Integer periodStd;

  private Float lostRate;

  private Float lostMaxRate;

  private Float lifeSignalStopRate;

  private Float lifeSignalStopMaxRate;


}
