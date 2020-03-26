package com.hirain.phm.bd.message.header;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 下午3:29:31
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class MessageHeader {

	private String city;

	private String line;

	private String project;

	private String train;

	private int sid;
}
