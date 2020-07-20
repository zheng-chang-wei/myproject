/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.factory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.hirain.phm.synapsis.algorithm.domain.AlgorithmStatus;
import com.hirain.phm.synapsis.exception.SynapsisException;

import io.netty.util.internal.StringUtil;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jan 20, 2020 3:28:47 PM
 * @Description
 *              <p>
 *              无论是Python算法还是C算法，其停止和状态查询命令是一致的，所以在此父类中统一实现了
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 20, 2020 zepei.tao@hirain.com 1.0 create file
 */
public class BaseAlgoControlFactory implements AlgoControlFactory {

	private String process_kill_cmd = "kill";

	private String hup_cmd = "-hup";

	private String status_cmd = "ps -p ";

	protected static Runtime rt = Runtime.getRuntime();

	/**
	 * @see com.hirain.phm.synapsis.algorithm.factory.AlgoControlFactory#launch(java.lang.String)
	 */
	@Override
	public void launch(String filePath) throws Exception {
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.factory.AlgoControlFactory#shut(int)
	 */
	@Override
	public void shut(int pid) throws Exception {
		String[] arguments = new String[] { process_kill_cmd, hup_cmd, String.valueOf(pid) };
		Process process = null;
		try {
			process = rt.exec(arguments);
		} catch (Exception e) {
			if (process != null) {
				process.destroy();
			}
			throw new SynapsisException("停止算法失败！");
		}
		// finally {
		// if (process != null) {
		// process.destroy();
		// }
		// }
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.factory.AlgoControlFactory#statusInquire(int)
	 */
	@Override
	public AlgorithmStatus statusInquire(int pid) throws Exception {
		String command = status_cmd + String.valueOf(pid);
		return isProcessRunning(String.valueOf(pid), command);
	}

	private AlgorithmStatus isProcessRunning(String pid, String command) throws Exception {
		try {
			Process pr = rt.exec(command);

			InputStreamReader isReader = new InputStreamReader(pr.getInputStream());
			BufferedReader bReader = new BufferedReader(isReader);
			String strLine = null;
			StringBuilder sb = new StringBuilder(StringUtil.EMPTY_STRING);
			while ((strLine = bReader.readLine()) != null) {
				sb.append(strLine);
			}
			if (sb.toString().contains(pid)) {
				return AlgorithmStatus.WORKING;
			}
			return AlgorithmStatus.STOP;
		} catch (Exception ex) {
			throw ex;
		}
	}

}
