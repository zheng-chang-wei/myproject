package com.hirain.dome;

import java.util.concurrent.CountDownLatch;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {

	public static void main(String[] args) {
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxIdle(20);
		jpc.setMaxTotal(20);
		// jpc.setMaxWaitMillis(1000);
		final JedisPool jedisPool = new JedisPool(jpc, "127.0.0.1", 6379);

		final CountDownLatch latch = new CountDownLatch(100);
		for (int i = 0; i < 20; i++) {
			final int counter = i;
			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println(counter + ":ready!!!");
					try {
						// latch.await();
						System.out.println(counter + ":" + getInfo(jedisPool));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			latch.countDown();
		}
	}

	public static String getInfo(JedisPool jedisPool) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			setInfo(jedisPool);
			String key = "key123456";
			return jedis.get(key);
		} finally {
			jedis.close();
		}
	}

	public static String setInfo(JedisPool jedisPool) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String key = "key123456";
			return jedis.set(key, "value123456");
		} finally {
			jedis.close();
		}
	}

}
