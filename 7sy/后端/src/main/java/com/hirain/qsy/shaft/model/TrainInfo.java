package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Table(name = "t_train_info")
@Data
public class TrainInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2302648330912410622L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	@Column(name = "train_num")
	private String trainNum;

	@Column(name = "train_type")
	private String trainType;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Transient
	private String trainNums;

	@Override
	public String toString() {
		return "TrainInfo [id=" + id + ", trainNum=" + trainNum + ", trainType=" + trainType + ", createTime=" + createTime + ", trainNums="
				+ trainNums + "]";
	}

}