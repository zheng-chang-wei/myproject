package com.hirain.phm.bd.message.decode.old;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jan 10, 2019 2:41:59 PM
 * @Description
 *              <p>
 *              运行数据帧
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 10, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
@Deprecated
public class RunDataFrame {

	private int id;

	private int type;

	private DataFrameD1 dataFrameD1;

	private DataFrameD2 dataFrameD2;

	private DataFrameD3 dataFrameD3;

	private DataFrameD4 dataFrameD4;

}
