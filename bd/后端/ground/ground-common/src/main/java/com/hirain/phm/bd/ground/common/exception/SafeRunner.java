package com.hirain.phm.bd.ground.common.exception;

import com.hirain.phm.bd.ground.common.model.ResponseBo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SafeRunner {

	public static <T> T run(ISafeRunnble<T> runnable, T defaultError) {
		try {
			return runnable.run();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return defaultError;
		}
	}

	public static ResponseBo getResponse(ISafeRunnble<ResponseBo> runnable, String error) {
		return run(runnable, ResponseBo.error(error));
	}
}
