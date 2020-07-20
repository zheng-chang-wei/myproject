/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.factory;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.exception.SynapsisException;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 19, 2019 9:56:39 AM
 * @Description
 *              <p>Python算法的控制器
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 19, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Component(".py")
public class PyAlgoControlFactory extends BaseAlgoControlFactory {

	private String python_start_cmd = "python";

	/** 
	 * @see com.hirain.phm.synapsis.algorithm.factory.AlgoControlFactory#launch()
	 */
	@Override
	public void launch(String filePath) throws Exception {
		String[] arguments = new String[] { python_start_cmd, filePath };
		Process process = null;
		try {
			process = rt.exec(arguments);
		} catch (Exception e) {
			if (process != null) {
				process.destroy();
			}
			throw new SynapsisException("启动Python算法失败！");
		}
		//		finally {
		//			if (process != null) {
		//				process.destroy();
		//			}
		//		}
	}

}
