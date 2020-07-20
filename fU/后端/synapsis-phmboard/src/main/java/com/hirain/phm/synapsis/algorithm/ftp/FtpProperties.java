/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 17, 2019 6:43:47 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 17, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Data
public class FtpProperties {

	@Value("${synapsis.algomanage.ftp.hostname}")
	private String hostname;

	@Value("${synapsis.algomanage.ftp.port}")
	private int port = 21;

	@Value("${synapsis.algomanage.ftp.username}")
	private String username = "root";

	@Value("${synapsis.algomanage.ftp.password}")
	private String password = "toorHirain";

	@Value("${synapsis.algomanage.ftp.timeout}")
	private Integer timeout = 5000;

	@Value("${synapsis.algomanage.ftp.encoding}")
	private String encoding = "UTF-8";

	@Value("${synapsis.algomanage.ftp.destPath}")
	private String destPath;
}
