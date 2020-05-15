package com.hirain.ptu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Table(name = "t_csport_data")
public class CsPortData implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "JDBC")
  private Long id;

  @Transient private String day;
  @Transient private String time;
  private String ip;
  private Integer comId;
  private Integer port;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date date;

  private Byte linkPHM;

  private Byte linkFlashPHM;

  private Byte rxTrafficPHM;

  private Byte rxErrRatePHM;

  private Byte rxErrPredictPHM;

  private Byte txTrafficPHM;

  private Byte txErrRatePHM;

  private Byte txErrPredictPHM;

  private Byte enable;

  private Float linkMean;

  private Integer rxMcast;

  private Integer rxTrafficMean;

  private Integer rxTrafficStd;

  private Integer rxErrRateMean;

  private Integer rxErrRateStd;

  private Integer txMcast;

  private Integer txTrafficMean;

  private Integer txTrafficStd;

  private Integer txErrRateMean;

  private Integer txErrRateStd;

  private Float frameCnt;
}
