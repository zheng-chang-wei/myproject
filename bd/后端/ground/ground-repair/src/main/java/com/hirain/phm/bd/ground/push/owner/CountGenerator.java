package com.hirain.phm.bd.ground.push.owner;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class CountGenerator {

	private AtomicInteger count = new AtomicInteger(1);

	public synchronized int getCount() {
		count.compareAndSet(100, 1);
		return count.getAndIncrement();
	}
}