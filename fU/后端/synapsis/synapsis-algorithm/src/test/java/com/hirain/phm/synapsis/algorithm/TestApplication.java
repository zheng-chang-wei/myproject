/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hirain.phm.synapsis.algorithm.domain.RunStatus;
import com.hirain.phm.synapsis.algorithm.message.AlgorithmStatusResponse;
import com.hirain.phm.synapsis.communication.Communication;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 1:49:46 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
	public Communication communication() {
		return new Communication() {

			private RunStatus status = RunStatus.Run;

			@Override
			public void sendAsync(Message<?> packet) {

			}

			@Override
			public Message<?> send(Message<?> packet, int timeout) throws Exception {
				AlgorithmStatusResponse statusResponse = new AlgorithmStatusResponse();
				Map<Integer, RunStatus> map = new HashMap<>();
				map.put(1, status);
				map.put(2, status);
				statusResponse.setStatusMap(map);
				status = status.equals(RunStatus.Err) ? RunStatus.Run : RunStatus.Err;
				TransportMessage<AlgorithmStatusResponse> message = new TransportMessage<>();
				message.setData(statusResponse);
				return message;
			}
		};
	}
}
