/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.SmartLifecycle;

import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.connection.Connection;
import com.hirain.phm.synapsis.udp.codec.MessageCodec;
import com.hirain.phm.synapsis.udp.handler.ServerHandler;
import com.hirain.phm.synapsis.udp.handler.UDPMessageCodec;
import com.hirain.phm.synapsis.udp.packet.UDPPacket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 2:39:47 PM
 * @Description
 *              <p>
 *              UDP通信连接
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
public class UDPServer implements Connection, SmartLifecycle, UdpCallback {

	@Autowired
	private UdpProperties properties;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private MessageCodec codec;

	private Channel channel;

	private int port;

	private volatile boolean running;

	protected final ReentrantLock lifecycleLock = new ReentrantLock();

	private UdpCallback callback = this;

	private ExecutorService pool = Executors.newFixedThreadPool(1);

	private Map<Integer, SyncSubscriber<Message<?>>> subscribers = new ConcurrentHashMap<>();

	public UDPServer(int port) {
		this.port = port;
	}

	public UDPServer(int port, UdpCallback callback) {
		this.port = port;
		this.callback = callback;
	}

	public UDPServer(int port, MessageCodec codec) {
		this.port = port;
		this.codec = codec;
	}

	/**
	 * 自启动
	 * 
	 * @see org.springframework.context.Lifecycle#start()
	 */
	@Override
	public void start() {
		lifecycleLock.lock();
		try {
			if (!running) {
				doStart();
				running = true;
				log.info("started " + this);
			}
		} finally {
			lifecycleLock.unlock();
		}
	}

	/**
	 * @see org.springframework.context.Lifecycle#stop()
	 */
	@Override
	public void stop() {
		lifecycleLock.lock();
		try {
			if (running) {
				doStop();
				running = false;
				log.info("stopped " + this);
			}
		} finally {
			lifecycleLock.unlock();
		}
	}

	private void doStart() {
		Bootstrap bootstrap = new Bootstrap();
		NioEventLoopGroup group = new NioEventLoopGroup();
		bootstrap.group(group);

		bootstrap.channel(NioDatagramChannel.class);
		bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(properties.getRecvByteAllocate()));
		bootstrap.option(ChannelOption.SO_RCVBUF, properties.getRcvBuf());
		bootstrap.option(ChannelOption.SO_REUSEADDR, properties.isReuseAddr());
		bootstrap.handler(new ChannelInitializer<NioDatagramChannel>() {

			@Override
			protected void initChannel(NioDatagramChannel ch) throws Exception {
				ch.pipeline().addLast("MessageCodec", new UDPMessageCodec());
				ch.pipeline().addLast("ServerHandler", new ServerHandler(UDPServer.this));
			}
		});
		try {
			ChannelFuture future = bootstrap.bind(port).sync().await();
			channel = future.channel();
			log.info("bind:" + channel.localAddress());
		} catch (Exception e) {
			log.error("监听端口：" + port + "失败", e);
		}
	}

	/**
	 * 
	 */
	private void doStop() {
		// do nothing
	}

	/**
	 */
	@Override
	public Message<?> send(Message<?> packet, int timeout) throws Exception {
		SyncSubscriber<Message<?>> subscriber = new SyncSubscriber<>(packet.getSid());
		subscribers.put(packet.getSid(), subscriber);
		send(packet);
		Message<?> result = subscriber.get(timeout, TimeUnit.SECONDS);
		subscriber = null;
		subscribers.remove(packet.getSid());
		return result;
	}

	/**
	 */
	@Override
	public void sendAsync(Message<?> packet) {
		send(packet);
	}

	/**
	 * 编码后发送
	 * 
	 * @param packet
	 */
	private void send(Message<?> packet) {
		if (codec == null) {
			throw new NullPointerException("MessageCodec is NULL");
		}
		UDPPacket msg = codec.encode(packet);
		System.err.println(System.currentTimeMillis() + " " + port + ": send " + msg);
		channel.writeAndFlush(msg);
	}

	/**
	 * handler收到的数据会到达这里
	 * 
	 * @param msg
	 */
	public void messageArrived(UDPPacket msg) {
		System.err.println(System.currentTimeMillis() + " " + port + ": receive " + msg);
		Message<?> packet = codec.decode(msg);
		callback.messageArrived(packet);
	}

	/**
	 * @see org.springframework.context.Lifecycle#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return false;
	}

	/**
	 * @see com.hirain.phm.synapsis.udp.UdpCallback#messageArrived(com.hirain.phm.synapsis.udp.packet.UDPPacket)
	 */
	@Override
	public void messageArrived(Message<?> packet) {
		pool.submit(() -> {
			SyncSubscriber<Message<?>> subscriber = subscribers.get(packet.getSid());
			if (subscriber != null && subscriber.getSid() == packet.getSid()) {
				// 转同步
				subscriber.setResponse(packet);
			} else {
				System.err.println(port + ":" + "publish");
				// 推给观察者
				publisher.publishEvent(packet.getData());
			}
		});
	}

}
