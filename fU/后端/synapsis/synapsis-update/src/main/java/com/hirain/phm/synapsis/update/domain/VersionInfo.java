/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 9, 2020 2:37:41 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 9, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_version_info")
@ToString
public class VersionInfo implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 3230434958221852520L;

	@Id
	private Long id;

	private String packageName;

	private String cpuVersion;

	private String mvbVersion;

	private String ecnVersion;

	private String ad1Version;

	private String ad2Version;

	private String ad3Version;

	private String phmImx8Version;

	private String phmTx2Version;

	private String phmAgxVersion;

	/**
	 * 上传时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date uploadTime;

	/**
	 * 生效时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date effectiveTime;

	/**
	 * 是否生效
	 */
	private Boolean effectived;

}
