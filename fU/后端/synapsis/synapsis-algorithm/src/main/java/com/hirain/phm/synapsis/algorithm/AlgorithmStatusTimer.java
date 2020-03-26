/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.message.AlgorithmStatusRequest;
import com.hirain.phm.synapsis.algorithm.message.AlgorithmStatusResponse;
import com.hirain.phm.synapsis.algorithm.service.AlgorithmService;
import com.hirain.phm.synapsis.board.BoardQuery;
import com.hirain.phm.synapsis.board.IBoard;
import com.hirain.phm.synapsis.communication.Communication;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.constant.Program;
import com.hirain.phm.synapsis.constant.SidConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 17, 2019 5:01:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 17, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Slf4j
public class AlgorithmStatusTimer {

	@Autowired
	private BoardQuery boardQuery;

	@Value("${udp.send.timeout:10}")
	private int timeout;

	@Autowired
	private Communication communication;

	@Autowired
	private AlgorithmService service;

	private AtomicBoolean stop;

	private ExecutorService executor = Executors.newSingleThreadExecutor(r -> new Thread(r, getClass().getName()));

	@Value("${synapsis.algorithm.status.period:10000}")
	private int period;

	/**
	 * 启动定时查询算法状态
	 */
	public void start() {
		stop = new AtomicBoolean(false);
		executor.submit(() -> {
			while (!stop.get()) {
				checkStatus();
				try {
					TimeUnit.MILLISECONDS.sleep(period);
				} catch (InterruptedException e) {
				}
			}
		});
	}

	/**
	 * 停止定时查询
	 */
	public void stop() {
		if (stop != null) {
			stop.set(true);
		}
	}

	private void checkStatus() {
		List<IBoard> boards = boardQuery.getBoards("PHM");
		for (IBoard board : boards) {
			AlgorithmStatusRequest request = new AlgorithmStatusRequest(board.getCardIP(), board.getSlotID());
			log.info(request.toString());
			TransportMessage<AlgorithmStatusRequest> message = new TransportMessage<>();
			message.setSource(Program.CPU_SERVICE.getCode());
			message.setTarget(Program.CPU_CONTROL.getCode());
			message.setSid(SidConstant.ALGORITHM_STATUS_COMMAND);
			message.setData(request);
			try {
				Message<?> result = communication.send(message, timeout);
				if (result != null) {
					AlgorithmStatusResponse data = (AlgorithmStatusResponse) result.getData();
					service.update(data.getStatusMap());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
