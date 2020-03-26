package com.hirain.phm.bd.transform.message;

public interface IMessageProcessor {

	void push(String topic, byte[] payload);

	void process();

}
