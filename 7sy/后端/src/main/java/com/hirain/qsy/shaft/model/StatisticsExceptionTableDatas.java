package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class StatisticsExceptionTableDatas implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1371514007497952840L;

    private String axleNum;

    private Integer pointNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date faultTime;

    private String details = "";

    public StatisticsExceptionTableDatas() {

    }

    public StatisticsExceptionTableDatas(String axleNum, int pointNum, Date faultTime) {

        this.axleNum = axleNum;
        this.pointNum = pointNum;
        this.faultTime = faultTime;
    }

}
