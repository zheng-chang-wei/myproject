/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.util;

import java.util.Date;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Apr 25, 2019 7:16:50 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 25, 2019 zepei.tao@hirain.com 1.0 create file
 */
public class GroundAccessHelper {

	public static final int TG_REGISTER_SID = 0x01;

	public static final int TG_STORAGESTATUS_SID = 0x02;

	public static final int TG_FAULTINFO_SID = 0x03;

	public static final int TG_SYSTEMCONFIGINFO_SID = 0x04;

	public static final int TG_DELETERESULT_SID = 0x05;

	public static final int TG_CONFIGRESULT_SID = 0x06;

	public static final int TG_DOORMESSAGE_SID = 0x07;

	public static final int TG_ONOFFLINENOTICE_SID = 0x08;

	public static final int TG_TRAINOFFSET_SID = 0x09;

	public static final int TG_LIFEINFO_SID = 0x0A;

	public static final int TG_SUBHEALTH_SID = 0x0B;

	public static final int GT_REGISTER_SID = 0x11;

	public static final int GT_DELETE_SID = 0x15;

	public static final int GT_USERUPADTE_SID = 0x16;

	public static final int GT_ONOFFLINENOTICE_SID = 0x18;

	public static final int GT_TRAINSTATUSUPADTE_SID = 0x19;

	public static final String TG_KAFKA_TOPIC = "train-ground";

	public static final String GT_KAFKA_TOPIC = "ground-train";

	public static final String SUBHEALTH_KAFKA_TOPIC = "sub-health-result";

	public static final String DIGITAL_TWIN_KAFKA_TOPIC = "digitaltwin-result";

	public static final String DATA_RECORD_TOPIC = "big-data-record";

	public static final int GG_DATA_RECORD = 0x21;

	public static final int GG_METADATA_EVENT = 0x22;

	/**
	 * @param time
	 * @return
	 */
	public static int calDaysBeforeNow(Date time) {
		return calDaysBetween(time, new Date());
	}

	public static int calDaysBetween(Date time, Date now) {
		int days = (int) (time.getTime() / (1000 * 60 * 60 * 24));
		int today = (int) (now.getTime() / (1000 * 60 * 60 * 24));
		return today - days;
	}

}
