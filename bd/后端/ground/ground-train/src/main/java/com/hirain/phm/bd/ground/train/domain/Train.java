package com.hirain.phm.bd.ground.train.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_train")
public class Train implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463746073971845896L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private String mac1;

	private String mac2;

	private Integer projectId;

	private String trainNo;

	private Byte trainStatus;

	private Boolean trainOnline;

	private String configInfo;

	private String protocolFile;

	private String serverip1;

	private String serverip2;

	private int doorCount;

	@Column(name = "offsets")
	private Integer offset;

}