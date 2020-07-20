package com.hirain.qsy.shaft.common.model;

import com.hirain.qsy.shaft.common.util.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @date 2020/05/28
 * <p>
 * Description:
 **/
@Data
public class HistoryData {
    /**
     * 原始表主键
     */
    private String originalprimarykey;

    /**
     * 车型
     */
    private String trainType;

    /**
     * 车号
     */
    private String trainNum;

    /**
     * 采集时间
     */
    private String acquisitionTime;

    /**
     * 环境温度1
     */
    private String ambientTemperature1;

    /**
     * 环境温度2
     */
    private String ambientTemperature2;

    /**
     * GPS速度
     */
    private String gpsSpeed;

    /**
     * 1轴测试点1温度-℃
     */
    private String axle11;
    /**
     * 1轴测试点2温度-℃
     */

    private String axle12;

    private String axle13;

    private String axle14;

    private String axle15;

    private String axle16;

    private String axle21;

    private String axle22;

    private String axle23;

    private String axle24;

    private String axle25;

    private String axle26;

    private String axle31;

    private String axle32;

    private String axle33;

    private String axle34;

    private String axle35;

    private String axle36;

    private String axle41;

    private String axle42;

    private String axle43;

    private String axle44;

    private String axle45;

    private String axle46;

    private String axle51;

    private String axle52;

    private String axle53;

    private String axle54;

    private String axle55;

    private String axle56;

    private String axle61;

    private String axle62;

    private String axle63;

    private String axle64;

    private String axle65;

    private String axle66;

    public Date getAcquisitionTime() {
        return DateUtil.string2Date(acquisitionTime);
    }

//    public Integer getAmbientTemperature1() {
//        return Integer.valueOf(ambientTemperature1);
//    }
//
//    public Integer getAmbientTemperature2() {
//        return Integer.valueOf(ambientTemperature2);
//    }
//
//    public Integer getGpsSpeed() {
//        return Integer.valueOf(gpsSpeed);
//    }
//
//    public Integer getAxle11() {
//        return Integer.valueOf(axle11);
//    }
//
//    public Integer getAxle12() {
//        return Integer.valueOf(axle12);
//    }
//
//    public Integer getAxle13() {
//        return Integer.valueOf(axle13);
//    }
//
//    public Integer getAxle14() {
//        return Integer.valueOf(axle14);
//    }
//
//    public Integer getAxle15() {
//        return Integer.valueOf(axle15);
//    }
//
//    public Integer getAxle16() {
//        return Integer.valueOf(axle16);
//    }
//
//    public Integer getAxle21() {
//        return Integer.valueOf(axle21);
//    }
//
//    public Integer getAxle22() {
//        return Integer.valueOf(axle22);
//    }
//
//    public Integer getAxle23() {
//        return Integer.valueOf(axle23);
//    }
//
//    public Integer getAxle24() {
//        return Integer.valueOf(axle24);
//    }
//
//    public Integer getAxle25() {
//        return Integer.valueOf(axle25);
//    }
//
//    public Integer getAxle26() {
//        return Integer.valueOf(axle26);
//    }
//
//    public Integer getAxle31() {
//        return Integer.valueOf(axle31);
//    }
//
//    public Integer getAxle32() {
//        return Integer.valueOf(axle32);
//    }
//
//    public Integer getAxle33() {
//        return Integer.valueOf(axle33);
//    }
//
//    public Integer getAxle34() {
//        return Integer.valueOf(axle34);
//    }
//
//    public Integer getAxle35() {
//        return Integer.valueOf(axle35);
//    }
//
//    public Integer getAxle36() {
//        return Integer.valueOf(axle36);
//    }
//
//    public Integer getAxle41() {
//        return Integer.valueOf(axle41);
//    }
//
//    public Integer getAxle42() {
//        return Integer.valueOf(axle42);
//    }
//
//    public Integer getAxle43() {
//        return Integer.valueOf(axle43);
//    }
//
//    public Integer getAxle44() {
//        return Integer.valueOf(axle44);
//    }
//
//    public Integer getAxle45() {
//        return Integer.valueOf(axle45);
//    }
//
//    public Integer getAxle46() {
//        return Integer.valueOf(axle46);
//    }
//
//    public Integer getAxle51() {
//        return Integer.valueOf(axle51);
//    }
//
//    public Integer getAxle52() {
//        return Integer.valueOf(axle52);
//    }
//
//    public Integer getAxle53() {
//        return Integer.valueOf(axle53);
//    }
//
//    public Integer getAxle54() {
//        return Integer.valueOf(axle54);
//    }
//
//    public Integer getAxle55() {
//        return Integer.valueOf(axle55);
//    }
//
//    public Integer getAxle56() {
//        return Integer.valueOf(axle56);
//    }
//
//    public Integer getAxle61() {
//        return Integer.valueOf(axle61);
//    }
//
//    public Integer getAxle62() {
//        return Integer.valueOf(axle62);
//    }
//
//    public Integer getAxle63() {
//        return Integer.valueOf(axle63);
//    }
//
//    public Integer getAxle64() {
//        return Integer.valueOf(axle64);
//    }
//
//    public Integer getAxle65() {
//        return Integer.valueOf(axle65);
//    }
//
//    public Integer getAxle66() {
//        return Integer.valueOf(axle66);
//    }
}
