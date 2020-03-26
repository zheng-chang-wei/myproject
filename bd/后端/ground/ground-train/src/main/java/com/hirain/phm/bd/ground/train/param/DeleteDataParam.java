package com.hirain.phm.bd.ground.train.param;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DeleteDataParam {

	private TrainParamHeader[] trainParams;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date date;

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return "[" + Arrays.toString(trainParams) + ", date=" + format.format(date) + "]";
	}

}
